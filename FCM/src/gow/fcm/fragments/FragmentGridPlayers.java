package gow.fcm.fragments;


import gow.fcm.footballcoachmanager.R;
import gow.fcm.sentencias.SentenciasSQLitePlayingGround;
import gow.fcm.utilidades.ArrayAdapterPlayersGrid;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;

public class FragmentGridPlayers extends Fragment {
	private GridView gridView;
	private ArrayAdapterPlayersGrid gridViewCustomeAdapter;
	private OnGridPlayersFragment mCallback;
	private boolean selected;
	private int lastPosition;
	private int[] playersInTheGround;
	SentenciasSQLitePlayingGround sentences = new SentenciasSQLitePlayingGround();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_grid_players, container,
				false);
		selected = false;
		gridView = (GridView) view.findViewById(R.id.gridView1);
		Bundle bundle = this.getArguments(); // Aqui obtenemos el objeto que trae los adjuntos al crear esta clase.
		playersInTheGround = bundle.getIntArray("players_selected");
		sentences.ObtenerDatosDelimitadosJugadores(getActivity(), playersInTheGround); // Mandamos la orden de obtener los datos de los jugadores.
		// Se crea el adaptador para mostrar los datos.
		createAdapter(getActivity(), sentences.getId_jugador(), sentences.getNombreApellidos(), sentences.getPosicion(), sentences.getDorsal());

		return view;
	}

	/**
	 * 
	 * @param activity
	 * @param names
	 * @param types
	 * @param numbers
	 */
	private void createAdapter(Activity activity, int[] id, String[] names, String[] position, int[] numbers){
		gridViewCustomeAdapter = new ArrayAdapterPlayersGrid(activity, id, names, position, numbers);

		gridView.setAdapter(gridViewCustomeAdapter);
		
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?>  parent, View view,
			          int position, long id) {

				for (int j = 0; j < parent.getChildCount(); j++)
					parent.getChildAt(j).setBackgroundResource(R.color.charcoal_A);
				
				if(lastPosition!=position)
					selected = false;
				
				lastPosition=position;
				
				if(!selected){
					view.setBackgroundResource(R.color.green_peas);
					selected = true;
				}else{
					view.setBackgroundResource(R.color.charcoal_A);
					selected = false;
				}
				mCallback.onClickPlayerSelectionEvent(view.getId());
			}
		});
		
		gridView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?>  parent, View view,
			          int position, long id) {
				lastPosition = position;
				// Aqui hay que controlar la seleccion ANTERIOR de OnItemClic.
				// Por si se selecciona un item y luego sin deseleccionarlo se desea
				// cambiar a un jugador con OnItemLongClic.
				for (int j = 0; j < parent.getChildCount(); j++)
					parent.getChildAt(j).setBackgroundResource(R.color.charcoal_A);
				
				view.setBackgroundResource(R.color.blue_eyes);
				mCallback.onLongClickPlayerSelectionEvent(view.getId(), playersInTheGround);
				view.setBackgroundResource(R.color.charcoal_A);
				return true;
			}
		});
	}

	/**
	 * Este metodo es llamado cuando se necesita realizar un cambio en
	 * un jugador de los mostrados en el GridView.
	 * @param player
	 */
	public void onSwapPlayer(int playerID){
		int[] pid = new int[1];
		pid[0] = playerID;
		sentences.ObtenerDatosDelimitadosJugadores(getActivity(), pid); // Mandamos la orden de obtener los datos de los jugadores.
		gridViewCustomeAdapter.onItemUpdate(this.lastPosition, playerID, sentences.getNombreApellidos(), sentences.getPosicion(), sentences.getDorsal());
		this.playersInTheGround[this.lastPosition] = playerID;
	}

	public interface OnGridPlayersFragment {
		public void onClickPlayerSelectionEvent(int playerID);
		public void onLongClickPlayerSelectionEvent(int playerID, int[] playersInUse);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mCallback = (OnGridPlayersFragment) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " debe implementar la clase OnGridPlayersFragment");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mCallback = null;
	}

}