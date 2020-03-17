package cn.cbsd.cjyfunction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import cn.cbsd.cjyfunctionlib.Func_Activity.FaceInitActivity;
import cn.cbsd.cjyfunctionlib.Func_Activity.HttpAndCollectionBoxActivity;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tv_hello).setOnClickListener(view -> {
//            Intent intent = new Intent(MainActivity.this, CJYExtensionAndUpdateActivity.class);
//            Intent intent = new Intent(MainActivity.this, CardActivity.class);
//            Intent intent = new Intent(MainActivity.this, OutputControlActivity.class);
//            Intent intent = new Intent(MainActivity.this, FingerPrintActivity.class);
            Intent intent = new Intent(MainActivity.this, HttpAndCollectionBoxActivity.class);
//            Intent intent = new Intent(MainActivity.this, FaceInitActivity.class);
            startActivity(intent);
        });

    }

}
