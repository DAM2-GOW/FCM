package gow.fcm.sentencias;

import gow.fcm.sharefprefs.DatosFootball;
import android.content.Context;
import android.util.Log;

public class SentenciasSQLiteNuevoEditarJugador {
	
private static String nameJugador,surnameJugador,ageJugador,positionJugador,typeJugador,numberJugador,fotoJugador;
	
	public static String getNameJugador() {
		return nameJugador;
	}

	public static String getSurnameJugador() {
		return surnameJugador;
	}

	public static String getAgeJugador() {
		return ageJugador;
	}

	public static String getPositionJugador() {
		return positionJugador;
	}

	public static String getTypeJugador() {
		return typeJugador;
	}

	public static String getNumberJugador() {
		return numberJugador;
	}

	public static String getFotoJugador() {
		return fotoJugador;
	}
	
	//Este método obtiene los datos del partido para editarlo
	public static int getNumDatosNuevoJugador(Context contexto,String dorsal){
		//Reseteamos los valores antes de obtenerlos para obtener los valores adecuados para el método
		SentenciasSelectSQLite.borrarTodosValores();

		//Obtenemos el identificador
		DatosFootball.getDatosFootball(contexto);
		int id=DatosFootball.getIdEquipo();
		
		//Ejcutamos la sentencia
		SentenciasSelectSQLite.seleccionarSQLite("Jugadores",new String[]{"COUNT(dorsal)"},"id_equipo="+id+" AND dorsal="+dorsal+"");
		String[] valores=SentenciasSelectSQLite.getValores();
		return Integer.parseInt(valores[0]);
	}
	
	//Este método obtiene los datos del partido para editarlo
	public static int getNumDatosEditarJugador(Context contexto,String dorsal){
		//Reseteamos los valores antes de obtenerlos para obtener los valores adecuados para el método
		SentenciasSelectSQLite.borrarTodosValores();

		//Obtenemos el identificador
		DatosFootball.getDatosFootball(contexto);
		int id=DatosFootball.getIdEquipo();
		
		//Ejcutamos la sentencia
		SentenciasSelectSQLite.seleccionarSQLite("Jugadores",new String[]{"id_jugador"},"id_equipo="+id+" AND dorsal="+dorsal+"");
		String[] valores=SentenciasSelectSQLite.getValores();
		String idJugador=valores[0];
		
		//Ejcutamos la sentencia
		SentenciasSelectSQLite.seleccionarSQLite("Jugadores",new String[]{"COUNT(dorsal)"},"id_jugador!="+idJugador+" AND id_equipo="+id+" AND dorsal="+dorsal+"");
		String[] valor=SentenciasSelectSQLite.getValores();
		return Integer.parseInt(valor[0]);
	}
	
	//Este método obtiene los datos del jugador
	public static void getDatosEditarJugador(Context contexto,String dorsal){
		//Reseteamos los valores antes de obtenerlos para obtener los valores adecuados para el método
		SentenciasSelectSQLite.borrarTodosValores();

		//Obtenemos el identificador
		DatosFootball.getDatosFootball(contexto);
		int id=DatosFootball.getIdEquipo();

		//Ejcutamos la sentencia
		SentenciasSelectSQLite.seleccionarSQLite("Jugadores",new String[]{"nombre","apellidos","edad","posicion","tipo","dorsal","foto"},"id_equipo="+id+" AND dorsal="+dorsal+"");

		//Obtenemos el número de valores
		String[] valores=(String[]) SentenciasSelectSQLite.getValores();
		nameJugador=valores[0];
		surnameJugador=valores[1];
		ageJugador=valores[2];
		positionJugador=valores[3];
		typeJugador=valores[4];
		numberJugador=valores[5];
		fotoJugador=valores[6];
	}
}