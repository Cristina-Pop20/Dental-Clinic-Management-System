module gui{
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    //requires org.controlsfx.controls;
    requires org.xerial.sqlitejdbc;
    requires org.slf4j;


    opens gui to javafx.fxml;
    opens main to javafx.fxml;
    exports gui;
    exports main;
}