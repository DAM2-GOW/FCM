package gow.fcm.sentencias;

import gow.fcm.basedatos.ConexionSQLite;
import android.database.Cursor;

public class SentenciasSelectSQLite{
	
	//Valores a almacenar de los campos de la base de datos
	private static int maxValores=24; //N�mero de valores a obtener en la sentencia como m�ximo
	private static String[] valores=new String[maxValores];//Indicamos el tama�o del array para que no de un error
	
	public static String[] getValores(){
		return valores;
	}
	
	//Reseteamos los valores de la clase gen�rica Object
	public static void borrarTodosValores(){
		for(int i=0;i<maxValores;i++){
			valores[i]=null;
		}
	}
	
	//M�todo que obtiene un registro en la base de datos pasandole como parametros el nombre de la tabla, el nombre de los campos y la condici�n que se debe cumplir
	public static void seleccionarSQLite(String tabla,String[] campos,String condicion){
		ConexionSQLite.getAbrirSQLite(); //Abrimos la conexi�n
		
		maxValores=campos.length;
		valores=new String[maxValores]; //Le indicamos el nuevo tama�o del vector seg�n el n�mero de campos a obtener
		
		//Convertimos a string el vector
		String campo="";
		for(int i=0;i<campos.length;i++){
			if(i==campos.length-1){
				campo=campo.concat(campos[i]);
			}else{
				campo=campo.concat(campos[i]+",");
			}
		}
		
		//Ejcutamos la sentencia
		String sentencia="SELECT "+campo+" FROM "+tabla+" WHERE "+condicion+";";
		CursorSentenciasSelect.abrirCursor(sentencia);
		Cursor cursor=CursorSentenciasSelect.getCursor();
		
		//Nos aseguramos de que existe al menos un registro
		if(cursor.moveToFirst()){
			//Recorremos el cursor hasta que no haya m�s registros
			do{
				//Seg�n el n�mero de campos que le hayamos pasados, devolver� un determinado conjunto de valores
				for(int i=0;i<campos.length;i++){
					valores[i]=cursor.getString(i);
				}
			}while(cursor.moveToNext());
		}
		
		//Cerramos el cursor y la conexi�n
		CursorSentenciasSelect.cerrarCursor();
		ConexionSQLite.getCerrarSQLite();
	}
	
}