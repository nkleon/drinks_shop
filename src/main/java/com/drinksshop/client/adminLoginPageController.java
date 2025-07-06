package com.drinksshop.client;

import com.drinksshop.shared.DBFunctions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Objects;

public class adminLoginPageController {

    @FXML
    private Button btnExitAdminPage;

    @FXML
    private Button btnLoginAdmin;

    @FXML
    private PasswordField txtPasswordAdmin;

    @FXML
    private TextField txtUsernameAdmin;

    @FXML
    private VBox vboxAdminLgn;

    @FXML
    void exitAdminPage(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void exitAdminToSplashPage(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("SplashPage.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void loginAdmin(ActionEvent event) {
        String username = txtUsernameAdmin.getText();
        String password = txtPasswordAdmin.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Login Error", "Please fill all the input fields");
            return;
        }

        try {
            // Connect to RMI registry (adjust host if not localhost)
            Registry registry = LocateRegistry.getRegistry("localhost", 10800);
            DBFunctions db = (DBFunctions) registry.lookup("DBFunctions");

            boolean loginOk = db.checkAdminCredentials(username, password);

            if (loginOk) {
                showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome, " + username + "!");

                // Switch to Admin Dashboard
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                try {
                    AdminClientFX adminApp = new AdminClientFX();
                    Parent dashboardRoot = adminApp.getDashboardRoot();
                    Scene dashboardScene = new Scene(dashboardRoot, 900, 600);
                    stage.setScene(dashboardScene);
                    stage.setTitle("Admin Dashboard");
                } catch (Exception e) {
                    e.printStackTrace();
                    showAlert(Alert.AlertType.ERROR, "Dashboard Error", "Failed to load admin dashboard:\n" + e.getMessage());
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Login Failed", "Incorrect admin name or password.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Remote Error", "Could not contact RMI server:\n" + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}