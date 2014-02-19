package gow.fcm.sentencias;

import gow.fcm.sharefprefs.DatosFootball;
import android.content.Context;

public class SentenciasSQLiteListaJugadores {
	
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

	private static String[] nombreJugador, apellidosJugador, posicionJugador,dorsalJugador;
	
	//Este método obtiene los datos del jugador
		public static void getDatosJugador(Context contexto){
			//Obtenemos el identificador
			DatosFootball.getDatosFootball(contexto);
			int id=DatosFootball.getIdEquipo();
			
			//Ejcutamos la sentencia
			SentenciasSelectSQLite.seleccionarSQLite("Jugadores",new String[]{"COUNT(*)"},"id_equipo="+id+"");
			
			//Obtenemos el número de valores
			String numValores=(String) SentenciasSelectSQLite.getValor1();
			
			//Asignamos el tamaño de los vectores
			nombreJugador=new String[Integer.parseInt(numValores)];
			apellidosJugador=new String[Integer.parseInt(numValores)];
			posicionJugador=new String[Integer.parseInt(numValores)];
			dorsalJugador=new String[Integer.parseInt(numValores)];
			
			for(int i=0;i<Integer.parseInt(numValores);i++){
				//Ejcutamos la sentencia
				SentenciasSelectSQLite.seleccionarSQLite("Jugadores",new String[]{"nombre","apellidos","posicion","dorsal"},"id_equipo="+id+" LIMIT 0,"+(i+1)+"");
				//Almacenamos los valores
				nombreJugador[i]=(String) SentenciasSelectSQLite.getValor1();
				apellidosJugador[i]=(String) SentenciasSelectSQLite.getValor2();
				posicionJugador[i]=(String) SentenciasSelectSQLite.getValor3();
				dorsalJugador[i]=(String) SentenciasSelectSQLite.getValor4();
			}
			
		}
		
		//Este método obtiene los datos del jugador
		public static void getDatosJugadorTipo(Context contexto,String tipo){
			//Obtenemos el identificador
			DatosFootball.getDatosFootball(contexto);
			int id=DatosFootball.getIdEquipo();

			//Ejcutamos la sentencia
			SentenciasSelectSQLite.seleccionarSQLite("Jugadores",new String[]{"COUNT(*)"},"id_equipo="+id+" AND tipo='"+tipo+"'");

			//Obtenemos el número de valores
			String numValores=(String) SentenciasSelectSQLite.getValor1();

			//Asignamos el tamaño de los vectores
			nombreJugador=new String[Integer.parseInt(numValores)];
			apellidosJugador=new String[Integer.parseInt(numValores)];
			posicionJugador=new String[Integer.parseInt(numValores)];
			dorsalJugador=new String[Integer.parseInt(numValores)];

			for(int i=0;i<Integer.parseInt(numValores);i++){
				//Ejcutamos la sentencia
				SentenciasSelectSQLite.seleccionarSQLite("Jugadores",new String[]{"nombre","apellidos","posicion","dorsal"},"id_equipo="+id+" AND tipo='"+tipo+"' LIMIT 0,"+(i+1)+"");
				//Almacenamos los valores
				nombreJugador[i]=(String) SentenciasSelectSQLite.getValor1();
				apellidosJugador[i]=(String) SentenciasSelectSQLite.getValor2();
				posicionJugador[i]=(String) SentenciasSelectSQLite.getValor3();
				dorsalJugador[i]=(String) SentenciasSelectSQLite.getValor4();
			}

		}
		
}
