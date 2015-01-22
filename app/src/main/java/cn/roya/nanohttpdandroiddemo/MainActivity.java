package cn.roya.nanohttpdandroiddemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cn.roya.nanohttpdandroiddemo.WebServer.MService;


public class MainActivity extends ActionBarActivity {

    private Toolbar toolbar;
    private TextView textView;

    private EditText editTextMsg;
    private Button buttonMsg;

    Handler handler;

    ServiceConnection serviceConnection;
    MService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        textView = (TextView)findViewById(R.id.textView);
        editTextMsg = (EditText)findViewById(R.id.editText_msg);
        buttonMsg = (Button)findViewById(R.id.button_msg);

        buttonMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextMsg.getText() != null){
                    setMessge(editTextMsg.getText().toString());
                }
            }
        });

//      get service
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                MService.mBinder mBinder = (MService.mBinder) iBinder;
                mService = mBinder.getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {

            }
        };

        Intent serviceIntent;
        serviceIntent = new Intent(getApplicationContext(), MService.class);
        bindService(serviceIntent, serviceConnection, BIND_AUTO_CREATE);


        handler = new Handler(){
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        textView.setText("listening:"+mService.getLocal());
                        break;
                }
                super.handleMessage(msg);
            }
        };

        new Thread(new Runnable() {
            boolean waiting = true;
            @Override
            public void run() {
                do {
                    if (mService != null){

                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);

                        waiting = false;
                        break;
                    }
                }while (waiting);
            }
        }).start();
    }


    private void setMessge(final String msg){
        new Thread(new Runnable() {
            boolean waiting = true;
            @Override
            public void run() {
                do {
                    if (mService != null){

                        mService.setMessge(msg);

                        waiting = false;
                        break;
                    }
                }while (waiting);
            }
        }).start();
    }

}
