package practice.javafx.bin2dec;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Bin2DecApplication extends Application {

    public static void main(final String[] args) { launch(args); }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Bin2DecMain.fxml")));
        primaryStage.setTitle("Binary to Decimal Converter");
        primaryStage.setScene(new Scene(root, 312, 400));
        primaryStage.show();
    }
}
