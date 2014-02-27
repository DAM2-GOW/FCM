package gow.fcm.pantallas;

import gow.fcm.footballcoachmanager.R;
import gow.fcm.sentencias.SentenciasSQLiteObservaciones;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class Obsevaciones extends Activity{
	
	private String varFechaEvento="date_event",varPosicion="position";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_observaciones);
		//Quitamos el header y mostramos las observaciones
		
		Intent i=getIntent();
		String posicion=i.getExtras().getString(varPosicion);
		String dia=i.getExtras().getString(varFechaEvento);
		
		SentenciasSQLiteObservaciones.obtenerObservaciones(this,posicion,dia);
		String obervaciones=SentenciasSQLiteObservaciones.getObservaciones();
		
		TextView textoObservaviones=(TextView) findViewById(R.id.textView_observaciones);
		
		textoObservaviones.setText(obervaciones);
	}

}