package sudo;

import com.example.studenttest.MainActivity;
import com.example.studenttest.R;
import com.example.studenttest.ShowTps;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.UpdateListener;
import dataformat._User;

public class SettingFragment extends Fragment implements OnClickListener{
	
	private EditText passwordText;
	private EditText schoolText;
	private EditText classText;
	
	private ImageView confirmButton;
	private ImageView cancleButton;
	private ImageView logoutButton;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.setting, null);
		initView(view);
		return view;
	}
	
	private void initView(View view){
		passwordText=(EditText)view.findViewById(R.id.setting_password);
		schoolText=(EditText)view.findViewById(R.id.setting_school);
		classText=(EditText)view.findViewById(R.id.setting_class);
		
		confirmButton=(ImageView)view.findViewById(R.id.setting_confirm_button);
		cancleButton=(ImageView)view.findViewById(R.id.setting_cancle_button);
		logoutButton=(ImageView)view.findViewById(R.id.logout_button);
		
		confirmButton.setOnClickListener(this);
		cancleButton.setOnClickListener(this);
		logoutButton.setOnClickListener(this);
	}
	
	@Override
	public void onDestroyView() {
		//getActivity().findViewById(R.layout.setting).setBackgroundResource(0);
		System.gc();
		super.onDestroyView();
	}
	
	
	@Override
	public void onDestroy() {
		System.gc();
		super.onDestroy();
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.setting_cancle_button:
			switchFragment(new TestResultFragment(), "DashBoard");
			break;
		case R.id.setting_confirm_button:
			updateUserInfo();
			break;
		case R.id.logout_button:
			System.exit(0);
			break;
		default:
			break;
		}
	}
	
	private void updateUserInfo() {
		_User user=new _User();
		
		user.Class.clear();
		user.setPassword(passwordText.getText().toString());
		user.Class.add(classText.getText().toString());
		user.School=schoolText.getText().toString();
		
		user.update(getActivity(),BmobUser.getCurrentUser(getActivity()).getObjectId(), new UpdateListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				ShowTps.getInstance().showToast("ÐÞ¸Ä³É¹¦£¡");
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
	}
	
	/**
	 * ÇÐ»»fragment
	 * @param fragment
	 */
	private void switchFragment(Fragment fragment, String title) {
		if (getActivity() == null) {
			return;
		}
		if (getActivity() instanceof MainActivity) {
			MainActivity fca = (MainActivity) getActivity();
			fca.switchConent(fragment, title);
			fca.initLeftMenu();
		}
	}
}
