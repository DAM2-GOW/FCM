package gow.fcm.sentencias;

import gow.fcm.sharefprefs.DatosFootball;
import android.content.Context;

public class SentenciasSQLiteNuevoEditarPartido{
	
	private static String lugarPartido,rivalPartido,fechaPartido;
	
	//Obtenemos el lugar del partido
	public static String getLugar(){
		return lugarPartido;
	}
	
	//Obtenemos el rival del partido
	public static String geRival(){
		return rivalPartido;
	}
	
	//Obtenemos la fecha del partido
	public static String getFecha(){
		return fechaPartido;
	}
	
	//Este método obtiene los datos del partido para editarlo
	public static void getDatosEditarPartido(Context contexto,String dia){
		//Reseteamos los valores antes de obtenerlos para obtener los valores adecuados para el método
		SentenciasSelectSQLite.borrarTodosValores();

		//Obtenemos el identificador
		DatosFootball.getDatosFootball(contexto);
		int id=DatosFootball.getIdEquipo();

		//Ejcutamos la sentencia
		SentenciasSelectSQLite.seleccionarSQLite("Partido",new String[]{"lugar","rival","fecha"},"id_equipo="+id+" AND dia='"+dia+"'");

		//Almacenamos los valores
		String[] valores=(String[]) SentenciasSelectSQLite.getValores();
		lugarPartido=valores[0];
		rivalPartido=valores[1];
		fechaPartido=valores[2];
	}
	
}