package gow.fcm.pantallas;

import gow.fcm.footballcoachmanager.R;
import gow.fcm.popups.PopUp_config_entre;
import gow.fcm.utilidades.ArrayAdapterStatsList;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

public class Config_entre extends Activity {
	ArrayAdapterStatsList aasl;
	ImageButton bt;
	String[] dato1;
	String[] dato2;
	String[] modulo;
	View header;
	ArrayAdapterStatsList adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_config_entre);

		ListView lv = (ListView) findViewById(R.id.listView1);

		header = (View)getLayoutInflater().inflate(R.layout.list_header_entrenamientos, null);
		
		lv.addHeaderView(header);
		
		lv.setAdapter(adapter);
		
		bt = (ImageButton) findViewById(R.id.imageButton1);

		bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent info = new Intent(Config_entre.this,PopUp_config_entre.class);
				startActivityForResult(info, 1);

			}
		});
		// Aqui llamo a la base de datos y obtengo datos.

		/*
		 * aasl = new ArrayAdapterStatsList(context, titulo, tipo, datoEspecial)
		 */

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent datos) {

		if (requestCode == 1) {

			if (resultCode == RESULT_OK) {
				 dato1 = datos.getStringArrayExtra("txtDato1");
				 dato2 = datos.getStringArrayExtra("txtDato2");
				 modulo = datos.getStringArrayExtra("modulo");
				 
				 ArrayAdapterStatsList adapter = new ArrayAdapterStatsList(this,modulo,dato1,dato2);
			}
			if (resultCode == RESULT_CANCELED) {
				// Write your code if there's no result
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.config_entre, menu);
		return true;
	}

}
