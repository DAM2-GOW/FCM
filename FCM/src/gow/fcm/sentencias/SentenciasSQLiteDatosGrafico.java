package gow.fcm.sentencias;

import gow.fcm.sharefprefs.DatosFootball;
import android.content.Context;

public class SentenciasSQLiteDatosGrafico {
	
	private static int[] datos, dias;
	
	public static int[] getDatos(){
		return datos;
	}
	
	public static int[] getDias(){
		return dias;
	}
	
	//Este método obtiene los datos del jugador
	public static void getDatosGrafico(Context contexto,String dato){
		
		//Reseteamos los valores antes de obtenerlos para obtener los valores adecuados para el método
		SentenciasSelectSQLite.borrarTodosValores();
		
		//Obtenemos el identificador
		DatosFootball.getDatosFootball(contexto);
		int idEquipo=DatosFootball.getIdEquipo();
		
		//Ejcutamos la sentencia
		SentenciasSelectSQLite.seleccionarSQLite("Partidos",new String[]{"COUNT(*)","id_partido"},"id_equipo="+idEquipo+"");
		
		//Obtenemos el número de valores
		String[] valor=SentenciasSelectSQLite.getValores();
		String numPartidos=valor[0];
		String idPartido=valor[1];
		
		datos=new int[Integer.parseInt(numPartidos)];
		dias=new int[Integer.parseInt(numPartidos)];
		
		for(int i=0;i<datos.length;i++){
			SentenciasSelectSQLite.seleccionarSQLite("Estadisticas_Partidos",new String[]{dato},"id_partido="+idPartido+" ORDER BY id_partido LIMIT "+(i)+",1");
			String[] valores=(String[]) SentenciasSelectSQLite.getValores();
			datos[i]=Integer.parseInt(valores[0]);
			SentenciasSelectSQLite.seleccionarSQLite("Partidos",new String[]{"id_partido"},"id_equipo="+idEquipo+" ORDER BY id_partido LIMIT "+(i)+",1");
			String[] valores2=(String[]) SentenciasSelectSQLite.getValores();
			dias[i]=Integer.parseInt(valores2[0]);
		}
	}
	
}