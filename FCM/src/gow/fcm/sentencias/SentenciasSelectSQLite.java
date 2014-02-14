package gow.fcm.sentencias;

import gow.fcm.basedatos.ConexionSQLite;
import android.database.Cursor;

public class SentenciasSelectSQLite{
	
	//Valores a almacenar de los campos de la base de datos
	private static Object valor1,valor2,valor3,valor4,valor5,valor6,valor7,valor8,valor9,valor10,valor11,valor12;
	private static Object valor13,valor14,valor15,valor16,valor17,valor18,valor19,valor20,valor21,valor22,valor23,valor24;
	
	//Obtenemos el valor como la clase genérica Object
	public static Object getValor1(){
		return valor1;
	}
	
	//Obtenemos el valor como la clase genérica Object
	public static Object getValor2(){
		return valor2;
	}
	
	//Obtenemos el valor como la clase genérica Object
	public static Object getValor3(){
		return valor3;
	}
	
	//Obtenemos el valor como la clase genérica Object
	public static Object getValor4(){
		return valor4;
	}
	
	//Obtenemos el valor como la clase genérica Object
	public static Object getValor5(){
		return valor5;
	}

	//Obtenemos el valor como la clase genérica Object
	public static Object getValor6(){
		return valor6;
	}

	//Obtenemos el valor como la clase genérica Object
	public static Object getValor7(){
		return valor7;
	}

	//Obtenemos el valor como la clase genérica Object
	public static Object getValor8(){
		return valor8;
	}

	//Obtenemos el valor como la clase genérica Object
	public static Object getValor9(){
		return valor9;
	}

	//Obtenemos el valor como la clase genérica Object
	public static Object getValor10(){
		return valor10;
	}

	//Obtenemos el valor como la clase genérica Object
	public static Object getValor11(){
		return valor11;
	}

	//Obtenemos el valor como la clase genérica Object
	public static Object getValor12(){
		return valor12;
	}

	//Obtenemos el valor como la clase genérica Object
	public static Object getValor13(){
		return valor13;
	}

	//Obtenemos el valor como la clase genérica Object
	public static Object getValor14(){
		return valor14;
	}

	//Obtenemos el valor como la clase genérica Object
	public static Object getValor15(){
		return valor15;
	}

	//Obtenemos el valor como la clase genérica Object
	public static Object getValor16(){
		return valor16;
	}

	//Obtenemos el valor como la clase genérica Object
	public static Object getValor17(){
		return valor17;
	}

	//Obtenemos el valor como la clase genérica Object
	public static Object getValor18(){
		return valor18;
	}

	//Obtenemos el valor como la clase genérica Object
	public static Object getValor19(){
		return valor19;
	}

	//Obtenemos el valor como la clase genérica Object
	public static Object getValor20(){
		return valor20;
	}

	//Obtenemos el valor como la clase genérica Object
	public static Object getValor21(){
		return valor21;
	}

	//Obtenemos el valor como la clase genérica Object
	public static Object getValor22(){
		return valor22;
	}

	//Obtenemos el valor como la clase genérica Object
	public static Object getValor23(){
		return valor23;
	}

	//Obtenemos el valor como la clase genérica Object
	public static Object getValor24(){
		return valor24;
	}
	
