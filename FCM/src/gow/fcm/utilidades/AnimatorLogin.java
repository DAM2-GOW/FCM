package gow.fcm.utilidades;

import gow.fcm.footballcoachmanager.R;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;

public class AnimatorLogin implements Runnable{
	private Context context;
	private Activity activity;
	private View view;
	private Animation animation_in, animation_out, animation_wait_from_in,
			animation_wait_from_out;
	private boolean first, running=true;
	private Thread t;
	private int[] images1;
	private int[] images2;
	private int imageToShow = 0;

	public AnimatorLogin(Activity a, Context c, View v, boolean f) {
		// Constructor.
		this.context = c; // Almacenamos el contexto recibido.
		this.activity = a; // Almacenamos el activity desde el que procede la llamada;
		this.view = v; // Almacenamos el view recibido.
		this.first = f; // Almacenamos si se trata del primer View o el segundo.

		// Se almacenan en el array las imagenes que seran mostradas.
		images1 = new int[3];
		images2 = new int[4];
		images1[0] = R.drawable.anim_pic1;
		images1[1] = R.drawable.anim_pic2;
		images1[2] = R.drawable.anim_pic3;
		images2[0] = R.drawable.anim_pic4;
		images2[1] = R.drawable.anim_pic5;
		images2[2] = R.drawable.anim_pic6;
		images2[3] = R.drawable.anim_pic7;

		t = new Thread(this, "AnimatorThread"); // Creamos un nuevo hilo con nombre.
		t.start(); // Iniciamos el hilo creado.
		
	}

	/**
	 * Este metodo es llamado para interrumpir el bucle
	 * con las animaciones. Segun informaciones, android
	 * gestiona automaticamente la vida de un hilo si no
	 * es util.
	 */
	public void stopThread(){
		Log.d("THREAD", "interrumpiendo: "+t.toString());
		running = false;
	}

	/**
	 * Este metodo es llamado junto al metodo onResume()
	 * del activity que inicio el hilo cuando se volvio
	 * a llamar al activity.
	 */
	public void resumeThread(){
		Log.d("THREAD", "resumiendo: "+t.toString());
		running = true;
	}

	// Definimos la actividad que tendra el Thread.
	@Override
	public void run() {
		// Esta linea se encarga de detener el hilo dejandolo sin
		// actuar. El sistema eliminara el hilo si no es util.
		if (!running) return;

		// Definimos la prioridad del Thread para que no consuma por
		// encima del Thread principal.
		android.os.Process
				.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);

		// Definimos los diferentes tipos de animaciones a usar.
		animation_in = AnimationUtils.loadAnimation(context,
				R.animator.fade_in_login);
		animation_out = AnimationUtils.loadAnimation(context,
				R.animator.fade_out_login);
		animation_wait_from_in = AnimationUtils.loadAnimation(context,
				R.animator.wait_anim_from_in);
		animation_wait_from_out = AnimationUtils.loadAnimation(context,
				R.animator.wait_anim_from_out);

		// Cambiar las propiedades de los elementos en otro hilo que no sea
		// el principal que los ha creado no se permite. Por lo que se ha de
		// de generar otro hilo procedente directamente de una rama inferior
		// del hilo que ha generado esos objetos, lo que nos permitira cambiar
		// sus propiedades.
		activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
            	// Definimos la prioridad del Thread para que no consuma por
        		// encima del Thread principal.
        		android.os.Process
        				.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
        		
                // Este hilo correra dentro del hilo de la UI, por lo tanto permite la modificacion de elementos.
            	if (first) {
        			view.startAnimation(animation_in);
        			view.setBackgroundResource(images1[imageToShow]);
        		} else {
        			view.startAnimation(animation_out);
        			view.setBackgroundResource(images2[(imageToShow)]);
        		}
            	
            	return;
            }
        });
		// Desencadenante del inicio de la animacion.
		

		// Interpreta las fases de la animacion de Entrada.
		animation_in.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				Log.d("ANIM", "STARTED IN");
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				Log.d("ANIM", "ENDED IN");
				if (running)
					view.startAnimation(animation_wait_from_in);
			}
		});

		// Interpreta las fases de la animacion de Espera desde fade_in.
		animation_wait_from_in
				.setAnimationListener(new AnimationListener() {

					@Override
					public void onAnimationStart(Animation animation) {
						Log.d("ANIM", "STARTED WAIT FROM IN");
					}

					@Override
					public void onAnimationRepeat(Animation animation) {

					}

					@Override
					public void onAnimationEnd(Animation animation) {
						if (running)
							view.startAnimation(animation_out);
					}
				});

		// Interpreta las fases de la animacion de Espera desde
		// fade_out.
		animation_wait_from_out
				.setAnimationListener(new AnimationListener() {

					@Override
					public void onAnimationStart(Animation animation) {
						Log.d("ANIM", "STARTED WAIT FROM OUT");
						imageToShow = imageToShow+1;
						
						activity.runOnUiThread(new Runnable() {
				            @Override
				            public void run() {
				            	// Definimos la prioridad del Thread para que no consuma por
				        		// encima del Thread principal.
				        		android.os.Process
				        				.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
				        		
				                // Este hilo correra dentro del hilo de la UI, por lo tanto permite la modificacion de elementos.
				            	if (first) {
				            		if(imageToShow >= images1.length)
				            			imageToShow = 0;
				            		Log.d("IMAGE FIRST", "CHANGE TO: "+imageToShow);
				        			view.setBackgroundResource(images1[imageToShow]);
				        		} else {
				        			if(imageToShow >= images2.length)
				            			imageToShow = 0;
				        			Log.d("IMAGE SECOND", "CHANGE TO: "+imageToShow);
				        			view.setBackgroundResource(images2[(imageToShow)]);
				        		}
				            	
				            	return;
				            }
				        });
					}

					@Override
					public void onAnimationRepeat(Animation animation) {

					}

					@Override
					public void onAnimationEnd(Animation animation) {
						Log.d("ANIM", "ENDED WAIT FROM OUT");
						if (running)
							view.startAnimation(animation_in);
					}
				});

		// Interpreta las fases de la animacion de Salida.
		animation_out.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				Log.d("ANIM", "STARTED OUT");
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				Log.d("ANIM", "ENDED OUT");
				if (running)
					view.startAnimation(animation_wait_from_out);
			}
		});
	}
}
