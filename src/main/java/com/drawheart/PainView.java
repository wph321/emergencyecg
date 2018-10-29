package com.drawheart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;

import com.example.emergencyecg.MainActivity;
import com.example.emergencyecg.WriteFile;

import static android.content.ContentValues.TAG;

/**
 * Created by wph on 2018/3/7  pm  15:00.
 * 绘制心电波形
 */

public class PainView extends CardiographView {

    int a = this.getWidth();
    int index = 0;
    int size = 0;
    private float [][] oldpint = new float[2530][4];//创建上一段需要显示的点
    private Paint p;//创建画笔
    public int i = 0;
    public PainView(Context context) {
        super(context);
    }

    public PainView(Context context,AttributeSet attrs){

        super(context, attrs);

    }

    public PainView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        p = new Paint();
        p.setColor(Color.GREEN);//画笔设置绿色
        p.setStyle(Paint.Style.FILL);
        p.setColor(Color.parseColor("#1FF421"));
        p.setAntiAlias(true);
        p.setStrokeWidth(2);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(2);
        mPaint.setAntiAlias(true);

        for (int d = i +10; d < oldpint.length; d++) {//绘制上次绘制的波
            canvas.drawLine(oldpint[d][0],oldpint[d][1],oldpint[d][2],oldpint[d][3],p);
        }

        if(MainActivity.flag = true) {
//            i+=10;
            mHandler.postDelayed(r, 1);//定时器
        }

        canvas.drawRect(i , 0, i +10, getHeight(), mPaint);//绘制刷新黑框

        if(index<MainActivity.datas.size()-1) {

            index++;
            Log.d(TAG, "index: "+index);
        }else if(index<0){
            index=0;
            MainActivity.datas.clear();
        }

        for (int j = 20; j < i; j+=10) {

            if (j == getWidth()-20 || i == oldpint.length) {//到达边界后返回
                j = 20;
                i = 0;
                MainActivity.datas.clear();
                index=0;
            }

            if(MainActivity.datas.size()>0) {

                if(index>0 && MainActivity.flag == true) {
                    //保存上次绘图坐标
                    oldpint[i][0] = j - 10;
                    oldpint[i][1] = getHeight()/2 - MainActivity.datas.get(index - 1);
                    oldpint[i][2] = j;
                    oldpint[i][3] = getHeight()/2 - MainActivity.datas.get(index);

                    MainActivity.flag = false;
                }else{
                    continue;
                }
            }else{
                canvas.drawLine(j-10, getHeight()/2, j, getHeight()/2, p);//绘制波形
                oldpint[i][0] = j - 10;
                oldpint[i][1] = getHeight()/2;
                oldpint[i][2] = j;
                oldpint[i][3] = getHeight()/2 ;
                continue;
            }


            if(MainActivity.flag = true) {
                canvas.drawLine(oldpint[j][0], oldpint[j][1], oldpint[j][2], oldpint[j][3], p);//绘制波形

                WriteFile.initData(MainActivity.datas);

            }

        }
    }

    //  正弦函数
    public double Sin(int i) {

        double result = 0;
//      result = Math.cos(i * Math.PI / 180);
        result =100* Math.sin((i) * Math.PI / 180)+300;
//      result = Math.tan(i * Math.PI / 180);
        return result;
    }

    //  定时器回掉
    Handler mHandler = new Handler();
    Runnable r = new Runnable() {

        @Override
        public void run() {
//          每隔1s循环执行run方法
            i+=10;
            invalidate();

        }
    };

}