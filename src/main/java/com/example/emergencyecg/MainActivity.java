package com.example.emergencyecg;


import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.drawheart.CardiographView;
import com.drawheart.PainView;
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD

import java.io.BufferedReader;
=======
>>>>>>> parent of 427465b... 更改数据传输格式
=======
>>>>>>> parent of 427465b... 更改数据传输格式
=======
>>>>>>> parent of 427465b... 更改数据传输格式
=======
>>>>>>> parent of 427465b... 更改数据传输格式
import java.io.File;
import java.io.FileOutputStream;

import java.io.IOException;
import java.io.InputStream;

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
=======
>>>>>>> parent of 427465b... 更改数据传输格式
=======
>>>>>>> parent of 427465b... 更改数据传输格式
=======
>>>>>>> parent of 427465b... 更改数据传输格式
=======
>>>>>>> parent of 427465b... 更改数据传输格式
import java.util.ArrayList;
import java.util.List;
=======
>>>>>>> parent of c93d6c9... 实现绘制
=======
>>>>>>> parent of c93d6c9... 实现绘制
import java.util.Set;
import java.util.UUID;



public class MainActivity extends AppCompatActivity {

    private static final boolean D = true;

    private static final String NAME = "emergencyecg";

    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private BluetoothAdapter bTAdatper;

    private String TAG = "MainActivity";

<<<<<<< HEAD
<<<<<<< HEAD
    public static List<Float> datas = new ArrayList();

    public static boolean flag = true;

    public static boolean size_change = true;

=======
>>>>>>> parent of c93d6c9... 实现绘制
=======
>>>>>>> parent of c93d6c9... 实现绘制
    private BluetoothSocket socket;

    public static float[] array2 = new float[1024];

    public static float arraydanqian[] = new float[1024];

    public static float arraydanhou[] = new float[1024];

    public static float arraydoubleqian[] = new float[1024];

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
    public static float arraydoublehou  [] = new float[1024];
=======
    public static float arraydoublehou  [] = new float[112];
>>>>>>> parent of 427465b... 更改数据传输格式
=======
    public static float arraydoublehou  [] = new float[112];
>>>>>>> parent of 427465b... 更改数据传输格式
=======
    public static float arraydoublehou  [] = new float[112];
>>>>>>> parent of 427465b... 更改数据传输格式
=======
    public static float arraydoublehou  [] = new float[112];
>>>>>>> parent of 427465b... 更改数据传输格式

    private int REQUEST_ENABLE = 1;

    private int backage_flag = 1;

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


        final Button connectBluetooth = findViewById(R.id.ConnectBLE);

//        点击添加心电图波形view
        connectBluetooth.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
//            回调方法
             public void onClick(View view) {

                LinearLayout bigLinearLayout = findViewById(R.id.bigPanel);
                final PainView painView = new PainView(MainActivity.this);
                final CardiographView gd = new CardiographView(MainActivity.this);
                gd.setMinimumWidth(bigLinearLayout.getWidth());
                gd.setMinimumHeight(bigLinearLayout.getHeight());
//                Log.e("宽", String.valueOf(bigLinearLayout.getWidth()));
//                Log.e("高", String.valueOf(bigLinearLayout.getHeight()));

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

//            Toast.makeText(MainActivity.this,"连接成功",Toast.LENGTH_LONG);
        }

    }

