package practice.javafx.borderradiuspreviewer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class BorderRadiusPreviewerMainController implements Initializable {
    @FXML
    Rectangle box;

    @FXML
    Slider slider;

    @FXML
    Label radius;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        slider.valueProperty().addListener((listener, oldValue, newValue) -> {
            int newRadius = newValue.intValue();
            radius.setText("Current Radius: " + newRadius);
            box.setStyle("-fx-arc-height: " + newRadius + ";" + "-fx-arc-width: " + newRadius + ";");
        });
    }
}
