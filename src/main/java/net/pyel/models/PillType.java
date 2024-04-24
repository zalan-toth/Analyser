package net.pyel.models;

import java.util.HashMap;

public class PillType {


	int[] relation;
	private String name = "";
	private int color1 = 0;
	private int color2 = 0;
	private int amount = 0;

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	private HashMap<Integer, Pill> pills = new HashMap<>();

	@Override
	public String toString() {
		return "PillType{" +
				"type='" + name + '\'' +
				", color1=" + color1 +
				", color2=" + color2 +
				", pills=" + pills +
				'}';
	}

	public void removePill(Pill pill) {
		/*if ((pill.getPixelUnits() > 0) && (pill.getPixelUnits() < 50000)) {
			pills.put(pill.getRelationRoot(), pill);
		} else {
			System.out.println("PILL ADD FAILED DUE TO SMALL OR TOO BIG PIXEL SIZE");
		}*/

		pills.remove(pill.getRelationRoot());
		//pills.put(pill.getRelationRoot(), pill);
	}

	public void addPill(Pill pill) {
		/*if ((pill.getPixelUnits() > 0) && (pill.getPixelUnits() < 50000)) {
			pills.put(pill.getRelationRoot(), pill);
		} else {
			System.out.println("PILL ADD FAILED DUE TO SMALL OR TOO BIG PIXEL SIZE");
		}*/

		pills.put(pill.getRelationRoot(), pill);
		//pills.put(pill.getRelationRoot(), pill);
	}


	public PillType(String name, int color1, int color2, int[] relation) {
		this.name = name;
		this.color1 = color1;
		this.color2 = color2;
		this.relation = relation;
	}

	public PillType(String name, int color1, int color2, HashMap<Integer, Pill> pills) {
		this.name = name;
		this.color1 = color1;
		this.color2 = color2;
		this.pills = pills;
	}

	public int[] getRelation() {
		return relation;
	}

	public void setRelation(int[] relation) {
		this.relation = relation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getColor1() {
		return color1;
	}

	public void setColor1(int color1) {
		this.color1 = color1;
	}

	public int getColor2() {
		return color2;
	}

	public void setColor2(int color2) {
		this.color2 = color2;
	}

	public HashMap<Integer, Pill> getPills() {
		return pills;
	}

	public void setPills(HashMap<Integer, Pill> pills) {
		this.pills = pills;
	}
}
