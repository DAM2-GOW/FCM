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
	private String fechaActual,fechaSeleccionada,varFechaEvento="date_event"; //Variables para las fechas en la base de datos
	
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
	
	@Override
	protected void onResume(){
		super.onResume();
		
		//Este método actualiza el contenido del calendario al cerrar el Popup
		accionesMostrarEventos(getFechaSeleccionada());
	}
	
	//Método de creación de los menús contextuales
	@Override
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
	@SuppressLint({"NewApi","ResourceAsColor"})
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
		botonAgregarEntrenamiento=findViewById(R.id.boton_agregar_entrenamiento);
		botonVerEntrenamiento=findViewById(R.id.boton_ver_entrenamiento);
		botonEditarEntrenamiento=findViewById(R.id.boton_editar_entrenamiento);
		botonBorrarEntrenamiento=findViewById(R.id.boton_borrar_entrenamiento);
		botonAgregarPartido=findViewById(R.id.boton_agregar_partido);
		botonVerPartido=findViewById(R.id.boton_ver_partido);
		botonEditarPartido=findViewById(R.id.boton_editar_partido);
		botonBorrarPartido=findViewById(R.id.boton_borrar_partido);
		
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
		
		accionesMostrarEventos(fechaSeleccionada); //Imagenes desactivadas y activadas
		
		accionesHerramientasEventos(); //Acciones de las imagenes
		
		calendario.setOnDateChangeListener(new OnDateChangeListener(){
			@Override
			public void onSelectedDayChange(CalendarView arg, int year, int mes, int dia){
				mes=mes+1; //Le debemos sumar 1 al mes porque va solo del 0 al 11
				String month="0";
				if(mes<10){ //Si el mes es fechaInferior a 2 cifras, le agregamos un 0 delante para mantener el formato
					month=month.concat(String.valueOf(mes));
				}else{
					month=String.valueOf(mes);
				}
				
				String day="0";
				if(dia<10){
					day=day.concat(String.valueOf(dia));
				}else{
					day=String.valueOf(dia);
				}
				
				fechaSeleccionada=year+"-"+month+"-"+day;
				accionesMostrarEventos(fechaSeleccionada);
			}
		});
	}
	
	@SuppressLint("SimpleDateFormat")
	private void accionesMostrarEventos(String fecha){
		SentenciasSQLiteCalendario.getDatosEntrenamientos(this,fecha);
		SentenciasSQLiteCalendario.getDatosPartidos(this,fecha);
		
		//Sentencias para comprobar los eventos
		int totalEntrenamiento=Integer.parseInt(SentenciasSQLiteCalendario.getTotalEntrenamiento());
		int totalPartido=Integer.parseInt(SentenciasSQLiteCalendario.getTotalPartido());
		
		//Sentencias para comprobar la fecha de los eventos
		Date fechaEntrenamiento=SentenciasSQLiteCalendario.getFechaEntrenamiento();
		Date fechaPartido=SentenciasSQLiteCalendario.getFechaPartido();
		
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
		TextView numeroEntrenamiento=(TextView) findViewById(R.id.numero_entrenamiento);
		TextView entrenamientoEvento=(TextView) findViewById(R.id.entrenamiento_evento);
		TextView eventoPartido=(TextView) findViewById(R.id.evento_partido);
		TextView numeroPartido=(TextView) findViewById(R.id.numero_partido);
		TextView partidoEvento=(TextView) findViewById(R.id.partido_evento);
		
		//Desactivamos o activamos las imagenes comprobando las condiciones
		if((totalEntrenamiento==0 & totalPartido==0) || (String.valueOf(totalEntrenamiento)==null & String.valueOf(totalPartido)==null)){
			
			if(dates.after(dates2)){
				fechaActualImagenes("fechaSuperior",1);
			}else{
				fechaActualImagenes("fechaInferior",1);
			}
			
			eventoEntrenamiento.setText(R.string.no_training);
			numeroEntrenamiento.setText("");
			entrenamientoEvento.setText("");
			eventoPartido.setText(R.string.no_match);
			numeroPartido.setText("");
			partidoEvento.setText("");
				
		}else if(totalEntrenamiento!=0 & totalPartido==0){
			
			if(dates.after(fechaEntrenamiento)){
				fechaActualImagenes("fechaSuperior",2);
			}else{
				fechaActualImagenes("fechaInferior",2);
			}
			
			eventoPartido.setText(R.string.no_match);
			numeroPartido.setText("");
			partidoEvento.setText("");
			
			if(totalEntrenamiento>1){
				eventoEntrenamiento.setText(R.string.there_are);
				numeroEntrenamiento.setText(" "+totalEntrenamiento+" ");
				entrenamientoEvento.setText(R.string.trainings);
			}else if(totalEntrenamiento==1){
				eventoEntrenamiento.setText(R.string.there_are);
				numeroEntrenamiento.setText(" "+totalEntrenamiento+" ");
				entrenamientoEvento.setText(R.string.training);
			}
			
		}else if(totalEntrenamiento==0 & totalPartido!=0){
			
			if(dates.after(fechaPartido)){
				fechaActualImagenes("fechaSuperior",3);
			}else{
				fechaActualImagenes("fechaInferior",3);
			}
			
			eventoEntrenamiento.setText(R.string.no_training);
			numeroEntrenamiento.setText("");
			entrenamientoEvento.setText("");
			
			if(totalPartido==1){
				eventoPartido.setText(R.string.there_are);
				numeroPartido.setText(" "+totalPartido+" ");
				partidoEvento.setText(R.string.match);
			}
			
		}else if(totalEntrenamiento!=0 & totalPartido!=0){
			
			if(dates.after(fechaEntrenamiento) & dates.after(fechaPartido)){
				fechaActualImagenes("fechaSuperior",4);
			}else{
				fechaActualImagenes("fechaInferior",4);
			}
			
			if(totalEntrenamiento>1){
				eventoEntrenamiento.setText(R.string.there_are);
				numeroEntrenamiento.setText(" "+totalEntrenamiento+" ");
				entrenamientoEvento.setText(R.string.trainings);
			}else if(totalEntrenamiento==1){
				eventoEntrenamiento.setText(R.string.there_are);
				numeroEntrenamiento.setText(" "+totalEntrenamiento+" ");
				entrenamientoEvento.setText(R.string.training);
			}
			if(totalPartido==1){
				eventoPartido.setText(R.string.there_are);
				numeroPartido.setText(" "+totalPartido+" ");
				partidoEvento.setText(R.string.match);
			}
			
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
		if(signo=="fechaSuperior" & num==1){
			
			opcionAgregarEntrenamiento("desactivar");
			opcionVerEntrenamiento("desactivar");
			opcionEditarEntrenamiento("desactivar");
			opcionBorrarEntrenamiento("desactivar");
			opcionAgregarPartido("desactivar");
			opcionVerPartido("desactivar");
			opcionEditarPartido("desactivar");
			opcionBorrarPartido("desactivar");
			
		}else if(signo=="fechaInferior" & num==1){
			
			opcionAgregarEntrenamiento("activar");
			opcionVerEntrenamiento("desactivar");
			opcionEditarEntrenamiento("desactivar");
			opcionBorrarEntrenamiento("desactivar");
			opcionAgregarPartido("activar");
			opcionVerPartido("desactivar");
			opcionEditarPartido("desactivar");
			opcionBorrarPartido("desactivar");
			
		}else if(signo=="fechaSuperior" & num==2){
			
			opcionAgregarEntrenamiento("desactivar");
			opcionVerEntrenamiento("activar");
			opcionEditarEntrenamiento("desactivar");
			opcionBorrarEntrenamiento("desactivar");
			opcionAgregarPartido("desactivar");
			opcionVerPartido("desactivar");
			opcionEditarPartido("desactivar");
			opcionBorrarPartido("desactivar");
			
		}else if(signo=="fechaInferior" & num==2){
			
			opcionAgregarEntrenamiento("activar");
			opcionVerEntrenamiento("desactivar");
			opcionEditarEntrenamiento("activar");
			opcionBorrarEntrenamiento("activar");
			opcionAgregarPartido("activar");
			opcionVerPartido("desactivar");
			opcionEditarPartido("desactivar");
			opcionBorrarPartido("desactivar");
			
		}else if(signo=="fechaSuperior" & num==3){
			
			opcionAgregarEntrenamiento("desactivar");
			opcionVerEntrenamiento("desactivar");
			opcionEditarEntrenamiento("desactivar");
			opcionBorrarEntrenamiento("desactivar");
			opcionAgregarPartido("desactivar");
			opcionVerPartido("activar");
			opcionEditarPartido("desactivar");
			opcionBorrarPartido("desactivar");
				
		}else if(signo=="fechaInferior" & num==3){
			
			opcionAgregarEntrenamiento("activar");
			opcionVerEntrenamiento("desactivar");
			opcionEditarEntrenamiento("desactivar");
			opcionBorrarEntrenamiento("desactivar");
			opcionAgregarPartido("desactivar");
			opcionVerPartido("desactivar");
			opcionEditarPartido("activar");
			opcionBorrarPartido("activar");
			
		}else if(signo=="fechaSuperior" & num==4){
			
			opcionAgregarEntrenamiento("desactivar");
			opcionVerEntrenamiento("activar");
			opcionEditarEntrenamiento("desactivar");
			opcionBorrarEntrenamiento("desactivar");
			opcionAgregarPartido("desactivar");
			opcionVerPartido("activar");
			opcionEditarPartido("desactivar");
			opcionBorrarPartido("desactivar");
			
		}else if(signo=="fechaInferior" & num==4){
			
			opcionAgregarEntrenamiento("activar");
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
		//Pasamos la fecha seleccionada a milisegundos
		String fecha=getFechaSeleccionada();
		Date date=null;
		try{
			date=formato.parse(fecha);
		}catch (ParseException e){
			e.printStackTrace();
		}
		
		Intent i=new Intent(this,PopUpNuevoEntrenamiento.class);
		i.putExtra(varFechaEvento,date.getTime());
		startActivity(i);
	}
	
	private void verStatsEntrenamiento(){
		Intent i=new Intent(this,PaginaCalendario.class);
		startActivity(i);
	}
	
	private void editarEntrenamiento(){
		Intent i=new Intent(this,PaginaCalendario.class);
		startActivity(i);
	}
	
	private void borrarEntrenamiento(){
		String fecha=getFechaSeleccionada();
		SentenciasSQLiteCalendario.borrarEventoEntrenamiento(this,fecha);
		accionesMostrarEventos(fecha);
	}
	
	private void agregarPartido(){
		//Pasamos la fecha seleccionada a milisegundos
		String fecha=getFechaSeleccionada();
		Date date=new Date();
		try{
			date=formato.parse(fecha);
		}catch (ParseException e){
			e.printStackTrace();
		}
		
		Intent i=new Intent(this,PopUpNuevoPartido.class);
		i.putExtra(varFechaEvento,date.getTime());
		startActivity(i);
	}
	
	private void verStatsPartido(){
		Intent i=new Intent(this,PaginaCalendario.class);
		startActivity(i);
	}
	
	private void editarPartido(){
		Intent i=new Intent(this,PaginaCalendario.class);
		startActivity(i);
	}
	
	private void borrarPartido(){
		String fecha=getFechaSeleccionada();
		SentenciasSQLiteCalendario.borrarEventoPartido(this,fecha);
		accionesMostrarEventos(fecha);
	}
	
}