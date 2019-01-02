package com.shiming.hement.ui.permission;

import android.Manifest;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.shiming.hement.R;
import com.shiming.hement.ui.base.BaseActivity;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.IOException;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

/**
 * <p>
 *
 * </p>
 *
 * @author shiming
 * @version v1.0
 * @since 2018/12/5 10:12
 */

public class RxPermissionsActivity extends BaseActivity implements View.OnClickListener {


    private Camera camera;
    private SurfaceView surfaceView;
    private Disposable disposable;
    private RxPermissions mRxPermissions;
    private Button mNeedMorePermission;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_permissions);
        surfaceView = findViewById(R.id.surfaceView);
        mNeedMorePermission = findViewById(R.id.btn_need_more_permission);
        mRxPermissions = new RxPermissions(this);
        mRxPermissions.setLogging(true);
        mNeedMorePermission.setOnClickListener(this);
        //使用RxView的简单的例子
        disposable = RxView.clicks(findViewById(R.id.enableCamera))
                // Ask for permissions when button is clicked
                .compose(mRxPermissions.ensureEach(Manifest.permission.CAMERA))
                .subscribe(new Consumer<Permission>() {
                               @Override
                               public void accept(Permission permission) {
                                   Timber.tag(getClassName()).i("Permission result " + permission);
                                   if (permission.granted) {
                                       releaseCamera();
                                       camera = Camera.open(0);
                                       try {
                                           camera.setPreviewDisplay(surfaceView.getHolder());
                                           camera.startPreview();
                                       } catch (IOException e) {
                                           Timber.tag(getClassName()).i("IOException result " + e);
                                       }
                                   } else if (permission.shouldShowRequestPermissionRationale) {
                                       Toast.makeText(RxPermissionsActivity.this,
                                               "权限拒绝，但是没有点不在询问",
                                               Toast.LENGTH_SHORT).show();
                                   } else {
                                       Toast.makeText(RxPermissionsActivity.this,
                                               "用户已经点了不在询问，需要去启动设置开启权限",
                                               Toast.LENGTH_SHORT).show();
                                   }
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable t) {
                                Timber.tag(getClassName()).i("发生异常" + t);
                            }
                        },
                        new Action() {
                            @Override
                            public void run() {
                                Timber.tag(getClassName()).i("完成");
                            }
                        });


    }

    @Override
    protected void onDestroy() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseCamera();
    }

    private void releaseCamera() {
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }

    @Override
    public void onClick(View v) {
        //连续获取两个权限以上
        mRxPermissions
                .requestEach(Manifest.permission.CALL_PHONE,
                        Manifest.permission.BLUETOOTH)
                .subscribe(new Consumer<Permission>() {
                               @Override
                               public void accept(Permission permission) throws Exception {
                                   if (permission.granted) {
                                       // 获取了权限
                                       Toast.makeText(RxPermissionsActivity.this,
                                               "获取两个权限",
                                               Toast.LENGTH_SHORT).show();
                                   } else if (permission.shouldShowRequestPermissionRationale) {
                                       //没有获取权限，但是用户没有点不在询问
                                       Toast.makeText(RxPermissionsActivity.this,
                                               "权限拒绝，但是没有点不在询问",
                                               Toast.LENGTH_SHORT).show();
                                   } else {
                                       //用户已经点了不在询问，需要去启动设置开启权限
                                       Toast.makeText(RxPermissionsActivity.this,
                                               "权限拒绝，并且不能询问",
                                               Toast.LENGTH_SHORT).show();
                                   }
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable t) {
                                Timber.tag(getClassName()).i("发生异常" + t);
                            }
                        },
                        new Action() {
                            @Override
                            public void run() {
                                Timber.tag(getClassName()).i("完成");
                            }
                        });

    }
}
