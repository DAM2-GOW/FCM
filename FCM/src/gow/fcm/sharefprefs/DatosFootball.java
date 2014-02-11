package gow.fcm.sharefprefs;

import android.content.Context;
import android.content.SharedPreferences;

public class DatosFootball{
	
	private static String nombreDatos="Datos_equipo",varIdEquipo="id_equipo",varIdEntrenador="id_entrenador"; //Variables relacionadas con los datos de la aplicaci�n
	private static int idEquipo,idEntrenador; //Variable que almacenar�n los identificadores necesarios del equipo y entrenador para la aplicaci�n
	
	//M�todo que almacena el identificador del equipo
	public static int getIdEquipo(){
		return idEquipo;
	}
	
	//M�todo que almacena el identificador del entrenador
	public static int getIdEntrenador(){
		return idEntrenador;
	}
	
	//M�todo para llamar a las preferencias
	@SuppressWarnings("static-access")
	public static SharedPreferences getDatos(Context contexto){
		return contexto.getSharedPreferences(nombreDatos,contexto.MODE_PRIVATE);
	}
	
	//M�todo que obtiene el identificador del equipo y del entrenador
	public static void getDatosFootball(Context contexto){
		idEquipo=getDatos(contexto).getInt(varIdEquipo,0);
		idEntrenador=getDatos(contexto).getInt(varIdEntrenador,0);
	}
	
	//M�todo que asigna el identificador del equipo y del entrenador
	public static void setDatosFootball(Context contexto,int idTeam,int idCoach){
		getDatos(contexto).edit().putInt(varIdEquipo,idTeam).commit();
		getDatos(contexto).edit().putInt(varIdEntrenador,idCoach).commit();
	}
	
}