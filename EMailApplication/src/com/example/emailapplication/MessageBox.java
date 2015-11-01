package com.example.emailapplication;

import java.util.ArrayList;
import java.util.Vector;


public class MessageBox {
	private ArrayList<EMail> box;
	
	public MessageBox()
	{
		box = new ArrayList<EMail>();
		box.add(null);
	}
        
        public int getSize()
        {
            return box.size();
        }
	
	public void printMessageBox()
	{
		for(int i = 1; i < box.size(); i++)
		{
			System.out.println(i + "nd message");
			System.out.println(box.get(i));
		}
	}
	
	public void addMessageBox(EMail email)
	{
		box.add(email);
	}

	public ArrayList<EMail> getBox() {
		return box;
	}

	public void setBox(ArrayList<EMail> box) {
		this.box = box;
	}
	
	public EMail getMessage(int id)
	{
		return this.box.get(id);
	}

}
