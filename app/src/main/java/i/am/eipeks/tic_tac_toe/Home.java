package i.am.eipeks.tic_tac_toe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Home extends AppCompatActivity implements View.OnClickListener{

    private enum Value {
        ZERO, ONE
    }

    private String[][] board;

    @BindView(R.id.row0_column0) Button row0_column0;
    @BindView(R.id.row0_column1) Button row0_column1;
    @BindView(R.id.row0_column2) Button row0_column2;
    @BindView(R.id.row1_column0) Button row1_column0;
    @BindView(R.id.row1_column1) Button row1_column1;
    @BindView(R.id.row1_column2) Button row1_column2;
    @BindView(R.id.row2_column0) Button row2_column0;
    @BindView(R.id.row2_column1) Button row2_column1;
    @BindView(R.id.row2_column2) Button row2_column2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        setUpBoard();
    }

    @Override
    public void onClick(View v) {

    }

    private void setUpBoard(){
        board = new String[3][3];
        for (int row = 0; row < board.length; row++){
            if (row == 1){
                continue;
            }
            for (int column = 0; column < board[row].length; column++){
                if (row == 0){
                    board[row][column] = Value.ONE.toString();
                } else {
                    board[row][column] = Value.ZERO.toString();
                }
            }
        }
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
    }

}
