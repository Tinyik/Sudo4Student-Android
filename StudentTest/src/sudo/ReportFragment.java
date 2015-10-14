package sudo;

import java.util.List;

import com.example.studenttest.R;
import com.example.studenttest.ShowTps;

import android.R.drawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import dataformat.SudoSheet_;
import dataformat._User;
import user.info.save.SharedPreferencesUtils;

public class ReportFragment extends Fragment {
	private TextView reportScore;
	private ListView answerListView;
	private ReportListViewAdapter adapter;
	
	private Integer rightAnswerNumber=0;
	
	private _User studentUser;
	
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
		View view = inflater.inflate(R.layout.frag_myfavorities, null);
		
		
		initView(view);
		
		return view;
	}
	
	private void initView(View view) {
		
		reportScore=(TextView)view.findViewById(R.id.report_score);
		answerListView=(ListView)view.findViewById(R.id.answer_list);
		adapter=new ReportListViewAdapter(getActivity(),SudoSheet_.choosenSheet);
		answerListView.setAdapter(adapter);
		
		reportScore.setText(getRightAnswerPercent().toString()+"%");
	}
	
	@Override
	public void onDestroyView() {
		System.gc();
		super.onDestroyView();
	}
	
	
	@Override
	public void onDestroy() {
		System.gc();
		
		super.onDestroy();
	}
	
	private  Integer getRightAnswerPercent() {
		for (int i = 0; i < StudentSheetListAdapter.answerList.size(); i++) {
			if (StudentSheetListAdapter.answerList.get(i)==SudoSheet_.choosenSheet.Answer.get(i)) {
				rightAnswerNumber++;
			}
		}
		SudoSheet_.resultMap.put(SudoSheet_.choosenSheet.SheetName, (rightAnswerNumber*100)/SudoSheet_.choosenSheet.QuestionNumber);
		SharedPreferencesUtils.setParam(getActivity(), "exp", Integer.valueOf(rightAnswerNumber*5+SudoSheet_.choosenSheet.QuestionNumber*3));
		queryUserInfo();
		return (rightAnswerNumber*100)/SudoSheet_.choosenSheet.QuestionNumber;
	} 
	
	private void updateUserInfo() {
		_User user=new _User();
		
		user.Experience=Integer.valueOf((Integer)SharedPreferencesUtils.getParam(getActivity(), "exp",Integer.valueOf(0))+studentUser.Experience);
		user.Class=studentUser.Class;
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
	
	private void queryUserInfo(){
		final BmobQuery <_User> bmobQuery	 = new BmobQuery<_User>();
		bmobQuery.addWhereEqualTo("username",SharedPreferencesUtils.getParam(getActivity(), "user_name",""));
		//bmobQuery.setLimit(objectsCount);
		bmobQuery.order("createdAt");
		
		bmobQuery.findObjects(getActivity(), new FindListener<_User>() {

			@Override
			public void onSuccess(List<_User> student) {
				// TODO Auto-generated method stub
				studentUser=student.get(0);
				updateUserInfo();
			}
			
			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				ShowTps.getInstance().showToast(arg1);
			}
		});
	}
	
	
}
