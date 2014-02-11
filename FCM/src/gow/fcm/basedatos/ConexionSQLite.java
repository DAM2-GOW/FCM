package gow.fcm.basedatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class ConexionSQLite{
	
	private static String baseDatos="Football"; //Nombre de la base de datos SQLite
	private static int versionBD=1; //Versión de la base de datos
	private static CreateUpgradeSQLite abrir;
	private static SQLiteDatabase bd;
	
	public static void getCrearSQLite(Context contexto){
		//Creamos la base de datos
		abrir=new CreateUpgradeSQLite(contexto,baseDatos,null,versionBD);
	}
	
	public static void getAbrirSQLite(){
		//Abrimos la conexion a la base de datos
		bd=abrir.getWritableDatabase();
	}
	
	//Método que obtiene el objeto de conexión de la base de datos SQLite
	public static SQLiteDatabase getConexion(){
		return bd;
	}
	
	public static void getCerrarSQLite(){
		//Cerramos la conexion a la base de datos
		bd.close();
	}
	
}