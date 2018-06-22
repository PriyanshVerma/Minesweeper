package com.example.lenovo.minesweeper;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;

public class MSButton extends AppCompatButton{

    private int value;
    public MSButton(Context context) {
        super(context);
    }

    public void setValue(int value){
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
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


