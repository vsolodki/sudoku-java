package ui;

import constants.State;
import domain.Coordinates;
import domain.Game;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import domain.IUserContractable;

import java.util.HashMap;

public class UserInterface implements IUserContractable.View, EventHandler<KeyEvent> {

    private final Stage stage;
    private final Group root;

    private HashMap<Coordinates, GameTextField> textFieldCoordinates;
    private IUserContractable.EventListener listener;
    private static final double WINDOW_X = 668;
    private static final double WINDOW_Y = 732;
    private static final double BOARD_PADDING = 50;
    private static final double BOARD_X_AND_Y = 576;
    private static final Color WINDOW_BACKGROUND_COLOR = Color.rgb(0,150,136);
    private static final Color BOARD_BACKGROUND_COLOR = Color.rgb(224,242,241);
    private static final String SUDOKU = "Sudoku";

    public UserInterface(Stage stage) {
        this.stage = stage;
        this.root = new Group();
        this.textFieldCoordinates = new HashMap<>();
        initializeUserInterface();
    }

    private void initializeUserInterface() {
        drawBackground(root);
        drawTitle(root);
        drawBoard(root);
        drawTextFields(root);
        drawGridLines(root);
        stage.show();
    }

    private void drawGridLines(Group root) {
        int x_y = 114;
        int index = 0;
        while (index < 8){
            int thickness;
            if (index == 2 || index == 5){
                thickness = 3;
            } else{
                thickness = 2;
            }
            Rectangle verticalLine = getLine(x_y + 64 * index,BOARD_PADDING,BOARD_X_AND_Y,thickness);
            Rectangle horizontalLine = getLine(BOARD_PADDING, x_y + 64 * index,thickness, BOARD_X_AND_Y);
            root.getChildren().addAll(verticalLine,horizontalLine);
            index++;
        }
    }

    private Rectangle getLine(double x, double y, double height, double width) {
        Rectangle line = new Rectangle();
        line.setX(x);
        line.setY(y);
        line.setHeight(height);
        line.setWidth(width);
        line.setFill(Color.BLACK);
        return line;
    }

    private void drawTextFields(Group root) {
        final int xOrigin = 50;
        final int yOrigin = 50;
        final int x_yDelta = 64;
        for (int xIndex = 0; xIndex < 9; xIndex++){
            for(int yIndex = 0; yIndex < 9; yIndex++){
                int x = xOrigin + xIndex * x_yDelta;
                int y = yOrigin + yIndex * x_yDelta;
                GameTextField tile = new GameTextField(xIndex,yIndex);
                tileStylize(tile, x, y);
                tile.setOnKeyPressed(this);
                textFieldCoordinates.put(new Coordinates(xIndex, yIndex), tile);
                root.getChildren().add(tile);
            }
        }
    }

    private void tileStylize(GameTextField tile, int x, int y) {
        Font numberFont = new Font(32);
        tile.setFont(numberFont);
        tile.setAlignment(Pos.CENTER);
        tile.setLayoutX(x);
        tile.setLayoutY(y);
        tile.setPrefHeight(64);
        tile.setPrefWidth(64);
        tile.setBackground(Background.EMPTY);
    }

    private void drawBoard(Group root) {
        Rectangle boardBackground = new Rectangle();
        boardBackground.setX(BOARD_PADDING);
        boardBackground.setY(BOARD_PADDING);
        boardBackground.setHeight(BOARD_X_AND_Y);
        boardBackground.setWidth(BOARD_X_AND_Y);
        boardBackground.setFill(BOARD_BACKGROUND_COLOR);
        root.getChildren().addAll(boardBackground);
    }

    private void drawTitle(Group root) {
        Text title = new Text(235, 690, SUDOKU);
        title.setFill(Color.WHITE);
        Font titleFont = new Font(43);
        title.setFont(titleFont);
        root.getChildren().add(title);
    }

    private void drawBackground(Group root) {
        Scene scene = new Scene(root, WINDOW_X, WINDOW_Y);
        scene.setFill(WINDOW_BACKGROUND_COLOR);
        stage.setScene(scene);
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED){
            if (keyEvent.getText().matches("[0-9]")){
                int value = Integer.parseInt(keyEvent.getText());
                handleInput(value, keyEvent.getSource());
            } else if (keyEvent.getCode() == KeyCode.BACK_SPACE){
                handleInput(0, keyEvent.getSource());
            } else{
                ((TextField) keyEvent.getSource()).setText("");
            }

        }
        keyEvent.consume();
    }

    @Override
    public void setListener(IUserContractable.EventListener listener) {
        this.listener = listener;

    }

    @Override
    public void updateSquare(int x, int y, int input) {
        GameTextField tile = textFieldCoordinates.get(new Coordinates(x, y));
        String value = Integer.toString(input);
        if (value.equals("0")) value = "";
        tile.textProperty().setValue(value);
    }

    @Override
    public void updateBoard(Game game) {
        for (int xIndex = 0; xIndex < 9; xIndex++){
            for (int yIndex = 0; yIndex < 9; yIndex++){
                TextField tile = textFieldCoordinates.get(new Coordinates(xIndex, yIndex));
                String value = Integer.toString(game.getCopyOfGridState()[xIndex][yIndex]);
                if (value.equals("0")) value = "";
                tile.setText(value);
                if (game.getGameState() == State.NEW){
                    if (value.equals("")){
                        tile.setStyle("-fx-opacity: 1; ");
                        tile.setDisable(false);
                    } else{
                        tile.setStyle("-fx-opacity: 0.8; ");
                        tile.setDisable(true);
                    }
                }
            }
        }
    }

    @Override
    public void showDialog(String message) {
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.OK);
        dialog.showAndWait();
        if (dialog.getResult() == ButtonType.OK) listener.onClick();
    }

    @Override
    public void showError(String message) {
        Alert dialog = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        dialog.showAndWait();
    }

    private void handleInput(int value, Object source) {
        listener.onInput(
                ((GameTextField) source).getX(),
                ((GameTextField) source).getY(),
                value);
    }

}
