package gow.fcm.sentencias;

import gow.fcm.sharefprefs.DatosFootball;
import android.content.Context;
import android.util.Log;

public class SentenciasSQLiteNuevoEditarPartido{
	
	private static String lugarPartido,rivalPartido,diaPartido,horaPartido,minutosPartido;
	
	//Obtenemos el lugar del partido
	public static String getLugarPartido(){
		return lugarPartido;
	}
	
	//Obtenemos el rival del partido
	public static String getRivalPartido(){
		return rivalPartido;
	}
	
	//Obtenemos el dia del partido
	public static String getDiaPartido(){
		return diaPartido;
	}
	
	//Obtenemos la hora del partido
	public static String getHoraPartido(){
		return horaPartido;
	}
	
	//Obtenemos los minutos del partido
	public static String getMinutosPartido(){
		return minutosPartido;
	}
	
	//Este método obtiene los datos del partido para editarlo
	public static void getDatosEditarPartido(Context contexto,String dia){
		//Reseteamos los valores antes de obtenerlos para obtener los valores adecuados para el método
		SentenciasSelectSQLite.borrarTodosValores();
		
		//Obtenemos el identificador
		DatosFootball.getDatosFootball(contexto);
		int id=DatosFootball.getIdEquipo();
		
		//Ejcutamos la sentencia
		SentenciasSelectSQLite.seleccionarSQLite("Partidos",new String[]{"lugar","rival","dia","fecha"},"id_equipo="+id+" AND dia='"+dia+"'");
		
		//Almacenamos los valores
		String[] valores=(String[]) SentenciasSelectSQLite.getValores();
		lugarPartido=valores[0];
		rivalPartido=valores[1];
		diaPartido=valores[2];
		String fecha=valores[3];
		horaPartido="";
		for(int i=0;i<=1;i++){
			horaPartido=horaPartido.concat(String.valueOf(fecha.charAt(i+11)));
		}
		Log.d("Hola", horaPartido);
		Log.d("Hola", horaPartido);
	}
	
}