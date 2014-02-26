package gow.fcm.sentencias;

import gow.fcm.sharefprefs.DatosFootball;
import android.content.Context;

public class SentenciasSQLitePerfilEntrenador {

	private static String nombreEntrenador, apellidosEntrenador, fotoEntrenador, nombreEquipo;
	
	//Obtenemos el nombre del entrenador
	public static String getNombreEntrenador(){
		return nombreEntrenador;
	}
	
	//Obtenemos los apellidos del entrenador
	public static String getApellidosEntrenador(){
		return apellidosEntrenador;
	}
	
	//Obtenemos la foto del entrenador
	public static String getFotoEntrenador(){
		return fotoEntrenador;
	}
	
	//Obtenemos el nombre del equipo
	public static String getNombreEquipo(){
		return nombreEquipo;
	}
	
	//Este método obtiene los datos del partido para editarlo
	public static void getDatosPerfilEntrenador(Context contexto){
		//Reseteamos los valores antes de obtenerlos para obtener los valores adecuados para el método
		SentenciasSelectSQLite.borrarTodosValores();
		
		//Obtenemos los identificadores
		DatosFootball.getDatosFootball(contexto);
		int id_entrenador=DatosFootball.getIdEntrenador();
		int id_equipo=DatosFootball.getIdEquipo();
		
		//Ejcutamos la sentencia
		SentenciasSelectSQLite.seleccionarSQLite("Entrenadores",new String[]{"nombre","apellidos","foto"},"id_entrenador="+id_entrenador+"");
		
		//Almacenamos los valores
		String[] valores=(String[]) SentenciasSelectSQLite.getValores();
		nombreEntrenador=valores[0];
		apellidosEntrenador=valores[1];
		fotoEntrenador=valores[2];
		
		//Ejcutamos la sentencia
		SentenciasSelectSQLite.seleccionarSQLite("Equipos",new String[]{"nombre"},"id_equipo="+id_equipo+"");
		
		//Almacenamos los valores
		String[] valor=(String[]) SentenciasSelectSQLite.getValores();
		nombreEquipo=valor[0];
	}
}