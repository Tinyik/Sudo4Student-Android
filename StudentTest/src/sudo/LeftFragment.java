package sudo;

import java.util.ArrayList;
import java.util.List;

import com.example.studenttest.MainActivity;
import com.example.studenttest.R;
import com.example.studenttest.ShowTps;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobQuery.CachePolicy;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.FindListener;
import dataformat.StudentSheet_;
import dataformat.SudoSheet_;
import dataformat._User;
import user.info.save.SharedPreferencesUtils;
/**
 * @description 侧边栏菜单
 */
public class LeftFragment extends Fragment implements OnClickListener{
	private View dashBoardView;
	private View notificationsView;
	private View settingsView;
	private ListView notificationsListView;
	
	private int objectsCount=0;
	private ArrayList<SudoSheet_>sheetList;
	private NotificationListAdapter notificationAdapter;
	private TextView levelText;
	private TextView expText;
	
	private _User studentUser;
	private int totalExp=0;
	private int studentCount=0;
	
	private List<StudentSheet_> studentSheet_List;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.layout_menu, null);
		findViews(view);
		queryUserInfo();
		
		return view;
	}
	
	
	public void findViews(View view) {
		dashBoardView=(View)view.findViewById(R.id.dashBoard);
		settingsView=(View)view.findViewById(R.id.settings);
		notificationsView=(View)view.findViewById(R.id.notifications);
		notificationsListView=(ListView)view.findViewById(R.id.notification_list);
		
		levelText=(TextView)view.findViewById(R.id.level);
		expText=(TextView)view.findViewById(R.id.exp_value);
		
		dashBoardView.setOnClickListener(this);
		settingsView.setOnClickListener(this);
		notificationsView.setOnClickListener(this);
		
		//notificationsListView.setAdapter(new NotificationListAdapter(getActivity(), list));
		notificationsListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				SudoSheet_.choosenSheet=sheetList.get(position);
				SharedPreferencesUtils.setParam(getActivity(), "sheetId", sheetList.get(position).getObjectId());
				Fragment newContent = null;
				newContent=new StudentSheetFragment();
				switchFragment(newContent, "DashBoard");
			}
		});
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

	@Override
	public void onClick(View v) {
		Fragment newContent = null;
		String title = null;
		switch (v.getId()) {
		case R.id.dashBoard: // 今日
			newContent = new TestResultFragment();
			title = getString(R.string.today);
			break;
		case R.id.settings:// 往期列表
			newContent = new SettingFragment();
			title = getString(R.string.lastList);
			break;
		default:
			break;
		}
		if (newContent != null) {
			switchFragment(newContent, title);
		}
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
		}
	}
	
	private void querySudoSheet(){
		final BmobQuery <SudoSheet_> bmobQuery	 = new BmobQuery<SudoSheet_>();
		bmobQuery.addWhereEqualTo("School",studentUser.School);
		bmobQuery.setLimit(objectsCount);
		bmobQuery.order("createdAt");
		//先判断是否有缓存
		boolean isCache = bmobQuery.hasCachedResult(getActivity(),SudoSheet_.class);
		if(isCache){
			bmobQuery.setCachePolicy(CachePolicy.CACHE_ELSE_NETWORK);	// 先从缓存取数据，如果没有的话，再从网络取�?
		}else{
			bmobQuery.setCachePolicy(CachePolicy.NETWORK_ELSE_CACHE);	// 如果没有缓存的话，则先从网络中取
		}
		bmobQuery.findObjects(getActivity(), new FindListener<SudoSheet_>() {

			@Override
			public void onSuccess(List<SudoSheet_> list) {
				// TODO Auto-generated method stub
				
				ArrayList<SudoSheet_> tempList=new ArrayList<SudoSheet_>();
				for (int i = 0; i < list.size(); i++) {
					for (int j = 0; j <studentUser.Class.size() ; j++) {
						if (list.get(i).Class.size()>0) {
							if (list.get(i).Class.contains(studentUser.Class.get(j))&&!list.get(i).isSessionEnded) {
								tempList.add(list.get(i));
							}
						}
					}
				}
				for (int i = 0; i < tempList.size(); i++) {
					for (int j = 0; j < studentSheet_List.size(); j++) {
						if (studentSheet_List.get(j).SheetID.equals(tempList.get(i).getObjectId())
								&&studentSheet_List.get(j).StudentName.equals(BmobUser.getCurrentUser(getActivity()).getUsername())) {
							tempList.remove(i);
							if (i>0) {
								i--;
							}else {
								i=0;
							}
							
						}
					}
				}
				sheetList=(ArrayList<SudoSheet_>)tempList;
				notificationAdapter=new NotificationListAdapter(getActivity(), sheetList);
				notificationsListView.setAdapter(notificationAdapter);
				
			}

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				ShowTps.getInstance().showToast(arg1);
			}
		});
	}
	
	private void queryObjectsCount() {
		final BmobQuery <SudoSheet_> bmobQuery	 = new BmobQuery<SudoSheet_>();
		bmobQuery.addWhereEqualTo("School",studentUser.School);
		
		bmobQuery.count(getActivity(),SudoSheet_.class,new CountListener() {
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				ShowTps.getInstance().showToast(arg1);
			}
			
			@Override
			public void onSuccess(int arg0) {
				// TODO Auto-generated method stub
				//ShowTps.log_E(TAG,String.valueOf(arg0));
				objectsCount=arg0;
				querySudoSheet();
			}
		});
	}
	private void  queryStudentCount() {
		final BmobQuery<StudentSheet_> bmobQuery=new BmobQuery<StudentSheet_>();
		bmobQuery.addWhereEqualTo("StudentName", studentUser.getUsername());
		bmobQuery.count(getActivity(), StudentSheet_.class, new CountListener() {
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onSuccess(int arg0) {
				// TODO Auto-generated method stub
				studentCount=arg0;
				queryStudentSheet();
				
			}
		});
	}
	
	private void queryStudentSheet(){
		final BmobQuery<StudentSheet_> bmobQuery=new BmobQuery<StudentSheet_>();
		bmobQuery.addWhereEqualTo("StudentName", studentUser.getUsername());
		bmobQuery.setLimit(studentCount);
		bmobQuery.order("createdAt");
		//先判断是否有缓存
		boolean isCache = bmobQuery.hasCachedResult(getActivity(),StudentSheet_.class);
		if(isCache){
			bmobQuery.setCachePolicy(CachePolicy.CACHE_ELSE_NETWORK);	// 先从缓存取数据，如果没有的话，再从网络取�?
		}else{
			bmobQuery.setCachePolicy(CachePolicy.NETWORK_ELSE_CACHE);	// 如果没有缓存的话，则先从网络中取
		}
		bmobQuery.findObjects(getActivity(), new FindListener<StudentSheet_>() {

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(List<StudentSheet_> list) {
				// TODO Auto-generated method stub
				queryObjectsCount();
				studentSheet_List=list;
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
				SharedPreferencesUtils.setParam(getActivity(), "school_name",studentUser.School);
				queryStudentCount();
				
				
				levelText.setText("Level   "+getLevel(studentUser.Experience));
				expText.setText(studentUser.Experience+" / "+totalExp);
			}
			
			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				ShowTps.getInstance().showToast(arg1);
			}
		});
	}
	
	private Integer getLevel(Integer experience){
		Integer level=0;
		if (experience<5) {
			level=0;
			totalExp=5;
		}else if (experience<20) {
			level=1;
			totalExp=20;
		}else if (experience<50) {
			level=2;
			totalExp=50;
		}else if (experience<100) {
			level=3;
			totalExp=100;
		}else if (experience<200) {
			level=4;
			totalExp=200;
		}else if (experience<400) {
			level=5;
			totalExp=400;
		}else if (experience<800) {
			level=6;
			totalExp=800;
		}else if (experience<1500) {
			level=7;
			totalExp=1500;
		}else if (experience<2500) {
			level=8;
			totalExp=2500;
		}else if (experience<4000) {
			level=9;
			totalExp=4000;
		}else {
			level=10;
			totalExp=6000;
		}
		return level;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
	
	
}
