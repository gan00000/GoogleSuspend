package com.starpy.v;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;

import com.core.base.utils.ApkInfoUtil;
import com.core.base.utils.ApkInstallUtil;
import com.core.base.utils.PL;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class MainActivity extends Activity {

    private Handler h;

    AlertDialog d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        d = new AlertDialog.Builder(this)

                .setNegativeButton("確認", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                        checkInstallObb();

                    }
                })

                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                        finish();
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        dialog.dismiss();
                        finish();
                    }
                })

                .setMessage("是否安裝別惹萌萌噠")
                .create();


    }

    private String renameApk;

    @Override
    protected void onResume() {
        super.onResume();

        PL.i("onResume");
        if (d != null){
            d.show();
        }

    }



    void checkInstallObb() {

        if (revertGameApk(this) && !TextUtils.isEmpty(renameApk)) {

            ApkInstallUtil.installApk(this, renameApk);

        }
    }

    protected String getCurrentVersionObbFilePath(Context context) {
        String obbFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/obb/" + this.getPackageName() +
                "/main." + ApkInfoUtil.getVersionCode(this) + "." + this.getPackageName() + ".obb";
        return obbFilePath;
    }


    protected File getObbFile(Context context) {
        String path = getCurrentVersionObbFilePath(this);
        if (!TextUtils.isEmpty(path)) {
            return new File(path);
        }
        return null;
    }

    private boolean revertGameApk(Activity activity) {

        try {
            String obbPath = getCurrentVersionObbFilePath(activity);

            File apkFile = new File(obbPath + ".apk");
            if (apkFile.exists()) {
                renameApk = apkFile.getAbsolutePath();
                return true;
            }

            File obbFile = new File(obbPath);
            if (obbFile == null) {
                return false;
            }

            if (obbFile.exists()) {
                obbFile.renameTo(apkFile);

                RandomAccessFile r = new RandomAccessFile(apkFile, "rw");
                r.seek(0); //指针设置在a后.
                r.write(new byte[]{80});
                r.close();
                renameApk = apkFile.getAbsolutePath();
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }


}
