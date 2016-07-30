package com.popoaichuiniu.jacy.views;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.popoaichuiniu.jacy.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by jacy on 2015/11/16.
 */
public class RegisterDialog extends Dialog implements View.OnClickListener, DialogInterface.OnShowListener, View.OnFocusChangeListener {


    private Context context = null;
    private EditText email;
    private EditText authenticationID;
    private EditText password;
    private EditText confirmPassword;
    private boolean emailFlag;
    private boolean authenIDFlag;
    private boolean passwordFlag;
    private boolean confirmPasswordFlag;
    private String passwordString;
    private String confirmPasswordString;
    private String emailString;

    private GetAuthenticationIDButton getAuthenticationIDButton;

    public RegisterDialog(Context context, int themeResId) {


        super(context, themeResId);
        this.context = context;
        this.setOnShowListener(this);


        LinearLayout linearLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.register_dialog, null);
        Button buttonRegister = (Button) linearLayout.findViewById(R.id.register_button);
        Button buttonCancel = (Button) linearLayout.findViewById(R.id.cancel_button);
        email = (EditText) linearLayout.findViewById(R.id.email);
        authenticationID = (EditText) linearLayout.findViewById(R.id.authenticationID);
        password = (EditText) linearLayout.findViewById(R.id.password);
        confirmPassword = (EditText) linearLayout.findViewById(R.id.confirmPassword);
        getAuthenticationIDButton = (GetAuthenticationIDButton) linearLayout.findViewById(R.id.get_authenticationID_button);

        email.setOnFocusChangeListener(this);
        authenticationID.setOnFocusChangeListener(this);
        password.setOnFocusChangeListener(this);
        confirmPassword.setOnFocusChangeListener(this);
        getAuthenticationIDButton.setOnClickListener(this);

        buttonRegister.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        getWindow().setContentView(linearLayout, params);


    }

    private boolean judgeConfirmPassword() {
        boolean flag = true;
        confirmPasswordString = confirmPassword.getText().toString().trim();

        if (!confirmPasswordString.equals(passwordString)) {
            confirmPassword.setError("确认密码应与密码相同");
            flag = false;
        }
        if (flag)
            confirmPassword.setError(null);


        return flag;

    }

    private boolean judgePassword() {
        boolean flag = true;
        passwordString = password.getText().toString().trim();
        int passwordLength = passwordString.length();
        if (passwordLength < 8 || passwordLength > 16) {
            password.setError("应为8-16个字符");
            flag = false;

        }


        return flag;

    }

    private boolean judgeAuthenIDInput() {
        boolean flag = true;
        String authenIDString = authenticationID.getText().toString().trim();
        if (authenIDString.length() >= 7) {
            authenticationID.setError("数字长度应小于7个");
            flag = false;
        }
        return flag;

    }

    private boolean judgeEmailInput() {
        boolean flag = true;
        emailString = email.getText().toString().trim();
        if (emailString != null) {
            if (emailString.equals("")) {
                email.setError("邮箱不能为空");
                flag = false;

            } else {
                String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
                Pattern regex = Pattern.compile(check);
                Matcher matcher = regex.matcher(emailString);
                boolean isMatched = matcher.matches();
                if (!isMatched) {
                    email.setError("邮箱格式不对");
                    flag = false;
                }
            }

        } else {
            email.setError("邮箱不能为空");
            flag = false;
        }
        return flag;

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_button:

                this.dismiss();
                break;
            case R.id.cancel_button:
                this.dismiss();
                break;
            case R.id.get_authenticationID_button:
//                if (judgeEmailInput()) {
//                    String title = "gallop用户注册";
//                    String cotent = "尊敬的用户:\n 您好！您正在用这个邮箱进行注册gallop 跑步app用户，您的验证码是，如果这不是您的操作，请忽略。\n 来自gallop官方邮件";
//
//
//                    Mail m = new Mail("1053728265@qq.com", "icrossmyheart20!");
//
//                    String[] toArr = {"songsongsongi@126.com"};
//                    m.setTo(toArr);
//                    m.setFrom("1053728265@qq.com");
//                    m.setSubject("This is an email sent using my Mail JavaMail wrapper from an Android device.");
//                    m.setBody("Email body.");
//                    try {
//                        if (m.send()) {
//                            Toast.makeText(context, "Email was sent successfully.", Toast.LENGTH_LONG).show();
//                        } else {
//                            Toast.makeText(context, "Email was not sent.", Toast.LENGTH_LONG).show();
//                        }
//                    } catch (Exception e) {
//                        Toast.makeText(context, "There was a problem sending the email.", Toast.LENGTH_LONG).show();
//                        Log.e("MailApp", "Could not send email", e);
//                    }
//
//                    if (getAuthenticationIDButton.getCount() == -1)//邮件已经发送成功
//                    {
//                        getAuthenticationIDButton.setCount(60);
//                        getAuthenticationIDButton.setBackgroundResource(R.drawable.dialog_button2_clicked);
//                        getAuthenticationIDButton.getCountDownTimer().start();
//
//                    }
//
//
//                }

                break;

        }

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.email:
                if (!hasFocus)
                    emailFlag = judgeEmailInput();
                break;
            case R.id.authenticationID:
                if (!hasFocus)
                    authenIDFlag = judgeAuthenIDInput();
                break;
            case R.id.password:
                if (!hasFocus)
                    passwordFlag = judgePassword();
                break;
            case R.id.confirmPassword:
                if (!hasFocus)
                    confirmPasswordFlag = judgeConfirmPassword();
                break;


        }
    }

    @Override
    public void onShow(DialogInterface dialog) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(email, InputMethodManager.SHOW_IMPLICIT);
    }
}
