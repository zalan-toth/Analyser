package net.pyel.models;

import java.util.HashMap;

public class Labeler {

	private HashMap<Integer, PillType> pillTypes = new HashMap<>();

	public Labeler(HashMap<Integer, PillType> pillTypes) {
		this.pillTypes = pillTypes;
	}

	public Labeler() {
	}

	public void addPillType(PillType pillType) {
		pillTypes.put(pillType.getColor1(), pillType);
	}


	public void removeAllPillTypes() {
		pillTypes = null;
		pillTypes = new HashMap<>();
	}

	public HashMap<Integer, PillType> getPillTypes() {
		return pillTypes;
	}

	public void setPillTypes(HashMap<Integer, PillType> pillTypes) {
		this.pillTypes = pillTypes;
	}

}
