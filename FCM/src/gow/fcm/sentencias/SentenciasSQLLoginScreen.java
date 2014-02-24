package gow.fcm.sentencias;


public class SentenciasSQLLoginScreen {

	public int comprobarNombreUsuario(String usuario) {

		// Ejcutamos la sentencia
		SentenciasSelectSQLite
				.seleccionarSQLite("Entrenadores",
						new String[] { "COUNT (usuario)" }, "usuario='"+usuario+"'");

		// Obtenemos respuesta de existencia.
		String[] valores = SentenciasSelectSQLite.getValores();
		int userName = Integer.parseInt(valores[0]);
		
		return userName;
	}

	public int comprobarPasswordUsuario(String userName, String clave) {

		// Ejcutamos la sentencia.
		SentenciasSelectSQLite.seleccionarSQLite("Entrenadores",
				new String[] { "COUNT (clave)" }, "usuario='"+userName+"' and clave='"+clave+"'");
		
		// Obtenemos respuesta de existencia.
		String[] valores = SentenciasSelectSQLite.getValores();
		int password = Integer.parseInt(valores[0]);
		
		return password;
	}

	public int comprobarPreguntaSeguridad(String userName, String respuesta) {

		// Ejcutamos la sentencia
		SentenciasSelectSQLite.seleccionarSQLite("Entrenadores",
				new String[] { "COUNT (clave)" }, "usuario='"+userName+" and clave='"+respuesta+"'");

		// Obtenemos respuesta de existencia.
		String[] valores = SentenciasSelectSQLite.getValores();
		int pregunta = Integer.parseInt(valores[0]);
		
		return pregunta;
	}
	
	public String recuperarPassword(String userName, String respuesta) {

		// Ejcutamos la sentencia
		SentenciasSelectSQLite.seleccionarSQLite("Entrenadores",
				new String[] { "clave" }, "usuario='"+userName+" and clave='"+respuesta+"'");

		// Obtenemos respuesta de existencia.
		String[] valores = SentenciasSelectSQLite.getValores();
		String pregunta = valores[0];
		
		return pregunta;
	}
}