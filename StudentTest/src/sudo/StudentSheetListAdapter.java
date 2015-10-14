package sudo;

import java.util.ArrayList;

import com.example.studenttest.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import dataformat.SudoSheet_;

public class StudentSheetListAdapter extends BaseAdapter{

	private Context context;
	private LayoutInflater inflater;
	private SudoSheet_ sheet_;
	
	public static ArrayList<Integer> answerList=new ArrayList<Integer>();
	
	 public StudentSheetListAdapter(Context context,SudoSheet_ sheet_) {
		// TODO Auto-generated constructor stub
		 this.sheet_=sheet_;
		 this.context=context;
		 //sheetList=list;
		 inflater=LayoutInflater.from(context);
		 
		 answerList.clear();
		 
		 for (int i = 0; i < SudoSheet_.choosenSheet.QuestionNumber; i++) {
			 answerList.add(i,0);
		}
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return sheet_.QuestionNumber;
		//return sheet_.QuestionNumber;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
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
			convertView=inflater.inflate(R.layout.answer_list_item, null);
			viewHolder.choice_A=(ImageView)convertView.findViewById(R.id.choice_A);
			viewHolder.choice_B=(ImageView)convertView.findViewById(R.id.choice_B);
			viewHolder.choice_C=(ImageView)convertView.findViewById(R.id.choice_C);
			viewHolder.choice_D=(ImageView)convertView.findViewById(R.id.choice_D);
			viewHolder.choice_E=(ImageView)convertView.findViewById(R.id.choice_E);
			viewHolder.question_number=(TextView)convertView.findViewById(R.id.question_number);
			
			viewHolder.setChoiceOnClickListener();
			
			convertView.setTag(viewHolder);
		}
		else {
			viewHolder=(ViewHolder) convertView.getTag();
		}
		
		
		if (sheet_!=null) {
			if (sheet_.isRequireChoiceE) {
				viewHolder.choice_E.setVisibility(View.VISIBLE);
			}
			else {
				viewHolder.choice_E.setVisibility(View.GONE);
			}
			viewHolder.question_number.setText(String.valueOf(position+1));
		}
		return convertView;
	}

	public class ViewHolder implements OnClickListener{

		public ImageView choice_A;
		public ImageView choice_B;
		public ImageView choice_C;
		public ImageView choice_D;
		public ImageView choice_E;
		public TextView question_number;
		
		
		public void setChoiceOnClickListener() {
			if (choice_A!=null) {
				choice_A.setOnClickListener(this);
			}
			if (choice_B!=null) {
				choice_B.setOnClickListener(this);
			}
			if (choice_C!=null) {
				choice_C.setOnClickListener(this);
			}
			if (choice_D!=null) {
				choice_D.setOnClickListener(this);
			}
			if (choice_E!=null) {
				choice_E.setOnClickListener(this);
			}
			
		}
		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
		
			switch (view.getId()) {
			case R.id.choice_A:
				choice_A.setImageDrawable(context.getResources().getDrawable(R.drawable.choice_selected));
				choice_B.setImageDrawable(context.getResources().getDrawable(R.drawable.choice_b));
				choice_C.setImageDrawable(context.getResources().getDrawable(R.drawable.choice_c));
				choice_D.setImageDrawable(context.getResources().getDrawable(R.drawable.choice_d));
				choice_E.setImageDrawable(context.getResources().getDrawable(R.drawable.choice_e));
				
				answerList.set(Integer.valueOf(question_number.getText().toString())-1,1);
				break;
			case R.id.choice_B:
				choice_B.setImageDrawable(context.getResources().getDrawable(R.drawable.choice_selected));
				choice_A.setImageDrawable(context.getResources().getDrawable(R.drawable.choice_a));
				choice_C.setImageDrawable(context.getResources().getDrawable(R.drawable.choice_c));
				choice_D.setImageDrawable(context.getResources().getDrawable(R.drawable.choice_d));
				choice_E.setImageDrawable(context.getResources().getDrawable(R.drawable.choice_e));
				
				answerList.set(Integer.valueOf(question_number.getText().toString())-1,2);
				break;
			case R.id.choice_C:
				choice_C.setImageDrawable(context.getResources().getDrawable(R.drawable.choice_selected));
				choice_B.setImageDrawable(context.getResources().getDrawable(R.drawable.choice_b));
				choice_A.setImageDrawable(context.getResources().getDrawable(R.drawable.choice_a));
				choice_D.setImageDrawable(context.getResources().getDrawable(R.drawable.choice_d));
				choice_E.setImageDrawable(context.getResources().getDrawable(R.drawable.choice_e));
				answerList.set(Integer.valueOf(question_number.getText().toString())-1,3);
				break;
			case R.id.choice_D:
				choice_D.setImageDrawable(context.getResources().getDrawable(R.drawable.choice_selected));
				choice_B.setImageDrawable(context.getResources().getDrawable(R.drawable.choice_b));
				choice_C.setImageDrawable(context.getResources().getDrawable(R.drawable.choice_c));
				choice_A.setImageDrawable(context.getResources().getDrawable(R.drawable.choice_a));
				choice_E.setImageDrawable(context.getResources().getDrawable(R.drawable.choice_e));
				
				answerList.set(Integer.valueOf(question_number.getText().toString())-1,4);
				break;
			case R.id.choice_E:
				choice_E.setImageDrawable(context.getResources().getDrawable(R.drawable.choice_selected));
				choice_B.setImageDrawable(context.getResources().getDrawable(R.drawable.choice_b));
				choice_C.setImageDrawable(context.getResources().getDrawable(R.drawable.choice_c));
				choice_D.setImageDrawable(context.getResources().getDrawable(R.drawable.choice_d));
				choice_A.setImageDrawable(context.getResources().getDrawable(R.drawable.choice_a));
				
				answerList.set(Integer.valueOf(question_number.getText().toString())-1,5);
				break;
			default:
				answerList.set(Integer.valueOf(question_number.getText().toString())-1,0);
				break;
				
				
			}
		}
		
		
	}
}
