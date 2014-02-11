package gow.fcm.sentencias;

import gow.fcm.basedatos.ConexionSQLite;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CursorSentenciasSelect{
	
	private static Cursor c;
	
	//M�todo para obtener el cursor
	public static Cursor getCursor(){
		return c;
	}
	
	//M�todo para abrir el cursor
	public static void abrirCursor(String sentencia){
		SQLiteDatabase bd=ConexionSQLite.getConexion();
		c=bd.rawQuery(sentencia,null);
	}
	
	//M�todo para cerrar el cursor
	public static void cerrarCursor(){
		c.close();
	}
	
}