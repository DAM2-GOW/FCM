package gow.fcm.popups;

import gow.fcm.basedatos.ConexionSQLite;
import gow.fcm.footballcoachmanager.R;
import gow.fcm.footballcoachmanager.R.layout;
import gow.fcm.footballcoachmanager.R.menu;
import gow.fcm.sentencias.SentenciasInsertSQLite;
import gow.fcm.sharefprefs.DatosFootball;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.TimePicker.OnTimeChangedListener;

public class PopUpNuevoPartido extends Activity {

	TimePicker tp;
	DatePicker dp;
	Button bt;
	EditText lugar,rival;
	String fechaEntrenamiento="", horaMinuto, diaEntrenamiento, mesEntrenamiento, anyoEntrenamiento;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		showAsPopup(this); //Llama al método que pone el activity en modo ventana PopuP.
		
		setContentView(R.layout.activity_popup_nuevo_partido);
		lugar = (EditText) findViewById(R.id.lugarPartidoNuevo);
		rival = (EditText) findViewById(R.id.rivalPartido);
		
		//Llama a las clases necesarias para recoger los datos y guardarlos en la BD.
		ConexionSQLite.getCrearSQLite(this);
		DatosFootball.getDatosFootball(this);
		
		final int id_equipo = DatosFootball.getIdEquipo();
		
		//Modificamos el diseño de la fecha para que no se muestre un calendario.
		dp = (DatePicker) findViewById(R.id.fecha_PartidoNuevo);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
	        dp.setCalendarViewShown(false);
	    }
		
		dp.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS); //Evitamos que el usuario lo cambie a mano 
		dp.setMinDate(System.currentTimeMillis()-1000); //Obtenemos la fecha seleccionada y la ponemos como la mínima
		
		//Modificamos el diseño de la hora para que este en formato 24H.
		tp = (TimePicker) findViewById(R.id.hora_PartidoNuevo);
		tp.setIs24HourView(DateFormat.is24HourFormat(this));
		
		tp.setOnTimeChangedListener(new OnTimeChangedListener() {
			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				horaMinuto = String.valueOf(hourOfDay)+":"+String.valueOf(minute)+":00";
			}
		});
		
		tp.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS); //Evitamos que el usuario lo cambie a mano 
		
		//Guardamos el partido en el calendario recogiendo los datos pertinentes.
		bt = (Button) findViewById(R.id.guardarPartidoNuevo);
		bt.setOnClickListener(new OnClickListener(){
			 
            @Override
            public void onClick(View v) {
            	if(lugar.getText().toString().trim().equals("") || lugar.getText().toString().trim()==null ){
            		Toast.makeText(getApplicationContext(), "Esta vacio el campo lugar partido, rellenalo", Toast.LENGTH_SHORT).show();
            		
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
            		
            		SentenciasInsertSQLite.insertarSQLite("Partidos", new String[]{"id_equipo","lugar","rival","dia","fecha"}, new String[]{String.valueOf(id_equipo),lugar.getText().toString(),rival.getText().toString(),fechaEntrenamiento,fechaEntrenamiento+" "+horaMinuto});
                	PopUpNuevoPartido.this.finish();
            }
            }
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pop_up_nuevo_partido, menu);
		return true;
	}
	
	//Metodo que hace mostrar el activity como PopUp.
	public static void showAsPopup(Activity activity) {
		activity.requestWindowFeature(Window.FEATURE_ACTION_BAR); // Esta caracteristica habilita la barra (menu) en el Pop-Up.
		activity.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_DIM_BEHIND,
				WindowManager.LayoutParams.FLAG_DIM_BEHIND); // Marcamos activity para habilitar opciones diversas.
		LayoutParams params = activity.getWindow().getAttributes(); // Obtenemos objeto de configuracion del Activity.
		params.height = LayoutParams.WRAP_CONTENT; // Adaptamos el tamaño en altura segun componentes del XML.
		params.width = 800; // Fijamos el tamaño en anchura.
		params.alpha = 1.0f; // Podemos otorgarle transparencia al Pop-Up.
		params.dimAmount = 0.5f; // Fijamos el nivel de oscuridad para el activity de fondo.
		activity.getWindow().setAttributes(
				(android.view.WindowManager.LayoutParams) params); // Aplicamos los valores establecidos al Activity.
	}

}
