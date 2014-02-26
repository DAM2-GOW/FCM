package gow.fcm.sentencias;

public class SentenciasSQLiteVistaDetallada {

	/*
	 * METODOS ENTRENAMIENTOS.
	 */
	
	public String[] obtenerIDEntrenamientos(String dia, int idEquipo) {

		SentenciasSelectSQLite.seleccionarSQLite("Entrenamientos",new String[]{"COUNT(*)"},"dia='"+dia+"' and id_equipo='"+idEquipo+"'");
		String[] co = SentenciasSelectSQLite.getValores();
		int c = Integer.parseInt(co[0]);
		
		String ID[] = new String[c];
		
		// Ejecutamos la sentencia
		for(int i=0; i < c; i++){
		SentenciasSelectSQLite.seleccionarSQLite("Entrenamientos",
				new String[] { "id_entrenamiento" },
				"dia='"+dia+"' and id_equipo='"+idEquipo+"' LIMIT "+i+",1");
		
		// Obtenemos respuesta de existencia.
		String[] valores = SentenciasSelectSQLite.getValores();
		ID[i] = valores[0];
		}
		
		return ID;
	}
	
	public String[] obtenerTipoEntrenamientos(String dia, int idEquipo) {

		SentenciasSelectSQLite.seleccionarSQLite("Entrenamientos",new String[]{"COUNT(*)"},"dia='"+dia+"' and id_equipo='"+idEquipo+"'");
		String[] co = SentenciasSelectSQLite.getValores();
		int c = Integer.parseInt(co[0]);
		
		String Tipo[] = new String[c];
		
		// Ejecutamos la sentencia
		for(int i=0; i < c; i++){
		SentenciasSelectSQLite.seleccionarSQLite("Entrenamientos",
				new String[] { "tipo" },
				"dia='"+dia+"' and id_equipo='"+idEquipo+"' LIMIT "+i+",1");
		
		// Obtenemos respuesta de existencia.
		String[] valores = SentenciasSelectSQLite.getValores();
		Tipo[i] = valores[0];
		}
		return Tipo;
	}
	
	public String[] obtenerTituloEntrenamientos(String dia, int idEquipo) {

		SentenciasSelectSQLite.seleccionarSQLite("Entrenamientos",new String[]{"COUNT(*)"},"dia='"+dia+"' and id_equipo='"+idEquipo+"'");
		String[] co = SentenciasSelectSQLite.getValores();
		int c = Integer.parseInt(co[0]);
		
		String[] Titulo = new String[c];
		
		// Ejecutamos la sentencia
		for(int i=0; i < c; i++){
		SentenciasSelectSQLite.seleccionarSQLite("Entrenamientos",
				new String[] { "dirigido" },
				"dia='"+dia+"' and id_equipo='"+idEquipo+"' LIMIT "+i+",1");
		
		// Obtenemos respuesta de existencia.
		String[] valores = SentenciasSelectSQLite.getValores();
		Titulo[i] = valores[0];
		}
		return Titulo;
	}
	
	public String[] obtenerHoraEntrenamientos(String dia, int idEquipo) {

		SentenciasSelectSQLite.seleccionarSQLite("Entrenamientos",new String[]{"COUNT(*)"},"dia='"+dia+"' and id_equipo='"+idEquipo+"'");
		String[] co = SentenciasSelectSQLite.getValores();
		int c = Integer.parseInt(co[0]);
		
		String[] Hora = new String[c];
		
		// Ejecutamos la sentencia
		for(int i=0; i < c; i++){
		SentenciasSelectSQLite.seleccionarSQLite("Entrenamientos",
				new String[] { "fecha" },
				"dia='"+dia+"' and id_equipo='"+idEquipo+"' LIMIT "+i+",1");
		
		// Obtenemos respuesta de existencia.
		String[] valores = SentenciasSelectSQLite.getValores();
		String fecha=valores[0];
		//Obtenemos la hora
		String[] horaMinPartido= {"1", "2", "3", "4", "5"};
		int index=fecha.indexOf(" ")+1;
		for(int ii=0;ii<=1;ii++){
			horaMinPartido[ii] = String.valueOf(fecha.charAt(ii+index));	
		}
		if((horaMinPartido[1] == ":")){
			horaMinPartido[4] = horaMinPartido[3];
			horaMinPartido[3] = horaMinPartido[2];
			horaMinPartido[1] = horaMinPartido[0];
			horaMinPartido[2] = ":";
			horaMinPartido[0] = "0";
		}
		Hora[i] = fecha;
		}
		
		return Hora;
	}
	
