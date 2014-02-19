package gow.fcm.popups;

import gow.fcm.footballcoachmanager.R;
import java.io.File;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
	private String posJug;
	private Button botonEditar;
	private int SELECT_IMAGE = 237487;
	private int TAKE_PICTURE = 829038;
	Cursor cursor;
	ImageView imgPhoto;
	AlertDialog.Builder builder;
	
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
		botonEditar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try{
					  final CharSequence[] items = {"Seleccionar de la galería", "Hacer una foto"};

					  builder = new AlertDialog.Builder(PopUpNuevoJugador.this);
					  builder.setTitle("Seleccionar una foto");
					  builder.setItems(items, new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog, int item) {
					      switch(item){
					       case 0:
					    	   Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
					    	   intent.setType("image/*");
					    	   startActivityForResult(intent, SELECT_IMAGE);   
					    	   break;
					       case 1:
					    	   startActivityForResult(new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE), TAKE_PICTURE);
					    	   break;
					      }
					            
					    }
					  });
					  AlertDialog alert = builder.create();
					  alert.show(); 
					     } catch(Exception e){}
				
			}
		});
		
	}

	@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
      super.onActivityResult(requestCode, resultCode, data);
          
      try{
    	  if (requestCode == SELECT_IMAGE)
    		  if (resultCode == Activity.RESULT_OK) {
    			  Uri selectedImage = data.getData();
    			  //lblPhoto.setText(getPath(selectedImage));
    			  imgPhoto.setImageURI(selectedImage); 
    		  } 
    	  if(requestCode == TAKE_PICTURE)
    		  if(resultCode == Activity.RESULT_OK){
    			  Uri selectedImage = data.getData();
    			  //lblPhoto.setText(getPath(selectedImage));  
    			  imgPhoto.setImageURI(selectedImage); 
    		  }
      	} catch(Exception e){}
    }   

//	private String getPath(Uri uri) {
//		 String[] projection = { android.provider.MediaStore.Images.Media.DATA };
//		 cursor = managedQuery(uri, projection, null, null, null);
//		 int column_index = cursor.getColumnIndexOrThrow(android.provider.MediaStore.Images.Media.DATA);
//		 cursor.moveToFirst();
//		 return cursor.getString(column_index);
//		   }
	
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
