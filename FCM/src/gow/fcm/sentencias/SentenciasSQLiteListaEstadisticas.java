package gow.fcm.sentencias;

import gow.fcm.sharefprefs.DatosFootball;
import android.content.Context;
import android.util.Log;

public class SentenciasSQLiteListaEstadisticas {
	
	public static String getCarreras() {
		return carreras;
	}
	
	public static String getPases() {
		return pases_completados;
	}
	
	public static String getRecepcion() {
		return recepciones;
	}
	
	public static String getFumble() {
		return fumbles;
	}
	
	public static String getSack() {
		return sacks;
	}
	
	public static String getPlacaje() {
		return placajes;
	}
	
	public static String getIntercepcion() {
		return intercepciones;
	}
	
	public static String getFieldgoals() {
		return field_goals;
	}
	
	public static String getPunts() {
		return punts;
	}
	
	public static String getTouchdowns() {
		return touchdowns;
	}
	
	public static String getFaltas() {
		return faltas;
	}
	
	public static String getExtrapoint() {
		return extra_points_completados;
	}
	
	public static String getPtconversion() {
		return pt_conversions_completados;
	}
	
	

	
	private static String carreras, pases_completados, recepciones, fumbles, sacks, placajes, intercepciones, field_goals, punts, touchdowns, faltas, extra_points_completados, pt_conversions_completados;
	
	//Este método obtiene los datos del jugador
		public static void getDatosJugador(Context contexto){
			//Reseteamos los valores antes de obtenerlos para obtener los valores adecuados para el método
			SentenciasSelectSQLite.borrarTodosValores();
			
			//Obtenemos el identificador
			
			
			//Ejcutamos la sentencia
			SentenciasSelectSQLite.seleccionarSQLite("Estadisticas_Partidos",new String[]{"COUNT(*)"},"id_jugador = 1");
			
			//Obtenemos el número de valores
			String[] valores;
			
				//Ejcutamos la sentencia
				SentenciasSelectSQLite.seleccionarSQLite("Estadisticas_Partidos", new String[]{"carreras","pases_completados","recepciones","fumbles","sacks","placajes","intercepciones","field_goals","punts","touchdowns","faltas","extra_points_completados","2pt_conversions_completados"}, "id_jugador = 1");
				//Almacenamos los valores
				valores=(String[]) SentenciasSelectSQLite.getValores();
				carreras = valores[0];
				pases_completados = valores[1];
				recepciones=valores[2];
				fumbles=valores[3];
				sacks=valores[4];
				placajes=valores[5];
				intercepciones=valores[6];
				field_goals=valores[7];
				punts=valores[8];
				touchdowns=valores[9];
				faltas=valores[10];
				extra_points_completados=valores[11];
				pt_conversions_completados=valores[12];
				
			}
		
}
