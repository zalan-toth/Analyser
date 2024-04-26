package net.pyel.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

public class Labeler {

	private int minPixels = 100;
	private int maxPixels = 10000;

	public int getMinPixels() {
		return minPixels;
	}

	public void setMinPixels(int minPixels) {
		this.minPixels = minPixels;
	}

	public int getMaxPixels() {
		return maxPixels;
	}

	public void setMaxPixels(int maxPixels) {
		this.maxPixels = maxPixels;
	}

	public int[] getRelationSet() {
		return relationSet;
	}

	public void setRelationSet(int[] relationSet) {
		this.relationSet = relationSet;
	}

	public int[] getIdSet() {
		return idSet;
	}

	public void setIdSet(int[] idSet) {
		this.idSet = idSet;
	}

	public static int staticFind(int[] a, int id) {
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

	public static void processData(int[] data) {

		for (int v = 0; v < data.length; v++) {
			if (data[v] == -2) {


				data[v] = v;
				if (((v % 60) - 1) >= 0) { //60 is hardcoded width
					if (data[(v - 1)] >= 0) {
						data[v] = v - 1;
					}
				}
				/*if (((v % width) + 1) < width) {
					if (setToStoreRelation[(v + 1)] >= 0) {
						setToStoreRelation[v + 1] = v;
					}
				}*/


			} else {
				data[v] = -1;
			}
		}
	}

	public static void staticUnion(int[] a, int p, int q) {

		int rootP = find(a, p);
		int rootQ = find(a, q);

		if ((p != -1) && (q != -1) && (rootP != -1) && (rootQ != -1)) {
			a[rootQ] = rootP; // The root of q is made reference the root of p
		}
	}

	public static Integer calcPixelAmount(int[] arr) {
		int amount = 0;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] != -1) {
				amount++;
			}
		}
		return amount;
	}

	public static int newID(int[] idSet) {
		int highestID = -1;
		for (int v = 0; v < idSet.length; v++) {

			if (idSet[v] > highestID) {
				highestID = idSet[v];
			}

		}
		highestID++;
		return highestID;
	}

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
		// Check if the id is -1 or out of bounds, and return -1
		if (id == -1 || id >= a.length) {
			return -1;
		}

		while (a[id] != id) {
			// Additional check to prevent exeption
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
					int[] relA = singularPill.getSetToStoreRelationA();
					int currentPixel = singularPill.getSetToStoreRelationA()[v];
					int rootFound = find(relA, currentPixel);
					rootMappedToSet.put(rootFound, emptySet);
					rootMappedToSet.get(find(singularPill.getSetToStoreRelationA(), singularPill.getSetToStoreRelationA()[v]))[v] = v;
				} else {
					rootMappedToSet.get(find(singularPill.getSetToStoreRelationA(), singularPill.getSetToStoreRelationA()[v]))[v] = v;
				}

			}
		}
		Iterator<Integer> rootIterator = uniqueRoots.iterator();
		while (rootIterator.hasNext()) {
			int root = rootIterator.next();
			int[] set = rootMappedToSet.get(root);
			int pixelCount = calculatePixelAmountForArray(set);
			if (pixelCount < minPixels || pixelCount > maxPixels) {
				rootIterator.remove(); //removes from uniqueRoots
				rootMappedToSet.remove(root);
			}
		}
		/*for (int v = 0; v < uniqueRoots.size(); v++) {
			int[] tempArray = rootMappedToSet.get(uniqueRoots.get(v));
			int px = calculatePixelAmountForArray(tempArray);
			if ((px < 400) || (px > 5000)) {
				int rootFound = find(tempArray, v);
				rootMappedToSet.remove(rootFound);
				uniqueRoots.remove(v);
				//uniqueRoots.get();
				/*for (int i = 0; i < uniqueRoots.size(); i++) {
					if (uniqueRoots.get(i) == rootFound) {
						uniqueRoots.remove(i);
					}
				}
			}
		}*/
		for (int v = 0; v < uniqueRoots.size(); v++) {
			int[] tempArray = rootMappedToSet.get(uniqueRoots.get(v));
			pillTypes.get(idNumber).addPill(new Pill(singularPill.getColor1(), singularPill.getColor2(), v + 1, uniqueRoots.get(v), -1, -1, calculatePixelAmountForArray(tempArray), tempArray));

			System.out.println("!: " + pillTypes.get(idNumber).getPills().get(uniqueRoots.get(v)).getNumber());
		}
		/*for (int v = 0; v < pillTypes.get(idNumber).getPills().size(); v++) { //Pill pill : pillTypes.get(idNumber).getPills().values()

			Integer root = uniqueRoots.get(v);
			Pill pill = pillTypes.get(idNumber).getPills().get(root);
			if ((pill.getPixelUnits() < 200) || (pill.getPixelUnits() > 5000)) {
				rootMappedToSet.remove(pill.getRelationRoot());
				pillTypes.get(idNumber).removePill(pill);
				uniqueRoots.remove(v);
			}
		}*/
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
