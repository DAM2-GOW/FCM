package gow.fcm.pantallas;

import gow.fcm.footballcoachmanager.R;
import gow.fcm.sentencias.SentenciasSQLiteVistaDetallada;
import gow.fcm.sharefprefs.DatosFootball;
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
	private int opcion, idEquipo;
	private String dia;
	SentenciasSQLiteVistaDetallada ssvd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_detallecalendario);
		Bundle b = getIntent().getExtras();
		dia = b.getString("dia");
		opcion = b.getInt("opcion");
		idEquipo = DatosFootball.getIdEquipo();
		
		// Obtenemos el elemento 'ListView' de android desde el XML.
		ListView lv = (ListView)findViewById(R.id.listView_eventoscalendario);

		// Obtenemos datos desde la DB.
		String Titulo[];
		String Tipo[];
		String Fecha[];
		String indicativo;

		if(opcion==0){
			// Si se recibe orden de tipo entrenamiento.
		indicativo = "Entrenamiento";
		Titulo = ssvd.obtenerTituloEntrenamientos(dia, idEquipo);
		Tipo = ssvd.obtenerTipoEntrenamientos(dia, idEquipo);
		Fecha = ssvd.obtenerHoraEntrenamientos(dia, idEquipo);
		}else{
			// Si se recibe orden de tipo partido.
		indicativo = "Partido";
		Titulo = ssvd.obtenerRivalPartidos(dia, idEquipo);
		Tipo = ssvd.obtenerLugarPartidos(dia, idEquipo);
		Fecha = ssvd.obtenerHoraPartidos(dia, idEquipo);
		}

		// Creamos un 'Adapter' para recoger esos datos los cuales son enviados a 
		// una clase java que extiende a 'ArrayAdapter' de android pero personalizada.
		ArrayAdapterVistaCalendario adapter = new ArrayAdapterVistaCalendario(this, 
				Titulo, Tipo, Fecha, indicativo);

        // Se colocan los datos en el 'ListView'.
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?>  parent, View view,
			         int position, long id) {
				//Cuando clicamos en un elemento del listview abre un contextMenu para 
				// seleccionar entre 3 opciones; editar evento, vef observaciones y borrar.
				
				// lanzarObservacion(view);
				
				
			}
		});

	}
	 
	public void lanzarObservacion(View view) {
        Intent i = new Intent(this, Obsevaciones.class );
        //i.putExtra("idObserv", value);
        startActivity(i);
        // Este evento nos manda a la ventana de observaciones
  }  

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
	
