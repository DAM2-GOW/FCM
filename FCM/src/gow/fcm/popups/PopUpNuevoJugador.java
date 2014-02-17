package gow.fcm.popups;

import gow.fcm.footballcoachmanager.R;
import gow.fcm.footballcoachmanager.R.layout;
import gow.fcm.footballcoachmanager.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class PopUpNuevoJugador extends Activity {

	Spinner tipoJugador, posicionJugador;
	String posJug;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		showAsPopup(this);
		setContentView(R.layout.activity_popup_nuevo_jugador);
		
		tipoJugador = (Spinner)findViewById(R.id.spinnerTipoJugador);
		posicionJugador = (Spinner)findViewById(R.id.spinnerPosicionJugador);
		
		ArrayAdapter<CharSequence> adaptadorTipo = ArrayAdapter.createFromResource(this, R.array.TipoJugador, android.R.layout.simple_spinner_item);
		final ArrayAdapter<CharSequence> adaptador2 = ArrayAdapter.createFromResource(this, R.array.PosicionAtaque, android.R.layout.simple_spinner_item);
		
		final ArrayAdapter<CharSequence> adaptador3 = ArrayAdapter.createFromResource(this, R.array.PosicionDefensa, android.R.layout.simple_spinner_item);
		
		final ArrayAdapter<CharSequence> adaptador4 = ArrayAdapter.createFromResource(this, R.array.PosicionEE, android.R.layout.simple_spinner_item);
		
		posicionJugador.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
				if(position == 0){
					posicionJugador.setAdapter(adaptador2);
					posicionJugador.setOnItemSelectedListener(new OnItemSelectedListener(){

						@Override
						public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
							posJug = posicionJugador.getSelectedItem().toString();
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
						}
						
					});
				} else {
					if(position == 1){
						posicionJugador.setAdapter(adaptador3);
						posicionJugador.setOnItemSelectedListener(new OnItemSelectedListener() {

							@Override
							public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
								posJug = posicionJugador.getSelectedItem().toString();
							}

							@Override
							public void onNothingSelected(AdapterView<?> arg0) {
							}
							
						});
					}else{
						if(position == 2){
							posicionJugador.setAdapter(adaptador4);
							posicionJugador.setOnItemSelectedListener(new OnItemSelectedListener() {

								@Override
								public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
									posJug = posicionJugador.getSelectedItem().toString();
								}

								@Override
								public void onNothingSelected(AdapterView<?> arg0) {
								}
								
							});
						}
					}
				}
			}

			public void onNothingSelected(AdapterView<?> parentView) {

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pop_up_nuevo_jugador, menu);
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
