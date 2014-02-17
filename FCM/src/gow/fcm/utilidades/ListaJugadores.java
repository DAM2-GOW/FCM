package gow.fcm.utilidades;

import gow.fcm.footballcoachmanager.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListaJugadores extends ArrayAdapter<String>{
	private final Context contexto; //Recoge el contexto en el que es lanzado
	private final String[] dorsal; //Recoge el dorsal de cada jugador recibido como array de int
	private final String[] name; //Recoge los nombres recibidos como array de String
	private final String[] surname; //Recoge los apellidos recibidos como array de String
	private final String[] position; //Recoge las posiciones de jugador recibidos como array de String
	
	//Este es el constructor de la clase que recibe el nombre, los apellidos, la posición y dorsal de cada jugador
	public ListaJugadores(Context contexto,String[] dorsal,String[] name,String[] surname,String[] position){
		super(contexto, R.layout.item_lista_jugadores, name);
		this.dorsal=dorsal;
		this.contexto=contexto;
		this.name=name;
		this.surname=surname;
		this.position=position;
	}
	
	@Override
	public View getView(int posicion, View convertView, ViewGroup parent){
		//Generamos el servicio inflate sobre el layout previamente creado
		//Obtenemos sus items y rellenamos el layout
		LayoutInflater inflater=(LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView=inflater.inflate(R.layout.item_lista_jugadores, parent, false);
		
		//Obtenemos los elementos del layout
		TextView dorsalJugador=(TextView) rowView.findViewById(R.id.dorsal_player_lista);
		TextView nombreJugador=(TextView) rowView.findViewById(R.id.nombre_player_lista);
		TextView apellidosJugador=(TextView) rowView.findViewById(R.id.apellidos_player_lista);
		TextView posicionJugador=(TextView) rowView.findViewById(R.id.posicion_player_lista);
		
		//Rellenamos el layout con los elementos aportados al llamar a la clase
		dorsalJugador.setText(dorsal[posicion]);
		nombreJugador.setText(name[posicion]);
		apellidosJugador.setText(" "+surname[posicion]);
		posicionJugador.setText(" "+position[posicion]);
		
		//Devolvemos los datos que serán colocados en el layout principal
		return rowView;
	}
	
}