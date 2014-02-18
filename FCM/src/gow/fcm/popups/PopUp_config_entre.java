/*package gow.fcm.popups;

import gow.fcm.footballcoachmanager.R;
import gow.fcm.pantallas.Config_entre;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/*public class PopUp_config_entre extends Activity {
Spinner sp;
EditText txtDato1;
EditText txtDato2;
Button enviar;
String resultModulo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		showAsPopup(this);
		setContentView(R.layout.popup_config_entre);
		
		txtDato1 = (EditText) findViewById(R.id.dato1);
		txtDato2 = (EditText) findViewById(R.id.dato2);
		enviar = (Button) findViewById(R.id.btnEnviar);
		
	
		ArrayAdapter<CharSequence> adaptador = ArrayAdapter.createFromResource(this, R.array.TipoModulo, android.R.layout.simple_spinner_item);
		sp = (Spinner) findViewById(R.id.modulo);
		sp.setAdapter(adaptador);
		sp.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				 resultModulo = sp.getSelectedItem().toString();
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			
				
			}
			
		});
		
		enviar.setOnClickListener(new OnClickListener() {
			
			@Override
//			public void onClick(View v) {
			/*	Intent info = new Intent(PopUp_config_entre.this,Config_entre.class);
				info.putExtra("dato1", txtDato1.getText().toString());
				info.putExtra("dato2", txtDato2.getText().toString());
				info.putExtra("modulo", resultModulo.toString());
				setResult(Activity.RESULT_OK, info);
				
			}
			});
		
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pop_up_config_entre, menu);
		return true;
	}
	
	/**
	 * Este metodo permite la personalizacion de este activity para terminar de
	 * convertirlo, junto al uso de '@style/', en un Activity de menu o Pop-Up.
	 * Para conocer toda la informacion mirar tambien el archivo XML 'styles',
	 * en la carpeta values.
	 * 
	 * @param activity : le pasamos el activity actual para personalizarlo.
	 */
	/*public static void showAsPopup(Activity activity) {
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

*/