//      接收信息线程
    class getDataThread extends Thread {

        private BluetoothServerSocket bluetoothServerSocket;
        private PainView painView = new PainView(MainActivity.this);


    @Override
        public void run() {

            try {
                InputStream inputStream = socket.getInputStream();
//                OutputStream outputStream = socket.getOutputStream();
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
                final byte[] buffer = new byte[112];
=======
>>>>>>> parent of c93d6c9... 实现绘制
=======
>>>>>>> parent of c93d6c9... 实现绘制
                final  byte[] buffer = new byte[1024];
=======
                final  byte[] buffer = new byte[112];
>>>>>>> parent of 427465b... 更改数据传输格式
=======
                final  byte[] buffer = new byte[112];
>>>>>>> parent of 427465b... 更改数据传输格式
=======
                final  byte[] buffer = new byte[112];
>>>>>>> parent of 427465b... 更改数据传输格式
=======
                final  byte[] buffer = new byte[112];
>>>>>>> parent of 427465b... 更改数据传输格式
                int bytes;

                while(true){
//      读取数据
                    bytes = inputStream.read(buffer);
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
                    if (bytes > -1) {
=======
                    if(bytes > -1) {
>>>>>>> parent of 427465b... 更改数据传输格式
=======
                    if(bytes > -1) {
>>>>>>> parent of 427465b... 更改数据传输格式
=======
                    if(bytes > -1) {
>>>>>>> parent of 427465b... 更改数据传输格式
=======
                    if(bytes > -1) {
>>>>>>> parent of 427465b... 更改数据传输格式
                        final byte[] data = new byte[112];
=======
>>>>>>> parent of c93d6c9... 实现绘制
=======
>>>>>>> parent of c93d6c9... 实现绘制
                    if(bytes > -1) {
                        final byte[] data = new byte[1024];

//                        复制到data数组
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
                        System.arraycopy(buffer, 0, data, 0, bytes);

////                      将字节数组转换为字符串数字并判断正负
//                        for (int j = 0; j < data.length; j++) {
//                            array2[j] = data[j];
//
////                      判断正负修改数据符号问题
//                            if (array2[j] < 0) {
//                                array2[j] = 256 + array2[j];
//                            }
//                        }
//
////                      判断标识位（55，F0）
//                        for (int j = 0; j < data.length - 1; j++) {
//
//                            if (array2[j] == 85 && array2[j + 1] == 240) {
//
//                                if (backage_flag == 1) {
////                              去除第一包开始标志前数据
//                                    System.arraycopy(array2, j, arraydanhou, 0, data.length - 1 - j);
//                                } else if (backage_flag % 2 != 0) {
////                              保存单数包标志前后数据
//                                    System.arraycopy(array2, 0, arraydanqian, data.length - j, j);
//                                    System.arraycopy(array2, j, arraydanhou, 0, data.length - 1 - j);
////                              整合双数包标志后与单数包标志前的数据
//                                    System.arraycopy(arraydoublehou, 0, arraydanqian, 0, data.length - 1 - j);
//
////                                        for (int i = 0; i < arraydanqian.length; i++) {
////                                            Log.e(TAG, "单前: " + arraydanqian[i]);
////                                        }
//
//                                    datas.add(arraydanqian[2]);
//                                    flag = true;
////                                        WriteFile2.initData(array2);
////                                        WriteFileArray.initData(arraydanqian);
//
//                                } else if (backage_flag % 2 == 0) {
////                              保存双数包标志前后数据
//                                    System.arraycopy(array2, 0, arraydoubleqian, data.length - j, j);
//                                    System.arraycopy(array2, j, arraydoublehou, 0, data.length - 1 - j);
////                              整合双数包标志前与单数包标志后的数据
//                                    System.arraycopy(arraydanhou, 0, arraydoubleqian, 0, data.length - 1 - j);
//
////                                        for (int i = 0;  i < arraydoubleqian.length; i++) {
////                                            Log.e(TAG, "双后: " + arraydoubleqian[i]);
////                                         }
//
//                                    datas.add(arraydoubleqian[2]);
//                                    flag = true;
////                                        WriteFile2.initData(array2);
//
////                                        WriteFile2.initData(arraydanqian[2]);
////                                        WriteFileArray.initData(arraydoubleqian);
//
//                                }
//                            } else {
//                                if (backage_flag % 2 != 0) {
//                                    System.arraycopy(array2, 0, arraydanqian, 0, array2.length);
//                                } else {
//
//                                    System.arraycopy(array2, 0, arraydoubleqian, 0, array2.length);
//                                }
//                            }
//                        }
=======
>>>>>>> parent of 427465b... 更改数据传输格式
=======
>>>>>>> parent of 427465b... 更改数据传输格式
=======
>>>>>>> parent of 427465b... 更改数据传输格式
=======
>>>>>>> parent of 427465b... 更改数据传输格式
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

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
//                                        Log.d(TAG, "单前: " + arraydanqian[2]);


=======
=======
>>>>>>> parent of 427465b... 更改数据传输格式
=======
>>>>>>> parent of 427465b... 更改数据传输格式
=======
>>>>>>> parent of 427465b... 更改数据传输格式
                                        datas.add(arraydanqian[2]);
                                        flag = true;
//                                        WriteFile2.initData(array2);

//                                        WriteFileArray.initData(arraydanqian);
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
>>>>>>> parent of 427465b... 更改数据传输格式
=======
>>>>>>> parent of 427465b... 更改数据传输格式
=======
>>>>>>> parent of 427465b... 更改数据传输格式
=======
>>>>>>> parent of 427465b... 更改数据传输格式

                                    } else if (backage_flag % 2 == 0) {
//                              保存双数包标志前后数据
                                        System.arraycopy(array2, 0, arraydoubleqian, data.length - j, j);
                                        System.arraycopy(array2, j, arraydoublehou, 0, data.length - 1 - j);
//                              整合双数包标志前与单数包标志后的数据
                                        System.arraycopy(arraydanhou,   0, arraydoubleqian, 0, data.length - 1 - j);

//                                        for (int i = 0;  i < arraydoubleqian.length; i++) {
//                                            Log.e(TAG, "双后: " + arraydoubleqian[i]);
//                                         }

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
//                                            Log.e(TAG, "双后: " + arraydoubleqian[2]);
=======
=======
>>>>>>> parent of 427465b... 更改数据传输格式
=======
>>>>>>> parent of 427465b... 更改数据传输格式
=======
>>>>>>> parent of 427465b... 更改数据传输格式
                                        datas.add(arraydoubleqian[2]);
                                        flag = true;
//                                        WriteFile2.initData(array2);

//                                        WriteFile2.initData(arraydanqian[2]);
//                                        WriteFileArray.initData(arraydoubleqian);
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
>>>>>>> parent of 427465b... 更改数据传输格式
=======
>>>>>>> parent of 427465b... 更改数据传输格式
=======
>>>>>>> parent of 427465b... 更改数据传输格式
=======
>>>>>>> parent of 427465b... 更改数据传输格式

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

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
    private int bytestoint(byte[] datam) {

        int result = 0;

        for(int i = 0;i<datam.length;i++)

        if (file.exists()==false) {
            try {
                File tempFile=null;
                tempFile = tempFile.createTempFile("users", "properties");
                byte[] buffer = new byte[1024];
                FileOutputStream writeFile = new  FileOutputStream(tempFile);
                InputStream inStream = getResources().getAssets().open("log.txt");
                int length = inStream.read(buffer);
                writeFile.write(buffer, 0, length);
                writeFile.flush();
                inStream.close();
                writeFile.close();
                file = tempFile;



            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


        result=datam[1]<<8 | datam[0];

        if (datam[11]>>7==0x01)
        {
            result=result-65536;
        }

        return result;
    }

    //read file and add data to list
    public static void readInternal(InputStream inputStream) throws IOException{

        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(inputStream, "gbk");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                String[] arr = line.split(" ");
                Log.e("readfile", "readInternal: "+arr[0]+"  --  "+line);
                datas.add(Float.parseFloat(arr[0]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
=======
>>>>>>> parent of 427465b... 更改数据传输格式
=======
>>>>>>> parent of 427465b... 更改数据传输格式
=======
>>>>>>> parent of 427465b... 更改数据传输格式
=======
>>>>>>> parent of 427465b... 更改数据传输格式
}
