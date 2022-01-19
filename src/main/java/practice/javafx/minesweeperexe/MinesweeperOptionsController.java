package practice.javafx.minesweeperexe;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MinesweeperOptionsController implements Initializable {
    @FXML
    Slider rowsSlider, columnsSlider, minesSlider;

    @FXML
    Label rowsOutput, columnsOutput, minesOutput;

    @FXML
    Button newGame;

    boolean newGameRequested = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        rowsSlider.valueProperty().addListener((event, oldValue, newValue) -> rowsOutput.setText("" + newValue.intValue()));
        columnsSlider.valueProperty().addListener((event, oldValue, newValue) -> columnsOutput.setText("" + newValue.intValue()));
        minesSlider.valueProperty().addListener((event, oldValue, newValue) -> minesOutput.setText("" + newValue.intValue() + "%"));
    }

    @FXML
    void newGameRequested() {
        newGameRequested = true;
        ((Stage) rowsSlider.getScene().getWindow()).close();
    }

    public void initValues(int numberOfRows, int numberOfColumns, double percentMines) {
        rowsSlider.setValue(numberOfRows);
        columnsSlider.setValue(numberOfColumns);
        minesSlider.setValue(percentMines * 100);
    }

    public boolean getNewGameRequested() { return newGameRequested; }
    public int getRowsRequested() { return rowsSlider.valueProperty().intValue(); }
    public int getColumnsRequested() { return columnsSlider.valueProperty().intValue(); }
    public double getMinesRequested() { return (double) minesSlider.valueProperty().intValue() / 100.0; }
}
