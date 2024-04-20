package net.pyel.models;

import java.util.ArrayList;

public class Labeler {

	private ArrayList<PillType> pillTypes = new ArrayList<>();

	public Labeler(ArrayList<PillType> pillTypes) {
		this.pillTypes = pillTypes;
	}

	public Labeler() {
	}

	public void addPillType(PillType pillType) {
		pillTypes.add(pillType);
	}


	public void removeAllPillTypes() {
		pillTypes = null;
		pillTypes = new ArrayList<>();
	}

	public ArrayList<PillType> getPillTypes() {
		return pillTypes;
	}

	public void setPillTypes(ArrayList<PillType> pillTypes) {
		this.pillTypes = pillTypes;
	}

}
