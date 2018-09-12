package com.example.emergencyecg;


import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.drawheart.CardiographView;
import com.drawheart.PainView;

import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;



public class MainActivity extends AppCompatActivity {

    private static final boolean D = true;

    private static final String NAME = "emergencyecg";

    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private BluetoothAdapter bTAdatper;

    private String TAG = "MainActivity";

    private BluetoothSocket socket;

    final int arrayfinal[] = new int[112];

    final int array2[] = new int[112];

    final int arraydata[] = new int[112];

    final int arraydanqian[] = new int[112];

    final int arraydanhou[] = new int[112];

    final int arraydoubleqian[] = new int[112];

    final int arraydoublehou  [] = new int[112];

    final int array1[] = new int[112];

    private int REQUEST_ENABLE = 1;

    private int backage_flag = 1;

    private PainView painView = null;

    private Handler handler;


    //    搜索蓝牙设备
/**
    private final BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.e("TAG", "onReceive: " + "11111111111111");
//            Log.e("TAG", "onReceive: " + action);
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//                //避免重复添加已经绑定过的设备
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
//                  此处的adapter是列表的adapter，不是BluetoothAdapter
                    Log.e("TAG", "onReceive: " + device.getName());
                    List<String> data = new ArrayList<>();
                    data.add(device.getName());
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                            MainActivity.this, android.R.layout.simple_list_item_1, data);
                    ListView listView = (ListView) findViewById(R.id.blutoothlistView);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    Log.d("TAG", "onReceive: " + data);
                }
            }
            else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                Toast.makeText(MainActivity.this, "开始搜索", Toast.LENGTH_SHORT).show();
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                Toast.makeText(MainActivity.this, "搜索完毕", Toast.LENGTH_SHORT).show();
            }
        }
    };
**/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//      获取蓝牙适配器
        bTAdatper = BluetoothAdapter.getDefaultAdapter();

//      判断设备是否支持蓝牙功能
        if (bTAdatper == null) {
            Toast.makeText(this, "当前设备不支持蓝牙功能", Toast.LENGTH_SHORT).show();
        }

//      打开蓝牙
        if (!bTAdatper.isEnabled()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(intent);
//      不做提示，直接打开蓝牙
//            bTAdatper.enable();
        }




//        while (true){
//            if(painView.getFlag()==0) {
//
//                    Log.d(TAG, "onClick: " + arraydanqian[2]);
//
//                    painView.setPointY(arraydanqian[2]);
//
//
//            }
//        }


//        handler = new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//
//                switch (msg.what) {
//
//                    case 0:
//                        if(painView.getFlag()==1) {
//                            if (backage_flag != 1) {
//
//                            } else if (backage_flag % 2 != 0) {
//
//                                painView.setPointY(arraydanqian[2]);
//
//                                Log.d(TAG, "PointY: " + painView.getPointY());
//
//                            } else if (backage_flag % 2 == 0) {
//                                painView.setPointY(arraydoubleqian[2]);
//
//                                Log.d(TAG, "PointY: " + painView.getPointY());
//
//
//                            }
//                        }
//
//                }
//            }
//        };


        Button batten = findViewById(R.id.scanButton);
        batten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//      判断蓝牙是否打开
                if (!bTAdatper.isEnabled()) {
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intent, REQUEST_ENABLE);
//      不做提示，直接打开蓝牙
//              bTAdatper.enable();

                }

//      开启被其它蓝牙设备发现的功能
//                if (bTAdatper.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
//                    Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
//                    //设置为一直开启
//                    i.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 50);
//                    startActivity(i);
//                }
//                //      开始搜索蓝牙
//                if(bTAdatper.isDiscovering()){
//                    bTAdatper.cancelDiscovery();
//                }
//                bTAdatper.startDiscovery();
//                Toast.makeText(MainActivity.this,"正在搜索附近蓝牙设备......",Toast.LENGTH_LONG);

//      注册广播接收者
//                IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
//      发现设备
//                filter.addAction(BluetoothDevice.ACTION_FOUND);
//      设备连接状态改变
//                filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
//      蓝牙设备状态改变
//                filter.addAction(bTAdatper.ACTION_STATE_CHANGED);
//      注册接收者
//                registerReceiver(receiver, filter);

//                Log.e("TAG", "onClick: "+filter.actionsIterator()  );

//      获取已配对蓝牙
                Set<BluetoothDevice> Bondedlist = bTAdatper.getBondedDevices();
                for (BluetoothDevice mdevice : Bondedlist) {
                    Log.e("TAG", "onClick: " + mdevice.getName()+mdevice.getUuids()+mdevice.getAddress());
                    try {
                        connect(mdevice);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                getDataThread connectThread = new getDataThread();
                connectThread.start();

            }
        });

        final PainView painView = new PainView(MainActivity.this);



        final Button connectBluetooth = findViewById(R.id.ConnectBLE);

        final LinearLayout bigLinearLayout = findViewById(R.id.bigPanel);

//        点击添加心电图波形view

        connectBluetooth.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
//            回调方法
             public void onClick(View view) {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Log.d(TAG, "onCreate: " + arraydanqian[2]);
                            painView.setPointY(arraydanqian[2]);

                        }
                    },1000);


                final CardiographView gd = new CardiographView(MainActivity.this);
                gd.setMinimumWidth(bigLinearLayout.getWidth());
                gd.setMinimumHeight(bigLinearLayout.getHeight());
