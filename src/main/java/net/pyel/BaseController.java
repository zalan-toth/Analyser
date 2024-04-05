package net.pyel;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
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
 * @author Zalán Tóth & Marcin Budzinski
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
	Stage popupstage = new Stage();
	Parent popuproot;
	Scene popupScene;
	Stage terminalStage = new Stage();
	Parent terminalRoot;
	Scene terminalScene;
	boolean setRun = true;
	ImageData id;

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
	//██╗░░░░░░█████╗░░██████╗░██╗███╗░░██╗
	//██║░░░░░██╔══██╗██╔════╝░██║████╗░██║
	//██║░░░░░██║░░██║██║░░██╗░██║██╔██╗██║
	//██║░░░░░██║░░██║██║░░╚██╗██║██║╚████║
	//███████╗╚█████╔╝╚██████╔╝██║██║░╚███║
	//╚══════╝░╚════╝░░╚═════╝░╚═╝╚═╝░░╚══╝
	//░█████╗░░█████╗░███╗░░██╗████████╗██████╗░░█████╗░██╗░░░░░██╗░░░░░███████╗██████╗░
	//██╔══██╗██╔══██╗████╗░██║╚══██╔══╝██╔══██╗██╔══██╗██║░░░░░██║░░░░░██╔════╝██╔══██╗
	//██║░░╚═╝██║░░██║██╔██╗██║░░░██║░░░██████╔╝██║░░██║██║░░░░░██║░░░░░█████╗░░██████╔╝
	//██║░░██╗██║░░██║██║╚████║░░░██║░░░██╔══██╗██║░░██║██║░░░░░██║░░░░░██╔══╝░░██╔══██╗
	//╚█████╔╝╚█████╔╝██║░╚███║░░░██║░░░██║░░██║╚█████╔╝███████╗███████╗███████╗██║░░██║
	//░╚════╝░░╚════╝░╚═╝░░╚══╝░░░╚═╝░░░╚═╝░░╚═╝░╚════╝░╚══════╝╚══════╝╚══════╝╚═╝░░╚═╝

	public BaseController() {
		//panelAPI = BackgroundController.getPanelAPI();
		//machines = panelAPI.panel.getMachines();
		//games = panelAPI.panel.getGames();
	}

	//██████╗░███████╗██████╗░░██████╗██╗░██████╗████████╗███████╗███╗░░██╗░█████╗░███████╗
	//██╔══██╗██╔════╝██╔══██╗██╔════╝██║██╔════╝╚══██╔══╝██╔════╝████╗░██║██╔══██╗██╔════╝
	//██████╔╝█████╗░░██████╔╝╚█████╗░██║╚█████╗░░░░██║░░░█████╗░░██╔██╗██║██║░░╚═╝█████╗░░
	//██╔═══╝░██╔══╝░░██╔══██╗░╚═══██╗██║░╚═══██╗░░░██║░░░██╔══╝░░██║╚████║██║░░██╗██╔══╝░░
	//██║░░░░░███████╗██║░░██║██████╔╝██║██████╔╝░░░██║░░░███████╗██║░╚███║╚█████╔╝███████╗

	/**
	 * Loads data from panel.xml
	 */
	@FXML
	private void loadData() {
		BackgroundController.loadData();
		//BackgroundController.setPanelAPI(panelAPI);
		//deselectMachine();
		//deselectGame();
		//machines = panelAPI.panel.getMachines();
		//games = panelAPI.panel.getGames();
		initialize(null, null);
	}


	/**
	 * Saves data to panel.xml
	 */
	@FXML
	private void saveData() {
		BackgroundController.saveData();
	}

	/**
	 * Will load the panel with new data
	 */
	@FXML
	private void newPanel() throws IOException {
		//BackgroundController.setPanelAPI(new PanelAPI(staff.getText()));
		App.setRoot("main");
		App.getStageInfo().setTitle("Game Panel | Ports");
		App.getStageInfo().setHeight(900);//920
		App.getStageInfo().setWidth(1400);//1434
		App.getStageInfo().setResizable(false);


	}

	/**
	 * Setup of basepanel
	 */
	@FXML
	private void basePanel() throws IOException {
		//BackgroundController.setPanelAPI(new PanelAPI(staff.getText()));
		App.getStageInfo().setFullScreen(false);
		App.getStageInfo().setHeight(647);
		App.getStageInfo().setWidth(1024);
		App.getStageInfo().setResizable(false);
		App.getStageInfo().setTitle("Game Panel | Welcome");
		App.setRoot("base");


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

	/**
	 * Will load the panel with the saved data panel.xml
	 */
	@FXML
	private void loadPanel() throws IOException {
		//	BackgroundController.setPanelAPI(new PanelAPI(staff.getText()));
		//panelAPI = BackgroundController.getPanelAPI();
		loadData();
		//machines = panelAPI.panel.getMachines();
		//games = panelAPI.panel.getGames();
		/*if (shipsOnSea == null) {
			shipsOnSea = new CustomList<>();
		}*/
		initialize(null, null);
		App.setRoot("main");
		App.getStageInfo().setHeight(900);
		App.getStageInfo().setWidth(1400);
		App.getStageInfo().setResizable(false);


	}

	//██╗░░██╗███████╗██╗░░░░░██████╗░░░░░░██╗░░░░░░░██╗██╗███╗░░██╗██████╗░░█████╗░░██╗░░░░░░░██╗
	//██║░░██║██╔════╝██║░░░░░██╔══██╗░░░░░██║░░██╗░░██║██║████╗░██║██╔══██╗██╔══██╗░██║░░██╗░░██║
	//███████║█████╗░░██║░░░░░██████╔╝░░░░░╚██╗████╗██╔╝██║██╔██╗██║██║░░██║██║░░██║░╚██╗████╗██╔╝
	//██╔══██║██╔══╝░░██║░░░░░██╔═══╝░░░░░░░████╔═████║░██║██║╚████║██║░░██║██║░░██║░░████╔═████║░
	//██║░░██║███████╗███████╗██║░░░░░░░░░░░╚██╔╝░╚██╔╝░██║██║░╚███║██████╔╝╚█████╔╝░░╚██╔╝░╚██╔╝░
	//╚═╝░░╚═╝╚══════╝╚══════╝╚═╝░░░░░░░░░░░░╚═╝░░░╚═╝░░╚═╝╚═╝░░╚══╝╚═════╝░░╚════╝░░░░╚═╝░░░╚═╝░░
	@FXML
	private void openHelpMenu() throws IOException {
		popuproot = FXMLLoader.load(getClass().getResource("help.fxml"));
		popupstage.setResizable(false);
		popupstage.setTitle("Image Analyser | About & Help Centre");
		popupScene = new Scene(popuproot);
		popupstage.setScene(popupScene);
		popupstage.show();
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

		imageView.setImage(id.getImageToEdit());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		if (setRun) {
			setupPortListViewListener();
			setRun = false;
		}
	}


	private void setupPortListViewListener() {
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