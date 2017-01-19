package com.espire.campaign.exception;

import org.apache.log4j.Logger;

public class DBException extends Exception {


	final static Logger log = Logger.getLogger(DBException.class);	
	private static final long serialVersionUID = 5716989367714954339L;

	public DBException(){
		super();
	}
	
	public DBException(String message){
		super(message);
		log.error(message);
	}
	
}
