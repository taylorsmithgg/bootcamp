package com.taylorsmith.XML;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DownloadHistory {
	public DownloadHistory(){
		
	}
	@XmlElementWrapper(name = "downloads")
	@XmlElement(name = "file")
	private ArrayList<String> downloadHistory;

	public ArrayList<String> getDownloadHistory() {
		return downloadHistory;
	}

	public void setDownloadHistory(ArrayList<String> downloadHistory) {
		this.downloadHistory = downloadHistory;
	}
}
