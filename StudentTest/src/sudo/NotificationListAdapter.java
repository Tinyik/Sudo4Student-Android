package sudo;

import java.util.ArrayList;

import com.example.studenttest.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import dataformat.NotificationData;
import dataformat.SudoSheet_;

public class NotificationListAdapter extends BaseAdapter {

	//private Context context;
	private ArrayList<SudoSheet_> notificationList;
	private LayoutInflater inflater;
	
	public NotificationListAdapter(Context context,ArrayList<SudoSheet_> list) {
		// TODO Auto-generated constructor stub
		//this.context=context;
		notificationList=list;
		inflater=LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return notificationList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return notificationList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder=null;
		if (convertView==null) {
			viewHolder=new ViewHolder();
			convertView=inflater.inflate(R.layout.notification_list_item,null);
		
			
			
			viewHolder.teacherIdentifier=(TextView)convertView.findViewById(R.id.teacher_identifier);
			viewHolder.testName=(TextView)convertView.findViewById(R.id.test_name);
			viewHolder.totalQuestionNumber=(TextView)convertView.findViewById(R.id.total_question_number);
			
			convertView.setTag(viewHolder);
		}
		else {
			viewHolder=(ViewHolder) convertView.getTag();
		}
		
		SudoSheet_ data=(SudoSheet_) getItem(position);
		if (data!=null) {
			viewHolder.teacherIdentifier.setText(data.TeacherIndentifier);
			viewHolder.testName.setText(data.SheetName);
			viewHolder.totalQuestionNumber.setText(data.QuestionNumber+"Ã‚");
		}
	
		return convertView;
	}

	public class ViewHolder {
		public TextView teacherIdentifier;
		public TextView testName;
		public TextView totalQuestionNumber;
	}
	
	
}
