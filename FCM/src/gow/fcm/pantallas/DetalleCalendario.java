package gow.fcm.pantallas;

import gow.fcm.footballcoachmanager.R;
import gow.fcm.utilidades.ArrayAdapterVistaCalendario;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

public class DetalleCalendario extends Activity {
	private boolean selected;
	private int lastPosition;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_detallecalendario);
		
		// Inicializamos la variable de selecciona de elementos.
		selected = false;
		
		// Obtenemos el elemento 'ListView' de android desde el XML.
		ListView lv = (ListView)findViewById(R.id.listView_eventoscalendario);
		
		// Forzamos los datos de entrada que seran mostrados en la lista.
		String[] data_name = {"Entrenamiento de piernas","Entrenamiento de brazo","Entrenamiento importante del primer equipo"};
		String[] data_type = {"Resistencia","Agilidad","Fuerza"};
		String[] data_hora = {"8:30", "9:30", "10:30"};
		String[] data_cosa = {"Entrenamiento", "Entrenamiento", "Entrenamiento"};
		
		// Creamos un 'Adapter' para recoger esos datos los cuales son enviados a 
		// una clase java que extiende a 'ArrayAdapter' de android pero personalizada.
		final ArrayAdapterVistaCalendario adapter = new ArrayAdapterVistaCalendario(this, 
                data_name, data_type, data_hora, data_cosa);
        
        // Se colocan los datos en el 'ListView'.
        lv.setAdapter(adapter);
        
        
        lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?>  parent, View view,
			         int position, long id) {
				lanzarObservacion(view);
				//Cuando clicamos en un elemento del listview nos manda a la ventana de observacones
			}
		});
        lv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// lanzarEdicion(view);
				return false;
				//cuando mantenemos pulsado un elemento nos manda a la ventana para editar el entrenamiento o partido.
			}
		});
	}
	 
	public void lanzarObservacion(View view) {
        Intent i = new Intent(this, Obsevaciones.class );
        //i.putExtra("idObservaciones", value)
        startActivity(i);
        // Este evento nos manda a la ventana de observaciones
  }  
	/*public void lanzarEdicion(View view) {
		Intent i = new Intent(this, ventanasenent.class);
		i.putExtra("idObservaciones", value)
		startActivity(i);
	}
	*/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
	//	getMenuInflater().inflate(R.menu.main, menu);
		return true;
		// Este evento nos manda a la ventana de edicion
	}

}
	
