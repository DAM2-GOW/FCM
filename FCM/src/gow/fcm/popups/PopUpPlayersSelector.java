package gow.fcm.popups;

import gow.fcm.footballcoachmanager.R;
import gow.fcm.sentencias.SentenciasSQLitePlayingGround;
import gow.fcm.utilidades.ArrayAdapterPlayersGrid;
import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

public class PopUpPlayersSelector extends Activity {
	ArrayAdapterPlayersGrid gridViewCustomeAdapter;
	SentenciasSQLitePlayingGround sentences;
	GridView gridView;
	ArrayList<Integer> playersPositionSelected = new ArrayList<Integer>(); // Almacena una lista de jugadores por posicion seleccionados.
	ArrayList<Integer> playersIDSelected = new ArrayList<Integer>(); // Almacena una lista de ID de jugador seleccionados para jugar.
	Button btn_OK;
	private boolean firstTime; // indica si es la primera que se selecciona a un Jugador del GridView.
	private boolean selected; // Indica si el jugador esta seleccionado por motivos de deseleccion y coloreado.
	private int lastPosition; // Se almancena la ultima posicion del jugador seleccionado.
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		showAsPopup(this); // La personalizacion del Activity depende de esta linea, y DEBE ser llamada en este punto concreto.
		setContentView(R.layout.popup_players_selector);
		sentences = new SentenciasSQLitePlayingGround(this);
		btn_OK = (Button) findViewById(R.id.btn_OK);// Obtenemos onjetos a usar.
		gridView = (GridView) findViewById(R.id.popUpGridView);
		selected = false; // Se inicia la variable por defecto en falso. (todavia no se ha seleccioando ningun jugador.)
		firstTime = this.getIntent().getExtras().getBoolean("first_time"); // Se obtienen los añadidos adjuntos con esta clase.
		initialize(); // Se llama al metodo que muestra en la clase la tabla de los jugadores.

	}

	/**
	 * Este metodo inicia el GridView mostrando los jugadores que son requeridos, todos
	 * si es la primera vez que se inicia el PopUp, o todos a excepcion de los que ya
	 * se encuentran en el campo si es llamado para cambiar a un jugador.
	 */
	private void initialize(){
		
		if(firstTime){
			sentences.ObtenerDatosCompletosJugadores(this); // Mandamos la orden de obtener los datos de los jugadores.
		}else{
			sentences.ObtenerDatosDescartandoJugadores(this, this.getIntent().getExtras().getIntArray("players_in_use")); // Mandamos la orden de obtener los datos de los jugadores.
		}

		// Se llama al metodo para crear el adaptador que mostrara los datos obtenidos.
		createAdapter(this, sentences.getId_jugador(), sentences.getNombreApellidos(), sentences.getPosicion(), sentences.getDorsal());

		btn_OK.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(firstTime){
					// Esta seccion se utiliza si 'firstTime'=true, obtiene la ID de los 11 jugadores en pantalla seleccionados.
					if(playersIDSelected.size() == 11){
						int[] playersToUse = new int[playersIDSelected.size()];
						for (int i=0; i < playersToUse.length; i++){
							playersToUse[i] = playersIDSelected.get(i).intValue();
						}
				
						Intent data = new Intent();
						data.putExtra("players_selection", playersToUse);
						setResult(RESULT_OK, data);
						PopUpPlayersSelector.this.finish();
					}else{
						Toast.makeText(getApplicationContext(), getString(R.string.playingGround_popup_toast1), Toast.LENGTH_SHORT).show();
					}
				}else{
					// Esta seccion se utiliza si 'firstTime'=false, obtiene la ID de 1 jugador en pantalla seleccionados.
					if(playersIDSelected.size() == 1){
						Intent data = new Intent();
						data.putExtra("player_swap", playersIDSelected.get(0));
						setResult(RESULT_OK, data);
						PopUpPlayersSelector.this.finish();
					}else{
						Toast.makeText(getApplicationContext(), getString(R.string.playingGround_popup_toast2), Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
	}

	/**
	 * Crea el Adaptador para el GridView personalizado pasandole los datos de los jugadores a mostrar.
	 * @param activity : el activity en el cual sera mostrado.
	 * @param id : referencia al campo id_jugador. Se le pasa un Array de tipo Integer con los datos.
	 * @param names : referencia al campo Nombre del jugador. Se le pasa un Array de tipo String con los datos.
	 * @param types : referencia al campo tipo del jugador. Se le pasa un Array de tipo String con los datos.
	 * @param numbers : referencia al campo dorsal del jugador. Se le pasa un Array de tipo Integer con los datos.
	 */
	private void createAdapter(Activity activity, int[] id, String[] names, String[] types, int[] numbers){
		gridViewCustomeAdapter = new ArrayAdapterPlayersGrid(activity, id, names, types, numbers);

		gridView.setAdapter(gridViewCustomeAdapter);

		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?>  parent, View view, int position, long id) {
				if(firstTime){
					// Esta seccion se utiliza si 'firstTime'=true, permite marcar 11 jugadores en pantalla seleccionados con clic.
					if(playersPositionSelected.size() < 11){
						if(playersPositionSelected.contains(position)){
							view.setBackgroundResource(R.color.charcoal_A);
							playersPositionSelected.remove(playersPositionSelected.indexOf(position));
							playersIDSelected.remove(playersIDSelected.indexOf(view.getId()));
						}else{
							view.setBackgroundResource(R.color.blue_eyes);
							playersPositionSelected.add(position);
							playersIDSelected.add(view.getId());
						}
					}else{
						if(playersPositionSelected.contains(position)){
							view.setBackgroundResource(R.color.charcoal_A);
							playersPositionSelected.remove(playersPositionSelected.indexOf(position));
							playersIDSelected.remove(playersIDSelected.indexOf(view.getId()));
						}
					}
					
				}else{
					// Esta seccion se utiliza si 'firstTime'=false, permite marcar 1 jugador en pantalla seleccionado con clic.
					for (int j = 0; j < parent.getChildCount(); j++)
						parent.getChildAt(j).setBackgroundResource(R.color.charcoal_A);
						
					playersIDSelected.clear();
						
					if(lastPosition!=position)
						selected = false;
						
					lastPosition=position;
						
					if(!selected){
						view.setBackgroundResource(R.color.blue_eyes);
						selected = true;
						playersIDSelected.add(view.getId());
					}else{
						view.setBackgroundResource(R.color.charcoal_A);
						selected = false;
					}
				}
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.popup_menu_player_selector, menu);
		return true;
	}

	/**
	 * Este metodo permite la personalizacion de este activity para terminar de
	 * convertirlo, junto al uso de '@style/', en un Activity de menu o Pop-Up.
	 * Para conocer toda la informacion mirar tambien el archivo XML 'styles',
	 * en la carpeta values.
	 * 
	 * @param activity : le pasamos el activity actual para personalizarlo.
	 */
	public static void showAsPopup(Activity activity) {
		activity.requestWindowFeature(Window.FEATURE_ACTION_BAR); // Esta caracteristica habilita la barra (menu) en el Pop-Up.
		activity.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_DIM_BEHIND,
				WindowManager.LayoutParams.FLAG_DIM_BEHIND); // Marcamos activity para habilitar opciones diversas.
		LayoutParams params = activity.getWindow().getAttributes(); // Obtenemos objeto de configuracion del Activity.
		params.height = 680; // Adaptamos el tamaño en altura segun componentes del XML.
		params.width = 1100; // Fijamos el tamaño en anchura.
		params.alpha = 1.0f; // Podemos otorgarle transparencia al Pop-Up.
		params.dimAmount = 0.5f; // Fijamos el nivel de oscuridad para el activity de fondo.
		activity.getWindow().setAttributes(
				params); // Aplicamos los valores establecidos al Activity.
	}
	
}