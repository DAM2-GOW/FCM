 package gow.fcm.utilidades;

import gow.fcm.footballcoachmanager.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ArrayAdapterPlayersGrid extends ArrayAdapter<Object> {
  private final Context context; // Recoge el contexto en el que es lanzado.
  private final String[] name; // Recoge los nombres recibidos como array de String.
  private final String[] position; // Recoge las posiciones de jugador recibidas como array de String.
  private final int[] num; // Recoge los numeros de cada jugador recibido como array de int.
  private final int[] id;
  
  /**
   * Este es el constructor de la clase personalizada.
   * Recibe los datos de nombre, tipo y numero de cada jugador.
   * @param context
   * @param name
   * @param position
   * @param num
   */
  public ArrayAdapterPlayersGrid(Context context,int[] id, String[] name, String[] position, int[] num) {
    super(context, R.layout.template_item_player_grid, name);
    this.context = context;
    this.name = name;
    this.position = position;
    this.num = num;
    this.id = id;
  }
  
  @SuppressLint("ResourceAsColor")
  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
	  // Generamos el servicio 'inflate' sobre el layout previamente creado
	  // de 'list_items_players_list' para poder obtener sus items y
	  // rellenarlos con informacion.
    LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View rowView = inflater.inflate(R.layout.template_item_player_grid, parent, false);
    
    // Obtenemos los elementos de ese layout.
    TextView tv_player_name = (TextView) rowView.findViewById(R.id.firstLine);
    TextView tv_player_position = (TextView) rowView.findViewById(R.id.secondLine);
    TextView tv_player_num = (TextView) rowView.findViewById(R.id.player_num);
    
    //Rellenamos los elementos de ese layout con los elementos aportados
    // al llamar a esta clase.
    tv_player_name.setText(name[position]);
    tv_player_position.setText(context.getString(R.string.playingGround_template_position)+" "+this.position[position]);
    tv_player_num.setText(Integer.toString(num[position]));
    
    // Aqui se le determina un ID a cada VIEW por jugador mostrado. Este ID
    // sera la clave principal asociada al Jugador.
    rowView.setId(id[position]);
    
    // Al ser una clase independiente, lo que nos interesa es obtener de ella datos,
    // ahora que han sido procesados las acciones oportunas devolvemos todos esos
    // datos que seran colocados en el layout principal.
    
    return rowView;
  }

  public void onItemUpdate(int lastPosition, int playerID, String[] data_name, String[] data_position, int[] data_dorsal) {
	// Se le cambia el valor ID del View del anterior PlayerID al nuevo Player cambiado.
	this.name[lastPosition] = data_name[0];
	this.position[lastPosition] = data_position[0];
	this.num[lastPosition] = data_dorsal[0];
	this.id[lastPosition] = playerID;
	notifyDataSetChanged();
  }
} 
