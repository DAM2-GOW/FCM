package gow.fcm.pantallas;

import gow.fcm.basedatos.ConexionSQLite;
import gow.fcm.footballcoachmanager.R;
import gow.fcm.popups.PopUpNewUser;
import gow.fcm.popups.PopUpRecoverPassword;
import gow.fcm.sentencias.SentenciasSQLLoginScreen;
import gow.fcm.sharefprefs.DatosFootball;
import gow.fcm.utilidades.AnimatorLogin;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginScreen extends Activity {
	private AnimatorLogin AL, AL2;
	private EditText et_username, et_password;
	private SentenciasSQLLoginScreen ssls;
	private TextView tv_pass;
	private Button btn_singIn, btn_singUp, btn_DEMO;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login_screen);
		
		//Se crea la base de datos si no existe o se actualiza.
		ConexionSQLite.getCrearSQLite(this);
		//SentenciasInsertSQLite.insertarSQLite("Equipos", new String[] {"nombre"}, new String[] {"MismaCity"});//DEV
		//SentenciasInsertSQLite.insertarSQLite("Entrenadores", new String[] {"id_equipo","nombre","apellidos", "usuario","clave","pregunta_seguridad","respuesta_seguridad"}, new String[] {"0","Mismo","Mismamente","user","1234","0","no lo se"});//DEV
		
		ssls = new SentenciasSQLLoginScreen();
		et_username = (EditText) findViewById(R.id.editText_username);
		et_password = (EditText) findViewById(R.id.editText_password);
		tv_pass = (TextView) findViewById(R.id.textView_recover_pass);
		btn_singIn = (Button) findViewById(R.id.btn_singIn);
		btn_singUp = (Button) findViewById(R.id.btn_singUp);
		
		// Contenido para slideShow de imagenes.
		View myView1 = (View) findViewById(R.id.view_image1);
		View myView2 = (View) findViewById(R.id.view_image2);
		AL = new AnimatorLogin(this, getApplicationContext(), myView1, true);
		AL2 = new AnimatorLogin(this, getApplicationContext(), myView2, false);
		
		btn_singIn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				// Comprobamos los campos.
				final String username = et_username.getText().toString();
				String pass= et_password.getText().toString();
				if((username == null) || (username.equals(""))){
					// Comprobar que el campo usuario tenga contenido escrito.
					Toast.makeText(getApplicationContext(), getString(R.string.login_screen_toast_username_null), Toast.LENGTH_SHORT).show();
				}else if((pass == null) || (pass.equals(""))){
					// Comprobar que el campo contraseña tenga contenido escrito.
					Toast.makeText(getApplicationContext(), getString(R.string.login_screen_toast_password_null), Toast.LENGTH_SHORT).show();
				}else if(ssls.comprobarNombreUsuario(username)==0){
					// Comprobar en la DB si existe el usuario.
					Toast.makeText(getApplicationContext(), getString(R.string.login_screen_toast_username_not_exist), Toast.LENGTH_SHORT).show();
				}else if(ssls.comprobarPasswordUsuario(username, pass)==0){
					// Comprobar en la DB si se corresponde la contraseña con el usuario dado.
					Toast.makeText(getApplicationContext(), getString(R.string.login_screen_toast_password_not_match), Toast.LENGTH_SHORT).show();
				}else{
					// Detenemos los hilos que ejecutan la animacion.
					AL.stopThread();
					AL2.stopThread();
					//AL = null;
					//AL2 = null;
					
					// Si todo esta correcto, entrar a la pantalla principal y poner entrenador definido por el usuario
					// en las sharedPrefs. MEJORA: Context menu para preguntar por equipo a usar.
					int entrenadorID = ssls.obtenerIDEntrenador(username);
					int equipoAUsar = ssls.obtenerEquiposEntrenador(entrenadorID);
					DatosFootball.setDatosFootball(getApplicationContext(), equipoAUsar, entrenadorID);
					Intent i = new Intent(getApplicationContext(), PaginaPrincipal.class);
					startActivity(i);
					
				}
			}
		});
		
		btn_singUp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Iniciamos un PopUp para registro de entrenador nuevo.
				Intent i = new Intent(getApplicationContext(),  PopUpNewUser.class);
				startActivityForResult(i, 001);
			}
		});
		
		tv_pass.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Se abre un pop-up para recuperar la contraseña.
				Intent i = new Intent(getApplicationContext(), PopUpRecoverPassword.class);
				startActivityForResult(i, 002);
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login_screen, menu);
		return true;
	}

	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// Este metodo es llamado automaticamente cuando iniciamos un activity nuevo
		// con un 'Intent' y el metodo 'startActivityForResult()', en este se reciben
		// los datos que nos devuelve el Activity al terminar su vida util.
		switch(requestCode){
		case 001: // Codigo que proviene de crear un nuevo usuario.
			if(resultCode==Activity.RESULT_OK){
				Toast.makeText(getApplicationContext(), getString(R.string.login_screen_toast_new_user_complete), Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(getApplicationContext(), getString(R.string.login_screen_toast_new_user_cancel), Toast.LENGTH_SHORT).show();
			}
			break;
		case 002: // Codigo que proviene de recordar contraseña.
			if(resultCode==Activity.RESULT_OK){
				Toast.makeText(getApplicationContext(), getString(R.string.login_screen_toast_recover_password), Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(getApplicationContext(), getString(R.string.login_screen_toast_recover_password_cancel), Toast.LENGTH_SHORT).show();
			}
			break;
		
		default:
			break;
		}
	}
}
