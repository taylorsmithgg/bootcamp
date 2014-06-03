package com.taylorsmith.XML;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.taylorsmith.client.Client;
import com.taylorsmith.server.Server;

@XmlRootElement(name = "serverconfig")
@XmlAccessorType(XmlAccessType.FIELD)
public class ServerConfig {
	public ServerConfig(){
		
	}
	@XmlElement(name = "port")
	int port = 2663;

	@XmlElement(name = "developer")
	String devName = Server.DEVELOPER;

	@XmlElement(name = "shareDirectoryPath")
	String shareDirectory = ".\\shared\\";

	@XmlElement(name = "historyPath")
	String historyPath = ".\\history\\";

	public int getPort() {
		return port;
	}

	public String getDevName() {
		return devName;
	}

	public String getShareDirectory() {
		return shareDirectory;
	}

	public String getHistoryPath() {
		return historyPath;
	}
}
