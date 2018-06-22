package com.example.lenovo.minesweeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {

    LinearLayout rootLayout;

    public int size = 3;

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

                LinearLayout row = rows.get(i);
                row.addView(button);

                board[i][j] = button;
            }
        }
    }

    private void setMines() {
        int count = 4*size;
        while (count > 0){
            Random random = new Random();
            int i = random.nextInt((size - 1) - 0 + 1);

            Random random1 = new Random();
            int j = random1.nextInt((size - 1) - 0 + 1);

            //set mine on (i, j)
            MSButton button = board[i][j];
            button.setValue(MINE);

            count--;
        }
    }/////all mines have been set now


    @Override
    public void onClick(View view) {
        if (currentStatus == INCOMPLETE){

            MSButton button = (MSButton) view;
            int value = button.getValue();

            if (value == MINE){
                currentStatus = COMPLETE_LOST;
                displayAllMines();
            }

            else {
                checkGameStatus();

            }
        }
    }

    private void displayAllMines() {
        //display all MINE BUTTONS
    }

    private void checkGameStatus() {

    }


}
