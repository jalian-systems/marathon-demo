package net.sourceforge.marathon.demo;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

public abstract class ModalDialog<T> {

    private T returnValue;
    private String title;
    private Stage stage;
    private double sceneWidth;
    private double sceneHeight;

    public ModalDialog(String title) {
        this.title = title;
    }

    public ModalDialog(String title, double sceneWidth, double sceneHeight) {
        this(title);
        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;
    }

    public T show(Window parent) {
        Stage stage = getStage();
        stage.showAndWait();
        return getReturnValue();
    }

    public Stage getStage() {
        if (stage != null)
            return stage;
        Parent contentPane = getContentPane();
        Scene scene;
        if (sceneWidth > 0 && sceneHeight > 0)
            scene = new Scene(contentPane, sceneWidth, sceneHeight);
        else
            scene = new Scene(contentPane);
        initialize(scene);
        stage = new Stage();
        stage.setScene(scene);
        initialize(stage);
        if (title != null && stage.getTitle() == null)
            stage.setTitle(title);
        return stage;
    }

    protected void initialize(Stage stage) {
        stage.sizeToScene();
    }

    protected void initialize(Scene scene) {
    }

    protected abstract Parent getContentPane();

    public T getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(T value) {
        returnValue = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        stage.setTitle(title);
    }
}
