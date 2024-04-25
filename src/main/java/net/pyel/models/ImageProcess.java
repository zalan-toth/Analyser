package net.pyel.models;

import javafx.scene.image.*;

import java.util.HashMap;
import java.util.Map;

public class ImageProcess {
	boolean debug = false;
	int width;
	int height;
	int sizeOfSet;
	int redThreshold = 0;
	int greenThreshold = 0;
	int blueThreshold = 0;
	int[] setToStoreARGB;
	int[] setToStoreRelation; //for first color
	int[] setToStoreRelationF;  //for second color
	int[] setToStoreRelationA; //for the whole pill (ALL pixels for the pill with the same root)
	int[] setToStoreBW;
	Image image;
	int backgroundColor = 0xFFFFFFFF;
	double colorMargin = 0;

	public int getRedThreshold() {
		return redThreshold;
	}

	public void setRedThreshold(int redThreshold) {
		this.redThreshold = redThreshold;
	}

	public int getGreenThreshold() {
		return greenThreshold;
	}

	public void setGreenThreshold(int greenThreshold) {
		this.greenThreshold = greenThreshold;
	}

	public int getBlueThreshold() {
		return blueThreshold;
	}

	public void setBlueThreshold(int blueThreshold) {
		this.blueThreshold = blueThreshold;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
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

	public int getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(int backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public double getColorMargin() {
		return colorMargin;
	}

	public void setColorMargin(double colorMargin) {
		this.colorMargin = colorMargin;
	}

	public ImageProcess(Image image) {
		this.image = image;
	}

	public void processAllPills() {

	}

	public void processMe() {
		width = (int) image.getWidth();
		height = (int) image.getHeight();
		sizeOfSet = width * height;
		setToStoreBW = new int[sizeOfSet];
		setToStoreARGB = new int[sizeOfSet];
		setToStoreRelationA = new int[sizeOfSet];
		setToStoreRelationF = new int[sizeOfSet];
		setToStoreRelation = new int[sizeOfSet]; // It seems you're not using this for counting colors.

		PixelReader pr = image.getPixelReader();
		Map<Integer, Integer> colorCount = new HashMap<>();

		for (int v = 0; v < setToStoreARGB.length; v++) {
			int xCoordinate = v % width;
			int yCoordinate = v / width;
			int argbValue = pr.getArgb(xCoordinate, yCoordinate);
			setToStoreARGB[v] = argbValue;
			setToStoreRelation[v] = -1;

		}

		for (int v = 0; v < setToStoreRelation.length; v++) { //create base

			setToStoreBW[v] = 0xFF000000;


		}

	}

	public int findClickedColor(int x, int y) {
		PixelReader pr = image.getPixelReader();
		int pixelData = pr.getArgb(x, y);

		int a = (pixelData >> 24) & 0xFF;
		int r = (pixelData >> 16) & 0xFF;
		int g = (pixelData >> 8) & 0xFF;
		int b = pixelData & 0xFF;
		if (debug) {

			System.out.println(a + " " + r + " " + g + " " + b);
		}
		if (pixelData == backgroundColor) {
			return 0;
		}
		return pixelData;
	}


	public boolean colorsCheck(int colorValue, int colorInput1, int colorInput2) {
		return (colorValue == colorInput1 || colorValue == colorInput2);
	}

	public boolean simpleColorCheck(int colorValue, int colorInput) {
		return (colorValue == colorInput);
	}


	public boolean thresholdColorCheck(int colorValue, int colorInput) {

		int colorInputA = (colorInput >> 24) & 0xFF; //color we are looking for
		int colorInputR = (colorInput >> 16) & 0xFF; //TODO manipulate this
		int colorInputG = (colorInput >> 8) & 0xFF;
		int colorInputB = colorInput & 0xFF;

		int colorValueA = (colorValue >> 24) & 0xFF; //colorvalue at the specified pixel
		int colorValueR = (colorValue >> 16) & 0xFF;
		int colorValueG = (colorValue >> 8) & 0xFF;
		int colorValueB = colorValue & 0xFF;

		int maxValueForColorInputR = colorInputR + redThreshold;
		int minValueForColorInputR = colorInputR - redThreshold;
		int maxValueForColorInputG = colorInputG + greenThreshold;
		int minValueForColorInputG = colorInputG - greenThreshold;
		int maxValueForColorInputB = colorInputB + blueThreshold;
		int minValueForColorInputB = colorInputB - blueThreshold;


		if ((colorValueR <= maxValueForColorInputR) && (colorValueR >= minValueForColorInputR)) {

			if ((colorValueG <= maxValueForColorInputG) && (colorValueG >= minValueForColorInputG)) {

				if ((colorValueB <= maxValueForColorInputB) && (colorValueB >= minValueForColorInputB)) {
					return true;
				}
			}
		}

		return false;
	}

	public void createRelationSet(int colorInput1, int colorInput2, int mode) {

		if (mode == 0) {

			for (int v = 0; v < setToStoreRelation.length; v++) {

				int colorValue = setToStoreARGB[v];
				if (thresholdColorCheck(colorValue, colorInput1)) { //check which colors to process
					setToStoreRelation[v] = -2; //-2 means we have to process it!
					setToStoreRelationA[v] = -2; //-2 means we have to process it!
				} else if (thresholdColorCheck(colorValue, colorInput2)) {
					setToStoreRelationF[v] = -2; //-2 means we have to process it!
					setToStoreRelationA[v] = -2; //-2 means we have to process it!
				} else {
					setToStoreRelationA[v] = -1; //we do not process -1
					setToStoreRelationF[v] = -1; //we do not process -1
					setToStoreRelation[v] = -1; //we do not process -1
				}
				if (debug) {
					//System.out.print(setToStoreRelation[v] + " ");
				}
			}
		} else if (mode == 1) {
			/*for (int v = 0; v < setToStoreRelation.length; v++) {

				int colorValue = setToStoreARGB[v];
				if (backgroundColor != colorValue) { //check which colors to process
					setToStoreRelation[v] = -2; //-2 means we have to process it!
				} else {
					setToStoreRelation[v] = -1; //we do not process -1
				}
				if (debug) {
					//System.out.print(setToStoreRelation[v] + " ");
				}
			}*/
		}

		if (debug) {
			System.out.println("H");
		}
		for (int v = 0; v < setToStoreRelation.length; v++) {

			if (setToStoreRelation[v] == -2) {


				setToStoreRelation[v] = v;
				if (((v % width) - 1) >= 0) {
					if (setToStoreRelation[(v - 1)] >= 0) {
						setToStoreRelation[v] = v - 1;
					}
				}
				/*if (((v % width) + 1) < width) {
					if (setToStoreRelation[(v + 1)] >= 0) {
						setToStoreRelation[v + 1] = v;
					}
				}*/


			} else {
				setToStoreRelation[v] = -1;
			}

			if (setToStoreRelationF[v] == -2) {


				setToStoreRelationF[v] = v;
				if (((v % width) - 1) >= 0) {
					if (setToStoreRelationF[(v - 1)] >= 0) {
						setToStoreRelationF[v] = v - 1;
					}
				}
				/*if (((v % width) + 1) < width) {
					if (setToStoreRelation[(v + 1)] >= 0) {
						setToStoreRelation[v + 1] = v;
					}
				}*/


			} else {
				setToStoreRelationF[v] = -1;
			}

			if (setToStoreRelationA[v] == -2) {


				setToStoreRelationA[v] = v;
				if (((v % width) - 1) >= 0) {
					if (setToStoreRelationA[(v - 1)] >= 0) {
						setToStoreRelationA[v] = v - 1;
					}
				}
				/*if (((v % width) + 1) < width) {
					if (setToStoreRelation[(v + 1)] >= 0) {
						setToStoreRelation[v + 1] = v;
					}
				}*/


			} else {
				setToStoreRelationA[v] = -1;
			}


		}
		for (int v = 0; v < setToStoreRelation.length; v++) {

			if (setToStoreRelation[v] >= 0) {

				if ((v - width) >= 0) {
					if (setToStoreRelation[(v - width)] >= 0) {
						int value = find(setToStoreRelation, v);
						int value2 = find(setToStoreRelation, v - width);
						setToStoreRelation[value] = value2; //This is technically a UNION
					}
				}

				/*if ((v + width) < setToStoreRelation.length) {
					if (setToStoreRelation[(v + width)] >= 0) {
						setToStoreRelation[v + width] = v;
					}
				}*/


			}
			if (setToStoreRelationF[v] >= 0) {

				if ((v - width) >= 0) {
					if (setToStoreRelationF[(v - width)] >= 0) {
						int value = find(setToStoreRelationF, v);
						int value2 = find(setToStoreRelationF, v - width);
						setToStoreRelationF[value] = value2; //This is technically a UNION
					}
				}

				/*if ((v + width) < setToStoreRelation.length) {
					if (setToStoreRelation[(v + width)] >= 0) {
						setToStoreRelation[v + width] = v;
					}
				}*/


			}
			if (setToStoreRelationA[v] >= 0) {

				if ((v - width) >= 0) {
					if (setToStoreRelationA[(v - width)] >= 0) {
						int value = find(setToStoreRelationA, v);
						int value2 = find(setToStoreRelationA, v - width);
						setToStoreRelationA[value] = value2; //This is technically a UNION
					}
				}


			}
		}

		for (int id = 0; id < setToStoreRelation.length; id++) {
			if (debug) {
				if (setToStoreRelation[id] != -1) {
					System.out.print(setToStoreRelation[find(setToStoreRelation, id)] + " ");
					System.out.print(setToStoreRelationF[find(setToStoreRelationF, id)] + " ");
					System.out.print(setToStoreRelationA[find(setToStoreRelationA, id)] + " ");
				}
				//System.out.println("The root of " + id + " is " + find(setToStoreRelation, id));
			}
		}
	}

	/*public void createBWSet() {

		for (int v = 0; v < setToStoreRelation.length; v++) {

			setToStoreBW[v] = 0xFF000000;


		}

		for (int v = 0; v < setToStoreRelation.length; v++) {

			if (setToStoreRelation[v] >= 0) {
				setToStoreBW[v] = 0xFFFFFFFF;
			}
			if (setToStoreRelationF[v] >= 0) {
				setToStoreBW[v] = 0xFF888888;
			}


		}
	}*/
	public SingularPill getSingularPillData() {
		return new SingularPill(width, height, sizeOfSet, setToStoreARGB, setToStoreRelation, setToStoreRelationF, setToStoreRelationA, setToStoreBW, image);
	}

	public Image createSetForBW() {
		WritableImage bw = new WritableImage(width, height);
		PixelWriter pw = bw.getPixelWriter();
		for (int v = 0; v < setToStoreRelation.length; v++) {

			int x = v % width;
			int y = v / width;
			setToStoreBW[v] = 0xFF000000;
			pw.setArgb(x, y, setToStoreBW[v]);


		}
		for (int v = 0; v < setToStoreRelation.length; v++) {

			int x = v % width;
			int y = v / width;
			if (setToStoreRelation[v] >= 0) {
				if (debug) {
					//System.out.println(x + " " + y + " Fekete");
				}
				setToStoreBW[v] = 0xFFFFFFFF;
			}
			if (setToStoreRelationF[v] >= 0) {
				if (debug) {
					//System.out.println(x + " " + y + " Fekete");
				}
				setToStoreBW[v] = 0xFF888888;
			}
			pw.setArgb(x, y, setToStoreBW[v]);


		}
		return new ImageView(bw).getImage();
	}

	public static void union(int[] a, int p, int q) {
		a[find(a, q)] = find(a, p); //The root of q is made reference the root of p
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


	public static int find2(int[] a, int id) {
		if (a[id] == id) return id;
		else return find2(a, a[id]);
	}

	public static int find3(int[] a, int id) {
		return a[id] == id ? id : find3(a, a[id]);
	}

	public void markContiguousRegions() {
		int regionId = 0; // Start with an arbitrary region ID.

		for (int v = 0; v < setToStoreRelation.length; v++) {
			if (setToStoreRelation[v] == -2) { // Found a pixel marked for processing.
				floodFill(v % width, v / width, regionId++); // Perform flood fill starting from this pixel.
			}
		}
	}

	private void floodFill(int x, int y, int regionId) {
		int index = y * width + x;
		if (x < 0 || x >= width || y < 0 || y >= height) return; // Check bounds
		if (setToStoreRelation[index] != -2) return; // Check if already processed or not marked for processing

		setToStoreRelation[index] = regionId; // Mark this pixel with its region ID.

		// Recursive fill for four directions
		floodFill(x + 1, y, regionId);
		floodFill(x - 1, y, regionId);
		floodFill(x, y + 1, regionId);
		floodFill(x, y - 1, regionId);
	}

}
