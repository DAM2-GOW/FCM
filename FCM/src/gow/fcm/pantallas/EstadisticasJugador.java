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
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


public class EstadisticasJugador extends Activity {

	int playerId;
	String varDorsal="dorsal_jugador",nombre,apellidos,edat,num,pos,tip,fot;
	String carrera, pases, pase_comp, recep, fumbles, sacks, placajes, intercepciones, field_goals, punts, touchdown, faltas;
	String yardas_carrera, yardas_pase, yardas_recepcion, yardas_fumble, yardas_intercepcion, yardas_field_goal, yardas_punt, extra_points, extra_points_completados, pt2_conversions, pt2_conversion_completados, yardas_falta;

	String car, pas, rec, fum, sac, pla, inter, figo, pun, tou, fal, extp, ptcon; 

	GraphViewSeries series;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_estadisticas_jugador);

		Intent i=getIntent();
		String dorsal=i.getExtras().getString(varDorsal);
		
		ConexionSQLite.getCrearSQLite(this);
		
		//SentenciasInsertSQLite.insertarSQLite("Estadisticas_Partidos", new String[]{"id_partido","id_jugador","carreras","pases","pases_completados","recepciones","fumbles","sacks","placajes","intercepciones","field_goals","punts","touchdowns","faltas","yardas_carrera","yardas_pase","yardas_recepcion","yardas_fumble","yardas_intercepcion","yardas_field_goal","yardas_punt","extra_points","extra_points_completados","pt2_conversions","pt2_conversions_completados"},new String[]{"1","1","100","100","100","100","100","100","100","100","100","100","100","100","100","100","100","100","100","100","100","100","100","100","100"});
		
		SentenciasSQLiteDatosJugador.getDatosJugador(this,dorsal);
		nombre = SentenciasSQLiteDatosJugador.getNombre();
		apellidos = SentenciasSQLiteDatosJugador.getApellido();
		edat = SentenciasSQLiteDatosJugador.getEdad();
		pos = SentenciasSQLiteDatosJugador.getPosicion();
		tip = SentenciasSQLiteDatosJugador.getTipo();
		num = SentenciasSQLiteDatosJugador.getNumero();
		fot = SentenciasSQLiteDatosJugador.getFoto();

		FillPlayer( nombre, apellidos, edat, num,tip,pos,fot);
		
		SentenciasSQLiteListaEstadisticas.getEstadisticasPartido(this);
		carrera = SentenciasSQLiteListaEstadisticas.getCarreras();
		pases = SentenciasSQLiteListaEstadisticas.getPases();
		pase_comp = SentenciasSQLiteListaEstadisticas.getPases_completados();
		recep = SentenciasSQLiteListaEstadisticas.getRecepciones();
		fumbles = SentenciasSQLiteListaEstadisticas.getFumbles();
		sacks = SentenciasSQLiteListaEstadisticas.getSacks();
		placajes = SentenciasSQLiteListaEstadisticas.getPlacajes();
		intercepciones = SentenciasSQLiteListaEstadisticas.getIntercepciones();
		field_goals = SentenciasSQLiteListaEstadisticas.getField_goals();
		punts = SentenciasSQLiteListaEstadisticas.getPunts();
		touchdown = SentenciasSQLiteListaEstadisticas.getTouchdowns();
		faltas = SentenciasSQLiteListaEstadisticas.getFaltas();
		
		yardas_carrera=SentenciasSQLiteListaEstadisticas.getYardas_carrera();
		yardas_pase=SentenciasSQLiteListaEstadisticas.getYardas_pase();
		yardas_recepcion=SentenciasSQLiteListaEstadisticas.getYardas_recpcion();
		yardas_fumble=SentenciasSQLiteListaEstadisticas.getYardas_funmble();
		yardas_intercepcion=SentenciasSQLiteListaEstadisticas.getYardas_intercepcion();
		yardas_field_goal=SentenciasSQLiteListaEstadisticas.getYardas_field_goal();
		yardas_punt=SentenciasSQLiteListaEstadisticas.getYardas_punt();
		extra_points = SentenciasSQLiteListaEstadisticas.getExtra_points();
		extra_points_completados=SentenciasSQLiteListaEstadisticas.getExtra_points_completados();
		pt2_conversions = SentenciasSQLiteListaEstadisticas.getPt2_conversions();
		pt2_conversion_completados=SentenciasSQLiteListaEstadisticas.getPt2_conversions_completados();
		yardas_falta=SentenciasSQLiteListaEstadisticas.getYardas_falta();



		FillStatistics(carrera, pases, pase_comp, recep, fumbles, sacks, placajes, intercepciones, field_goals, punts, touchdown, faltas, yardas_carrera, yardas_pase, yardas_recepcion, yardas_fumble, yardas_intercepcion, yardas_field_goal, yardas_punt, extra_points,extra_points_completados, pt2_conversions, pt2_conversion_completados, yardas_falta);

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
		
		String dataS = "pases";
		SentenciasSQLiteDatosGrafico.getDatosGrafico(this, dataS);
		DatosR = SentenciasSQLiteDatosGrafico.getDatos();
		Dias = SentenciasSQLiteDatosGrafico.getDias();
		FillGraphic(DatosR, Dias);
		graphView.addSeries(series);
		
		LinearLayout layout = (LinearLayout) findViewById(R.id.graph1);
		layout.addView(graphView);

	}

	//declaración de atributos

	String[] nombre_stat =  new String[]{"Carreras","Pases","Recepciones","Fumbles","Sacks","Placajes","Intercepciones","Field Goals","Punts","Touchdowns","Faltas","Extra point","Extra game"};
	String[] tipo_stat =  new String[]{"YD","","","","","","","","","","","","","","","","","","","","","",""};
	ArrayAdapterStatisticsList adapter;
	String[] numero_stat;
	String numeros;
	String tipos;
	String nombres;


	public void FillStatistics (String care, String pases, String pases_completados, String rece, String fume, String sacks, String placajes, String intercepciones, String field_goals, String punts, String touchdown, String faltas, String yardas_carrera, String yardas_pase, String yardas_recepcion, String yardas_fumble, String yardas_intercepcion, String yardas_field_goal, String yardas_punt, String extra_point, String extra_point_completados, String pt2_conversion, String pt2_conversions_completados, String yardas_falta){

		numero_stat =  new String[]{care,pases,pases_completados,rece,fume,sacks,placajes,intercepciones,field_goals,punts,touchdown,faltas,yardas_carrera,yardas_pase,yardas_recepcion,yardas_fumble,yardas_intercepcion,yardas_field_goal,yardas_punt,extra_point,extra_point_completados,pt2_conversion,pt2_conversions_completados,yardas_falta};

		ListView lv = (ListView)findViewById(R.id.statistics_list);

			nombres = nombre_stat[0];
			tipos = tipo_stat[0];
			numeros = numero_stat[0];

			adapter = new ArrayAdapterStatisticsList(EstadisticasJugador.this, nombres, tipos, numeros);
			
		View header = (View)getLayoutInflater().inflate(R.layout.list_header_statistics, null);

		lv.addHeaderView(header);
		lv.setAdapter(adapter);
	}

	//declaración de atributos

	TextView etName;
	TextView etAge;
	TextView etNum;
	TextView etPos;
	ImageView etTipo;
	ImageView etFoto;

	//declaración de constructor

	public void FillPlayer(String nombre, String apellidos, String edat, String num, String tipo, String pos, String fot) {


		etName = (TextView) findViewById(R.id.nombre_jug);
		etName.setText(nombre+" "+apellidos);

		etTipo = (ImageView) findViewById(R.id.imagen_tipo);
		switch(Integer.parseInt(tipo)) {
		  case 0:
			  etTipo.setImageResource(R.drawable.btn_all_attack);
		  break;
		  case 1:
			  etTipo.setImageResource(R.drawable.btn_all_def);
		  break;
		  case 2:
			  etTipo.setImageResource(R.drawable.btn_all_ee);
		  break;
		  }

		etAge = (TextView) findViewById(R.id.edad_jug);
		etAge.setText(edat);

		etNum = (TextView) findViewById(R.id.dorsal_jug);
		etNum.setText(num);

		etPos = (TextView) findViewById(R.id.posicion_jug);
		etPos.setText(pos);

		etFoto= (ImageView) findViewById(R.id.imagen_jug);
		etFoto.setImageURI(Uri.parse(fot));
	}

	//declaración de atributos
	int dato;
	int dia;
	GraphViewData[]  Dfinal;
	//declaración de constructor

	public void FillGraphic(int[] data, int[] dias){
		Dfinal = new GraphViewData[data.length];
		for (int i=0; i<data.length; i++) {		   
			data[i] = dato;
			dias[i] = dia;
			Dfinal[i] = new GraphViewData(dato, dia);
		}
		series = new GraphViewSeries(Dfinal);
	}
}
