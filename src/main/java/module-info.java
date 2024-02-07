module net.pyel {
	requires javafx.controls;
	requires javafx.fxml;
	requires xstream;
	requires java.desktop;
    requires java.sql;

    opens net.pyel to javafx.fxml, xstream;
	exports net.pyel;
	exports net.pyel.utils;
	opens net.pyel.utils to javafx.fxml, xstream;
	exports net.pyel.models;
	opens net.pyel.models to javafx.fxml, xstream;

}
