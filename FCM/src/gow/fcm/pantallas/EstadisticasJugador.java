package gow.fcm.pantallas;

import gow.fcm.footballcoachmanager.R;
import gow.fcm.footballcoachmanager.R.layout;
import gow.fcm.footballcoachmanager.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class EstadisticasJugador extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_estadisticas_jugador);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.estadisticas_jugador, menu);
		return true;
	}
	
	//jajajajajaja

}
