package com.example.emailapplication;

import java.util.ArrayList;
import java.util.Vector;

import javax.net.ssl.SSLSocketFactory;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	//GUI components
	public static Button connect;
	public static Button disconnect;
	public static EditText host;
	public static EditText port;
	public static EditText user;
	public static EditText pass;
	public static TextView screen;
	public static MessageBox box;
	/*******************************/
	
	public static Session session;
	public static Connector con;
	public static ArrayList<String> body;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Setting reference to gui components
		connect = (Button) findViewById(R.id.connect);
		disconnect = (Button) findViewById(R.id.disconnect);
		host = (EditText) findViewById(R.id.host);
		port = (EditText) findViewById(R.id.port);
		user = (EditText) findViewById(R.id.user);
		pass = (EditText) findViewById(R.id.pass);
		screen = (TextView) findViewById(R.id.display);	
		
		
		//Connect Button
		connect.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//Taking credentials
				String server = host.getText().toString();
				String Port = port.getText().toString();
				String userName = user.getText().toString();
				String password = pass.getText().toString();
				//Server Connection
				AsynTaskRunner runner = new AsynTaskRunner();
				runner.execute(server, Port, userName, password);
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//Switching to mail list screen
				Intent openEmailListActivity = new Intent(MainActivity.this, EmailListActivity.class);
				ArrayList<String> sent = new ArrayList<String>();   //Container for mail headers
				
				//Assigning elements of message box to header container
				for(int i = 1; i < box.getSize(); i++)
				{
					sent.add(box.getMessage(i).toString());
				}
				
				
				openEmailListActivity.putExtra("key", sent);			//Sending header container to mail list activity
				//Sending Credentials to maillist activity
				openEmailListActivity.putExtra("host", server);
				openEmailListActivity.putExtra("port", Port);
				openEmailListActivity.putExtra("user", userName);
				openEmailListActivity.putExtra("pass", password);
				
				
				startActivity(openEmailListActivity);
			}
		});
		
	}
	
	//Asynchronous task for server connection
	private class AsynTaskRunner extends AsyncTask<String, String, String>
	{
		//Server connection
		@Override
		protected String doInBackground(String... params) {
			session = new Session(params[0], Integer.parseInt(params[1]), params[2], params[3]);
			con = new Connector(session);
			con.connect();					//Connecting to server
			con.login();					//Login into account
			con.selectInbox();				//Select inbox command
			con.readInbox();				//Reading mail headers
			box = con.getBox();				//taking message headers
			String resp = con.logOut();		//Loging out
			con.disconnect();				//closing socket
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
			//Updating screen
			screen.setText(result);
			
		}
	}
	
	
}
