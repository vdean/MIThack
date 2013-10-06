package com.example.telpic;

import com.example.telpic.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Sketch view = (Sketch) findViewById(R.id.sketch);
		Button submit = (Button) findViewById(R.id.submit);
		Button clear = (Button) findViewById(R.id.clear);
		view.setOnTouchListener(view);
		submit.setOnClickListener(this);
		clear.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
        case R.id.NewGame:
        	break;
        	
        case R.id.Quit:
            Toast.makeText(this /*Needs a Context*/, "Quit", Toast.LENGTH_SHORT).show();
//            Intent intent = getIntent();
//            finish();
//            startActivity(intent);
            return true;
        }
        
        return false;
    }
    
    boolean isIn(char c, String str)
    {
        int len = str.length();
        for (int i=0; i < len; i++)
        {
            if (str.charAt(i) == c) return true;
        }
        return false;
    }

	@Override
	public void onClick(View v) {
		Button b = (Button)v;
		String text = b.getText().toString();
//	    switch (text){
//	    case "Clear":
//	    	break;
//	    case "Submit":
//	    	break;	    
//	    default:
//	    	break;
	    }

	}
	