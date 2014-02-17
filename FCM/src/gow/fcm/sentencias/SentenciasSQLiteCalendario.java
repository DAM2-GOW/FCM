package gow.fcm.sentencias;

import gow.fcm.sharefprefs.DatosFootball;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.annotation.SuppressLint;
import android.content.Context;

public class SentenciasSQLiteCalendario{
	
	//Variables de la aplicación o de la base de datos
	private static String tipoEntrenamiento=null,dirigidoEntrenamiento,lugarPartido=null,rivalPartido;
	private static Date fechaEntrenamiento,fechaPartido; //Variables para usarlas en las sentencias
	@SuppressLint("SimpleDateFormat")
	private static SimpleDateFormat formatoTexto=new SimpleDateFormat("yyyy-MM-dd"); //Formato de conversión a Date
	
	//Obtenemos el tipo de entrenamiento
	public static String getTipoEntrenamiento(){
		return tipoEntrenamiento;
	}
	
	//Resetea el tipo de entrenamiento
	public static void setTipoEntrenamiento(){
		tipoEntrenamiento=null;
	}
	
	//Obtenemos a quien va dirigido el entrenamiento
	public static String getDirigidoEntrenamiento(){
		return dirigidoEntrenamiento;
	}
	
	//Obtenemos la fecha del entrenamiento
	public static Date getFechaEntrenamiento(){
		return fechaEntrenamiento;
	}
	
	//Obtenemos el lugar del partido
	public static String getLugarPartido(){
		return lugarPartido;
	}
	
	//Resetea el lugar del partido
	public static void setLugarPartido(){
		lugarPartido=null;
	}
	
	//Obtenemos el rival del partido
	public static String getRivalPartido(){
		return rivalPartido;
	}
	
	//Obtenemos la fecha del partido
	public static Date getFechaPartido(){
		return fechaPartido;
	}
	
	//Este método obtiene los datos del entrenamiento
	public static void getDatosEntrenamientos(Context contexto,String fecha){
		//Obtenemos el identificador
		DatosFootball.getDatosFootball(contexto);
		int id=DatosFootball.getIdEquipo();
		
		//Ejcutamos la sentencia
		SentenciasSelectSQLite.seleccionarSQLite("Entrenamientos",new String[]{"tipo","dirigido","fecha"},"id_equipo="+id+" AND dia='"+fecha+"'");
		
		//Almacenamos los valores
		tipoEntrenamiento=(String) SentenciasSelectSQLite.getValor1();
		dirigidoEntrenamiento=(String) SentenciasSelectSQLite.getValor2();
		String fechaTraining=(String) SentenciasSelectSQLite.getValor3();
		if(fechaTraining!=null){
			try{
				fechaEntrenamiento=formatoTexto.parse(fechaTraining);
			}catch(ParseException e){
				e.printStackTrace();
			}
		}
		
	}
	
	//Este método borrar los entrenamientos
	public static void borrarEventoEntrenamiento(Context contexto,String fecha){
		//Obtenemos el identificador
		DatosFootball.getDatosFootball(contexto);
		int id=DatosFootball.getIdEntrenador();
		
		//Ejcutamos la sentencia
		SentenciasSelectSQLite.seleccionarSQLite("Entrenamientos",new String[]{"id_entrenamiento"},"id_equipo="+id+" AND dia='"+fecha+"'");
		
		//Almacenamos los valores
		int idEntrenamiento=(Integer) SentenciasSelectSQLite.getValor1();
		
		//Ejcutamos la sentencia
		SentenciasDeleteSQLite.borrarSQLite("Entrenamientos","id_entrenamiento="+idEntrenamiento+"");
	}
	
	//Este método obtiene los datos del partido
	public static void getDatosPartidos(Context contexto,String fecha){
		//Obtenemos el identificador
		DatosFootball.getDatosFootball(contexto);
		int id=DatosFootball.getIdEntrenador();
		
		//Ejcutamos la sentencia
		SentenciasSelectSQLite.seleccionarSQLite("Partidos",new String[]{"lugar","rival","fecha"},"id_equipo="+id+" AND dia='"+fecha+"'");
		
		//Almacenamos los valores
		lugarPartido=(String) SentenciasSelectSQLite.getValor1();
		rivalPartido=(String) SentenciasSelectSQLite.getValor2();
		String fechaMatch=(String) SentenciasSelectSQLite.getValor3();
		if(fechaMatch!=null){
			try{
				fechaPartido=formatoTexto.parse(fechaMatch);
			}catch (ParseException e){
				e.printStackTrace();
			}
		}
	}
	
	//Este método borrar los partidos
	public static void borrarEventoPartido(Context contexto,String fecha){
		//Obtenemos el identificador
		DatosFootball.getDatosFootball(contexto);
		int id=DatosFootball.getIdEquipo();
		
		//Ejcutamos la sentencia
		SentenciasSelectSQLite.seleccionarSQLite("Partidos",new String[]{"id_partido"},"id_equipo="+id+" AND dia='"+fecha+"'");
		
		//Almacenamos los valores
		int idPartido=(Integer) SentenciasSelectSQLite.getValor1();
		
		//Ejcutamos la sentencia
		SentenciasDeleteSQLite.borrarSQLite("Partidos","id_partido="+idPartido+"");
	}
	
}