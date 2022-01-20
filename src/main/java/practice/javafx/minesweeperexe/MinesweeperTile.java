package practice.javafx.minesweeperexe;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Random;

public class MinesweeperTile extends StackPane {
    static int amountCreated = 0;

    final static Random random = new Random();

    final static Color[] hiddenVariations = {
            Color.rgb(170, 215, 81),
            Color.rgb(162, 209, 73)
    };

    final static Color[] displayedVariations = {
            Color.rgb(229, 194, 159),
            Color.rgb(215, 184, 153)
    };

    final static Color[] mineVariations = {
            Color.rgb(219, 50, 54),
            Color.rgb(237, 68, 181),
            Color.rgb(72, 133, 237),
            Color.rgb(0, 135, 68),
            Color.rgb(72, 230, 241),
            Color.rgb(182, 72, 242),
            Color.rgb(244, 194, 13),
            Color.rgb(244, 132, 13)
    };

    final static Color[] labelColors = {
            Color.rgb(32, 120, 208),
            Color.rgb(69, 146, 67),
            Color.rgb(211, 48, 48),
            Color.rgb(131, 44, 161),
            Color.rgb(255, 143, 0),
            Color.rgb(0, 151, 167),
            Color.rgb(66, 66, 66),
            Color.rgb(158, 158, 158)
    };

    private final double x;
    private final double y;

    private final double width;
    private final double height;

    private boolean isBomb;
    private int amountOfBombsNearby;

    private boolean isFlagged;
    private boolean isFlipped;

    private Rectangle flippedBackground;
    private Text flippedLabel;
    private ImageView flaggedImage;

    MinesweeperTile(double x, double y, double width, double height) {
        super();

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.isFlagged = false;
        this.isBomb = false;
        this.isFlipped = false;
        this.amountOfBombsNearby = 0;
    }

    public void setBomb() {
        isBomb = true;
    }

    public void incrementBombsNear() {
        if (isBomb) return;
        amountOfBombsNearby++;
    }

    public boolean isFlipped() { return isFlipped; }
    public boolean isFlagged() { return isFlagged; }
    public boolean isBomb() { return isBomb; }
    public int getAmountOfBombsNearby() { return amountOfBombsNearby; }

    public void flip() {
        if (isFlipped) return;
        getChildren().clear();
        getChildren().add(flippedBackground);
        if (flippedLabel != null) {
            getChildren().add(flippedLabel);
        }
        isFlipped = true;
    }

    public void flag() {
        if (isFlagged) {
            getChildren().remove(flaggedImage);
            flaggedImage = null;
            isFlagged = false;
        } else {
            flaggedImage = new ImageView(MinesweeperApplication.flag);
            getChildren().add(flaggedImage);
            flaggedImage.setPreserveRatio(true);
            flaggedImage.setFitWidth(width);
            flaggedImage.setFitHeight(height);
            isFlagged = true;
        }
    }

    public void markWrong() {
        getChildren().clear();
        getChildren().add(flippedBackground);
        ImageView wrongFlag = new ImageView(MinesweeperApplication.wrongFlag);
        getChildren().add(wrongFlag);
        wrongFlag.setPreserveRatio(true);
        wrongFlag.setFitHeight(width);
        wrongFlag.setFitHeight(height);
    }

    public void renderTile() {
        getChildren().clear();
        setLayoutX(x);
        setLayoutY(y);

        flippedBackground = new Rectangle();
        flippedBackground.setStrokeWidth(0);
        flippedBackground.setArcHeight(0);
        flippedBackground.setArcWidth(0);
        flippedBackground.setWidth(width);
        flippedBackground.setHeight(height);

        if (isBomb) {
            flippedBackground.setFill(mineVariations[random.nextInt(mineVariations.length)]);
        } else {
            flippedBackground.setFill(displayedVariations[amountCreated % 2]);
        }

        Rectangle hiddenBackground = new Rectangle();
        hiddenBackground.setStrokeWidth(0);
        hiddenBackground.setArcHeight(0);
        hiddenBackground.setArcWidth(0);
        hiddenBackground.setWidth(width);
        hiddenBackground.setHeight(height);
        hiddenBackground.setFill(hiddenVariations[amountCreated % 2]);

        if (amountOfBombsNearby > 0) {
            flippedLabel = new Text("" + amountOfBombsNearby);
            flippedLabel.setFill(labelColors[amountOfBombsNearby - 1]);
            Font font = new Font("Consolas", Math.max(width, height) + 4);
            flippedLabel.setFont(font);

            while (flippedLabel.getBoundsInLocal().getWidth() >= width || flippedLabel.getBoundsInLocal().getHeight() >= height) {
                font = new Font("Consolas", font.getSize() - 2);
                flippedLabel.setFont(font);
            }
        }

        getChildren().add(hiddenBackground);
        amountCreated++;
    }
}
