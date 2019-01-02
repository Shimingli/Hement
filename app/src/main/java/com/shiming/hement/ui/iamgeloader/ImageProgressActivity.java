package com.shiming.hement.ui.iamgeloader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.GlideException;
import com.shiming.hement.R;
import com.shiming.imageloader.ImageLoaderV4;
import com.shiming.imageloader.okhttp.OnGlideImageViewListener;
import com.shiming.imageloader.okhttp.OnProgressListener;

public class ImageProgressActivity extends AppCompatActivity {
    private ImageView mImageView_8;
    private TextView mProgress;

    private ImageView mImageView_7;
    private TextView mProgress7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_progress);

        mImageView_8 = (ImageView) findViewById(R.id.image_view_8);

        mProgress = findViewById(R.id.tv_progress);
        mImageView_7 = (ImageView) findViewById(R.id.image_view_7);
        mProgress7 = findViewById(R.id.tv_progress_7);


        ImageLoaderV4.getInstance().disPlayImageProgress(this, "http://img.zcool.cn/community/0142135541fe180000019ae9b8cf86.jpg@1280w_1l_2o_100sh.png", mImageView_8, R.mipmap.test, R.mipmap.test, new OnGlideImageViewListener() {
            @Override
            public void onProgress(int percent, boolean isDone, GlideException exception) {
                System.out.println("shiming percent="+percent);
                System.out.println("shiming isDone="+isDone);
                mProgress.setText("我在主线程，进度是多少=="+percent+"%");

                if (isDone){
                    mProgress.setText("我在主线程，进度是多少==100%");
                }
            }
        });


        ImageLoaderV4.getInstance().disPlayImageProgressByOnProgressListener(this, "http://img.zcool.cn/community/0142135541fe180000019ae9b8cf86.jpg@1280w_1l_2o_100sh.png", mImageView_7, R.mipmap.test, R.mipmap.test, new OnProgressListener() {
            @Override
            public void onProgress(String imageUrl, long bytesRead, long totalBytes, boolean isDone, GlideException exception) {
                System.out.println("shiming bytesRead="+bytesRead);
                System.out.println("shiming totalBytes="+totalBytes);
                mProgress7.setText("我在主线程，进度是多少==+bytesRead"+bytesRead+"totalBytes="+totalBytes);

                if (isDone){
                    mProgress7.setText("我在主线程，进度是多少==100%");
                }
            }
        });
    }

    @Override
    public void onBackPressed() {

//        ImageLoaderV4.getInstance().glidePauseRequests(this);
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
         ImageLoaderV4.getInstance().clear(this,mImageView_7);
        ImageLoaderV4.getInstance().clear(this,mImageView_8);
        ImageLoaderV4.getInstance().glidePauseRequests(this);
        super.onPause();
    }

    @Override
    protected void onResume() {
//        ImageLoaderV4.getInstance().clear(this,mImageView_7);
//        ImageLoaderV4.getInstance().clear(this,mImageView_8);
//        ImageLoaderV4.getInstance().glideResumeRequests(this);
        super.onResume();
    }
}
