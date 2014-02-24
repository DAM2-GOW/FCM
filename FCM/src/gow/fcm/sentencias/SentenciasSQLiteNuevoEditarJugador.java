package gow.fcm.sentencias;

import gow.fcm.sharefprefs.DatosFootball;
import android.content.Context;

public class SentenciasSQLiteNuevoEditarJugador {

	//Este método obtiene los datos del partido para editarlo
		public static int getDatosEditarPartido(Context contexto,String dorsal){
			//Reseteamos los valores antes de obtenerlos para obtener los valores adecuados para el método
			SentenciasSelectSQLite.borrarTodosValores();
			
			//Obtenemos el identificador
			DatosFootball.getDatosFootball(contexto);
			int id=DatosFootball.getIdEquipo();
			
			//Ejcutamos la sentencia
			SentenciasSelectSQLite.seleccionarSQLite("Jugadores",new String[]{"COUNT(dorsal)"},"id_equipo="+id+" AND dorsal="+dorsal+"");
			String[] valores = SentenciasSelectSQLite.getValores();
			return Integer.parseInt(valores[0]);
		}
}

