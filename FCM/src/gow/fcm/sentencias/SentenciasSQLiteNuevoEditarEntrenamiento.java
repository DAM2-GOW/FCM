package gow.fcm.sentencias;

import gow.fcm.sharefprefs.DatosFootball;
import android.content.Context;

public class SentenciasSQLiteNuevoEditarEntrenamiento{
	
	private static String tipoEntrenamiento,dirigidoEntrenamiento,diaEntrenamiento,horaEntrenamiento,minutosEntrenamiento,observaciones;
	
	//Obtenemos el tipo del entrenamiento
	public static String getTipoEntrenamiento(){
		return tipoEntrenamiento;
	}
	
	//Obtenemos a quién va dirigido el entrenamiento
	public static String getDirigidoEntrenamiento(){
		return dirigidoEntrenamiento;
	}
	
	//Obtenemos el dia del entrenamiento
	public static String getDiaEntrenamiento(){
		return diaEntrenamiento;
	}
	
	//Obtenemos la hora del entrenamiento
	public static String getHoraEntrenamiento(){
		return horaEntrenamiento;
	}
	
	//Obtenemos los minutos del entrenamiento
	public static String getMinutosEntrenamiento(){
		return minutosEntrenamiento;
	}
	
	//Obtenemos las observaciones del entrenamiento
	public static String getObservaciones(){
		return observaciones;
	}
	
	//Este método obtiene los datos del entrenamiento para editarlo
	public static void getDatosEditarEntrenamiento(Context contexto,String dia,String posicion){
		//Reseteamos los valores antes de obtenerlos para obtener los valores adecuados para el método
		SentenciasSelectSQLite.borrarTodosValores();
		
		//Obtenemos el identificador
		DatosFootball.getDatosFootball(contexto);
		int id=DatosFootball.getIdEquipo();
		
		//Ejcutamos la sentencia
		SentenciasSelectSQLite.seleccionarSQLite("Entrenamientos",new String[]{"tipo","dirigido","dia","fecha","observaciones"},"id_equipo="+id+" AND dia='"+dia+"' LIMIT "+posicion+",1");
		
		//Almacenamos los valores
		String[] valores=(String[]) SentenciasSelectSQLite.getValores();
		tipoEntrenamiento=valores[0];
		dirigidoEntrenamiento=valores[1];
		diaEntrenamiento=valores[2];
		String fecha=valores[3];
		
		//Obtenemos la hora
		horaEntrenamiento="";
		for(int i=0;i<=1;i++){
			int index=fecha.indexOf(" ")+1;
			horaEntrenamiento=horaEntrenamiento.concat(String.valueOf(fecha.charAt(i+index)));
			horaEntrenamiento=horaEntrenamiento.replace(":","");
		}
		
		//Obtenemos los minutos
		minutosEntrenamiento="";
		for(int i=0;i<=1;i++){
			int index=fecha.indexOf(":")+1;
			minutosEntrenamiento=minutosEntrenamiento.concat(String.valueOf(fecha.charAt(i+index)));
			minutosEntrenamiento=minutosEntrenamiento.replace(":","");
		}
		
		observaciones=valores[4];
	}
	
}