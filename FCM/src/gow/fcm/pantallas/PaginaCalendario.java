package gow.fcm.pantallas;

import gow.fcm.basedatos.ConexionSQLite;
import gow.fcm.popups.PopUpNuevoEntrenamiento;
import gow.fcm.popups.PopUpNuevoPartido;
import gow.fcm.sentencias.SentenciasSQLiteCalendario;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import gow.fcm.footballcoachmanager.R;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnTouchListener;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.CalendarView.OnDateChangeListener;

public class PaginaCalendario extends Activity{
	
	//Herramientas a usar por los eventos
	private ImageView agregarEventoEntrenamiento,verEstadisticasEntrenamiento,editarEventoEntrenamiento,borrarEventoEntrenamiento;
	private ImageView agregarEventoPartido,verEstadisticasPartido,editarEventoPartido,borrarEventoPartido;
	//Botones a usar por los eventos
	private View botonAgregarEntrenamiento,botonVerEntrenamiento,botonEditarEntrenamiento,botonBorrarEntrenamiento;
	private View botonAgregarPartido,botonVerPartido,botonEditarPartido,botonBorrarPartido;
	private CalendarView calendario; //Calendario de los eventos
	@SuppressLint("SimpleDateFormat")
	private SimpleDateFormat formato=new SimpleDateFormat("yyyy-MM-dd"); //Formato de conversión a Date
	private String fechaActual,fechaSeleccionada; //Variables para las fechas en la base de datos
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); //No mostramos la barra de la cabecera con el nombre de la aplicación
		setContentView(R.layout.pagina_calendario);
		
		//Se crea la base de datos si no existe o se actualiza si fuera necesario
		ConexionSQLite.getCrearSQLite(this);
		
		//Método que mostrará los eventos del calendario
		mostrarEventos();
	}

	//Método de creación de los menús contextuales
	public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo){
		super.onCreateContextMenu(menu,v,menuInfo);
		MenuInflater inflater=getMenuInflater();
		switch(v.getId()){
			case R.id.borrar_evento_entrenamiento: inflater.inflate(R.menu.entrenamiento_select, menu);
				break;
			case R.id.borrar_evento_partido: inflater.inflate(R.menu.partido_select, menu);
				break;
			default:
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
			case R.id.optionSiEnt: borrarEntrenamiento();
				return true;
			case R.id.borrarPart: //Esta opción solo sirve para mostrar los siguientes valores
				return true;
			case R.id.optionNoPart: //Esta opción no realiza ninguna acción
				return true;
			case R.id.optionSiPart: borrarPartido();
				return true;
			default:
				return super.onContextItemSelected(item);
		}
	}
	
	//Método que devuelve la fecha actual
	private String getFechaActual(){
		//Obtenemos la fecha actual
		Date dates=new Date();
		fechaActual=formato.format(dates);
		return fechaActual;
	}
	
	//Método que devuelve la fecha seleccionada en el calendario
	private String getFechaSeleccionada(){
		return fechaSeleccionada;
	}
	
	//Método para mostrar los eventos del calendario
	@SuppressLint({ "NewApi", "ResourceAsColor" })
	private void mostrarEventos(){
		calendario=(CalendarView) findViewById(R.id.calendario_eventos);
		calendario.setFirstDayOfWeek(Calendar.MONDAY); //Hacemos que el primer día de la semana sea Lunes
		calendario.setShowWeekNumber(false); //Ocultamos el número de la semana
		
		//Obtenemos la resolución de pantalla y cambiamos la altura del calendario si fuera necesario
		DisplayMetrics sizeScreen=new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(sizeScreen);
		int altoPantalla=sizeScreen.heightPixels;
		
		if(altoPantalla<800){ //Comprueba que la pantalla sea menor a 800p de altura
			//800 es el tamaño de la pantalla de desarrollo con margenes
			int altoCalendario=800-altoPantalla; //Obtenemos la diferencia del tamaño de la pantalla actual
			altoCalendario=640-altoCalendario; //Obtenemos la nueva altura del calendario
			
			//Hacemos que la altura cuadre con la resolución de nuestro dispositivo
			LinearLayout.LayoutParams propiedades=(LinearLayout.LayoutParams) calendario.getLayoutParams();
			propiedades.height=altoCalendario;
			calendario.setLayoutParams(propiedades);
		}
		
		//Declaramos la variables que harán de botones
		botonAgregarEntrenamiento=(View) findViewById(R.id.boton_agregar_entrenamiento);
		botonVerEntrenamiento=(View) findViewById(R.id.boton_ver_entrenamiento);
		botonEditarEntrenamiento=(View) findViewById(R.id.boton_editar_entrenamiento);
		botonBorrarEntrenamiento=(View) findViewById(R.id.boton_borrar_entrenamiento);
		botonAgregarPartido=(View) findViewById(R.id.boton_agregar_partido);
		botonVerPartido=(View) findViewById(R.id.boton_ver_partido);
		botonEditarPartido=(View) findViewById(R.id.boton_editar_partido);
		botonBorrarPartido=(View) findViewById(R.id.boton_borrar_partido);
		
		//Declaramos las imagenes que haran a función de herramientas para los eventos
		agregarEventoEntrenamiento=(ImageView) findViewById(R.id.agregar_evento_entrenamiento);
		verEstadisticasEntrenamiento=(ImageView) findViewById(R.id.ver_estadisticas_entrenamiento);
		editarEventoEntrenamiento=(ImageView) findViewById(R.id.editar_evento_entrenamiento);
		borrarEventoEntrenamiento=(ImageView) findViewById(R.id.borrar_evento_entrenamiento);
		agregarEventoPartido=(ImageView) findViewById(R.id.agregar_evento_partido);
		verEstadisticasPartido=(ImageView) findViewById(R.id.ver_estadisticas_partido);
		editarEventoPartido=(ImageView) findViewById(R.id.editar_evento_partido);
		borrarEventoPartido=(ImageView) findViewById(R.id.borrar_evento_partido);
		
		//Registramos los controles de borrar como menús contextuales
		registerForContextMenu(borrarEventoEntrenamiento);
		registerForContextMenu(borrarEventoPartido);
		
		fechaActual=getFechaActual(); //Fecha actual
		fechaSeleccionada=fechaActual; //Igualamos la fecha actual a la fecha seleccionada
		
		accionesMostrarEventos(fechaActual); //Imagenes desactivadas y activadas
		
		accionesHerramientasEventos(); //Acciones de las imagenes
		
		calendario.setOnDateChangeListener(new OnDateChangeListener(){
			@Override
			public void onSelectedDayChange(CalendarView arg, int year, int mes, int dia){
				mes=mes+1; //Le debemos sumar 1 al mes porque va solo del 0 al 11
				fechaSeleccionada=year+"-"+mes+"-"+dia;
				accionesMostrarEventos(fechaSeleccionada);
			}
		});
	}
	
	@SuppressLint("SimpleDateFormat")
	private void accionesMostrarEventos(String fecha){
		SentenciasSQLiteCalendario.getDatosEntrenamientos(this,fecha);
		SentenciasSQLiteCalendario.getDatosPartidos(this,fecha);
		
		//Sentencias para comprobar los eventos
		String tipoEntrenamiento=SentenciasSQLiteCalendario.getTipoEntrenamiento();
		String lugarPartido=SentenciasSQLiteCalendario.getLugarPartido();
		
		//Sentencias para comprobar la fecha de los eventos
		Date fechaEntrenamiento=SentenciasSQLiteCalendario.getFechaEntrenamiento();
		Date fechaPartido=SentenciasSQLiteCalendario.getFechaPartido();
		
		//Sentencias a mostrar datos en los TextView del Entrenamiento
		String dirigido=SentenciasSQLiteCalendario.getDirigidoEntrenamiento();
		
		//Sentencias a mostrar datos en los TextView del Partido
		String rival=SentenciasSQLiteCalendario.getRivalPartido();
		
		//Variable local como Date para convertirla a fecha
		Date dates=null;
		Date dates2=null;
		try{
			dates=formato.parse(getFechaActual());
			dates2=formato.parse(getFechaSeleccionada());
		}catch(ParseException e){
			e.printStackTrace();
		}
		
		//TextView a modificar según el tipo de evento
		TextView eventoEntrenamiento=(TextView) findViewById(R.id.evento_entrenamiento);
		TextView entrenamientoEvento=(TextView) findViewById(R.id.entrenamiento_evento);
		TextView eventoPartido=(TextView) findViewById(R.id.evento_partido);
		TextView partidoEvento=(TextView) findViewById(R.id.partido_evento);
		
		//Desactivamos o activamos las imagenes comprobando las condiciones
		if(tipoEntrenamiento==null & lugarPartido==null){
			
			if(dates.after(dates2)){
				fechaActualImagenes("Superior",1);
			}else{
				fechaActualImagenes("Inferior",1);
			}
			
			eventoEntrenamiento.setText(R.string.no_training);
			entrenamientoEvento.setText("");
			eventoPartido.setText(R.string.no_match);
			partidoEvento.setText("");
				
		}else if(tipoEntrenamiento!=null & lugarPartido==null){
			
			if(dates.after(fechaEntrenamiento)){
				fechaActualImagenes("Superior",2);
			}else{
				fechaActualImagenes("Inferior",2);
			}
			
			eventoPartido.setText(R.string.no_match);
			partidoEvento.setText("");
			
			if(tipoEntrenamiento.equals("Resistencia")){
				eventoEntrenamiento.setText(R.string.event_training1);
				entrenamientoEvento.setText(" "+dirigido);
			}else if(tipoEntrenamiento.equals("Fuerza")){
				eventoEntrenamiento.setText(R.string.event_training2);
				entrenamientoEvento.setText(" "+dirigido);
			}else if(tipoEntrenamiento.equals("Agilidad")){
				eventoEntrenamiento.setText(R.string.event_training3);
				entrenamientoEvento.setText(" "+dirigido);
			}
			
			SentenciasSQLiteCalendario.setTipoEntrenamiento(); //Reseta a null el valor
			
		}else if(tipoEntrenamiento==null & lugarPartido!=null){
			
			if(dates.after(fechaPartido)){
				fechaActualImagenes("Superior",3);
			}else{
				fechaActualImagenes("Inferior",3);
			}
			
			eventoEntrenamiento.setText(R.string.no_training);
			entrenamientoEvento.setText("");
			
			if(lugarPartido.equals("Local")){
				eventoPartido.setText(R.string.event_match1);
				partidoEvento.setText(" "+rival);
			}else if(lugarPartido.equals("Visitante")){
				eventoPartido.setText(R.string.event_match2);
				partidoEvento.setText(" "+rival);
			}
			
			SentenciasSQLiteCalendario.setLugarPartido(); //Reseta a null el valor
			
		}else if(tipoEntrenamiento!=null & lugarPartido!=null){
			
			if(dates.after(fechaEntrenamiento) & dates.after(fechaPartido)){
				fechaActualImagenes("Superior",4);
			}else{
				fechaActualImagenes("Inferior",4);
			}
			
			if(tipoEntrenamiento.equals("Resistencia")){
				eventoEntrenamiento.setText(R.string.event_training1);
				entrenamientoEvento.setText(" "+dirigido);
			}else if(tipoEntrenamiento.equals("Fuerza")){
				eventoEntrenamiento.setText(R.string.event_training2);
				entrenamientoEvento.setText(" "+dirigido);
			}else if(tipoEntrenamiento.equals("Agilidad")){
				eventoEntrenamiento.setText(R.string.event_training3);
				entrenamientoEvento.setText(" "+dirigido);
			}
			if(lugarPartido.equals("Local")){
				eventoPartido.setText(R.string.event_match1);
				partidoEvento.setText(" "+rival);
			}else if(lugarPartido.equals("Visitante")){
				eventoPartido.setText(R.string.event_match2);
				partidoEvento.setText(" "+rival);
			}
			
			SentenciasSQLiteCalendario.setTipoEntrenamiento(); //Reseta a null el valor
			SentenciasSQLiteCalendario.setLugarPartido(); //Reseta a null el valor
		}
	}
	
	private void opcionAgregarEntrenamiento(String accion){
		//Desactivamos o activamos las imagenes a usar y le añadimos o quitamos visibilidad
		if(accion=="desactivar"){
			botonAgregarEntrenamiento.setEnabled(false);
			agregarEventoEntrenamiento.setAlpha(0.25F);
		}else if(accion=="activar"){
			botonAgregarEntrenamiento.setEnabled(true);
			agregarEventoEntrenamiento.setAlpha(1F);
		}
	}
	
	private void opcionVerEntrenamiento(String accion){
		//Desactivamos o activamos las imagenes a usar y le añadimos o quitamos visibilidad
		if(accion=="desactivar"){
			botonVerEntrenamiento.setEnabled(false);
			verEstadisticasEntrenamiento.setAlpha(0.25F);
		}else if(accion=="activar"){
			botonVerEntrenamiento.setEnabled(true);
			verEstadisticasEntrenamiento.setAlpha(1F);
		}
	}
	
	private void opcionEditarEntrenamiento(String accion){
		//Desactivamos o activamos las imagenes a usar y le añadimos o quitamos visibilidad
		if(accion=="desactivar"){
			botonEditarEntrenamiento.setEnabled(false);
			editarEventoEntrenamiento.setAlpha(0.25F);
		}else if(accion=="activar"){
			botonEditarEntrenamiento.setEnabled(true);
			editarEventoEntrenamiento.setAlpha(1F);
		}
	}
	
	private void opcionBorrarEntrenamiento(String accion){
		//Desactivamos o activamos las imagenes a usar y le añadimos o quitamos visibilidad
		if(accion=="desactivar"){
			botonBorrarEntrenamiento.setEnabled(false);
			borrarEventoEntrenamiento.setAlpha(0.25F);
		}else if(accion=="activar"){
			botonBorrarEntrenamiento.setEnabled(true);
			borrarEventoEntrenamiento.setAlpha(1F);
		}
	}
	
	private void opcionAgregarPartido(String accion){
		//Desactivamos o activamos las imagenes a usar y le añadimos o quitamos visibilidad
		if(accion=="desactivar"){
			botonAgregarPartido.setEnabled(false);
			agregarEventoPartido.setAlpha(0.25F);
		}else if(accion=="activar"){
			botonAgregarPartido.setEnabled(true);
			agregarEventoPartido.setAlpha(1F);
		}
	}
	
	private void opcionVerPartido(String accion){
		//Desactivamos o activamos las imagenes a usar y le añadimos o quitamos visibilidad
		if(accion=="desactivar"){
			botonVerPartido.setEnabled(false);
			verEstadisticasPartido.setAlpha(0.25F);
		}else if(accion=="activar"){
			botonVerPartido.setEnabled(true);
			verEstadisticasPartido.setAlpha(1F);
		}
	}
	
	private void opcionEditarPartido(String accion){
		//Desactivamos o activamos las imagenes a usar y le añadimos o quitamos visibilidad
		if(accion=="desactivar"){
			botonEditarPartido.setEnabled(false);
			editarEventoPartido.setAlpha(0.25F);
		}else if(accion=="activar"){
			botonEditarPartido.setEnabled(true);
			editarEventoPartido.setAlpha(1F);
		}
	}
	
	private void opcionBorrarPartido(String accion){
		//Desactivamos o activamos las imagenes a usar y le añadimos o quitamos visibilidad
		if(accion=="desactivar"){
			botonBorrarPartido.setEnabled(false);
			borrarEventoPartido.setAlpha(0.25F);
		}else if(accion=="activar"){
			botonBorrarPartido.setEnabled(true);
			borrarEventoPartido.setAlpha(1F);
		}
	}
	
	//Método que desactiva y activa las imágenes
	private void fechaActualImagenes(String signo,int num){
		if(signo=="Superior" & num==1){
			
			opcionAgregarEntrenamiento("desactivar");
			opcionVerEntrenamiento("desactivar");
			opcionEditarEntrenamiento("desactivar");
			opcionBorrarEntrenamiento("desactivar");
			opcionAgregarPartido("desactivar");
			opcionVerPartido("desactivar");
			opcionEditarPartido("desactivar");
			opcionBorrarPartido("desactivar");
			
		}else if(signo=="Inferior" & num==1){
			
			opcionAgregarEntrenamiento("activar");
			opcionVerEntrenamiento("desactivar");
			opcionEditarEntrenamiento("desactivar");
			opcionBorrarEntrenamiento("desactivar");
			opcionAgregarPartido("activar");
			opcionVerPartido("desactivar");
			opcionEditarPartido("desactivar");
			opcionBorrarPartido("desactivar");
			
		}else if(signo=="Superior" & num==2){
			
			opcionAgregarEntrenamiento("desactivar");
			opcionVerEntrenamiento("activar");
			opcionEditarEntrenamiento("desactivar");
			opcionBorrarEntrenamiento("desactivar");
			opcionAgregarPartido("desactivar");
			opcionVerPartido("desactivar");
			opcionEditarPartido("desactivar");
			opcionBorrarPartido("desactivar");
			
		}else if(signo=="Inferior" & num==2){
			
			opcionAgregarEntrenamiento("desactivar");
			opcionVerEntrenamiento("desactivar");
			opcionEditarEntrenamiento("activar");
			opcionBorrarEntrenamiento("activar");
			opcionAgregarPartido("desactivar");
			opcionVerPartido("desactivar");
			opcionEditarPartido("desactivar");
			opcionBorrarPartido("desactivar");
			
		}else if(signo=="Superior" & num==3){
			
			opcionAgregarEntrenamiento("desactivar");
			opcionVerEntrenamiento("desactivar");
			opcionEditarEntrenamiento("desactivar");
			opcionBorrarEntrenamiento("desactivar");
			opcionAgregarPartido("desactivar");
			opcionVerPartido("activar");
			opcionEditarPartido("desactivar");
			opcionBorrarPartido("desactivar");
				
		}else if(signo=="Inferior" & num==3){
			
			opcionAgregarEntrenamiento("desactivar");
			opcionVerEntrenamiento("desactivar");
			opcionEditarEntrenamiento("desactivar");
			opcionBorrarEntrenamiento("desactivar");
			opcionAgregarPartido("desactivar");
			opcionVerPartido("desactivar");
			opcionEditarPartido("activar");
			opcionBorrarPartido("activar");
			
		}else if(signo=="Superior" & num==4){
			
			opcionAgregarEntrenamiento("desactivar");
			opcionVerEntrenamiento("activar");
			opcionEditarEntrenamiento("desactivar");
			opcionBorrarEntrenamiento("desactivar");
			opcionAgregarPartido("desactivar");
			opcionVerPartido("activar");
			opcionEditarPartido("desactivar");
			opcionBorrarPartido("desactivar");
			
		}else if(signo=="Inferior" & num==4){
			
			opcionAgregarEntrenamiento("desactivar");
			opcionVerEntrenamiento("desactivar");
			opcionEditarEntrenamiento("activar");
			opcionBorrarEntrenamiento("activar");
			opcionAgregarPartido("desactivar");
			opcionVerPartido("desactivar");
			opcionEditarPartido("activar");
			opcionBorrarPartido("activar");
			
		}
	}
	
	private void accionesHerramientasEventos(){
		//Método que realiza la animación
		botonAgregarEntrenamiento.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1){
				switch(arg1.getAction()){
					case MotionEvent.ACTION_DOWN: {
						agregarEventoEntrenamiento.setImageResource(R.drawable.add_event_down);
						agregarEntrenamiento(); //Método para agregar el entrenamiento
						break;
					}
					case MotionEvent.ACTION_UP:
					case MotionEvent.ACTION_CANCEL: {
						agregarEventoEntrenamiento.setImageResource(R.drawable.add_event_up);
						break;
					}
				}
				return true;
			}
		});
		
		//Método que realiza la animación
		botonVerEntrenamiento.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1){
				switch(arg1.getAction()){
					case MotionEvent.ACTION_DOWN: {
						verEstadisticasEntrenamiento.setImageResource(R.drawable.look_stats_down);
						verStatsEntrenamiento(); //Método para ver las estadisticas del entrenamiento
						break;
					}
					case MotionEvent.ACTION_UP:
					case MotionEvent.ACTION_CANCEL: {
						verEstadisticasEntrenamiento.setImageResource(R.drawable.look_stats_up);
						break;
					}
				}
				return true;
			}
		});
		
		//Método que realiza la animación
		botonEditarEntrenamiento.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1){
				switch(arg1.getAction()){
					case MotionEvent.ACTION_DOWN: {
						editarEventoEntrenamiento.setImageResource(R.drawable.edit_event_down);
						editarEntrenamiento(); //Método para editar el entrenamiento
						break;
					}
					case MotionEvent.ACTION_UP:
					case MotionEvent.ACTION_CANCEL: {
						editarEventoEntrenamiento.setImageResource(R.drawable.edit_event_up);
						break;
					}
				}
				return true;
			}
		});
		
		//Método que realiza la animación
		botonBorrarEntrenamiento.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1){
				switch(arg1.getAction()){
					case MotionEvent.ACTION_DOWN: {
						borrarEventoEntrenamiento.setImageResource(R.drawable.delete_event_down);
						borrarEventoEntrenamiento.showContextMenu(); //Menú contextual para borrar el entrenamiento
						break;
					}
					case MotionEvent.ACTION_UP:
					case MotionEvent.ACTION_CANCEL: {
						borrarEventoEntrenamiento.setImageResource(R.drawable.delete_event_up);
						break;
					}
				}
				return true;
			}
		});
		
		//Método que realiza la animación
		botonAgregarPartido.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1){
				switch(arg1.getAction()){
					case MotionEvent.ACTION_DOWN: {
						agregarEventoPartido.setImageResource(R.drawable.add_event_down);
						agregarPartido(); //Método para agregar el partido
						break;
					}
					case MotionEvent.ACTION_UP:
					case MotionEvent.ACTION_CANCEL: {
						agregarEventoPartido.setImageResource(R.drawable.add_event_up);
						break;
					}
				}
				return true;
			}
		});
		
		//Método que realiza la animación
		botonVerPartido.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1){
				switch(arg1.getAction()){
					case MotionEvent.ACTION_DOWN: {
						verEstadisticasPartido.setImageResource(R.drawable.look_stats_down);
						verStatsPartido(); //Método para ver las estadisticas del partido
						break;
					}
					case MotionEvent.ACTION_UP:
					case MotionEvent.ACTION_CANCEL: {
						verEstadisticasPartido.setImageResource(R.drawable.look_stats_up);
						break;
					}
				}
				return true;
			}
		});
		
		//Método que realiza la animación
		botonEditarPartido.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1){
				switch(arg1.getAction()){
					case MotionEvent.ACTION_DOWN: {
						editarEventoPartido.setImageResource(R.drawable.edit_event_down);
						editarPartido(); //Método para editar el partido
						break;
					}
					case MotionEvent.ACTION_UP:
					case MotionEvent.ACTION_CANCEL: {
						editarEventoPartido.setImageResource(R.drawable.edit_event_up);
						break;
					}
				}
				return true;
			}
		});
		
		//Método que realiza la animación
		botonBorrarPartido.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1){
				switch(arg1.getAction()){
					case MotionEvent.ACTION_DOWN: {
						borrarEventoPartido.setImageResource(R.drawable.delete_event_down);
						borrarEventoPartido.showContextMenu(); //Menú contextual para borrar el partido
						break;
					}
					case MotionEvent.ACTION_UP:
					case MotionEvent.ACTION_CANCEL: {
						borrarEventoPartido.setImageResource(R.drawable.delete_event_up);
						break;
					}
				}
				return true;
			}
		});
		
	}
	
	//Los siguientes métodos abren otras pantallas o borran el contenido seleccionado del calendario
	
	private void agregarEntrenamiento(){
		String fecha=getFechaSeleccionada();
		Intent i=new Intent(this,PopUpNuevoEntrenamiento.class);
		i.putExtra("tipo","entrenamiento");
		i.putExtra("fecha",fecha);
		startActivity(i);
	}
	
	private void verStatsEntrenamiento(){
		String fecha=getFechaSeleccionada();
		Intent i=new Intent(this,PaginaCalendario.class);
		i.putExtra("tipo","entrenamiento");
		i.putExtra("fecha",fecha);
		startActivity(i);
	}
	
	private void editarEntrenamiento(){
		String fecha=getFechaSeleccionada();
		Intent i=new Intent(this,PaginaCalendario.class);
		i.putExtra("tipo","entrenamiento");
		i.putExtra("fecha",fecha);
		startActivity(i);
	}
	
	private void borrarEntrenamiento(){
		String fecha=getFechaSeleccionada();
		SentenciasSQLiteCalendario.borrarEventoEntrenamiento(this,fecha);
		accionesMostrarEventos(fecha);
	}
	
	private void agregarPartido(){
		String fecha=getFechaSeleccionada();
		Intent i=new Intent(this,PopUpNuevoPartido.class);
		i.putExtra("tipo","partido");
		i.putExtra("fecha",fecha);
		startActivity(i);
	}
	
	private void verStatsPartido(){
		String fecha=getFechaSeleccionada();
		Intent i=new Intent(this,PaginaCalendario.class);
		i.putExtra("tipo","partido");
		i.putExtra("fecha",fecha);
		startActivity(i);
	}
	
	private void editarPartido(){
		String fecha=getFechaSeleccionada();
		Intent i=new Intent(this,PaginaCalendario.class);
		i.putExtra("tipo","partido");
		i.putExtra("fecha",fecha);
		startActivity(i);
	}
	
	private void borrarPartido(){
		String fecha=getFechaSeleccionada();
		SentenciasSQLiteCalendario.borrarEventoPartido(this,fecha);
		accionesMostrarEventos(fecha);
	}
	
}