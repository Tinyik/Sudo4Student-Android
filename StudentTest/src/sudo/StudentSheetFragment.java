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
import android.widget.ImageView;
import android.widget.ListView;
import cn.bmob.v3.listener.SaveListener;
import dataformat.StudentSheet_;
import dataformat.SudoSheet_;
import user.info.save.SharedPreferencesUtils;

public class StudentSheetFragment extends Fragment implements OnClickListener{
	public static String TAG = "bmob";
	private ImageView sendButton;
	private ImageView cancleButton;
	private ListView answerListView;
	private StudentSheetListAdapter aSheetListAdapter;
	
	
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
		View view = inflater.inflate(R.layout.student_sheet, null);
		
		//gridInflater=LayoutInflater.from(getActivity());
		ShowTps.getInstance().setContext(getActivity());
		initView(view);
		//queryObjectsCount();
		//queryObjects();
		return view;
	}
	
	private void initView(View view) {
		//sheetLayout=(LinearLayout)view.findViewById(R.id.sheet_layout);
		//gridLayout=(GridLayout)view.findViewById(R.id.grid_layout);
		
		sendButton=(ImageView)view.findViewById(R.id.send_button);
		cancleButton=(ImageView)view.findViewById(R.id.cancle_button);
		answerListView=(ListView)view.findViewById(R.id.answer_list);
		
		
		sendButton.setOnClickListener(this);
		cancleButton.setOnClickListener(this);
		
		if (SudoSheet_.choosenSheet!=null) {
			aSheetListAdapter=new StudentSheetListAdapter(getActivity(), SudoSheet_.choosenSheet);
			answerListView.setAdapter(aSheetListAdapter);
			((MainActivity)getActivity()).setActivityTitle(SudoSheet_.choosenSheet.SheetName);
		}
	}
	
	@Override
	public void onDestroyView() {
		//getActivity().findViewById(R.layout.student_sheet).setBackgroundResource(0);
		System.gc();
		super.onDestroyView();
	}
	
	
	@Override
	public void onDestroy() {
		System.gc();
		super.onDestroy();
	}
	
	


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.cancle_button:
			
			break;
		case R.id.send_button:
			submitData();
			
			break;
		
		default:
			break;
		}
		
	}
	
	private void submitData() {
		StudentSheet_ studentSheet_=new StudentSheet_();
		
		studentSheet_.StudentName=SharedPreferencesUtils.getParam(getActivity(), "user_name", "").toString();
		studentSheet_.Answer=StudentSheetListAdapter.answerList;
		studentSheet_.SheetID=SharedPreferencesUtils.getParam(getActivity(), "sheetId", "").toString();
		studentSheet_.save(getActivity(), new SaveListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				ShowTps.getInstance().showToast("提交成功！");
				switchFragment(new SeeReportFragment(), "Test");
				
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				ShowTps.getInstance().showToast(arg1);
				ShowTps.log_E("连接超时信息", arg1);
			}
		});
	}
	
	/**
	 * 切换fragment
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
