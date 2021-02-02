package com.errag.models;


import android.net.Uri;
import android.os.AsyncTask;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpAsync extends AsyncTask<Void,Void,Void> {
    private String url = null;
    private String method = null;
    private String data = null;

    public HttpAsync(String _url, String _method, String _data) {
        this.url = _url;
        this.method = _method;
        this.data = _data;
    }

    protected Void doInBackground(Void... params) {
        try {
            URL targetURL = new URL(url);
            HttpURLConnection http = (HttpURLConnection)targetURL.openConnection();
            http.setRequestMethod(method);
            http.setDoInput(true);

            if(data != null && data.length() > 0) {
                String query = "";
                String[] queryParams = data.split("&");

                if(queryParams.length > 0) {
                    Uri.Builder builder = new Uri.Builder();

                    for(String queryParam : queryParams) {
                        String[] queryData = queryParam.split("=");

                        if(queryData.length == 2)
                            builder = builder.appendQueryParameter(queryData[0], queryData[1]);
                    }

                    query = builder.build().getEncodedQuery();
                }

                if(query.length() > 0) {
                    OutputStream outputStream = http.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    writer.write(query);
                    writer.flush();
                    writer.close();
                    outputStream.close();
                    http.setDoOutput(true);
                }
            }

            http.connect();
            http.getInputStream();
            http.disconnect();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}