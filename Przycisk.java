package com.example.kolkoikrzyzyk;

import android.graphics.Color;
import android.view.View;
import android.widget.RadioButton;
import android.content.Context;
class Przycisk extends RadioButton {
    char[][][] macierz = new char[20][20][20];
    int x=0;
    int y=0;
    int z=0;

    public Przycisk(Context c, int x, int y, int z) {
        super(c);
        setOnClickListener(new NewClick());

    }
    public class NewClick implements View.OnClickListener{
        @Override
        public void onClick(View v){
            setBackgroundColor(Color.parseColor("#0000FF"));
            macierz[z][y][x]='R';

        }
    }
}
