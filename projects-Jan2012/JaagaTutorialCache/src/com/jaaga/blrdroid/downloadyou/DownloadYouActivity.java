package com.jaaga.blrdroid.downloadyou;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Vector;







import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.VideoView;

public class DownloadYouActivity extends Activity {
	// Layout
	View listView; // view
	LinearLayout layout;
	LayoutInflater layoutInflater;
	ListView displayView;
	String array[] = null;
	ArrayAdapter<String> adapter = null;
	String tag =  "DownloadYouActivity";
	public static int listView_rid = R.layout.listview;// resource id
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        layout = (LinearLayout)findViewById(R.id.mainLayout);
		layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		listView = layoutInflater.inflate(listView_rid, null); // state 2
		
		displayView = (ListView) listView.findViewById(R.id.list01);

		layout.addView(displayView);
		
		update();
		
		
        //new Downloader(this).execute();
    }   
    
    public void update() {
		Log.i(tag, "Update!");
		// super.update();
		
			String path = "/sdcard";
			File fil = new File(path);
			if (fil.isDirectory()) {

				array = fil.list();
				for (int i = 0; i < array.length; i++) {

				}
				adapter = new ArrayAdapter<String>(this,
						android.R.layout.simple_list_item_1, array);

			}

			displayView.setAdapter(adapter);
		

	}

}  