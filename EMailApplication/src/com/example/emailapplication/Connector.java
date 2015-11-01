package com.example.emailapplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.Vector;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

//This class handles connection operations

public class Connector implements Serializable {
	
	private Socket socket = null;			//Socket for server connection
	private Scanner scanner = null;			//reads server messages
	private PrintWriter writer = null;		//send to server message
	private Session session;				//keep host port username and password
	private MessageBox box;					//keeps messages
	private BufferedReader output = null;

	//Constructer
	public Connector(Session session) {
		this.session = session;
	}   
        
        //Accessor of message box variable
        public MessageBox getBox()
        {
            return this.box;
        }
	
	//Connects to the server
	//If connects returns true else false
	public String connect()
	{
		String g = "";
		try {
			//Creating new socket
			SSLSocketFactory socketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
			socket = (SSLSocket) socketFactory.createSocket(this.session.getServer(), this.session.getPort());
			//Initializg buffers
			writer = new PrintWriter(socket.getOutputStream(), true);
			scanner = new Scanner(this.socket.getInputStream());
			
			//Reading message that server sends
			String s;
			s = scanner.nextLine().toString();
			System.out.println(s);
			return s;
		} catch (UnknownHostException e) {
			return "UnkownHostException";
		} catch (IOException e) {
			return "IOException";
		}
	}
	
	//Logs in account
	//Returns true if it logs in else false
	public String login()
	{
		String s;
		try {   //Operates login command
			writer.println("A002 Login " + this.session.getUsername() + " " +  this.session.getPass());
			output = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			System.out.println("Scanner setlendi");
			while ((s = output.readLine()) != null)
			{
				System.out.println(s);
				if(s.contains("authenticated (Success)"))
					break;
			}
		} catch (IOException e) {
			return "IOException";
		}
		return "donguden cikti";
	}
        //Operates select inbox command
	public String selectInbox()
	{
		writer.println("A003 SELECT INBOX\r");
		
		String s = null;
		try {
			output = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			System.out.println("dongu girisi");
			while ((s = output.readLine()) != null)
			{
				System.out.println(s);
				if(s.contains("INBOX selected"))
					break;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}
	
        //Reads inbox messages headers
	public void readInbox()
	{
            //Operates fetch command. I have  fetched just 6 message.
		writer.println("A004 FETCH 1:6 (BODY[HEADER.FIELDS (FROM SUBJECT DATE)])\r");
		String s;
		box = new MessageBox(); //inbox object
		int idd = 0;
		
		
		try {
			output = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while ((s = output.readLine()) != null)
			{
				if(s.contains("FETCH"))
				{	
	                            //Assigning response to Email object
					EMail head = new EMail();
					head.setMessageId(++idd);
					head.setDate(output.readLine());
					head.setSubject(output.readLine());
					head.setFrom(output.readLine());
					box.addMessageBox(head);
				}
				if(s.contains("A004 OK Success"))
					break;
			}
			box.printMessageBox();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
        //Getting Email body
	public String writeMail(String MailId)
	{
	
		//Operating command
		writer.println("A005 fetch " + MailId + " body[text]\r");
		String s;
		String acc = "";
		try {
			output = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while ((s = output.readLine()) != null)
			{
				acc += s + "\n";
				if(s.contains("A005 OK Success"))
					break;
			}
			return acc;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
                
			
	}
	//Loging out from server
	public String logOut()
	{
		writer.println("tag logout\r");
		String s = "";
		while(scanner.hasNextLine())
		{
			s = scanner.nextLine();
			System.out.println(s);
		}
		return s;
	}
	
        //Closing socket
	public void disconnect()
	{
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
