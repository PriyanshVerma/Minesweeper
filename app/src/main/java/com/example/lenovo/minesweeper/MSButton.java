package com.example.lenovo.minesweeper;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;

public class MSButton extends AppCompatButton{

    boolean NbrToFirst = false;
    private int r, c;
    boolean isFlag = false; ////declared default coz MainActivity mei use krna hai
                            //// isliye private nhi kr skte!
    private int value = 0; // set default for same reasons as above
    boolean isRevealed = false;

    public MSButton(Context context) {
        super(context);
    }

    public void setRowCoord(int r){
        this.r = r;
    }

    public void setColCoord(int c){
        this.c = c;
    }

    public int getRowCoord(){
        return this.r;
    }

    public int getColCoord(){
        return this.c;
    }

    public void setValue(int additionInValue){
        this.value += additionInValue;
    }

    public int getValue() { return this.value; }

    public boolean isFlagged() { return isFlag; }



    /*
    VALUE?
    BOMB?
    yes.
    No - 0, 1, 2, etc;
    FLAG?


     */

//    public boolean isFlag;
//    private int val; //can be BOMB (-1), flag, 0, 1, 2, 3, ...
//    public boolean isBomb;
//
//    //coordinates
//    int i, j;
//
//    public


}


