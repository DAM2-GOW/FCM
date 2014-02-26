package gow.fcm.sentencias;

import gow.fcm.sharefprefs.DatosFootball;
import android.content.Context;

public class SentenciasSQLiteListaEstadisticas {

	private static String carreras, pases, pases_completados, recepciones, fumbles, sacks, placajes, intercepciones, field_goals, punts, touchdowns, faltas;
	private static String yardas_carrera, yardas_pase, yardas_recepcion, yardas_fumble, yardas_intercepcion, yardas_field_goal, yardas_punt, extra_points, extra_points_completados, pt2_conversions, pt2_conversions_completados, yardas_falta;

	public static String getCarreras() {
		return carreras;
	}

	public static String getPases() {
		return pases;
	}

	public static String getPunts() {
		return punts;
	}

	public static String getTouchdowns() {
		return touchdowns;
	}

	public static String getFaltas() {
		return faltas;
	}

	public static String getPases_completados() {
		return pases_completados;
	}

	public static String getRecepciones() {
		return recepciones;
	}

	public static String getFumbles() {
		return fumbles;
	}

	public static String getSacks() {
		return sacks;
	}

	public static String getPlacajes() {
		return placajes;
	}

	public static String getIntercepciones() {
		return intercepciones;
	}

	public static String getField_goals() {
		return field_goals;
	}

	public static String getYardas_carrera() {
		return yardas_carrera;
	}

	public static String getYardas_pase() {
		return yardas_pase;
	}

	public static String getYardas_recpcion() {
		return yardas_recepcion;
	}

	public static String getYardas_funmble() {
		return yardas_fumble;
	}

	public static String getYardas_intercepcion() {
		return yardas_intercepcion;
	}

	public static String getYardas_field_goal() {
		return yardas_field_goal;
	}

	public static String getYardas_punt() {
		return yardas_punt;
	}

	public static String getExtra_points() {
		return extra_points;
	}

	public static String getExtra_points_completados() {
		return extra_points_completados;
	}

	public static String getPt2_conversions() {
		return pt2_conversions;
	}

	public static String getPt2_conversions_completados() {
		return pt2_conversions_completados;
	}

	public static String getYardas_falta() {
		return yardas_falta;
	}
	
	//Este método obtiene los datos del jugador
	public static void getEstadisticasPartido(Context contexto){
		//Reseteamos los valores antes de obtenerlos para obtener los valores adecuados para el método
		SentenciasSelectSQLite.borrarTodosValores();

		//Obtenemos el identificador
		DatosFootball.getDatosFootball(contexto);
		int idEquipo=DatosFootball.getIdEquipo();

		//Ejcutamos la sentencia
		SentenciasSelectSQLite.seleccionarSQLite("Partidos",new String[]{"id_partido"},"id_equipo="+idEquipo+"");

		//Obtenemos el número de valores
		String[] valor=SentenciasSelectSQLite.getValores();
		String idPartido=valor[0];

		//Ejcutamos la sentencia
		SentenciasSelectSQLite.seleccionarSQLite("Estadisticas_Partidos", new String[]{"carreras","pases","pases_completados","recepciones","fumbles","sacks","placajes","intercepciones","field_goals","punts","touchdowns","faltas","yardas_carrera","yardas_pase","yardas_recepcion","yardas_fumble","yardas_intercepcion","yardas_field_goal","yardas_punt","extra_points","extra_points_completados","pt2_conversions","pt2_conversions_completados","yardas_falta"},"id_partido="+idPartido+"");
		//Almacenamos los valores
		String[] valores=SentenciasSelectSQLite.getValores();
		carreras = valores[0];
		pases=valores[1];
		pases_completados = valores[2];
		recepciones=valores[3];
		fumbles=valores[4];
		sacks=valores[5];
		placajes=valores[6];
		intercepciones=valores[7];
		field_goals=valores[8];
		punts=valores[9];
		touchdowns=valores[10];
		faltas=valores[11];
		yardas_carrera=valores[12];
		yardas_pase=valores[13];
		yardas_recepcion=valores[14];
		yardas_fumble=valores[15];
		yardas_intercepcion=valores[16];
		yardas_field_goal=valores[17];
		yardas_punt=valores[18];
		extra_points=valores[19];
		extra_points_completados=valores[20];
		pt2_conversions=valores[21];
		pt2_conversions_completados=valores[22];
		yardas_falta=valores[23];
	}

}
