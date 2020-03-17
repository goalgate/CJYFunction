package cn.cbsd.cjyfunctionlib.Func_FaceDetect.view;

import android.graphics.Bitmap;

import com.baidu.aip.entity.User;

import cn.cbsd.cjyfunctionlib.Func_FaceDetect.presenter.FacePresenter;


public interface IFaceView {
    void onText(FacePresenter.FaceAction action,FacePresenter.FaceResultType resultType, String text);

    void onBitmap(FacePresenter.FaceAction action,FacePresenter.FaceResultType resultType, Bitmap bitmap);

    void onUser(FacePresenter.FaceAction action,FacePresenter.FaceResultType resultType, User user);

}
