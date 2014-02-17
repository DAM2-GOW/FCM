package gow.fcm.popups;

import java.io.File;

import gow.fcm.footballcoachmanager.R;
import gow.fcm.footballcoachmanager.R.layout;
import gow.fcm.footballcoachmanager.R.menu;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class PopUpNuevoJugador extends Activity {

	private Spinner tipoJugador, posicionJugador;
	private String posJug;
	private ImageView fotoEntrenador; //Imagen o foto del entrenador
	private final int camara=1,galeria=2,recortar=3; //Variable usadas para tomar la foto o imagen del entrenador o recortarla
	private Uri selectedImageUri; //Imagen seleccionada desde la cámara
	private File dirActual=Environment.getExternalStorageDirectory(); //Directorio donde esta la carpeta de las imágenes
	private String dirRecortes="image/*"; //Directorio donde se encuentran las imágenes recortadas
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		showAsPopup(this); //Llama al metodo que hace que se muestre como PopUp.
		setContentView(R.layout.activity_popup_nuevo_jugador);
		
		tipoJugador = (Spinner)findViewById(R.id.spinnerTipoJugador);
		posicionJugador = (Spinner)findViewById(R.id.spinnerPosicionJugador);
		
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
	}
	
	//Metodo que hace que puedas seleccionar una foto desde la galeria o hacerla en el instante.
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pop_up_nuevo_jugador, menu);
		return true;
	}
	
	//Metodo que hace que el activity salga como PopUp.
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
