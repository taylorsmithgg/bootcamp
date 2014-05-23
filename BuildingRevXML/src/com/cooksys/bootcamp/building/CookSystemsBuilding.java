package com.cooksys.bootcamp.building;

public class CookSystemsBuilding extends Building {

	public CookSystemsBuilding(String name,int numRooms,int numFloors,double cost){
		setName(name);
		setNumRooms(numRooms);
		setNumFloors(numFloors);
		setCost(cost);
	}
	
	public CookSystemsBuilding(){
		
	}

	public void built() {
		System.out.println(this.toString() + " built!");
	}

	@Override
	public String toString() {
		return "Cook Systems Building";
	}

}
