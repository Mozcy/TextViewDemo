package com.example.textviewdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    private static final String TAG = "MainActivity";
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private float y1 = 0;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        //填充足够长的内容用于测试
        tv1.setText("填充足够长的内容用于测试");
        tv2.setText("填充足够长的内容用于测试");
        tv3.setText("填充足够长的内容用于测试");

        //让TextView可以滑动
        tv1.setMovementMethod(ScrollingMovementMethod.getInstance());
        tv2.setMovementMethod(ScrollingMovementMethod.getInstance());
        tv3.setMovementMethod(ScrollingMovementMethod.getInstance());

        //手势监听
        tv1.setOnTouchListener(this::onTouch);
        tv2.setOnTouchListener(this::onTouch);
        tv3.setOnTouchListener(this::onTouch);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //按下去第一个Y坐标
            y1 = event.getY();
            //通知父控件不要干扰
            v.getParent().requestDisallowInterceptTouchEvent(true);
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if (y1 - event.getY() > 50) {
                Log.e(TAG, "向上滑");
                v.getParent().requestDisallowInterceptTouchEvent(canVerticalScroll((TextView) v, true));
            } else if (event.getY() - y1 > 380) {
                Log.e(TAG, "向下滑: ");
                v.getParent().requestDisallowInterceptTouchEvent(canVerticalScroll((TextView) v, false));
            }

        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            v.getParent().requestDisallowInterceptTouchEvent(false);
        }
        return false;
    }

    /**
     * @param view 滑动的TextView
     * @param flag true 向上滑动 false 向下滑动
     * @return
     */
    protected boolean canVerticalScroll(TextView view, boolean flag) {
        //滚动的距离
        int scrollY = view.getScrollY();
        Log.e(TAG, "滚动距离: " + scrollY);
        //控件内容的总高度
        int scrollRange = view.getLayout().getHeight();
        Log.e(TAG, "控件内容的总高度: " + scrollRange);
        //控件实际显示的高度
        int scrollExtent = view.getHeight() - view.getCompoundPaddingTop() - view.getCompoundPaddingBottom();
        Log.e(TAG, "控件实际显示的高度: " + scrollExtent);
        //控件内容总高度与实际显示高度的差值
        int scrollDifference = scrollRange - scrollExtent;
        Log.e(TAG, "显示高度的差值: " + scrollDifference);
        if (flag) {
            return scrollDifference == scrollY ? false : true;
        } else {
            return scrollDifference == 0;
        }
    }
}