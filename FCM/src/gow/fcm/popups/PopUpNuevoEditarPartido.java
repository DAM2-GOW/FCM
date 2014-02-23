package gow.fcm.popups;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import gow.fcm.basedatos.ConexionSQLite;
import gow.fcm.footballcoachmanager.R;
import gow.fcm.sentencias.SentenciasInsertSQLite;
import gow.fcm.sentencias.SentenciasSQLiteNuevoEditarPartido;
import gow.fcm.sentencias.SentenciasUpdateSQLite;
import gow.fcm.sharefprefs.DatosFootball;
import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.TimePicker.OnTimeChangedListener;

public class PopUpNuevoEditarPartido extends Activity {

	private TimePicker tp;
	private DatePicker dp;
	private Button bt;
	private EditText lugar,rival;
	//Elementos que interactuan con el popup e Intents de otras páginas
	private String fechaEntrenamiento="", horaMinuto, diaEntrenamiento, mesEntrenamiento, anyoEntrenamiento, varFechaEvento="date_event", varAccion="action", times;
	private int hour,min; //Variables que almacenan la hora del sistema o del evento
	@SuppressLint("SimpleDateFormat")
	private SimpleDateFormat formato=new SimpleDateFormat("yyyy-MM-dd"); //Formato de conversión a Date
	
	//Método que devuelve la fecha actual
	private String getFechaActual(){
		//Obtenemos la fecha actual
		Date dates=new Date();
		String fecha=formato.format(dates);
		return fecha;
	}
	
	@SuppressLint("SimpleDateFormat")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		showAsPopup(this); //Llama al método que pone el activity en modo ventana PopuP
		setContentView(R.layout.activity_popup_nuevo_editar_partido);
		
		Intent i=getIntent();
		final long fecha=i.getExtras().getLong(varFechaEvento);
		final String accion=i.getExtras().getString(varAccion);
		
		//Declaramos los elementos a usar por el Popup
		lugar = (EditText) findViewById(R.id.lugarPartidoNuevo);
		rival = (EditText) findViewById(R.id.rivalPartido);
		dp = (DatePicker) findViewById(R.id.fecha_PartidoNuevo);
		tp = (TimePicker) findViewById(R.id.hora_PartidoNuevo);
		bt = (Button) findViewById(R.id.guardarPartidoNuevo);
		
		if(accion.equals("editar")){
			times=formato.format(fecha);
			SentenciasSQLiteNuevoEditarPartido.getDatosEditarPartido(this,times);
			
			//Obtenemos los datos
			String placeMatch=SentenciasSQLiteNuevoEditarPartido.getLugarPartido();
			String rivalMatch=SentenciasSQLiteNuevoEditarPartido.getRivalPartido();
			
			lugar.setText(placeMatch);
			rival.setText(rivalMatch);
		}
		
		//Llama a las clases necesarias para recoger los datos y guardarlos en la BD.
		ConexionSQLite.getCrearSQLite(this);
		DatosFootball.getDatosFootball(this);
		final int id_equipo = DatosFootball.getIdEquipo();
		
