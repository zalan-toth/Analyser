package net.pyel;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import net.pyel.models.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Base Controller - Manages all windows with fxml
 *
 * @author Zalán Tóth
 */
public class BaseController implements Initializable {


	//██████╗░███████╗░█████╗░██╗░░░░░░█████╗░██████╗░███████╗
	//██╔══██╗██╔════╝██╔══██╗██║░░░░░██╔══██╗██╔══██╗██╔════╝
	//██║░░██║█████╗░░██║░░╚═╝██║░░░░░███████║██████╔╝█████╗░░
	//██║░░██║██╔══╝░░██║░░██╗██║░░░░░██╔══██║██╔══██╗██╔══╝░░
	//██████╔╝███████╗╚█████╔╝███████╗██║░░██║██║░░██║███████╗
	//╚═════╝░╚══════╝░╚════╝░╚══════╝╚═╝░░╚═╝╚═╝░░╚═╝╚══════╝
	//private static PanelAPI panelAPI = new PanelAPI(null);
	//private CustomList<Machine> machines;
	//private CustomList<Game> games = new CustomList<>();
	Labeler labeler;
	private List<Node> activeRectangles = new ArrayList<>();
	ArrayList<PillType> selectedPillTypes;
	Stage popupstage = new Stage();
	Parent popuproot;
	Scene popupScene;
	Stage terminalStage = new Stage();
	Parent terminalRoot;
	Scene terminalScene;
	boolean setRun = true;
	ImageData id;
	ImageProcess ip;
	ImageBuilder ib = new ImageBuilder();
	int color1 = 0;
	int color2 = 0;
	int location;
	int backgroundColor = 0xFFFFFFFF;

	//███████╗██╗░░██╗███╗░░░███╗██╗░░░░░░░░░░░░░██████╗░███████╗░█████╗░██╗░░░░░░█████╗░██████╗░███████╗
	//██╔════╝╚██╗██╔╝████╗░████║██║░░░░░░░░░░░░░██╔══██╗██╔════╝██╔══██╗██║░░░░░██╔══██╗██╔══██╗██╔════╝
	//█████╗░░░╚███╔╝░██╔████╔██║██║░░░░░░░░░░░░░██║░░██║█████╗░░██║░░╚═╝██║░░░░░███████║██████╔╝█████╗░░
	//██╔══╝░░░██╔██╗░██║╚██╔╝██║██║░░░░░░░░░░░░░██║░░██║██╔══╝░░██║░░██╗██║░░░░░██╔══██║██╔══██╗██╔══╝░░
	//██║░░░░░██╔╝╚██╗██║░╚═╝░██║███████╗░░░░░░░░██████╔╝███████╗╚█████╔╝███████╗██║░░██║██║░░██║███████╗
	//╚═╝░░░░░╚═╝░░╚═╝╚═╝░░░░░╚═╝╚══════╝░░░░░░░░╚═════╝░╚══════╝░╚════╝░╚══════╝╚═╝░░╚═╝╚═╝░░╚═╝╚══════╝

	@FXML
	private ListView<String> viewFacility = new ListView<>();


	@FXML
	public TextField typeNameBox = new TextField();
	@FXML
	public Text pillsSelected = new Text();

