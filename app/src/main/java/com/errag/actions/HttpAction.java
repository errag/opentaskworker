package com.errag.actions;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import com.errag.models.Action;
import com.errag.models.HttpAsync;
import com.errag.models.Parameter;
import com.errag.models.State;
import com.errag.opentaskworker.R;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpAction extends Action {
    private final static String HTTP_METHODS = "GET;POST;DELETE;PUT;HEAD;CONNECT;OPTIONS;TRACE;PATCH";

    @Override
    public boolean exec(Context context, Parameter[] params) throws Exception {
        String url = getACString(State.Http.URL.toString(), params);
        String method = getACString(State.Http.METHOD.toString(), params);
        String data = getACString(State.Http.PARAMS.toString(), params);

        new HttpAsync(url, method, data).execute();

        return true;
    }

    @Override
    public int getImage() {
        return R.drawable.img_http;
    }

    @Override
    public Parameter[] setDialogInputParameter() {
        return new Parameter[]{
                new Parameter(R.string.http_url, State.Http.URL.toString(), Parameter.Type.STRING),
                new Parameter(R.string.http_method, State.Http.METHOD.toString(), Parameter.Type.DROPDOWN, HTTP_METHODS),
                new Parameter(R.string.http_parameter, State.Http.PARAMS.toString(), Parameter.Type.STRING),
        };
    }

}
