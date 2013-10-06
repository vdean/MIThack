package com.example.timeline;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;


public class MainActivity extends Activity {

	SimpleAdapter adapter;
	static final int add_index=12;
	private static final int REMOVE = 11;
	ArrayList <Map<String,String>> myEvents=new ArrayList <Map<String, String>>();
	ListView myList;
	String[] from = {"name", "countDown"};
    int[] to = {R.id.eventName, R.id.countDown };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		buildData();
		myList = (ListView) findViewById(R.id.myAdapterView);
		adapter = new SimpleAdapter(this, myEvents, R.layout.list_item, from, to);
		Log.d("Mine","created adapter");
		myList.setAdapter(adapter); 
        registerForContextMenu(myList);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);

		Log.d("Mine", "onCreateContextMenu");
		menu.setHeaderTitle("Event Removal Selection");
		menu.add(Menu.NONE, REMOVE, Menu.NONE, "Remove");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		super.onContextItemSelected(item);

		Log.d("Mine", "onContextItemSelected");
		switch (item.getItemId()) {
		case (REMOVE): {
			
			AdapterView.AdapterContextMenuInfo menuInfo;
			menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
			int index = menuInfo.position;
			Log.d("Mine","index: " + index);
			removeItem(index);
			return true;
		}
		}
		return false;
	}
	private void removeItem(int _index) {
		Log.d("Mine","in remove");
		String eraseThis = myEvents.get(_index).get("name");
		myEvents.remove(_index);
		ClientDB.delete(this, eraseThis);
		Log.d("Mine","Earse: "+ eraseThis);
		adapter.notifyDataSetChanged();
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.mymenu, menu);

		return true;
	}
	

	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId()){
			case R.id.addEvent:
				Log.d("Mine", "Calling intent addEvent");
				Intent intent = new Intent(this, AddEvent.class);
				Log.d("Mine", "Calling startActivity");
				startActivityForResult(intent, add_index);
				return true;
		}

		return false;
	}
	
	@Override 
    public void onActivityResult(int reqCode, int resCode, Intent data) {
        super.onActivityResult(reqCode, resCode, data);

        Long results;
        Log.d("Mine", "onActivityResult");
        if (resCode != Activity.RESULT_OK)
        {
           results=(long) 0;
        }
        else
        {
            switch (reqCode)
            {
            case add_index:
            	Log.d("Mine","getting long and string");
                results =data.getLongExtra("timeEntered", 0);
                String eventNam = data.getStringExtra("event");
                Log.d("Mine","reult: "+ results +" event: "+eventNam);
                myEvents.add(putData(eventNam, (getDaysLeft(results))));
                Log.d("Mine","event added");
                adapter = new SimpleAdapter(this, myEvents, R.layout.list_item, from, to);
        		Log.d("Mine","created adapter");
        		myList.setAdapter(adapter); 
                break;
           
            }
            
            adapter.notifyDataSetChanged();
        }
    }

	
	public void buildData()
	{
		myEvents.clear();
		Cursor cursor = ClientDB.getEvent(this);
		if (cursor==null)
		{
			Log.d("Mine","Nothing in database");
			//myEvents.add(putData("Add a event", "Now"));
			
		}
		else{
			
        while(cursor.moveToNext())
        {
        	Log.d("Mine","shift: "+ cursor.getString(1)+" : " + cursor.getLong(5));
        	myEvents.add(putData(cursor.getString(1), getDaysLeft(cursor.getLong(5))));
        }
		}
		Log.d("Mine","myEvents:::: "+myEvents.size());
	}
	
	private HashMap<String, String> putData(String name, String daysLeft) {
		Log.d("Mine","In putData");
	    HashMap<String, String> item = new HashMap<String, String>();
	    Log.d("Mine","name: "+name+ " days: "+daysLeft);
	    item.put("name", name);
	    item.put("countDown", daysLeft);
	    Log.d("Mine","Return from putData");
	    return item;
	  }
	public String getDaysLeft(long eventTime)
	{ 	Log.d("Mine","getDaysLeft");
		String numLeft = "";
		Date currDate = new Date();
		Log.d("Mine","eventTime: "+eventTime);
		Log.d("Mine","currTime: "+currDate.getTime());
		long millisLeft = eventTime - currDate.getTime();
		Log.d("Mine","millis: "+millisLeft);
		int daysLeft = (int) (millisLeft/86400000);
		Log.d("Mine","daysleft: "+ daysLeft);
		if (daysLeft != 0) {
			numLeft = " " + daysLeft + " days";
			return numLeft;
		} else {
			return "Done!";
		}
		}
	}
