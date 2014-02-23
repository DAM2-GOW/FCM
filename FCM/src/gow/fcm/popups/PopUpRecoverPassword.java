package gow.fcm.popups;

import gow.fcm.footballcoachmanager.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class PopUpRecoverPassword extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.popup_recover_password);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.popup_recover_password, menu);
		return true;
	}

}
