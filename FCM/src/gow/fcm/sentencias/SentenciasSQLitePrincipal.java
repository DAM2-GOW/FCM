package gow.fcm.sentencias;

import gow.fcm.basedatos.ConexionSQLite;
import gow.fcm.sharefprefs.DatosFootball;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SentenciasSQLitePrincipal{
	
	private static int total; //Variable para usarla en las sentencias
	private static String[] vectorNombre,vectorApellidos,vectorPosicion,vectorDorsal; //Variables usadas para almacenar valores a usar posteriormente
	//Variables de la aplicación o de la base de datos
	private static String nombreEntrenador=null,apellidosEntrenador=null,fotoEntrenador=null,nombreEquipo=null,tipoEntrenamiento=null,dirigidoEntrenamiento,lugarPartido=null,rivalPartido;
	private static Date fechaEntrenamiento,fechaPartido; //Variables para usarlas en las sentencias
	@SuppressLint("SimpleDateFormat")
	private static SimpleDateFormat formatoTexto=new SimpleDateFormat("yyyy-MM-dd"); //Formato de conversión a Date
	
	//Almacena el nombre del entrenador
	public static String getNombreEntrenador(){
		return nombreEntrenador;
	}
	
	//Resetea el nombre del entrenador
	public static void setNombreEntrenador(){
		nombreEntrenador=null;
	}
	
	//Almacena los apellidos del entrenador
	public static String getApellidosEntrenador(){
		return apellidosEntrenador;
	}
	
	//Resetea los apellidos del entrenador
	public static void setApellidosEntrenador(){
		apellidosEntrenador=null;
	}
	
	//Almacena la foto del entrenador
	public static String getFotoEntrenador(){
		return fotoEntrenador;
	}
	
	//Resetea la foto del entrenador
	public static void setFotoEntrenador(){
		fotoEntrenador=null;
	}
	
	//Almacena el nombre del equipo
	public static String getNombreEquipo(){
		return nombreEquipo;
	}
	
	//Resetea el nombre del equipo
	public static void setNombreEquipo(){
		nombreEquipo=null;
	}
	
	//Almacena los nombres
	public static String[] getVectorNombre(){
		return vectorNombre;
	}
	
	//Almacena los apellidos
	public static String[] getVectorApellidos(){
		return vectorApellidos;
	}
	
	//Almacena las posiciones
	public static String[] getVectorPosicion(){
		return vectorPosicion;
	}
	
	//Almacena los dorsales
	public static String[] getVectorDorsal(){
		return vectorDorsal;
	}
	
	//Almacena el tipo de entrenamiento
	public static String getTipoEntrenamiento(){
		return tipoEntrenamiento;
	}
	
	//Resetea el tipo de entrenamiento
	public static void setTipoEntrenamiento(){
		tipoEntrenamiento=null;
	}
	
	//Almacena a quien va dirigido el entrenamiento
	public static String getDirigidoEntrenamiento(){
		return dirigidoEntrenamiento;
	}
	
	//Almacena la fecha del entrenamiento
	public static Date getFechaEntrenamiento(){
		return fechaEntrenamiento;
	}
	
	//Almacena el lugar del partido
	public static String getLugarPartido(){
		return lugarPartido;
	}
	
	//Resetea el lugar del partido
	public static void setLugarPartido(){
		lugarPartido=null;
	}
	
	//Almacena el rival del partido
	public static String getRivalPartido(){
		return rivalPartido;
	}
	
	//Almacena la fecha del partido
	public static Date getFechaPartido(){
		return fechaPartido;
	}
	
	//Este método obtiene los datos del entrenador
	public static void getDatosEntrenador(Context contexto){
		ConexionSQLite.getAbrirSQLite();
		DatosFootball.getDatosFootball(contexto);
		int id=DatosFootball.getIdEntrenador();
		CursorSentenciasSelect.abrirCursor("SELECT nombre,apellidos,foto FROM Entrenadores WHERE id_entrenador="+id+";");
		Cursor cursor=CursorSentenciasSelect.getCursor();
		
		//Nos aseguramos de que existe al menos un registro
		if(cursor.moveToFirst()){
			//Recorremos el cursor hasta que no haya más registros
			do{
				nombreEntrenador=cursor.getString(0);
				apellidosEntrenador=cursor.getString(1);
				fotoEntrenador=cursor.getString(2);
			}while(cursor.moveToNext());
		}
		
		//Cerramos el cursor y la conexión
		CursorSentenciasSelect.cerrarCursor();
		ConexionSQLite.getCerrarSQLite();
	}
	
	//Este método actualiza la foto del entrenador
	public static void actualizarFotoEntrenador(String foto,Context contexto){
		//Abrimos la conexion a la base de datos
		ConexionSQLite.getAbrirSQLite();
		SQLiteDatabase bd=ConexionSQLite.getConexion();
		DatosFootball.getDatosFootball(contexto);
		int id=DatosFootball.getIdEntrenador();
		
		//Realizamos la sentencia
		String sentencia="UPDATE Entrenadores SET foto='"+foto+"' WHERE id_entrenador="+id+";";
		bd.execSQL(sentencia);
		
		//Cerramos la conexión
		ConexionSQLite.getCerrarSQLite();
	}
	
	//Este método obtiene el nombre del equipo
	public static void getNombreEquipo(Context contexto){
		ConexionSQLite.getAbrirSQLite();
		DatosFootball.getDatosFootball(contexto);
		int id=DatosFootball.getIdEquipo();
		CursorSentenciasSelect.abrirCursor("SELECT nombre FROM Equipos WHERE id_equipo="+id+";");
		Cursor cursor=CursorSentenciasSelect.getCursor();
		
		//Nos aseguramos de que existe al menos un registro
		if(cursor.moveToFirst()){
			//Recorremos el cursor hasta que no haya más registros
			do{
				nombreEquipo=cursor.getString(0);
			}while(cursor.moveToNext());
		}
		
		//Cerramos el cursor y la conexión
		CursorSentenciasSelect.cerrarCursor();
		ConexionSQLite.getCerrarSQLite();
	}
	
	//Este método obtiene si existe algún jugador del equipo seleccionado
	private static void getTotalJugadores(Context contexto){
		ConexionSQLite.getAbrirSQLite();
		DatosFootball.getDatosFootball(contexto);
		int id=DatosFootball.getIdEquipo();
		CursorSentenciasSelect.abrirCursor("SELECT COUNT(*) AS Total FROM Jugadores WHERE id_equipo="+id+";");
		Cursor cursor=CursorSentenciasSelect.getCursor();
		
		//Nos aseguramos de que existe al menos un registro
		if(cursor.moveToFirst()){
			//Recorremos el cursor hasta que no haya más registros
			do{
				total=cursor.getInt(0);
			}while(cursor.moveToNext());
		}
		
		//Cerramos el cursor y la conexión
		CursorSentenciasSelect.cerrarCursor();
		ConexionSQLite.getCerrarSQLite();
	}
	
	//Este método obtiene los datos de los jugadores
	public static void getDatosJugadores(Context contexto){
		getTotalJugadores(contexto);
		int max=total;
		
		//Declaramos el tamaño de las matrices
		vectorNombre=new String[max];
		vectorApellidos=new String[max];
		vectorPosicion=new String[max];
		vectorDorsal=new String[max];
		
		ConexionSQLite.getAbrirSQLite();
		DatosFootball.getDatosFootball(contexto);
		int id=DatosFootball.getIdEquipo();
		CursorSentenciasSelect.abrirCursor("SELECT nombre,apellidos,posicion,dorsal FROM Jugadores WHERE id_equipo="+id+" ORDER BY tipo,posicion;");
		Cursor cursor=CursorSentenciasSelect.getCursor();
		
		//Nos aseguramos de que existe al menos un registro
		if(cursor.moveToFirst()){
			//Recorremos el cursor hasta que no haya más registros
			int i=0;
			do{
				String nombre=cursor.getString(0);
				vectorNombre[i]=nombre;
				String apellidos=cursor.getString(1);
				vectorApellidos[i]=apellidos;
				String posicion=cursor.getString(2);
				vectorPosicion[i]=posicion;
				String dorsal=String.valueOf(cursor.getInt(3));
				vectorDorsal[i]=dorsal;
				i=i+1;
			}while(cursor.moveToNext());
		}
		
		//Cerramos el cursor y la conexión
		CursorSentenciasSelect.cerrarCursor();
		ConexionSQLite.getCerrarSQLite();
	}
	
	//Este método borra los jugadores
	public static void borrarJugador(int dorsal,Context contexto){
		ConexionSQLite.getAbrirSQLite();
		SQLiteDatabase bd=ConexionSQLite.getConexion();
		DatosFootball.getDatosFootball(contexto);
		int id=DatosFootball.getIdEquipo();
		CursorSentenciasSelect.abrirCursor("SELECT id_jugador FROM Jugadores WHERE id_equipo="+id+" AND dorsal="+dorsal+";");
		Cursor cursor=CursorSentenciasSelect.getCursor();
		int idJugador=0; //Declaramos una variable para almacenar el ID del jugador
		
		//Nos aseguramos de que existe al menos un registro
		if(cursor.moveToFirst()){
			//Recorremos el cursor hasta que no haya más registros
			do{
				idJugador=cursor.getInt(0);
			}while(cursor.moveToNext());
		}
		
		//Cerramos el cursor
		CursorSentenciasSelect.cerrarCursor();
		String sentencia="DELETE FROM Jugadores WHERE id_jugador="+idJugador+";";
		bd.execSQL(sentencia);
		
		//Cerramos la conexión
		ConexionSQLite.getCerrarSQLite();
	}
	
	//Este método obtiene los datos del entrenamiento
	public static void getDatosEntrenamientos(String fecha,Context contexto){
		ConexionSQLite.getAbrirSQLite();
		DatosFootball.getDatosFootball(contexto);
		int id=DatosFootball.getIdEquipo();
		CursorSentenciasSelect.abrirCursor("SELECT tipo,dirigido,fecha FROM Entrenamientos WHERE id_equipo="+id+" AND dia='"+fecha+"';");
		Cursor cursor=CursorSentenciasSelect.getCursor();
		
		//Nos aseguramos de que existe al menos un registro
		if(cursor.moveToFirst()){
			//Recorremos el cursor hasta que no haya más registros
			do{
				tipoEntrenamiento=cursor.getString(0);
				dirigidoEntrenamiento=cursor.getString(1);
				String fechaTraining=cursor.getString(2);
				try{
					fechaEntrenamiento=formatoTexto.parse(fechaTraining);
				}catch(ParseException e){
					e.printStackTrace();
				}
			}while(cursor.moveToNext());
		}
		
		//Cerramos el cursor y la conexión
		CursorSentenciasSelect.cerrarCursor();
		ConexionSQLite.getCerrarSQLite();
	}
	
	//Este método borrar los entrenamientos
	public static void borrarEventoEntrenamiento(String fecha,Context contexto){
		ConexionSQLite.getAbrirSQLite();
		SQLiteDatabase bd=ConexionSQLite.getConexion();
		DatosFootball.getDatosFootball(contexto);
		int id=DatosFootball.getIdEquipo();
		CursorSentenciasSelect.abrirCursor("SELECT id_entrenamiento FROM Entrenamientos WHERE id_equipo="+id+" AND dia='"+fecha+"';");
		Cursor cursor=CursorSentenciasSelect.getCursor();
		int idEntrenamiento=0; //Declaramos una variable para almacenar el ID del entrenamiento
		
		//Nos aseguramos de que existe al menos un registro
		if(cursor.moveToFirst()){
			//Recorremos el cursor hasta que no haya más registros
			do{
				idEntrenamiento=cursor.getInt(0);
			}while(cursor.moveToNext());
		}
		
		//Cerramos el cursor
		CursorSentenciasSelect.cerrarCursor();
		String sentencia="DELETE FROM Entrenamientos WHERE id_entrenamiento="+idEntrenamiento+";";
		bd.execSQL(sentencia);
		
		//Cerramos la conexión
		ConexionSQLite.getCerrarSQLite();
	}
	
	//Este método obtiene los datos del partido
	public static void getDatosPartidos(String fecha,Context contexto){
		ConexionSQLite.getAbrirSQLite();
		DatosFootball.getDatosFootball(contexto);
		int id=DatosFootball.getIdEquipo();
		CursorSentenciasSelect.abrirCursor("SELECT lugar,rival,fecha FROM Partidos WHERE id_equipo="+id+" AND dia='"+fecha+"';");
		Cursor cursor=CursorSentenciasSelect.getCursor();
		
		//Nos aseguramos de que existe al menos un registro
		if(cursor.moveToFirst()){
			//Recorremos el cursor hasta que no haya más registros
			do{
				lugarPartido=cursor.getString(0);
				rivalPartido=cursor.getString(1);
				String fechaMatch=cursor.getString(2);
				try{
					fechaPartido=formatoTexto.parse(fechaMatch);
				}catch (ParseException e){
					e.printStackTrace();
				}
			}while(cursor.moveToNext());
		}
		
		//Cerramos el cursor y la conexión
		CursorSentenciasSelect.cerrarCursor();
		ConexionSQLite.getCerrarSQLite();
	}
	
	//Este método borrar los partidos
	public static void borrarEventoPartido(String fecha,Context contexto){
		ConexionSQLite.getAbrirSQLite();
		SQLiteDatabase bd=ConexionSQLite.getConexion();
		DatosFootball.getDatosFootball(contexto);
		int id=DatosFootball.getIdEquipo();
		CursorSentenciasSelect.abrirCursor("SELECT id_partido FROM Partidos WHERE id_equipo="+id+" AND dia='"+fecha+"';");
		Cursor cursor=CursorSentenciasSelect.getCursor();
		int idPartido=0; //Declaramos una variable para almacenar el ID del partido
		
		//Nos aseguramos de que existe al menos un registro
		if(cursor.moveToFirst()){
			//Recorremos el cursor hasta que no haya más registros
			do{
				idPartido=cursor.getInt(0);
			}while(cursor.moveToNext());
		}
		
		//Cerramos el cursor
		CursorSentenciasSelect.cerrarCursor();
		String sentencia="DELETE FROM Partidos WHERE id_partido="+idPartido+";";
		bd.execSQL(sentencia);
		
		//Cerramos la conexión
		ConexionSQLite.getCerrarSQLite();
	}
	
}