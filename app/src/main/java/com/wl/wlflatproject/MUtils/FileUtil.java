package com.wl.wlflatproject.MUtils;

import android.content.Context;
import android.net.Uri;
import android.os.Build;


import androidx.core.content.FileProvider;

import java.io.File;

public class FileUtil {
    public static Uri getUriForFile(Context mContext, File file) {
        Uri fileUri = null;
        if (Build.VERSION.SDK_INT >= 24) {
            fileUri = FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".fileprovider", file);
        } else {
            fileUri = Uri.fromFile(file);
        }
        return fileUri;
    }

}
