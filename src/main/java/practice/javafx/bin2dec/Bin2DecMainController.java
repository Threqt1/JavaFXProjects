package practice.javafx.bin2dec;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class Bin2DecMainController implements Initializable {
    @FXML
    public TextField binaryInput;

    @FXML
    public TextField decimalOutput;

    final int maxCharacters = 8;

    public void initialize(URL url, ResourceBundle rb) {
        binaryInput.addEventHandler(KeyEvent.KEY_TYPED, event -> {
            TextField source = (TextField) event.getSource();
            String text = source.getText();

            if (source.getText().length() >= maxCharacters) {
                event.consume();
            } else {
                if (event.getCharacter().charAt(0) != 8 && event.getCharacter().matches("[^0-1]")) {
                    event.consume();
                } else
                    if (event.getCharacter().matches("[0-1]")) {
                        text += event.getCharacter();
                    }
                convertAndDisplayBinary(text);
            }
        });
    }

    public void convertAndDisplayBinary(String binary) {
        int decimalOutputNumber = 0;
        for (int i = binary.length() - 1, j = 0; i >= 0; i--, j++) {
            int numberAtI = Integer.parseInt("" + binary.charAt(i));
            decimalOutputNumber += numberAtI * (int)(Math.pow(2, j));
        }
        decimalOutput.setText("" + decimalOutputNumber);
    }
}
