package com.taylorsmith.XML;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.taylorsmith.server.Server;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ConnectionHistory {
	public ConnectionHistory(){
		
	}
	
	public ConnectionHistory(ArrayList<String> arrayList){
		this.client = arrayList;
	}
	
	@XmlElement(name = "developer")
	private String deveveloper = Server.DEVELOPER;

	@XmlElementWrapper(name = "clients")
	@XmlElement(name = "client")
	private ArrayList<String> client;

	public ArrayList<String> getClient() {
		return client;
	}

	public void setClientHistory(ArrayList<String> client) {
		this.client = client;
	}

}
