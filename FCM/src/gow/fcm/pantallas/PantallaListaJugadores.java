package gow.fcm.pantallas;

import gow.fcm.footballcoachmanager.R;
import gow.fcm.popups.PopUpNuevoEditarJugador;
import gow.fcm.sentencias.SentenciasSQLiteListaJugadores;
import gow.fcm.utilidades.ListaJugadores;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

public class PantallaListaJugadores extends Activity {

	private GridView listaJugadores; //Lista de los jugadores del equipo
	private int getDorsal; //Dorsal pulsado en la la lista
	private String[] nombreJugador,apellidosJugador,posicionJugador,dorsalJugador; //Matrices donde se les pasa los datos a mostrar en la lista
	private ListaJugadores adaptador; //Declaramos el adaptador
	private View pie; //Variable usada para almacenar un objeto View del pie de página de la lista de jugadores
	private String tiposJugadores;
	private static boolean botonAtaque=false,botonDefensa=false,botonEquiposEsp=false;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); // No mostramos la
		// barra de la
		// cabecera con el
		// nombre de la
		// aplicación
		setContentView(R.layout.activity_lista_jugadores);

		//DatosFootball.setDatosFootball(this,1,1);
		//SentenciasInsertSQLite.insertarSQLite("Equipos",new String[]{"nombre"},new String[]{"Pepe Team"});
		//SentenciasInsertSQLite.insertarSQLite("Entrenadores",new String[]{"id_equipo","nombre","apellidos","usuario","clave","pregunta_seguridad","respuesta_seguridad"},new String[]{"1","Pepe","García","pepe","1234","1","Chispi"});
		//SentenciasInsertSQLite.insertarSQLite("Jugadores",new String[]{"id_equipo","nombre","apellidos","edad","posicion","tipo","dorsal"},new String[]{"1","Antonio","Martinez","22","DE","0","32"});
		//SentenciasInsertSQLite.insertarSQLite("Jugadores",new String[]{"id_equipo","nombre","apellidos","edad","posicion","tipo","dorsal"},new String[]{"1","Jose","Martinez","23","QB","1","40"});
		//SentenciasInsertSQLite.insertarSQLite("Jugadores",new String[]{"id_equipo","nombre","apellidos","edad","posicion","tipo","dorsal"},new String[]{"1","Mario","Martinez","24","K","2","22"});
		//SentenciasInsertSQLite.insertarSQLite("Entrenamientos",new String[]{"id_equipo","tipo","dirigido","dia","fecha"},new String[]{"1","1","Defensas","2014-02-21","2014-02-21 18:00:00"});
		//SentenciasInsertSQLite.insertarSQLite("Entrenamientos",new String[]{"id_equipo","tipo","dirigido","dia","fecha"},new String[]{"1","2","Ataque","2014-02-21","2014-02-21 19:00:00"});
		//SentenciasInsertSQLite.insertarSQLite("Partidos",new String[]{"id_equipo","lugar","rival","dia","fecha"},new String[]{"1","0","New York Giants","2014-02-21","2014-02-21 18:00:00"});
		//SentenciasInsertSQLite.insertarSQLite("Partidos",new String[]{"id_equipo","lugar","rival","dia","fecha"},new String[]{"1","1","New York Jets","2014-02-22","2014-02-22 18:00:00"});

		// Método que mostrará la lista de jugadores
		mostrarLista();
		
		final ImageButton BtnAtaque = (ImageButton) findViewById(R.id.imageButtonListaAtaque);
		final ImageButton BtnDefensa = (ImageButton) findViewById(R.id.imageButtonListaDefensa);
		final ImageButton BtnEe = (ImageButton) findViewById(R.id.imageButtonListaEe);
		
		BtnAtaque.setOnClickListener(new OnClickListener() {

			// Funcionalidad al hacer click en el botón de Ataque
			@SuppressLint("ResourceAsColor")
			public void onClick(View v) {
				if(botonAtaque==false){
					tiposJugadores="0";
					actualizarLista(tiposJugadores);
					botonAtaque=true;
					botonDefensa=false;
					botonEquiposEsp=false;
					BtnAtaque.setBackgroundColor(0x0FFFFFFF);
					BtnDefensa.setBackgroundColor(0x00000000);
					BtnEe.setBackgroundColor(0x00000000);
				}else if(botonAtaque==true){
					mostrarLista();
					botonAtaque=false;
					botonDefensa=false;
					botonEquiposEsp=false;
					BtnAtaque.setBackgroundColor(0x00000000);
					BtnDefensa.setBackgroundColor(0x00000000);
					BtnEe.setBackgroundColor(0x00000000);
				}
			}
		}
				);

		BtnDefensa.setOnClickListener(new OnClickListener() {
			// Funcionalidad al hacer click en el botón de Defensa
			public void onClick(View v) {
				if(botonDefensa==false){
					tiposJugadores="1";
					actualizarLista(tiposJugadores);
					botonAtaque=false;
					botonDefensa=true;
					botonEquiposEsp=false;
					BtnAtaque.setBackgroundColor(0x00000000);
					BtnDefensa.setBackgroundColor(0x0FFFFFFF);
					BtnEe.setBackgroundColor(0x00000000);
				}else if(botonDefensa==true){
					mostrarLista();
					botonAtaque=false;
					botonDefensa=false;
					botonEquiposEsp=false;
					BtnAtaque.setBackgroundColor(0x00000000);
					BtnDefensa.setBackgroundColor(0x00000000);
					BtnEe.setBackgroundColor(0x00000000);
				}
			}
		}
				);
		
		BtnEe.setOnClickListener(new OnClickListener() {
			// Funcionalidad al hacer click en el botón de EE
			public void onClick(View v) {
				if(botonEquiposEsp==false){
					tiposJugadores="2";
					actualizarLista(tiposJugadores);
					botonAtaque=false;
					botonDefensa=false;
					botonEquiposEsp=true;
					BtnAtaque.setBackgroundColor(0x00000000);
					BtnDefensa.setBackgroundColor(0x00000000);
					BtnEe.setBackgroundColor(0x0FFFFFFF);
				}else if(botonEquiposEsp==true){
					mostrarLista();
					botonAtaque=false;
					botonDefensa=false;
					botonEquiposEsp=false;
					BtnAtaque.setBackgroundColor(0x00000000);
					BtnDefensa.setBackgroundColor(0x00000000);
					BtnEe.setBackgroundColor(0x00000000);
				}
			}
		});
	}

	protected void onResume(){
		super.onResume();
		
		if(botonAtaque==true){
			tiposJugadores="0";
			actualizarLista(tiposJugadores);
		}else if(botonDefensa==true){
			tiposJugadores="1";
			actualizarLista(tiposJugadores);
		}else if(botonEquiposEsp==true){
			tiposJugadores="2";
			actualizarLista(tiposJugadores);
		}else{
			mostrarLista();
		}
	}
	
	// Método de creación de los menús contextuales
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		switch (v.getId()) {
		case R.id.lista_jugadores_equipo:
			inflater.inflate(R.menu.jugador_select, menu);
			break;
		default:
			break;
		}
	}
	
	// Método que indica la acción a realizar según la opción elegida en el menú
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.estadisticasJugador: estadisticasJugador();
			return true;
		case R.id.editarJug: editarJugador();
			return true;
		case R.id.borrarJug: // Esta opción solo sirve para mostrar los
			// siguientes valores
			return true;
		case R.id.optionNoJugador: // Esta opción no realiza ninguna acción
			return true;
		case R.id.optionSiJugador: borrarJugador();
		
			if(botonAtaque==true){
				tiposJugadores="0";
				actualizarLista(tiposJugadores);
			}else if(botonDefensa==true){
				tiposJugadores="1";
				actualizarLista(tiposJugadores);
			}else if(botonEquiposEsp==true){
				tiposJugadores="2";
				actualizarLista(tiposJugadores);
			}else{
				mostrarLista();
			}
		
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}
	
	private void estadisticasJugador(){
		Intent i=new Intent(this,EstadisticasJugador.class);
		startActivity(i);
	}
	
	private void cargarJugadores() {
		//Forzamos los datos de entrada que serán mostrados en la lista
		SentenciasSQLiteListaJugadores.getDatosJugador(this);
		dorsalJugador = SentenciasSQLiteListaJugadores.getDorsalJugador();
		nombreJugador = SentenciasSQLiteListaJugadores.getNombreJugador();
		apellidosJugador = SentenciasSQLiteListaJugadores.getApellidosJugador();
		posicionJugador = SentenciasSQLiteListaJugadores.getPosicionJugador();

		//dorsalJugador=new String[]{"1","2","3","4","5","6","7","8","9","10","11","12","13"};
		//Log.d("Raul", "cargarJugadores"+dorsalJugador[5]);
		//nombreJugador=new String[]{"Pepe","Matias","Raul","Joli","Kevin","Artur","Segnent","Josep","Lolo","Paco","Tono","Pedro","Luis"};
		//apellidosJugador=new String[]{"Lopez","Garcia","Martinez","Fernandez","Hernandez","Castillo","Cucu","Perez","Palau","Lopez","Perez","Martin","Martinez"};
		//posicionJugador=new String[]{"DE","QB","LB","DE","QB","LB","DE","QB","LB","LB","LB","DE","DE"};
		//tipoJugador=new String[]{"Ataque","Ataque","Ataque","Defensa","Defensa","Defensa","Defensa","Defensa","Equipos Especiales","Equipos Especiales","Equipos Especiales","Equipos Especiales","Equipos Especiales"};
	}

	private void cargarTipoJugadores(String tipo){
		//Forzamos los datos de entrada que serán mostrados en la lista según el tipo de jugador
		SentenciasSQLiteListaJugadores.getDatosJugadorTipo(this,tipo);
		dorsalJugador = SentenciasSQLiteListaJugadores.getDorsalJugador();
		nombreJugador = SentenciasSQLiteListaJugadores.getNombreJugador();
		apellidosJugador = SentenciasSQLiteListaJugadores.getApellidosJugador();
		posicionJugador = SentenciasSQLiteListaJugadores.getPosicionJugador();
	}

	private void mostrarLista() {
		// Obtenemos el elemento GridView de android
		listaJugadores = (GridView) findViewById(R.id.lista_jugadores_equipo);
		//Registramos el menú contextual
		registerForContextMenu(listaJugadores);

		//Buscamos en la base de datos
		cargarJugadores();

		//Le enviamos a la clase ListaJugadores los datos a rellenar
		adaptador = new ListaJugadores(this, dorsalJugador, nombreJugador, apellidosJugador, posicionJugador);

		//Se colocan los datos en la lista
		listaJugadores.setAdapter(adaptador);

		accionesListaJugadores();
	}

	private void actualizarLista(String tipo) {
		cargarTipoJugadores(tiposJugadores);

		//Le enviamos a la clase ListaJugadores los datos a rellenar
		adaptador = new ListaJugadores(this, dorsalJugador, nombreJugador, apellidosJugador, posicionJugador);

		//Se colocan los datos en la lista
		listaJugadores.setAdapter(adaptador);

		accionesListaJugadores();
	}

	private void accionesListaJugadores() {
		// Este contenido se mostrará para informar de que no hay jugadores
		ImageView imgDiv = (ImageView) findViewById(R.id.img_div2);

		//Al hacer click un jugador de la lista se despliega un menú

		//Lo ocultamos
		imgDiv.setVisibility(View.INVISIBLE);

		//Evento de selección de opción de la lista de jugadores
		listaJugadores.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View elemento,
					int position, long duration) {
				if (elemento == pie) {
					// Solo controlamos la excepción para que no devuela un
					// error
				} else { // Controlamos que si no es el pie de página debe
					// realizar una acción diferente
					getDorsal = Integer.parseInt(dorsalJugador[position]);
					listaJugadores.showContextMenu(); // Muestra las
					// opciones del menú
					// contextual
				}
			}
		});


		//Lo mostramos
		imgDiv.setVisibility(View.VISIBLE);

		//}

		//Cargamos un elemento ImageView
		final ImageView addJugador = (ImageView) findViewById(R.id.add_jugador);

		//Este código realiza una animación al hacer clic sobre la imágen de
		//agregar jugador
		addJugador.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				switch (arg1.getAction()) {
				case MotionEvent.ACTION_DOWN: {
					addJugador.setImageResource(R.drawable.add_jugador_down);
					agregarJugador(); // Método para agregar al jugador
					break;
				}
				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_CANCEL: {
					addJugador.setImageResource(R.drawable.add_jugador_up);
					break;
				}
				}
				return true;
			}
		});
	}
	
	//Método para editar al jugador
	private void editarJugador() {
		String dorsal = String.valueOf(getDorsal);
		Intent i = new Intent(this,PopUpNuevoEditarJugador.class);
		i.putExtra("action", "editar");
		i.putExtra("dorsal", dorsal);
		startActivity(i);
	}

	//Método para agregar al jugador
	private void agregarJugador() {
		Intent i = new Intent(this, PopUpNuevoEditarJugador.class);
		i.putExtra("action", "agregar");
		startActivity(i);
	}

	//Método para borrar al jugador
	private void borrarJugador() {
		SentenciasSQLiteListaJugadores.borrarJugador(this,getDorsal);
	}

}
