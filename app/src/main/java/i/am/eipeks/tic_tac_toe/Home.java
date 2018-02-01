package i.am.eipeks.tic_tac_toe;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Home extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    private enum Value {
        ZERO, X, EMPTY, PLAYER_ONE, PLAYER_TWO, COMPUTER, IS_CHECKED
    }

    private Value[][] board;
    private int[] firstButtonSelected, secondButtonSelected;
    private boolean hasSelectedAButtonBefore, isDontShowChecked;
    private String textToPass;

    private Value valueToPass, currentPlayer;

    private Button fromButton, toButton;

    private AlertDialog dialog;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

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
    private CheckBox warningCheck;

    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        setUpSharedPreferences();

        setUpBoard();

        warningCheck = view.findViewById(R.id.check_box);

        setListeners();
    }

    @Override
    public void onClick(View v) {

        if (!hasSelectedAButtonBefore){
            Toast.makeText(this, "Long press to select block.", Toast.LENGTH_SHORT).show();
            return;
        }

        toButton = findViewById(v.getId());
        if (hasSelectedAButtonBefore && !isValidMove(firstButtonSelected, getRowAndColumnOfButton(v.getId()))){
            Toast.makeText(this, "Invalid move. Try again", Toast.LENGTH_SHORT).show();
        } else {
            toButton = findViewById(v.getId());
            secondButtonSelected = getRowAndColumnOfButton(v.getId());
            if (preferences.getBoolean(Value.IS_CHECKED.toString(), true)){
                showDialog();
            } else {
                play(firstButtonSelected, secondButtonSelected);
            }
        }
    }

    @Override
    public boolean onLongClick(View v) {

        if (isEmptyBlock(getRowAndColumnOfButton(v.getId()))){
            Toast.makeText(this, "Cannot select empty block. Try again", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (hasSelectedAButtonBefore){
            if (fromButton.getId() == v.getId()){
                return true;
            }
            hasSelectedAButtonBefore = false;
        } else {
            firstButtonSelected = getRowAndColumnOfButton(v.getId());
        }

        switch (v.getId()){
            case R.id.row0_column0:
                if (!hasSelectedAButtonBefore){
                    row0_column0.setSelected(true);
                    hasSelectedAButtonBefore = true;
                    if (fromButton != null){
                        fromButton.setSelected(false);
                    }
                }
                break;
            case R.id.row0_column1:
                if (!hasSelectedAButtonBefore){
                    row0_column1.setSelected(true);
                    hasSelectedAButtonBefore = true;
                    if (fromButton != null){
                        fromButton.setSelected(false);
                    }
                }
                break;
            case R.id.row0_column2:
                if (!hasSelectedAButtonBefore){
                    row0_column2.setSelected(true);
                    hasSelectedAButtonBefore = true;
                    if (fromButton != null){
                        fromButton.setSelected(false);
                    }
                }
                break;
            case R.id.row1_column0:
                if (!hasSelectedAButtonBefore){
                    row1_column0.setSelected(true);
                    hasSelectedAButtonBefore = true;
                    if (fromButton != null){
                        fromButton.setSelected(false);
                    }
                }
                break;
            case R.id.row1_column1:
                if (!hasSelectedAButtonBefore){
                    row1_column1.setSelected(true);
                    hasSelectedAButtonBefore = true;
                    if (fromButton != null){
                        fromButton.setSelected(false);
                    }
                }
                break;
            case R.id.row1_column2:
                if (!hasSelectedAButtonBefore){
                    row1_column2.setSelected(true);
                    hasSelectedAButtonBefore = true;
                    if (fromButton != null){
                        fromButton.setSelected(false);
                    }
                }
                break;
            case R.id.row2_column0:
                if (!hasSelectedAButtonBefore){
                    row2_column0.setSelected(true);
                    hasSelectedAButtonBefore = true;
                    if (fromButton != null){
                        fromButton.setSelected(false);
                    }
                }
                break;
            case R.id.row2_column1:
                if (!hasSelectedAButtonBefore){
                    row2_column1.setSelected(true);
                    hasSelectedAButtonBefore = true;
                    if (fromButton != null){
                        fromButton.setSelected(false);
                    }
                }
                break;
            case R.id.row2_column2:
                if (!hasSelectedAButtonBefore){
                    row2_column2.setSelected(true);
                    hasSelectedAButtonBefore = true;
                    if (fromButton != null){
                        fromButton.setSelected(false);
                    }
                }
                break;
        }

        fromButton = findViewById(v.getId());

        return true;
    }

    private void setUpSharedPreferences(){
        preferences = getSharedPreferences("TicTacToe", MODE_PRIVATE);
        editor = preferences.edit();
        editor.apply();
    }

    private void setUpBoard(){
        board = new Value[3][3];
        for (int row = 0; row < board.length; row++){
            for (int column = 0; column < board[row].length; column++){
                if (row == 0){
                    board[row][column] = Value.X;
                } else if (row == 2){
                    board[row][column] = Value.ZERO;
                } else {
                    board[row][column] = Value.EMPTY;
                }
            }
        }

        view = LayoutInflater.from(this).inflate(R.layout.play_dialog, null);
        ButterKnife.bind(view);

        currentPlayer = Value.PLAYER_ONE;
        resetButtons();
    }

    private void resetButtons(){
        row0_column0.setText(getResources().getString(R.string.x_text));
        row0_column1.setText(getResources().getString(R.string.x_text));
        row0_column2.setText(getResources().getString(R.string.x_text));
        row1_column0.setText("");
        row1_column1.setText("");
        row1_column2.setText("");
        row2_column0.setText(getResources().getString(R.string.zero_text));
        row2_column1.setText(getResources().getString(R.string.zero_text));
        row2_column2.setText(getResources().getString(R.string.zero_text));

        resetVariables();
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

        row0_column0.setOnLongClickListener(this);
        row0_column1.setOnLongClickListener(this);
        row0_column2.setOnLongClickListener(this);
        row1_column0.setOnLongClickListener(this);
        row1_column1.setOnLongClickListener(this);
        row1_column2.setOnLongClickListener(this);
        row2_column0.setOnLongClickListener(this);
        row2_column1.setOnLongClickListener(this);
        row2_column2.setOnLongClickListener(this);

        warningCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isDontShowChecked = isChecked;
            }
        });
    }

    private void showDialog(){
        dialog = new AlertDialog.Builder(this)
                .setView(view).setCancelable(false).setTitle("Play")
                .setPositiveButton("PLAY", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        play(firstButtonSelected, secondButtonSelected);
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }

    private void play(final int[] from, final int[] to){
        valueToPass = board[from[0]][from[1]];
        board[from[0]][from[1]] = Value.EMPTY;
        board[to[0]][to[1]] = valueToPass;
        if (valueToPass.equals(Value.X)){
            textToPass = getString(R.string.x_text);
        } else {
            textToPass = getString(R.string.zero_text);
        }

        editor.putBoolean(Value.IS_CHECKED.toString(), isDontShowChecked);
        editor.apply();

        updateBlock();

        resetVariables();
    }

    private boolean isEmptyBlock(int[] rowAndColumn){
        return board[rowAndColumn[0]][rowAndColumn[1]].equals(Value.EMPTY);
    }

    private boolean isNextDiagonal(int[] from, int[] to){
        return (Math.abs(from[0] - to[0]) == 1 && Math.abs(from[1] - to[1]) == 1);
    }

    private boolean isValidMove(int[] from, int[] to){
        if (!isEmptyBlock(to)){
            return false;
        } else if (from[0] == to[0]){
            return Math.abs(from[1] - to[1]) <= 1;
        } else if (from[1] == to[1]){
            return Math.abs(from[0] - to[0]) <= 1;
        } else if (Math.abs(from[0] - to[0]) > 1 || Math.abs(from[1] - to[1]) > 1) {
            return false;
        } else if (isNextDiagonal(from, to)){
            return true;
        }
        return false;
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

    private void updateBlock(){
        fromButton.setText("");
        toButton.setText(textToPass);
    }

    private void resetVariables(){
        hasSelectedAButtonBefore = false;

        if (fromButton != null && toButton != null){
            fromButton.setSelected(false);
            toButton.setSelected(false);
        }

        fromButton = null;
        toButton = null;

        firstButtonSelected = null;
        secondButtonSelected = null;
    }

    public void Restart(View view) {
        setUpBoard();
    }

    private void nextPlayer(){
        if (currentPlayer.equals(Value.PLAYER_ONE)){

        }
    }
}
