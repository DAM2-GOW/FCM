package gow.fcm.pantallas;

import gow.fcm.basedatos.ConexionSQLite;
import gow.fcm.footballcoachmanager.R;
import gow.fcm.footballcoachmanager.R.layout;
import gow.fcm.footballcoachmanager.R.menu;
import gow.fcm.sentencias.SentenciasSQLiteListaEstadisticas;
import gow.fcm.sentencias.SentenciasSelectSQLite;
import gow.fcm.utilidades.ArrayAdapterStatisticsList;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

public class EstadisticasJugador extends Activity {

	int playerId;
	String nombre;
	String apellidos;
	int edat;
	int num;
	String pos;
	int tip;
	String fot;
	String[] carrera, pase, pase_comp, recep, fumbles, sacks, placajes, intercepciones, field_goals, punts, touchdown, faltas, extra_point, pt_conversion;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_estadisticas_jugador);
		
		Bundle bundle = getIntent().getExtras();
		//playerId= bundle.getInt("playerId");
		playerId = 1;
		ConexionSQLite.getCrearSQLite(this);
		SentenciasSelectSQLite.seleccionarSQLite("Jugadores", new String[]{"nombre","apellidos","edad","posicion","tipo","dorsal","foto"}, "id_jugador = "+playerId+"");
		
		
		
//		nombre = (String) SentenciasSelectSQLite.getValor1();
//		apellidos = (String) SentenciasSelectSQLite.getValor2();
//		edat = (Integer) SentenciasSelectSQLite.getValor3();
//		pos = (String) SentenciasSelectSQLite.getValor4();
//		tip = (Integer) SentenciasSelectSQLite.getValor5();
//		num = (Integer) SentenciasSelectSQLite.getValor6();
//		fot = (String) SentenciasSelectSQLite.getValor7();
		
		//new FillPlayer(nombre, apellidos, edat, num, tip, pos, fot);
		
		ConexionSQLite.getCrearSQLite(this);
		SentenciasSelectSQLite.seleccionarSQLite("Estadisticas_Partidos", new String[]{"carreras","pases_completados","recepciones","fumbles","sacks","placajes","intercepciones","field_goals","punts","touchdowns","faltas","extra_points_completados","2pt_conversions_completados"}, "id_jugador = "+playerId+"");
		carrera = SentenciasSQLiteListaEstadisticas.getCarreras();
		pase_comp = SentenciasSQLiteListaEstadisticas.getPases();
		recep = SentenciasSQLiteListaEstadisticas.getRecepcion();
		fumbles = SentenciasSQLiteListaEstadisticas.getFumble();
		sacks = SentenciasSQLiteListaEstadisticas.getSack();
		placajes = SentenciasSQLiteListaEstadisticas.getPlacaje();
		intercepciones = SentenciasSQLiteListaEstadisticas.getIntercepcion();
		field_goals = SentenciasSQLiteListaEstadisticas.getFieldgoals();
		punts = SentenciasSQLiteListaEstadisticas.getPunts();
		touchdown = SentenciasSQLiteListaEstadisticas.getTouchdowns();
		faltas = SentenciasSQLiteListaEstadisticas.getFaltas();
		extra_point = SentenciasSQLiteListaEstadisticas.getExtrapoint();
		pt_conversion = SentenciasSQLiteListaEstadisticas.getPtconversion();
		
		new FillStatistics (carrera, pase_comp, recep, fumbles, sacks, placajes, intercepciones, field_goals, punts, touchdown, faltas, extra_point, pt_conversion);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.estadisticas_jugador, menu);
		return true;
	}
	
class FillStatistics {

	    //declaración de atributos
		 
		String[] nombre_stat =  new String[]{"Carreras","Pases","Recepciones","Fumbles","Sacks","Placajes","Intercepciones","Field Goals","Punts","Touchdowns","Faltas","Extra point","Extra game"};
		String[] tipo_stat =  new String[]{"YD","","","","","","","","","","","",""};
		ArrayAdapterStatisticsList adapter;
		int[] numero_stat;
		int numeros;
		String tipos;
		String nombres;
		
		 public FillStatistics(int car, int pas, int rec, int fum, int sac, int pla, int inter, int figo, int pun, int tou, int fal, int extp, int ptcon){
		 
			 numero_stat =  new int[]{car,pas,rec,fum,sac,pla,inter,figo,pun,tou,fal,extp,ptcon};
		 
		 	ListView lv = (ListView)findViewById(R.id.statistics_list);
		 	
		 	for(int i = 0; i<=nombre_stat.length; i++){
		 		nombres = nombre_stat[i];
		 		tipos = tipo_stat[i];
		 		numeros = numero_stat[i];
		 		
			adapter = new ArrayAdapterStatisticsList(EstadisticasJugador.this, nombres, tipos, numeros);
		 	}
			View header = (View)getLayoutInflater().inflate(R.layout.list_header_statistics, null);
			
	        lv.addHeaderView(header);
	        lv.setAdapter(adapter);
	 }

		public FillStatistics(String[] carrera, String[] pase_comp,
				String[] recep, String[] fumbles, String[] sacks,
				String[] placajes, String[] intercepciones,
				String[] field_goals, String[] punts, String[] touchdown,
				String[] faltas, String[] extra_point, String[] pt_conversion) {
			// TODO Auto-generated constructor stub
		}
	}
class FillPlayer {

	    //declaración de atributos
	
	 EditText etName;
	 EditText etAge;
	 EditText etNum;
	 EditText etPos;
	 ImageView etTipo;
	 ImageView etFoto;
	 
	  //declaración de constructor

	  public FillPlayer(String nombre, String ape, int edad, int numero, int tipo, String posicion, String foto) {
		  
		  etName = (EditText) findViewById(R.id.nombre_jug);
		  etName.setText(nombre+" "+ape);
		  
		  etTipo = (ImageView) findViewById(R.id.imagen_tipo);
		  switch(tipo) {
		  case 1:
			  etTipo.setId(R.drawable.btn_all_attack);
		  break;
		  case 2:
			  etTipo.setId(R.drawable.btn_all_def);
		  break;
		  case 3:
			  etTipo.setId(R.drawable.btn_all_ee);
		  break;
		  }
		  
		  etAge = (EditText) findViewById(R.id.edad_jug);
		  etAge.setText(edad);
		  
		  etNum = (EditText) findViewById(R.id.dorsal_jug);
		  etNum.setText(numero);
		  
		  etPos = (EditText) findViewById(R.id.posicion_jug);
		  etPos.setText(posicion);
		  
//		  etFoto= (ImageView) findViewById(R.id.imagen_jug);
//		  etFoto.setId(foto);
		  
  }
	
	 }
}
