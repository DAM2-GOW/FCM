package gow.fcm.pantallas;

import gow.fcm.footballcoachmanager.R;
import gow.fcm.utilidades.ArrayAdapterStatsList;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;

public class Config_entre extends Activity {
ArrayAdapterStatsList aasl;

@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_config_entre);
		
		ListView lv = (ListView)findViewById(R.id.listView1); 
		
		//Aqui llamo a la base de datos y obtengo datos.
		
		/*
		aasl = new ArrayAdapterStatsList(context, titulo, tipo, datoEspecial)*/
		
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.config_entre, menu);
		return true;
	}

}
