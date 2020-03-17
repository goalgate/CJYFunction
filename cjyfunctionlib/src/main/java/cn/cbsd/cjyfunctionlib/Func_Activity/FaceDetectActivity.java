package cn.cbsd.cjyfunctionlib.Func_Activity;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.aip.entity.User;
import com.baidu.aip.face.AutoTexturePreviewView;
import com.baidu.aip.manager.FaceSDKManager;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.io.File;
import java.util.concurrent.TimeUnit;

import cn.cbsd.cjyfunctionlib.Func_FaceDetect.presenter.FacePresenter;
import cn.cbsd.cjyfunctionlib.Func_FaceDetect.view.IFaceView;
import cn.cbsd.cjyfunctionlib.R;
import cn.cbsd.cjyfunctionlib.Tools.FileUtils;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

import static cn.cbsd.cjyfunctionlib.Func_FaceDetect.Module.FaceDetectImpl.FEATURE_DATAS_UNREADY;

public class FaceDetectActivity extends Activity implements IFaceView {

    public FacePresenter fp = FacePresenter.getInstance();

    Button btn_faceReg;

    Button btn_faceBmpReg;

    Button btn_faceDelete;

    Button btn_faceVerify;

    Button btn_faceIdentifyModel;

    Button btn_faceIdentify;

    Button btn_faceNormal;

    Button btn_faceCount;

    AutoTexturePreviewView previewView;

    AutoTexturePreviewView previewView1;

    TextureView textureView;

    TextView tv_info;

    ImageView iv_headphotoRGB;

    ImageView iv_headphotoIR;

    String userId = "441302199308100538";

    String userInfo = "王振文";

    Bitmap userBmp;

    private Disposable disposableTips;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.facedetect_activity);
        userBmp = BitmapFactory.decodeResource(getResources(), R.drawable.user);
        btn_faceReg = (Button) findViewById(R.id.btn_faceReg);
        btn_faceReg.setOnClickListener(mOnClickListener);
        btn_faceBmpReg = (Button) findViewById(R.id.btn_faceBmpReg);
        btn_faceBmpReg.setOnClickListener(mOnClickListener);
        btn_faceDelete = (Button) findViewById(R.id.btn_faceDelete);
        btn_faceDelete.setOnClickListener(mOnClickListener);
        btn_faceCount = (Button) findViewById(R.id.btn_faceCount);
        btn_faceCount.setOnClickListener(mOnClickListener);
        btn_faceIdentify = (Button) findViewById(R.id.btn_faceIdentify);
        btn_faceIdentify.setOnClickListener(mOnClickListener);
        btn_faceIdentifyModel = (Button) findViewById(R.id.btn_faceIdentifyModel);
        btn_faceIdentifyModel.setOnClickListener(mOnClickListener);
        btn_faceVerify = (Button) findViewById(R.id.btn_faceVerify);
        btn_faceVerify.setOnClickListener(mOnClickListener);
        btn_faceNormal = (Button) findViewById(R.id.btn_faceNormal);
        btn_faceNormal.setOnClickListener(mOnClickListener);
        previewView = (AutoTexturePreviewView) findViewById(R.id.preview_view);
        previewView1 = (AutoTexturePreviewView) findViewById(R.id.preview_view1);
        textureView = (TextureView) findViewById(R.id.texture_view);
        tv_info = (TextView) findViewById(R.id.tv_info);
        iv_headphotoIR = (ImageView) findViewById(R.id.iv_headphotoIR);
        iv_headphotoRGB = (ImageView) findViewById(R.id.iv_headphotoRGB);
        disposableTips = RxTextView.textChanges(tv_info)
                .debounce(3, TimeUnit.SECONDS)
                .switchMap(charSequence -> Observable.just("信息展示"))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((s) -> tv_info.setText(s));
    }

    @Override
    protected void onStart() {
        super.onStart();
        fp.CameraPreview(this, previewView, previewView1, textureView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fp.FacePresenterSetView(this);
        fp.FaceIdentifyReady();
    }

    @Override
    protected void onPause() {
        super.onPause();
        fp.FacePresenterSetView(null);
        fp.FaceSetNoAction();
        fp.setIdentifyStatus(FEATURE_DATAS_UNREADY);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposableTips != null) {
            disposableTips.dispose();
        }
    }

    @Override
    public void onBackPressed() {
        fp.PreviewCease(() -> finish());
    }

    View.OnClickListener mOnClickListener = view -> {
        int vid = view.getId();
        if (vid == R.id.btn_faceReg) {
            fp.FaceVerifyAndReg(userId, userInfo, userBmp);

        } else if (vid == R.id.btn_faceBmpReg) {
            if (fp.FaceRegByBase64(userId, userInfo, FileUtils.bitmapToBase64(userBmp))) {
                tv_info.setText("人脸特征存储成功");
            } else {
                tv_info.setText("人脸特征存储失败");
            }
        } else if (vid == R.id.btn_faceDelete) {
            fp.FaceDelete(userId);
        } else if (vid == R.id.btn_faceVerify) {
            fp.FaceVerify(userId, userInfo, userBmp);
        } else if (vid == R.id.btn_faceIdentifyModel) {
            fp.FaceIdentify_model();
        } else if (vid == R.id.btn_faceIdentify) {
            fp.FaceIdentify();
        } else if (vid == R.id.btn_faceNormal) {
            fp.FaceSetNoAction();
        } else if (vid == R.id.btn_faceCount) {
            fp.FaceIdentifyReady();
        }
    };

    @Override
    public void onText(FacePresenter.FaceAction action, FacePresenter.FaceResultType resultType, String text) {
        switch (resultType) {
            case IMG_MATCH_IMG_Score:
                break;
            default:
                tv_info.setText(text);
                break;
        }
    }

    @Override
    public void onBitmap(FacePresenter.FaceAction action, FacePresenter.FaceResultType resultType, Bitmap bitmap) {
        if (resultType == FacePresenter.FaceResultType.headphotoRGB) {
            iv_headphotoRGB.setImageBitmap(bitmap);
            Observable.timer(1, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe((l) -> iv_headphotoRGB.setImageBitmap(null));
        } else if (resultType == FacePresenter.FaceResultType.headphotoIR) {
            iv_headphotoIR.setImageBitmap(bitmap);
            Observable.timer(1, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe((l) -> iv_headphotoIR.setImageBitmap(null));
        }

    }

    @Override
    public void onUser(FacePresenter.FaceAction action, FacePresenter.FaceResultType resultType, User user) {

    }


}
