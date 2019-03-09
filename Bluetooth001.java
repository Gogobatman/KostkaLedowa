package com.example.bluetooth001;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Set;


public class MainActivity extends AppCompatActivity {
    BluetoothAdapter myBluetoothAdapter;
    int requestCodeForEnable=1;
    Button bt_on;
    Button bt_off;
    Button bt;
    TextView textView;
    ListView listView;
    Intent enableBluetoothIntent;
    CountDownTimer timer;
    ImageView imageView;
    long timerLeft = 600000;
    String[] nazwyUrzadzen = new String[5];
    int numerUrzadzenia=0;

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
        bt_on = (Button)  findViewById(R.id.bt_on);
        bt_off = (Button)  findViewById(R.id.bt_off);
        bt = (Button)  findViewById(R.id.bt);
        textView = (TextView)  findViewById(R.id.textView);
        listView = (ListView)  findViewById(R.id.listView);
        imageView = (ImageView) findViewById(R.id.imageView);
        myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        bluetoothON();
        bluetoothOFF();
        rozpocznijOdliczanie();
        pokazListe();
        obrazek();

    }
    private void obrazek(){
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setImageResource(R.drawable.bluetoothoff);
            }
        });
    }
    private void bluetoothON(){
        bt_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (myBluetoothAdapter == null) {
                    textView.setText("This device doesn't support bluetooth");
                } else {
                    if (!myBluetoothAdapter.isEnabled()) {

                        startActivityForResult(enableBluetoothIntent, requestCodeForEnable);
                        textView.setText("turning on the bluetooth");
                        nazwyUrzadzen[numerUrzadzenia]=myBluetoothAdapter.getName();
                        numerUrzadzenia++;

                    }
                }


            }
        });
    }
    private void bluetoothOFF(){
        bt_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myBluetoothAdapter == null) {
                    textView.setText("This device doesn't support bluetooth");
                } else {
                    if (myBluetoothAdapter.isEnabled()) {
                        myBluetoothAdapter.disable();
                        textView.setText("turning off the bluetooth");
                    }
                }
            }
        });
    }
    private void pokazListe(){
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("Urzadzenia to \n" );
                for(int i=0; i<numerUrzadzenia; i++){
                    textView.setText(nazwyUrzadzen[i]);
                }


            }
        });
    }
}
