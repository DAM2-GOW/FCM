package gow.fcm.pantallas;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import gow.fcm.footballcoachmanager.R;
import gow.fcm.popups.PopUpNuevoEditarEntrenamiento;
import gow.fcm.popups.PopUpNuevoEditarPartido;
import gow.fcm.sentencias.SentenciasSQLiteVistaDetallada;
import gow.fcm.sharefprefs.DatosFootball;
import gow.fcm.utilidades.ArrayAdapterVistaCalendario;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class DetalleCalendario extends Activity {
	private int opcion, idEquipo;
	private String accion,dia,varAccion="action",varFechaEvento="date_event",varOpcion="opcion",varPosicion="position",posicionActual;
	private ListView lv;
	@SuppressLint("SimpleDateFormat")
	private SimpleDateFormat formato=new SimpleDateFormat("yyyy-MM-dd"); //Formato de conversión a Date
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_detallecalendario);
		Bundle b = getIntent().getExtras();
		accion = b.getString(varAccion);
		dia = b.getString(varFechaEvento);
		opcion = b.getInt(varOpcion);
		idEquipo = DatosFootball.getIdEquipo();
		
		// Obtenemos el elemento 'ListView' de android desde el XML.
		lv = (ListView)findViewById(R.id.listView_eventoscalendario);
		
		registerForContextMenu(lv);
		
		mostrarEventos();
	}
	
	
	protected void onResume(){
		super.onResume();
		
		//Mostramos el entrenamiento con los nuevos datos.
		mostrarEventos();
	}
	//Método de creación de los menús contextuales
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo){
		super.onCreateContextMenu(menu,v,menuInfo);
		MenuInflater inflater=getMenuInflater();
		switch(v.getId()){
		case R.id.listView_eventoscalendario: 
			if(accion.equals("borrar") & opcion==0){
				inflater.inflate(R.menu.entrenamiento_select, menu);
			}else if(accion.equals("borrar") & opcion==1){
				inflater.inflate(R.menu.partido_select, menu);
			}
			break;
		default: super.onCreateContextMenu(menu,v,menuInfo);
			break;
		}
	}

	//Método que indica la acción a realizar según la opción elegida en el menú
	@Override
	public boolean onContextItemSelected(MenuItem item){
		switch(item.getItemId()){
		case R.id.borrarEnt: //Esta opción solo sirve para mostrar los siguientes valores
			return true;
		case R.id.optionNoEnt: //Esta opción no realiza ninguna acción
			return true;
		case R.id.optionSiEnt: borrarEntrenamiento(posicionActual);
		return true;
		case R.id.borrarPart: //Esta opción solo sirve para mostrar los siguientes valores
			return true;
		case R.id.optionNoPart: //Esta opción no realiza ninguna acción
			return true;
		case R.id.optionSiPart: borrarPartido(posicionActual);
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}
	
	private void mostrarEventos(){
		// Obtenemos datos desde la DB.
		String Titulo[];
		String Tipo[];
		String Fecha[];
		String indicativo;
		
		if(opcion==0){
			// Si se recibe orden de tipo entrenamiento.
			indicativo = "Entrenamiento";
			SentenciasSQLiteVistaDetallada.obtenerEntrenamientos(dia, idEquipo);
			Titulo = SentenciasSQLiteVistaDetallada.getDirigido();
			Tipo = SentenciasSQLiteVistaDetallada.getTipo();
			Fecha = SentenciasSQLiteVistaDetallada.getHora();
		}else{
			// Si se recibe orden de tipo partido.
			indicativo = "Partido";
			SentenciasSQLiteVistaDetallada.obtenerPartidos(dia, idEquipo);
			Titulo = SentenciasSQLiteVistaDetallada.getLugar();
			Tipo = SentenciasSQLiteVistaDetallada.getRival();
			Fecha = SentenciasSQLiteVistaDetallada.getHora();
		}
		
		//Si no hay ningún evento, mostramos un mensaje
		if(Titulo.length<1 & Tipo.length<1 & Fecha.length<1){
			this.finish();
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
					int position, long id){
				posicionActual=String.valueOf(position);
				
				if(accion.equals("ver")){
					
					if(opcion==0){
						lanzarObservacion(posicionActual);
					}
					
				}else if(accion.equals("editar")){
					
					if(opcion==0){
						editarEntrenamiento(posicionActual);
					}else{
						editarPartido(posicionActual);
					}
						
				}else if(accion.equals("borrar")){
					lv.showContextMenu();
				}
			}
		});
	}
	
	public void lanzarObservacion(String posicion){
        Intent i = new Intent(this, Obsevaciones.class );
        i.putExtra(varPosicion,posicion);
		i.putExtra(varFechaEvento,dia);
        startActivity(i);
    }
	
	//El siguiente método abre un popup para editar los entrenamientos
	private void editarEntrenamiento(String posicion){
		//Pasamos la fecha seleccionada a milisegundos
		String fecha=dia;
		Date date=null;
		try{
			date=formato.parse(fecha);
		}catch (ParseException e){
			e.printStackTrace();
		}
		
		Intent i=new Intent(this,PopUpNuevoEditarEntrenamiento.class);
		i.putExtra(varAccion,"editar");
		i.putExtra(varPosicion,posicion);
		i.putExtra(varFechaEvento,date.getTime());
		startActivity(i);
	}
	
	//El siguiente método abre un popup para borrar los entrenamiento
	private void borrarEntrenamiento(String posicion){
		SentenciasSQLiteVistaDetallada.borrarEventoEntrenamiento(this,dia,posicion);
		mostrarEventos(); //Se actualiza la lista
	}
	
	//El siguiente método abre un popup para editar un partido
	private void editarPartido(String posicion){
		//Pasamos la fecha seleccionada a milisegundos
		String fecha=dia;
		Date date=null;
		try{
			date=formato.parse(fecha);
		}catch (ParseException e){
			e.printStackTrace();
		}
		
		Intent i=new Intent(this,PopUpNuevoEditarPartido.class);
		i.putExtra(varAccion,"editar");
		i.putExtra(varPosicion,posicion);
		i.putExtra(varFechaEvento,date.getTime());
		startActivity(i);
	}
	
	//El siguiente método borra un partido
	private void borrarPartido(String posicion){
		SentenciasSQLiteVistaDetallada.borrarEventoPartido(this,dia,posicion);
		mostrarEventos(); //Se actualiza la lista
	}
}
	