	//Método que obtiene un registro en la base de datos pasandole como parametros el nombre de la tabla, el nombre de los campos y la condición que se debe cumplir
	public static void seleccionarSQLite(String tabla,String[] campos,String condicion){
		ConexionSQLite.getAbrirSQLite(); //Abrimos la conexión
		
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
					case 1: valor1=cursor.getString(0);
						break;
						
					case 2: valor1=cursor.getString(0);
						valor2=cursor.getString(1);
						break;
						
					case 3: valor1=cursor.getString(0);
						valor2=cursor.getString(1);
						valor3=cursor.getString(2);
						break;
						
					case 4: valor1=cursor.getString(0);
						valor2=cursor.getString(1);
						valor3=cursor.getString(2);
						valor4=cursor.getString(3);
						break;
						
					case 5: valor1=cursor.getString(0);
						valor2=cursor.getString(1);
						valor3=cursor.getString(2);
						valor4=cursor.getString(3);
						valor5=cursor.getString(4);
						break;

					case 6: valor1=cursor.getString(0);
						valor2=cursor.getString(1);
						valor3=cursor.getString(2);
						valor4=cursor.getString(3);
						valor5=cursor.getString(4);
						valor6=cursor.getString(5);
						break;

					case 7: valor1=cursor.getString(0);
						valor2=cursor.getString(1);
						valor3=cursor.getString(2);
						valor4=cursor.getString(3);
						valor5=cursor.getString(4);
						valor6=cursor.getString(5);
						valor7=cursor.getString(6);
						break;

					case 8: valor1=cursor.getString(0);
						valor2=cursor.getString(1);
						valor3=cursor.getString(2);
						valor4=cursor.getString(3);
						valor5=cursor.getString(4);
						valor6=cursor.getString(5);
						valor7=cursor.getString(6);
						valor8=cursor.getString(7);
						break;

					case 9: valor1=cursor.getString(0);
						valor2=cursor.getString(1);
						valor3=cursor.getString(2);
						valor4=cursor.getString(3);
						valor5=cursor.getString(4);
						valor6=cursor.getString(5);
						valor7=cursor.getString(6);
						valor8=cursor.getString(7);
						valor9=cursor.getString(8);
						break;

					case 10: valor1=cursor.getString(0);
						valor2=cursor.getString(1);
						valor3=cursor.getString(2);
						valor4=cursor.getString(3);
						valor5=cursor.getString(4);
						valor6=cursor.getString(5);
						valor7=cursor.getString(6);
						valor8=cursor.getString(7);
						valor9=cursor.getString(8);
						valor10=cursor.getString(9);
						break;

					case 11: valor1=cursor.getString(0);
						valor2=cursor.getString(1);
						valor3=cursor.getString(2);
						valor4=cursor.getString(3);
						valor5=cursor.getString(4);
						valor6=cursor.getString(5);
						valor7=cursor.getString(6);
						valor8=cursor.getString(7);
						valor9=cursor.getString(8);
						valor10=cursor.getString(9);
						valor11=cursor.getString(10);
						break;

					case 12: valor1=cursor.getString(0);
						valor2=cursor.getString(1);
						valor3=cursor.getString(2);
						valor4=cursor.getString(3);
						valor5=cursor.getString(4);
						valor6=cursor.getString(5);
						valor7=cursor.getString(6);
						valor8=cursor.getString(7);
						valor9=cursor.getString(8);
						valor10=cursor.getString(9);
						valor11=cursor.getString(10);
						valor12=cursor.getString(11);
						break;

					case 13: valor1=cursor.getString(0);
						valor2=cursor.getString(1);
						valor3=cursor.getString(2);
						valor4=cursor.getString(3);
						valor5=cursor.getString(4);
						valor6=cursor.getString(5);
						valor7=cursor.getString(6);
						valor8=cursor.getString(7);
						valor9=cursor.getString(8);
						valor10=cursor.getString(9);
						valor11=cursor.getString(10);
						valor12=cursor.getString(11);
						valor13=cursor.getString(12);
						break;

					case 14: valor1=cursor.getString(0);
						valor2=cursor.getString(1);
						valor3=cursor.getString(2);
						valor4=cursor.getString(3);
						valor5=cursor.getString(4);
						valor6=cursor.getString(5);
						valor7=cursor.getString(6);
						valor8=cursor.getString(7);
						valor9=cursor.getString(8);
						valor10=cursor.getString(9);
						valor11=cursor.getString(10);
						valor12=cursor.getString(11);
						valor13=cursor.getString(12);
						valor14=cursor.getString(13);
						break;

					case 15: valor1=cursor.getString(0);
						valor2=cursor.getString(1);
						valor3=cursor.getString(2);
						valor4=cursor.getString(3);
						valor5=cursor.getString(4);
						valor6=cursor.getString(5);
						valor7=cursor.getString(6);
						valor8=cursor.getString(7);
						valor9=cursor.getString(8);
						valor10=cursor.getString(9);
						valor11=cursor.getString(10);
						valor12=cursor.getString(11);
						valor13=cursor.getString(12);
						valor14=cursor.getString(13);
						valor15=cursor.getString(14);
						break;

					case 16: valor1=cursor.getString(0);
						valor2=cursor.getString(1);
						valor3=cursor.getString(2);
						valor4=cursor.getString(3);
						valor5=cursor.getString(4);
						valor6=cursor.getString(5);
						valor7=cursor.getString(6);
						valor8=cursor.getString(7);
						valor9=cursor.getString(8);
						valor10=cursor.getString(9);
						valor11=cursor.getString(10);
						valor12=cursor.getString(11);
						valor13=cursor.getString(12);
						valor14=cursor.getString(13);
						valor15=cursor.getString(14);
						valor16=cursor.getString(15);
						break;

					case 17: valor1=cursor.getString(0);
						valor2=cursor.getString(1);
						valor3=cursor.getString(2);
						valor4=cursor.getString(3);
						valor5=cursor.getString(4);
						valor6=cursor.getString(5);
						valor7=cursor.getString(6);
						valor8=cursor.getString(7);
						valor9=cursor.getString(8);
						valor10=cursor.getString(9);
						valor11=cursor.getString(10);
						valor12=cursor.getString(11);
						valor13=cursor.getString(12);
						valor14=cursor.getString(13);
						valor15=cursor.getString(14);
						valor16=cursor.getString(15);
						valor17=cursor.getString(16);
						break;

					case 18: valor1=cursor.getString(0);
						valor2=cursor.getString(1);
						valor3=cursor.getString(2);
						valor4=cursor.getString(3);
						valor5=cursor.getString(4);
						valor6=cursor.getString(5);
						valor7=cursor.getString(6);
						valor8=cursor.getString(7);
						valor9=cursor.getString(8);
						valor10=cursor.getString(9);
						valor11=cursor.getString(10);
						valor12=cursor.getString(11);
						valor13=cursor.getString(12);
						valor14=cursor.getString(13);
						valor15=cursor.getString(14);
						valor16=cursor.getString(15);
						valor17=cursor.getString(16);
						valor18=cursor.getString(17);
						break;

					case 19: valor1=cursor.getString(0);
						valor2=cursor.getString(1);
						valor3=cursor.getString(2);
						valor4=cursor.getString(3);
						valor5=cursor.getString(4);
						valor6=cursor.getString(5);
						valor7=cursor.getString(6);
						valor8=cursor.getString(7);
						valor9=cursor.getString(8);
						valor10=cursor.getString(9);
						valor11=cursor.getString(10);
						valor12=cursor.getString(11);
						valor13=cursor.getString(12);
						valor14=cursor.getString(13);
						valor15=cursor.getString(14);
						valor16=cursor.getString(15);
						valor17=cursor.getString(16);
						valor18=cursor.getString(17);
						valor19=cursor.getString(18);
						break;

					case 20: valor1=cursor.getString(0);
						valor2=cursor.getString(1);
						valor3=cursor.getString(2);
						valor4=cursor.getString(3);
						valor5=cursor.getString(4);
						valor6=cursor.getString(5);
						valor7=cursor.getString(6);
						valor8=cursor.getString(7);
						valor9=cursor.getString(8);
						valor10=cursor.getString(9);
						valor11=cursor.getString(10);
						valor12=cursor.getString(11);
						valor13=cursor.getString(12);
						valor14=cursor.getString(13);
						valor15=cursor.getString(14);
						valor16=cursor.getString(15);
						valor17=cursor.getString(16);
						valor18=cursor.getString(17);
						valor19=cursor.getString(18);
						valor20=cursor.getString(19);
						break;

					case 21: valor1=cursor.getString(0);
						valor2=cursor.getString(1);
						valor3=cursor.getString(2);
						valor4=cursor.getString(3);
						valor5=cursor.getString(4);
						valor6=cursor.getString(5);
						valor7=cursor.getString(6);
						valor8=cursor.getString(7);
						valor9=cursor.getString(8);
						valor10=cursor.getString(9);
						valor11=cursor.getString(10);
						valor12=cursor.getString(11);
						valor13=cursor.getString(12);
						valor14=cursor.getString(13);
						valor15=cursor.getString(14);
						valor16=cursor.getString(15);
						valor17=cursor.getString(16);
						valor18=cursor.getString(17);
						valor19=cursor.getString(18);
						valor20=cursor.getString(19);
						valor21=cursor.getString(20);
						break;

					case 22: valor1=cursor.getString(0);
						valor2=cursor.getString(1);
						valor3=cursor.getString(2);
						valor4=cursor.getString(3);
						valor5=cursor.getString(4);
						valor6=cursor.getString(5);
						valor7=cursor.getString(6);
						valor8=cursor.getString(7);
						valor9=cursor.getString(8);
						valor10=cursor.getString(9);
						valor11=cursor.getString(10);
						valor12=cursor.getString(11);
						valor13=cursor.getString(12);
						valor14=cursor.getString(13);
						valor15=cursor.getString(14);
						valor16=cursor.getString(15);
						valor17=cursor.getString(16);
						valor18=cursor.getString(17);
						valor19=cursor.getString(18);
						valor20=cursor.getString(19);
						valor21=cursor.getString(20);
						valor22=cursor.getString(21);
						break;

					case 23: valor1=cursor.getString(0);
						valor2=cursor.getString(1);
						valor3=cursor.getString(2);
						valor4=cursor.getString(3);
						valor5=cursor.getString(4);
						valor6=cursor.getString(5);
						valor7=cursor.getString(6);
						valor8=cursor.getString(7);
						valor9=cursor.getString(8);
						valor10=cursor.getString(9);
						valor11=cursor.getString(10);
						valor12=cursor.getString(11);
						valor13=cursor.getString(12);
						valor14=cursor.getString(13);
						valor15=cursor.getString(14);
						valor16=cursor.getString(15);
						valor17=cursor.getString(16);
						valor18=cursor.getString(17);
						valor19=cursor.getString(18);
						valor20=cursor.getString(19);
						valor21=cursor.getString(20);
						valor22=cursor.getString(21);
						valor23=cursor.getString(22);
						break;

					case 24: valor1=cursor.getString(0);
						valor2=cursor.getString(1);
						valor3=cursor.getString(2);
						valor4=cursor.getString(3);
						valor5=cursor.getString(4);
						valor6=cursor.getString(5);
						valor7=cursor.getString(6);
						valor8=cursor.getString(7);
						valor9=cursor.getString(8);
						valor10=cursor.getString(9);
						valor11=cursor.getString(10);
						valor12=cursor.getString(11);
						valor13=cursor.getString(12);
						valor14=cursor.getString(13);
						valor15=cursor.getString(14);
						valor16=cursor.getString(15);
						valor17=cursor.getString(16);
						valor18=cursor.getString(17);
						valor19=cursor.getString(18);
						valor20=cursor.getString(19);
						valor21=cursor.getString(20);
						valor22=cursor.getString(21);
						valor23=cursor.getString(22);
						valor24=cursor.getString(23);
						break;
				}
			}while(cursor.moveToNext());
		}
		
		//Cerramos el cursor y la conexión
		CursorSentenciasSelect.cerrarCursor();
		ConexionSQLite.getCerrarSQLite();
	}
	
}