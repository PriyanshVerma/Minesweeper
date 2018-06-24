package com.example.lenovo.minesweeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener, View.OnLongClickListener{

    LinearLayout rootLayout;

    public int size = 4;

    public ArrayList<LinearLayout> rows;
    public MSButton [][] board;

    public static final int INCOMPLETE = 1;
    public static final int COMPLETE_WON = 2;
    public static final int COMPLETE_LOST = 3;
    public int currentStatus;

    public static final int MINE = -1;
//    public static final int ;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rootLayout = findViewById(R.id.rootLayout);

        setupBoard();

        setMines();

        setNeighbors();
    }

    public void setupBoard(){

        rows = new ArrayList<>();
        board = new MSButton[size][size];

        for (int i = 0; i < size; i++){
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1);
            linearLayout.setLayoutParams(layoutParams);

            rootLayout.addView(linearLayout);
            rows.add(linearLayout);
        }

        //setting buttons
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                MSButton button = new MSButton(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
                button.setLayoutParams(layoutParams);

                button.setOnClickListener(this);
                button.setOnLongClickListener(this);

                LinearLayout row = rows.get(i);
                row.addView(button);

                board[i][j] = button;

                button.setRowCoord(i);
                button.setColCoord(j);
            }
        }
    }

    private void setMines() {
        int count = 2*size;
        while (count > 0){
            Random random = new Random();
            int i = random.nextInt(size);

            Random random1 = new Random();
            int j = random1.nextInt((size - 1) - 0 + 1);

            //set mine on (i, j)
            MSButton button = board[i][j];
            button.setValue(MINE);
//          button.setText("-1");

            count--;
        }
    }
    //all mines have been set now


    ///// new comment

    public void setNeighbors(){
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                MSButton button = board[i][j];
                if (button.getValue() == MINE){
                    //we ll check neighbors

                    int [] x = {-1, -1, -1, 0, 0, 1, 1, 1};
                    int [] y = {-1, 0, 1, -1, 1, -1, 0 ,1};

                    for (int k = 0; k < 8; k++){

                        int counter = 0;

                        int I = i + x[k];
                        int J = j + y[k];

                        //check border conditions
                        if (I >= 0 && I < size && J>=0 && J < size) {
                            // now check if (new i, new j) is a mine
                            // if yes do nothing
                            MSButton newButton = board[I][J];
                            if (newButton.getValue() != MINE){
                                counter++;
                                newButton.setValue(counter);

                            }
                        }
                    }
                }
            }
        }
    }
            /*
            YAHAN TAK SABBB SAHIII!
             */


    //HANDLE onLongClick, i.e. flagging part
    @Override
    public boolean onLongClick(View v) {

        if (currentStatus == INCOMPLETE) {
            MSButton button = (MSButton) v;

            //toggle the Flag
            if (button.isFlagged()) {
                button.isFlag = false;
                button.setText("");

            }

            else {//button.isFlagged() = false
                button.isFlag = true;
                button.setText("F");
            }
        }

        return true;
    }


    //HANDLE onClick
    @Override
    public void onClick(View view) {

        if (currentStatus == INCOMPLETE){

            MSButton button = (MSButton) view;

            checkGameStatus(button);
        }
    }

    private void checkGameStatus(MSButton button) {
        if (!(button.isFlagged()) && !(button.isRevealed))
        {
            if (button.getValue() == MINE) {
                button.isRevealed = true;
                displayAllMines();
                Toast.makeText(this, "Game Over!", Toast.LENGTH_LONG).show();
                currentStatus = COMPLETE_LOST;
            }

            else if (button.getValue() > 0){
                button.setText(button.getValue() + "");
                button.isRevealed = true;
            }

            else if (button.getValue() == 0){
                button.setText(".");
                button.isRevealed = true;

                //catch coords of current button
                int rowCoord = button.getRowCoord();
                int colCoord = button.getColCoord();

//                board[rowCoord][colCoord] = button;

                int [] x = {-1, -1, -1, 0, 0, 1, 1, 1};
                int [] y = {-1, 0, 1, -1, 1, -1, 0, 1};

                for (int k = 0; k < 8; k++){

                    int I = rowCoord + x[k];
                    int J = colCoord + y[k];

                    //check border conditions
                    if (I >= 0 && I < size && J>=0 && J < size) {
                        // now check  (new i, new j) has what value

                        MSButton newButton = board[I][J];

                        if (newButton.getValue() == MINE){
                            //do nothing
                            ///let it stay shut
                        }

                        else if (newButton.getValue() >= 0){

                            checkGameStatus(newButton);
                        }
                    }
                }
            }
        }
    }

    private void displayAllMines() {
        //display all MINE BUTTONS
        MSButton button;
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                button = board[i][j];
                if (button.getValue() == MINE) {
                    button.isRevealed = true;
                    button.setText("M");
                }
            }
        }
    }

}
