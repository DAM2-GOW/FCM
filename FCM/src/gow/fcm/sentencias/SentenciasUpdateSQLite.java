package gow.fcm.sentencias;

import gow.fcm.basedatos.ConexionSQLite;
import android.database.sqlite.SQLiteDatabase;

public class SentenciasUpdateSQLite{
	
	//Método que actualiza un registro en la base de datos pasandole como parametros el nombre de la tabla, el nombre de los campos, los valores a actualizar y la condición que se debe cumplir
	public static void actualizarSQLite(String tabla,String[] campos,String[] valores,String condicion){
		//Abrimos la conexión
		ConexionSQLite.getAbrirSQLite();
		SQLiteDatabase bd=ConexionSQLite.getConexion();
		
		//Convertimos a string el vector
		String actual="";
		for(int i=0;i<(campos.length & valores.length);i++){
			actual=actual.concat(campos[i]+"=");
			if(i==campos.length-1 & i==valores.length-1){
				actual=actual.concat("'"+valores[i]+"'");
			}else{
				actual=actual.concat("'"+valores[i]+"',");
			}
		}
		
		//Ejcutamos la sentencia
		String sentencia="UPDATE "+tabla+" SET "+actual+" WHERE "+condicion+";";
		bd.execSQL(sentencia);
		
		ConexionSQLite.getCerrarSQLite(); //Cerramos la conexión
	}
	
}