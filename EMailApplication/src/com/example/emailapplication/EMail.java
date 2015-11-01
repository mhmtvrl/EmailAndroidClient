package com.example.emailapplication;

public class EMail {
		private String date;
		private String from;
		private String subject;
		private int messageId;
		private String messageBody;
		
		
		public String getDate() {
			return date;
		}


		public void setDate(String date) {
			this.date = date;
		}


		public String getFrom() {
			return from;
		}


		public void setFrom(String from) {
			this.from = from;
		}


		public String getSubject() {
			return subject;
		}


		public void setSubject(String subject) {
			this.subject = subject;
		}


		public int getMessageId() {
			return messageId;
		}


		public void setMessageId(int messageId) {
			this.messageId = messageId;
		}


		public String getMessageBody() {
			return messageBody;
		}


		public void setMessageBody(String messageBody) {
			this.messageBody = messageBody;
		}


		public String toString() {
			return messageId + " " + date + " " + from + " " + subject;
		}
	}