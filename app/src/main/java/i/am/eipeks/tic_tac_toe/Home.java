package i.am.eipeks.tic_tac_toe;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Home extends AppCompatActivity implements View.OnClickListener {

    private enum Value {
        ZERO, X, EMPTY, PLAYER_ONE, PLAYER_TWO, COMPUTER
    }

    private int movesCounter;

    private Value[][] board;

    private Value currentPlayer;

    private Button currentButton;

    @BindView(R.id.row0_column0) Button row0_column0;
    @BindView(R.id.row0_column1) Button row0_column1;
    @BindView(R.id.row0_column2) Button row0_column2;
    @BindView(R.id.row1_column0) Button row1_column0;
    @BindView(R.id.row1_column1) Button row1_column1;
    @BindView(R.id.row1_column2) Button row1_column2;
    @BindView(R.id.row2_column0) Button row2_column0;
    @BindView(R.id.row2_column1) Button row2_column1;
    @BindView(R.id.row2_column2) Button row2_column2;
    @BindView(R.id.turn_text) TextView turnDisplay;
    @BindView(R.id.game_result) TextView gameResult;
    @BindView(R.id.restart_game) Button restartGame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        setUpBoard();

        setListeners();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.restart_game){
            Restart(v);
            return;
        }

        if (!isEmptyBlock(getRowAndColumnOfButton(id))){
            Toast.makeText(this, "Invalid move.", Toast.LENGTH_SHORT).show();
            return;
        }

        currentButton = findViewById(id);
        play(id);
