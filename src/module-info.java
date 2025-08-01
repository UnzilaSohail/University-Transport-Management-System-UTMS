module projtestt {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.sql;
	requires javafx.graphics;
	requires javafx.base;
	   opens Controller;
	   opens JavaFiles to javafx.base;
	    exports application;
	    opens application to javafx.fxml, javafx.base;
exports JavaFiles;
	
}
