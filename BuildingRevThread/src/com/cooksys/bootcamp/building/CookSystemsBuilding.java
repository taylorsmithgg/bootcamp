package com.cooksys.bootcamp.building;

public class CookSystemsBuilding extends Building {

	public CookSystemsBuilding(String name,int numRooms,int numFloors,double cost,long buildTime){
		super();
		setName(name);
		setNumRooms(numRooms);
		setNumFloors(numFloors);
		setCost(cost);
		this.buildTime = buildTime;
	}
	
	public CookSystemsBuilding(){
		
	}

	public void built() {
		System.out.println(this + " built!");
	}

	@Override
	public String toString() {
		return "Cook Systems Building";
	}

}
