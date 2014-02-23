package gow.fcm.pantallas;

import java.text.SimpleDateFormat;
import java.util.Date;
import gow.fcm.basedatos.ConexionSQLite;
import gow.fcm.popups.PopupPizarraDigital;
import gow.fcm.sentencias.SentenciasSQLitePrincipal;
import android.net.Uri;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import gow.fcm.footballcoachmanager.R;

public class PaginaPrincipal extends Activity{
	
	private ImageView fotoEntrenador; //Imagen o foto del entrenador
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); //No mostramos la barra de la cabecera con el nombre de la aplicaci�n
		setContentView(R.layout.pagina_principal);
		
		//Se crea la base de datos si no existe o se actualiza si fuera necesario
		ConexionSQLite.getCrearSQLite(this);
		
		//M�todo que muestra el nombre del entrenador
		mostrarDatosEntreneador();
		
		//M�todo que muestra el nombre del equipo
		mostrarNombreEquipo();
		
		//M�todo que muestra las diferentes secciones de la aplicaci�n
		seccionesBotones();
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		
		//M�todo que muestra las diferentes secciones de la aplicaci�n
		seccionesBotones();
	}
	
	//M�todo que muestra
	private void mostrarDatosEntreneador(){
		//Decalaramos los TextView
		TextView nombreEntrenador=(TextView) findViewById(R.id.nombre_entrenador);
		TextView apellidosEntrenador=(TextView) findViewById(R.id.apellidos_entrenador);
		
		//Ejecutamos las sentencias
		SentenciasSQLitePrincipal.getDatosEntrenador(this);
		String nombreEnt=SentenciasSQLitePrincipal.getNombreEntrenador();
		String apellidosEnt=SentenciasSQLitePrincipal.getApellidosEntrenador();
		
		//Mostramos el contenido
		if(nombreEnt==null){
			nombreEntrenador.setText(R.string.coach_name);
			apellidosEntrenador.setText(R.string.coach_surname);
		}else{
			//Agregamos el valor o contenido a los elementos
			nombreEntrenador.setText(nombreEnt);
			apellidosEntrenador.setText(apellidosEnt);
			
			SentenciasSQLitePrincipal.setNombreEntrenador(); //Reseta a null el valor
			SentenciasSQLitePrincipal.setApellidosEntrenador(); //Reseta a null el valor
		}
		
		getFotoEntrenador();
		
		//M�todo que hace la funci�n de bot�n de opciones
		opcionesEntrenador();
	}
	
	//M�todo que obtiene y muestra la foto actual del entrenador
	private void getFotoEntrenador(){
		//Decalaramos el ImageView
		fotoEntrenador=(ImageView) findViewById(R.id.foto_entrenador);
		
		//Registramos la imagen del entrenador como men� contextual
		registerForContextMenu(fotoEntrenador);
		
		//Ejecutamos las sentencias
		SentenciasSQLitePrincipal.getDatosEntrenador(this);
		String fotoEnt=SentenciasSQLitePrincipal.getFotoEntrenador();
		
		//Mostramos la foto del entrenador si la hay o no
		if(fotoEnt==null){
			fotoEntrenador.setImageResource(R.drawable.no_coach_photo);
		}else{
			//Agregamos el valor o contenido a los elementos
			fotoEntrenador.setImageURI(Uri.parse(fotoEnt));
			
			SentenciasSQLitePrincipal.setFotoEntrenador(); //Reseta a null el valor
		}
	}
	
	//M�todo que realiza una acciones al hacer clic sobre las diferentes opciones del entrenador
	private void opcionesEntrenador(){
		//Declaramos como variable la imagen
		final View configuracionEntrenador=findViewById(R.id.boton_entrenador);
		
		//Acci�n a realizar
		configuracionEntrenador.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1){
				switch(arg1.getAction()){
				case MotionEvent.ACTION_DOWN: {
					datosEntrenador(); //M�todo para modificar los datos del entrenador o entrenadores y su equipo o equipos
					break;
				}
				}
				return true;
			}
		});
	}

	//M�todo que abre la pantalla con los datos del entrenador y su equipo
	private void datosEntrenador(){
		Intent i=new Intent(this,PantallaPerfilEntrenador.class);
		startActivity(i);
	}

	private void informacionAplicacion(){
		ImageButton botonInfo=(ImageButton) findViewById(R.id.imageButtonInfo); //Declaramos la variable que har�n de bot�n
		
		//M�todo del bot�n info
		botonInfo.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				botonInfo();
			}
		});
	}
	
	//M�todo para acceder al "Acerca de"
	private void botonInfo(){
		Intent i=new Intent (this,PantallaAcercaDe.class);
		startActivity(i);
	}
	
	//M�todo que carga y muestra en pantalla en nombre del equipo
	@SuppressLint("CutPasteId")
	private void mostrarNombreEquipo(){
		//Declaramos las variables de texto que muestra el nombre del equipo
		TextView nombreEquipo=(TextView) findViewById(R.id.nombre_equipo);
		TextView efectoShadow=(TextView) findViewById(R.id.efectoShadow);
		
		//Llamamos a los m�todos para obtener el nombre del equipo
		SentenciasSQLitePrincipal.getNombreEquipo(this);
		String nombre=SentenciasSQLitePrincipal.getNombreEquipo();
		
		//Creamos la animaci�n del texto
		AlphaAnimation  blinkanimation=new AlphaAnimation(1,0); //Visible->Invisible
		blinkanimation.setDuration(1000); //Tiempo en milisegundos
		blinkanimation.setInterpolator(new LinearInterpolator()); //Tansici�n linear
		blinkanimation.setRepeatCount(Animation.INFINITE); //Repetir la animaci�n infinitamente
		blinkanimation.setRepeatMode(Animation.REVERSE); //Vuelve a empezar
		View nombresEquipos=findViewById(R.id.nombre_equipo); //Objeto view del texto
		nombresEquipos.setAnimation(blinkanimation); //Asignamos el efecto
		
		if(nombre==null){
			nombreEquipo.setText(R.string.team_name);
			efectoShadow.setText(R.string.team_name);
		}else{
			nombreEquipo.setText(nombre);
			efectoShadow.setText(nombre);
			SentenciasSQLitePrincipal.setNombreEquipo(); //Reseta a null el valor
		}
		
		//M�todo que lleva a la pantalla de Acerca de
		informacionAplicacion();
	}
	
	//M�todo que devuelve la fecha actual
	@SuppressLint("SimpleDateFormat")
	private String getFechaActual(){
		//Obtenemos la fecha actual
		Date dates=new Date();
		SimpleDateFormat formato=new SimpleDateFormat("yyyy-MM-dd"); //Formato de conversi�n a Date
		String fecha=formato.format(dates);
		return fecha;
	}
	
	//M�todo que muestra los botones en los cuales puedes navegar en la aplicaci�n
	private void seccionesBotones(){
		//Declaramos la variables que har�n de botones
		View botonEquipo=findViewById(R.id.boton_equipo);
		View botonPizarra=findViewById(R.id.boton_pizarra);
		View botonEvento=findViewById(R.id.boton_partido);
		View botonCalendario=findViewById(R.id.boton_calendario);
		
		//Declaramos la variables que har�n la animaci�n al pulsar el bot�n
		final ImageView imgEquipo=(ImageView) findViewById(R.id.equipo);
		final ImageView imgPizarra=(ImageView) findViewById(R.id.pizarra);
		final ImageView imgEvento=(ImageView) findViewById(R.id.partido);
		final ImageView imgCalendario=(ImageView) findViewById(R.id.calendario);
		
		String fechaActual=getFechaActual();
		SentenciasSQLitePrincipal.numeroPartidos(this,fechaActual);
		final int hayPartido=Integer.parseInt(SentenciasSQLitePrincipal.getNumPartidos());
		
		//M�todo que realiza la animaci�n
		botonEquipo.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1){
				switch(arg1.getAction()){
				case MotionEvent.ACTION_DOWN: {
					imgEquipo.setImageResource(R.drawable.team_management_down);
					botonAdministrarEquipo(); //M�todo para ir a la secci�n
					break;
				}
				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_CANCEL: {
					imgEquipo.setImageResource(R.drawable.team_management_up);
					break;
				}
				}
				return true;
			}
		});
		
		//M�todo que realiza la animaci�n
		botonPizarra.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1){
				switch(arg1.getAction()){
				case MotionEvent.ACTION_DOWN: {
					imgPizarra.setImageResource(R.drawable.digital_board_down);
					botonPizarraDigital(); //M�todo para ir a la secci�n
					break;
				}
				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_CANCEL: {
					imgPizarra.setImageResource(R.drawable.digital_board_up);
					break;
				}
				}
				return true;
			}
		});
		
		//M�todo que realiza la animaci�n
		botonEvento.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1){
				switch(arg1.getAction()){
				case MotionEvent.ACTION_DOWN: {
					imgEvento.setImageResource(R.drawable.event_management_down);
					
					if(hayPartido>0){
						botonIrPartido(); //M�todo para ir a la secci�n
					}else{
						Toast toast=Toast.makeText(getApplicationContext(),R.string.msg_no_match,Toast.LENGTH_SHORT);
						LinearLayout toastLayout=(LinearLayout) toast.getView();
						TextView toastTV=(TextView) toastLayout.getChildAt(0);
						toastTV.setTextSize(25);
						toast.show(); //Mostramos un mensaje de que no hay partido para dicho dia
					}
					
					break;
				}
				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_CANCEL: {
					imgEvento.setImageResource(R.drawable.event_management_up);
					break;
				}
				}
				return true;
			}
		});
		
		//M�todo que realiza la animaci�n
		botonCalendario.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1){
				switch(arg1.getAction()){
					case MotionEvent.ACTION_DOWN: {
						imgCalendario.setImageResource(R.drawable.team_calendar_down);
						botonCalendario(); //M�todo para ir a la secci�n
						break;
					}
					case MotionEvent.ACTION_UP:
					case MotionEvent.ACTION_CANCEL: {
						imgCalendario.setImageResource(R.drawable.team_calendar_up);
						break;
					}
				}
				return true;
			}
		});
		

	}
	
	//M�todo para acceder a la administraci�n del equipo
	private void botonAdministrarEquipo(){
		Intent i=new Intent(this,PantallaListaJugadores.class);
		startActivity(i);
	}
	
	//M�todo para acceder a la pizarra digital
	private void botonPizarraDigital(){
		Intent i=new Intent(this,PopupPizarraDigital.class);
		startActivity(i);
	}
	
	//M�todo para acceder a los eventos
	private void botonIrPartido(){
		Intent i=new Intent(this,PlayingGround.class);
		startActivity(i);
	}
	
	//M�todo para acceder al calendario
	private void botonCalendario(){
		Intent i=new Intent(this,PaginaCalendario.class);
		startActivity(i);
	}
}