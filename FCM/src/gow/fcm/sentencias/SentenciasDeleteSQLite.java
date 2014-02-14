package gow.fcm.sentencias;

import android.database.sqlite.SQLiteDatabase;
import gow.fcm.basedatos.ConexionSQLite;

public class SentenciasDeleteSQLite{
	
	//Método que borra un registro de la base de datos pasandole como parametros el nombre de la tabla y la condición que se debe cumplir
	public static void borrarSQLite(String tabla,String condicion){
		//Abrimos la conexión
		ConexionSQLite.getAbrirSQLite();
		SQLiteDatabase bd=ConexionSQLite.getConexion();
		
		//Ejcutamos la sentencia
		String sentencia="DELETE FROM "+tabla+" WHERE "+condicion+";";
		bd.execSQL(sentencia);
		
		ConexionSQLite.getCerrarSQLite(); //Cerramos la conexión
	}
}