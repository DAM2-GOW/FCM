package gow.fcm.popups;

import java.io.File;

import gow.fcm.footballcoachmanager.R;
import gow.fcm.sentencias.SentenciasInsertSQLite;
import gow.fcm.sentencias.SentenciasSQLLoginScreen;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class PopUpNewUser extends Activity {
	ImageView new_user_pic_preview;
	SentenciasSQLLoginScreen ssls;
	Spinner sp_security_question;
	Button btn_edit_photo, btn_save_new_user;
	EditText et_name, et_surName, et_user, et_password, et_password_repeat, et_security_answer, et_team_name;
	private final int camara=1,galeria=2,recortar=3; //Variable usadas para tomar la foto o imagen del entrenador o recortarla
	private Uri selectedImageUri; //Imagen seleccionada desde la cámara
	private File dirActual=Environment.getExternalStorageDirectory(); //Directorio donde esta la carpeta de las imágenes
	private String dirRecortes="image/*",rutaImagen; //Directorio donde se encuentran las imágenes recortadas
	
	//Reseteamos el valor de rutaImagen
	private void setRutaImagen(){
		rutaImagen=null; //Reseta a null el valor
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		showAsPopup(this);
		setContentView(R.layout.popup_new_user);
		ssls = new SentenciasSQLLoginScreen();
		
		new_user_pic_preview = (ImageView) findViewById(R.id.new_user_pic_preview);
		btn_edit_photo = (Button) findViewById(R.id.btn_edit_photo_entrenador);
		et_name = (EditText) findViewById(R.id.nom_Entrenador);
		et_surName = (EditText) findViewById(R.id.apellidos_entrenador);
		et_user = (EditText) findViewById(R.id.et_new_user);
		et_password = (EditText) findViewById(R.id.et_pass_user);
		et_password_repeat = (EditText) findViewById(R.id.rep_pass_user);
		sp_security_question = (Spinner) findViewById(R.id.sp_security_question);
		ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.PreguntasSeguridad));
		sp_security_question.setAdapter(spinnerArrayAdapter);
		et_security_answer = (EditText) findViewById(R.id.et_secret_answer);
		et_team_name = (EditText) findViewById(R.id.editText_team_name);
		btn_save_new_user = (Button) findViewById(R.id.btn_save_new_user);
		
		//Metodo que se encarga de cargar el menu contextual cuando selecciones el boton de editar foto.
		registerForContextMenu(btn_edit_photo);
		btn_edit_photo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				btn_edit_photo.showContextMenu();
			}
		});
		
		btn_save_new_user.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Esta seccion es llamada cuando se pulsa el boton de guardar nuevo usuario.
				String name = et_name.getText().toString().trim();
				String surName = et_surName.getText().toString().trim();
				String user = et_user.getText().toString().trim();
				String password = et_password.getText().toString().trim();
				String passwordRepeat = et_password_repeat.getText().toString().trim();
				String securityAnswer = et_security_answer.getText().toString().trim();
				String teamName = et_team_name.getText().toString().trim();
				
				if((name == null) || (name.equals(""))){
					// Se comprueba que el nombre tenga contenido.
					Toast.makeText(getApplicationContext(), getString(R.string.login_screen_toast_new_username_null), Toast.LENGTH_SHORT).show();
				}else if((surName == null) || (surName.equals(""))){
					// Se comprueba que los apellidos tengan contenido.
					Toast.makeText(getApplicationContext(), getString(R.string.login_screen_toast_surname_null), Toast.LENGTH_SHORT).show();
				}else if((user == null) || (user.equals(""))){
					// Se comprueba que el usuario tenga contenido.
					Toast.makeText(getApplicationContext(), getString(R.string.login_screen_toast_username_null), Toast.LENGTH_SHORT).show();
				}else if(ssls.comprobarNombreUsuario(user)==1){
					// Se comprueba que el usuario no extista actualmente.
					Toast.makeText(getApplicationContext(), getString(R.string.login_screen_toast_username_in_use), Toast.LENGTH_SHORT).show();
				}else if((password == null) || (password.equals(""))){
					// Se comprueba que la contraseña tenga contenido.
					Toast.makeText(getApplicationContext(), getString(R.string.login_screen_toast_new_password_null), Toast.LENGTH_SHORT).show();
				}else if((passwordRepeat == null) || (passwordRepeat.equals(""))){
					// Se comprueba que la repeticion de contraseña tenga contenido.
					Toast.makeText(getApplicationContext(), getString(R.string.login_screen_toast_password_repeat_null), Toast.LENGTH_SHORT).show();
				}else if(!password.equals(passwordRepeat)){
					// Se comprueba que la repeticion de contraseña sea identica a la
					// contraseña escrita.
					Toast.makeText(getApplicationContext(), getString(R.string.login_screen_toast_passwords_verification), Toast.LENGTH_SHORT).show();
				}else if((securityAnswer == null) || (securityAnswer.equals(""))){
					// Se comprueba que la respuesta de seguridad tenga contenido.
					Toast.makeText(getApplicationContext(), getString(R.string.login_screen_toast_security_answer_null), Toast.LENGTH_SHORT).show();
				}else if((teamName==null) || (teamName.equals(""))){
					// Se comprueba que el nombre de equipo tenga contenido.
					Toast.makeText(getApplicationContext(), getString(R.string.popup_new_user_toast_team_name_null), Toast.LENGTH_SHORT).show();
				}else{
					// Se aceptan los campos y se almacena en la DB el nuevo usuario.
					SentenciasInsertSQLite.insertarSQLite("Equipos", new String[] {"nombre"}, new String[] {teamName});
					int equipoID = ssls.obtenerIDEquipoPorNombre(teamName);
					int preguntaSeguridad = sp_security_question.getSelectedItemPosition();
					if(selectedImageUri==null){
						SentenciasInsertSQLite.insertarSQLite("Entrenadores", new String[] {"id_equipo","nombre","apellidos","usuario","clave","pregunta_seguridad","respuesta_seguridad"}, new String[] {String.valueOf(equipoID),name,surName,user,password,String.valueOf(preguntaSeguridad),securityAnswer});
					}else{
						SentenciasInsertSQLite.insertarSQLite("Entrenadores", new String[] {"id_equipo","nombre","apellidos","foto","usuario","clave","pregunta_seguridad","respuesta_seguridad"}, new String[] {String.valueOf(equipoID),name,surName,String.valueOf(selectedImageUri),user,password,String.valueOf(preguntaSeguridad),securityAnswer});
					}
					Intent i = new Intent();
					setResult(RESULT_OK, i);
					PopUpNewUser.this.finish();
				}
				
			}
		});
		
		getFotoEntrenador(); //Muestra la imágen del entrenador si existe
	}

	@Override
	protected void onActivityResult(int codigo,int resultado,Intent datos){
		//Comprobamos si se ha producido algún error
		if(resultado!=RESULT_OK){
			return;
		}
		
		//Acciones a realizar según la opción seleccionada previamente
		switch(codigo){
			case camara: Uri ruta=selectedImageUri; //Obtenemos la ruta
				
				recortarFotoCamara(ruta); //Recortamos la imagen
				break;
			case galeria: Uri ruta2=selectedImageUri; //Obtenemos la ruta
				rutaImagen=String.valueOf(ruta2); //Convertimos a string la ruta
				
				getFotoEntrenador();
				break;
			case recortar: Uri ruta3=selectedImageUri; //Obtenemos la ruta
				rutaImagen=String.valueOf(ruta3); //Convertimos a string la ruta
				
				getFotoEntrenador();
				break;
			default:
				break;
		}
	}
	
	//Método de creación de los menús contextuales
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo){
		super.onCreateContextMenu(menu,v,menuInfo);
		MenuInflater inflater=getMenuInflater();
		switch(v.getId()){
		case R.id.btn_edit_photo_entrenador: inflater.inflate(R.menu.foto_select, menu);
		break;
		default:
			break;
		}
	}
	
	//Método que indica la acción a realizar según la opción elegida en el menú
	@Override
	public boolean onContextItemSelected(MenuItem item){
		switch(item.getItemId()){
		case R.id.camaraFotos: cargarCamaraFotos();
		return true;
		case R.id.galeriaFotos: cargarGaleriaFotos();
		return true;
		default:
			return super.onContextItemSelected(item);
		}
	}
	
	//Método que carga la cámara de fotos
	private void cargarCamaraFotos(){
		String nombreFoto=System.currentTimeMillis()+".png";
		File directorioCompleto=new File(dirActual,nombreFoto);
		selectedImageUri=Uri.fromFile(directorioCompleto);
		Intent i=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		i.putExtra(MediaStore.EXTRA_OUTPUT,selectedImageUri);
		startActivityForResult(i,camara);
	}
	
	//Método que carga la galería de fotos
	private void cargarGaleriaFotos(){
		//Abrimos la galería
		Intent i=new Intent();
		i.setAction(Intent.ACTION_GET_CONTENT);
		i.setType(dirRecortes);
		i.putExtra("crop","true");
		i.putExtra("aspectX",1);
		i.putExtra("aspectY",1);
		i.putExtra("outputX",100);
		i.putExtra("outputY",102);
		i.putExtra("return-data",false);
		
		//Pasamos los parámetros para guardar la imagen
		String nombreFoto=System.currentTimeMillis()+".png";
		File directorioCompleto=new File(dirActual,nombreFoto);
		selectedImageUri=Uri.fromFile(directorioCompleto);
		
		//La guaradamos
		i.putExtra(MediaStore.EXTRA_OUTPUT,selectedImageUri);
		startActivityForResult(Intent.createChooser(i,"Completar acción"),galeria);
	}
	
	//Método que recorta la imagen
	private void recortarFotoCamara(Uri archivo){
		//Recortamos la imagen
		Intent i=new Intent("com.android.camera.action.CROP");
		i.setDataAndType(archivo,dirRecortes);
		i.putExtra("crop","true");
		i.putExtra("aspectX",1);
		i.putExtra("aspectY",1);
		i.putExtra("outputX",100);
		i.putExtra("outputY",102);
		i.putExtra("return-data",false);
		
		//Pasamos los parámetros para guardarla
		selectedImageUri=archivo;
		
		//La guaradamos
		i.putExtra(MediaStore.EXTRA_OUTPUT,selectedImageUri);
		startActivityForResult(i,recortar);
	}
	
	public void getFotoEntrenador(){
		//Mostramos la foto del entrenador si la hay o no
		if(rutaImagen==null){
			new_user_pic_preview.setImageResource(R.drawable.no_coach_photo);
		}else{
			//Agregamos el valor o contenido a los elementos
			new_user_pic_preview.setImageURI(Uri.parse(rutaImagen));
			setRutaImagen();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.pop_up_new_user, menu);
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
		params.height = 700; // Adaptamos el tamaño en altura segun componentes del XML.
		params.width = 1200; // Fijamos el tamaño en anchura.
		params.alpha = 1.0f; // Podemos otorgarle transparencia al Pop-Up.
		params.dimAmount = 0.5f; // Fijamos el nivel de oscuridad para el activity de fondo.
		activity.getWindow().setAttributes(params); // Aplicamos los valores establecidos al Activity.
	}
}
