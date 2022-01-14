package practice.javafx.borderradiuspreviewer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class BorderRadiusPreviewerApplication extends Application {
    public static void main(final String[] args) {launch();}

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("BorderRadiusPreviewerMain.fxml")));
        primaryStage.setTitle("Border Radius Previewer");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
