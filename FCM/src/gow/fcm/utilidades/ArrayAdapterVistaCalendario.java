package gow.fcm.utilidades;

import gow.fcm.footballcoachmanager.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ArrayAdapterVistaCalendario extends ArrayAdapter<String> {
  private final Context context; // Recoge el contexto en el que es lanzado.
  private final String[] name; // Recoge los titulos de entrenamiento o partido recibidos como array de String.
  private final String[] type; // Recoge los tipos de entrenamiento o el lugar de un partido recibidos como array de String.
  private final String[] hora; // Recoge las horas de cada evento recibido como String.
  private final String cosa; // Recoge el tipo de evento como String.
  /**
   * Este es el constructor de la clase personalizada.
   * Recibe los datos de nombre, tipo y numero de cada jugador.
   * @param context
   * @param name
   * @param type
   * @param num
   */
  public ArrayAdapterVistaCalendario(Context context, String[] name, String[] type, String[] hora, String cosa) {
    super(context, R.layout.list_items_detallecalendario, name);
    this.context = context;
    this.name = name;
    this.type = type;
    this.hora = hora;
    this.cosa = cosa;
  }

  @SuppressLint("ResourceAsColor")
@Override
  public View getView(int position, View convertView, ViewGroup parent) {
	  // Generamos el servicio 'inflate' sobre el layout previamente creado
	  // de 'list_items_players_list' para poder obtener sus items y
	  // rellenarlos con informacion.
    LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View rowView = inflater.inflate(R.layout.list_items_detallecalendario, parent, false);
    
    // Obtenemos los elementos de ese layout.
    TextView tv_player_name = (TextView) rowView.findViewById(R.id.textView_tituloeventocalendario);
    TextView tv_player_type = (TextView) rowView.findViewById(R.id.textView_tipoentrenamiento);
    TextView tv_player_hora = (TextView) rowView.findViewById(R.id.hora);
    TextView tv_player_cosa = (TextView) rowView.findViewById(R.id.textView_tipoevento);
    
    //Rellenamos los elementos de ese layout con los elementos aportados al llamar a esta clase
    tv_player_name.setText(name[position]);
    tv_player_hora.setText(context.getString(R.string.Vistadetallada_calendario_hora)+" "+hora[position]);
    tv_player_cosa.setText(cosa);
    
    //Actualizamos el valor según el tipo de evento que sea
    if(cosa.equals("Entrenamiento")){
        tv_player_type.setText(context.getString(R.string.Vistadetallada_calendario_tipo)+" "+type[position]);
    }else if(cosa.equals("Partido")){
        tv_player_type.setText(context.getString(R.string.Vistadetallada_calendario_rival)+" "+type[position]);
    }
    
    // Al ser una clase independiente, lo que nos interesa es obtener de ella datos,
    // ahora que han sido procesados las acciones oportunas devolvemos todos esos
    // datos que seran colocados en el layout principal.
    return rowView;
  }
  
  
} 
