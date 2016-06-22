package net.sourceforge.marathon.demo;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginFX extends ModalDialog<String> {

    private TextField nameField = new TextField();
    private PasswordField passwordField = new PasswordField();
    private CheckBox rememberMe = new CheckBox("Remember me");
    private Button loginButton = new Button("Login");
    private Button cancelButton = new Button("Cancel");
    private GridPane gridPane = new GridPane();

    public LoginFX() {
        super("Login");
    }

    @Override protected Parent getContentPane() {
        VBox root = new VBox();
        rememberMe.setId("rememberMe");
        root.getChildren().addAll(createFields(), createButtonBar());
        return root;
    }

    private Parent createFields() {
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(20);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(80);
        gridPane.getColumnConstraints().addAll(col1, col2);
        VBox.setMargin(gridPane, new Insets(10, 10, 0, 10));

        createNameField();
        createPasswordField();
        createRememberMeField();

        return gridPane;
    }

    private void createNameField() {
        Label nameLabel = new Label("Name: ");
        GridPane.setConstraints(nameLabel, 0, 0);
        nameField.textProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                updateLoginState();
            }
        });
        nameField.setId("userName");
        GridPane.setConstraints(nameField, 1, 0);
        gridPane.getChildren().addAll(nameLabel, nameField);
    }

    private void updateLoginState() {
        loginButton.setDisable(!(nameField.getText().length() != 0 && passwordField.getText().length() != 0));
    }

    private void createPasswordField() {
        Label passwordLabel = new Label("Password: ");
        GridPane.setConstraints(passwordLabel, 0, 1);
        GridPane.setMargin(passwordLabel, new Insets(5, 0, 0, 0));
        passwordField.textProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                updateLoginState();
            }
        });
        passwordField.setId("password");
        GridPane.setConstraints(passwordField, 1, 1);
        GridPane.setMargin(passwordField, new Insets(5, 0, 0, 0));
        gridPane.getChildren().addAll(passwordLabel, passwordField);
    }

    private void createRememberMeField() {
        rememberMe.setId("rememberMe");
        GridPane.setConstraints(rememberMe, 1, 3);
        GridPane.setMargin(rememberMe, new Insets(5, 0, 0, 0));
        gridPane.getChildren().add(rememberMe);
    }

    private ButtonBar createButtonBar() {
        ButtonBar buttonBar = new ButtonBar();
        loginButton.setDisable(true);
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                showMessage(getStage());
                dispose();
            }
        });
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                dispose();
            }
        });
        loginButton.setId("login");
        cancelButton.setId("cancel");
        buttonBar.getButtons().addAll(loginButton, cancelButton);
        VBox.setMargin(buttonBar, new Insets(5, 10, 5, 0));
        return buttonBar;
    }

    private void dispose() {
        getStage().close();
    }

    private void showMessage(Stage owner) {
        MeassageDialog messageDialog = new MeassageDialog(
                "You logged in as: " + nameField.getText() + " with password: " + passwordField.getText());
        messageDialog.show(owner);
    }
}
