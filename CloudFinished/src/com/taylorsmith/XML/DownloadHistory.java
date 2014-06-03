package com.taylorsmith.XML;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DownloadHistory {
	public DownloadHistory(ArrayList<String> downloadHistory){
		this.downloadHistory = downloadHistory;
	}
	
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
