package com.example.kolkoikrzyzyk;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {


    final int[][][] idArray = {
            {
                    {R.id.radioButton111, R.id.radioButton211, R.id.radioButton311, R.id.radioButton411},
                    {R.id.radioButton121, R.id.radioButton221, R.id.radioButton321, R.id.radioButton421},
                    {R.id.radioButton131, R.id.radioButton231, R.id.radioButton331, R.id.radioButton431},
                    {R.id.radioButton141, R.id.radioButton241, R.id.radioButton341, R.id.radioButton441},
            },
            {
                    {R.id.radioButton112, R.id.radioButton212, R.id.radioButton312, R.id.radioButton412},
                    {R.id.radioButton122, R.id.radioButton222, R.id.radioButton322, R.id.radioButton422},
                    {R.id.radioButton132, R.id.radioButton232, R.id.radioButton332, R.id.radioButton432},
                    {R.id.radioButton142, R.id.radioButton242, R.id.radioButton342, R.id.radioButton442},
            },
            {
                    {R.id.radioButton113, R.id.radioButton213, R.id.radioButton313, R.id.radioButton413},
                    {R.id.radioButton123, R.id.radioButton223, R.id.radioButton323, R.id.radioButton423},
                    {R.id.radioButton133, R.id.radioButton233, R.id.radioButton333, R.id.radioButton433},
                    {R.id.radioButton143, R.id.radioButton243, R.id.radioButton343, R.id.radioButton443},
            },
            {
                    {R.id.radioButton114, R.id.radioButton214, R.id.radioButton314, R.id.radioButton414},
                    {R.id.radioButton124, R.id.radioButton224, R.id.radioButton324, R.id.radioButton424},
                    {R.id.radioButton134, R.id.radioButton234, R.id.radioButton334, R.id.radioButton434},
                    {R.id.radioButton144, R.id.radioButton244, R.id.radioButton344, R.id.radioButton444},
            }
    };
    Button radiobutton[][][] = new Button[10][10][10];
    char macierz[][][] = new char[10][10][10];
    TextView textView;
    Button button;
    boolean wygrana = false;
    int tura = 0;
    private CountDownTimer timer;
    int czas = 0;
    long timerLeft = 600000;

    public void rozpocznijOdliczanie() {
        timer = new CountDownTimer(timerLeft, 3000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerLeft = millisUntilFinished;
                int minutes = (int) timerLeft / 60000;
                textView.setText(" ");
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        button = (Button) findViewById(R.id.button);
        zadeklarujPrzyciski();
        wyczyscPlansze();
        rozpocznijOdliczanie();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zadeklarujPrzyciskResetu();
            }
        });

    }


    public void zadeklarujPrzyciskResetu() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wygrana = false;
                wyczyscPlansze();
                textView.setText("Zresetowano rozgrywke");
                for (int z = 0; z < 4; z++) {
                    for (int y = 0; y < 4; y++) {
                        for (int x = 0; x < 4; x++) {
                            radiobutton[z][y][x].setBackgroundColor(Color.parseColor("#D3D3D3"));

                        }
                    }
                }
            }
        });
    }

    public void zadeklarujPrzyciski() {
        for (int z = 0; z < 4; z++) {
            for (int y = 0; y < 4; y++) {
                for (int x = 0; x < 4; x++) {
                    radiobutton[z][y][x] = (Button) findViewById(idArray[z][y][x]);
                    radiobutton[z][y][x].setBackgroundColor(Color.parseColor("#D3D3D3"));
                }
            }
        }
    }

    public void wyczyscPlansze() {
        for (int z = 0; z < 4; z++) {
            for (int y = 0; y < 4; y++) {
                for (int x = 0; x < 4; x++) {
                    macierz[z][y][x] = ' ';
                }
            }
        }
    }

    public void sprawdzCzyWygrana(char znak) {
        for (int z = 0; z < 4; z++) {
            for (int y = 0; y < 4; y++) {
                for (int x = 0; x < 4; x++) {
                    if (((macierz[z][y][x] == znak) && (macierz[z][y][x + 1] == znak) &&
                            (macierz[z][y][x + 2] == znak) && (macierz[z][y][x + 3] == znak)) ||
                            ((macierz[z][y][x] == znak) && (macierz[z][y + 1][x] == znak) &&
                                    (macierz[z][y + 2][x] == znak) && (macierz[z][y + 3][x] == znak)) ||
                            ((macierz[z][y][x] == znak) && (macierz[z + 1][y][x] == znak) &&
                                    (macierz[z + 2][y][x] == znak) && (macierz[z + 3][y][x] == znak)) ||
                            (((macierz[z][y][x] == znak) && (macierz[z + 1][y + 1][x + 1] == znak) &&
                                    (macierz[z + 2][y + 2][x + 2] == znak) && (macierz[z + 3][y + 3][x + 3] == znak))) ||
                            (((macierz[z][y][x + 3] == znak) && (macierz[z + 1][y + 1][x + 2] == znak) &&
                                    (macierz[z + 2][y + 2][x + 1] == znak) && (macierz[z + 3][y + 3][x] == znak))) ||
                            (((macierz[z][y + 3][x] == znak) && (macierz[z + 1][y + 2][x + 1] == znak) &&
                                    (macierz[z + 2][y + 1][x + 2] == znak) && (macierz[z + 3][y][x + 3] == znak))) ||
                            (((macierz[z + 3][y][x] == znak) && (macierz[z + 2][y + 1][x + 1] == znak) &&
                                    (macierz[z + 1][y + 2][x + 2] == znak) && (macierz[z][y + 3][x + 3] == znak)))) {
                        wygrana = true;
                        textView.setText("wygrales " + znak);
                    }

                }
            }
        }
    }


    public void doSomething(View v) {
        if (wygrana == false) {
            for (int z = 0; z < 4; z++) {
                for (int y = 0; y < 4; y++) {
                    for (int x = 0; x < 4; x++) {
                        if (v.getId() == idArray[x][y][z]) {

                            if (macierz[x][y][z] == ' ') {
                                if (tura % 3 == 0) {
                                    radiobutton[x][y][z].setBackgroundColor(Color.parseColor("#FF0000"));
                                    macierz[x][y][z] = 'R';
                                    textView.setText("Gracz R wykonal ruch na polu " + (x + 1) + (y + 1) + (z + 1));
                                    sprawdzCzyWygrana('R');
                                } else if (tura % 3 == 1) {
                                    radiobutton[x][y][z].setBackgroundColor(Color.parseColor("#00FF00"));
                                    macierz[x][y][z] = 'G';
                                    textView.setText("Gracz G wykonal ruch na polu " + (x + 1) + (y + 1) + (z + 1));
                                    sprawdzCzyWygrana('G');
                                } else if (tura % 3 == 2) {
                                    radiobutton[x][y][z].setBackgroundColor(Color.parseColor("#0000FF"));
                                    macierz[x][y][z] = 'B';
                                    textView.setText("Gracz B wykonal ruch na polu " + (x + 1) + (y + 1) + (z + 1));
                                    sprawdzCzyWygrana('B');
                                }
                                tura++;
                            } else if (macierz[x][y][z] != ' ') {
                                textView.setText("pole zajete " + (x + 1) + (y + 1) + (z + 1));
                            }
                            czas = 0;
                        }
                    }
                }
            }
        } else {
            for (int z = 0; z < 4; z++) {
                for (int y = 0; y < 4; y++) {
                    for (int x = 0; x < 4; x++) {
                        if (v.getId() == idArray[x][y][z]) {
                                textView.setText("wygrales");
                        }
                    }
                }
            }

        }
    }
}
