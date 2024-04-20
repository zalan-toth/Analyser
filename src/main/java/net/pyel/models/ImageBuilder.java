package net.pyel.models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import java.util.HashMap;
import java.util.Random;

public class ImageBuilder {
	boolean debug;
	Image image;
	SingularPill sp;
	int mode;
	int[] finalRelationSet;
	int[] finalColorSet;

	public ImageBuilder(Image image) {
		int sizeOfSet = (int) image.getWidth() * (int) image.getHeight();
		finalRelationSet = new int[sizeOfSet];
		finalColorSet = new int[sizeOfSet];
	}

	public void setUp() {
		int sizeOfSet = (int) image.getWidth() * (int) image.getHeight();
		finalRelationSet = new int[sizeOfSet];
		finalColorSet = new int[sizeOfSet];

		for (int v = 0; v < sizeOfSet; v++) {
			finalRelationSet[v] = -1;
			finalColorSet[v] = 0xFF000000;

		}
	}

	public ImageBuilder() {
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public SingularPill getSp() {
		return sp;
	}

	public void setSp(SingularPill sp) {
		this.sp = sp;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public void addPill() {

		for (int v = 0; v < sp.getSizeOfSet(); v++) {

			if (sp.getSetToStoreRelationA()[v] > 0) {
				finalRelationSet[v] = sp.getSetToStoreRelationA()[v];
			}

		}
	}

	public Image clearImage() {

		for (int v = 0; v < sp.getSizeOfSet(); v++) {
			finalRelationSet[v] = -1;

		}
		WritableImage bw = new WritableImage(sp.getWidth(), sp.getHeight());
		PixelWriter pw = bw.getPixelWriter();
		for (int v = 0; v < sp.getSizeOfSet(); v++) {

			finalColorSet[v] = 0xFF000000;
			int x = v % sp.getWidth();
			int y = v / sp.getWidth();
			pw.setArgb(x, y, finalColorSet[v]);


		}
		return new ImageView(bw).getImage();
	}

	public Image buildBWImage() {
		WritableImage bw = new WritableImage(sp.getWidth(), sp.getHeight());
		PixelWriter pw = bw.getPixelWriter();
		for (int v = 0; v < sp.getSizeOfSet(); v++) {

			int x = v % sp.getWidth();
			int y = v / sp.getWidth();
			finalColorSet[v] = 0xFF000000;
			if (finalRelationSet[v] >= 0) {
				if (debug) {
					//System.out.println(x + " " + y + " Fekete");
				}
				finalColorSet[v] = 0xFFFFFFFF;
			}
			pw.setArgb(x, y, finalColorSet[v]);


		}
		return new ImageView(bw).getImage();

	}

	public Image buildColoredImage() {
		WritableImage bw = new WritableImage(sp.getWidth(), sp.getHeight());
		PixelWriter pw = bw.getPixelWriter();
		for (int v = 0; v < sp.getSizeOfSet(); v++) {

			int x = v % sp.getWidth();
			int y = v / sp.getWidth();
			//finalColorSet[v] = 0xFF000000;
			if (sp.getSetToStoreRelation()[v] >= 0) {
				finalColorSet[v] = sp.getColor1();
			}

			if (sp.getSetToStoreRelationF()[v] >= 0) {
				finalColorSet[v] = sp.getColor2();
			}
			pw.setArgb(x, y, finalColorSet[v]);


		}
		return new ImageView(bw).getImage();

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

	public Image buildRandomColoredImage() {
		WritableImage bw = new WritableImage(sp.getWidth(), sp.getHeight());
		PixelWriter pw = bw.getPixelWriter();
		HashMap<Integer, Integer> seperatePills = new HashMap<>(); //map root to color
		for (int v = 0; v < sp.getSizeOfSet(); v++) {

			int valueToHandle = sp.getSetToStoreRelationA()[v];
			int x = v % sp.getWidth();
			int y = v / sp.getWidth();
			if (valueToHandle >= 0) {


				if (seperatePills.get(find(sp.getSetToStoreRelationA(), valueToHandle)) == null) {

					Random rand = new Random();
					double r = rand.nextFloat() / 2f + 0.5;
					double g = rand.nextFloat() / 2f + 0.5;
					double b = rand.nextFloat() / 2f + 0.5;

					int red = (int) (r * 255);
					int green = (int) (g * 255);
					int blue = (int) (b * 255);

					int randomColor = (255 << 24) | (red << 16) | (green << 8) | blue;
					seperatePills.put(find(sp.getSetToStoreRelationA(), valueToHandle), randomColor);
						
				}
				finalColorSet[v] = seperatePills.get(find(sp.getSetToStoreRelationA(), valueToHandle));

			}
			pw.setArgb(x, y, finalColorSet[v]);


		}
		return new ImageView(bw).getImage();

	}
}
