package com.example.kolkoikrzyzyk;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.Random;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView textView = (TextView) findViewById(R.id.textView);
          final int[][][] idArray = {
                {
                        {R.id.radioButton111, R.id.radioButton211,R.id.radioButton311,R.id.radioButton411},
                        {R.id.radioButton121, R.id.radioButton221,R.id.radioButton321,R.id.radioButton421},
                        {R.id.radioButton131, R.id.radioButton231,R.id.radioButton331,R.id.radioButton441},
                        {R.id.radioButton141, R.id.radioButton241,R.id.radioButton341,R.id.radioButton441},
                },
                {
                        {R.id.radioButton112, R.id.radioButton212,R.id.radioButton312,R.id.radioButton412},
                        {R.id.radioButton122, R.id.radioButton222,R.id.radioButton322,R.id.radioButton422},
                        {R.id.radioButton132, R.id.radioButton232,R.id.radioButton332,R.id.radioButton442},
                        {R.id.radioButton142, R.id.radioButton242,R.id.radioButton342,R.id.radioButton442},
                },
                {
                        {R.id.radioButton113, R.id.radioButton213,R.id.radioButton313,R.id.radioButton413},
                        {R.id.radioButton123, R.id.radioButton223,R.id.radioButton323,R.id.radioButton423},
                        {R.id.radioButton133, R.id.radioButton233,R.id.radioButton333,R.id.radioButton433},
                        {R.id.radioButton143, R.id.radioButton243,R.id.radioButton343,R.id.radioButton443},
                },
                {
                        {R.id.radioButton114, R.id.radioButton214,R.id.radioButton314,R.id.radioButton414},
                        {R.id.radioButton124, R.id.radioButton224,R.id.radioButton324,R.id.radioButton424},
                        {R.id.radioButton134, R.id.radioButton234,R.id.radioButton334,R.id.radioButton434},
                        {R.id.radioButton144, R.id.radioButton244,R.id.radioButton344,R.id.radioButton444},
                }
        };
        for(int z=0; z<4; z++){
            for(int y=0; y<4; y++){
                for(int x=0; x<4; x++){
                    Przycisk przycisk[][][]=new Przycisk[z][y][x];
                    przycisk[z][y][x]=  (Przycisk) findViewById(idArray[z][y][x]);
                }
            }
        }

    }
}
