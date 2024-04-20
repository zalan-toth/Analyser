package net.pyel.models;

public class Pill {

	private String name = "";
	private int milligram = 0;
	private int color1 = 0;
	private int color2 = 0;
	private int temporaryNumber = 0;
	private int number = 0;  //TODO sequental numbering from top
	private int relationRoot = -1;
	private int color1Root = -1;
	private int color2Root = -1;
	private int pixelUnits = 0;

	public Pill(String name, int milligram, int color1, int color2, int number, int relationRoot, int color1Root, int color2Root, int pixelUnits) {
		this.name = name;
		this.milligram = milligram;
		this.color1 = color1;
		this.color2 = color2;
		this.number = number;
		this.relationRoot = relationRoot;
		this.color1Root = color1Root;
		this.color2Root = color2Root;
		this.pixelUnits = pixelUnits;
	}

	public int getTemporaryNumber() {
		return temporaryNumber;
	}

	public void setTemporaryNumber(int temporaryNumber) {
		this.temporaryNumber = temporaryNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMilligram() {
		return milligram;
	}

	public void setMilligram(int milligram) {
		this.milligram = milligram;
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

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getRelationRoot() {
		return relationRoot;
	}

	public void setRelationRoot(int relationRoot) {
		this.relationRoot = relationRoot;
	}

	public int getColor1Root() {
		return color1Root;
	}

	public void setColor1Root(int color1Root) {
		this.color1Root = color1Root;
	}

	public int getColor2Root() {
		return color2Root;
	}

	public void setColor2Root(int color2Root) {
		this.color2Root = color2Root;
	}

	public int getPixelUnits() {
		return pixelUnits;
	}

	public void setPixelUnits(int pixelUnits) {
		this.pixelUnits = pixelUnits;
	}
}
