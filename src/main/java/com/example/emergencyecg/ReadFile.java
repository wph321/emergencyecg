package com.example.emergencyecg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * read file and add List
 * Created by wei on 2018/11/21.
 */

public class ReadFile {

    public static void readFileOnLine(String filePath){//输入文件路径
        try {
        StringBuffer sb = new StringBuffer();
        File file = new File(filePath);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = "";
            while((line = br.readLine())!=null){
                sb.append(line);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
