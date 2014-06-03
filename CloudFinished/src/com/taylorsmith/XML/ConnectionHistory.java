package com.taylorsmith.XML;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;

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
