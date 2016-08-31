package com.example.androidtest;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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

public class Register extends Activity {
	EditText cat,des,time;
	Spinner s1,s2;
	TextView t1;
	Button ok,cancel;
	public int clock;



	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addnew);

		cat = (EditText)findViewById(R.id.title);
		des =(EditText)findViewById(R.id.detail);
		s1 = (Spinner)findViewById(R.id.spinner01);
		s2 = (Spinner)findViewById(R.id.spinner02);
		t1 = (TextView)findViewById(R.id.Text02);
		time = (EditText)findViewById(R.id.EditText01);
		ok = (Button)findViewById(R.id.save);
		cancel = (Button)findViewById(R.id.cancel);
	}

	public void btnSavePressed(View view){
		clock = Integer.parseInt(time.getText().toString());
		((MyApplication)getApplication()).setTime(clock);
		Intent intent = new Intent(this, Count.class);
		startActivity(intent);

	}

	public void btnCancelPressed(View view) {
		Intent intent1 = new Intent(this, First.class);
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

