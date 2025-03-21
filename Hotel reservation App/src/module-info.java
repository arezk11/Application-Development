/**
 * 
 */
/**
 * 
 */
module Hotel {
	 requires javafx.controls;
	    requires javafx.fxml;
		requires javafx.graphics;
		requires javafx.base;
		requires java.sql;
		


	    opens controller to javafx.fxml;
	    exports application;
	    exports controller;
}