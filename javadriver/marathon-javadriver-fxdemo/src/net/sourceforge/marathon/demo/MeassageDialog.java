package net.sourceforge.marathon.demo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

class MeassageDialog extends ModalDialog<String> {

    private String message;

    public MeassageDialog(String message) {
        super("Login Success");
        this.message = message;
    }

    @Override protected Parent getContentPane() {
        VBox vBox = new VBox();
        Label label = new Label(message);
        VBox.setMargin(label, new Insets(10, 10, 0, 10));
        ButtonBar buttonBar = new ButtonBar();
        VBox.setMargin(buttonBar, new Insets(10, 10, 0, 10));
        Button okButton = new Button("Ok");
        okButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                dispose();
            }
        });
        buttonBar.getButtons().add(okButton);
        vBox.getChildren().addAll(label, buttonBar);
        return vBox;
    }

    private void dispose() {
        getStage().close();
    }
}