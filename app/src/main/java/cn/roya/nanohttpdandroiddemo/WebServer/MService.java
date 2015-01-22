package cn.roya.nanohttpdandroiddemo.WebServer;

import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;

import java.io.IOException;

import cn.roya.lightshare.widgets.Utils;
import cn.roya.lightshare.widgets.WebServer;


public class MService extends android.app.Service {

    private MHTTPD mhttpd;

    private static final String TAG = "MService";
    private String local;
    private int port = 8123;

    @Override
    public void onCreate() {
        super.onCreate();
        local = IPUtils.getIPAddress(true);
        mhttpd = new MHTTPD(local, port,getApplicationContext());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!mhttpd.wasStarted()){
            try {
                mhttpd.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        mhttpd.stop();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new mBinder();
    }

    public class mBinder extends Binder{
        public MService getService(){
            return MService.this;
        }
    }

    public String getLocal(){
        return local+":"+port;
    }

    public void setMessge(String messge){
        mhttpd.setMessge(messge);
    }

    public void add(String filename, String mimetype, Uri uri){
        mhttpd.add(filename, mimetype, uri);}

}
