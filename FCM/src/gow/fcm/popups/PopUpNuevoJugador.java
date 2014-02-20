package gow.fcm.popups;

import gow.fcm.footballcoachmanager.R;
import gow.fcm.sentencias.SentenciasSQLitePrincipal;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

public class PopUpNuevoJugador extends Activity {

	private Spinner tipoJugador, posicionJugador;
	private String posJug,rutaImagen;
	private Button botonEditar, guardar;
	
	private ImageView imgPhoto; //Imagen o foto del entrenador
	private final int camara=1,galeria=2,recortar=3; //Variable usadas para tomar la foto o imagen del entrenador o recortarla
	private Uri selectedImageUri; //Imagen seleccionada desde la c�mara
	private File dirActual=Environment.getExternalStorageDirectory(); //Directorio donde esta la carpeta de las im�genes
	private String dirRecortes="image/*"; //Directorio donde se encuentran las im�genes recortadas
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		showAsPopup(this); //Llama al metodo que hace que se muestre como PopUp.
		setContentView(R.layout.activity_popup_nuevo_jugador);
		
		tipoJugador = (Spinner)findViewById(R.id.spinnerTipoJugador);
		posicionJugador = (Spinner)findViewById(R.id.spinnerPosicionJugador);
		imgPhoto = (ImageView)findViewById(R.id.foto_Jugador);
		
		//Llamamos a los arrays que contienen los nombre del tipo de jugador y su posicion.
		final ArrayAdapter<CharSequence> adaptadorTipo = ArrayAdapter.createFromResource(this, R.array.TipoJugador, android.R.layout.simple_spinner_item);
		tipoJugador.setAdapter(adaptadorTipo);
		final ArrayAdapter<CharSequence> adaptador2 = ArrayAdapter.createFromResource(this, R.array.PosicionAtaque, android.R.layout.simple_spinner_item);
		
		final ArrayAdapter<CharSequence> adaptador3 = ArrayAdapter.createFromResource(this, R.array.PosicionDefensa, android.R.layout.simple_spinner_item);
		
		final ArrayAdapter<CharSequence> adaptador4 = ArrayAdapter.createFromResource(this, R.array.PosicionEE, android.R.layout.simple_spinner_item);
		
		//Dependiendo del tipo que jugador que selecciones tendras diferentes tipos de posicion.
		tipoJugador.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
				if(position == 0){ //Posicion de ataque
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
					if(position == 1){ //Posicion de defensa
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
						if(position == 2){ //Posicion de EE
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
		
		botonEditar = (Button)findViewById(R.id.editarFotoJugador);
		registerForContextMenu(botonEditar);
		botonEditar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				botonEditar.showContextMenu();
			}
		});
		
	}
	
	@Override
	protected void onActivityResult(int codigo,int resultado,Intent datos){
		//Comprobamos si se ha producido alg�n error
		if(resultado!=RESULT_OK){
			return;
		}
		
		//Acciones a realizar seg�n la opci�n seleccionada previamente
		switch(codigo){
			case camara: Uri ruta=selectedImageUri; //Obtenemos la ruta
				
				recortarFotoCamara(ruta); //Recortamos la imagen
				break;
			case galeria: Uri ruta2=selectedImageUri; //Obtenemos la ruta
				rutaImagen=String.valueOf(ruta2); //Convertimos a string la ruta
				
				setFotoEntrenador();
				break;
			case recortar: Uri ruta3=selectedImageUri; //Obtenemos la ruta
				rutaImagen=String.valueOf(ruta3); //Convertimos a string la ruta
				
				setFotoEntrenador();
				break;
			default:
				break;
		}
	}
	
	//M�todo de creaci�n de los men�s contextuales
	public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo){
		super.onCreateContextMenu(menu,v,menuInfo);
		MenuInflater inflater=getMenuInflater();
		switch(v.getId()){
			case R.id.editarFotoJugador: inflater.inflate(R.menu.foto_select, menu);
				break;
			default:
				break;
		}
	}
	
	//M�todo que indica la acci�n a realizar seg�n la opci�n elegida en el men�
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
	
	//M�todo que carga la c�mara de fotos
	private void cargarCamaraFotos(){
		String nombreFoto=System.currentTimeMillis()+".png";
		File directorioCompleto=new File(dirActual,nombreFoto);
		selectedImageUri=Uri.fromFile(directorioCompleto);
		Intent i=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		i.putExtra(MediaStore.EXTRA_OUTPUT,selectedImageUri);
		startActivityForResult(i,camara);
	}
	
	//M�todo que carga la galer�a de fotos
	private void cargarGaleriaFotos(){
		//Abrimos la galer�a
		Intent i=new Intent();
		i.setAction(Intent.ACTION_GET_CONTENT);
		i.setType(dirRecortes);
		i.putExtra("crop","true");
		i.putExtra("aspectX",1);
		i.putExtra("aspectY",1);
		i.putExtra("outputX",100);
		i.putExtra("outputY",102);
		i.putExtra("return-data",false);
		
		//Pasamos los par�metros para guardar la imagen
		String nombreFoto=System.currentTimeMillis()+".png";
		File directorioCompleto=new File(dirActual,nombreFoto);
		selectedImageUri=Uri.fromFile(directorioCompleto);
		
		//La guaradamos
		i.putExtra(MediaStore.EXTRA_OUTPUT,selectedImageUri);
		startActivityForResult(Intent.createChooser(i,"Completar acci�n"),galeria);
	}
	
	//M�todo que recorta la imagen
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
		
		//Pasamos los par�metros para guardarla
		selectedImageUri=archivo;
		
		//La guaradamos
		i.putExtra(MediaStore.EXTRA_OUTPUT,selectedImageUri);
		startActivityForResult(i,recortar);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pop_up_nuevo_jugador, menu);
		return true;
	}
	
	public void setFotoEntrenador(){
		//Mostramos la foto del entrenador si la hay o no
		if(rutaImagen==null){
			imgPhoto.setImageResource(R.drawable.no_coach_photo);
		}else{
			//Agregamos el valor o contenido a los elementos
			imgPhoto.setImageURI(Uri.parse(rutaImagen));
			
			rutaImagen=null; //Reseta a null el valor
		}
	}
	
	//Metodo que hace que el activity salga como PopUp.
	public static void showAsPopup(Activity activity) {
		activity.requestWindowFeature(Window.FEATURE_ACTION_BAR); // Esta caracteristica habilita la barra (menu) en el Pop-Up.
		activity.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_DIM_BEHIND,
				WindowManager.LayoutParams.FLAG_DIM_BEHIND); // Marcamos activity para habilitar opciones diversas.
		LayoutParams params = activity.getWindow().getAttributes(); // Obtenemos objeto de configuracion del Activity.
		params.height = LayoutParams.WRAP_CONTENT; // Adaptamos el tama�o en altura segun componentes del XML.
		params.width = 800; // Fijamos el tama�o en anchura.
		params.alpha = 1.0f; // Podemos otorgarle transparencia al Pop-Up.
		params.dimAmount = 0.5f; // Fijamos el nivel de oscuridad para el activity de fondo.
		activity.getWindow().setAttributes(
				(android.view.WindowManager.LayoutParams) params); // Aplicamos los valores establecidos al Activity.
	}

}
