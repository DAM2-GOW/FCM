package gow.fcm.sentencias;

import gow.fcm.sharefprefs.DatosFootball;
import android.content.Context;

public class SentenciasSQLitePrincipal{
	
	//Variables de la aplicación o de la base de datos
	private static String nombreEntrenador=null,apellidosEntrenador=null,fotoEntrenador=null,nombreEquipo=null;
	
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
	
	//Este método obtiene los datos del entrenador
	public static void getDatosEntrenador(Context contexto){
		//Obtenemos el identificador
		DatosFootball.getDatosFootball(contexto);
		int id=DatosFootball.getIdEntrenador();
		
		//Ejcutamos la sentencia
		SentenciasSelectSQLite.seleccionarSQLite("Entrenadores",new String[]{"nombre","apellidos","foto"},"id_entrenador="+id+"");
		
		//Almacenamos los valores
		nombreEntrenador=(String) SentenciasSelectSQLite.getValor1();
		apellidosEntrenador=(String) SentenciasSelectSQLite.getValor2();
		fotoEntrenador=(String) SentenciasSelectSQLite.getValor3();
	}
	
	//Este método obtiene el nombre del equipo
	public static void getNombreEquipo(Context contexto){
		//Obtenemos el identificador
		DatosFootball.getDatosFootball(contexto);
		int id=DatosFootball.getIdEntrenador();
		
		//Ejcutamos la sentencia
		SentenciasSelectSQLite.seleccionarSQLite("Equipos",new String[]{"nombre"},"id_equipo="+id+"");
		
		//Almacenamos el valor
		nombreEquipo=(String) SentenciasSelectSQLite.getValor1();
	}
	
}