package gow.fcm.pantallas;

import java.io.File;
import gow.fcm.footballcoachmanager.R;
import gow.fcm.sentencias.SentenciasSQLitePerfilEntrenador;
import gow.fcm.sentencias.SentenciasUpdateSQLite;
import gow.fcm.sharefprefs.DatosFootball;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class PantallaPerfilEntrenador extends Activity {
	
	private Button editPhotoEntre, saveNewEntre, saveContra, saveEquipo; //botones del perfil del entrenador
	private EditText nomEntrenador, apellEntrenador, newContra, repNewContra, nomEquipo; //editText del perfil del entrenador, nombre, equipo, contraseña.
	private ImageView imgPhoto; //Imagen o foto del entrenador
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
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_pantalla_perfil_entrenador);
		
		final int id_equipo = DatosFootball.getIdEquipo(); //Cogemos la id del equipo
		final int id_entrenador = DatosFootball.getIdEntrenador(); //Cogemos la id del entrenador
		
		//Relacionamos las variables con la id del layout.
		imgPhoto = (ImageView) findViewById(R.id.photo_entrenador);
		saveNewEntre = (Button) findViewById(R.id.guardarNombreEntrenador);
		nomEntrenador = (EditText) findViewById(R.id.new_nom_Entrenador);
		apellEntrenador = (EditText) findViewById(R.id.new_apellidos_Entrenador);
		newContra = (EditText) findViewById(R.id.new_pass_Entrenador);
		repNewContra = (EditText) findViewById(R.id.rep_new_pass_Entrenador);
		saveContra = (Button) findViewById(R.id.guardarContraEntrenador);
		nomEquipo = (EditText) findViewById(R.id.new_nom_Equipo);
		saveEquipo = (Button) findViewById(R.id.guardarEquipo);
		
		//Obtenemos y mostramos los datos por defecto
		SentenciasSQLitePerfilEntrenador.getDatosPerfilEntrenador(this);
		String nombreEntrenador=SentenciasSQLitePerfilEntrenador.getNombreEntrenador();
		String apellidosEntrenador=SentenciasSQLitePerfilEntrenador.getApellidosEntrenador();
		String nombreEquipo=SentenciasSQLitePerfilEntrenador.getNombreEquipo();
		
		nomEntrenador.setText(nombreEntrenador);
		apellEntrenador.setText(apellidosEntrenador);
		nomEquipo.setText(nombreEquipo);
		
		//Método que sirve para cambiar el nombre y apellidos del entrenador.
		saveNewEntre.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(nomEntrenador.getText().toString().trim().equals("") || nomEntrenador.getText().toString().trim().equals(null) || apellEntrenador.getText().toString().trim().equals("") || apellEntrenador.getText().toString().trim().equals(null)){
					Toast.makeText(getApplicationContext(), "Algun campo está vacío, compruébalo", Toast.LENGTH_SHORT).show();
				}else{
					if(selectedImageUri==null){
						SentenciasUpdateSQLite.actualizarSQLite("Entrenadores", new String[]{"nombre","apellidos"}, new String[]{nomEntrenador.getText().toString().trim(),apellEntrenador.getText().toString().trim()}, "id_entrenador="+id_entrenador+"");
						Toast.makeText(getApplicationContext(), "Cambios guardados", Toast.LENGTH_SHORT).show();
					}else{
						SentenciasUpdateSQLite.actualizarSQLite("Entrenadores", new String[]{"nombre","apellidos","foto"}, new String[]{nomEntrenador.getText().toString().trim(),apellEntrenador.getText().toString().trim(),String.valueOf(selectedImageUri)}, "id_entrenador="+id_entrenador+"");
						Toast.makeText(getApplicationContext(), "Cambios guardados", Toast.LENGTH_SHORT).show();
					}
					
				}
				
			}
		});
		
		//Método que sirve para cambiar la contraseña de login del entrenador.
		saveContra.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Guardar nueva contraseña
				if(newContra.getText().toString().trim().equals("") || newContra.getText().toString().trim().equals(null) || repNewContra.getText().toString().trim().equals("") || repNewContra.getText().toString().trim().equals(null)){
					Toast.makeText(getApplicationContext(), "Algun campo está vacío, compruébalo", Toast.LENGTH_SHORT).show();
				}else{
					if(newContra.getText().toString().trim().equals(repNewContra.getText().toString().trim())){
						SentenciasUpdateSQLite.actualizarSQLite("Entrenadores", new String[]{"clave"}, new String[]{newContra.getText().toString().trim()}, "id_entrenador="+id_entrenador+"");
						Toast.makeText(getApplicationContext(),"Contraseña actualizada",Toast.LENGTH_SHORT).show();
					}else{
						Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
		
		//Metodo que te permite cambiar el nombre del equipo y la ciudad local.
		saveEquipo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(nomEquipo.getText().toString().trim().equals("") || nomEquipo.getText().toString().trim().equals(null)){
					Toast.makeText(getApplicationContext(), "Algun campo está vacío, compruébalo", Toast.LENGTH_SHORT).show();
				}else{
					SentenciasUpdateSQLite.actualizarSQLite("Equipos", new String[]{"nombre"}, new String[]{nomEquipo.getText().toString().trim()}, "id_equipo="+id_equipo+"");
					Toast.makeText(getApplicationContext(), "Nombre de equipo guardado", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		
		//Metodo que saca un menu contextual para que puedas cambiar la foto del entrenador desde la camara de la tablet o de la galeria
		editPhotoEntre = (Button)findViewById(R.id.edit_photo_entrenador);
		registerForContextMenu(editPhotoEntre);
		editPhotoEntre.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				editPhotoEntre.showContextMenu();
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
			case R.id.edit_photo_entrenador: inflater.inflate(R.menu.foto_select, menu);
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
		String fotoEntrenador=SentenciasSQLitePerfilEntrenador.getFotoEntrenador(); //Obtenemos el valor
		
		if(fotoEntrenador==null & rutaImagen==null){ //No hay foto de entrenador
			imgPhoto.setImageResource(R.drawable.no_coach_photo);
		}else if(fotoEntrenador!=null){ //Hay foto de entrenador en la BD
			
			if(rutaImagen!=null){
				imgPhoto.setImageURI(Uri.parse(rutaImagen));
				setRutaImagen();
			}else{
				rutaImagen=fotoEntrenador;
				imgPhoto.setImageURI(Uri.parse(rutaImagen));
				setRutaImagen();
			}
			
		}else if(rutaImagen!=null){ //Hay foto de entrenador desde la cámara de foto o galería
			imgPhoto.setImageURI(Uri.parse(rutaImagen));
			setRutaImagen();
		}
	}
}
