package gow.fcm.basedatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class CreateUpgradeSQLite extends SQLiteOpenHelper{
	
	//Estas variables crean la base de Datos si no existe, es decir solo se crearán la primera vez
	private String sqlCreate1="CREATE TABLE IF NOT EXISTS Equipos ( id_equipo INTEGER PRIMARY KEY ASC AUTOINCREMENT NOT NULL UNIQUE, nombre VARCHAR( 45 ) NOT NULL );";
	private String sqlCreate2="CREATE TABLE IF NOT EXISTS Entrenadores ( id_entrenador INTEGER PRIMARY KEY ASC AUTOINCREMENT NOT NULL UNIQUE, id_equipo INTEGER NOT NULL UNIQUE, nombre VARCHAR( 45 ) NOT NULL, apellidos VARCHAR( 45 ) NOT NULL, foto VARCHAR( 45 ), CONSTRAINT 'id_equipo1' FOREIGN KEY ( id_equipo ) REFERENCES Equipos ( id_equipo ) ON DELETE CASCADE ON UPDATE CASCADE );";
	private String sqlCreate3="CREATE TABLE IF NOT EXISTS Jugadores ( id_jugador INTEGER PRIMARY KEY ASC AUTOINCREMENT NOT NULL UNIQUE, id_equipo INTEGER NOT NULL, nombre VARCHAR( 45 ) NOT NULL, apellidos VARCHAR( 45 ) NOT NULL, edad INT NOT NULL, posicion VARCHAR( 45 ) NOT NULL, tipo VARCHAR( 45 ) NOT NULL, dorsal INT NOT NULL, foto VARCHAR( 45 ), CONSTRAINT 'id_equipo3' FOREIGN KEY ( id_equipo ) REFERENCES Equipos ( id_equipo ) ON DELETE CASCADE ON UPDATE CASCADE );";
	private String sqlCreate4="CREATE TABLE IF NOT EXISTS Entrenamientos ( id_entrenamiento INTEGER PRIMARY KEY ASC AUTOINCREMENT NOT NULL UNIQUE, id_equipo INTEGER NOT NULL UNIQUE, tipo VARCHAR( 45 ) NOT NULL, dirigido VARCHAR( 45 ) NOT NULL, dia DATE NOT NULL, fecha DATETIME NOT NULL, CONSTRAINT 'id_equipo4' FOREIGN KEY ( id_equipo ) REFERENCES Equipos ( id_equipo ) ON DELETE CASCADE ON UPDATE CASCADE );";
	private String sqlCreate5="CREATE TABLE IF NOT EXISTS Partidos ( id_partido INTEGER PRIMARY KEY ASC AUTOINCREMENT NOT NULL UNIQUE, id_equipo INTEGER NOT NULL UNIQUE, lugar VARCHAR( 45 ) NOT NULL, rival VARCHAR( 45 ) NOT NULL, dia DATE NOT NULL, fecha DATETIME NOT NULL, CONSTRAINT 'id_equipo2' FOREIGN KEY ( id_equipo ) REFERENCES Equipos ( id_equipo ) ON DELETE CASCADE ON UPDATE CASCADE );";
	private String sqlCreate6="CREATE TABLE IF NOT EXISTS Estadisticas_Entrenamientos ( id_entrenamiento INTEGER PRIMARY KEY NOT NULL, id_jugador INTEGER NOT NULL, distancia_carrera INT NOT NULL DEFAULT ( 0 ), repeticiones_pesas INT NOT NULL DEFAULT ( 0 ), field_goals INT NOT NULL DEFAULT ( 0 ), punts INT NOT NULL DEFAULT ( 0 ), tiempo_carrera DATETIME, musculo_pesas VARCHAR( 45 ), field_goals_anotados INT, punts_anotados INT, CONSTRAINT 'id_entrenamiento' FOREIGN KEY ( id_entrenamiento ) REFERENCES Entrenamientos ( id_entrenamiento ) ON DELETE CASCADE ON UPDATE CASCADE, CONSTRAINT 'id_jugador2' FOREIGN KEY ( id_jugador ) REFERENCES Jugadores ( id_jugador ) ON DELETE CASCADE ON UPDATE CASCADE );";
	private String sqlCreate7="CREATE TABLE IF NOT EXISTS Equipo_Inicial( id_inicial INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, tipo VARCHAR( 45 ) NOT NULL, id_jugador1 INTEGER CONSTRAINT 'id_jugador3' REFERENCES Jugadores ( id_jugador ) ON DELETE CASCADE ON UPDATE CASCADE, id_jugador2 INTEGER CONSTRAINT 'id_jugador3' REFERENCES Jugadores ( id_jugador ) ON DELETE CASCADE ON UPDATE CASCADE, id_jugador3 INTEGER CONSTRAINT 'id_jugador5' REFERENCES Jugadores ( id_jugador ) ON DELETE CASCADE ON UPDATE CASCADE, id_jugador4 INTEGER CONSTRAINT 'id_jugador6' REFERENCES Jugadores ( id_jugador ) ON DELETE CASCADE ON UPDATE CASCADE, id_jugador5 INTEGER CONSTRAINT 'id_jugador7' REFERENCES Jugadores ( id_jugador ) ON DELETE CASCADE ON UPDATE CASCADE, id_jugador6 INTEGER CONSTRAINT 'id_jugador8' REFERENCES Jugadores ( id_jugador ) ON DELETE CASCADE ON UPDATE CASCADE, id_jugador7 INTEGER CONSTRAINT 'id_jugador9' REFERENCES Jugadores ( id_jugador ) ON DELETE CASCADE ON UPDATE CASCADE, id_jugador8 INTEGER CONSTRAINT 'id_jugador10' REFERENCES Jugadores ( id_jugador ) ON DELETE CASCADE ON UPDATE CASCADE, id_jugador9 INTEGER CONSTRAINT 'id_jugador11' REFERENCES Jugadores ( id_jugador ) ON DELETE CASCADE ON UPDATE CASCADE, id_jugador10 INTEGER CONSTRAINT 'id_jugador12' REFERENCES Jugadores ( id_jugador ) ON DELETE CASCADE ON UPDATE CASCADE, id_jugador11 INTEGER CONSTRAINT 'id_jugador13' REFERENCES Jugadores ( id_jugador ) ON DELETE CASCADE ON UPDATE CASCADE );";
	private String sqlCreate8="CREATE TABLE IF NOT EXISTS Estadisticas_Partidos ( id_partido INTEGER PRIMARY KEY NOT NULL, id_jugador INTEGER NOT NULL, carreras INT NOT NULL DEFAULT ( 0 ), pases INT NOT NULL DEFAULT ( 0 ), pases_completados INT NOT NULL DEFAULT ( 0 ), recepciones INT NOT NULL DEFAULT ( 0 ), fumbles INT NOT NULL DEFAULT ( 0 ), sacks INT NOT NULL DEFAULT ( 0 ), placajes INT NOT NULL DEFAULT ( 0 ), intercepciones INT NOT NULL DEFAULT ( 0 ), field_goals INT NOT NULL DEFAULT ( 0 ), punts INT NOT NULL DEFAULT ( 0 ), touchdowns INT NOT NULL DEFAULT ( 0 ), faltas INT NOT NULL DEFAULT ( 0 ), yardas_carrera INT, yardas_pase INT, yardas_recepcion INT, yardas_fumble INT, yardas_intercepcion INT, yardas_field_goal INT, yardas_punt INT, extra_points INT, extra_points_completados INT, pt2_conversions INT, pt2_conversions_completados INT, yardas_falta INT, CONSTRAINT 'id_partido' FOREIGN KEY ( id_partido ) REFERENCES Partidos ( id_partido ) ON DELETE CASCADE ON UPDATE CASCADE, CONSTRAINT 'id_jugador1' FOREIGN KEY ( id_jugador ) REFERENCES Jugadores ( id_jugador ) ON DELETE CASCADE ON UPDATE CASCADE );";
	//Si se modifica la versión de la base de datos, aquí se pone en SQL la alteración realizada a la BD en SQLite
	private String sqlAlter="";
	
	//Constructor de la clase que crea la base de datos (este código es necesario para la conexión a la BD)
	public CreateUpgradeSQLite(Context contexto,String nombre,CursorFactory factory,int version){
		super(contexto,nombre,factory,version);
	}
	
	public void onCreate(SQLiteDatabase db){
		//Se ejecuta la sentencia SQL de creación de la tabla
		db.execSQL(sqlCreate1);
		db.execSQL(sqlCreate2);
		db.execSQL(sqlCreate3);
		db.execSQL(sqlCreate4);
		db.execSQL(sqlCreate5);
		db.execSQL(sqlCreate6);
		db.execSQL(sqlCreate7);
		db.execSQL(sqlCreate8);
	}
	
	public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva){
		//Se modifica la versión anterior de la tabla
		db.execSQL(sqlAlter);
	}
	
}