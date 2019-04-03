package top.systemsec.survey.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * 不需要mvp设计模式的基类
 * Created by xiaohan on 2018/1/19.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        Log.d("BaseActivity", "onWindowFocusChanged: hasFocus " + hasFocus);
        super.onWindowFocusChanged(hasFocus);

    }


}