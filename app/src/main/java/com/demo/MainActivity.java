package com.demo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.video.compress.VideoCompressor;
import com.video.recorder.CameraActivity;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final int RESULT_CODE_COMPRESS_VIDEO = 3;
    private static final String TAG = "MainActivity";
    private EditText editText;
    private ProgressBar progressBar;
   private TextView  textView;
    private TextView textView2;
    public static final String APP_DIR = "VideoCompressor";

    public static final String COMPRESSED_VIDEOS_DIR = "/Compressed Videos/";

    public static final String TEMP_DIR = "/Temp/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        editText = (EditText) findViewById(R.id.editText);
        textView= (TextView) findViewById(R.id.textView);
        textView2= (TextView) findViewById(R.id.textView2);
        findViewById(R.id.btnSelectVideo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("video/*");
                startActivityForResult(intent, RESULT_CODE_COMPRESS_VIDEO);
            }
        });
        editText.setText(Environment.getExternalStorageDirectory()+"/test3.mp4");

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    protected void onActivityResult(int reqCode, int resCode, Intent data) {
        if (reqCode == RESULT_CODE_COMPRESS_VIDEO && resCode == Activity.RESULT_OK && data != null) {
            Uri uri = data.getData();
            if (uri != null) {
                Cursor cursor = getContentResolver().query(uri, null, null, null, null, null);
                try {
                    if (cursor != null && cursor.moveToFirst()) {
                        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns.DATA));
                        editText.setText(path);
                    } else {
                        editText.setText(uri.getPath());
                    }
                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                }
            }
        }else if (reqCode == 10 && resCode == RESULT_OK&& data != null){
            String  path=data.getExtras().getString(CameraActivity.OUT_FIle_KEY);
            textView.setText(path);
        }
    }

    public static void try2CreateCompressDir() {
        File f = new File(Environment.getExternalStorageDirectory(), File.separator + APP_DIR);
        f.mkdirs();
        f = new File(Environment.getExternalStorageDirectory(), File.separator + APP_DIR + COMPRESSED_VIDEOS_DIR);
        f.mkdirs();
        f = new File(Environment.getExternalStorageDirectory(), File.separator + APP_DIR + TEMP_DIR);
        f.mkdirs();
    }

    public void compress(View v) {
        try2CreateCompressDir();
        String outPath = Environment.getExternalStorageDirectory()
                + File.separator
                + APP_DIR
                + COMPRESSED_VIDEOS_DIR
                + "VIDEO_" + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date()) + ".mp4";
        progressBar.setVisibility(View.VISIBLE);
        VideoCompressor.compressVideo(editText.getText().toString(), outPath, new VideoCompressor.onCompressCompleteListener() {
            @Override
            public void onComplete(boolean compressed, String outPath) {
                progressBar.setVisibility(View.GONE);
                textView2.setVisibility(View.GONE);
                if (compressed) {
                    Log.d(TAG, "Compression successfully!");
                }
            }
        }, new VideoCompressor.OnVideoProgressListener() {
            @Override
            public void progress(int progress) {
                Log.d(TAG, "progress:" + progress);
                textView2.setText( "progress:" + progress);
            }
        });
    }

    public void record(View v) {
        CameraActivity.startRecordActivity(this,10);
    }
}
