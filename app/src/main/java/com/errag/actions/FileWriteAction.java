package com.errag.actions;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.os.FileUtils;

import com.errag.models.Action;
import com.errag.models.Parameter;
import com.errag.models.ParameterContainer;
import com.errag.models.State;
import com.errag.opentaskworker.PermissionController;
import com.errag.opentaskworker.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class FileWriteAction extends Action {
    @Override
    public boolean exec(Context context, ParameterContainer params) throws Exception {
        boolean result = true;

        Boolean write = params.getBoolean(State.FileWrite.WRITE.toString());
        Boolean append = params.getBoolean(State.FileWrite.APPEND.toString());
        String directory = params.getString(State.FileWrite.DIRECTORY.toString());
        String name = params.getString(State.FileWrite.FILENAME.toString());
        String text = params.getString(State.FileWrite.TEXT.toString());

        if(write || append) {
            File file = new File(directory, name);

            if(write && file.exists())
                file.delete();

            if(!file.exists())
                file.createNewFile();

            FileOutputStream fileStream = new FileOutputStream(file, append);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileStream);
            outputStreamWriter.write(text);
            outputStreamWriter.close();
        } else
            result = false;

        return result;
    }

    @Override
    public void askForPermissions(Activity activity) {
        PermissionController.askForFileIO(activity);
    }

    @Override
    public int getImage() {
        return R.drawable.img_file;
    }

    @Override
    public Parameter[] setDialogInputParameter() {
        return new Parameter[] {
                new Parameter(R.string.file_write_create, State.FileWrite.WRITE.toString(), Parameter.Type.RADIO),
                new Parameter(R.string.file_write_append, State.FileWrite.APPEND.toString(), Parameter.Type.RADIO),
                new Parameter(R.string.file_write_directory, State.FileWrite.DIRECTORY.toString(), Parameter.Type.DIRECTORY),
                new Parameter(R.string.file_write_filename, State.FileWrite.FILENAME.toString(), Parameter.Type.STRING),
                new Parameter(R.string.file_write_text, State.FileWrite.TEXT.toString(), Parameter.Type.STRING)
        };
    }
}
