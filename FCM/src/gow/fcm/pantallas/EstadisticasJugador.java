package gow.fcm.pantallas;

import com.jjoe64.graphview.BarGraphView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import gow.fcm.footballcoachmanager.R;
import gow.fcm.sentencias.SentenciasSQLiteDatosGrafico;
import gow.fcm.sentencias.SentenciasSQLiteDatosJugador;
import gow.fcm.sentencias.SentenciasSQLiteListaEstadisticas;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class EstadisticasJugador extends Activity{
	
	String varDorsal="dorsal_jugador";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_estadisticas_jugador);
		
		Intent i=getIntent();
		String dorsal=i.getExtras().getString(varDorsal);
		
		//Este método rellena los datos de los jugadores
		rellenarDatosJugador(dorsal);
		
		//Este método rellena el gráfico con estadisticas
		datosGrafico(dorsal,"carreras");
	}
	
	//Método que rellena los datos del jugador indicado
	private void rellenarDatosJugador(String dorsal){
		//Obtenemos los datos
		SentenciasSQLiteDatosJugador.getDatosJugador(this,dorsal);
		String nombre=SentenciasSQLiteDatosJugador.getNombre();
		String apellidos=SentenciasSQLiteDatosJugador.getApellido();
		String edat=SentenciasSQLiteDatosJugador.getEdad();
		String pos=SentenciasSQLiteDatosJugador.getPosicion();
		String tipo=SentenciasSQLiteDatosJugador.getTipo();
		String num=SentenciasSQLiteDatosJugador.getNumero();
		String fot=SentenciasSQLiteDatosJugador.getFoto();
		
		//Declaramos los  de texto
		TextView etName=(TextView) findViewById(R.id.nombre_jug);
		TextView etAge=(TextView) findViewById(R.id.edad_jug);
		TextView etNum=(TextView) findViewById(R.id.dorsal_jug);
		TextView etPos=(TextView) findViewById(R.id.posicion_jug);
		ImageView etFoto= (ImageView) findViewById(R.id.imagen_jug);
		ImageView etTipo=(ImageView) findViewById(R.id.imagen_tipo);
		
		//Rellenamos los datos del jugador
		etName.setText(nombre+" "+apellidos);
		etAge.setText(edat);
		etNum.setText(num);
		etPos.setText(pos);
		etFoto.setImageURI(Uri.parse(fot));
		
		switch(Integer.parseInt(tipo)){
			case 0: etTipo.setImageResource(R.drawable.btn_all_attack);
				break;
			case 1: etTipo.setImageResource(R.drawable.btn_all_def);
				break;
			case 2: etTipo.setImageResource(R.drawable.btn_all_ee);
				break;
		}
	}
	
	//Método que muestra en un gráfico la estadistica deseada
	private void datosGrafico(String dorsal,String dato){
		//Obtenemos los valores
		SentenciasSQLiteDatosGrafico.getDatosGrafico(this,dorsal,dato);
		SentenciasSQLiteDatosGrafico.getDiasGrafico(this);
		int valores[]=SentenciasSQLiteDatosGrafico.getDatos();
		int[] datos=valores;
		String[] fechas=SentenciasSQLiteDatosGrafico.getDias();
		
		TextView tituloDatos=(TextView) findViewById(R.id.graphic_data);
		
		if(valores.length==0){
			tituloDatos.setText(R.string.no_data);
		}else{
			tituloDatos.setText(R.string.yes_data);
		}
		
		GraphViewData[] datosY=new GraphViewData[datos.length];
		for(int num=0;num<datos.length;num++){
		   datosY[num]=new GraphViewData(num,datos[num]);
		}
		GraphViewSeries series1=new GraphViewSeries(datosY);
		
		GraphViewData[] datosX=new GraphViewData[datos.length];
		for(int num=0;num<datos.length;num++){
		   datosX[num]=new GraphViewData(num,num);
		}
		GraphViewSeries series2=new GraphViewSeries(datosX);
		
		GraphView graphView=new BarGraphView(this,"");
		if(valores.length==0 & fechas.length==0){
			graphView.getGraphViewStyle().setNumVerticalLabels(1);
			graphView.getGraphViewStyle().setNumHorizontalLabels(1);
		}else if(valores.length==0 & fechas.length!=0){
			graphView.getGraphViewStyle().setNumVerticalLabels(1);
		}else if(valores.length!=0 & fechas.length==0){
			graphView.getGraphViewStyle().setNumHorizontalLabels(1);
		}
		
		graphView.setHorizontalLabels(fechas);
		graphView.setViewPort(1,1);
		graphView.setScalable(true);
		
		graphView.addSeries(series2);
		graphView.addSeries(series1);
		
		LinearLayout layout=(LinearLayout) findViewById(R.id.graph1);
		layout.addView(graphView);
	}
	
	@SuppressWarnings("unused")
	private void estadisticasJugador(){
		//Obtenemos todas las estadisticas
		SentenciasSQLiteListaEstadisticas.getEstadisticasPartido(this);
		String carrera=SentenciasSQLiteListaEstadisticas.getCarreras();
		String pases=SentenciasSQLiteListaEstadisticas.getPases();
		String pase_comp=SentenciasSQLiteListaEstadisticas.getPases_completados();
		String recep=SentenciasSQLiteListaEstadisticas.getRecepciones();
		String fumbles=SentenciasSQLiteListaEstadisticas.getFumbles();
		String sacks=SentenciasSQLiteListaEstadisticas.getSacks();
		String placajes=SentenciasSQLiteListaEstadisticas.getPlacajes();
		String intercepciones=SentenciasSQLiteListaEstadisticas.getIntercepciones();
		String field_goals=SentenciasSQLiteListaEstadisticas.getField_goals();
		String punts=SentenciasSQLiteListaEstadisticas.getPunts();
		String touchdown=SentenciasSQLiteListaEstadisticas.getTouchdowns();
		String faltas=SentenciasSQLiteListaEstadisticas.getFaltas();
		
		String yardas_carrera=SentenciasSQLiteListaEstadisticas.getYardas_carrera();
		String yardas_pase=SentenciasSQLiteListaEstadisticas.getYardas_pase();
		String yardas_recepcion=SentenciasSQLiteListaEstadisticas.getYardas_recpcion();
		String yardas_fumble=SentenciasSQLiteListaEstadisticas.getYardas_funmble();
		String yardas_intercepcion=SentenciasSQLiteListaEstadisticas.getYardas_intercepcion();
		String yardas_field_goal=SentenciasSQLiteListaEstadisticas.getYardas_field_goal();
		String yardas_punt=SentenciasSQLiteListaEstadisticas.getYardas_punt();
		String extra_points=SentenciasSQLiteListaEstadisticas.getExtra_points();
		String extra_points_completados=SentenciasSQLiteListaEstadisticas.getExtra_points_completados();
		String pt2_conversions=SentenciasSQLiteListaEstadisticas.getPt2_conversions();
		String pt2_conversion_completados=SentenciasSQLiteListaEstadisticas.getPt2_conversions_completados();
		String yardas_falta=SentenciasSQLiteListaEstadisticas.getYardas_falta();
	}
}
