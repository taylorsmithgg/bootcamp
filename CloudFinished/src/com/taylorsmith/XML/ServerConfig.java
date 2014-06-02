package com.taylorsmith.XML;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "serverconfig")
@XmlAccessorType(XmlAccessType.FIELD)
public class ServerConfig {

	public ServerConfig() {

	}

	@XmlElement(name = "port")
	int port = 2663;

	@XmlElement(name = "developer")
	String developer = "Taylor Smith";

	@XmlElement(name = "shareDirectoryPath")
	String shareDirectory = ".\\shared\\";

	@XmlElement(name = "historyPath")
	String historyPath = ".\\history\\";

	public int getPort() {
		return port;
	}

	public String getDeveloper() {
		return developer;
	}

	public String getShareDirectory() {
		return shareDirectory;
	}

}
