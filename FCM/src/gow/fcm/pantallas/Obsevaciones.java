package gow.fcm.pantallas;

import gow.fcm.footballcoachmanager.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class Obsevaciones extends Activity {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_observaciones);
		//Quitamos el header y mostramos las observaciones
	}

}
