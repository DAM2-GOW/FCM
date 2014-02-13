package gow.fcm.utilidades;

import gow.fcm.footballcoachmanager.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ArrayAdapterStatsList extends ArrayAdapter<String> {
  private final Context context; // Recoge el contexto en el que es lanzado.
  private final String[] titulo; // Recoge los nombres recibidos como array de String.
  private final String[] type; // Recoge los tipos de jugador recibidos como array de String.
  private final String[] datoEspecial; // Recoge los numeros de cada jugador recibido como array de int.

  /**
   * Este es el constructor de la clase personalizada.
   * Recibe los datos de nombre, tipo y numero de cada jugador.
   * @param context
   * @param name
   * @param type
   * @param num
   */
  public ArrayAdapterStatsList(Context context, String[] titulo, String[] type, String[] datoEspecial) {
    super(context, R.layout.activity_config_entre, titulo);
    this.context = context;
    this.titulo = titulo;
    this.type = type;
    this.datoEspecial = datoEspecial;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
	  // Generamos el servicio 'inflate' sobre el layout previamente creado
	  // de 'list_items_players_list' para poder obtener sus items y
	  // rellenarlos con informacion.
    LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View rowView = inflater.inflate(R.layout.activity_config_entre, parent, false);
    
    // Obtenemos los elementos de ese layout.
    TextView tv_player_name = (TextView) rowView.findViewById(R.id.firstLine);
    TextView tv_player_type = (TextView) rowView.findViewById(R.id.secondLine);
    TextView tv_player_num = (TextView) rowView.findViewById(R.id.titulo);
    
    //Rellenamos los elementos de ese layout con los elementos aportados
    // al llamar a esta clase.
    tv_player_name.setText(titulo[position]);
    tv_player_type.setText(type[position]);
    tv_player_num.setText(datoEspecial[position]);
    
    // Al ser una clase independiente, lo que nos interesa es obtener de ella datos,
    // ahora que han sido procesados las acciones oportunas devolvemos todos esos
    // datos que seran colocados en el layout principal.
    return rowView;
  }
} 
