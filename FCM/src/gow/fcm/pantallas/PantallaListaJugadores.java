package gow.fcm.pantallas;

import gow.fcm.basedatos.ConexionSQLite;
import gow.fcm.footballcoachmanager.R;
import gow.fcm.popups.PopUpNuevoJugador;
import gow.fcm.sentencias.SentenciasSQLitePrincipal;
import gow.fcm.utilidades.ListaJugadores;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

public class PantallaListaJugadores extends Activity {
	
	private GridView listaJugadores; //Lista de los jugadores del equipo
	private int getDorsal; //Dorsal pulsado en la la lista
	private String[] nombreJugador,apellidosJugador,posicionJugador,dorsalJugador; //Matrices donde se les pasa los datos a mostrar en la lista
	private View pie; //Variable usada para almacenar un objeto View del pie de página de la lista de jugadores
	
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
		ConexionSQLite.getCrearSQLite(this);

		//Método que mostrará la lista de jugadores
		mostrarLista();
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
		//SentenciasSQLitePrincipal.getDatosJugadores(this);
		//dorsalJugador = SentenciasSQLitePrincipal.getVectorDorsal();
		//nombreJugador = SentenciasSQLitePrincipal.getVectorNombre();
		//apellidosJugador = SentenciasSQLitePrincipal.getVectorApellidos();
		//posicionJugador = SentenciasSQLitePrincipal.getVectorPosicion();
	}

	private void mostrarLista() {
		// Obtenemos el elemento GridView de android
		listaJugadores = (GridView) findViewById(R.id.lista_jugadores_equipo);
		// Registramos el menú contextual
		registerForContextMenu(listaJugadores);

		cargarJugadores();

		// Le enviamos a la clase ListaJugadores los datos a rellenar
		// ListaJugadores adaptador = new ListaJugadores(this, dorsalJugador,nombreJugador, apellidosJugador, posicionJugador);
		ListaJugadores adaptador = new ListaJugadores(this,new String[]{"1","2","3","4","5","6","7","8","9","10","11","12","13"},new String[]{"Pepe","Matias","Raul","Joli","Kevin","Artur","Segnent","Josep","Lolo","Paco","Tono","Pedro","Luis"},new String[]{"Lopez","Garcia","Martinez","Fernandez","Hernandez","Castillo","Cucu","Perez","Palau","Lopez","Perez","Martin","Martinez"},new String[]{"DE","QB","LB","DE","QB","LB","DE","QB","LB","LB","LB","DE","DE"});
		
	
		// Se colocan los datos en la lista
		listaJugadores.setAdapter(adaptador);

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

}
