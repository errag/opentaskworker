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
import com.google.gson.internal.$Gson$Preconditions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileAction extends Action {
    @Override
    public boolean exec(Context context, ParameterContainer params) throws Exception {
        Boolean copy = params.getBoolean(State.File.COPY.toString());
        Boolean move = params.getBoolean(State.File.MOVE.toString());
        String directory = params.getString(State.File.DIRECTORY.toString());
        String name = params.getString(State.File.FILENAME.toString());
        String target = params.getString(State.File.TARGET.toString());

        File fileDirectory = new File(directory);
        File[] files = fileDirectory.listFiles();

        if(files != null) {
            for (File file : files) {
                if (file.getName().matches(name)) {
                    File toFile = new File(target, file.getName());

                    if (!file.getAbsolutePath().equals(toFile.getAbsolutePath())) {
                        if (copy) {
                            if (!toFile.exists())
                                toFile.createNewFile();

                            InputStream inputStream = new FileInputStream(file);
                            OutputStream outputStream = new FileOutputStream(toFile);

                            byte[] buffer = new byte[1024];
                            int length;

                            while ((length = inputStream.read(buffer)) > 0)
                                outputStream.write(buffer, 0, length);

                            inputStream.close();
                            outputStream.close();
                        } else if (move) {
                            file.renameTo(toFile);
                        }
                    }
                }
            }
        }

        return false;
    }

    @Override
    public int getImage() {
        return R.drawable.img_copy;
    }

    @Override
    public void askForPermissions(Activity activity) {
        PermissionController.askForFileIO(activity);
    }

    @Override
    public Parameter[] setDialogInputParameter() {
        return new Parameter[] {
                new Parameter(R.string.file_copy, State.File.COPY.toString(), Parameter.Type.RADIO),
                new Parameter(R.string.file_move, State.File.MOVE.toString(), Parameter.Type.RADIO),
                new Parameter(R.string.file_directory, State.File.DIRECTORY.toString(), Parameter.Type.DIRECTORY),
                new Parameter(R.string.file_name, State.File.FILENAME.toString(), Parameter.Type.STRING),
                new Parameter(R.string.file_target, State.File.TARGET.toString(), Parameter.Type.DIRECTORY)
        };
    }
}
