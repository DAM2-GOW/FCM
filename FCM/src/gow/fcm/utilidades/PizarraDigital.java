package gow.fcm.utilidades;

import gow.fcm.footballcoachmanager.R;

import java.util.ArrayList;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Esta clase permite la personalizacion del dibujado del objeto
 * BlackBoard.
 * Esta clase forma parte del objeto personalizado creado de cero
 * llamado 'BlackBoard' y que ahora es accesible desde el panel de
 * objetos, seccion 'Custom & Library Views'. El objeto esta definido
 * en el XML 'attrs.xml' de la carpeta 'values'.
 * 
 * @author Kevin Fernandez
 * @see attrs.xml
 *
 */
public class PizarraDigital extends View {
	private Paint paint = new Paint(); // Generamos objetos para dibujar en el View.
	private Path path = new Path();
	private Bitmap bitmap_x, bitmap_circle; // Almacena en Bitmap las imagens a usar en BlackBoard.
	private ArrayList<Bitmap> drawObjects = new ArrayList<Bitmap>(); // Este ArrayList almacenara los objetos dibujados.
	private ArrayList<Float> Xcoords = new ArrayList<Float>(); // Este ArrayList almacena las coordenadas X de los bitmaps.
	private ArrayList<Float> Ycoords = new ArrayList<Float>(); // Este ArrayList almacena las coordenadas Y de los bitmaps.
	private int mode; // Indica el modo de dibujado (0, 1, 2).
	private float eventX; // Guarda las ultimas coordenadas X.
	private float eventY; // Guarda las ultimas coordenadas Y.
	
	public PizarraDigital(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (!this.isInEditMode()) // Este 'if' es solo para desarrollo. Asi la simulacion en eclipse no da problemas.
			setBackgroundResource(R.drawable.blackboard_field); // Le pasamos la imagen de fondo.

		// Configuramos el objeto que dibuja en el View.
		paint.setAntiAlias(true); // Alisado en la definicion.
		paint.setStrokeWidth(6f); // Tamaño del trazo en ancho.
		paint.setColor(Color.BLACK); // Color de la trazada.
		paint.setStyle(Paint.Style.STROKE); // Estilo de dibujado.
		paint.setStrokeJoin(Paint.Join.ROUND); // Acabado del trazo.
		
		mode = 0; // Por defecto se establece el modo trazado libre.
		
		// Se obtienen recursos para imagenes de circulo y equis.
		Resources res = getResources();
		bitmap_circle = BitmapFactory.decodeResource(res, R.drawable.bb_shape_circle);
		bitmap_x = BitmapFactory.decodeResource(res, R.drawable.bb_shape_x);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// Cada vez que se ha de dibujar en el canvas del View se hace de cero,
		// esto quiere decir que el canvas no almacena un dibujado anterior, y
		// por lo tanto se ha de volver a mandar la orden de dibujado de los
		// objetos anteriores mas el nuevo objeto. De ahi que se almacenen en
		// un ArrayList los objetos Bitmap junto a sus coordenadas de esta clase.

		canvas.drawPath(path, paint);

		for(int i= 0; i<drawObjects.size();i++)
			canvas.drawBitmap(drawObjects.get(i), Xcoords.get(i), Ycoords.get(i), null);

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// Obtenemos posicion del dedo cada vez que este metodo es llamado.
		eventX = event.getX();
		eventY = event.getY();

		switch(mode){
		case 0:
			// Este modo es de trazado libre.
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				// Este evento sucede cuando se detecta que el dedo ha bajado a pantalla.
				path.moveTo(eventX, eventY);
				return true;
			case MotionEvent.ACTION_MOVE:
				// Este evento sucede cuando se detecta que el dedo se mueve por el View.
				path.lineTo(eventX, eventY);
				break;
			case MotionEvent.ACTION_UP:
				// Este evento sucede cuando se detecta que el dedo ha salido de pantalla.
				break;
			default:
				return false;
			}
			break;
		case 1:
			// Este modo es de dibujado de circulos.
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				drawObjects.add(bitmap_circle);
				Xcoords.add(eventX - 48f);
				Ycoords.add(eventY - 48f);
				break;
			}
			break;
		case 2:
			// Este modo es de dibujado de equis.
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				drawObjects.add(bitmap_x);
				Xcoords.add(eventX - 48f);
				Ycoords.add(eventY - 48f);
				break;
			}
			break;
		default:
			break;

		}
		
		invalidate(); // Permite el redibujado del View actualizando cambios.
		return true;
	}

	/**
	 * Establece el modo de dibujado.
	 * 0 para trazar linea libre.
	 * 1 para poner circulos.
	 * 2 para poner equis.
	 * @param m : tipo de modo a seleccionar.
	 */
	public void setMode(int m){
		this.mode = m;
	}
}