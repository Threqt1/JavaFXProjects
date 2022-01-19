package practice.javafx.minesweeperexe;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

public class MinesweeperMainController implements Initializable {
    @FXML
    StackPane gameOverlays;

    @FXML
    Pane gamePane;

    @FXML
    Label statusLabel;

    final Random random = new Random();

    int boardSizeX = 10;
    int boardSizeY = 10;
    double percentMines = 0.2;

    Timeline statusUpdater = new Timeline();
    Timeline gameDeathAnimation = new Timeline();

    MinesweeperMinefield minefield;
    ArrayList<int[]> bombTiles = new ArrayList<>();
    ArrayList<int[]> flagTiles = new ArrayList<>();

    int numberOfFlags = 0;
    int time = 0;
    boolean gameRunning = false;

    ArrayList<int[]> animationFlipAsBombs = new ArrayList<>();
    ArrayList<int[]> animationExposeAsWrong = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(this::setupGame);
    }

    void setupGame() {
        resetGame();
        numberOfFlags = (int) (boardSizeX * boardSizeY * percentMines);
        minefield = new MinesweeperMinefield(boardSizeX, boardSizeY, numberOfFlags);
        double height = gamePane.getHeight() / (double) boardSizeY;
        double width = gamePane.getWidth() / (double) boardSizeX;

        for (int y = 0; y < boardSizeY; y++) {
            for (int x = 0; x < boardSizeX; x++) {
                int finalX = x;
                int finalY = y;
                MinesweeperTile tile = new MinesweeperTile(finalX * width, finalY * height, width, height);
                gamePane.getChildren().add(tile);
                minefield.set(finalX, finalY, tile);
                tile.setOnMouseClicked(event -> {
                    if (!gameRunning) return;
                    if (event.getButton() == MouseButton.PRIMARY) {
                        event.consume();
                        if (!minefield.flip(finalX, finalY)) {
                            stopGameLose();
                        }
                        if (minefield.isGameWon(numberOfFlags)) {
                            stopGameWin();
                        }
                    } else if (event.getButton() == MouseButton.SECONDARY) {
                        event.consume();
                        int flagResult = minefield.flag(finalX, finalY, numberOfFlags);
                        if (flagResult == 1) {
                            flagTiles.add(new int[]{finalX, finalY});
                            numberOfFlags--;
                        } else if (flagResult == -1) {
                            flagTiles.removeIf(coords -> coords[0] == finalX && coords[1] == finalY);
                            numberOfFlags++;
                        }
                        statusLabel.setText("Flags: " + numberOfFlags + " | Time: " + time);
                    }
                });
            }
        }

        int bombsCreated = 0;
        while (bombsCreated < numberOfFlags) {
            int x = random.nextInt(boardSizeX);
            int y = random.nextInt(boardSizeY);
            minefield.get(x, y).setBomb();
            bombTiles.add(new int[]{x, y});
            bombsCreated++;
        }

        for (int y = 0; y < boardSizeY; y++) {
            for (int x = 0; x < boardSizeX; x++) {
                MinesweeperTile tile = minefield.get(x, y);
                if (!tile.isBomb()) {
                    //Top
                    if (y - 1 >= 0) {
                        if (minefield.get(x, y - 1).isBomb()) {
                            tile.incrementBombsNear();
                        }
                    }
                    //Right
                    if (x + 1 < boardSizeX) {
                        if (minefield.get(x + 1, y).isBomb()) {
                            tile.incrementBombsNear();
                        }
                    }
                    //Left
                    if (x - 1 >= 0) {
                        if (minefield.get(x - 1, y).isBomb()) {
                            tile.incrementBombsNear();
                        }
                    }
                    //Bottom
                    if (y + 1 < boardSizeY) {
                        if (minefield.get(x, y + 1).isBomb()) {
                            tile.incrementBombsNear();
                        }
                    }
                    //Top-Left
                    if (y - 1 >= 0 && x - 1 >= 0) {
                        if (minefield.get(x - 1, y - 1).isBomb()) {
                            tile.incrementBombsNear();
                        }
                    }
                    //Bottom-Left
                    if (y + 1 < boardSizeY && x - 1 >= 0) {
                        if (minefield.get(x - 1, y + 1).isBomb()) {
                            tile.incrementBombsNear();
                        }
                    }
                    //Top-Right
                    if (y - 1 >= 0 && x + 1 < boardSizeX) {
                        if (minefield.get(x + 1, y - 1).isBomb()) {
                            tile.incrementBombsNear();
                        }
                    }
                    //Bottom-Right
                    if (y + 1 < boardSizeY && x + 1 < boardSizeX) {
                        if (minefield.get(x + 1, y + 1).isBomb()) {
                            tile.incrementBombsNear();
                        }
                    }
                }
            }
        }

        for (int y = 0; y < boardSizeY; y++) {
            for (int x = 0; x < boardSizeX; x++) {
                minefield.get(x, y).renderTile();
            }
        }

        statusUpdater = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            time++;
            statusLabel.setText("Flags: " + numberOfFlags + " | Time: " + time);
        }));
        statusUpdater.setCycleCount(Timeline.INDEFINITE);
        statusUpdater.play();
    }

    void stopGameWin() {
        gameRunning = false;
        statusUpdater.stop();
        try {
            handleEndGameModal(true);
        } catch (Exception ignored) {

        }
    }

    void handleEndGameModal(boolean gameWon) throws IOException {
        FXMLLoader modal = new FXMLLoader(getClass().getResource("Minesweeper" + (gameWon ? "Win" : "Lose") + ".fxml"));
        Parent root = modal.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("You " + (gameWon ? "Won" : "Lost") + "!");
        stage.initOwner(gamePane.getScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.setResizable(false);
        if (gameWon) {
            MinesweeperWinController controller = modal.getController();
            controller.setCurrentTime("" + time);
            stage.setOnHidden(e -> {
                if (controller.getNewGameRequested()) {
                    try {
                        loadOptions();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    Platform.exit();
                }
            });
        } else {
            MinesweeperLoseController controller = modal.getController();
            stage.setOnHidden(e -> {
                if (controller.getNewGameRequested()) {
                    try {
                        loadOptions();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    Platform.exit();
                }
            });
        }
        stage.show();
    }

    void stopGameLose() {
        gameRunning = false;
        statusUpdater.stop();
        gameDeathAnimation = new Timeline();
        float currentDuration = 0;
        for (int[] coordinates : bombTiles) {
            if (!minefield.get(coordinates[0], coordinates[1]).isFlagged()) {
                final int currentX = coordinates[0];
                final int currentY = coordinates[1];
                currentDuration += random.nextFloat() * 0.5f;
                animationFlipAsBombs.add(new int[] { currentX, currentY });
                gameDeathAnimation.getKeyFrames().add(new KeyFrame(Duration.seconds(currentDuration), e -> minefield.get(currentX, currentY).flip()));
            }
        }
        for (int[] coordinates : flagTiles) {
            MinesweeperTile tile = minefield.get(coordinates[0], coordinates[1]);
            if (!tile.isBomb()) {
                currentDuration += random.nextFloat() * 0.5f;
                final int currentX = coordinates[0];
                final int currentY = coordinates[1];
                animationExposeAsWrong.add(new int[] { currentX, currentY });
                gameDeathAnimation.getKeyFrames().add(new KeyFrame(Duration.seconds(currentDuration), e -> minefield.get(currentX, currentY).markWrong()));
            }
        }
        gameDeathAnimation.setOnFinished(e -> {
            try {
                handleEndGameModal(false);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        gameDeathAnimation.play();
    }

    @FXML
    void startNewGame(KeyEvent event) {
        if (event.getCharacter().equals(" ")) {
            event.consume();
            try {
                loadOptions();
            } catch (Exception ignored) {
            }
        }
    }

    @FXML
    void loadOptions() throws IOException {
        statusUpdater.pause();
        if (gameDeathAnimation.getStatus() == Animation.Status.RUNNING) {
            gameDeathAnimation.stop();
            for (int[] coordinates : animationFlipAsBombs) {
                minefield.get(coordinates[0], coordinates[1]).flip();
            }
            for (int[] coordinates : animationExposeAsWrong) {
                minefield.get(coordinates[0], coordinates[1]).markWrong();
            }
            try {
                handleEndGameModal(false);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            FXMLLoader loadOptionsModal = new FXMLLoader(getClass().getResource("MinesweeperOptions.fxml"));
            Parent root = loadOptionsModal.load();
            MinesweeperOptionsController controller = loadOptionsModal.getController();
            controller.initValues(boardSizeX, boardSizeY, percentMines);

            Scene scene = new Scene(root);
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Minesweeper Options");
            stage.initOwner(gamePane.getScene().getWindow());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.sizeToScene();
            stage.setResizable(false);
            stage.setOnHidden(e -> {
                if (controller.getNewGameRequested()) {
                    boardSizeX = controller.getRowsRequested();
                    boardSizeY = controller.getColumnsRequested();
                    percentMines = controller.getMinesRequested();
                    setupGame();
                } else {
                    statusUpdater.play();
                    gameDeathAnimation.play();
                }
            });
            stage.show();
        }
    }

    void resetGame() {
        gameOverlays.getChildren().clear();
        gamePane.getChildren().clear();
        statusUpdater.stop();
        gameDeathAnimation.stop();
        time = 0;
        numberOfFlags = 0;
        minefield = null;
        bombTiles.clear();
        flagTiles.clear();
        animationExposeAsWrong.clear();
        animationFlipAsBombs.clear();
        statusLabel.setText("Flags: 0 | Time: 0");
        gameRunning = true;
        gameOverlays.getChildren().add(gamePane);
    }
}
