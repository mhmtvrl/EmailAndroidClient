package com.example.emailapplication;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class mailTextActivity extends Activity {
	
	private TextView screen;
	private Connector con;
	private Session session;
	String server;
	String Port;
	String userName;
	String password;
	int position;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mailtextlayout);
		screen = (TextView) findViewById(R.id.body);
		
		Bundle extras = getIntent().getExtras();
		server = extras.getString("host") ;
		Port = extras.getString("port");
		userName = extras.getString("user");
		password = extras.getString("pass");
		position = extras.getInt("position");
		AsynTaskRunner runner = new AsynTaskRunner();
		runner.execute(server, Port, userName, password, Integer.toString(position + 1) );
		
		
		
		//System.out.println(server + Port + userName + password + position);
		
	}
	
	private class AsynTaskRunner extends AsyncTask<String, String, String>
	{

		@Override
		protected String doInBackground(String... params) {
			session = new Session(params[0], Integer.parseInt(params[1]), params[2], params[3]);
			con = new Connector(session);
			con.connect();
			con.login();
			con.selectInbox();
			String resp = con.writeMail(params[4]);
			con.logOut();
			con.disconnect();
			return resp;
		}
		
		@Override
		protected void onPreExecute()
		{
			
		}

		@Override
		protected void onProgressUpdate(String... text)
		{
			
		}
		
		@Override
		protected void onPostExecute(String result)
		{
			screen.setText(result);
			
		}
	}

}
