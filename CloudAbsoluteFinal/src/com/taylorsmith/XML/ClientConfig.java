package com.taylorsmith.XML;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.taylorsmith.client.Client;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class ClientConfig {
	public ClientConfig(){
		
	}

	@XmlElement
	private int serverPort = 2663;

	@XmlElement
	private String downloadPath = ".\\download\\";

	@XmlElement
	private String downloadHistoryPath = ".\\config\\";

	public int getServerPort() {
		return serverPort;
	}

	public String getDownloadPath() {
		return downloadPath;
	}

	public String getDownloadHistoryPath() {
		return downloadHistoryPath;
	}
}
