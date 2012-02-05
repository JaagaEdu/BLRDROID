package com.jaaga.blrdroid.downloadyou;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Vector;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class Downloader extends AsyncTask<String, Void, Void> {
	private ProgressDialog dialog;
	private String result;
	
    public Downloader(Context context) {
        dialog = new ProgressDialog(context);
    }
    
    protected void onPreExecute() {
        dialog.setMessage("Downloading...");
        dialog.show();
    }

    @Override
    protected Void doInBackground(String... arg0) {
        int begin, end;
        String tmpstr = null;
        try {//http://www.youtube.com/watch?v=y12-1miZHLs&nomobile=1
        	 //http://www.youtube.com/watch?v=P_JVZgdEIJY&nomobile=1
            URL url=new URL("http://www.youtube.com/watch?v=y12-1miZHLs&nomobile=1");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            InputStream stream=con.getInputStream();
            InputStreamReader reader=new InputStreamReader(stream);
            StringBuffer buffer=new StringBuffer();
            char[] buf=new char[262144];                
            int chars_read;
            while ((chars_read = reader.read(buf, 0, 262144)) != -1) {
                buffer.append(buf, 0, chars_read);
            }
            tmpstr=buffer.toString();

            begin  = tmpstr.indexOf("url_encoded_fmt_stream_map=");
            end = tmpstr.indexOf("&", begin + 27);
            if (end == -1) {
                end = tmpstr.indexOf("\"", begin + 27);
            }
            tmpstr = URLDecoder.decode(tmpstr.substring(begin + 27, end));

        } catch (MalformedURLException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }

        Vector url_encoded_fmt_stream_map = new Vector();
        begin = 0;
        end   = tmpstr.indexOf(",");

        while (end != -1) {
            url_encoded_fmt_stream_map.addElement(tmpstr.substring(begin, end));
            begin = end + 1;
            end   = tmpstr.indexOf(",", begin);
        }

        url_encoded_fmt_stream_map.addElement(tmpstr.substring(begin, tmpstr.length()));
        String result = "";
        Enumeration url_encoded_fmt_stream_map_enum = url_encoded_fmt_stream_map.elements();
        while (url_encoded_fmt_stream_map_enum.hasMoreElements()) {
            tmpstr = (String)url_encoded_fmt_stream_map_enum.nextElement();
            begin = tmpstr.indexOf("itag=");
            if (begin != -1) {
                end = tmpstr.indexOf("&", begin + 5);

                if (end == -1) {
                      end = tmpstr.length();
                }

                int fmt = Integer.parseInt(tmpstr.substring(begin + 5, end));

                if (fmt == 35) {
                    begin = tmpstr.indexOf("url=");
                    if (begin != -1) {
                        end = tmpstr.indexOf("&", begin + 4);
                        if (end == -1) {
                           end = tmpstr.length();
                        }
                        result = URLDecoder.decode(tmpstr.substring(begin + 4, end));
                        this.result=result;
                        break;
                    }
                }
            }
        }         
        try {
          URL u = new URL(result);
          HttpURLConnection c = (HttpURLConnection) u.openConnection();
          c.setRequestMethod("GET");
/*              c.setRequestProperty("Youtubedl-no-compression", "True");
          c.setRequestProperty("User-Agent", "YouTube");*/

          c.setDoOutput(true);
          c.connect();

          FileOutputStream f=new FileOutputStream(new File("/sdcard/new.flv"));

          InputStream in=c.getInputStream();
          byte[] buffer=new byte[1024];
          int sz = 0;
          while ( (sz = in.read(buffer)) > 0 ) {
               f.write(buffer,0, sz);
          }
          f.close();
        } catch (MalformedURLException e) {
            new RuntimeException();
        } catch (IOException e) {
            new RuntimeException();
        }
        return null;
    }

    protected void onPostExecute(Void unused) {
        dialog.dismiss();
    }    

}