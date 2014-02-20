package gow.fcm.pantallas;

import gow.fcm.footballcoachmanager.R;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.view.View.OnClickListener;

public class PantallaAcercaDe extends Activity{


	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_acerca_de);

	        //Función del botón que lleva a la web del repositorio de github al hacer click sobre él.
	        ImageButton botonGh = (ImageButton) findViewById(R.id.imageButtonGh);
	        botonGh.setOnClickListener(new OnClickListener() {
	        	
	            public void onClick(View v) {
	                Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
	                myWebLink.setData(Uri.parse("https://github.com/DAM2-GOW/FCM/"));
	                    startActivity(myWebLink);
	             }
	        });
	        
	      //Función del botón que lleva a la web de la florida al hacer click sobre él.
	        ImageButton botonFlo = (ImageButton) findViewById(R.id.imageButtonFlorida);
	        botonFlo.setOnClickListener(new OnClickListener() {
	        	
	            public void onClick(View v) {
	                Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
	                myWebLink.setData(Uri.parse("http://www.florida-uni.es/web_es/home.php"));
	                    startActivity(myWebLink);
	             }
	        });
	        
	      //Función del botón que lleva al blog del equipo al hacer click sobre él.
	        ImageButton botonBlog = (ImageButton) findViewById(R.id.imageButtonBlogger);
	        botonBlog.setOnClickListener(new OnClickListener() {
	        	
	            public void onClick(View v) {
	                Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
	                myWebLink.setData(Uri.parse("http://damgow.blogspot.com.es/"));
	                    startActivity(myWebLink);
	             }
	        });
	        
	    }
}
