package com.example.emailapplication;

import java.util.ArrayList;
import java.util.Vector;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class EmailListActivity extends ListActivity {
	
	ArrayList<String> classes;		//Container for message headers
	String server;
	String Port;
	String userName;
	String password;
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		
		super.onListItemClick(l, v, position, id);
		Intent openEmailListActivity = new Intent(EmailListActivity.this, mailTextActivity.class);
		//Sending credentials to mail text activity
		openEmailListActivity.putExtra("host", server);
		openEmailListActivity.putExtra("port", Port);
		openEmailListActivity.putExtra("user", userName);
		openEmailListActivity.putExtra("pass", password);
		openEmailListActivity.putExtra("position", position);
		startActivity(openEmailListActivity);
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//Taking credentials from main activity
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		classes = extras.getStringArrayList("key");
		server = extras.getString("host") ;
		Port = extras.getString("port");
		userName = extras.getString("user");
		password = extras.getString("pass");
		setListAdapter(new ArrayAdapter<String>(EmailListActivity.this, android.R.layout.simple_list_item_1, classes));
	}
}
