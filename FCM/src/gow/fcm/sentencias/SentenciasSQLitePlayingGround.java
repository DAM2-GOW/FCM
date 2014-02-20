package gow.fcm.sentencias;

import android.content.Context;

public class SentenciasSQLitePlayingGround {
	private String[] nombreApellidos, posicion;
	private int[] id_jugador, tipo, dorsal;

	public SentenciasSQLitePlayingGround() {
		// Constructor.
	}

	/**
	 * Obtiene todos los jugadores filtrados por 'ID'.
	 * 
	 * @param context
	 * @param jugadores : indica el 'ID' de jugadores a obtener.
	 */
	public void ObtenerDatosDelimitadosJugadores(Context context,
			int[] jugadores) {
		SentenciasSelectSQLite.borrarTodosValores();

		String IDsentence = "'" + jugadores[0] + "'";
		
		for (int i = 1; i < jugadores.length; i++)
			IDsentence = IDsentence + ", '" + jugadores[i] + "'";
		
		String[] campos = { "id_jugador", "nombre", "apellidos", "tipo",
				"posicion", "dorsal" };
		
		for (int i = 0; i < jugadores.length; i++) {
			SentenciasSelectSQLite.seleccionarSQLite("jugadores", campos,
					"id_jugador in (" + IDsentence + ") LIMIT 0," + (i + 1));
			Object[] valores = SentenciasSelectSQLite.getValores();
			// INICIALIZAR ARRAYS CON COUNT().
			id_jugador[i] = (Integer) valores[0];
			nombreApellidos[i] = (String) valores[1] + " "
					+ (String) valores[2];
			tipo[i] = (Integer) valores[3];
			posicion[i] = (String) valores[4];
			dorsal[i] = (Integer) valores[5];
		}
	}

	/**
	 * Obtiene todos los jugadores sin filtros.
	 * 
	 * @param context
	 */
	public void ObtenerDatosCompletosJugadores(Context context) {
		SentenciasSelectSQLite.borrarTodosValores();
		
		String[] campos = { "id_jugador", "nombre", "apellidos", "tipo",
				"posicion", "dorsal" };

		/*for (int i = 0; i < jugadores.length; i++) { // Obtener numero maximo. 
			SentenciasSelectSQLite.seleccionarSQLite("jugadores", campos,
					"id_jugador = id_jugador LIMIT 0," + (i + 1));
			Object[] valores = SentenciasSelectSQLite.getValores();
			// INICIALIZAR ARRAYS CON COUNT().
			id_jugador[i] = (Integer) valores[0];
			nombreApellidos[i] = (String) valores[1] + " "
					+ (String) valores[2];
			tipo[i] = (Integer) valores[3];
			posicion[i] = (String) valores[4];
			dorsal[i] = (Integer) valores[5];
		}*/
	}

	/**
	 * Obtiene todos los jugadores a excepcion de aquellos descartados por 'ID'.
	 * 
	 * @param context
	 * @param jugadores : indica el 'ID' de los jugadores a descartar.
	 */
	public void ObtenerDatosDescartandoJugadores(Context context, int[] jugadores) {
		SentenciasSelectSQLite.borrarTodosValores();
		
		String[] campos = { "id_jugador", "nombre", "apellidos", "tipo",
				"posicion", "dorsal" };

		String IDsentence = "'"+jugadores[0]+"'";

		for(int i=1;i<jugadores.length;i++)
			IDsentence = IDsentence+", '"+jugadores[i]+"'";

		for (int i = 0; i < jugadores.length; i++) {
			SentenciasSelectSQLite.seleccionarSQLite("jugadores", campos,
					"id_jugador NOT IN ("+IDsentence+") LIMIT 0," + (i + 1));
			Object[] valores = SentenciasSelectSQLite.getValores();
			// INICIALIZAR ARRAYS CON COUNT().
			id_jugador[i] = (Integer) valores[0];
			nombreApellidos[i] = (String) valores[1] + " "
					+ (String) valores[2];
			tipo[i] = (Integer) valores[3];
			posicion[i] = (String) valores[4];
			dorsal[i] = (Integer) valores[5];
		}
	}

	public String[] getNombreApellidos() {
		return nombreApellidos;
	}

	public String[] getPosicion() {
		return posicion;
	}

	public int[] getId_jugador() {
		return id_jugador;
	}

	public int[] getTipo() {
		return tipo;
	}

	public int[] getDorsal() {
		return dorsal;
	}
}
