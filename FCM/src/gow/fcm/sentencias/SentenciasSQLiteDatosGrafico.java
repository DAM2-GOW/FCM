package gow.fcm.sentencias;

import gow.fcm.sharefprefs.DatosFootball;
import android.content.Context;

public class SentenciasSQLiteDatosGrafico{
	
	private static int[] datos;
	private static String[] dias;
	
	public static int[] getDatos(){
		return datos;
	}
	
	public static String[] getDias(){
		return dias;
	}
	
	public static void getDatosGrafico(Context contexto,String dorsal,String dato){
		//Reseteamos los valores antes de obtenerlos para obtener los valores adecuados para el método
		SentenciasSelectSQLite.borrarTodosValores();
		
		//Obtenemos el identificador
		DatosFootball.getDatosFootball(contexto);
		int idEquipo=DatosFootball.getIdEquipo();
		
		//Ejcutamos la sentencia
		SentenciasSelectSQLite.seleccionarSQLite("Jugadores",new String[]{"id_jugador"},"id_equipo="+idEquipo+" AND dorsal='"+dorsal+"'");
		
		//Obtenemos el número de valores
		String[] valor=SentenciasSelectSQLite.getValores();
		String idJugador=valor[0];
		
		//Ejcutamos la sentencia
		SentenciasSelectSQLite.seleccionarSQLite("Estadisticas_Partidos",new String[]{"COUNT("+dato+")"},"id_jugador="+idJugador+"");
		
		//Obtenemos el número de valores
		String[] valor2=SentenciasSelectSQLite.getValores();
		String numPartidos=valor2[0];
		
		datos=new int[Integer.parseInt(numPartidos)];
		
		for(int i=0;i<datos.length;i++){
			SentenciasSelectSQLite.seleccionarSQLite("Estadisticas_Partidos",new String[]{dato},"id_jugador="+idJugador+" LIMIT "+i+",1");
			String[] valores=(String[]) SentenciasSelectSQLite.getValores();
			datos[i]=Integer.parseInt(valores[0]);
		}
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