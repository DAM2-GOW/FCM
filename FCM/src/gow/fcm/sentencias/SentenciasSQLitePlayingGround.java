package gow.fcm.sentencias;

import gow.fcm.sharefprefs.DatosFootball;
import android.content.Context;
import android.util.Log;

public class SentenciasSQLitePlayingGround {
	private String[] nombreApellidos, posicion;
	private int[] id_jugador, tipo, dorsal;
	private int EquipoEnUso;
	private int countJugadores;

	// Constructor.
	public SentenciasSQLitePlayingGround(Context context) {
		// Obtenemos el equipo en uso por el entrenador. 
		// Usamos ese equipo para delimitar las busquedas.
		Log.d("DEBUG", "ANTES");
		DatosFootball.getDatosFootball(context);
		Log.d("DEBUG", "CREADA");
		EquipoEnUso = DatosFootball.getIdEquipo();
		Log.d("EQUIPO", ""+EquipoEnUso);
		// Obtenemos el numero total de jugadores para inicializar los Arrays.
		SentenciasSelectSQLite.seleccionarSQLite("jugadores", new String[]{"count(*)"}, "id_equipo = '"+EquipoEnUso+"'");
		String[] valores = SentenciasSelectSQLite.getValores();
		countJugadores = Integer.parseInt(valores[0]);
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
		BorrarCampos();

		String IDsentence = "'" + jugadores[0] + "'";
		
		for (int i = 1; i < jugadores.length; i++)
			IDsentence = IDsentence + ", '" + jugadores[i] + "'";
		
		String[] campos = { "id_jugador", "nombre", "apellidos", "tipo",
				"posicion", "dorsal" };

		countJugadores = jugadores.length;
		// Inicializamos arrays segun necesidad de espacio.
		id_jugador = new int[countJugadores];
		nombreApellidos = new String[countJugadores];
		tipo = new int[countJugadores];
		posicion = new String[countJugadores];
		dorsal = new int[countJugadores];

		for (int i = 0; i < jugadores.length; i++) {
			SentenciasSelectSQLite.seleccionarSQLite("jugadores", campos,
					"id_equipo = '"+EquipoEnUso+"' and id_jugador in (" + IDsentence + ") LIMIT 0," + (i + 1));
			
			String[] valores = SentenciasSelectSQLite.getValores();
			
			// Obtenemos los datos.
			id_jugador[i] = Integer.parseInt(valores[0]);
			nombreApellidos[i] = valores[1]+" "+valores[2];
			tipo[i] = Integer.parseInt(valores[3]);
			posicion[i] = valores[4];
			dorsal[i] = Integer.parseInt(valores[5]);
		}
	}

	/**
	 * Obtiene todos los jugadores sin filtros.
	 * 
	 * @param context
	 */
	public void ObtenerDatosCompletosJugadores(Context context) {
		SentenciasSelectSQLite.borrarTodosValores();
		BorrarCampos();
		
		String[] campos = { "id_jugador", "nombre", "apellidos", "tipo",
				"posicion", "dorsal" };

		SentenciasSelectSQLite.seleccionarSQLite("jugadores", new String[]{"count(*)"}, "id_equipo = '"+EquipoEnUso+"'");
		String[] valor = SentenciasSelectSQLite.getValores();
		countJugadores = Integer.parseInt(valor[0]);
		// Inicializamos arrays segun necesidad de espacio.
					id_jugador = new int[countJugadores];
					nombreApellidos = new String[countJugadores];
					tipo = new int[countJugadores];
					posicion = new String[countJugadores];
					dorsal = new int[countJugadores];

		for (int i = 0; i < countJugadores; i++) {
			SentenciasSelectSQLite.seleccionarSQLite("jugadores", campos,
					"id_equipo = '"+EquipoEnUso+"' LIMIT "+i+",1");
			String[] valores = SentenciasSelectSQLite.getValores();

			// Obtenemos los datos.
			id_jugador[i] = Integer.parseInt(valores[0]);
			nombreApellidos[i] = valores[1]+" "+valores[2];
			tipo[i] = Integer.parseInt(valores[3]);
			posicion[i] = valores[4];
			dorsal[i] = Integer.parseInt(valores[5]);
		}
	}

	/**
	 * Obtiene todos los jugadores a excepcion de aquellos descartados por 'ID'.
	 * 
	 * @param context
	 * @param jugadores : indica el 'ID' de los jugadores a descartar.
	 */
	public void ObtenerDatosDescartandoJugadores(Context context, int[] jugadores) {
		SentenciasSelectSQLite.borrarTodosValores();
		BorrarCampos();
		
		String[] campos = { "id_jugador", "nombre", "apellidos", "tipo",
				"posicion", "dorsal" };

		String IDsentence = "'"+jugadores[0]+"'";

		for(int i=1;i<jugadores.length;i++)
			IDsentence = IDsentence+", '"+jugadores[i]+"'";

		SentenciasSelectSQLite.seleccionarSQLite("jugadores", new String[]{"count(*)"}, "id_equipo = '"+EquipoEnUso+"'");
		String[] valor = SentenciasSelectSQLite.getValores();
		int o = Integer.parseInt(valor[0]);
		countJugadores = (o - jugadores.length);
		// Inicializamos arrays segun necesidad de espacio.
		id_jugador = new int[countJugadores];
		nombreApellidos = new String[countJugadores];
		tipo = new int[countJugadores];
		posicion = new String[countJugadores];
		dorsal = new int[countJugadores];

		for (int i = 0; i < jugadores.length; i++) {
			SentenciasSelectSQLite.seleccionarSQLite("jugadores", campos,
					"id_equipo = '"+EquipoEnUso+"' and id_jugador NOT IN ("+IDsentence+") LIMIT 0," + (i + 1));
			String[] valores = SentenciasSelectSQLite.getValores();
				
			// Obtenemos los datos.
				id_jugador[i] = Integer.parseInt(valores[0]);
				nombreApellidos[i] = valores[1]+" "+valores[2];
				tipo[i] = Integer.parseInt(valores[3]);
				posicion[i] = valores[4];
				dorsal[i] = Integer.parseInt(valores[5]);
		}
	}

	/**
	 * Borra los Arrays para permitir usarlos de nuevo con otras
	 * especificaciones de cantidad.
	 */
	private void BorrarCampos(){
		id_jugador = null;
		nombreApellidos = null;
		tipo = null;
		posicion = null;
		dorsal = null;
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
