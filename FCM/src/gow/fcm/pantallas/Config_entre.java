package gow.fcm.pantallas;

import gow.fcm.footballcoachmanager.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Config_entre extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_config_entre);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.config_entre, menu);
		return true;
	}

}
