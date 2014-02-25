package gow.fcm.sentencias;

import gow.fcm.sharefprefs.DatosFootball;
import android.content.Context;

public class SentenciasSQLiteListaJugadores {
	
	private static String[] nombreJugador, apellidosJugador, posicionJugador,dorsalJugador;
	
	public static String[] getNombreJugador() {
		return nombreJugador;
	}

	public static String[] getApellidosJugador() {
		return apellidosJugador;
	}

	public static String[] getPosicionJugador() {
		return posicionJugador;
	}

	public static String[] getDorsalJugador() {
		return dorsalJugador;
	}
	
		//Este método obtiene los datos del jugador
		public static void getDatosJugador(Context contexto){
			//Reseteamos los valores antes de obtenerlos para obtener los valores adecuados para el método
			SentenciasSelectSQLite.borrarTodosValores();
			
			//Obtenemos el identificador
			DatosFootball.getDatosFootball(contexto);
			int id=DatosFootball.getIdEquipo();
			
			//Ejcutamos la sentencia
			SentenciasSelectSQLite.seleccionarSQLite("Jugadores",new String[]{"COUNT(*)"},"id_equipo="+id+"");
			
			//Obtenemos el número de valores
			String[] valores=(String[]) SentenciasSelectSQLite.getValores();
			String numValores=valores[0];
			
			//Asignamos el tamaño de los vectores
			nombreJugador=new String[Integer.parseInt(numValores)];
			apellidosJugador=new String[Integer.parseInt(numValores)];
			posicionJugador=new String[Integer.parseInt(numValores)];
			dorsalJugador=new String[Integer.parseInt(numValores)];
			
			for(int i=0;i<Integer.parseInt(numValores);i++){
				//Ejcutamos la sentencia
				SentenciasSelectSQLite.seleccionarSQLite("Jugadores",new String[]{"nombre","apellidos","posicion","dorsal"},"id_equipo="+id+" ORDER BY dorsal LIMIT "+(i)+",1");
				//Almacenamos los valores
				valores=(String[]) SentenciasSelectSQLite.getValores();
				nombreJugador[i]=valores[0];
				apellidosJugador[i]=valores[1];
				posicionJugador[i]=valores[2];
				dorsalJugador[i]=valores[3];
			}
		}
		
		//Este método obtiene los datos del jugador
		public static void getDatosJugadorTipo(Context contexto,String tipo){
			//Reseteamos los valores antes de obtenerlos para obtener los valores adecuados para el método
			SentenciasSelectSQLite.borrarTodosValores();
			
			//Obtenemos el identificador
			DatosFootball.getDatosFootball(contexto);
			int id=DatosFootball.getIdEquipo();

			//Ejcutamos la sentencia
			SentenciasSelectSQLite.seleccionarSQLite("Jugadores",new String[]{"COUNT(*)"},"id_equipo="+id+" AND tipo='"+tipo+"'");

			//Obtenemos el número de valores
			String[] valores=(String[]) SentenciasSelectSQLite.getValores();
			String numValores=valores[0];

			//Asignamos el tamaño de los vectores
			nombreJugador=new String[Integer.parseInt(numValores)];
			apellidosJugador=new String[Integer.parseInt(numValores)];
			posicionJugador=new String[Integer.parseInt(numValores)];
			dorsalJugador=new String[Integer.parseInt(numValores)];

			for(int i=0;i<Integer.parseInt(numValores);i++){
				//Ejcutamos la sentencia
				SentenciasSelectSQLite.seleccionarSQLite("Jugadores",new String[]{"nombre","apellidos","posicion","dorsal"},"id_equipo="+id+" AND tipo='"+tipo+"' ORDER BY dorsal LIMIT "+(i)+",1");
				//Almacenamos los valores
				valores=(String[]) SentenciasSelectSQLite.getValores();
				nombreJugador[i]=valores[0];
				apellidosJugador[i]=valores[1];
				posicionJugador[i]=valores[2];
				dorsalJugador[i]=valores[3];
			}
		}
		
		public static void borrarJugador(Context contexto,int dorsal){
			//Reseteamos los valores antes de obtenerlos para obtener los valores adecuados para el método
			SentenciasSelectSQLite.borrarTodosValores();
			
			//Obtenemos el identificador
			DatosFootball.getDatosFootball(contexto);
			int id=DatosFootball.getIdEquipo();
			
			//Ejcutamos la sentencia
			SentenciasSelectSQLite.seleccionarSQLite("Jugadores",new String[]{"id_jugador"},"id_equipo="+id+" AND dorsal='"+dorsal+"'");
			
			//Almacenamos los valores
			String[] valores=(String[]) SentenciasSelectSQLite.getValores();
			String idJugador=valores[0];
			
			//Ejcutamos la sentencia
			SentenciasDeleteSQLite.borrarSQLite("Jugadores","id_jugador="+idJugador+"");
		}
}
