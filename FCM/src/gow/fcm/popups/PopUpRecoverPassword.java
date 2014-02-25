package gow.fcm.popups;

import gow.fcm.footballcoachmanager.R;
import gow.fcm.sentencias.SentenciasSQLLoginScreen;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PopUpRecoverPassword extends Activity {
	EditText user, respuestaSecreta;
	TextView preguntaSecreta, mostrarPassword;
	Button acces1, acces2, btn_volver;
	SentenciasSQLLoginScreen ssls;
	String usuario;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		showAsPopup(this);
		setContentView(R.layout.popup_recover_password);
		ssls = new SentenciasSQLLoginScreen();
		
		// Se obtienen los componentes.
		user = (EditText) findViewById(R.id.txtUsuario);
		respuestaSecreta= (EditText) findViewById(R.id.respuestaSecreta);
		preguntaSecreta = (TextView) findViewById(R.id.textView_pregunta_secreta);
		acces1 = (Button) findViewById(R.id.btnConfirmar1);
		acces2 = (Button) findViewById(R.id.btnConfirmar2);
		btn_volver = (Button) findViewById(R.id.btn_volver);
		mostrarPassword = (TextView) findViewById(R.id.textView_show_password);
		
		// Se bloquean las secciones para abrirlas segun se avance.
		acces2.setEnabled(false);
		btn_volver.setEnabled(false);
		respuestaSecreta.setEnabled(false);

		acces1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				usuario = user.getText().toString().trim();
				if((usuario == null) || (usuario.equals(""))){
					// Se comprueba que el campo usuario tenga contenido.
					Toast.makeText(getApplicationContext(), getString(R.string.login_screen_toast_new_username_null), Toast.LENGTH_SHORT).show();
				}else if(ssls.comprobarNombreUsuario(usuario)==0){
					// Se comprueba que existe la cuenta.
					Toast.makeText(getApplicationContext(), getString(R.string.login_screen_toast_username_not_exist), Toast.LENGTH_SHORT).show();
				}else{
					// Se obtiene la pregunta de seguridad de esa cuenta.
					acces2.setEnabled(true);
					respuestaSecreta.setEnabled(true);
					int ps = ssls.obtenerPreguntaSeguridad(usuario);
					String[] pregseg = getResources().getStringArray(R.array.PreguntasSeguridad);
					preguntaSecreta.setText(pregseg[ps]);
				}
			}
		});
		
		acces2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				String respseg = respuestaSecreta.getText().toString().trim();
				if((respseg==null) || (respseg.equals(""))){
					// Se comprueba que el campo de pregunta secreta tenga contenido.
					Toast.makeText(getApplicationContext(), getString(R.string.login_screen_toast_security_answer_null), Toast.LENGTH_SHORT).show();
				}else{
					int rp = ssls.comprobarPreguntaSeguridad(usuario, respseg);
					if(rp==0){
						// Se comprueba que la respuesta se corresponde con el usuario.
						Toast.makeText(getApplicationContext(), getString(R.string.login_screen_toast_secret_answer_not_match), Toast.LENGTH_SHORT).show();
					}else{
						btn_volver.setEnabled(true);
						String pass = ssls.recuperarPassword(usuario, respseg);
						mostrarPassword.setText(pass);
					}
				}	
			}
		});
		
		btn_volver.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Se cierra la ventana.
				Intent i = new Intent();
				setResult(RESULT_OK, i);
				PopUpRecoverPassword.this.finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.popup_recover_password, menu);
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
	private static void showAsPopup(Activity activity) {
		activity.requestWindowFeature(Window.FEATURE_ACTION_BAR); // Esta caracteristica habilita la barra (menu) en el Pop-Up.
		activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND,WindowManager.LayoutParams.FLAG_DIM_BEHIND); // Marcamos activity para habilitar opciones diversas.
		LayoutParams params = activity.getWindow().getAttributes(); // Obtenemos objeto de configuracion del Activity.
		params.height = 500; // Adaptamos el tamaño en altura segun componentes del XML.
		params.width = 1000; // Fijamos el tamaño en anchura.
		params.alpha = 1.0f; // Podemos otorgarle transparencia al Pop-Up.
		params.dimAmount = 0.5f; // Fijamos el nivel de oscuridad para el activity de fondo.
		activity.getWindow().setAttributes(params); // Aplicamos los valores establecidos al Activity.
	}
}
