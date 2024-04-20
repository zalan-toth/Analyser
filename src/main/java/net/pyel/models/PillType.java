package net.pyel.models;

import java.util.ArrayList;

public class PillType {


	int[] relation;
	private String name = "";
	private int color1 = 0;
	private int color2 = 0;
	private ArrayList<Pill> pills = new ArrayList<>();

	@Override
	public String toString() {
		return "PillType{" +
				"type='" + name + '\'' +
				", color1=" + color1 +
				", color2=" + color2 +
				", pills=" + pills +
				'}';
	}

	public void addPill(Pill pill) {
		if ((pill.getPixelUnits() > 4) && (pill.getPixelUnits() < 500000)) {
			pills.add(pill);
		}
	}


	public PillType(String name, int color1, int color2, int[] relation) {
		this.name = name;
		this.color1 = color1;
		this.color2 = color2;
		this.relation = relation;
	}

	public PillType(String name, int color1, int color2, ArrayList<Pill> pills) {
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

	public ArrayList<Pill> getPills() {
		return pills;
	}

	public void setPills(ArrayList<Pill> pills) {
		this.pills = pills;
	}
}
