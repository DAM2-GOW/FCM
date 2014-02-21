package gow.fcm.pantallas;

import gow.fcm.basedatos.ConexionSQLite;
import gow.fcm.footballcoachmanager.R;
import gow.fcm.popups.PopUpNuevoJugador;
import gow.fcm.sentencias.SentenciasInsertSQLite;
import gow.fcm.sentencias.SentenciasSQLiteListaJugadores;
import gow.fcm.sentencias.SentenciasUpdateSQLite;
import gow.fcm.sharefprefs.DatosFootball;
import gow.fcm.utilidades.ListaJugadores;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
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
	private View pie; //Variable usada para almacenar un objeto View del pie de p�gina de la lista de jugadores
	private String tiposJugadores;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); // No mostramos la
		// barra de la
		// cabecera con el
		// nombre de la
		// aplicaci�n
		setContentView(R.layout.activity_lista_jugadores);
		
		// Se crea la base de datos si no existe o se actualiza si fuera necesario
		ConexionSQLite.getCrearSQLite(this);
		
		//DatosFootball.setDatosFootball(this,1,1);
		//SentenciasInsertSQLite.insertarSQLite("Equipos",new String[]{"nombre"},new String[]{"Pepe Team"});
		//SentenciasInsertSQLite.insertarSQLite("Entrenadores",new String[]{"id_equipo","nombre","apellidos","usuario","clave","pregunta_seguridad","respuesta_seguridad"},new String[]{"1","Pepe","Garc�a","pepe","1234","1","Chispi"});
		//SentenciasInsertSQLite.insertarSQLite("Jugadores",new String[]{"id_equipo","nombre","apellidos","edad","posicion","tipo","dorsal"},new String[]{"1","Antonio","Martinez","22","DE","Defensa","32"});
		//SentenciasInsertSQLite.insertarSQLite("Jugadores",new String[]{"id_equipo","nombre","apellidos","edad","posicion","tipo","dorsal"},new String[]{"1","Jose","Martinez","23","QB","Ataque","40"});
		//SentenciasInsertSQLite.insertarSQLite("Jugadores",new String[]{"id_equipo","nombre","apellidos","edad","posicion","tipo","dorsal"},new String[]{"1","Mario","Martinez","24","K","Equipos Especiales","22"});
		//SentenciasInsertSQLite.insertarSQLite("Entrenamientos",new String[]{"id_equipo","tipo","dirigido","dia","fecha"},new String[]{"1","1","Defensas","2014-02-21","2014-02-21 18:00:00"});
		//SentenciasInsertSQLite.insertarSQLite("Entrenamientos",new String[]{"id_equipo","tipo","dirigido","dia","fecha"},new String[]{"1","2","Ataque","2014-02-21","2014-02-21 19:00:00"});
		//SentenciasInsertSQLite.insertarSQLite("Partidos",new String[]{"id_equipo","lugar","rival","dia","fecha"},new String[]{"1","0","New York Giants","2014-02-21","2014-02-21 18:00:00"});
		//SentenciasInsertSQLite.insertarSQLite("Partidos",new String[]{"id_equipo","lugar","rival","dia","fecha"},new String[]{"1","1","New York Jets","2014-02-22","2014-02-22 18:00:00"});
		
		//M�todo que mostrar� la lista de jugadores
		mostrarLista();

		ImageButton BtnAtaque = (ImageButton) findViewById(R.id.imageButtonListaAtaque);
		BtnAtaque.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				tiposJugadores="Ataque";
				actualizarLista(tiposJugadores);
				Log.d("Raul", "Pulsado ataque");
			}
		}
				);

		ImageButton BtnDefensa = (ImageButton) findViewById(R.id.imageButtonListaDefensa);
		BtnDefensa.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				tiposJugadores="Defensa";
				actualizarLista(tiposJugadores);
				Log.d("Raul", "Pulsado defensa");
			}
		}
				);

		ImageButton BtnEe = (ImageButton) findViewById(R.id.imageButtonListaEe);
		BtnEe.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				tiposJugadores="Equipos Especiales";
				actualizarLista(tiposJugadores);
				Log.d("Raul", "Pulsado ee");
			}
		}
				);
	}

	// M�todo de creaci�n de los men�s contextuales
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

	// M�todo que indica la acci�n a realizar seg�n la opci�n elegida en el men�
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.editarJug:
			editarJugador();
			return true;
		case R.id.borrarJug: // Esta opci�n solo sirve para mostrar los
			// siguientes valores
			return true;
		case R.id.optionNoJugador: // Esta opci�n no realiza ninguna acci�n
			return true;
		case R.id.optionSiJugador:
			borrarJugador();
			actualizarLista(tiposJugadores);
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}

	private void cargarJugadores() {
		//Forzamos los datos de entrada que ser�n mostrados en la lista
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
		//Forzamos los datos de entrada que ser�n mostrados en la lista seg�n el tipo de jugador
		SentenciasSQLiteListaJugadores.getDatosJugadorTipo(this,tipo);
		dorsalJugador = SentenciasSQLiteListaJugadores.getDorsalJugador();
		nombreJugador = SentenciasSQLiteListaJugadores.getNombreJugador();
		apellidosJugador = SentenciasSQLiteListaJugadores.getApellidosJugador();
		posicionJugador = SentenciasSQLiteListaJugadores.getPosicionJugador();
	}

	private void mostrarLista(/*boolean seleccion*/) {
		// Obtenemos el elemento GridView de android
		listaJugadores = (GridView) findViewById(R.id.lista_jugadores_equipo);
		// Registramos el men� contextual
		registerForContextMenu(listaJugadores);

			//Buscamos en la base de datos
			cargarJugadores();

			// Le enviamos a la clase ListaJugadores los datos a rellenar
			 ListaJugadores adaptador = new ListaJugadores(this, dorsalJugador, nombreJugador, apellidosJugador, posicionJugador);

			// Se colocan los datos en la lista
			listaJugadores.setAdapter(adaptador);

		accionesListaJugadores();
	}

	private void actualizarLista(String tipo) {
		cargarTipoJugadores(tiposJugadores);

		// Le enviamos a la clase ListaJugadores los datos a rellenar
		 ListaJugadores adaptador = new ListaJugadores(this, dorsalJugador, nombreJugador, apellidosJugador, posicionJugador);
		 
		// Se colocan los datos en la lista
		listaJugadores.setAdapter(adaptador);

		accionesListaJugadores();
	}

	private void accionesListaJugadores() {
		// Este contenido se mostrar� para informar de que no hay jugadores
		ImageView imgDiv = (ImageView) findViewById(R.id.img_div2);

		// Al hacer click un jugador de la lista se despliega un men�
		//if (nombreJugador.length > 0) {

		// Lo ocultamos
		imgDiv.setVisibility(View.INVISIBLE);

		// Evento de selecci�n de opci�n de la lista de jugadores
		listaJugadores.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View elemento,
					int position, long duration) {
				if (elemento == pie) {
					// Solo controlamos la excepci�n para que no devuela un
					// error
				} else { // Controlamos que si no es el pie de p�gina debe
					// realizar una acci�n diferente
					getDorsal = Integer.parseInt(dorsalJugador[position - 1]);
					listaJugadores.showContextMenu(); // Muestra las
					// opciones del men�
					// contextual
				}
			}
		});

		//} else if (nombreJugador.length == 0) {

		// Lo mostramos
		imgDiv.setVisibility(View.VISIBLE);

		//}
		
		// Cargamos un elemento ImageView
		final ImageView addJugador = (ImageView) findViewById(R.id.add_jugador);

		// Este c�digo realiza una animaci�n al hacer clic sobre la im�gen de
		// agregar jugador
		addJugador.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				switch (arg1.getAction()) {
				case MotionEvent.ACTION_DOWN: {
					addJugador.setImageResource(R.drawable.add_jugador_down);
					agregarJugador(); // M�todo para agregar al jugador
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

	// M�todo para editar al jugador
	private void editarJugador() {
		int dorsal = getDorsal;
		Intent i = new Intent(this,PantallaListaJugadores.class);
		i.putExtra("dorsal", dorsal);
		startActivity(i);
	}

	// M�todo para agregar al jugador
	private void agregarJugador() {
		Intent i = new Intent(this, PopUpNuevoJugador.class);
		startActivity(i);
	}

	// M�todo para borrar al jugador
	private void borrarJugador() {
		//SentenciasSQLitePrincipal.borrarJugador(getDorsal, this);
	}

	//M�todo que permite obtener los datos de los jugadores
	/*private void obtenerJugadoresTipo(String tipoJ){
		jugadoresTipo=new ArrayList<String[]>();
		int i=0;
		Log.d("Hola", "Entrando en obtenerJugador:"+tipoJ);
		for (String tipo : tipoJugador) {
			Log.d("Hola", "Entrando dentro del for:"+tipo);
			if (tipo.compareTo(tipoJ)==0) {
				String[] jugador = new String[4];
				Log.d("Hola", dorsalJugador[i]+"i="+i);
				jugador[0]=dorsalJugador[i];
				Log.d("Hola", nombreJugador[i]+"i="+i);
				jugador[1]=nombreJugador[i];
				Log.d("Hola", apellidosJugador[i]+"i="+i);
				jugador[2]=apellidosJugador[i];
				Log.d("Hola", posicionJugador[i]+"i="+i);
				jugador[3]=posicionJugador[i];
				jugadoresTipo.add(jugador);
			}
			i++;
		}
	}*/
}
