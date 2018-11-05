package com.example.emergencyecg;

import android.nfc.Tag;
import android.util.Log;

import java.io.File;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by wei on 2018/10/25.
 */

public class WriteFile {

    public static void initData(List a) {
        String filePath = "/sdcard/Test1/";
        String fileName = "画波数据.txt";
        if(a.size()>0) {
            for (int i = 0; i < a.size(); i++) {
                writeTxtToFile(a.get(i).toString(), filePath, fileName);
            }
        }
    }

    // 将字符串写入到文本文件中
    public static void writeTxtToFile(String strcontent, String filePath, String fileName) {
        //生成文件夹之后，再生成文件，不然会出错
        makeFilePath(filePath, fileName);

        String strFilePath = filePath+fileName;
        // 每次写入时，都换行写
        String strContent = strcontent + "\r\n";
        try {
            File file = new File(strFilePath);
            if (!file.exists()) {
                Log.d("TestFile", "Create the file:" + strFilePath);
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            raf.seek(file.length());
            raf.write(strContent.getBytes());
            raf.close();
        } catch (Exception e) {
            Log.e("TestFile", "Error on write File:" + e);
        }
    }

    // 生成文件
    public static File makeFilePath(String filePath, String fileName) {
        File file = null;
        makeRootDirectory(filePath);
        try {
            file = new File(filePath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,e+"--生成文件时错误");
        }
        return file;
    }

    // 生成文件夹
    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
            Log.e("error:", e+"--生成文件夹时发生错误");
        }
    }

}
