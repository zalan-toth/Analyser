package net.pyel.models;

public class RandomColorMatch {
	int randomColor = 0;
	int root = -1;

	public RandomColorMatch(int randomColor, int root) {
		this.randomColor = randomColor;
		this.root = root;
	}

	public int getRandomColor() {
		return randomColor;
	}

	public void setRandomColor(int randomColor) {
		this.randomColor = randomColor;
	}

	public int getRoot() {
		return root;
	}

	public void setRoot(int root) {
		this.root = root;
	}
}
