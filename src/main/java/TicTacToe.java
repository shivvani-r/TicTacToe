package ticTacToe;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class TicTacToe extends Application {

    private static final String PLAYER_X = "X";
    private static final String PLAYER_O = "O";
    private static final Font APP_FONT = Font.font("Arial", 20);
    private static final int BUTTON_SIZE = 80;
    private static final String WINNING_STYLE = "-fx-border-color: red; -fx-border-width: 3px; -fx-border-radius: 5px;";

    private Button[][] buttons = new Button[3][3];
    private String currentPlayer = PLAYER_X;
    private boolean gameActive = true;
    private Label turnLabel;

    private int turnsTaken = 0;

    public static void main(String[] args) {
        launch(TicTacToe.class);
    }

    @Override
    public void start(Stage window) {
        this.turnLabel = new Label("Turn: " + currentPlayer);
        turnLabel.setFont(APP_FONT);

        Button restartButton = new Button("Restart");
        restartButton.setFont(APP_FONT);
        restartButton.setOnAction(event -> restartGame());

        BorderPane layout = new BorderPane();
        layout.setTop(turnLabel);
        layout.setBottom(restartButton);
        layout.setCenter(createButtonGrid());

        layout.setPadding(new Insets(30, 30, 30, 30));
        layout.setPrefSize(400, 400);

        BorderPane.setAlignment(restartButton, Pos.CENTER);
        BorderPane.setAlignment(turnLabel, Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.setTitle("Tic-Tac-Toe");
        window.show();

    }

    private GridPane createButtonGrid() {
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setVgap(10);
        pane.setHgap(10);
        pane.setPadding(new Insets(10, 10, 10, 10));

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Button btn = new Button("");
                btn.setFont(APP_FONT);
                btn.setPrefSize(BUTTON_SIZE, BUTTON_SIZE);

                btn.setOnAction(event -> handleButtonClick(btn));

                buttons[row][col] = btn;
                pane.add(btn, col, row);
            }
        }
        return pane;
    }

    private void handleButtonClick(Button btn) {
        if (btn.getText().isEmpty() && gameActive) {

            btn.setText(currentPlayer);
            turnsTaken++;

            if (checkWinner()) {
                turnLabel.setText(currentPlayer + " is the winner!");
                disableBoard();
            } else if (isBoardFull()) {
                turnLabel.setText("It's a draw!");
                disableBoard();
            } else {
                switchPlayer();
            }
        }
    }

    public void switchPlayer() {
        currentPlayer = currentPlayer.equals(PLAYER_X) ? PLAYER_O : PLAYER_X;
        turnLabel.setText("Turn: " + currentPlayer);
    }

    public boolean checkWinner() {

        //check rows
        for (int i = 0; i < 3; i++) {
            if (checkLine(buttons[i][0], buttons[i][1], buttons[i][2])) {
                return true;
            }
        }
        //check columns
        for (int i = 0; i < 3; i++) {
            if (checkLine(buttons[0][i], buttons[1][i], buttons[2][i])) {
                return true;
            }
        }
        // Check diagonals
        if (checkLine(buttons[0][0], buttons[1][1], buttons[2][2])) {
            return true;
        }
        if (checkLine(buttons[0][2], buttons[1][1], buttons[2][0])) {
            return true;
        }

        return false;
    }

    public boolean checkLine(Button b1, Button b2, Button b3) {
        String s1 = b1.getText();
        String s2 = b2.getText();
        String s3 = b3.getText();
        if (s1.isEmpty()|| s2.isEmpty() || s3.isEmpty()) {
            return false;
        }
        if (s1.equals(s2) && s2.equals(s3)) {
            highlightWinner(b1, b2, b3);
            return true;
        }
        return false;
    }

    private void highlightWinner(Button b1, Button b2, Button b3) {
        b1.setStyle(WINNING_STYLE);
        b2.setStyle(WINNING_STYLE);
        b3.setStyle(WINNING_STYLE);
    }

    public boolean isBoardFull() {
        return turnsTaken == 9;
    }

    public void disableBoard() {
        gameActive = false;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setDisable(true);
            }
        }
    }

    public void restartGame() {
        gameActive = true;
        currentPlayer = PLAYER_X;
        turnLabel.setText("Turn: " + currentPlayer);
        turnsTaken = 0;

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Button btn = buttons[row][col];
                btn.setStyle(null);
                btn.setDisable(false);
                btn.setText("");
            }
        }
    }

}
