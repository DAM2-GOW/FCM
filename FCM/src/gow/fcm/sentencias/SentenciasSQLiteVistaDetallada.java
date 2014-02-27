package gow.fcm.sentencias;

import gow.fcm.sharefprefs.DatosFootball;
import android.content.Context;

public class SentenciasSQLiteVistaDetallada {
	
	private static String[] dirigido,tipo,hora,lugar,rival;
	
	/*
	 * METODOS ENTRENAMIENTOS.
	 */
	
	/**
	 * @return the dirigido
	 */
	public static String[] getDirigido() {
		return dirigido;
	}
	
	/**
	 * @return the lugar
	 */
	public static String[] getLugar() {
		return lugar;
	}
	
	/**
	 * @return the rival
	 */
	public static String[] getRival() {
		return rival;
	}
	
	/**
	 * @return the tipo
	 */
	public static String[] getTipo() {
		return tipo;
	}
	
	/**
	 * @return the hora
	 */
	public static String[] getHora() {
		return hora;
	}

	public static void obtenerEntrenamientos(String dia, int idEquipo) {
		//Reseteamos los valores antes de obtenerlos para obtener los valores adecuados para el método
		SentenciasSelectSQLite.borrarTodosValores();
		
		SentenciasSelectSQLite.seleccionarSQLite("Entrenamientos",new String[]{"COUNT(*)"},"id_equipo="+idEquipo+" AND dia='"+dia+"'");
		String[] valor=SentenciasSelectSQLite.getValores();
		int num= Integer.parseInt(valor[0]);
		
		dirigido=new String[num];
		tipo=new String[num];
		hora=new String[num];
		
		// Ejecutamos la sentencia
		for(int i=0; i<num;i++){
			SentenciasSelectSQLite.seleccionarSQLite("Entrenamientos",new String[]{"dirigido","tipo","fecha"},"id_equipo="+idEquipo+" AND dia='"+dia+"' LIMIT "+i+",1");
			
			// Obtenemos respuesta de existencia
			String[] valores = SentenciasSelectSQLite.getValores();
			dirigido[i]=valores[0];
			tipo[i]=valores[1];
			String fecha=valores[2];
			
			//Obtenemos la hora
			String horaEntrenamiento="";
			String minutosEntrenamiento="";
			for(int i2=0;i2<=1;i2++){
				int index=fecha.indexOf(" ")+1;
				horaEntrenamiento=horaEntrenamiento.concat(String.valueOf(fecha.charAt(i2+index)));
				horaEntrenamiento=horaEntrenamiento.replace(":","");
				int index2=fecha.indexOf(":")+1;
				minutosEntrenamiento=minutosEntrenamiento.concat(String.valueOf(fecha.charAt(i2+index2)));
				minutosEntrenamiento=minutosEntrenamiento.replace(":","");
			}
			
			hora[i]=horaEntrenamiento+":"+minutosEntrenamiento+":00";
		}
	}
	
	public static void obtenerPartidos(String dia, int idEquipo) {
		//Reseteamos los valores antes de obtenerlos para obtener los valores adecuados para el método
		SentenciasSelectSQLite.borrarTodosValores();
		
		SentenciasSelectSQLite.seleccionarSQLite("Partidos",new String[]{"COUNT(*)"},"id_equipo="+idEquipo+" AND dia='"+dia+"'");
		String[] valor=SentenciasSelectSQLite.getValores();
		int num= Integer.parseInt(valor[0]);
		
		lugar=new String[num];
		rival=new String[num];
		hora=new String[num];
		
		// Ejecutamos la sentencia
		for(int i=0; i<num;i++){
			SentenciasSelectSQLite.seleccionarSQLite("Partidos",new String[]{"lugar","rival","fecha"},"id_equipo="+idEquipo+" AND dia='"+dia+"' LIMIT "+i+",1");
			
			// Obtenemos respuesta de existencia
			String[] valores = SentenciasSelectSQLite.getValores();
			lugar[i]=valores[0];
			rival[i]=valores[1];
			String fecha=valores[2];
			
			//Obtenemos la hora
			String horaEntrenamiento="";
			String minutosEntrenamiento="";
			for(int i2=0;i2<=1;i2++){
				int index=fecha.indexOf(" ")+1;
				horaEntrenamiento=horaEntrenamiento.concat(String.valueOf(fecha.charAt(i2+index)));
				horaEntrenamiento=horaEntrenamiento.replace(":","");
				int index2=fecha.indexOf(":")+1;
				minutosEntrenamiento=minutosEntrenamiento.concat(String.valueOf(fecha.charAt(i2+index2)));
				minutosEntrenamiento=minutosEntrenamiento.replace(":","");
			}
			
			hora[i]=horaEntrenamiento+":"+minutosEntrenamiento+":00";
		}
	}
	
	//Este método borrar los entrenamientos
	public static void borrarEventoEntrenamiento(Context contexto,String fecha,String posicion){
		//Reseteamos los valores antes de obtenerlos para obtener los valores adecuados para el método
		SentenciasSelectSQLite.borrarTodosValores();
		
		//Obtenemos el identificador
		DatosFootball.getDatosFootball(contexto);
		int id=DatosFootball.getIdEquipo();
		
		//Ejcutamos la sentencia
		SentenciasSelectSQLite.seleccionarSQLite("Entrenamientos",new String[]{"id_entrenamiento"},"id_equipo="+id+" AND dia='"+fecha+"' LIMIT "+posicion+",1");
		
		//Almacenamos los valores
		String[] valores=SentenciasSelectSQLite.getValores();
		String idEntrenamiento=valores[0];
		
		//Ejcutamos la sentencia
		SentenciasDeleteSQLite.borrarSQLite("Entrenamientos","id_entrenamiento="+idEntrenamiento+"");
	}
	
	//Este método borrar los partidos
	public static void borrarEventoPartido(Context contexto,String fecha,String posicion){
		//Reseteamos los valores antes de obtenerlos para obtener los valores adecuados para el método
		SentenciasSelectSQLite.borrarTodosValores();
		
		//Obtenemos el identificador
		DatosFootball.getDatosFootball(contexto);
		int id=DatosFootball.getIdEquipo();
		
		//Ejcutamos la sentencia
		SentenciasSelectSQLite.seleccionarSQLite("Partidos",new String[]{"id_partido"},"id_equipo="+id+" AND dia='"+fecha+"' LIMIT "+posicion+",1");
		
		//Almacenamos los valores
		String[] valores=SentenciasSelectSQLite.getValores();
		String idPartido=valores[0];
		
		//Ejcutamos la sentencia
		SentenciasDeleteSQLite.borrarSQLite("Partidos","id_partido="+idPartido+"");
	}
}
