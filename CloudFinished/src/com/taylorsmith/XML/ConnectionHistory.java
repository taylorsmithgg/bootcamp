package com.taylorsmith.XML;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ConnectionHistory {
	public ConnectionHistory() {

	}

	public ConnectionHistory(ArrayList<String> arrayList) {
		this.clientHistory = arrayList;
	}

	@XmlElement(name = "developer")
	private String developer = "Taylor Smith";
	@XmlElementWrapper(name = "String")
	@XmlElement(name = "client")
	private ArrayList<String> clientHistory;

	public ArrayList<String> getClientHistory() {
		return clientHistory;
	}

	public void setClientHistory(ArrayList<String> client) {
		this.clientHistory = client;
	}

}
