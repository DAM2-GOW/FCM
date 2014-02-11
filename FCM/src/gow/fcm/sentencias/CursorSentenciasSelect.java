package gow.fcm.sentencias;

import gow.fcm.basedatos.ConexionSQLite;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CursorSentenciasSelect{
	
	private static Cursor c;
	
	//Método para obtener el cursor
	public static Cursor getCursor(){
		return c;
	}
	
	//Método para abrir el cursor
	public static void abrirCursor(String sentencia){
		SQLiteDatabase bd=ConexionSQLite.getConexion();
		c=bd.rawQuery(sentencia,null);
	}
	
	//Método para cerrar el cursor
	public static void cerrarCursor(){
		c.close();
	}
	
}