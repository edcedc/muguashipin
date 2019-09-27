package com.yc.mugua.view.bottomFrg;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.yc.mugua.R;
import com.yc.mugua.base.BaseBottomSheetFrag;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/29
 * Time: 14:23
 */
public class CommentBottomFrg extends BaseBottomSheetFrag implements TextView.OnEditorActionListener {

    private AppCompatEditText etText;
    private int type = 1;

    @Override
    public int bindLayout() {
        return R.layout.f_comment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetEdit);
//        int screenHeight = getScreenHeight((Activity) act);
//        int statusBarHeight = getStatusBarHeight(getContext());
//        int dialogHeight = screenHeight - statusBarHeight;
//        act.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, dialogHeight == 0 ? ViewGroup.LayoutParams.MATCH_PARENT : dialogHeight);
    }

    @Override
    public void initView(View view) {
        view.findViewById(R.id.bt_submit).setOnClickListener(view1 -> {
            String s = etText.getText().toString();
            if (StringUtils.isEmpty(s)){
                return;
            }
            if (listener != null && type == 1){
                listener.onFirstComment(s);
                dismiss();
            }else if (listener != null && type == 2){
                listener.onSecondComment(position, s);
                dismiss();
            }
        });
        etText = view.findViewById(R.id.et_text);
        etText.setOnEditorActionListener(this);
        etText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                /*if (editable.length() > 100){
                    showToast("字数过长可能无法显示");
                    return;
                }*/
            }
        });
        new Handler().postDelayed(() -> showInput(etText), 200);
    }

    /**
     * 显示键盘
     *
     * @param et 输入焦点
     */
    public void showInput(final EditText et) {
        et.requestFocus();
        InputMethodManager imm = (InputMethodManager) act.getSystemService(INPUT_METHOD_SERVICE);
        imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        close(false);
        etText.setText("");
        type = 1;
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        switch(i){
            case EditorInfo.IME_ACTION_SEND:
                String s = textView.getText().toString();
                if (StringUtils.isEmpty(s)){
                    dismiss();
                }else {
                    if (listener != null && type == 1){
                        listener.onFirstComment(s);
                        dismiss();
                    }else if (listener != null && type == 2){
                        listener.onSecondComment(position, s);
                        dismiss();
                    }
                }
                break;
        }
        return true;
    }

    private onCommentListener listener;
    public void setOnCommentListener(onCommentListener listener){
        this.listener = listener;
    }

    private String videoId;
    private int position;
    public void onSecondComment(int position, int type, String videoId) {
        this.videoId = videoId;
        this.type = type;
        this.position = position;
    }

    public interface onCommentListener{
        void onFirstComment(String text);
        void onSecondComment(int position, String text);
    }

}
