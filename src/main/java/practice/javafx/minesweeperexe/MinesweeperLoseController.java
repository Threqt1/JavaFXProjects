package practice.javafx.minesweeperexe;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MinesweeperLoseController {
    @FXML
    Label timeTaken;

    @FXML
    Label bestTime;

    @FXML
    Button newGame;

    boolean newGameRequested = false;

    @FXML
    void newGameRequested() {
        newGameRequested = true;
        ((Stage) newGame.getScene().getWindow()).close();
    }

    void setBestTime(String time) {
        bestTime.setText(time);
    }

    public boolean getNewGameRequested() { return newGameRequested; }
}
