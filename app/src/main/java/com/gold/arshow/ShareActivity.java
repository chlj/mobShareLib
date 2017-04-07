package com.gold.arshow;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.gold.arshow.wxapi.sharesdkUtil;

public class ShareActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();

        findViewById(R.id.btns).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
                //分享
            }
        });
    }


    private void initData(){



    }




    private void share() {

        sharesdkUtil.ShareByCustomByMy(ShareActivity.this,
                "title",
                "http://img1.7wenta.com/upload/qa_headIcons/20150122/14219365390308909.jpg",
                "http://www.baidu.com",
                "content", new Handler() {
            @Override
            public void dispatchMessage(Message msg) {
                super.dispatchMessage(msg);
                if (msg.what == 1) {
                    Toast.makeText(ShareActivity.this,ShareActivity.this.getString(R.string.ssdk_oks_share_completed),Toast.LENGTH_LONG).show();

                } else if (msg.what == 2) {
                    Toast.makeText(ShareActivity.this,ShareActivity.this.getString(R.string.ssdk_oks_share_failed),Toast.LENGTH_LONG).show();
                    Log.i("Unity","分享失败error=" + msg.obj.toString().trim());


                } else if (msg.what == 3) {
                    Toast.makeText(ShareActivity.this,ShareActivity.this.getString(R.string.ssdk_oks_share_canceled),Toast.LENGTH_LONG).show();

                }
            }
        });
    }
    
}
