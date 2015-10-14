package com.example.studenttest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.VerifySMSCodeListener;
import dataformat._User;
import user.info.save.SharedPreferencesUtils;

public class LoginActivity extends ActionBarActivity implements OnClickListener{
	public static String APPID = "9c95eb1ccd7d852f243ce1cb01769f37";
	private ImageView loginImage;
	private ImageView registerImage;
	
	private LinearLayout loginLinearLayout;
	private LinearLayout registerLinearLayout;
	private LinearLayout baseLinearLayout;
	private LinearLayout registerConfirmLayout;
	
	
	private ClearEditText userName;
	private ClearEditText passWord;
	
	private ClearEditText phoneNumberText;
	private ClearEditText phoneCodeText;
	private Button registerButton;
	
	private TextView codeText;
	
	private Button loginButton;
	
	private EditText confirmPassword;
	private EditText confirmName;
	private EditText confirmClass;
	private EditText confirmSchool;
	private Button confirmButton;
	
	private Toast toast=null;
	private int secondTimeCount=59;
	
	private Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			//super.handleMessage(msg);
			codeText.setText(msg.obj.toString());
		}
		
	};
	
	private Runnable runnable=new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (secondTimeCount>0) {
				handler.postDelayed(runnable, 1000);
				Message message=new Message();
				message.obj=secondTimeCount--;
				handler.sendMessage(message);
			}else {
				codeText.setClickable(true);
				codeText.setText("Verfication");
			}
		}
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login_activity);
		
		SharedPreferencesUtils.setParam(getApplicationContext(), "exp", 0);
		SharedPreferencesUtils.setParam(getApplicationContext(), "sheetId", "");
		
		InitView();
		
		
		Bmob.initialize(this,APPID);
	}

	private void InitView() {
		loginLinearLayout=(LinearLayout)findViewById(R.id.login_layout);
		registerLinearLayout=(LinearLayout)findViewById(R.id.register_layout);
		baseLinearLayout=(LinearLayout)findViewById(R.id.base_linearLayout);
		registerConfirmLayout=(LinearLayout)findViewById(R.id.register_confirm_linearLayout);
		
		baseLinearLayout.setVisibility(View.VISIBLE);
		registerConfirmLayout.setVisibility(View.GONE);
		
		
		loginLinearLayout.setVisibility(View.VISIBLE);
		registerLinearLayout.setVisibility(View.GONE);
		
		loginImage=(ImageView)findViewById(R.id.login_btn);
		registerImage=(ImageView)findViewById(R.id.register_btn);
		
		
		loginImage.setImageDrawable(getResources().getDrawable(R.drawable.loginbtn_selected));
		loginImage.setOnClickListener(this);
		registerImage.setOnClickListener(this);
		
		userName=(ClearEditText)findViewById(R.id.usr_name);
		passWord=(ClearEditText)findViewById(R.id.password_text);
		
		
		codeText=(TextView)findViewById(R.id.code_text);
		phoneNumberText=(ClearEditText)findViewById(R.id.phone_number_text);
		phoneCodeText=(ClearEditText)findViewById(R.id.phone_code);
		registerButton=(Button)findViewById(R.id.register_button);
		
		registerButton.setOnClickListener(this);
		codeText.setOnClickListener(this);
		
		loginButton=(Button)findViewById(R.id.login_button);
		loginButton.setOnClickListener(this);
		
		
		confirmName=(EditText)findViewById(R.id.confirm_name);
		confirmClass=(EditText)findViewById(R.id.confirm_class);
		confirmPassword=(EditText)findViewById(R.id.confirm_password);
		confirmSchool=(EditText)findViewById(R.id.confirm_school);
		confirmButton=(Button)findViewById(R.id.confirm_button);
		
		confirmButton.setOnClickListener(this);
		
	}
	
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.login_btn:
			loginLinearLayout.setVisibility(View.VISIBLE);
			registerLinearLayout.setVisibility(View.GONE);
			loginImage.setImageDrawable(getResources().getDrawable(R.drawable.loginbtn_selected));
			registerImage.setImageDrawable(getResources().getDrawable(R.drawable.regibtn));
			break;
		case R.id.register_btn:
			loginLinearLayout.setVisibility(View.GONE);
			registerLinearLayout.setVisibility(View.VISIBLE);
			loginImage.setImageDrawable(getResources().getDrawable(R.drawable.loginbtn));
			registerImage.setImageDrawable(getResources().getDrawable(R.drawable.regibtn_selected));
			break;
		case R.id.register_button:
			verifySmsCode();
