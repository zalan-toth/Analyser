package net.pyel.models;

import java.util.HashMap;

public class Labeler {

	/*private ArrayList<PillType> pillTypes = new ArrayList<>();

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
	}*/
	private HashMap<Integer, PillType> pillTypes = new HashMap<>();

	public Labeler(HashMap<Integer, PillType> pillTypes) {
		this.pillTypes = pillTypes;
	}

	public Labeler() {
	}

	public void addPillType(int location, PillType pillType) {
		pillTypes.put(location, pillType);
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
