package com.espire.campaign.exception;

public class DBException extends Exception {


	private static final long serialVersionUID = 5716989367714954339L;

	public DBException(){
		super();
	}
	
	public DBException(String message){
		super(message);
	}
	
}