	@FXML
	public TextField minPixelsBox = new TextField();
	@FXML
	public TextField maxPixelsBox = new TextField();
	@FXML
	public Text pixelText = new Text();
	public CategoryAxis redChartCategoryAxis = new CategoryAxis();
	public NumberAxis redChartNumberAxis = new NumberAxis();
	public BarChart<String, Number> redChart = new BarChart<>(redChartCategoryAxis, redChartNumberAxis);
	public ImageView imageView = new ImageView();
	@FXML
	public Text color1text = new Text();
	@FXML
	public Text color2text = new Text();
	@FXML
	public CheckBox multiSelectCheckBox = new CheckBox();
	@FXML
	public CheckBox mode1box = new CheckBox();
	@FXML
	public CheckBox mode2box = new CheckBox();
	@FXML
	public CheckBox mode3box = new CheckBox();
	@FXML
	public CheckBox dualToneCheckBox = new CheckBox();
	@FXML
	public ImageView bwImageView = new ImageView();
	public ImageView originalImageView = new ImageView();
	public ImageView grayImageView = new ImageView();
	public ImageView redImageView = new ImageView();
	public ImageView greenImageView = new ImageView();
	public ImageView blueImageView = new ImageView();
	public Image image;
	public Image grayImage;
	public Image redImage;
	public Image greenImage;
	public Image blueImage;
	public File selectedFile;
	@FXML
	public Slider brightnessSlider = new Slider();
	@FXML
	public Slider hueSlider = new Slider();
	@FXML
	public Slider saturationSlider = new Slider();
	@FXML
	public Slider bogoSlider = new Slider();
	@FXML
	public Slider redValueSlider = new Slider();
	@FXML
	public Slider greenValueSlider = new Slider();
	@FXML
	public Slider blueValueSlider = new Slider();
	@FXML
	public Text redValueText = new Text();
	@FXML
	public Text greenValueText = new Text();
	@FXML
	public Text blueValueText = new Text();
	@FXML
	public Text bogoText = new Text();
	@FXML
	public Text saturationText = new Text();
	@FXML
	public Text hueText = new Text();
	@FXML
	public Text brightnessText = new Text();

	Translate bogoTranslate = new Translate();

	public BaseController() {
		//panelAPI = BackgroundController.getPanelAPI();
		//machines = panelAPI.panel.getMachines();
		//games = panelAPI.panel.getGames();
	}


	/**
	 * Call this method if you wanna close the application
	 */
	@FXML
	private void quit() {
		javafx.application.Platform.exit();
	}

	@FXML
	private void openImage() {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"JPG, PNG & GIF Images", "jpg", "gif", "png");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			selectedFile = chooser.getSelectedFile();
			try {
				image = convertToFxImage(ImageIO.read(selectedFile));
			} catch (IOException e) {
			}
			image = new Image(selectedFile.toURI().toString(), 512, 512, false, false); //downscale
			id = new ImageData(image);
			ip = new ImageProcess(image);
			ip.processMe();
			ib.setImage(ip.getImage());
			ib.setUp();


			labeler = new Labeler(ip.getSizeOfSet());

			originalImageView.setImage(id.getOriginalImage());
			imageView.setImage(id.getImageToEdit());
			grayImage = id.getGrayScaleImage();
			grayImageView.setImage(grayImage);
			redImageView.setImage(id.getOnlyRedChannelImage());
			greenImageView.setImage(id.getOnlyGreenChannelImage());
			blueImageView.setImage(id.getOnlyBlueChannelImage());
			//redChart = createBarChart(id.getRedFrequency(), "Red");

