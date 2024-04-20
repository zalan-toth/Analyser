package net.pyel.models;

import javafx.scene.image.Image;

public class SingularPill {
	int color1 = 0;
	int color2 = 0;
	int width = -1;
	int height = -1;
	int sizeOfSet = -1;
	int[] setToStoreARGB;
	int[] setToStoreRelation;
	int[] setToStoreRelationF;
	int[] setToStoreRelationA;
	int[] setToStoreBW;
	Image image;

	public SingularPill() {
	}

	public SingularPill(int[] setToStoreRelation) {
		this.setToStoreRelation = setToStoreRelation;
	}

	public SingularPill(int width, int height, int sizeOfSet, int[] setToStoreARGB, int[] setToStoreRelation, int[] setToStoreRelationF, int[] setToStoreRelationA, int[] setToStoreBW, Image image) {
		this.width = width;
		this.height = height;
		this.sizeOfSet = sizeOfSet;
		this.setToStoreARGB = setToStoreARGB;
		this.setToStoreRelation = setToStoreRelation;
		this.setToStoreRelationF = setToStoreRelationF;
		this.setToStoreRelationA = setToStoreRelationA;
		this.setToStoreBW = setToStoreBW;
		this.image = image;
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

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getSizeOfSet() {
		return sizeOfSet;
	}

	public void setSizeOfSet(int sizeOfSet) {
		this.sizeOfSet = sizeOfSet;
	}

	public int[] getSetToStoreARGB() {
		return setToStoreARGB;
	}

	public void setSetToStoreARGB(int[] setToStoreARGB) {
		this.setToStoreARGB = setToStoreARGB;
	}

	public int[] getSetToStoreRelation() {
		return setToStoreRelation;
	}

	public void setSetToStoreRelation(int[] setToStoreRelation) {
		this.setToStoreRelation = setToStoreRelation;
	}

	public int[] getSetToStoreRelationF() {
		return setToStoreRelationF;
	}

	public void setSetToStoreRelationF(int[] setToStoreRelationF) {
		this.setToStoreRelationF = setToStoreRelationF;
	}

	public int[] getSetToStoreRelationA() {
		return setToStoreRelationA;
	}

	public void setSetToStoreRelationA(int[] setToStoreRelationA) {
		this.setToStoreRelationA = setToStoreRelationA;
	}

	public int[] getSetToStoreBW() {
		return setToStoreBW;
	}

	public void setSetToStoreBW(int[] setToStoreBW) {
		this.setToStoreBW = setToStoreBW;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
}
