package com.example.emailapplication;

import java.util.Vector;


public class Session {
        private int id;
	private String server;
	private int port;
	private String username;
	private String pass;
        private Vector<Session> sessionList;
	
	public Session(String server, int port, String username, String pass) {
		this.server = server;
		this.port = port;
		this.username = username;
		this.pass = pass;
	}
        
        public Vector<Session> getSessionList()
        {
            return sessionList;
        }
        
        public void addSessionList(Session session)
        {
            sessionList.add(session);
        }
       

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
}