			selectedPillTypes = new ArrayList<>();
			setBar();
		}
	}

	/*public void markPills(int root) {
		int width = 512;
		int count = 0;

		// First, count the number of pixels in the set
		for (int i = 0; i < sets.length; i++) {
			if (find(sets, i) == root) {
				count++;
			}
		}

		// Only proceed if the set contains more than 5 pixels
		if (count > 5) {
			int minX = Integer.MAX_VALUE;
			int maxX = Integer.MIN_VALUE;
			int minY = Integer.MAX_VALUE;
			int maxY = Integer.MIN_VALUE;

			for (int i = 0; i < sets.length; i++) {
				if (find(sets, i) == root) {
					int row = i / width;
					int col = i % width;

					if (row < minY) minY = row;
					if (row > maxY) maxY = row;
					if (col < minX) minX = col;
					if (col > maxX) maxX = col;
				}
			}

			Rectangle r = new Rectangle(minX, minY, maxX - minX, maxY - minY);
			// Create and position the label
			Label l = new Label(String.valueOf(this.count));
			l.setLayoutX(minX - 14); // Position the label at the top-left corner of the rectangle
			l.setLayoutY(minY - 14);
			Pane parentPane = (Pane) imagePane.getParent();

			// Increment the count for the next label
			this.count++;
			r.setFill(Color.TRANSPARENT); // Set the fill to transparent
			r.setStroke(Color.GREEN); // Set the stroke color to green
			r.setStrokeWidth(2);

			parentPane.getChildren().addAll(r, l); // Add both the rectangle and the label to the pane
		}
	}*/

	private void setBar() {

		redChart.getData().clear();
		XYChart.Series<String, Number> redSeries = new XYChart.Series<>();
		redSeries.setName("Red");
		for (int i = 0; i < id.getRedFrequency().length; i++) {
			redSeries.getData().add(new XYChart.Data<>(String.valueOf(i), id.getRedFrequency()[i]));
		}

		redChart.getData().add(redSeries);

		XYChart.Series<String, Number> greenSeries = new XYChart.Series<>();
		greenSeries.setName("Green");
		for (int i = 0; i < id.getGreenFrequency().length; i++) {
			greenSeries.getData().add(new XYChart.Data<>(String.valueOf(i), id.getGreenFrequency()[i]));
		}

		redChart.getData().add(greenSeries);

		XYChart.Series<String, Number> blueSeries = new XYChart.Series<>();
		blueSeries.setName("Blue");
		for (int i = 0; i < id.getBlueFrequency().length; i++) {
			blueSeries.getData().add(new XYChart.Data<>(String.valueOf(i), id.getBlueFrequency()[i]));
		}

		redChart.getData().add(blueSeries);
	}
	/*private BarChart<String, Number> createBarChart(int[] frequency, String color) {
		BarChart<String, Number> barChart = new BarChart<>(redChartCategoryAxis, redChartNumberAxis);
		barChart.setTitle(color + " Channel");

		XYChart.Series<String, Number> series = new XYChart.Series<>();
		series.setName(color);

		for (int i = 0; i < frequency.length; i++) {
			series.getData().add(new XYChart.Data<>(String.valueOf(i), frequency[i]));
			//System.out.println(String.valueOf(i) + " " + frequency[i]);
		}

		barChart.getData().add(series);

		return barChart;
	}*/

	@FXML
	private void openImageDiff() {
		FileDialog dialog = new FileDialog((Frame) null, "Select File to Open");
		dialog.setMode(FileDialog.LOAD);
		dialog.setVisible(true);
		String file = dialog.getFile();
		dialog.dispose();
		System.out.println(file + " chosen.");
	}

	private static Image convertToFxImage(BufferedImage image) {
		WritableImage wr = null;
		if (image != null) {
			wr = new WritableImage(image.getWidth(), image.getHeight());
			PixelWriter pw = wr.getPixelWriter();
			for (int x = 0; x < image.getWidth(); x++) {
				for (int y = 0; y < image.getHeight(); y++) {
					pw.setArgb(x, y, image.getRGB(x, y));
				}
			}
		}

		return new ImageView(wr).getImage();
	}


	//░██████╗██╗░░░██╗███╗░░██╗░█████╗░
	//██╔════╝╚██╗░██╔╝████╗░██║██╔══██╗
	//╚█████╗░░╚████╔╝░██╔██╗██║██║░░╚═╝
	//░╚═══██╗░░╚██╔╝░░██║╚████║██║░░██╗
	//██████╔╝░░░██║░░░██║░╚███║╚█████╔╝
	//╚═════╝░░░░╚═╝░░░╚═╝░░╚══╝░╚════╝░
	@FXML
	private void refresh() {
		initialize(null, null);
	}

	public void letsdobogo() {

		id.doBogo();

		ip.setImage(id.getImageToEdit());
		ip.processMe();
		imageView.setImage(id.getImageToEdit());
	}

	public void makeHue() {

		id.makeImageHue();

		ip.setImage(id.getImageToEdit());
		ip.processMe();
		imageView.setImage(id.getImageToEdit());
	}


	public void resetImage() {

		id.setImageToEdit(id.getOriginalImage());

		ip.setImage(id.getImageToEdit());
		ip.processMe();
		imageView.setImage(id.getImageToEdit());
	}

	@FXML
	public void selectAllPills() {

		ip.createRelationSet(0, 0, 1);
		bwImageView.setImage(ip.createSetForBW());

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		if (setRun) {
			setupPortListViewListener();
			setRun = false;
		}
	}


	public int findColor(double x, double y) {

		System.out.println("Clicked coordinates: x = " + x + ", y = " + y);

		double viewWidth = imageView.getFitWidth();
		double viewHeight = imageView.getFitHeight();

		// Actual dimensions of the image
		double actualWidth = imageView.getImage().getWidth();
		double actualHeight = imageView.getImage().getHeight();

		// Compute the scale
		double scaleX = actualWidth / viewWidth;
		double scaleY = actualHeight / viewHeight;

		// Adjusting for the actual display size within the ImageView
		double displayWidth = imageView.getBoundsInLocal().getWidth();
		double displayHeight = imageView.getBoundsInLocal().getHeight();

		// Calculate the ratio of the original image to the displayed image
		double ratioX = actualWidth / displayWidth;
		double ratioY = actualHeight / displayHeight;

		// Calculate the original coordinates of the click
		double originalX = x * ratioX;
		double originalY = y * ratioY;

		System.out.println("Original coordinates: x = " + (int) originalX + ", y = " + (int) originalY);
		location = ((int) originalY * (int) imageView.getImage().getWidth()) + (int) originalX;
		System.out.println("Original setcoord: = " + (int) location);
		return ip.findClickedColor((int) originalX, (int) originalY);
	}

	@FXML
	public void selectMode1() {
		if (mode1box.isSelected()) {

			mode2box.setSelected(false);
			mode3box.setSelected(false);
		}
	}

	@FXML
	public void selectMode2() {
		if (mode2box.isSelected()) {

			mode1box.setSelected(false);
			mode3box.setSelected(false);
		}
	}

	@FXML
	public void selectMode3() {
		if (mode3box.isSelected()) {

			mode1box.setSelected(false);
			mode2box.setSelected(false);
		}
	}

	@FXML
	public int findSetFromColor(int color1, int color2) {
		return -1;
	}

	@FXML
	public void addLabel() {
		int identification = labeler.getID(location);
		if (identification == -1) {
			labeler.addPillType(new PillType(typeNameBox.getText(), color1, color2, ip.getSetToStoreRelationA()), ip.getSingularPillData());
			typeNameBox.clear();
			System.out.println(labeler.getPillType(labeler.getID(location)));

			//PROCESS EACH PILL


		} else {
			System.out.println("PillType already exist!");
			System.out.println(labeler.getPillType(labeler.getID(location)));
		}
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

	private Rectangle getRectangleForPill(Pill pill) {
		int width = (int) imageView.getImage().getWidth();
		int height = (int) imageView.getImage().getHeight();

		double fitWidth = imageView.getFitWidth(); //image.getWidth() / 1.83; //TODO 700px vs 1280px original
		double fitHeight = imageView.getFitHeight(); //TODO 484px vs 720px original

		// Determine scale factors
		double scaleX = fitWidth / width;
		double scaleY = fitHeight / height;

		int minX = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		int minY = Integer.MAX_VALUE;
		int maxY = Integer.MIN_VALUE;

		for (int i = 0; i < pill.getRelation().length; i++) {
			if (pill.getRelation()[i] != -1) {
				int row = i / width;
				int col = i % width;

				if (row < minY) minY = row;
				if (row > maxY) maxY = row;
				if (col < minX) minX = col;
				if (col > maxX) maxX = col;
			}
		}

		// Adjusting for ImageView scaling
		double rectX = minX * scaleX;
		double rectY = minY * scaleY;
		double rectWidth = (maxX - minX + 1) * scaleX;
		double rectHeight = (maxY - minY + 1) * scaleY;

		Rectangle r = new Rectangle(rectX, rectY, rectWidth, rectHeight);
		r.setFill(Color.TRANSPARENT);
		r.setStroke(Color.GREEN);
		r.setStrokeWidth(2);
		return r;
	}

	private void clearRectangles() {
		Pane parentPane = (Pane) imageView.getParent();
		for (Node node : activeRectangles) {
			parentPane.getChildren().remove(node);
		}
		activeRectangles.clear();  // Clear the list after removing the nodes
	}

	private void showRectanglesOnPills() {
		Pane parentPane = (Pane) imageView.getParent();


		int[] allRel = new int[(int) image.getWidth() * (int) image.getHeight()];
		for (int v = 0; v < allRel.length; v++) {
			allRel[v] = -1;

		}
		ArrayList<Integer> uniqueRoots = new ArrayList<>();
		for (PillType pillType : selectedPillTypes) {
			int[] tempRel = pillType.getRelation();
			for (int v = 0; v < tempRel.length; v++) {
				if (tempRel[v] != -1) {
					allRel[v] = tempRel[v];
				}
			}
		}

		for (int v = 0; v < allRel.length; v++) {
			int root = find(allRel, v);
			if (!uniqueRoots.contains(root)) {
				for (PillType pillType : selectedPillTypes) {
					if (pillType.getPills().containsKey(root)) {
						uniqueRoots.add(root);
					}
				}

			}

		}
		Collections.sort(uniqueRoots);

		for (PillType pillType : selectedPillTypes) {
			int count = 0;
			for (Pill pill : pillType.getPills().values()) {
				for (int v = 0; v < uniqueRoots.size(); v++) {
					if (uniqueRoots.get(v) == pill.getRelationRoot()) {
						pill.setTemporaryNumber(v);
					}
				}
				count++;
			}
			pillType.setAmount(count);
		}

		int pillCount = 0;
		for (PillType pillType : selectedPillTypes) {
			for (Pill pill : pillType.getPills().values()) {
				pillCount++;
				Rectangle r = getRectangleForPill(pill);
				Label l = new Label(String.valueOf(pill.getTemporaryNumber() + 1));
				l.setLayoutX(r.getX());
				l.setLayoutY(r.getY());
				Tooltip tooltip = new Tooltip("Name: " + pillType.getName() + "\n" + pillType.getPills().size() + " pills have this type." + "\nOrder in pilltype: " + pill.getNumber() + "\nOrder among all selected pills: " + (pill.getTemporaryNumber() + 1) + "\nPixels: " + pill.getPixelUnits());
				Tooltip.install(r, tooltip);

				r.setOnMouseClicked(event -> {
					event.consume();
					removePillTypeSelection(pillType);
					parentPane.getChildren().removeAll(r, l);
					updateUIAfterPillTypeRemoval();
				});

				parentPane.getChildren().addAll(r, l);
				activeRectangles.add(r);  // Keep track of the rectangle
				activeRectangles.add(l);  // Keep track of the label
			}
		}

		pillsSelected.setText(pillCount + " pills selected");
	}

	private void removePillTypeSelection(PillType pillType) {
		selectedPillTypes.remove(pillType);
	}


	@FXML
	private void adjustPixelLimits() {
		System.out.println("Adjusted pixel limits/threshold");

		labeler.setMaxPixels(Integer.parseInt(maxPixelsBox.getText()));
		labeler.setMinPixels(Integer.parseInt(minPixelsBox.getText()));
		pixelText.setText("PIXEL THRESHOLD (" + labeler.getMinPixels() + "-" + labeler.getMaxPixels() + ")");
	}

	private void updateUIAfterPillTypeRemoval() {
		clearRectangles();
		showRectanglesOnPills();
	}

	private void setupPortListViewListener() {
		imageView.setOnMouseClicked(event -> {
			double x = event.getX();
			double y = event.getY();
			if (dualToneCheckBox.isSelected()) {

				if (color1 == 0) {
					color1 = findColor(x, y);
					color1text.setText("Color 1 selected");
					color2text.setText("-");
					System.out.println("c1");
				} else if (color2 == 0) {
					color2 = findColor(x, y);
					color2text.setText("Color 2 selected");
					System.out.println("c2");
					ip.createRelationSet(color1, color2, 0);
					SingularPill sp = ip.getSingularPillData();
					sp.setColor1(color1);
					sp.setColor2(color2);
					ib.setSp(sp);
					if (!multiSelectCheckBox.isSelected()) {
						ib.clearImage();
					}
					ib.addPill();
					if (mode1box.isSelected()) {
						bwImageView.setImage(ib.buildBWImage());
					} else if (mode2box.isSelected()) {
						bwImageView.setImage(ib.buildColoredImage());
					} else if (mode3box.isSelected()) {
						bwImageView.setImage(ib.buildRandomColoredImage());
					}
					color1 = 0;
					color2 = 0;
				} /*else {
					color1 = 0;
					color2 = 0;
					color1text.setText("-");
					color2text.setText("-");
				}*/

			} else {

				color1text.setText("-");
				color2text.setText("-");
				int foundColor = findColor(x, y);
				color1 = foundColor;
				color2 = 0;
				ip.createRelationSet(foundColor, 0, 0);
				SingularPill sp = ip.getSingularPillData();
				sp.setColor1(foundColor);
				ib.setSp(sp);
				if (!multiSelectCheckBox.isSelected()) {
					ib.clearImage();
				}
				ib.addPill();
				if (mode1box.isSelected()) {
					bwImageView.setImage(ib.buildBWImage());
				} else if (mode2box.isSelected()) {
					bwImageView.setImage(ib.buildColoredImage());
				} else if (mode3box.isSelected()) {
					bwImageView.setImage(ib.buildRandomColoredImage());
				}


				//labeler.addPillType(new PillType("Test", foundColor, 0, ip.getSetToStoreRelationA()));
			}

			int identification = labeler.getID(location);
			if (identification != -1) {
				PillType pt = labeler.getPillType(labeler.getID(location));
				Integer rootOfClick = find(pt.getRelation(), location);
				Pill p = pt.getPills().get(rootOfClick);
				System.out.println(p);

				if (selectedPillTypes.contains(pt)) {
					selectedPillTypes.remove(pt);
				} else {
					selectedPillTypes.add(pt);
				}

				//TODO CLEAR THE RECTANGLES

				clearRectangles();
				//TODO SHOW RECTANGLES ON PILLS OVER THE IMAGE
				showRectanglesOnPills();
				/*int count = 0;
				for (PillType pillType : selectedPillTypes) {
					for (Pill pill : pillType.getPills().values()) {
						int minX = Integer.MAX_VALUE;
						int maxX = Integer.MIN_VALUE;
						int minY = Integer.MAX_VALUE;
						int maxY = Integer.MIN_VALUE;

						for (int i = 0; i < pill.getRelation().length; i++) {
							if (find(pill.getRelation(), i) == pill.getRelationRoot()) {
								int row = i / (int) image.getWidth();
								int col = i % (int) image.getWidth();

								if (row < minY) minY = row;
								if (row > maxY) maxY = row;
								if (col < minX) minX = col;
								if (col > maxX) maxX = col;
							}
						}

						javafx.scene.shape.Rectangle r = new Rectangle(minX, minY, maxX - minX, maxY - minY);
						// Create and position the label
						javafx.scene.control.Label l = new Label(String.valueOf(count));
						l.setLayoutX(minX - 14); // Position the label at the top-left corner of the rectangle
						l.setLayoutY(minY - 14);
						Pane parentPane = (Pane) imageView.getParent();

						// Increment the count for the next label
						count++;
						r.setFill(javafx.scene.paint.Color.TRANSPARENT); // Set the fill to transparent
						r.setStroke(Color.RED); // Set the stroke color to green
						r.setStrokeWidth(2);
					}

				}
				count = 0;*/

			}

		});
		viewFacility.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			// This will be called whenever the user selects a different item in the list
			if (newValue != null) {
				refresh();
			}
		});

		redValueSlider.valueProperty().addListener(new ChangeListener<>() {

			@Override
			public void changed(
					ObservableValue<? extends Number> observableValue,
					Number oldValue,
					Number newValue) {
				redValueText.setText(String.valueOf((int) (newValue.intValue() * 1.28)));
				ip.setRedThreshold((int) (newValue.intValue() * 1.28));
				//id.setBogoAmount(newValue.intValue() / 2);

				refresh();
				//betLabel.textProperty().setValue(
				//String.valueOf(newValue.intValue());

			}
		});

		greenValueSlider.valueProperty().addListener(new ChangeListener<>() {

			@Override
			public void changed(
					ObservableValue<? extends Number> observableValue,
					Number oldValue,
					Number newValue) {
				greenValueText.setText(String.valueOf((int) (newValue.intValue() * 1.28)));
				ip.setGreenThreshold((int) (newValue.intValue() * 1.28));
				//id.setBogoAmount(newValue.intValue() / 2);

				refresh();
				//betLabel.textProperty().setValue(
				//String.valueOf(newValue.intValue());

			}
		});

		blueValueSlider.valueProperty().addListener(new ChangeListener<>() {

			@Override
			public void changed(
					ObservableValue<? extends Number> observableValue,
					Number oldValue,
					Number newValue) {
				blueValueText.setText(String.valueOf((int) (newValue.intValue() * 1.28)));
				ip.setBlueThreshold((int) (newValue.intValue() * 1.28));
				//id.setBogoAmount(newValue.intValue() / 2);

				refresh();
				//betLabel.textProperty().setValue(
				//String.valueOf(newValue.intValue());

			}
		});
		bogoSlider.valueProperty().addListener(new ChangeListener<>() {

			@Override
			public void changed(
					ObservableValue<? extends Number> observableValue,
					Number oldValue,
					Number newValue) {
				bogoText.setText(String.valueOf(newValue.intValue() / 25));
				id.setBogoAmount(newValue.intValue() / 25);

				refresh();
				//betLabel.textProperty().setValue(
				//String.valueOf(newValue.intValue());

			}
		});

		saturationSlider.valueProperty().addListener(new ChangeListener<>() {

			@Override
			public void changed(
					ObservableValue<? extends Number> observableValue,
					Number oldValue,
					Number newValue) {
				saturationText.setText(String.valueOf(newValue.intValue()));
				//betLabel.textProperty().setValue(
				//String.valueOf(newValue.intValue());

			}
		});

		hueSlider.valueProperty().addListener(new ChangeListener<>() {

			@Override
			public void changed(
					ObservableValue<? extends Number> observableValue,
					Number oldValue,
					Number newValue) {
				hueText.setText(String.valueOf(newValue.intValue()));
				//betLabel.textProperty().setValue(
				//String.valueOf(newValue.intValue());

			}
		});

		brightnessSlider.valueProperty().addListener(new ChangeListener<>() {

			@Override
			public void changed(
					ObservableValue<? extends Number> observableValue,
					Number oldValue,
					Number newValue) {
				brightnessText.setText(String.valueOf(newValue.intValue()));
				//betLabel.textProperty().setValue(
				//String.valueOf(newValue.intValue());

			}
		});
	}


}