//        switch (id){
//            case R.id.row0_column0:
//                play(id);
//                break;
//            case R.id.row0_column1:
//                play(id);
//                break;
//            case R.id.row0_column2:
//                play(id);
//                break;
//            case R.id.row1_column0:
//
//                break;
//            case R.id.row1_column1:
//
//                break;
//            case R.id.row1_column2:
//
//                break;
//            case R.id.row2_column0:
//
//                break;
//            case R.id.row2_column1:
//
//                break;
//            case R.id.row2_column2:
//
//                break;
//        }

    }

    @SuppressLint("InflateParams")
    private void setUpBoard(){
        board = new Value[3][3];
        for (int row = 0; row < board.length; row++){
            for (int column = 0; column < board[row].length; column++){
                board[row][column] = Value.EMPTY;
            }
        }
        resetButtons();
        enableBoard(true);
        updateTurnDisplayText();
    }

    private void resetButtons(){
        row0_column0.setText("");
        row0_column1.setText("");
        row0_column2.setText("");
        row1_column0.setText("");
        row1_column1.setText("");
        row1_column2.setText("");
        row2_column0.setText("");
        row2_column1.setText("");
        row2_column2.setText("");

        restartGame.setText(String.format(Locale.ENGLISH, "%s", "Restart"));

        gameResult.setText(String.format(Locale.ENGLISH, "%s", "Game Result"));

        resetVariables();
    }

    private void resetVariables(){
        currentButton = null;
        currentPlayer = Value.PLAYER_ONE;
        movesCounter = 0;
    }

    private void setListeners(){
        row0_column0.setOnClickListener(this);
        row0_column1.setOnClickListener(this);
        row0_column2.setOnClickListener(this);
        row1_column0.setOnClickListener(this);
        row1_column1.setOnClickListener(this);
        row1_column2.setOnClickListener(this);
        row2_column0.setOnClickListener(this);
        row2_column1.setOnClickListener(this);
        row2_column2.setOnClickListener(this);

        restartGame.setOnClickListener(this);
    }

    private void play(int id){
        movesCounter += 1;
        int rowColumn[] = getRowAndColumnOfButton(id);
        switch (currentPlayer){
            case PLAYER_ONE:
                board[rowColumn[0]][rowColumn[1]] = Value.X;
                updateBlock(Value.X);
                break;
            case PLAYER_TWO:
                board[rowColumn[0]][rowColumn[1]] = Value.ZERO;
                updateBlock(Value.ZERO);
                break;
        }
        if (checkForWinner()){
            declareWinner(false);
            return;
        } else if (movesCounter == 9){
            declareWinner(true);
            return;
        }
        nextPlayer();
    }

    private boolean isEmptyBlock(int[] rowAndColumn){
        return board[rowAndColumn[0]][rowAndColumn[1]].equals(Value.EMPTY);
    }

    private int[] getRowAndColumnOfButton(int id){
        int row = 0, column = 0;

        switch (id){
            case R.id.row0_column0:
                row = 0;
                column = 0;
                break;
            case R.id.row0_column1:
                row = 0;
                column = 1;
                break;
            case R.id.row0_column2:
                row = 0;
                column = 2;
                break;
            case R.id.row1_column0:
                row = 1;
                column = 0;
                break;
            case R.id.row1_column1:
                row = 1;
                column = 1;
                break;
            case R.id.row1_column2:
                row = 1;
                column = 2;
                break;
            case R.id.row2_column0:
                row = 2;
                column = 0;
                break;
            case R.id.row2_column1:
                row = 2;
                column = 1;
                break;
            case R.id.row2_column2:
                row = 2;
                column = 2;
                break;
        }

        return new int[]{row, column};
    }

    private void updateBlock(Value value){
        switch (value){
            case X:
                currentButton.setText(getString(R.string.x_text));
                break;
            case ZERO:
                currentButton.setText(getString(R.string.zero_text));
                break;
        }
    }

    public void Restart(View view) {
        setUpBoard();
    }

    private void updateTurnDisplayText(){
        String currentPlayerString;
        switch (currentPlayer){
            case PLAYER_ONE:
                currentPlayerString = "Player One";
                turnDisplay.setText(String.format(Locale.ENGLISH, "%s turn", currentPlayerString));
                break;
            case PLAYER_TWO:
                currentPlayerString = "Player Two";
                turnDisplay.setText(String.format(Locale.ENGLISH, "%s turn", currentPlayerString));
                break;
            case COMPUTER:
                currentPlayerString = "Computer";
                turnDisplay.setText(String.format(Locale.ENGLISH, "%s turn", currentPlayerString));
                break;
        }
    }

    private void declareWinner(boolean draw){
        if (draw){
            gameResult.setText(String.format(Locale.ENGLISH, "%s", "Draw"));
            restartGame.setText(String.format(Locale.ENGLISH, "%s", "Play again"));
            enableBoard(false);
            return;
        }
        switch (currentPlayer){
            case COMPUTER:
                gameResult.setText(String.format(Locale.ENGLISH, "%s", "Computer wins"));
                break;
            case PLAYER_ONE:
                gameResult.setText(String.format(Locale.ENGLISH, "%s", "Player 1 wins"));
                break;
            case PLAYER_TWO:
                gameResult.setText(String.format(Locale.ENGLISH, "%s", "Player 2 wins"));
                break;
        }
        restartGame.setText(String.format(Locale.ENGLISH, "%s", "Play again"));

        enableBoard(false);
    }

    private void nextPlayer(){
        switch (currentPlayer) {
            case PLAYER_ONE:
                currentPlayer = Value.PLAYER_TWO;
                break;
            case PLAYER_TWO:
                currentPlayer = Value.PLAYER_ONE;
                break;
            case COMPUTER:
                currentPlayer = Value.PLAYER_ONE;
                break;
        }
        updateTurnDisplayText();
    }

    @SuppressWarnings("ConstantConditions")
    private boolean checkForWinner() {
        return (board[1][0] == board[1][1] && board[1][1] == board[1][2] && board[1][0] != Value.EMPTY)
                || (board[0][0] == board[1][0] && board[1][0] == board[2][0] && board[0][0] != Value.EMPTY)
                || (board[0][1] == board[1][1] && board[1][1] == board[2][1] && board[0][1] != Value.EMPTY)
                || (board[0][2] == board[1][2] && board[1][2] == board[2][2] && board[0][2] != Value.EMPTY)
                || (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != Value.EMPTY)
                || (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != Value.EMPTY)
                || (board[0][0] == board[0][1] && board[0][1] == board[0][2] && board[0][2] != Value.EMPTY)
                || (board[2][0] == board[2][1] && board[2][1] == board[2][2] && board[2][0] != Value.EMPTY);

    }

    private void enableBoard(boolean disable){
        row0_column0.setEnabled(disable);
        row0_column1.setEnabled(disable);
        row0_column2.setEnabled(disable);
        row1_column0.setEnabled(disable);
        row1_column1.setEnabled(disable);
        row1_column2.setEnabled(disable);
        row2_column0.setEnabled(disable);
        row2_column1.setEnabled(disable);
        row2_column2.setEnabled(disable);
    }
}
