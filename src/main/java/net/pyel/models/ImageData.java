package net.pyel.models;

import javafx.scene.image.*;

import java.util.Random;

public class ImageData {
	Image originalImage;
	Image imageToEdit;
	Image grayScaleImage;
	Image onlyRedChannelImage;
	Image onlyGreenChannelImage;
	Image onlyBlueChannelImage;
	int[] redFrequency = new int[256];
	int[] greenFrequency = new int[256];
	int[] blueFrequency = new int[256];

	int sizeOfSet;
	int width;
	int height;
	int[] setToStoreARGB;
	int[] setToStoreRelation;
	int bogoAmount = 0;

	public ImageData(Image originalImage) {
		this.originalImage = originalImage;
		this.imageToEdit = originalImage;
		ImageData id = processImages(originalImage);
		processMe(originalImage);
		this.grayScaleImage = id.getGrayScaleImage();
		this.onlyRedChannelImage = id.getOnlyRedChannelImage();
		this.onlyGreenChannelImage = id.getOnlyGreenChannelImage();
		this.onlyBlueChannelImage = id.getOnlyBlueChannelImage();
	}

	public ImageData(Image originalImage, Image grayScaleImage, Image onlyRedChannelImage, Image onlyGreenChannelImage, Image onlyBlueChannelImage, int[] redFrequency, int[] greenFrequency, int[] blueFrequency) {
		this.originalImage = originalImage;
		this.imageToEdit = originalImage;
		this.grayScaleImage = grayScaleImage;
		this.onlyRedChannelImage = onlyRedChannelImage;
		this.onlyGreenChannelImage = onlyGreenChannelImage;
		this.onlyBlueChannelImage = onlyBlueChannelImage;
		this.redFrequency = redFrequency;
		this.greenFrequency = greenFrequency;
		this.blueFrequency = blueFrequency;
	}

	private void processMe(Image image) {
		width = (int) image.getWidth();
		height = (int) image.getHeight();
		sizeOfSet = width * height;
		setToStoreARGB = new int[sizeOfSet];
		setToStoreRelation = new int[sizeOfSet];

		PixelReader pr = image.getPixelReader();
		for (int v = 0; v < setToStoreARGB.length; v++) {
			int xCoordinate = v % width; // Correct calculation of xCoordinate
			int yCoordinate = v / width; // Correct calculation of yCoordinate
			int valueToSet = pr.getArgb(xCoordinate, yCoordinate);
			setToStoreARGB[v] = valueToSet;
			setToStoreRelation[v] = -1;
			//System.out.print(setToStoreRelation[v]);

		}

	}

	public static int getHue(int pixel) {
		if (pixel != 0xFFFFFFFF) {
			int red = (pixel >> 16) & 0xFF;
			int green = (pixel >> 8) & 0xFF;
			int blue = pixel & 0xFF;

			float r = red / 255.0f;
			float g = green / 255.0f;
			float b = blue / 255.0f;

			float max = Math.max(r, Math.max(g, b));
			float min = Math.min(r, Math.min(g, b));
			float delta = max - min;

			float hue = 0.0f;

			if (delta == 0) {
				hue = 0;
			} else if (max == r) {
				hue = ((g - b) / delta) % 6;
			} else if (max == g) {
				hue = (b - r) / delta + 2;
			} else if (max == b) {
				hue = (r - g) / delta + 4;
			}

			hue *= 60; // convert to degrees

			if (hue < 0) {
				hue += 360;
			}

			return Math.round(hue);
		}
		return -1;
	}

