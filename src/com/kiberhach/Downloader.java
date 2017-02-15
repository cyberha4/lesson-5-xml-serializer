package com.kiberhach;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by admin on 15.02.2017.
 */
public class Downloader {
    String url;
    public Downloader(String url){
        this.url = url;
    }
    public void download(String strPath){
        try {
            String strURL = url;
            //String strPath = "test.jar";

            URL connection = new URL(strURL);
            HttpURLConnection urlconn;
            urlconn = (HttpURLConnection) connection.openConnection();
            urlconn.setRequestMethod("GET");
            urlconn.connect();
            InputStream in = null;
            in = urlconn.getInputStream();
            OutputStream writer = new FileOutputStream(strPath);

            byte buffer[] = new byte[1000000];
            int c = in.read(buffer);
            while (c > 0) {
                writer.write(buffer, 0, c);
                c = in.read(buffer);
            }
            writer.flush();
            writer.close();
            in.close();
            System.out.println("Download is complete");
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