//			baseLinearLayout.setVisibility(View.GONE);
//			registerConfirmLayout.setVisibility(View.VISIBLE);
			break;
		case R.id.login_button:
			login();
			break;
		case R.id.code_text:
			requestSmsCode();
			
			break;
		case R.id.confirm_button:
			signUp();
			
			break;
		default:
			break;
		}
		
	}
	
	private void login(){
		final BmobUser bmUser=new BmobUser();
		bmUser.setUsername(userName.getText().toString());
		bmUser.setPassword(passWord.getText().toString());
		
		bmUser.login(this, new SaveListener() {

			@Override
			public void onFailure(int code, String msg) {
				// TODO Auto-generated method stub
				showToast(msg);
			}

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				showToast("登陆成功");
				
				SharedPreferencesUtils.setParam(getApplicationContext(), "user_name",userName.getText().toString());
				SharedPreferencesUtils.setParam(getApplicationContext(), "password", passWord.getText().toString());
				Intent intent=new Intent();
				intent.setClass(getApplicationContext(),MainActivity.class);
				LoginActivity.this.startActivity(intent);
				LoginActivity.this.finish();
			}
			
		});
	}
	
	private void signUp() {
		final _User myUser = new _User();
		myUser.setUsername(confirmName.getText().toString());
		myUser.setPassword(confirmPassword.getText().toString());
		myUser.setMobilePhoneNumber(phoneNumberText.getText().toString());
		myUser.Class.add(confirmClass.getText().toString());
		myUser.School=confirmSchool.getText().toString();
		myUser.Experience=0;
		
		myUser.signUp(this, new SaveListener() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
//				toast("注册成功:" + myUser.getUsername() + "-"
//						+ myUser.getObjectId() + "-" + myUser.getCreatedAt()
//						+ "-" + myUser.getSessionToken()+",是否验证："+myUser.getEmailVerified());
				showToast("注册成功");
				
				baseLinearLayout.setVisibility(View.VISIBLE);
				registerConfirmLayout.setVisibility(View.GONE);
				loginLinearLayout.setVisibility(View.VISIBLE);
				registerLinearLayout.setVisibility(View.GONE);
				loginImage.setImageDrawable(getResources().getDrawable(R.drawable.loginbtn_selected));
				registerImage.setImageDrawable(getResources().getDrawable(R.drawable.regibtn));
				
				//SharedPreferencesUtils.setParam(getApplicationContext(), "school", confirmSchool.getText().toString());
			}

			@Override
			public void onFailure(int code, String msg) {
//				// TODO Auto-generated method stub
//				toast("注册失败:" + msg);
				showToast("注册失败:" + msg);
			}
		});
	}
	
	/** 请求短信验证码 
	 * @method requestSmsCode    
	 * @return void  
	 * @exception   
	 */
	private void requestSmsCode(){
		String number = phoneNumberText.getText().toString();
		if(!TextUtils.isEmpty(number)){
			
			codeText.setClickable(false);
			handler.post(runnable);
			codeText.setText("59");
			secondTimeCount=59;
			
			BmobSMS.requestSMSCode(this, number, "注册模板",new RequestSMSCodeListener() {
				
				@Override
				public void done(Integer smsId,BmobException ex) {
					// TODO Auto-generated method stub
					if(ex==null){//验证码发送成功
						showToast("验证码发送成功，短信id："+smsId);
					}else{
						showToast("errorCode = "+ex.getErrorCode()+",errorMsg = "+ex.getLocalizedMessage());
					}
				}
			});
		}else{
			showToast("请输入手机号！");
		}
	}
	
	/** 验证短信验证码 
	 * @method requestSmsCode    
	 * @return void  
	 * @exception   
	 */
	private void verifySmsCode(){
		String number = phoneNumberText.getText().toString();
		String code = phoneCodeText.getText().toString();
		if(!TextUtils.isEmpty(number)&&!TextUtils.isEmpty(code)){
			BmobSMS.verifySmsCode(LoginActivity.this,number,code, new VerifySMSCodeListener() {
				
				@Override
				public void done(BmobException ex) {
					// TODO Auto-generated method stub
					if(ex==null){//短信验证码已验证成功
						showToast("验证通过");
						baseLinearLayout.setVisibility(View.GONE);
						registerConfirmLayout.setVisibility(View.VISIBLE);
					}else{
						showToast("验证失败：code ="+ex.getErrorCode()+",msg = "+ex.getLocalizedMessage());
					}
				}
			});
		}else{
			showToast("请输入手机号和验证码");
		}
	}
	
	private void showToast(String msg){
		if(toast==null){
			toast=Toast.makeText(getApplicationContext(),msg,
					Toast.LENGTH_SHORT);//用于查询本次短信发送详情
			toast.show();
		}
		else {
			toast.setText(msg);
			toast.show();
		}
	}
	
	
}
