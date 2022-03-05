package pl.mati.smssender;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Aktualizacja extends Activity {

	int iloscNr = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aktualizacja);
		File f = Environment.getExternalStorageDirectory();
		File file2 = new File(f.getAbsolutePath() + "/SmsSender/kontakty.txt");
		if (!file2.exists()) {
			TextView text = (TextView) findViewById(R.id.lista);
			text.setText("");
			return;
		}
		TextView ilosc = (TextView) findViewById(R.id.iloscNr);

		try {

			Scanner s = new Scanner(file2, "ISO-8859-2");
			String linia = "";
			int i = 0;
			while (s.hasNextLine()) {
				linia += s.nextLine() + "\n";
				if(linia.length() > 9){
					iloscNr++;
				}
			}
			ilosc.setText(Integer.toString(iloscNr));
			TextView text = (TextView) findViewById(R.id.lista);
			text.setText(linia);
			s.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public void zapisz(View view) {
		try {
			PrintWriter base = null;
			File f = Environment.getExternalStorageDirectory();
			base = new PrintWriter(f.getAbsolutePath() + "/SmsSender/kontakty.txt","ISO-8859-2");
			TextView text = (TextView) findViewById(R.id.lista);
			base.write(text.getText().toString());
			System.out.println(text.getText().toString());
			base.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		this.onBackPressed();
	}

	public void anuluj(View view) {
		this.onBackPressed();
	}

}
