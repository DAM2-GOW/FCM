package gow.fcm.pantallas;

import java.util.ArrayList;

import gow.fcm.basedatos.ConexionSQLite;
import gow.fcm.footballcoachmanager.R;
import gow.fcm.popups.PopUpNuevoJugador;
import gow.fcm.sentencias.SentenciasSQLitePrincipal;
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
	private String[] nombreJugador,apellidosJugador,posicionJugador,dorsalJugador, tipoJugador; //Matrices donde se les pasa los datos a mostrar en la lista
	private View pie; //Variable usada para almacenar un objeto View del pie de página de la lista de jugadores
	private ArrayList<String[]> jugadoresTipo;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); // No mostramos la
		// barra de la
		// cabecera con el
		// nombre de la
		// aplicación
		setContentView(R.layout.activity_lista_jugadores);

		// Se crea la base de datos si no existe o se actualiza si fuera necesario
		
		// JOSEEEEEEEP AQUI CREA LA BASE DE DATOS ¿OTRA VEZ?
		ConexionSQLite.getCrearSQLite(this);

		//Método que mostrará la lista de jugadores
		mostrarLista(true);

		ImageButton BtnAtaque = (ImageButton) findViewById(R.id.imageButtonListaAtaque);
		BtnAtaque.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				obtenerJugadoresTipo("Ataque");
				mostrarLista(false);
				Log.d("Raul", "Pulsado ataque");
			}
		}
				);

		ImageButton BtnDefensa = (ImageButton) findViewById(R.id.imageButtonListaDefensa);
		BtnDefensa.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				obtenerJugadoresTipo("Defensa");
				mostrarLista(false);
				Log.d("Raul", "Pulsado defensa");
			}
		}
				);


		ImageButton BtnEe = (ImageButton) findViewById(R.id.imageButtonListaEe);
		BtnEe.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				obtenerJugadoresTipo("Equipos Especiales");
				mostrarLista(false);
				Log.d("Raul", "Pulsado ee");
			}
		}
				);

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
		case R.id.editarJug:
			editarJugador();
			return true;
		case R.id.borrarJug: // Esta opción solo sirve para mostrar los
			// siguientes valores
			return true;
		case R.id.optionNoJugador: // Esta opción no realiza ninguna acción
			return true;
		case R.id.optionSiJugador:
			borrarJugador();
			actualizarLista();
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}

	private void cargarJugadores() {
		//Forzamos los datos de entrada que serán mostrados en la lista
		/*SentenciasSQLitePrincipal.getDatosJugadores(this);
		dorsalJugador = SentenciasSQLitePrincipal.getVectorDorsal();
		nombreJugador = SentenciasSQLitePrincipal. .getVectorNombre();
		apellidosJugador = SentenciasSQLitePrincipal.getVectorApellidos();
		posicionJugador = SentenciasSQLitePrincipal.getVectorPosicion();
		tipoJugador = //Necesito recibir esto JOSEEEEEEEEEEEEP
		 */
		dorsalJugador=new String[]{"1","2","3","4","5","6","7","8","9","10","11","12","13"};
		Log.d("Raul", "cargarJugadores"+dorsalJugador[5]);
		nombreJugador=new String[]{"Pepe","Matias","Raul","Joli","Kevin","Artur","Segnent","Josep","Lolo","Paco","Tono","Pedro","Luis"};
		apellidosJugador=new String[]{"Lopez","Garcia","Martinez","Fernandez","Hernandez","Castillo","Cucu","Perez","Palau","Lopez","Perez","Martin","Martinez"};
		posicionJugador=new String[]{"DE","QB","LB","DE","QB","LB","DE","QB","LB","LB","LB","DE","DE"};
		tipoJugador=new String[]{"Ataque","Ataque","Ataque","Defensa","Defensa","Defensa","Defensa","Defensa","Equipos Especiales","Equipos Especiales","Equipos Especiales","Equipos Especiales","Equipos Especiales"};
	}

	private void mostrarLista(boolean seleccion) {
		// Obtenemos el elemento GridView de android
		listaJugadores = (GridView) findViewById(R.id.lista_jugadores_equipo);
		// Registramos el menú contextual
		registerForContextMenu(listaJugadores);

		if(seleccion){
			//Buscamos en la base de datos
			cargarJugadores();

			// Le enviamos a la clase ListaJugadores los datos a rellenar
			 ListaJugadores adaptador = new ListaJugadores(this, dorsalJugador, nombreJugador, apellidosJugador, posicionJugador);

			// Se colocan los datos en la lista
			listaJugadores.setAdapter(adaptador);
		}else{
			//Estos datos se cogen de los datos ya leidos
			int longitud =jugadoresTipo.size();
			String[] dorsales=new String[longitud];
			String[] nombres=new String[longitud];
			String[] apellidos=new String[longitud];
			String[] posiciones=new String[longitud];
			for (int i = 0; i <longitud; i++) {
				String[] jugadores = new String[longitud];
				jugadores = jugadoresTipo.get(i);
				dorsales[i]= jugadores[0];
				nombres[i]= jugadores[1];
				apellidos[i]= jugadores[2];
				posiciones[i]= jugadores[3];
			}
			ListaJugadores adaptador = new ListaJugadores(this, dorsales, nombres, apellidos, posiciones);
			// Se colocan los datos en la lista
			listaJugadores.setAdapter(adaptador);
		}



		accionesListaJugadores();
	}


	private void actualizarLista() {
		cargarJugadores();

		// Le enviamos a la clase ListaJugadores los datos a actualizar
		ListaJugadores adaptador = new ListaJugadores(this, nombreJugador, apellidosJugador, posicionJugador, dorsalJugador);

		// Se colocan los datos en la lista
		listaJugadores.setAdapter(adaptador);

		accionesListaJugadores();
	}

	private void accionesListaJugadores() {
		// Este contenido se mostrará para informar de que no hay jugadores
		ImageView imgDiv = (ImageView) findViewById(R.id.img_div2);

		// Al hacer click un jugador de la lista se despliega un menú
		//if (nombreJugador.length > 0) {

		// Lo ocultamos
		imgDiv.setVisibility(View.INVISIBLE);

		// Evento de selección de opción de la lista de jugadores
		listaJugadores.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View elemento,
					int position, long duration) {
				if (elemento == pie) {
					// Solo controlamos la excepción para que no devuela un
					// error
				} else { // Controlamos que si no es el pie de página debe
					// realizar una acción diferente
					getDorsal = Integer.parseInt(dorsalJugador[position - 1]);
					listaJugadores.showContextMenu(); // Muestra las
					// opciones del menú
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

		// Este código realiza una animación al hacer clic sobre la imágen de
		// agregar jugador
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

	// Método para editar al jugador
	private void editarJugador() {
		int dorsal = getDorsal;
		Intent i = new Intent(this,PantallaListaJugadores.class);
		i.putExtra("dorsal", dorsal);
		startActivity(i);
	}

	// Método para agregar al jugador
	private void agregarJugador() {
		Intent i = new Intent(this, PopUpNuevoJugador.class);
		startActivity(i);
	}

	// Método para borrar al jugador
	private void borrarJugador() {
		//SentenciasSQLitePrincipal.borrarJugador(getDorsal, this);
	}

	private void obtenerJugadoresTipo(String tipoJ){
		jugadoresTipo=new ArrayList<String[]>();
		int i=0;
		Log.d("Raul", "Entrando en obtenerJugador:"+tipoJ);
		for (String tipo : tipoJugador) {
			Log.d("Raul", "Entrando dentro del for:"+tipo);
			if (tipo.compareTo(tipoJ)==0) {
				String[] jugador = new String[4];
				Log.d("Raul", dorsalJugador[i]+"i="+i);
				jugador[0]=dorsalJugador[i];
				Log.d("Raul", nombreJugador[i]+"i="+i);
				jugador[1]=nombreJugador[i];
				Log.d("Raul", apellidosJugador[i]+"i="+i);
				jugador[2]=apellidosJugador[i];
				Log.d("Raul", posicionJugador[i]+"i="+i);
				jugador[3]=posicionJugador[i];
				jugadoresTipo.add(jugador);
			}
			i++;

		}
	}
}
