package gow.fcm.popups;

import gow.fcm.basedatos.ConexionSQLite;
import gow.fcm.footballcoachmanager.R;
import gow.fcm.sentencias.SentenciasInsertSQLite;
import gow.fcm.sharefprefs.DatosFootball;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TimePicker;

public class PopUpNuevoEntrenamiento extends Activity {

	TimePicker tp;
	DatePicker dp;
	Spinner sp;
	Button bt;
	EditText titulEntrenamiento;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		showAsPopup(this);
		
		setContentView(R.layout.activity_popup_nuevo_entrenamiento);
		ConexionSQLite.getCrearSQLite(this);
		DatosFootball.getDatosFootball(this);
		final int id_equipo = DatosFootball.getIdEquipo();
		titulEntrenamiento = (EditText) findViewById(R.id.titulo_entrenamientoNuevo);
		
		ArrayAdapter<CharSequence> adaptador = ArrayAdapter.createFromResource(this, R.array.TipoEntrenamiento, android.R.layout.simple_spinner_item);
		sp = (Spinner)findViewById(R.id.tipo_entrenamientoNuevo);
		sp.setAdapter(adaptador);
		
		sp.setOnItemSelectedListener(new OnItemSelectedListener() {
			
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				String stringTipoEntrenamiento = sp.getSelectedItem().toString();				
			}
			public void onNothingSelected(AdapterView<?> parentView){
				
			}
		});
		
		dp = (DatePicker) findViewById(R.id.fecha_entrenamientoNuevo);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
	        dp.setCalendarViewShown(false);
	    }
		
		tp = (TimePicker) findViewById(R.id.hora_entrenamientoNuevo);
		tp.setIs24HourView(DateFormat.is24HourFormat(this));
		
		bt = (Button) findViewById(R.id.guardarEntrenamientoNuevo);
		bt.setOnClickListener(new OnClickListener( ){
			 
            @Override
            public void onClick(View v) {
            	//SentenciasInsertSQLite.insertarSQLite("Entrenamientos", new String[]{"id_equipo","tipo","dirigido","dia","fecha"}, new String[]{""+id_equipo+"", ""+titulEntrenamiento+"", ""+sp+"", ""+tp+"", ""+dp+""});
            	PopUpNuevoEntrenamiento.this.finish();
            }
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pop_up_nuevo_entrenamiento, menu);
		return true;
	}
	
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
