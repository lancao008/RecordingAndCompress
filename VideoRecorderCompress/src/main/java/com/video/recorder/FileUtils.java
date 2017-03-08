package com.video.recorder;

import android.content.Context;

import java.io.File;
import java.util.UUID;

/**
 * Created by yangchaojiang on 1/16/2017.
 * E-Mail:1007181167@qq.com
 * Description:
 */

public class FileUtils {

    private final static String TAG = "FileUtils";

    /****
     * 文件存放地址
     *
     * @param context 上下文
     * @param type    存放文件的类型  使用 Environment
     **/
    public static String getDiskFileDir(Context context, String type) {
        String cachePath;
        File file = context.getExternalFilesDir(type);
        if (file != null) {
            cachePath = file.getAbsolutePath();
        } else {
            cachePath = context.getCacheDir().getAbsolutePath();
        }
        return cachePath;
    }

    /****
     * 生成filekey
     ***/
    public static String getFileKey() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
