package net.pyel.models;

import java.util.ArrayList;
import java.util.Arrays;
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
	int[] relationSet;
	int[] idSet;
	private HashMap<Integer, PillType> pillTypes = new HashMap<>(); //map id to pilltype

	public int newID() {
		int highestID = -1;
		for (int v = 0; v < idSet.length; v++) {

			if (idSet[v] > highestID) {
				highestID = idSet[v];
			}

		}
		highestID++;
		return highestID;
	}

	public int getID(int location) {
		return idSet[location];
	}

	public Labeler(int size) {
		relationSet = new int[size];
		idSet = new int[size];
		Arrays.fill(idSet, -1);
	}

	public static int find(int[] a, int id) {
		// Check if the id is -1 or out of bounds, and return -1 immediately.
		if (id == -1 || id >= a.length) {
			return -1;
		}

		while (a[id] != id) {
			// Additional check to prevent ArrayIndexOutOfBoundsException
			// if a[id] is -1 or out of valid range (this depends on your logic)
			if (a[id] == -1 || a[id] >= a.length) {
				return -1;
			}
			id = a[id];
		}
		return id;
	}

	public void addPillType(PillType pillType, SingularPill singularPill) {
		ArrayList<Integer> uniqueRoots = new ArrayList<>();
		HashMap<Integer, int[]> rootMappedToSet = new HashMap<>();
		//ArrayList<int[]> onePillRelation = new ArrayList<>();
		int idNumber = newID();
		for (int v = 0; v < relationSet.length; v++) {
			if (singularPill.getSetToStoreRelationA()[v] > 0) {
				relationSet[v] = singularPill.getSetToStoreRelationA()[v];
				idSet[v] = idNumber;
			}
		}
		pillTypes.put(idNumber, pillType);

		for (int v = 0; v < relationSet.length; v++) {
			if (singularPill.getSetToStoreRelationA()[v] != -1) {
				if (!uniqueRoots.contains(find(singularPill.getSetToStoreRelationA(), singularPill.getSetToStoreRelationA()[v]))) {
					uniqueRoots.add(find(singularPill.getSetToStoreRelationA(), singularPill.getSetToStoreRelationA()[v]));

					int[] emptySet = new int[singularPill.getSizeOfSet()];
					Arrays.fill(emptySet, -1);
					rootMappedToSet.put(find(singularPill.getSetToStoreRelationA(), singularPill.getSetToStoreRelationA()[v]), emptySet);
					rootMappedToSet.get(find(singularPill.getSetToStoreRelationA(), singularPill.getSetToStoreRelationA()[v]))[v] = v;
				} else {
					rootMappedToSet.get(find(singularPill.getSetToStoreRelationA(), singularPill.getSetToStoreRelationA()[v]))[v] = v;
				}

			}
		}
		for (int v = 0; v < uniqueRoots.size(); v++) {
			int[] tempArray = rootMappedToSet.get(uniqueRoots.get(v));
			pillTypes.get(idNumber).addPill(new Pill(singularPill.getColor1(), singularPill.getColor2(), v + 1, uniqueRoots.get(v), -1, -1, calculatePixelAmountForArray(tempArray), tempArray));

			System.out.println("!: " + pillTypes.get(idNumber).getPills().get(uniqueRoots.get(v)).getNumber());
		}
		System.out.println("Found pills: " + uniqueRoots.size());
	}

	public Integer calculatePixelAmountForArray(int[] arr) {
		int amount = 0;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] != -1) {
				amount++;
			}
		}
		return amount;
	}

	public PillType getPillType(int id) {
		return pillTypes.get(id);

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
