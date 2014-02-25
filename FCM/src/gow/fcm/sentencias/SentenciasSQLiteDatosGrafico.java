package gow.fcm.sentencias;

import gow.fcm.sharefprefs.DatosFootball;
import android.content.Context;
import android.util.Log;

public class SentenciasSQLiteDatosGrafico {
	
	static String datos;
	static String dia;
	static int id;
	
public static void setDatos(String dataS, int idj) {
		
		datos = dataS;
		id = idj;
	}
	
public static int[] getDatos() {
		
		return data;
	}

public static int[] getDias() {
	
	return dias;
}
	
	
private static int[] data, dias;

	//Este método obtiene los datos del jugador
		public static void getDatosJugador(Context contexto){
			
			//Reseteamos los valores antes de obtenerlos para obtener los valores adecuados para el método
			SentenciasSelectSQLite.borrarTodosValores();
			
			//Obtenemos el identificador
			
			
			//Ejcutamos la sentencia
			SentenciasSelectSQLite.seleccionarSQLite("Jugadores",new String[]{"COUNT(*)"},"id_jugador = "+id+"");
			
			//Obtenemos el número de valores
			String[] valores=(String[]) SentenciasSelectSQLite.getValores();
			String numValores=valores[0];
			
			data=new int[Integer.parseInt(numValores)];
			
			for(int i=0;i<Integer.parseInt(numValores);i++){
			
				//Ejcutamos la sentencia
				SentenciasSelectSQLite.seleccionarSQLite("Jugadores", new String[]{datos}, "id_jugador = "+id+"");
				//Almacenamos los valores
				valores=(String[]) SentenciasSelectSQLite.getValores();
				data[i]=Integer.parseInt(valores[0]);
			
			}
			
			dias=new int[Integer.parseInt(numValores)];
			
			for(int i=0;i<Integer.parseInt(numValores);i++){
			
				//Ejcutamos la sentencia
				SentenciasSelectSQLite.seleccionarSQLite("Partidos", new String[]{dia}, "id_jugador = "+id+"");
				//Almacenamos los valores
				valores=(String[]) SentenciasSelectSQLite.getValores();
				dias[i]=Integer.parseInt(valores[0]);
			
			}
		}
		
}
