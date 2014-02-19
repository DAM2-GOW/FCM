package gow.fcm.sentencias;

import gow.fcm.basedatos.ConexionSQLite;
import android.database.Cursor;

public class SentenciasSelectSQLite{
	
	//Valores a almacenar de los campos de la base de datos
	private static int maxValores=24; //Número de valores a obtener en la sentencia como máximo
	private static String[] valores=new String[maxValores];//Indicamos el tamaño del array para que no de un error
	
	public static String[] getValores(){
		return valores;
	}
	
	//Reseteamos los valores de la clase genérica Object
	public static void borrarTodosValores(){
		for(int i=0;i<maxValores;i++){
			valores[i]=null;
		}
	}
	
	//Método que obtiene un registro en la base de datos pasandole como parametros el nombre de la tabla, el nombre de los campos y la condición que se debe cumplir
	public static void seleccionarSQLite(String tabla,String[] campos,String condicion){
		ConexionSQLite.getAbrirSQLite(); //Abrimos la conexión
		
		maxValores=campos.length;
		valores=new String[maxValores]; //Le indicamos el nuevo tamaño del vector según el número de campos a obtener
		
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
			//Recorremos el cursor hasta que no haya más registros
			do{
				
				//Según el número de campos que le hayamos pasados, devolverá un determinado conjunto de valores
				switch(campos.length){
					case 1: for(int i=0;i<campos.length;i++){
							valores[i]=cursor.getString(i);
						}
						break;
						
					case 2: for(int i=0;i<campos.length;i++){
							valores[i]=cursor.getString(i);
						}
						break;
						
					case 3: for(int i=0;i<campos.length;i++){
							valores[i]=cursor.getString(i);
						}
						break;
						
					case 4: for(int i=0;i<campos.length;i++){
							valores[i]=cursor.getString(i);
						}
						break;
						
					case 5: for(int i=0;i<campos.length;i++){
							valores[i]=cursor.getString(i);
						}
						break;

					case 6: for(int i=0;i<campos.length;i++){
							valores[i]=cursor.getString(i);
						}
						break;

					case 7: for(int i=0;i<campos.length;i++){
							valores[i]=cursor.getString(i);
						}
						break;

					case 8: for(int i=0;i<campos.length;i++){
							valores[i]=cursor.getString(i);
						}
						break;

					case 9: for(int i=0;i<campos.length;i++){
							valores[i]=cursor.getString(i);
						}
						break;

					case 10: for(int i=0;i<campos.length;i++){
							valores[i]=cursor.getString(i);
						}
						break;

					case 11: for(int i=0;i<campos.length;i++){
							valores[i]=cursor.getString(i);
						}
						break;

					case 12: for(int i=0;i<campos.length;i++){
							valores[i]=cursor.getString(i);
						}
						break;

					case 13: for(int i=0;i<campos.length;i++){
							valores[i]=cursor.getString(i);
						}
						break;

					case 14: for(int i=0;i<campos.length;i++){
							valores[i]=cursor.getString(i);
						}
						break;

					case 15: for(int i=0;i<campos.length;i++){
							valores[i]=cursor.getString(i);
						}
						break;

					case 16: for(int i=0;i<campos.length;i++){
							valores[i]=cursor.getString(i);
						}
						break;

					case 17: for(int i=0;i<campos.length;i++){
							valores[i]=cursor.getString(i);
						}
						break;

					case 18: for(int i=0;i<campos.length;i++){
							valores[i]=cursor.getString(i);
						}
						break;

					case 19: for(int i=0;i<campos.length;i++){
							valores[i]=cursor.getString(i);
						}
						break;

					case 20: for(int i=0;i<campos.length;i++){
							valores[i]=cursor.getString(i);
						}
						break;

					case 21: for(int i=0;i<campos.length;i++){
							valores[i]=cursor.getString(i);
						}
						break;

					case 22: for(int i=0;i<campos.length;i++){
							valores[i]=cursor.getString(i);
						}
						break;

					case 23: for(int i=0;i<campos.length;i++){
							valores[i]=cursor.getString(i);
						}
						break;

					case 24: for(int i=0;i<campos.length;i++){
							valores[i]=cursor.getString(i);
						}
						break;
				}
			}while(cursor.moveToNext());
		}
		
		//Cerramos el cursor y la conexión
		CursorSentenciasSelect.cerrarCursor();
		ConexionSQLite.getCerrarSQLite();
	}
	
}