package cn.cbsd.cjyfunctionlib.Func_FaceDetect.Module;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.TextureView;

import com.baidu.aip.entity.User;
import com.baidu.aip.face.AutoTexturePreviewView;
import com.baidu.aip.manager.FaceSDKManager;

import cn.cbsd.cjyfunctionlib.Func_FaceDetect.presenter.FacePresenter;


public interface IFace {

    void FaceInit(Context context, FaceSDKManager.SdkInitListener listener);

    void CameraPreview(Context context, AutoTexturePreviewView previewView, AutoTexturePreviewView previewView1, TextureView textureView, IFaceListener listener);

    void FaceIdentify();

    void FaceIdentify_model();

    void FaceVerify(String userId, String userInfo, Bitmap bitmap);

    void FaceReg(String userId, String userInfo);

    boolean FaceRegByBase64(String userId, String userInfo, String base64);

    boolean IMG_to_IMG(Bitmap bmp1, Bitmap bmp2, boolean IDCard_HeadPhoto,boolean useThread);

    void FaceSetNoAction();

    void setIdentifyStatus(int i);

    void FaceIdentifyReady();

    void PreviewCease(CeaseListener listener);

    void useRGBCamera(boolean status);

    Bitmap getGlobalBitmap();

    void SetGroupID(String groupId);

    void FaceDelete(String userId);

    void FaceVerifyAndReg(String userId, String userInfo, Bitmap bitmap);

    interface IFaceListener {
        void onText(FacePresenter.FaceAction action, FacePresenter.FaceResultType resultType, String text);

        void onBitmap(FacePresenter.FaceAction action, FacePresenter.FaceResultType resultType, Bitmap bitmap);

        void onUser(FacePresenter.FaceAction action, FacePresenter.FaceResultType resultType, User user);
    }

    interface CeaseListener {
        void CeaseCallBack();
    }


}
