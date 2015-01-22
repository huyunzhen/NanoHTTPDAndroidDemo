package cn.roya.nanohttpdandroiddemo.WebServer;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roya on 14/12/26.
 */
public class DataCache {

    public class StreamData{
        public String filename;
        public String mimeType;
        public Uri uri;
    }


    private List<StreamData> streamDataList;

    public DataCache(){
        streamDataList = new ArrayList<>();
    }


    public List<StreamData> getAll(){
        return streamDataList;
    }

    public void clean(){
        streamDataList.clear();
    }

    public void add(String filename , String mimeType , Uri uri){
        StreamData item = new StreamData();
        item.filename = "/"+filename;
        item.mimeType = mimeType;
        item.uri = uri;
        add(item);
    }

    public void add(StreamData item){
        streamDataList.add(item);
    }
}
