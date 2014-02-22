package gow.fcm.sentencias;

import gow.fcm.sharefprefs.DatosFootball;
import android.content.Context;

public class SentenciasSQLitePrincipal{
	
	//Variables de la aplicación o de la base de datos
	private static String nombreEntrenador=null,apellidosEntrenador=null,fotoEntrenador=null,nombreEquipo=null,numPartidos;
	
	//Obtenemos la foto del entrenador
	public static String getFotoEntrenador(){
		return fotoEntrenador;
	}
	
	//Resetea la foto del entrenador
	public static void setFotoEntrenador(){
		fotoEntrenador=null;
	}
	
	//Obtenemos el nombre del entrenador
	public static String getNombreEntrenador(){
		return nombreEntrenador;
	}
	
	//Resetea el nombre del entrenador
	public static void setNombreEntrenador(){
		nombreEntrenador=null;
	}
	
	//Obtenemos los apellidos del entrenador
	public static String getApellidosEntrenador(){
		return apellidosEntrenador;
	}
	
	//Resetea los apellidos del entrenador
	public static void setApellidosEntrenador(){
		apellidosEntrenador=null;
	}
	
	//Obtenemos el nombre del equipo
	public static String getNombreEquipo(){
		return nombreEquipo;
	}
	
	//Resetea el nombre del equipo
	public static void setNombreEquipo(){
		nombreEquipo=null;
	}
	
	//Obtenemos el número de partidos
	public static String getNumPartidos(){
		return numPartidos;
	}
	
	//Este método obtiene los datos del entrenador
	public static void getDatosEntrenador(Context contexto){
		//Reseteamos los valores antes de obtenerlos para obtener los valores adecuados para el método
		SentenciasSelectSQLite.borrarTodosValores();
		
		//Obtenemos el identificador
		DatosFootball.getDatosFootball(contexto);
		int id=DatosFootball.getIdEntrenador();
		
		//Ejcutamos la sentencia
		SentenciasSelectSQLite.seleccionarSQLite("Entrenadores",new String[]{"nombre","apellidos","foto"},"id_entrenador="+id+"");
		
		//Almacenamos los valores
		String[] valores=SentenciasSelectSQLite.getValores();
		nombreEntrenador=valores[0];
		apellidosEntrenador=valores[1];
		fotoEntrenador=valores[2];
	}
	
	//Este método obtiene el nombre del equipo
	public static void getNombreEquipo(Context contexto){
		//Reseteamos los valores antes de obtenerlos para obtener los valores adecuados para el método
		SentenciasSelectSQLite.borrarTodosValores();
		
		//Obtenemos el identificador
		DatosFootball.getDatosFootball(contexto);
		int id=DatosFootball.getIdEntrenador();
		
		//Ejcutamos la sentencia
		SentenciasSelectSQLite.seleccionarSQLite("Equipos",new String[]{"nombre"},"id_equipo="+id+"");
		
		//Almacenamos el valor
		String[] valores=SentenciasSelectSQLite.getValores();
		nombreEquipo=valores[0];
	}
	
	public static void numeroPartidos(Context contexto,String fecha){
		//Reseteamos los valores antes de obtenerlos para obtener los valores adecuados para el método
		SentenciasSelectSQLite.borrarTodosValores();

		//Obtenemos el identificador
		DatosFootball.getDatosFootball(contexto);
		int id=DatosFootball.getIdEquipo();

		//Ejcutamos la sentencia
		SentenciasSelectSQLite.seleccionarSQLite("Partidos",new String[]{"COUNT(*)"},"id_equipo="+id+" AND dia='"+fecha+"'");

		//Almacenamos los valores
		String[] valores=SentenciasSelectSQLite.getValores();
		numPartidos=valores[0];
	}
}