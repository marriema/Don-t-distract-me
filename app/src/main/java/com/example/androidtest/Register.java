package com.example.androidtest;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import java.io.*;
import java.sql.*;
import android.text.Editable;
import android.text.InputType;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;



/**
 * Created by Yanqing Ma on 11/1/2016.
 */
public class Register extends Activity {
	EditText cat,des,time;
	TextView t1, t3;
	Button ok,cancel,account;
	public int clock;



	protected void onCreate(Bundle savedInstanceState){
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addnew);

		cat = (EditText)findViewById(R.id.title);
		des =(EditText)findViewById(R.id.detail);
		t1 = (TextView)findViewById(R.id.Text02);
		t3 = (TextView)findViewById(R.id.textView3);
		t3.setText("Hello, "+((MyApplication)getApplication()).getUsername());
		time = (EditText)findViewById(R.id.EditText01);
		ok = (Button)findViewById(R.id.save);
		cancel = (Button)findViewById(R.id.cancel);
		account = (Button)findViewById(R.id.button3);

	}

	// set user's input time as time and start Count class after pressing "save"
	public void btnSavePressed(View view) throws IOException {
			String com = des.getText().toString();
			if(com.matches("") || time.getText().toString().matches(""))
				Toast.makeText(getApplicationContext(), "empty time or descrpition", Toast.LENGTH_SHORT).show();
			else
			{
				clock = Integer.parseInt(time.getText().toString());
				((MyApplication) getApplication()).setTime(clock);
				DatabaseHelper dbhelper = ((MyApplication) getApplication()).getDB();
				dbhelper.insertRecord(((MyApplication) getApplication()).getUsername(), com, clock, 1);
				((MyApplication) getApplication()).setDs(com);
				System.out.println(dbhelper.getList());
				Intent intent = new Intent(this, Count.class);
				startActivity(intent);
			}
	}



	//return to previous stage
	public void btnCancelPressed(View view) {
		Intent intent1 = new Intent(this, First.class);
		startActivity(intent1);
	}



	/**
	 * button trigger for "view account" button
	 *
	 * @param  view  current view
	 */
	public void btnViewAccount(View view) {
		Intent intent1 = new Intent(this, Account.class);
		startActivity(intent1);
	}



//	public void onClick(View v) {
//			switch (v.getId()) {
//				case R.id.save:
//					clock = Integer.parseInt(time.getText().toString());
//					((MyApplication)getApplication()).setTime(clock);
//					Intent intent = new Intent("com.example.androidtest.COUNT" );
//					startActivity(intent);
//					break;
//
//				case R.id.cancel:
//					Intent intent1 = new Intent("com.example.androidtest.FIRST" );
//					startActivity(intent1);
//					break;
//
//
//			}
//
//		}

	}

