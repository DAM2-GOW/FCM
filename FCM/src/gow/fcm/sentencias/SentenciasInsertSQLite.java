package gow.fcm.sentencias;

import gow.fcm.basedatos.ConexionSQLite;
import android.database.sqlite.SQLiteDatabase;

public class SentenciasInsertSQLite{
	
	//Método que inserta un registro en la base de datos pasandole como parametros el nombre de la tabla, el nombre de los campos y los valores a insertar
	public static void insertarSQLite(String tabla,String[] campos,String[] valores){
		//Abrimos la conexión
		ConexionSQLite.getAbrirSQLite();
		SQLiteDatabase bd=ConexionSQLite.getConexion();
		
		//Convertimos a string el vector
		String campo="";
		String valor="";
		for(int i=0;i<(valores.length & campos.length);i++){
			if(i==campos.length-1 & i==valores.length-1){
				campo=campo.concat(campos[i]);
				valor=valor.concat("'"+valores[i]+"'");
			}else{
				campo=campo.concat(campos[i]+",");
				valor=valor.concat("'"+valores[i]+"',");
			}
		}
		
		//Ejcutamos la sentencia
		String sentencia="INSERT INTO "+tabla+"("+campo+") VALUES("+valor+");";
		bd.execSQL(sentencia);
		
		ConexionSQLite.getCerrarSQLite(); //Cerramos la conexión
	}
	
}