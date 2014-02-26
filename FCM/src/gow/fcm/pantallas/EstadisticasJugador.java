package gow.fcm.pantallas;

import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;
import gow.fcm.basedatos.ConexionSQLite;
import gow.fcm.footballcoachmanager.R;
import gow.fcm.sentencias.SentenciasSQLiteDatosGrafico;
import gow.fcm.sentencias.SentenciasSQLiteDatosJugador;
import gow.fcm.sentencias.SentenciasSQLiteListaEstadisticas;
import gow.fcm.utilidades.ArrayAdapterStatisticsList;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


public class EstadisticasJugador extends Activity {

	int playerId;
	String nombre;
	String apellidos;
	String edat;
	String num;
	String pos;
	String tip;
	String fot;
	String carrera, pase_comp, recep, fumbles, sacks, placajes, intercepciones, field_goals, punts, touchdown, faltas, extra_point, pt_conversion;
	
	String car, pas, rec, fum, sac, pla, inter, figo, pun, tou, fal, extp, ptcon; 
	
	GraphViewSeries series;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_estadisticas_jugador);
		
		Bundle bundle = getIntent().getExtras();
		playerId= bundle.getInt("playerId");
		
		ConexionSQLite.getCrearSQLite(this);
		
		
		
		nombre = SentenciasSQLiteDatosJugador.getNombre();
		apellidos = SentenciasSQLiteDatosJugador.getApellido();
		edat = SentenciasSQLiteDatosJugador.getEdad();
		pos = SentenciasSQLiteDatosJugador.getPosicion();
		tip = SentenciasSQLiteDatosJugador.getTipo();
		num = SentenciasSQLiteDatosJugador.getNumero();
		fot = SentenciasSQLiteDatosJugador.getFoto();
		
		
		new FillPlayer( nombre, apellidos, edat, num,tip,pos,fot);
		
		ConexionSQLite.getCrearSQLite(this);
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
		
		
			car = carrera;
			pas = pase_comp;
			rec = recep;
			fum = fumbles;
			sac = sacks;
			pla = placajes;
			inter = intercepciones;
			figo = field_goals;
			pun = punts;
			tou = touchdown;
			fal = faltas;
			extp = extra_point;
			ptcon = pt_conversion;
			
			
			
			new FillStatistics(carrera, pase_comp, recep, fumbles, sacks, placajes, intercepciones, field_goals, punts, touchdown, faltas, extra_point, pt_conversion);
	
			CheckBox cb = (CheckBox) findViewById(R.id.checkBox1);
			TextView et = (TextView) findViewById(R.id.statistic);
			int[] DatosR;
			int[] Dias;
			
			
			LineGraphView graphView = new LineGraphView( this, "Graficos Estadisticas");
				graphView.setDrawDataPoints(true);
				graphView.setDataPointsRadius(15f);
				graphView.setViewPort(2, 10);
				graphView.setScalable(true);
				// optional - legend
				graphView.setShowLegend(true);
				
				String dataS;
				if(cb.isChecked()==true){
					dataS = et.toString();
					SentenciasSQLiteDatosGrafico.setDatos(dataS, playerId);
					DatosR = SentenciasSQLiteDatosGrafico.getDatos();
					Dias = SentenciasSQLiteDatosGrafico.getDias();
					new FillGraphic(DatosR, Dias);
					graphView.addSeries(series);
				}
				LinearLayout layout = (LinearLayout) findViewById(R.id.graph1);
				layout.addView(graphView);
			
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
		String[] numero_stat;
		String numeros;
		String tipos;
		String nombres;
		
		
		public FillStatistics (String care, String pase, String rece, String fume, String sacks, String placajes, String intercepciones, String field_goals, String punts, String touchdown, String faltas, String extra_point, String pt_conversion){
		 
			 numero_stat =  new String[]{care,pase,rece,fume,sacks,placajes,intercepciones,field_goals,punts,touchdown,faltas,extra_point,pt_conversion};
		 
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

	  public FillPlayer(String nombre, String apellidos, String edat, String num, String tipo, String pos, String fot) {
		  
		  
		  etName = (EditText) findViewById(R.id.nombre_jug);
		  etName.setText(nombre+" "+apellidos);
		  
		  etTipo = (ImageView) findViewById(R.id.imagen_tipo);
		  switch(Integer.parseInt(tipo)) {
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
		  etAge.setText(edat);
		  
		  etNum = (EditText) findViewById(R.id.dorsal_jug);
		  etNum.setText(num);
		  
		  etPos = (EditText) findViewById(R.id.posicion_jug);
		  etPos.setText(pos);
		  
//		  	etFoto= (ImageView) findViewById(R.id.imagen_jug);
// 			etFoto.setId(foto);
	  }}
  
	  class FillGraphic {

		    //declaración de atributos
		  
		  
		  
		  int dato;
		  int dia;
		  GraphViewData[]  Dfinal;
		  //declaración de constructor

		  public FillGraphic(int[] data, int[] dias){
			  Dfinal = new GraphViewData[data.length];
			  for (int i=0; i<data.length; i++) {		   
			     data[i] = dato;
			     dias[i] = dia;
			    Dfinal[i] = new GraphViewData(dato, dia);
			  }
			  series = new GraphViewSeries(Dfinal);
	  }
}}

