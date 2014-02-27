package gow.fcm.sentencias;

import gow.fcm.sharefprefs.DatosFootball;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.annotation.SuppressLint;
import android.content.Context;

public class SentenciasSQLiteCalendario{
	
	//Variables de la aplicación o de la base de datos
	private static String totalEntrenamiento,totalPartido;
	private static Date fechaEntrenamiento,fechaPartido; //Variables para usarlas en las sentencias
	@SuppressLint("SimpleDateFormat")
	private static SimpleDateFormat formatoTexto=new SimpleDateFormat("yyyy-MM-dd"); //Formato de conversión a Date
	
	//Obtenemos el total de entrenamientos
	public static String getTotalEntrenamiento(){
		return totalEntrenamiento;
	}
	
	//Obtenemos el total de partidos
	public static String getTotalPartido(){
		return totalPartido;
	}
	
	//Obtenemos la fecha del entrenamiento
	public static Date getFechaEntrenamiento(){
		return fechaEntrenamiento;
	}
	
	//Obtenemos la fecha del partido
	public static Date getFechaPartido(){
		return fechaPartido;
	}
	
	//Este método obtiene los datos del entrenamiento
	public static void getDatosEntrenamientos(Context contexto,String fecha){
		//Reseteamos los valores antes de obtenerlos para obtener los valores adecuados para el método
		SentenciasSelectSQLite.borrarTodosValores();
		
		//Obtenemos el identificador
		DatosFootball.getDatosFootball(contexto);
		int id=DatosFootball.getIdEquipo();
		
		//Ejcutamos la sentencia
		SentenciasSelectSQLite.seleccionarSQLite("Entrenamientos",new String[]{"COUNT(*)","fecha"},"id_equipo="+id+" AND dia='"+fecha+"'");
		
		//Almacenamos los valores
		String[] valores=SentenciasSelectSQLite.getValores();
		totalEntrenamiento=valores[0];
		String fechaTraining=valores[1];
		if(fechaTraining!=null){
			try{
				fechaEntrenamiento=formatoTexto.parse(fechaTraining);
			}catch(ParseException e){
				e.printStackTrace();
			}
		}
	}
	
	//Este método obtiene los datos del partido
	public static void getDatosPartidos(Context contexto,String fecha){
		//Reseteamos los valores antes de obtenerlos para obtener los valores adecuados para el método
		SentenciasSelectSQLite.borrarTodosValores();
		
		//Obtenemos el identificador
		DatosFootball.getDatosFootball(contexto);
		int id=DatosFootball.getIdEquipo();
		
		//Ejcutamos la sentencia
		SentenciasSelectSQLite.seleccionarSQLite("Partidos",new String[]{"COUNT(*)","fecha"},"id_equipo="+id+" AND dia='"+fecha+"'");
		
		//Almacenamos los valores
		String[] valores=SentenciasSelectSQLite.getValores();
		totalPartido=valores[0];
		String fechaMatch=valores[1];
		if(fechaMatch!=null){
			try{
				fechaPartido=formatoTexto.parse(fechaMatch);
			}catch (ParseException e){
				e.printStackTrace();
			}
		}
	}
}