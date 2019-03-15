package com.luffy.utils;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.luffy.utilslib.utils.AppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AppActivity extends AppCompatActivity {

    @BindView(R.id.edit)
    EditText edit;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.img_icon)
    ImageView imgIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_get)
    public void onViewClicked() {
        /*获取信息*/
        String appName = AppUtils.getInstance().getAppName(this, edit.getText().toString());
        Drawable appIcon = AppUtils.getInstance().getAppIcon(this, edit.getText().toString());
        /*绑定信息*/
        txtName.setText(appName);
        imgIcon.setImageDrawable(appIcon);
    }
}
