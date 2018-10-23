package com.drawheart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.util.Log;

import com.example.emergencyecg.MainActivity;

import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Created by wph on 2018/3/7  pm  15:00.
 * 绘制心电波形
 */

public class PainView extends CardiographView {

    private float [][] oldpint = new float[3000][4];//创建上一段需要显示的点

    private Paint p;//创建画笔


    int datasize = MainActivity.arraydanhou.length;

    private int i = 0;

    public PainView(Context context) {
        super(context);
    }

    public PainView(Context context, AttributeSet attrs) {
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

        for (int d = i + 10; d < oldpint.length-1; d++) {//绘制上次绘制的波
            canvas.drawLine(oldpint[d][0],oldpint[d][1],oldpint[d][2],oldpint[d][3],p);
        }

        mHandler.postDelayed(r, 4);//定时器



        for(int j = 0;j<i;j+=10) {

            if (j == getWidth()) {
                j = 0;
                i = 0;
            }


            //保存上次绘图坐标
            oldpint[j][1] = MainActivity.arraydanhou[2] + 300;
            oldpint[j][3] = MainActivity.arraydanhou[2] + 300;
            oldpint[j][2] = j;
            oldpint[j][0] = j + 10;


            canvas.drawRect(i, 0, i + 10, getHeight(), mPaint);//绘制刷新黑框
            canvas.drawLine(j, oldpint[j][1], j+10, oldpint[j][3], p);//绘制波形

//            Log.d(TAG, "stax:" + (j - 1) + "   " + "stay:" + oldpint[j][1] + "   " + "stox:" + j + "   " + "stoy:" + oldpint[j][3]);


        }

    }

    //  定时器回调
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