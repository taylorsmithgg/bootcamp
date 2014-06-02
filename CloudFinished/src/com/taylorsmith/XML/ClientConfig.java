package com.taylorsmith.XML;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class ClientConfig {

	public ClientConfig() {

	}

	@XmlElement
	public int serverPort = 2663;

	@XmlElement
	public String sharedPath = ".\\shared\\";

	@XmlElement
	public String downloadHistoryPath = ".\\history\\";

	public int getServerPort() {
		return serverPort;
	}

	public String getDownloadPath() {
		return sharedPath;
	}

	public String getDownloadHistoryPath() {
		return downloadHistoryPath;
	}
}
