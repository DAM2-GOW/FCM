package gow.fcm.sentencias;

import gow.fcm.sharefprefs.DatosFootball;
import android.content.Context;

public class SentenciasSQLiteDatosGrafico{
	
	private static String[] dias;
	
	public static String[] getDias(){
		return dias;
	}
	
	//Este método obtiene los datos del jugador
	public static void getDiasGrafico(Context contexto){
		
		//Reseteamos los valores antes de obtenerlos para obtener los valores adecuados para el método
		SentenciasSelectSQLite.borrarTodosValores();
		
		//Obtenemos el identificador
		DatosFootball.getDatosFootball(contexto);
		int idEquipo=DatosFootball.getIdEquipo();
		
		//Ejcutamos la sentencia
		SentenciasSelectSQLite.seleccionarSQLite("Partidos",new String[]{"COUNT(*)"},"id_equipo="+idEquipo+"");
		
		//Obtenemos el número de valores
		String[] valor=SentenciasSelectSQLite.getValores();
		String numPartidos=valor[0];
		
		dias=new String[Integer.parseInt(numPartidos)];
		
		for(int i=0;i<dias.length;i++){
			SentenciasSelectSQLite.seleccionarSQLite("Partidos",new String[]{"dia"},"id_equipo="+idEquipo+" LIMIT "+i+",1");
			String[] valores=(String[]) SentenciasSelectSQLite.getValores();
			dias[i]=valores[0];
		}
	}
	
}