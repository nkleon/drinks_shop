module com.drinksshop.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.rmi;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    exports com.drinksshop.client;
    exports com.drinksshop.shared;
    opens com.drinksshop.client to javafx.fxml;
}