		//Modificamos el diseño de la fecha para que no se muestre un calendario.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
	        dp.setCalendarViewShown(false);
	    }
		
		dp.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS); //Evitamos que el usuario lo cambie a mano
		
		if(accion.equals("agregar")){
			dp.setMinDate(fecha); //Obtenemos la fecha del dia seleccionado en el calendario y la ponemos como la mínima
			dp.setMaxDate(fecha); //Obtenemos la fecha del dia seleccionado en el calendario y la ponemos como la máxima
		}else if(accion.equals("editar")){
			//Obtenemos los datos
			String diaMatch=SentenciasSQLiteNuevoEditarPartido.getDiaPartido();
			
			//Pasamos la fecha seleccionada a milisegundos
			Date date=null;
			try{
				date=formato.parse(diaMatch);
			}catch (ParseException e){
				e.printStackTrace();
			}
			long fechas=date.getTime(); //Guardamos la fecha en formato long
			
			dp.setMinDate(fechas); //Obtenemos la fecha del dia seleccionado en el calendario y la ponemos como la mínima
			dp.setMaxDate(fechas); //Obtenemos la fecha del dia seleccionado en el calendario y la ponemos como la máxima
		}
		
		//Modificamos el diseño de la hora para que este en formato 24H.
		tp.setIs24HourView(true);
		
		//Pasamos la fecha seleccionada a milisegundos
		String dates=getFechaActual();
		Date date=null;
		try{
			date=formato.parse(dates);
		}catch (ParseException e){
			e.printStackTrace();
		}
		final long fechasMilis=date.getTime(); //Guardamos la fecha en formato long
		
		if(accion.equals("agregar")){
			Date horaActual=new Date(System.currentTimeMillis());
			SimpleDateFormat formato=new SimpleDateFormat("HH:mm:ss"); //Formato de conversión a hora
			String hora=formato.format(horaActual);
			
			//Obtenemos la hora
			String horaPartido="";
			for(int num=0;num<=1;num++){
				horaPartido=horaPartido.concat(String.valueOf(hora.charAt(num+0)));
				horaPartido=horaPartido.replace(":","");
			}
			hour=Integer.parseInt(horaPartido);
			
			//Obtenemos los minutos
			String minutosPartido="";
			for(int num=0;num<=1;num++){
				int index=hora.indexOf(":");
				minutosPartido=minutosPartido.concat(String.valueOf(hora.charAt(num+index)));
				minutosPartido=minutosPartido.replace(":","");
			}
			min=Integer.parseInt(minutosPartido);
			
			tp.setCurrentHour(hour); //Especificamos la hora actual para el formato de 24 horas
			tp.setCurrentMinute(min); //Especificamos la hora actual para el formato de 24 horas
		}else if(accion.equals("editar")){
			//Obtenemos los datos
			hour=Integer.parseInt(SentenciasSQLiteNuevoEditarPartido.getHoraPartido());
			min=Integer.parseInt(SentenciasSQLiteNuevoEditarPartido.getMinutosPartido());
			
			tp.setCurrentHour(hour); //Especificamos la hora actual para el formato de 24 horas
			tp.setCurrentMinute(min); //Especificamos la hora actual para el formato de 24 horas
		}
		
		horaMinuto=String.valueOf(hour)+":"+String.valueOf(min)+":00"; //Almacenamos la hora actual para que se guarde un valor null
		
		tp.setOnTimeChangedListener(new OnTimeChangedListener() {
			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				//Si la fecha coincide con la actual, ponemos la hora actual como mínima
				if(fechasMilis==fecha){
					if(hourOfDay<hour){
						tp.setCurrentHour(hour);
					}else if(minute<min){
						tp.setCurrentMinute(min);
					}
				}
				
				horaMinuto = String.valueOf(hourOfDay)+":"+String.valueOf(minute)+":00";
			}
		});
		
		tp.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS); //Evitamos que el usuario lo cambie a mano 
		
		//Guardamos el partido en el calendario recogiendo los datos pertinentes.
		bt.setOnClickListener(new OnClickListener(){
			 
            @Override
            public void onClick(View v) {
            	if(lugar.getText().toString().trim().equals("") || lugar.getText().toString().trim().equals(null)|| rival.getText().toString().trim().equals("") || rival.getText().toString().trim().equals(null)){
            		Toast.makeText(getApplicationContext(), "Debes rellenar todos los campos", Toast.LENGTH_SHORT).show();
            		
            	}else{
            		diaEntrenamiento = String.valueOf(dp.getDayOfMonth());
            		if(Integer.parseInt(diaEntrenamiento)<10){
            			diaEntrenamiento="0"+diaEntrenamiento;
            		}
            		mesEntrenamiento = String.valueOf(dp.getMonth()+1);
            		if(Integer.parseInt(mesEntrenamiento)<10){
            			mesEntrenamiento="0"+mesEntrenamiento;
            		}
            		anyoEntrenamiento = String.valueOf(dp.getYear());
            		
            		fechaEntrenamiento = fechaEntrenamiento.concat(anyoEntrenamiento+"-").concat(mesEntrenamiento+"-").concat(diaEntrenamiento);
            		
            		if(accion.equals("agregar")){
            			SentenciasInsertSQLite.insertarSQLite("Partidos", new String[]{"id_equipo","lugar","rival","dia","fecha"}, new String[]{String.valueOf(id_equipo),lugar.getText().toString(),rival.getText().toString(),fechaEntrenamiento,fechaEntrenamiento+" "+horaMinuto});
            		}else if(accion.equals("editar")){
            			SentenciasUpdateSQLite.actualizarSQLite("Partidos", new String[]{"lugar","rival","fecha"}, new String[]{lugar.getText().toString(),rival.getText().toString(),fechaEntrenamiento+" "+horaMinuto},"id_equipo="+id_equipo+" AND dia='"+times+"'");
            		}
            		
            		PopUpNuevoEditarPartido.this.finish();
            }
            }
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pop_up_nuevo_partido, menu);
		MenuItem item = menu.findItem(R.id.action_settings);
		item.setVisible(false);
		return true;
	}
	
	//Metodo que hace mostrar el activity como PopUp.
	public static void showAsPopup(Activity activity) {
		activity.requestWindowFeature(Window.FEATURE_ACTION_BAR); // Esta caracteristica habilita la barra (menu) en el Pop-Up.
		activity.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_DIM_BEHIND,
				WindowManager.LayoutParams.FLAG_DIM_BEHIND); // Marcamos activity para habilitar opciones diversas.
		LayoutParams params = activity.getWindow().getAttributes(); // Obtenemos objeto de configuracion del Activity.
		params.height = android.view.ViewGroup.LayoutParams.WRAP_CONTENT; // Adaptamos el tamaño en altura segun componentes del XML.
		params.width = 800; // Fijamos el tamaño en anchura.
		params.alpha = 1.0f; // Podemos otorgarle transparencia al Pop-Up.
		params.dimAmount = 0.5f; // Fijamos el nivel de oscuridad para el activity de fondo.
		activity.getWindow().setAttributes(
				params); // Aplicamos los valores establecidos al Activity.
	}

}
