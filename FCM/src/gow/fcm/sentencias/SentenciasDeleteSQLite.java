package gow.fcm.sentencias;

import android.database.sqlite.SQLiteDatabase;
import gow.fcm.basedatos.ConexionSQLite;

public class SentenciasDeleteSQLite{
	
	//M�todo que borra un registro de la base de datos pasandole como parametros el nombre de la tabla y la condici�n que se debe cumplir
	public static void borrarSQLite(String tabla,String condicion){
		//Abrimos la conexi�n
		ConexionSQLite.getAbrirSQLite();
		SQLiteDatabase bd=ConexionSQLite.getConexion();
		
		//Ejcutamos la sentencia
		String sentencia="DELETE FROM "+tabla+" WHERE "+condicion+";";
		bd.execSQL(sentencia);
		
		ConexionSQLite.getCerrarSQLite(); //Cerramos la conexi�n
	}
}