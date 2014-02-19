package gow.fcm.utilidades;

import android.content.Context;
import gow.fcm.sentencias.SentenciasSelectSQLite;
import gow.fcm.sharefprefs.DatosFootball;

public class RegistroUsuarios{
	
	private String nombreUsuario; //Nombre de usuario del entrenador
	
	public String getNombreUsuario(){
		return nombreUsuario;
	}
	
	public void comprobarNombreUsuario(Context contexto,String usuario){
		//Obtenemos el identificador
		DatosFootball.getDatosFootball(contexto);
		
		//Ejcutamos la sentencia
		SentenciasSelectSQLite.seleccionarSQLite("Entrenadores",new String[]{"COUNT (usuario)"},"usuario='"+usuario+"'");
		
		//Almacenamos los valores
		String[] valores=(String[]) SentenciasSelectSQLite.getValores();
		nombreUsuario=valores[0];
	}
	
}