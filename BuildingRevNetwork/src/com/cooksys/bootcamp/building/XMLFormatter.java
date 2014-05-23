package com.cooksys.bootcamp.building;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Building")
@XmlAccessorType(XmlAccessType.FIELD)
public class XMLFormatter {
	protected List<Building> buildingList;

	public void setBuildingList(List<Building> buildingList) {
		this.buildingList = buildingList;
	}

}