	public void makeImageHue() {
		int width = (int) imageToEdit.getWidth();
		int height = (int) imageToEdit.getHeight();
		PixelReader pr = imageToEdit.getPixelReader();
		WritableImage wi = new WritableImage(width, height);
		PixelWriter pw = wi.getPixelWriter();

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int pixel = pr.getArgb(x, y);
				int hue = getHue(pixel);
				int rgb = hueToRGB(hue);
				pw.setArgb(x, y, rgb);
			}
		}

		imageToEdit = new ImageView(wi).getImage();
	}

	private int hueToRGB(int hue) {
		float s = 1.0f; // saturation
		float v = 1.0f; // brightness
		float h = hue / 360.0f * 6;
		int i = (int) h;
		float f = h - i;
		float p = v * (1 - s);
		float q = v * (1 - f * s);
		float t = v * (1 - (1 - f) * s);

		float r = 0, g = 0, b = 0;
		switch (i) {
			case 0:
				r = v;
				g = t;
				b = p;
				break;
			case 1:
				r = q;
				g = v;
				b = p;
				break;
			case 2:
				r = p;
				g = v;
				b = t;
				break;
			case 3:
				r = p;
				g = q;
				b = v;
				break;
			case 4:
				r = t;
				g = p;
				b = v;
				break;
			case 5:
				r = v;
				g = p;
				b = q;
				break;
		}
		int red = Math.round(r * 255);
		int green = Math.round(g * 255);
		int blue = Math.round(b * 255);
		if (hue != -1) {
			return (0xFF << 24) | (red << 16) | (green << 8) | blue;
		} else {
			return 0xFFFFFFFF;
		}
	}

	private ImageData processImages(Image image) {
		int width = (int) image.getWidth();
		int height = (int) image.getHeight();

		redFrequency = null;
		greenFrequency = null;
		blueFrequency = null;
		redFrequency = new int[256];
		greenFrequency = new int[256];
		blueFrequency = new int[256];
		WritableImage gray = new WritableImage(width, height);
		WritableImage red = new WritableImage(width, height);
		WritableImage green = new WritableImage(width, height);
		WritableImage blue = new WritableImage(width, height);

		PixelReader pr = image.getPixelReader();
		PixelWriter pwGray = gray.getPixelWriter();
		PixelWriter pwRed = red.getPixelWriter();
		PixelWriter pwGreen = green.getPixelWriter();
		PixelWriter pwBlue = blue.getPixelWriter();
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				int v = pr.getArgb(x, y);
				int a = (v >> 24) & 0xFF;
				int r = (v >> 16) & 0xFF;
				int g = (v >> 8) & 0xFF;
				int b = v & 0xFF;
				int avg = (r + g + b) / 3;
				//System.out.println(a + " " + r + " " + g + " " + b);
				pwGray.setArgb(x, y, (a << 24 | avg << 16 | avg << 8 | avg));
				pwRed.setArgb(x, y, (a << 24 | r << 16));
				pwGreen.setArgb(x, y, (a << 24 | g << 8));
				pwBlue.setArgb(x, y, (a << 24 | b));
				redFrequency[r]++;
				greenFrequency[g]++;
				blueFrequency[b]++;
				//System.out.println(redFrequency[a]);
			}
		}

		//	return new ImageView(wr).getImage();

		return new ImageData(image, new ImageView(gray).getImage(), new ImageView(red).getImage(), new ImageView(green).getImage(), new ImageView(blue).getImage(), redFrequency, greenFrequency, blueFrequency);
	}

	public void doBogo() {
		int width = (int) imageToEdit.getWidth();
		int height = (int) imageToEdit.getHeight();

		WritableImage newImage = new WritableImage(width, height);

		PixelReader pr = imageToEdit.getPixelReader();
		PixelWriter pw = newImage.getPixelWriter();

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int v = pr.getArgb(x, y);
				pw.setArgb(x, y, v);
			}
		}
		if (bogoAmount == 4) {
			bogoAmount = 16;
		}
		long l = ((long) 100 * height) * ((long) bogoAmount * width / 500);
		for (long i = 0; i < l; i++) {

			getMeSwapping(width, height, pw, pr);
		}

		setImageToEdit(newImage);
	}

	private void getMeSwapping(int width, int height, PixelWriter pw, PixelReader pr) {
		Random random = new Random();
		int swap1x = random.nextInt(width);
		int swap1y = random.nextInt(height);
		int swap2x = random.nextInt(width);
		int swap2y = random.nextInt(height);


		int v1 = pr.getArgb(swap1x, swap1y);
		int v2 = pr.getArgb(swap2x, swap2y);
		int saveMe = v1;
		pw.setArgb(swap1x, swap1y, v2);
		pw.setArgb(swap2x, swap2y, saveMe);
	}

	public Image getImageToEdit() {
		return imageToEdit;
	}

	public void setImageToEdit(Image imageToEdit) {
		this.imageToEdit = imageToEdit;
	}

	public int[] getRedFrequency() {
		return redFrequency;
	}

	public void setRedFrequency(int[] redFrequency) {
		this.redFrequency = redFrequency;
	}

	public int[] getGreenFrequency() {
		return greenFrequency;
	}

	public void setGreenFrequency(int[] greenFrequency) {
		this.greenFrequency = greenFrequency;
	}

	public int[] getBlueFrequency() {
		return blueFrequency;
	}

	public void setBlueFrequency(int[] blueFrequency) {
		this.blueFrequency = blueFrequency;
	}

	public Image getOriginalImage() {
		return originalImage;
	}

	public void setOriginalImage(Image originalImage) {
		this.originalImage = originalImage;
	}

	public Image getGrayScaleImage() {
		return grayScaleImage;
	}

	public void setGrayScaleImage(Image grayScaleImage) {
		this.grayScaleImage = grayScaleImage;
	}

	public Image getOnlyRedChannelImage() {
		return onlyRedChannelImage;
	}

	public void setOnlyRedChannelImage(Image onlyRedChannelImage) {
		this.onlyRedChannelImage = onlyRedChannelImage;
	}

	public Image getOnlyGreenChannelImage() {
		return onlyGreenChannelImage;
	}

	public void setOnlyGreenChannelImage(Image onlyGreenChannelImage) {
		this.onlyGreenChannelImage = onlyGreenChannelImage;
	}

	public Image getOnlyBlueChannelImage() {
		return onlyBlueChannelImage;
	}

	public void setOnlyBlueChannelImage(Image onlyBlueChannelImage) {
		this.onlyBlueChannelImage = onlyBlueChannelImage;
	}

	public int getBogoAmount() {
		return bogoAmount;
	}

	public void setBogoAmount(int bogoAmount) {
		this.bogoAmount = bogoAmount;
	}
}
