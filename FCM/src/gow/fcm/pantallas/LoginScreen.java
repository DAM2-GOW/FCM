package gow.fcm.pantallas;

import gow.fcm.footballcoachmanager.R;
import gow.fcm.popups.PopUpNewUser;
import gow.fcm.popups.PopUpRecoverPassword;
import gow.fcm.sentencias.SentenciasSQLLoginScreen;
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
	AnimatorLogin AL, AL2;
	EditText et_username, et_password;
	SentenciasSQLLoginScreen ssls;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login_screen);
		ssls = new SentenciasSQLLoginScreen();
		et_username = (EditText) findViewById(R.id.editText_username);
		et_password = (EditText) findViewById(R.id.editText_password);
		TextView tv_pass = (TextView) findViewById(R.id.textView_recover_pass);
		Button btn_singIn = (Button) findViewById(R.id.btn_singIn);
		Button btn_singUp = (Button) findViewById(R.id.btn_singUp);
		View myView1 = (View) findViewById(R.id.view_image1);
		View myView2 = (View) findViewById(R.id.view_image2);
		
		AL = new AnimatorLogin(this, getApplicationContext(), myView1, true);
		AL2 = new AnimatorLogin(this, getApplicationContext(), myView2, false);
		
		btn_singIn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Detenemos los hilos que ejecutan la animacion.
				AL.stopThread();
				AL2.stopThread();
				//AL = null;
				//AL2 = null;
				
				// Comprobamos los campos.
				String username = et_username.getText().toString();
				String pass= et_password.getText().toString();
				if((username == null) || (username.equals(""))){
					// Comprobar que el campo usuario tenga contenido escrito.
					Toast.makeText(getApplicationContext(), getString(R.string.login_screen_toast_username_null), Toast.LENGTH_SHORT).show();
				}else if((pass == null) || (pass.equals(""))){
					// Comprobar que el campo contraseña tenga contenido escrito.
					Toast.makeText(getApplicationContext(), getString(R.string.login_screen_toast_password_null), Toast.LENGTH_SHORT).show();
				}else if(ssls.comprobarNombreUsuario(username)==0){
					// Comprobar en la DB si existe el usuario.
					Toast.makeText(getApplicationContext(), "El nombre de usuario no existe", Toast.LENGTH_SHORT).show();
				}else if(ssls.comprobarPasswordUsuario(username, pass)==0){
					// Comprobar en la DB si se corresponde la contraseña con el usuario dado.
					Toast.makeText(getApplicationContext(), "La contraseña no se corresponde con el usuario", Toast.LENGTH_SHORT).show();
				}else{
					// Si todo esta correcto, entrar a la pantalla principal y poner entrenador definido por el usuario
					// en las sharedPrefs. Context menu para preguntar por equipo a usar.
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
		//getMenuInflater().inflate(R.menu.login_screen, menu);
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
