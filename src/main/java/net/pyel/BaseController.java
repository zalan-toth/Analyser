package net.pyel;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.text.Text;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import net.pyel.models.ImageData;
import net.pyel.models.ImageProcess;
import net.pyel.models.Labeler;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
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
	Labeler labeler = new Labeler();
	Stage popupstage = new Stage();
	Parent popuproot;
	Scene popupScene;
	Stage terminalStage = new Stage();
	Parent terminalRoot;
	Scene terminalScene;
	boolean setRun = true;
	ImageData id;
	ImageProcess ip;
	int color1 = 0;
	int color2 = 0;
	int backgroundColor = 0xFFFFFFFF;

	//███████╗██╗░░██╗███╗░░░███╗██╗░░░░░░░░░░░░░██████╗░███████╗░█████╗░██╗░░░░░░█████╗░██████╗░███████╗
	//██╔════╝╚██╗██╔╝████╗░████║██║░░░░░░░░░░░░░██╔══██╗██╔════╝██╔══██╗██║░░░░░██╔══██╗██╔══██╗██╔════╝
	//█████╗░░░╚███╔╝░██╔████╔██║██║░░░░░░░░░░░░░██║░░██║█████╗░░██║░░╚═╝██║░░░░░███████║██████╔╝█████╗░░
	//██╔══╝░░░██╔██╗░██║╚██╔╝██║██║░░░░░░░░░░░░░██║░░██║██╔══╝░░██║░░██╗██║░░░░░██╔══██║██╔══██╗██╔══╝░░
	//██║░░░░░██╔╝╚██╗██║░╚═╝░██║███████╗░░░░░░░░██████╔╝███████╗╚█████╔╝███████╗██║░░██║██║░░██║███████╗
	//╚═╝░░░░░╚═╝░░╚═╝╚═╝░░░░░╚═╝╚══════╝░░░░░░░░╚═════╝░╚══════╝░╚════╝░╚══════╝╚═╝░░╚═╝╚═╝░░╚═╝╚══════╝

	@FXML
	private ListView<String> viewFacility = new ListView<>();


	public CategoryAxis redChartCategoryAxis = new CategoryAxis();
	public NumberAxis redChartNumberAxis = new NumberAxis();
	public BarChart<String, Number> redChart = new BarChart<>(redChartCategoryAxis, redChartNumberAxis);
	public ImageView imageView = new ImageView();
	@FXML
	public Text color1text = new Text();
	@FXML
	public Text color2text = new Text();
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
			id = new ImageData(image);
			ip = new ImageProcess(image);
			ip.processMe();


			originalImageView.setImage(id.getOriginalImage());
			imageView.setImage(id.getImageToEdit());
			grayImage = id.getGrayScaleImage();
			grayImageView.setImage(grayImage);
			redImageView.setImage(id.getOnlyRedChannelImage());
			greenImageView.setImage(id.getOnlyGreenChannelImage());
			blueImageView.setImage(id.getOnlyBlueChannelImage());
			//redChart = createBarChart(id.getRedFrequency(), "Red");

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
		return ip.findClickedColor((int) originalX, (int) originalY);
	}


	private void setupPortListViewListener() {
		imageView.setOnMouseClicked(event -> {
			double x = event.getX();
			double y = event.getY();
			if (dualToneCheckBox.isSelected()) {

				if (color1 == 0) {
					color1 = findColor(x, y);
					color1text.setText("Color 1 selected");
					System.out.println("c1");
				} else if (color2 == 0) {
					color2 = findColor(x, y);
					color2text.setText("Color 2 selected");
					System.out.println("c2");
					ip.createRelationSet(color1, color2, 0);
					bwImageView.setImage(ip.createSetForBW());
				} else {
					color1 = 0;
					color2 = 0;
					color1text.setText("-");
					color2text.setText("-");
				}

			} else {

				int foundColor = findColor(x, y);
				ip.createRelationSet(foundColor, 0, 0);
				bwImageView.setImage(ip.createSetForBW());
			}
		});
		viewFacility.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			// This will be called whenever the user selects a different item in the list
			if (newValue != null) {
				refresh();
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