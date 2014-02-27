package gow.fcm.sentencias;

import android.content.Context;
import gow.fcm.sharefprefs.DatosFootball;

public class SentenciasSQLiteObservaciones{
	
	private static String observaciones;
	
	public static String getObservaciones(){
		return observaciones;
	}
	
	public static void obtenerObservaciones(Context contexto,String posicion,String dia) {
		//Reseteamos los valores antes de obtenerlos para obtener los valores adecuados para el método
		SentenciasSelectSQLite.borrarTodosValores();
		
		//Obtenemos el identificador
		DatosFootball.getDatosFootball(contexto);
		int id=DatosFootball.getIdEquipo();
		
		SentenciasSelectSQLite.seleccionarSQLite("Entrenamientos",new String[]{"observaciones"},"id_equipo="+id+" AND dia='"+dia+"' LIMIT "+posicion+",1");
		String[] valores=SentenciasSelectSQLite.getValores();
		observaciones=valores[0];
	}
}