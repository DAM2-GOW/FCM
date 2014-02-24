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
				new String[] { "COUNT (id_entrenador)" }, "usuario='"+userName+"' and respuesta_seguridad='"+respuesta+"'");

		// Obtenemos respuesta de existencia.
		String[] valores = SentenciasSelectSQLite.getValores();
		int pregunta = Integer.parseInt(valores[0]);
		
		return pregunta;
	}
	
	public String recuperarPassword(String userName, String respuesta) {

		// Ejcutamos la sentencia
		SentenciasSelectSQLite.seleccionarSQLite("Entrenadores",
				new String[] { "clave" }, "usuario='"+userName+"' and respuesta_seguridad='"+respuesta+"'");

		// Obtenemos respuesta de existencia.
		String[] valores = SentenciasSelectSQLite.getValores();
		String resp = valores[0];
		
		return resp;
	}
	
	public int obtenerIDEntrenador(String userName) {

		// Ejcutamos la sentencia
		SentenciasSelectSQLite.seleccionarSQLite("Entrenadores",
				new String[] { "id_entrenador" }, "usuario='"+userName+"'");

		// Obtenemos respuesta de existencia.
		String[] valores = SentenciasSelectSQLite.getValores();
		int ID = Integer.parseInt(valores[0]);
		
		return ID;
	}
	
	public int obtenerEquiposEntrenador(int IDentrenador) {

		// Ejcutamos la sentencia
		SentenciasSelectSQLite.seleccionarSQLite("Entrenadores",
				new String[] { "id_equipo" }, "id_entrenador='"+IDentrenador+"'");

		// Obtenemos respuesta de existencia.
		String[] valores = SentenciasSelectSQLite.getValores();
		int teams = Integer.parseInt(valores[0]);

		return teams;
	}
	
	public String obtenerNombreEquipos(int equipo) {

		// Ejcutamos la sentencia
		SentenciasSelectSQLite.seleccionarSQLite("Equipos",
				new String[] { "nombre" }, "id_equipo = '"+equipo+"'");

		// Obtenemos respuesta de existencia.
		String[] valores = SentenciasSelectSQLite.getValores();
		String nombre = valores[0];
		
		return nombre;
	}
	
	public int obtenerIDEquipoPorNombre(String nombreEquipo) {

		// Ejcutamos la sentencia
		SentenciasSelectSQLite.seleccionarSQLite("Equipos",
				new String[] { "id_equipo" }, "nombre='"+nombreEquipo+"'");

		// Obtenemos respuesta de existencia.
		String[] valores = SentenciasSelectSQLite.getValores();
		int idTeam = Integer.parseInt(valores[0]);

		return idTeam;
	}
	
	public int obtenerPreguntaSeguridad(String user){
		// Ejcutamos la sentencia
				SentenciasSelectSQLite.seleccionarSQLite("Entrenadores",
						new String[] { "pregunta_seguridad" }, "usuario='"+user+"'");
				
		// Obtenemos respuesta de existencia.
		String[] valores = SentenciasSelectSQLite.getValores();
		int pregunta = Integer.parseInt(valores[0]);
			
		return pregunta;
	}
}