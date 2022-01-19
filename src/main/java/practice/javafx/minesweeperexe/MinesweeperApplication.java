package practice.javafx.minesweeperexe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class MinesweeperApplication extends Application {

    public static void main(final String[] args) {launch();}

    public static Image flag;
    public static Image wrongFlag;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MinesweeperMain.fxml")));
        //root.getStylesheets().add(Objects.requireNonNull(getClass().getResource("MinesweeperMain.css")).toExternalForm());
        flag = new Image(Objects.requireNonNull(getClass().getResource("img/flag_icon.png")).toExternalForm());
        wrongFlag = new Image(Objects.requireNonNull(getClass().getResource("img/incorrect_flag.png")).toExternalForm());
        primaryStage.setTitle("Minesweeper");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMaximized(true);
        primaryStage.show();
    }
}
