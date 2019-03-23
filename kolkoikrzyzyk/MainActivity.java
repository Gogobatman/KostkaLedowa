
package com.example.kolkoikrzyzyk;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{


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
    static Button radiobutton[][][] = new Button[10][10][10];
    char macierz[][][] = new char[10][10][10];
    TextView textView;
    Button button;
    Button discover;
    ImageView imageView;
    Button connect;
    Button DE;

    String nazwaUrzadzenia1="";
    String nazwaUrzadzenia2="";
    Button doNazwy;
    boolean wygrana = false;
    int tura = 0;
    int kolor=Color.RED;
    char litera='R';
    private CountDownTimer timer;
    int czas = 0;
    long timerLeft = 600000;

    boolean polaczono=false;
    BluetoothConnectionService mBluetoothConnection;
    private static final UUID MY_UUID_INSECURE = UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");
    BluetoothAdapter bluetoothAdapter;
    ListView lvNewDevices;
    BluetoothDevice mBTDevice;
    public ArrayList<BluetoothDevice> mBTDevices = new ArrayList<>();
    public DeviceListAdapter mDeviceListAdapter;
    StringBuilder messages;


    private final BroadcastReceiver mBroadcastReceiver1 = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (action.equals(bluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, bluetoothAdapter.ERROR);

                switch(state){
                    case BluetoothAdapter.STATE_OFF:
                        textView.setText("onReceive: STATE OFF");
                        imageView.setImageResource(R.drawable.bluetoothoff);
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        textView.setText("mBroadcastReceiver1: STATE TURNING OFF");
                        imageView.setImageResource(R.drawable.bluetooth);
                        break;
                    case BluetoothAdapter.STATE_ON:
                        textView.setText("mBroadcastReceiver1: STATE ON");
                        imageView.setImageResource(R.drawable.bluetoothon);
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        textView.setText("mBroadcastReceiver1: STATE TURNING ON");
                        imageView.setImageResource(R.drawable.bluetooth);
                        break;
                }
            }
        }
    };
    private final BroadcastReceiver mBroadcastReceiver2 = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED)) {

                int mode = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE, BluetoothAdapter.ERROR);

                switch (mode) {
                    //Device is in Discoverable Mode
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE:
                        textView.setText("mBroadcastReceiver2: Discoverability Enabled.");
                        break;
                    //Device not in discoverable mode
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE:
                        textView.setText("mBroadcastReceiver2: Discoverability Disabled. Able to receive connections.");
                        break;
                    case BluetoothAdapter.SCAN_MODE_NONE:
                        textView.setText("mBroadcastReceiver2: Discoverability Disabled. Not able to receive connections.");
                        break;
                    case BluetoothAdapter.STATE_CONNECTING:
                        textView.setText("mBroadcastReceiver2: Connecting....");
                        break;
                    case BluetoothAdapter.STATE_CONNECTED:
                        textView.setText("mBroadcastReceiver2: Connected.");
                        break;
                }

            }
        }
    };
    private BroadcastReceiver mBroadcastReceiver3 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            textView.setText("onReceive: ACTION FOUND.");

            if (action.equals(BluetoothDevice.ACTION_FOUND)){
                BluetoothDevice device = intent.getParcelableExtra (BluetoothDevice.EXTRA_DEVICE);
                mBTDevices.add(device);
                textView.setText("onReceive: " + device.getName() + ": " + device.getAddress());
                mDeviceListAdapter = new DeviceListAdapter(context, R.layout.device_adapter_view, mBTDevices);
                lvNewDevices.setAdapter(mDeviceListAdapter);
            }
        }
    };
    private final BroadcastReceiver mBroadcastReceiver4 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if(action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)){
                BluetoothDevice mDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                //3 cases:
                //case1: bonded already
                if (mDevice.getBondState() == BluetoothDevice.BOND_BONDED){
                    textView.setText("BroadcastReceiver: BOND_BONDED.");
                    //inside BroadcastReceiver4
                    mBTDevice = mDevice;
                }
                //case2: creating a bone
                if (mDevice.getBondState() == BluetoothDevice.BOND_BONDING) {
                    textView.setText("BroadcastReceiver: BOND_BONDING.");
                }
                //case3: breaking a bond
                if (mDevice.getBondState() == BluetoothDevice.BOND_NONE) {
                    textView.setText("BroadcastReceiver: BOND_NONE.");
                }
            }
        }
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver1);
        unregisterReceiver(mBroadcastReceiver2);
        unregisterReceiver(mBroadcastReceiver3);
        unregisterReceiver(mBroadcastReceiver4);
    }

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
    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //messages=new StringBuilder(); //DZIEKI TEMU NIE MASZ CALY CZAS DODAWANEGO!!!1
            String text= intent.getStringExtra("the message"); //ten pobiera jeden ciag znakow, ktory mozna dodawac do siebie w StringBuilderze
            //messages.append(text);
            textView.setText(text);
            //int numer = text.length();
            if(text.equals("reset")){
                reset();
                tura++; //mała latka do kodu ktorej nie rozumiem ale dziala
            }
            else if(tura%2==0){
                kolor = Color.RED;
                litera = 'R';
            }
            else if(tura%2==1){
                kolor = Color.GREEN;
                litera = 'G';
            }
            else if (text.equals("Xiaomi")) {
                nazwaUrzadzenia2="Xiaomi";
                textView.setText(nazwaUrzadzenia1 + nazwaUrzadzenia2);  //nie mam pojecia czemu nie czyta nazwy urzadzenia
            }
            else if (text.equals("HUAWEI")) {
                nazwaUrzadzenia2="HUAWEI";
                textView.setText(nazwaUrzadzenia1 + nazwaUrzadzenia2);
            }
            if(text.equals("000")){ //wymiar xyz
                radiobutton[0][0][0].setBackgroundColor(kolor); //wymiary zyx
                macierz[0][0][0]=litera;
            }else if(text.equals("001")){
                radiobutton[1][0][0].setBackgroundColor(kolor);
                macierz[1][0][0]=litera;
            }else if(text.equals("002")) {
                radiobutton[2][0][0].setBackgroundColor(kolor);
                macierz[2][0][0] = litera;
            }else if(text.equals("003")) {
                radiobutton[3][0][0].setBackgroundColor(kolor);
                macierz[3][0][0] = litera;
            }

            else if(text.equals("010")){ //wymiar xyz
                radiobutton[0][1][0].setBackgroundColor(kolor); //wymiary zyx
                macierz[0][1][0]=litera;
            }else if(text.equals("011")){
                radiobutton[1][1][0].setBackgroundColor(kolor);
                macierz[1][1][0]=litera;
            }else if(text.equals("012")) {
                radiobutton[2][1][0].setBackgroundColor(kolor);
                macierz[2][1][0] = litera;
            }else if(text.equals("013")) {
                radiobutton[3][1][0].setBackgroundColor(kolor);
                macierz[3][1][0] = litera;
            }

            else if(text.equals("020")){ //wymiar xyz
                radiobutton[0][2][0].setBackgroundColor(kolor); //wymiary zyx
                macierz[0][2][0]=litera;
            }else if(text.equals("021")){
                radiobutton[1][2][0].setBackgroundColor(kolor);
                macierz[1][2][0]=litera;
            }else if(text.equals("022")) {
                radiobutton[2][2][0].setBackgroundColor(kolor);
                macierz[2][2][0] = litera;
            }else if(text.equals("023")) {
                radiobutton[3][2][0].setBackgroundColor(kolor);
                macierz[3][2][0] = litera;
            }

            else if(text.equals("030")){ //wymiar xyz
                radiobutton[0][3][0].setBackgroundColor(kolor); //wymiary zyx
                macierz[0][3][0]=litera;
            }else if(text.equals("031")){
                radiobutton[1][3][0].setBackgroundColor(kolor);
                macierz[1][3][0]=litera;
            }else if(text.equals("032")) {
                radiobutton[2][3][0].setBackgroundColor(kolor);
                macierz[2][3][0] = litera;
            }else if(text.equals("033")) {
                radiobutton[3][3][0].setBackgroundColor(kolor);
                macierz[3][3][0] = litera;
            }



            else if(text.equals("100")){ //wymiar xyz
                radiobutton[0][0][1].setBackgroundColor(kolor); //wymiary zyx
                macierz[0][0][1]=litera;
            }else if(text.equals("101")){
                radiobutton[1][0][1].setBackgroundColor(kolor);
                macierz[1][0][1]=litera;
            }else if(text.equals("102")) {
                radiobutton[2][0][1].setBackgroundColor(kolor);
                macierz[2][0][1] = litera;
            }else if(text.equals("103")) {
                radiobutton[3][0][1].setBackgroundColor(kolor);
                macierz[3][0][1] = litera;
            }

            else if(text.equals("110")){ //wymiar xyz
                radiobutton[0][1][1].setBackgroundColor(kolor); //wymiary zyx
                macierz[0][1][1]=litera;
            }else if(text.equals("111")){
                radiobutton[1][1][1].setBackgroundColor(kolor);
                macierz[1][1][1]=litera;
            }else if(text.equals("112")) {
                radiobutton[2][1][1].setBackgroundColor(kolor);
                macierz[2][1][1] = litera;
            }else if(text.equals("113")) {
                radiobutton[3][1][1].setBackgroundColor(kolor);
                macierz[3][1][1] = litera;
            }

            else if(text.equals("120")){ //wymiar xyz
                radiobutton[0][2][1].setBackgroundColor(kolor); //wymiary zyx
                macierz[0][2][1]=litera;
            }else if(text.equals("121")){
                radiobutton[1][2][1].setBackgroundColor(kolor);
                macierz[1][2][1]=litera;
            }else if(text.equals("122")) {
                radiobutton[2][2][1].setBackgroundColor(kolor);
                macierz[2][2][1] = litera;
            }else if(text.equals("123")) {
                radiobutton[3][2][1].setBackgroundColor(kolor);
                macierz[3][2][1] = litera;
            }

            else if(text.equals("130")){ //wymiar xyz
                radiobutton[0][3][1].setBackgroundColor(kolor); //wymiary zyx
                macierz[0][3][1]=litera;
            }else if(text.equals("131")){
                radiobutton[1][3][1].setBackgroundColor(kolor);
                macierz[1][3][1]=litera;
            }else if(text.equals("132")) {
                radiobutton[2][3][1].setBackgroundColor(kolor);
                macierz[2][3][1] = litera;
            }else if(text.equals("133")) {
                radiobutton[3][3][1].setBackgroundColor(kolor);
                macierz[3][3][1] = litera;
            }


            else if(text.equals("200")){ //wymiar xyz
                radiobutton[0][0][2].setBackgroundColor(kolor); //wymiary zyx
                macierz[0][0][2]=litera;
            }else if(text.equals("201")){
                radiobutton[1][0][2].setBackgroundColor(kolor);
                macierz[1][0][2]=litera;
            }else if(text.equals("202")) {
                radiobutton[2][0][2].setBackgroundColor(kolor);
                macierz[2][0][2] = litera;
            }else if(text.equals("203")) {
                radiobutton[3][0][2].setBackgroundColor(kolor);
                macierz[3][0][2] = litera;
            }

            else if(text.equals("210")){ //wymiar xyz
                radiobutton[0][1][2].setBackgroundColor(kolor); //wymiary zyx
                macierz[0][1][2]=litera;
            }else if(text.equals("211")){
                radiobutton[1][1][2].setBackgroundColor(kolor);
                macierz[1][1][2]=litera;
            }else if(text.equals("212")) {
                radiobutton[2][1][2].setBackgroundColor(kolor);
                macierz[2][1][2] = litera;
            }else if(text.equals("213")) {
                radiobutton[3][1][2].setBackgroundColor(kolor);
                macierz[3][1][2] = litera;
            }

            else if(text.equals("220")){ //wymiar xyz
                radiobutton[0][2][2].setBackgroundColor(kolor); //wymiary zyx
                macierz[0][2][2]=litera;
            }else if(text.equals("221")){
                radiobutton[1][2][2].setBackgroundColor(kolor);
                macierz[1][2][2]=litera;
            }else if(text.equals("222")) {
                radiobutton[2][2][2].setBackgroundColor(kolor);
                macierz[2][2][2] = litera;
            }else if(text.equals("223")) {
                radiobutton[3][2][2].setBackgroundColor(kolor);
                macierz[3][2][2] = litera;
            }

            else if(text.equals("230")){ //wymiar xyz
                radiobutton[0][3][2].setBackgroundColor(kolor); //wymiary zyx
                macierz[0][3][2]=litera;
            }else if(text.equals("231")){
                radiobutton[1][3][2].setBackgroundColor(kolor);
                macierz[1][3][2]=litera;
            }else if(text.equals("232")) {
                radiobutton[2][3][2].setBackgroundColor(kolor);
                macierz[2][3][2] = litera;
            }else if(text.equals("233")) {
                radiobutton[3][3][2].setBackgroundColor(kolor);
                macierz[3][3][2] = litera;
            }

            else if(text.equals("300")){ //wymiar xyz
                radiobutton[0][0][3].setBackgroundColor(kolor); //wymiary zyx
                macierz[0][0][3]=litera;
            }else if(text.equals("301")){
                radiobutton[1][0][3].setBackgroundColor(kolor);
                macierz[1][0][3]=litera;
            }else if(text.equals("302")) {
                radiobutton[2][0][3].setBackgroundColor(kolor);
                macierz[2][0][3] = litera;
            }else if(text.equals("303")) {
                radiobutton[3][0][3].setBackgroundColor(kolor);
                macierz[3][0][3] = litera;
            }

            else if(text.equals("310")){ //wymiar xyz
                radiobutton[0][1][3].setBackgroundColor(kolor); //wymiary zyx
                macierz[0][1][3]=litera;
            }else if(text.equals("311")){
                radiobutton[1][1][3].setBackgroundColor(kolor);
                macierz[1][1][3]=litera;
            }else if(text.equals("312")) {
                radiobutton[2][1][3].setBackgroundColor(kolor);
                macierz[2][1][3] = litera;
            }else if(text.equals("313")) {
                radiobutton[3][1][3].setBackgroundColor(kolor);
                macierz[3][1][3] = litera;
            }

            else if(text.equals("320")){ //wymiar xyz
                radiobutton[0][2][3].setBackgroundColor(kolor); //wymiary zyx
                macierz[0][2][3]=litera;
            }else if(text.equals("321")){
                radiobutton[1][2][3].setBackgroundColor(kolor);
                macierz[1][2][3]=litera;
            }else if(text.equals("322")) {
                radiobutton[2][2][3].setBackgroundColor(kolor);
                macierz[2][2][3] = litera;
            }else if(text.equals("323")) {
                radiobutton[3][2][3].setBackgroundColor(kolor);
                macierz[3][2][3] = litera;
            }

            else if(text.equals("330")){ //wymiar xyz
                radiobutton[0][3][3].setBackgroundColor(kolor); //wymiary zyx
                macierz[0][3][3]=litera;
            }else if(text.equals("331")){
                radiobutton[1][3][3].setBackgroundColor(kolor);
                macierz[1][3][3]=litera;
            }else if(text.equals("332")) {
                radiobutton[2][3][3].setBackgroundColor(kolor);
                macierz[2][3][3] = litera;
            }else if(text.equals("333")) {
                radiobutton[3][3][3].setBackgroundColor(kolor);
                macierz[3][3][3] = litera;
            }
            sprawdzCzyWygrana('G');
            sprawdzCzyWygrana('R');
            tura++;

        }
    };
    public void startConnection(){
        startBTConnection(mBTDevice,MY_UUID_INSECURE);
    }

    /**
     * starting chat service method
     */
    public String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return model;
        } else {
            return manufacturer;
        }
    }
    public void startBTConnection(BluetoothDevice device, UUID uuid){
        textView.setText("startBTConnection: Initializing RFCOM Bluetooth Connection.");
        mBluetoothConnection.startClient(device,uuid);
        polaczono=true;
    }
    public void enableDisableBT(){
        if(bluetoothAdapter == null){
            textView.setText("enableDisableBT: Does not have BT capabilities.");
        }
        if(!bluetoothAdapter.isEnabled()){
            textView.setText("enableDisableBT: enabling BT.");
            Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableBTIntent);

            IntentFilter BTIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            registerReceiver(mBroadcastReceiver1, BTIntent);
        }
        if(bluetoothAdapter.isEnabled()){
            textView.setText("enableDisableBT: disabling BT.");
            bluetoothAdapter.disable();

            IntentFilter BTIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            registerReceiver(mBroadcastReceiver1, BTIntent);
        }

    }
    public void btnEnableDisable_Discoverable() {
        textView.setText("btnEnableDisable_Discoverable: Making device discoverable for 300 seconds.");

        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(discoverableIntent);

        IntentFilter intentFilter = new IntentFilter(bluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
        registerReceiver(mBroadcastReceiver2,intentFilter);

    }
    public void zadeklarujPrzyciskiRDC() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String string = ("reset");
                byte[] bytes = string.getBytes(Charset.defaultCharset());
                mBluetoothConnection.write(bytes);
                reset();

            }
        });
        discover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnDiscover();
            }
        });
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startConnection();
            }
        });
        DE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnEnableDisable_Discoverable();
            }
        });
        doNazwy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String string = nazwaUrzadzenia1;
                byte[] bytes = string.getBytes(Charset.defaultCharset());
                mBluetoothConnection.write(bytes);
            }
        });
    }
    public void reset(){
        tura=0;
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
    public void btnDiscover() {
        textView.setText("btnDiscover: Looking for unpaired devices.");

        if(bluetoothAdapter.isDiscovering()){
            bluetoothAdapter.cancelDiscovery();
            textView.setText("btnDiscover: Canceling discovery.");

            //check BT permissions in manifest
            checkBTPermissions();

            bluetoothAdapter.startDiscovery();
            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(mBroadcastReceiver3, discoverDevicesIntent);
        }
        if(!bluetoothAdapter.isDiscovering()){

            //check BT permissions in manifest
            checkBTPermissions();

            bluetoothAdapter.startDiscovery();
            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(mBroadcastReceiver3, discoverDevicesIntent);
        }
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
        //tutaj daj funkcjonalnosc przycisku send i wysylaj konkretny text, a nie jak tam zczytywales z EditView, np wyslij "111" i to zapali poszczegolny button
//        byte[] bytes = etSend.getText().toString().getBytes(Charset.defaultCharset());

////        etSend.setText(" ");
        if(polaczono==true){
            if (wygrana == false) {
                for (int z = 0; z < 4; z++) {
                    for (int y = 0; y < 4; y++) {
                        for (int x = 0; x < 4; x++) {
                            if (v.getId() == idArray[x][y][z]) {
                                if (macierz[x][y][z] == ' ') {

                                    if (tura % 2 == 0 && nazwaUrzadzenia1.equals("Xiaomi")) {  //&& nazwaUrzadzenia1.equals("Xiaomi")
                                        radiobutton[x][y][z].setBackgroundColor(Color.parseColor("#FF0000"));
                                        macierz[x][y][z] = 'R';
                                        textView.setText("Gracz R wykonal ruch na polu " + (x + 1) + (y + 1) + (z + 1));
                                        sprawdzCzyWygrana('R');
                                        String temp1=Integer.toString(x);
                                        String temp2=Integer.toString(y);
                                        String temp3=Integer.toString(z);
                                        String string = (temp3+temp2+temp1);
                                        byte[] bytes = string.getBytes(Charset.defaultCharset());
                                        mBluetoothConnection.write(bytes);
                                        tura++;
                                    } else if (tura % 2 == 1 && nazwaUrzadzenia1.equals("HUAWEI")) { //&& nazwaUrzadzenia1.equals("HUAWEI")
                                        radiobutton[x][y][z].setBackgroundColor(Color.parseColor("#00FF00"));
                                        macierz[x][y][z] = 'G';
                                        textView.setText("Gracz G wykonal ruch na polu " + (x + 1) + (y + 1) + (z + 1));
                                        sprawdzCzyWygrana('G');
                                        String temp1=Integer.toString(x);
                                        String temp2=Integer.toString(y);
                                        String temp3=Integer.toString(z);
                                        String string = (temp3+temp2+temp1);
                                        byte[] bytes = string.getBytes(Charset.defaultCharset());
                                        mBluetoothConnection.write(bytes);
                                        tura++;
                                    }
//                                     else if (tura % 3 == 2) {
//                                        radiobutton[x][y][z].setBackgroundColor(Color.parseColor("#0000FF"));
//                                        macierz[x][y][z] = 'B';
//                                        textView.setText("Gracz B wykonal ruch na polu " + (x + 1) + (y + 1) + (z + 1));
//                                        sprawdzCzyWygrana('B');
//                                    }

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
                                textView.setText("koniec gry");
                            }
                        }
                    }
                }

            }
        }
    }
    private void checkBTPermissions() {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            int permissionCheck = this.checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATION");
            permissionCheck += this.checkSelfPermission("Manifest.permission.ACCESS_COARSE_LOCATION");
            if (permissionCheck != 0) {

                this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1001); //Any number
            }
        }else{
            textView.setText("checkBTPermissions: No need to check permissions. SDK version < LOLLIPOP.");
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //first cancel discovery because its very memory intensive.
        bluetoothAdapter.cancelDiscovery();

        textView.setText("onItemClick: You Clicked on a device.");
        String deviceName = mBTDevices.get(i).getName();
        String deviceAddress = mBTDevices.get(i).getAddress();

        textView.setText("onItemClick: deviceName = " + deviceName);
        textView.setText("onItemClick: deviceAddress = " + deviceAddress);

        //create the bond.
        //NOTE: Requires API 17+? I think this is JellyBean
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2){
            textView.setText("Trying to pair with " + deviceName);
            mBTDevices.get(i).createBond();

            mBTDevice = mBTDevices.get(i);
            mBluetoothConnection = new BluetoothConnectionService(MainActivity.this);
            polaczono=true;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        button = (Button) findViewById(R.id.button);
        connect = (Button) findViewById(R.id.connect);
        imageView = (ImageView) findViewById(R.id.imageView);
        lvNewDevices = (ListView) findViewById(R.id.lvNewDevices);
        discover= (Button) findViewById(R.id.discover);
        DE= (Button) findViewById(R.id.DE);
        doNazwy= (Button) findViewById(R.id.doNazwy);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableDisableBT();
            }
        });
        zadeklarujPrzyciski();
        wyczyscPlansze();
        rozpocznijOdliczanie();
        zadeklarujPrzyciskiRDC();
        nazwaUrzadzenia1=getDeviceName();
        messages = new StringBuilder();
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver,new IntentFilter("incoming message"));

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        registerReceiver(mBroadcastReceiver4, filter);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        lvNewDevices.setOnItemClickListener(MainActivity.this);
    }


}
