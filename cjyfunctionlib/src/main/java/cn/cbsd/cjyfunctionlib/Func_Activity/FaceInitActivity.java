package cn.cbsd.cjyfunctionlib.Func_Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.baidu.aip.manager.FaceSDKManager;

import java.util.concurrent.TimeUnit;

import cn.cbsd.cjyfunctionlib.Func_FaceDetect.presenter.FacePresenter;
import cn.cbsd.cjyfunctionlib.R;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class FaceInitActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faceinit);

        FacePresenter.getInstance().FaceInit(this, new FaceSDKManager.SdkInitListener() {
            @Override
            public void initStart() {
            }

            @Override
            public void initSuccess() {
                Observable.timer(3, TimeUnit.SECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe((l) -> {

                            Intent intent = new Intent(FaceInitActivity.this, FaceDetectActivity.class);
                            startActivity(intent);

                        });
            }

            @Override
            public void initFail(int errorCode, String msg) {
                runOnUiThread(() -> Toast.makeText(FaceInitActivity.this, "加载人脸算法失败,请联网重试", Toast.LENGTH_LONG).show());
            }
        });
    }
}
