package cn.roya.nanohttpdandroiddemo.WebServer;

import android.content.Context;
import android.net.Uri;

import java.io.InputStream;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;


public class MHTTPD extends NanoHTTPD {

    Context context;
    private static final String HTTP_TITLE = "Demo";
    private String HTTP_MSG = "Hello,world!";

    public MHTTPD(int port, Context context) {
        super(port);
        this.context = context;
    }

    public MHTTPD(String hostname, int port, Context context) {
        super(hostname, port);
        this.context = context;
    }

    @Override
    public Response serve(IHTTPSession session) {
        Method method = session.getMethod();
        String uri = session.getUri();
        Map<String, String> parms = session.getParms();

        System.out.println(method + " '" + uri + "' ");

        if (!uri.equals("/")){
            for (DataCache.StreamData i : DateCacheUtils.getDatabase().getAll()){
                if (i.filename.equals(uri)){
                    System.out.println(i.filename);
                    try {
                        InputStream inputStream = context.getContentResolver().openInputStream(i.uri);
                        return new NanoHTTPD.Response(Response.Status.OK, i.mimeType, inputStream);
                    }catch (Exception e){
                        e.printStackTrace();
                        return new NanoHTTPD.Response(Response.Status.NOT_FOUND, MIME_HTML, "ERROR404");
                    }
                }
            }
            return new NanoHTTPD.Response(Response.Status.NOT_FOUND, MIME_HTML, "ERROR404");

        }else {
            String msg =
                    "<html><body>" +
                            "<h2>" + HTTP_TITLE + "</h2>" +
                    "<h1>" + HTTP_MSG + "<h1>";

            for (DataCache.StreamData i : DateCacheUtils.getDatabase().getAll()){
                msg += "<img src=" +i.filename + ">";
            }
            msg +=          "</body></html>";

            return new NanoHTTPD.Response(msg);
        }
    }

    public void setMessge(String messge){
        HTTP_MSG = messge;
    }

    public void add(String filename, String mimetype, Uri uri){
        DateCacheUtils.getDatabase().add(filename,mimetype,uri);
    }
}
