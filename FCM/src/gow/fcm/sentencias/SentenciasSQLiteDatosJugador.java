package gow.fcm.sentencias;

import gow.fcm.sharefprefs.DatosFootball;
import android.content.Context;

public class SentenciasSQLiteDatosJugador {
	
	private static String nombre, apellidos, edad, posicion, tipo, numero, foto;
	
	public static String getNombre() {
		return nombre;
	}
	
	public static String getApellido() {
		return apellidos;
	}
	
	public static String getEdad() {
		return edad;
	}
	
	public static String getPosicion() {
		return posicion;
	}
	
	public static String getTipo() {
		return tipo;
	}
	
	public static String getNumero() {
		return numero;
	}
	
	public static String getFoto() {
		return foto;
	}
	
	//Este método obtiene los datos del jugador
		public static void getDatosJugador(Context contexto){
			//Reseteamos los valores antes de obtenerlos para obtener los valores adecuados para el método
			SentenciasSelectSQLite.borrarTodosValores();
			
			//Obtenemos el identificador
			DatosFootball.getDatosFootball(contexto);
			int idEquipo=DatosFootball.getIdEquipo();
			
			//Ejcutamos la sentencia
			SentenciasSelectSQLite.seleccionarSQLite("Jugadores",new String[]{"COUNT(*)"},"id_equipo="+idEquipo+"");
			
			//Obtenemos el número de valores
			String[] valores=SentenciasSelectSQLite.getValores();
			
				//Ejcutamos la sentencia
				SentenciasSelectSQLite.seleccionarSQLite("Jugadores", new String[]{"nombre","apellidos","edad","posicion","tipo","dorsal","foto"}, "id_equipo="+idEquipo+"");
				//Almacenamos los valores
				valores=SentenciasSelectSQLite.getValores();
				nombre=valores[0];
				apellidos=valores[1];
				edad=valores[2];
				posicion=valores[3];
				tipo=valores[4];
				numero=valores[5];
				foto=valores[6];
		}
}