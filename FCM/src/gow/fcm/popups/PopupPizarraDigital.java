package gow.fcm.popups;

import gow.fcm.footballcoachmanager.R;
import gow.fcm.utilidades.PizarraDigital;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;

public class PopupPizarraDigital extends Activity {
	LinearLayout ll; // Declaramos los objetos a utilizar.
	PizarraDigital bb;
	View view_btn_paint, view_btn_circle, view_btn_x;
	boolean pass_paint, pass_circle, pass_x;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		showAsPopup(this); // La personalizacion del Activity depende de esta linea, y DEBE ser llamada en este punto concreto.
		setContentView(R.layout.popup_pizarra);
		
		ll = (LinearLayout) findViewById(R.id.LinearLayoutBB); // Obtenemos los objetos a manipular.
		bb = (PizarraDigital) findViewById(R.id.jugadas_campo);
		view_btn_paint = (View) findViewById(R.id.view_btn_paint);
		view_btn_circle = (View) findViewById(R.id.view_btn_circle);
		view_btn_x = (View) findViewById(R.id.view_btn_x);
		
		pass_paint = true; // Personalizamos los estados iniciales.
		pass_circle = false;
		pass_x = false;
		applyListenerToView();
	}

	/**
	 * Este metodo tan solo añade un Listener a los 'View/Imagenes/Botones'.
	 * Se ha extraido de 'onCreate()' por comodidad.
	 */
	private void applyListenerToView() {
		
		view_btn_paint.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Cambios en las imagenes y comportamiento en la pizarra.
				if(!pass_paint){
					view_btn_paint.setBackgroundDrawable(getResources().getDrawable(R.drawable.bb_btn_paint_selected));
					pass_paint = true;
					view_btn_circle.setBackgroundDrawable(getResources().getDrawable(R.drawable.bb_btn_circle));
					pass_circle = false;
					view_btn_x.setBackgroundDrawable(getResources().getDrawable(R.drawable.bb_btn_x));
					pass_x = false;
					bb.setMode(0);
				}
			}
		});
		
		view_btn_circle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Cambios en las imagenes y comportamiento en la pizarra.
				if(!pass_circle){
					view_btn_circle.setBackgroundDrawable(getResources().getDrawable(R.drawable.bb_btn_circle_selected));
					pass_circle = true;
					view_btn_paint.setBackgroundDrawable(getResources().getDrawable(R.drawable.bb_btn_paint));
					pass_paint = false;
					view_btn_x.setBackgroundDrawable(getResources().getDrawable(R.drawable.bb_btn_x));
					pass_x = false;
					bb.setMode(1);
				}
			}
		});
		
		view_btn_x.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Cambios en las imagenes y comportamiento en la pizarra.
				if(!pass_x){
					view_btn_x.setBackgroundDrawable(getResources().getDrawable(R.drawable.bb_btn_x_selected));
					pass_x = true;
					view_btn_circle.setBackgroundDrawable(getResources().getDrawable(R.drawable.bb_btn_circle));
					pass_circle = false;
					view_btn_paint.setBackgroundDrawable(getResources().getDrawable(R.drawable.bb_btn_paint));
					pass_paint = false;
					bb.setMode(2);
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.opciones_pizarra, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Maneja la seleccion de Items.
	    switch (item.getItemId()) {
	    case R.id.item_menu_restore:
	        // Redibujamos el BlackBoard.
	    	ll.removeAllViews();
	    	bb = new PizarraDigital(this, null);
	    	ll.addView(bb);
	    	ll.invalidate();
	    	ll.requestLayout();
	        return true;
	    case R.id.item_menu_save:
	        // Nada por el momento.
	    	// Usar - canvas.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(new File("img.png")));
	        return true;
	    case R.id.item_menu_exit:
	        // Salimos de ese activity.
	    	this.finish();
	        return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}

	/**
	 * Este metodo permite la personalizacion de este activity para terminar de
	 * convertirlo, junto al uso de '@style/', en un Activity de menu o Pop-Up.
	 * Para conocer toda la informacion mirar tambien el archivo XML 'styles',
	 * en la carpeta values.
	 * 
	 * @param activity : le pasamos el activity actual para personalizarlo.
	 */
	private static void showAsPopup(Activity activity) {
		activity.requestWindowFeature(Window.FEATURE_ACTION_BAR); // Esta caracteristica habilita la barra (menu) en el Pop-Up.
		activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND,WindowManager.LayoutParams.FLAG_DIM_BEHIND); // Marcamos activity para habilitar opciones diversas.
		LayoutParams params = activity.getWindow().getAttributes(); // Obtenemos objeto de configuracion del Activity.
		params.height = 700; // Adaptamos el tamaño en altura segun componentes del XML.
		params.width = 1200; // Fijamos el tamaño en anchura.
		params.alpha = 1.0f; // Podemos otorgarle transparencia al Pop-Up.
		params.dimAmount = 0.5f; // Fijamos el nivel de oscuridad para el activity de fondo.
		activity.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params); // Aplicamos los valores establecidos al Activity.
	}
	
}
