package com.example.lenovo.minesweeper;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener, View.OnLongClickListener{

    LinearLayout rootLayout;

    public int size = 9;
    public int noOfMines = 10;

    public ArrayList<LinearLayout> rows;
    public MSButton [][] board;

    public static final int INCOMPLETE = 1;
    public static final int COMPLETE_WON = 2;
    public static final int COMPLETE_LOST = 3;
    public int currentStatus;

    public static final int MINE = -1;

    int checkFirstClick;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rootLayout = findViewById(R.id.rootLayout);

        setupBoard();

        checkFirstClick = 0;

//        setMines();
        //this is now called in onClick, after first click

//        setNeighbors();
        //this is now called in setMines, after all mines are set
    }

    /* MENU OPTIONS-
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.resetItem){
            setupBoard();
        }
        else if (id == R.id.easy){
            size = 9;
            noOfMines = 10;
            setupBoard();
        }
        else if (id == R.id.difficult){
            size = 16;
            noOfMines = 40;
            setupBoard();
        }
        return super.onOptionsItemSelected(item);
    }
    */

    public void setupBoard(){
        currentStatus = INCOMPLETE;
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

                button.setBackgroundResource(R.drawable.blackbordered);
            }
        }
    }

    private void setMines() {

        noOfMines = 10;
        while (noOfMines > 0){
            Random random = new Random();
            int i = random.nextInt(size);

            Random random1 = new Random();
            int j = random1.nextInt((size - 1) - 0 + 1);

            //set mine on (i, j)
            MSButton button = board[i][j];

            if (button.getValue() != MINE && !(button.NbrToFirst)) {
                button.setValue(MINE);
                noOfMines--;
            }
        }

        setNeighbors();
    }
    //all mines have been set now


    ///// new comment

    public void setNeighbors(){
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                MSButton button = board[i][j];
                if (button.getValue() == MINE){
                    //Now check neighbors

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
            the board   the mines   the neighboring cell values
            ALL have been set so far
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
                button.setBackgroundResource(R.drawable.blackbordered);

            }

            else {//button.isFlagged() = false
                button.isFlag = true;
                //button.setText("F");
                //button.setTextColor(getApplication().getResources().getColor(R.color.red));
                //button.setTextSize(50);
                button.setBackgroundResource(R.drawable.flag);
            }
        }

        return true;
    }


    //HANDLE onClick
    @Override
    public void onClick(View view) {
        MSButton button = (MSButton) view;

        if (currentStatus == INCOMPLETE) {

            if (checkFirstClick == 0) {

                setZeroOnFirstClick(button);

                checkFirstClick++;
                //NOW set mines
                setMines();
                revealValuesOnClick(button);
            }

            else {
                revealValuesOnClick(button);
            }
        }

        //first check if game is over and won
        boolean check = checkGameCompleteWon();

        if (check){
            currentStatus = COMPLETE_WON;
            Toast.makeText(this, "Congrats!\n  You win", Toast.LENGTH_LONG).show();
        }
    }

    private boolean checkGameCompleteWon() {
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size;j ++){
                MSButton button = board[i][j];
                if (button.getValue() != MINE && !(button.isRevealed)){
                    return false;
                }
            }
        }
      return true;
    }

    private void setZeroOnFirstClick(MSButton button) {
        //catch co-ordinates of current button
        int rowCoord = button.getRowCoord();
        int colCoord = button.getColCoord();

        int [] x = {-1, -1, -1, 0, 0, 1, 1, 1, 0};
        int [] y = {-1, 0, 1, -1, 1, -1, 0, 1, 0};  //(0,0) handles corrdinates of first click btn.
                                                    //It also shudnt have a mine!

        for (int k = 0; k < 9; k++) {

            int I = rowCoord + x[k];
            int J = colCoord + y[k];

            //check border conditions
            if (I >= 0 && I < size && J >= 0 && J < size) {
                MSButton newButton = board[I][J];
                newButton.NbrToFirst = true;
            }
        }
    }

    private void revealValuesOnClick(MSButton button) {

        if (button.isFlagged()){
            Toast.makeText(this, "Press longer to remove flag", Toast.LENGTH_SHORT).show();
        }


        // NOW the MAIN LOGIC
        else if (!(button.isFlagged()) && !(button.isRevealed))
        {
            if (button.getValue() == MINE) {
                button.isRevealed = true;
                displayAllMines();
                Toast.makeText(this, "GAME OVER! \nTry next time ", Toast.LENGTH_LONG).show();
                currentStatus = COMPLETE_LOST;
            }

            else if (button.getValue() > 0){
                button.setText(button.getValue() + "");
                button.setTextSize(30);
                button.setBackgroundResource(R.drawable.white2);
                button.isRevealed = true;
                button.setEnabled(false);
            }

            else if (button.getValue() == 0){
                button.setText("");
                button.setBackgroundResource(R.drawable.white2);
                button.isRevealed = true;
                button.setEnabled(false);

                //catch co-ordinates of current button
                int rowCoord = button.getRowCoord();
                int colCoord = button.getColCoord();


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
                            //let it stay shut
                        }

                        else if (newButton.getValue() >= 0){

                            revealValuesOnClick(newButton);
                            //Recursive Call!
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
                    button.setText("");
                    button.setBackgroundResource(R.drawable.mine2);
                }
            }
        }
    }

}
