package com.example.timeline;

import java.util.GregorianCalendar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

public class AddEvent extends Activity implements OnClickListener {
	DatePicker date;
	Button sumit;
	EditText name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("Mine", "In addEvent");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add);
		name = (EditText) findViewById(R.id.name);
		date = (DatePicker) findViewById(R.id.date);
		sumit = (Button) findViewById(R.id.sumit);
		sumit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String event = name.getText().toString();
		int month = date.getMonth();
		int day = date.getDayOfMonth();
		int year = date.getYear();
		GregorianCalendar gc = new GregorianCalendar();
		gc.clear();
		gc.set(year, month, day);
		long oldMillis = gc.getTimeInMillis();
		Log.d("Mine", "Millis is: " + oldMillis);
		ClientDB.insert(this, event, month, day, year, oldMillis);
		Log.d("Mine", "Date being saved: " + month + " " + day + " " + year);
		Intent outData = new Intent();
		outData.putExtra("event", event);
		outData.putExtra("timeEntered", oldMillis);
		setResult(Activity.RESULT_OK, outData);
		finish();

	}

}