//                          Log.e("宽", String.valueOf(bigLinearLayout.getWidth()));
//                          Log.e("高", String.valueOf(bigLinearLayout.getHeight()));
//           重绘view组件
                painView.invalidate();

                bigLinearLayout.addView(painView);

            }
        });


    }

     public void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(receiver);
    }


//    连接已配对设备
    public void connect(BluetoothDevice device) throws IOException {

//      获取socket
        socket = device.createInsecureRfcommSocketToServiceRecord(MY_UUID);

        if(socket.isConnected()) {
            return;
        }else{
            socket.connect();
        }
        if(socket.isConnected()){
            Log.e(TAG, "connect: "+"连接成功" );
        }

        }

//      接收信息线程
    class getDataThread extends Thread {

        private BluetoothServerSocket bluetoothServerSocket;

         @Override
        public void run() {

            try {
                InputStream inputStream = socket.getInputStream();
//                OutputStream outputStream = socket.getOutputStream();
                final  byte[] buffer = new byte[112];
                int bytes;

                while(true){
//      读取数据
                    bytes = inputStream.read(buffer);
                    if(bytes > -1) {
                        final byte[] data = new byte[112];

//                        复制到data数组
                            System.arraycopy(buffer, 0, data, 0, bytes);

                            int c = data.length;
//                      将字节数组转换为字符串数字并判断正负
                            for (int j = 0; j < data.length; j++) {
                                array2[j] = data[j];
//                      判断正负修改数据符号问题
                                if (array2[j] < 0) {
                                    array2[j] = 256 + array2[j];
                                }

                            }

//                      判断标识位（55，F0）
                            for (int j = 0; j < data.length-1; j++) {

                                if (array2[j] == 85 && array2[j + 1] == 240) {

                                    if (backage_flag == 1) {
//                              去除第一包开始标志前数据
                                        System.arraycopy(array2, j, arraydanhou, 0, data.length - 1 - j);

                                    } else if (backage_flag % 2 != 0) {
//                              保存单数包标志前后数据
                                        System.arraycopy(array2, 0, arraydanqian, data.length - j, j);
                                        System.arraycopy(array2, j, arraydanhou, 0, data.length - 1 - j);
//                              整合双数包标志后与单数包标志前的数据
                                        System.arraycopy(arraydoublehou, 0, arraydanqian, 0, data.length - 1 - j);

//                                        for (int i = 0; i < arraydanqian.length; i++) {
//                                            Log.e(TAG, "单前: " + arraydanqian[i]);
//                                        }

//                                        Message message = Message.obtain();
//
//                                        message.what = 0;
//                                        handler.sendMessage(message);
//                                        Log.d(TAG, "onClick: " + arraydanqian[2]);



                                    } else if (backage_flag % 2 == 0) {
//                              保存双数包标志前后数据
                                        System.arraycopy(array2, 0, arraydoubleqian, data.length - j, j);
                                        System.arraycopy(array2, j, arraydoublehou, 0, data.length - 1 - j);
//                              整合双数包标志前与单数包标志后的数据
                                        System.arraycopy(arraydanhou, 0, arraydoubleqian, 0, data.length - 1 - j);

//                                        for (int i = 0; i < arraydoubleqian.length; i++) {
//                                            Log.e(TAG, "双后: " + arraydoubleqian[i]);
//                                         }

//                                        Message message = Message.obtain();
//
//                                        message.what = 0;
//                                        handler.sendMessage(message);
//                                        Log.d(TAG, "onClick: " + arraydoubleqian[2]);

//                                        painView.setPointY(arraydoubleqian[2]);
//                                        Log.e(TAG, "run: " + "----------------------------------");
                                    }
                                } else {
                                    if (backage_flag % 2 != 0) {
                                        System.arraycopy(array2, 0, arraydanqian, 0, array2.length);
                                    } else {

                                        System.arraycopy(array2, 0, arraydoubleqian, 0, array2.length);
                                    }
                                }
                            }

//                            Log.e(TAG, "run: " + "数组打印完毕，下一组为：");

                    }
                        backage_flag++;

                   }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void writeFile(int arrayx[]){

        String fileName="C:\\kuka.txt";
                        try
                       {
                                    //使用这个构造函数时，如果存在kuka.txt文件，
                                   //则先把这个文件给删除掉，然后创建新的kuka.txt
                                    FileWriter writer=new FileWriter(fileName);
                           for (int i = 0; i < arraydanqian.length; i++) {
                                  writer.write(arraydanqian[i]);
                            }
                               writer.close();
                           } catch (IOException e)
                      {
                                 e.printStackTrace();
                        }


    }


    private String bytesToHex(final byte[] dataBytes) {
        char temp;
        String str = "";
        for (int n=0; n<dataBytes.length; n++) {
            temp = (char) ((dataBytes[n] & 0xf0) >> 4);
            str += (char)(  temp >= 10? 'A'+(temp-10):'0'+temp);
            temp = (char) ((dataBytes[n] & 0x0f) >> 0);
            str += (char)( temp >= 10? 'A'+(temp-10):'0'+temp);
            str +=  ' ';
        }
        return str;
    }

}
