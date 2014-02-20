package gow.fcm.pantallas;

import gow.fcm.footballcoachmanager.R;
import gow.fcm.fragments.FragmentFinishEvent;
import gow.fcm.fragments.FragmentFinishEvent.OnClickFinishEvent;
import gow.fcm.fragments.FragmentGridPlayers;
import gow.fcm.fragments.FragmentGridPlayers.OnGridPlayersFragment;
import gow.fcm.fragments.FragmentPlayingGroundPlayersOptions;
import gow.fcm.fragments.FragmentPlayingGroundPlayersOptions.OnPlayerOptionClickEvent;
import gow.fcm.fragments.FragmentSelectStartingPlayers;
import gow.fcm.fragments.FragmentSelectStartingPlayers.OnSelectStartingPlayers;
import gow.fcm.popups.PopUpPlayersSelector;
import gow.fcm.sentencias.SentenciasInsertSQLite;
import gow.fcm.sentencias.SentenciasSQLitePlayingGround;
import gow.fcm.sharefprefs.DatosFootball;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentManager.OnBackStackChangedListener;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

public class PlayingGround extends Activity implements OnSelectStartingPlayers, OnGridPlayersFragment, OnClickFinishEvent, OnPlayerOptionClickEvent{
	FrameLayout flTopLeft;
	LinearLayout flBottom;
	FragmentGridPlayers fgp;
	FragmentSelectStartingPlayers fssp;
	SentenciasSQLitePlayingGround sentences;
	FragmentFinishEvent ffe;
	FragmentManager fm;
	FragmentTransaction ft;
	int lastPlayerID = -1;
	boolean entered = false; // Indica si se ha pulsado un jugador del grid de la lista de 11 jugadores.
	String[] dataSummaryToShow = new String[4]; // Almacena el texto a mostrar en el fragment final de resumen de opciones de jugador.
	private int lastDimensionShowed; // Indica la ultima dimension mostrada en los fragments de opciones de jugador.
	private int[] playersID; // Almacena una lista de jugadores seleccionados y mostrados en el grid de 11 jugadores.
	private int playerToBeChanged; // Almacena el jugador en el cual se quiere realizar un cambio.
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_playing_ground);
		
		flTopLeft = (FrameLayout) findViewById(R.id.FrameLayoutTopLeft);
		flBottom = (LinearLayout) findViewById(R.id.LinearLayoutBottom);
		
		fm = getFragmentManager();
		fssp = new FragmentSelectStartingPlayers();
		ffe = new FragmentFinishEvent();
		ft = fm.beginTransaction();
		ft.add(flTopLeft.getId(), fssp);
		ft.add(flBottom.getId(), ffe);
		ft.commit();
		fm.addOnBackStackChangedListener(new OnBackStackChangedListener() {
			
			@Override
			public void onBackStackChanged() {
				// Si BACKSTACK esta vacio entonces boton seleccionado de jugador sin pintar y 'Entered' false.
				if(fm.getBackStackEntryCount() == 0){
					Log.d("onBackStackChanged", "HA LLEGADO A 0");
					entered = false;
					lastPlayerID = -1;
				}
				
			}
		});
		
				
		/*******************************************
				
		// Se loguea un entrenador y equipo asociado.
		DatosFootball.setDatosFootball(this,1,1);
		
		// Se rellena la base de datos.
		String[] data_position = { "QB", "DL", "RB", "DL", "WR", "S", "RB", "QB", "WR", "S", "QB", "DL", "RB", "DL", "WR", "S", "RB", "QB", "WR", "S"};
		int[] data_type = { 0, 1, 2, 0, 1, 2, 0, 1, 2, 0, 1, 2, 0, 1, 2, 0, 1, 2, 0, 1};
		int[] data_num = { 8, 17, 45, 12, 54, 43, 57, 16, 45, 67, 28, 56, 21, 22, 23, 41, 42, 78, 87, 98};
		SentenciasInsertSQLite.insertarSQLite("Equipos",new String[]{"nombre"},new String[]{"Mismo"});
		SentenciasInsertSQLite.insertarSQLite("Entrenadores",new String[]{"id_equipo","nombre","apellidos","usuario","clave","pregunta_seguridad","respuesta_seguridad"},new String[]{"1","Pepe","García","pepe","1234","1","Chispi"});
		for(int i = 0;i < data_type.length;i++){
			SentenciasInsertSQLite.insertarSQLite("Jugadores",new String[]{"id_equipo","nombre","apellidos","edad","posicion","tipo","dorsal"},new String[]{"1","Nombre "+i,"Apellidos "+i,"22",data_position[i],String.valueOf(data_type[i]),String.valueOf(data_num[i])});
		}
		
		************************************************/
		sentences = new SentenciasSQLitePlayingGround(this); // Esto va arriba, pero por DEV se pone abajo.
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_playing_ground, menu);
		return true;
	}

	@Override
	public void onFinishEvent() {
		// Este metodo es llamado por la clase Fragment 'FinishEventFragment' cuando se
		// pulsa el boton de 'Finalizar Evento' dentro del partido.
		// Preguntara primero si deseamos acabar este evento y mostrara un resumen de lo sucedido.
		// Almacenara los datos en la DB.
		Toast.makeText(getApplicationContext(), "Finalizacion de evento", Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public void onClickPlayerSelectionEvent(int playerID) {
		// Este metodo es llamado por la clase Fragment 'GridPlayersFragment' cuando se
		// selecciona un jugador desde el GridView.
		// Permite la interaccion de los jugadores que estan en el terreno de juego.

		if(playerID==lastPlayerID && !entered){
			// Si el jugador seleccionado es igual al jugador seleccionado con anterioridad y no habias mostrado sus opciones, entonces lo borras todo y las creas.
				fm.popBackStack("playerOptions", FragmentManager.POP_BACK_STACK_INCLUSIVE); // Aqui se reinicia la pila de fragments.
				FragmentPlayingGroundPlayersOptions pgpo1 = new FragmentPlayingGroundPlayersOptions();
				Bundle args = new Bundle();
				args.putInt("playerID", playerID);
				args.putInt("lastDimension",0);
				args.putInt("comeFromRbtn",-1);
				args.putInt("branchType",-1);
				pgpo1.setArguments(args);
				ft = fm.beginTransaction();
				ft.setCustomAnimations(R.animator.slide_in, R.animator.slide_out); // Se le añade animacion ed entrada/salida.
				ft.replace(flBottom.getId(), pgpo1);
				ft.addToBackStack("playerOptions"); // Esto es la pila donde se almacenan los fragments añadidos.
				ft.commit();
				entered = true;
		}else if(playerID==lastPlayerID && entered){
			// Si el jugador seleccionado es igual al jugador seleccionado con anterioridad y ya habias mostrado sus opciones, entonces las borras(todo).
				fm.popBackStack("playerOptions", FragmentManager.POP_BACK_STACK_INCLUSIVE); // Aqui se reinicia la pila de fragments.
				entered = false;
			}

		if(playerID!=lastPlayerID){
			// Si el jugador seleccionado es distinto al jugador seleccionado con anterioridad, lo borras todo y muestras las opciones pertinenetes a ese nuevo jugador.
				fm.popBackStack("playerOptions", FragmentManager.POP_BACK_STACK_INCLUSIVE); // Aqui se reinicia la pila de fragments.
				FragmentPlayingGroundPlayersOptions pgpo1 = new FragmentPlayingGroundPlayersOptions();
				Bundle args = new Bundle();
				args.putInt("playerID", playerID);
				args.putInt("lastDimension",0);
				args.putInt("comeFromRbtn",-1);
				args.putInt("branchType",-1);
				pgpo1.setArguments(args);
				ft = fm.beginTransaction();
				ft.setCustomAnimations(R.animator.slide_in, R.animator.slide_out); // Se le añade animacion ed entrada/salida.
				ft.replace(flBottom.getId(), pgpo1);
				ft.addToBackStack("playerOptions"); // Esto es la pila donde se almacenan los fragments añadidos.
				ft.commit();
				entered = true;
			}
		
		lastPlayerID = playerID; // Almaceno el ultimo jugador seleccionado.
		int[] pid = new int[1];
		pid[0] = playerID;
		sentences.ObtenerDatosDelimitadosJugadores(this, pid); // Mandamos la orden de obtener los datos de los jugadores.
		String[] pna = sentences.getNombreApellidos();
		dataSummaryToShow[0] = pna[0]; // Se almacena el nombre del jugador que realiza la accion.

	}

	@Override
	public void onLongClickPlayerSelectionEvent(int playerID, int playersInUse[]) {
		// Este metodo es llamado por la clase Fragment 'GridPlayersFragment' cuando se
		// selecciona con Long clic un jugador desde el GridView.
		// Permite realizar cambios en el jugador seleccionado con Long clic.
		Intent i = new Intent(this, PopUpPlayersSelector.class);
		playerToBeChanged = playerID;
		i.putExtra("player_selected", playerID);
		i.putExtra("players_in_use", playersInUse);
		i.putExtra("first_time", false);
		startActivityForResult(i, 102);
	}

	@Override
	public void onClickOptionEvent(int rbtnIDSelected,int lastDimension, int branchType, String dataSelected) {
		// Este es llamado por la clase Fragment 'PlayingGroundPlayersOptions' cuando
		// se hace clic sobre un objeto que ofrece un dato sobre lo que esta realizando
		// el jugador y necesita ademas que inicie otro fragment para continuar la cadena.
		//Log.d("DEBUG", "DATASELECT: "+dataSelected+"; RBTN: "+rbtnIDSelected+"; DIMENSION: "+lastDimension+"; RAMA: "+branchType);
		//Log.d("BACKSTAB NUM",""+fm.getBackStackEntryCount());
		this.lastDimensionShowed = fm.getBackStackEntryCount(); // Se almacena la ultima posicion del fragment que se muestra (1,2,3,4).
		//Log.d("A BORRAR", "PETICION DESDE: "+lastDimension+"; ULTIMO MOSTRADO: "+lastDimensionShowed);
			for(int i = 4; i > 0; i--){ // Aqui se borran los BackStab superiores a "lastDimension".
				if(this.lastDimensionShowed > lastDimension){
					fm.popBackStackImmediate(i, FragmentManager.POP_BACK_STACK_INCLUSIVE);
					this.lastDimensionShowed = fm.getBackStackEntryCount(); 
				}
			}
		dataSummaryToShow[lastDimension] = dataSelected; // Se almacenan los datos recibidos para ser mostrados en resumen al final.
		FragmentPlayingGroundPlayersOptions pgpoX = new FragmentPlayingGroundPlayersOptions();
		Bundle args = new Bundle();
		args.putInt("lastDimension", lastDimension);
		args.putInt("comeFromRbtn", rbtnIDSelected);
		args.putInt("branchType", branchType);
		
		if((branchType==0 && lastDimension==2 && rbtnIDSelected==0) || (lastDimension==3) || (branchType==2  && lastDimension==2 && (rbtnIDSelected==0 || rbtnIDSelected==1 || rbtnIDSelected==2 || rbtnIDSelected==3)) || (branchType==1  && lastDimension==2 && rbtnIDSelected==1))
			// Si el fragment para mostrar fuera el ultimo se le pasa un parametro extra con lo seleccionado hasta el momento.
			args.putStringArray("dataToShow", dataSummaryToShow);
		
		if((branchType==0 && lastDimension==2 && rbtnIDSelected==3) || (branchType==1 && lastDimension==2 && rbtnIDSelected==2) || (branchType==2 && lastDimension==2 && rbtnIDSelected==4) || (branchType==0 && lastDimension==1 && rbtnIDSelected==2))
			// Si el fragment para mostrar tuviera seleccion de Jugador se le pasa un parametro extra con el array de jugadores en el campo.
			args.putIntArray("playersOnTheField", playersID);
		
		pgpoX.setArguments(args);
		ft = fm.beginTransaction();
		ft.setCustomAnimations(R.animator.fade_in, R.animator.fade_out); // Se le añade animacion de entrada/salida.
		ft.add(flBottom.getId(), pgpoX);
		ft.addToBackStack(lastDimension+"D"); // Esto es la pila donde se almacenan los fragments añadidos.
		ft.commit();
		Log.d("BACKSTAB NUM-AFTER",""+fm.getBackStackEntryCount());

	}

	@Override
	public void onClickFinalOptionEvent() {
		// Este metodo se ejecuta por la clase 'PlayingGroundPlayersOptions' cuando
		// se acepta lo mostrado en el resumen y almacena en la DB las diversas
		// opciones que se han ido seleccionando.
		Toast.makeText(getApplicationContext(), "Se almacenan los datos en la DB y se ejecutan las acciones", Toast.LENGTH_SHORT).show();
		fm.popBackStack("playerOptions", FragmentManager.POP_BACK_STACK_INCLUSIVE); // Aqui se reinicia la pila de fragments.
	}

	@Override
	public void onClickEvent() {
		// Este metodo es llamado por la clase Fragment 'SelectStartingPlayers' cuando
		// se hace clic en el cartel añadir los primeros 11 jugadores.
		Intent i = new Intent(this, PopUpPlayersSelector.class);
		i.putExtra("first_time", true);
		startActivityForResult(i, 101);
	}
	
	private void showGridPlayersFragment(int[] ID){
		fgp = new FragmentGridPlayers();
		Bundle args = new Bundle();
		args.putIntArray("players_selected", ID);
		fgp.setArguments(args);
		ft = fm.beginTransaction();
		ft.replace(flTopLeft.getId(), fgp);
		ft.commit();
	}

	/**
	 * Este metodo es llamado para realizar un cambio de jugador en
	 * el GridView.
	 * @param playerIDToChange : ID del jugador en la DB.
	 */
	private void OnPlayerChanged(int playerIDToChange){
		for(int i = 0; i < playersID.length; i++){
			// Comprueba cada uno de los strings almacenados.
			// Cuando detecta una coincidencia, cambia el valor del
			// jugador anterior por el nuevo jugador a cambiar.
			if(playersID[i]==playerToBeChanged){
				playersID[i] = playerIDToChange;
				break;
			}
		}
		playerToBeChanged = -1; // Se asigna un valor neutro una vez finalizado el intercambio.
		fgp.onSwapPlayer(playerIDToChange);
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// Este metodo es llamado automaticamente cuando iniciamos un activity nuevo
		// con un 'Intent' y el metodo 'startActivityForResult()', en este se reciben
		// los datos que nos devuelve el Activity al terminar su vida util.
		switch(requestCode){
		case 101:
			if(resultCode==Activity.RESULT_OK){
				playersID = data.getExtras().getIntArray("players_selection");
				showGridPlayersFragment(playersID);
			}else{
				Toast.makeText(getApplicationContext(), getString(R.string.playingGround_toast_no_player_selected), Toast.LENGTH_SHORT).show();
			}
			break;
		case 102:
			if(resultCode==Activity.RESULT_OK){
				OnPlayerChanged(data.getExtras().getInt("player_swap")); // Se obtiene el nuevo jugador a cambiar.
			}else{
				Toast.makeText(getApplicationContext(), getString(R.string.playingGround_toast_cancel_swap_player), Toast.LENGTH_SHORT).show();
			}
			break;
		
		default:
			break;
		}
	}
	
}