	public String[] obtenerObservacionesEntrenamientos(String dia, int idEquipo) {

		SentenciasSelectSQLite.seleccionarSQLite("Entrenamientos",new String[]{"COUNT(*)"},"dia='"+dia+"' and id_equipo='"+idEquipo+"'");
		String[] co = SentenciasSelectSQLite.getValores();
		int c = Integer.parseInt(co[0]);
		
		String[] Observ = new String[c];
		
		// Ejecutamos la sentencia
		for(int i=0; i < c; i++){
		SentenciasSelectSQLite.seleccionarSQLite("Entrenamientos",
				new String[] { "observaciones" },
				"dia='"+dia+"' and id_equipo='"+idEquipo+"' LIMIT "+i+",1");
		
		// Obtenemos respuesta de existencia.
		String[] valores = SentenciasSelectSQLite.getValores();
		Observ[i] = valores[0];
		}
		
		return Observ;
	}
	
	/*
	 * METODOS PARTIDOS.
	 */
	
	public String[] obtenerIDPartidos(String dia, int idEquipo) {

		SentenciasSelectSQLite.seleccionarSQLite("Partidos",new String[]{"COUNT(*)"},"dia='"+dia+"' and id_equipo='"+idEquipo+"'");
		String[] co = SentenciasSelectSQLite.getValores();
		int c = Integer.parseInt(co[0]);
		
		String[] ID = new String[c];
		
		// Ejecutamos la sentencia
		for(int i=0; i < c; i++){
		SentenciasSelectSQLite.seleccionarSQLite("Partidos",
				new String[] { "id_partido" },
				"dia='"+dia+"' and id_equipo='"+idEquipo+"' LIMIT "+i+",1");
		
		// Obtenemos respuesta de existencia.
		String[] valores = SentenciasSelectSQLite.getValores();
		ID[i] = valores[0];
		}
		
		return ID;
	}
	
	public String[] obtenerLugarPartidos(String dia, int idEquipo) {

		SentenciasSelectSQLite.seleccionarSQLite("Partidos",new String[]{"COUNT(*)"},"dia='"+dia+"' and id_equipo='"+idEquipo+"'");
		String[] co = SentenciasSelectSQLite.getValores();
		int c = Integer.parseInt(co[0]);
		
		String[] Lugar = new String[c];
		
		// Ejecutamos la sentencia
		for(int i=0; i < c; i++){
		SentenciasSelectSQLite.seleccionarSQLite("Partidos",
				new String[] { "lugar" },
				"dia='"+dia+"' and id_equipo='"+idEquipo+"' LIMIT "+i+",1");
		
		// Obtenemos respuesta de existencia.
		String[] valores = SentenciasSelectSQLite.getValores();
		Lugar[i] = valores[0];
		}
		
		return Lugar;
	}
	
	public String[] obtenerRivalPartidos(String dia, int idEquipo) {

		SentenciasSelectSQLite.seleccionarSQLite("Partidos",new String[]{"COUNT(*)"},"dia='"+dia+"' and id_equipo='"+idEquipo+"'");
		String[] co = SentenciasSelectSQLite.getValores();
		int c = Integer.parseInt(co[0]);
		
		String[] Rival = new String[c];
		
		// Ejecutamos la sentencia
		for(int i=0; i < c; i++){
		SentenciasSelectSQLite.seleccionarSQLite("Entrenamientos",
				new String[] { "rival" },
				"dia='"+dia+"' and id_equipo='"+idEquipo+"' LIMIT "+i+",1");
		
		// Obtenemos respuesta de existencia.
		String[] valores = SentenciasSelectSQLite.getValores();
		Rival[i] = valores[0];
		}
		
		return Rival;
	}
	
	public String[] obtenerHoraPartidos(String dia, int idEquipo) {

		SentenciasSelectSQLite.seleccionarSQLite("Partidos",new String[]{"COUNT(*)"},"dia='"+dia+"' and id_equipo='"+idEquipo+"'");
		String[] co = SentenciasSelectSQLite.getValores();
		int c = Integer.parseInt(co[0]);
		
		String[] Hora = new String[c];
		
		// Ejecutamos la sentencia
		for(int i=0; i < c; i++){
		SentenciasSelectSQLite.seleccionarSQLite("Partidos",
				new String[] { "fecha" },
				"dia='"+dia+"' and id_equipo='"+idEquipo+"' LIMIT "+i+",1");
		
		// Obtenemos respuesta de existencia.
		String[] valores = SentenciasSelectSQLite.getValores();
		
		String fecha=valores[0];
		//Obtenemos la hora
		String[] horaMinPartido= {"1", "2", "3", "4", "5"};
		int index=fecha.indexOf(" ")+1;
		for(int ii=0;ii<=1;ii++){
			horaMinPartido[ii] = String.valueOf(fecha.charAt(ii+index));	
		}
		if((horaMinPartido[1] == ":")){
			horaMinPartido[4] = horaMinPartido[3];
			horaMinPartido[3] = horaMinPartido[2];
			horaMinPartido[1] = horaMinPartido[0];
			horaMinPartido[2] = ":";
			horaMinPartido[0] = "0";
		}
		Hora[i] = fecha;
		
		}
		
		return Hora;
	}
}
