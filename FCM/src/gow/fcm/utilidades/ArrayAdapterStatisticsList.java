package gow.fcm.utilidades;

import gow.fcm.footballcoachmanager.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ArrayAdapterStatisticsList extends ArrayAdapter<Object> {
  private final Context context; // Recoge el contexto en el que es lanzado.
  private final String[] statistic; // Recoge las estadisticas recibidas como array de String.
  
  /**
   * Este es el constructor de la clase personalizada.
   * Recibe los datos de nombre, numero y dato de cada estadistica.
   */
  public ArrayAdapterStatisticsList(Context context, String[] stat) {
    super(context, R.layout.template_item_player_grid);
    this.context = context;
    this.statistic = stat;
   
  }
  
  @SuppressLint("ResourceAsColor")
  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
	  // Generamos el servicio 'inflate' sobre el layout previamente creado
	  // de 'EstadisticasJugador' para poder obtener sus items y
	  // rellenarlos con informacion.
    LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View rowView = inflater.inflate(R.layout.template_item_statistic_list, parent, false);
    
    // Obtenemos los elementos de ese layout.
    TextView tv_statistic = (TextView) rowView.findViewById(R.id.statistic);
    
    //Rellenamos los elementos de ese layout con los elementos aportados
    // al llamar a esta clase.
    tv_statistic.setText(statistic[position]);
    
    return rowView;
  }
} 
