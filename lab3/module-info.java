module W3Carloan {
	 requires javafx.controls;
	    requires javafx.fxml;

	    opens Controller to javafx.fxml;
	    exports application;
	    exports Controller;
}
