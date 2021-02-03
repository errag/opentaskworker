package com.errag.actions;

import android.content.Context;
import android.os.Environment;

import com.errag.models.Action;
import com.errag.models.Parameter;
import com.errag.models.ParameterContainer;
import com.errag.models.State;
import com.errag.opentaskworker.R;

import java.io.File;

public class FileDeleteAction extends Action {
    @Override
    public boolean exec(Context context, ParameterContainer params) throws Exception {
        String directory = params.getString(State.FileDelete.DIRECTORY.toString());
        String name = params.getString(State.FileDelete.FILENAME.toString());

        File fileDirectory = new File(directory);
        File[] files = fileDirectory.listFiles();

        if(files != null) {
            for (File file : files) {
                if (file.getName().matches(name)) {
                    file.delete();
                }
            }
        }

        return true;
    }

    @Override
    public int getImage() {
        return R.drawable.img_trash;
    }

    @Override
    public Parameter[] setDialogInputParameter() {
        return new Parameter[] {
                new Parameter(R.string.file_delete_directory, State.FileDelete.DIRECTORY.toString(), Parameter.Type.DIRECTORY),
                new Parameter(R.string.file_delete_filename, State.FileDelete.FILENAME.toString(), Parameter.Type.STRING)
        };
    }
}
