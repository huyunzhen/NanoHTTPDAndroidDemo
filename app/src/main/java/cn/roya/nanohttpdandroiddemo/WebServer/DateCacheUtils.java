package cn.roya.nanohttpdandroiddemo.WebServer;

/**
 * Created by roya on 14/12/26.
 */
public class DateCacheUtils {
    public static DataCache database;

    public synchronized static DataCache getDatabase(){
        if (database == null){
            database = new DataCache();
        }
        return database;

    }
}
