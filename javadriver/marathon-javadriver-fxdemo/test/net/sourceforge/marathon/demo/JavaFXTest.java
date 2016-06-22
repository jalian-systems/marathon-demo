package net.sourceforge.marathon.demo;

import org.junit.After;
import org.junit.Before;

import com.thoughtworks.selenium.Wait;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public abstract class JavaFXTest {

    public static class ApplicationHelper extends Application {

        public static void startApplication() {
            new Thread(new Runnable() {
                @Override public void run() {
                    Application.launch(ApplicationHelper.class);
                }
            }).start();
        }

        private Stage primaryStage;
        protected Pane mainPane;
        protected Stage mainStage;

        @Override public void start(Stage primaryStage) throws Exception {
            this.primaryStage = primaryStage;
            JavaFXTest.applicationHelper = this;
        }

        public Stage getPrimaryStage() {
            return primaryStage;
        }

        public void startGUI(final JavaFXTest test) {
            final Object[] return_value = new Object[] { null };
            Platform.runLater(new Runnable() {
                @Override public void run() {
                    mainPane = test.getMainPane();
                    mainStage = test.getMainStage();
                    primaryStage.hide();
                    primaryStage.setTitle(test.getStageTitle());
                    if (mainPane != null && mainStage == null) {
                        // primaryStage.hide();
                        // primaryStage.setTitle(test.getStageTitle());
                        primaryStage.setScene(new Scene(mainPane));
                        primaryStage.sizeToScene();
                        return_value[0] = primaryStage;
                        primaryStage.show();
                    } else if (mainPane == null && mainStage != null) {
                        // primaryStage.hide();
                        // primaryStage.setTitle(test.getStageTitle());
                        primaryStage.setScene(new Scene(new Label("Testing Stages...")));
                        primaryStage.sizeToScene();
                        primaryStage.show();
                        mainStage.initOwner(primaryStage);
                        return_value[0] = mainStage;
                        mainStage.showAndWait();
                    } else {
                        return_value[0] = new RuntimeException("Only one of getMainPane or getMainStage should be implemented");
                    }
                }
            });
            new Wait() {
                @Override public boolean until() {
                    return return_value[0] != null;
                }
            }.wait("Waiting for return_value to be set", 5000);
            if (return_value[0] instanceof RuntimeException)
                throw (RuntimeException) return_value[0];
        }
    }

    private static ApplicationHelper applicationHelper;

    public JavaFXTest() {
    }

    protected String getStageTitle() {
        return "";
    }

    @Before public void startGUI() throws Throwable {
        if (applicationHelper == null)
            ApplicationHelper.startApplication();
        new Wait() {
            @Override public boolean until() {
                return applicationHelper != null;
            }
        }.wait("Waiting for applicationHelper to be initialized", 5000);
        if (applicationHelper == null) {
            throw new RuntimeException("Application Helper = null");
        }
        applicationHelper.mainPane = null;
        applicationHelper.mainStage = null;
        applicationHelper.startGUI(this);
    }

    @After public void closeStageIfNeeded() throws Throwable {
        if (applicationHelper.mainStage != null) {
            Platform.runLater(new Runnable() {
                @Override public void run() {
                    applicationHelper.mainStage.close();
                }
            });
            new Wait() {
                @Override public boolean until() {
                    return !applicationHelper.mainStage.isShowing();
                }

            }.wait("Waiting for mainStage to disapper", 5000);
        }
    }

    public Pane getMainPane() {
        return null;
    }

    public Stage getMainStage() {
        return null;
    }

    public Stage getPrimaryStage() {
        return applicationHelper.getPrimaryStage();
    }

}
