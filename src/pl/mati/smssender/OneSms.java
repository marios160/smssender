package pl.mati.smssender;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class OneSms extends Activity {

	TextView znaki;
	EditText pole;
	Context context;
	FileInputStream inputStream;
//	static String path;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.one_sms);
		File f = Environment.getExternalStorageDirectory();
		File folder = new File(Environment.getExternalStorageDirectory() + File.separator + "SmsSender");
		if (!folder.exists()) {
			folder.mkdir();
		}
		znaki = (TextView) findViewById(R.id.ilZnakow);
		pole = (EditText) findViewById(R.id.msg);


		pole.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				double x = pole.getText().toString().length() / 160;
				int y = (int) (Math.floor(x) + 1);
				znaki.setText(Integer.toString(y));

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub

			}
		});

	}
	
	public void edytuj(View view) {
		
		Intent intent = new Intent(this, Aktualizacja.class);
		startActivity(intent);
		
	}

	public void sendSms(View view) {
		EditText msg = (EditText) findViewById(R.id.msg);
		String wiadomosc = msg.getText().toString();

		
		SmsManager sm = SmsManager.getDefault();
		ArrayList<String> parts = sm.divideMessage(wiadomosc);
		int numParts = parts.size();
		ArrayList<PendingIntent> deliveryIntents = new ArrayList<PendingIntent>(numParts);
		ArrayList<PendingIntent> sentIntents = new ArrayList<PendingIntent>(numParts);
		
		File f = Environment.getExternalStorageDirectory();
		File file2 = new File(f.getAbsolutePath() + "/SmsSender/kontakty.txt");
		if (!file2.exists()) {
			Toast.makeText(this, "Nie ma listy!", Toast.LENGTH_SHORT).show();
		}
		List<String> numery = new ArrayList<String>();
		Scanner s = null;
		try {
			s = new Scanner(file2, "ISO-8859-2");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
		}
		while (s.hasNextLine()) {
			String linia = s.nextLine();
			if(linia.length() > 9){
				numery.add(linia.substring(0,9));
			}
		}


		try {

			for (String numer : numery) {
				sm.sendMultipartTextMessage(numer, null, parts, sentIntents, deliveryIntents);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
		}

		Toast.makeText(this, "Wyslano!", Toast.LENGTH_SHORT).show();
		msg.setText("");

	}
	
//	private static final int FILE_SELECT_CODE = 0;
//
//	public void showFileChooser(View view) {
//	    Intent intent = new Intent(Intent.ACTION_GET_CONTENT); 
//	    intent.setType("*/*"); 
//	    intent.addCategory(Intent.CATEGORY_OPENABLE);
//
//	    try {
//	        startActivityForResult(
//	                Intent.createChooser(intent, "Select a File to Upload"),
//	                FILE_SELECT_CODE);
//	    } catch (android.content.ActivityNotFoundException ex) {
//	        // Potentially direct the user to the Market with a Dialog
//	        Toast.makeText(this, "Please install a File Manager.", 
//	                Toast.LENGTH_SHORT).show();
//	    }
//	}
//	
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//	    switch (requestCode) {
//	        case FILE_SELECT_CODE:
//	        if (resultCode == RESULT_OK) {
//	            // Get the Uri of the selected file 
//	            Uri uri = data.getData();
//	            Log.d("TAG", "File Uri: " + uri.toString());
//	            // Get the path
//	            path = null;
//				try {
//					path = getPath(this, uri);
//				} catch (URISyntaxException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//	            Log.d("TAG", "File Path: " + path);
//	            // Get the file instance
//	            // File file = new File(path);
//	            // Initiate the upload
//	        }
//	        break;
//	    }
//	    super.onActivityResult(requestCode, resultCode, data);
//	}
//	
//	public static String getPath(Context context, Uri uri) throws URISyntaxException {
//	    if ("content".equalsIgnoreCase(uri.getScheme())) {
//	        String[] projection = { "_data" };
//	        Cursor cursor = null;
//
//	        try {
//	            cursor = context.getContentResolver().query(uri, projection, null, null, null);
//	            int column_index = cursor.getColumnIndexOrThrow("_data");
//	            if (cursor.moveToFirst()) {
//	                return cursor.getString(column_index);
//	            }
//	        } catch (Exception e) {
//	            // Eat it
//	        }
//	    }
//	    else if ("file".equalsIgnoreCase(uri.getScheme())) {
//	        return uri.getPath();
//	    }
//
//	    return null;
//	} 

}
