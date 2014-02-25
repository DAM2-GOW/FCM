package gow.fcm.sentencias;

import gow.fcm.sharefprefs.DatosFootball;
import android.content.Context;

public class SentenciasSQLitePerfilEntrenador {

	private String nombreEntrenador, nombreEquipo;
	
	//Este método obtiene los datos del partido para editarlo
		public static void getDatosEditarPartido(Context contexto){
			//Reseteamos los valores antes de obtenerlos para obtener los valores adecuados para el método
			SentenciasSelectSQLite.borrarTodosValores();
			
			//Obtenemos el identificador
			DatosFootball.getDatosFootball(contexto);
			int id=DatosFootball.getIdEquipo();
			
			//Ejcutamos la sentencia
			//SentenciasSelectSQLite.seleccionarSQLite("Entrenadores",new String[]{"nombre","apellidos"},"id_equipo="+id+" AND dia='"+dia+"'");
			
			//Almacenamos los valores
			String[] valores=(String[]) SentenciasSelectSQLite.getValores();
		}
}