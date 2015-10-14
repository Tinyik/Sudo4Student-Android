package sudo;

import com.example.studenttest.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import dataformat.SudoSheet_;

public class ReportListViewAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater inflater;
	private SudoSheet_ sheet_;
	
	
	public ReportListViewAdapter(Context context,SudoSheet_ sheet_) {
		// TODO Auto-generated constructor stub
		this.context=context;
		this.sheet_=sheet_;
		inflater=LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return sheet_.QuestionNumber;
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
			convertView=inflater.inflate(R.layout.report_answer_listitme,null);
			viewHolder.choice_A=(ImageView)convertView.findViewById(R.id.choice_A);
			viewHolder.choice_B=(ImageView)convertView.findViewById(R.id.choice_B);
			viewHolder.choice_C=(ImageView)convertView.findViewById(R.id.choice_C);
			viewHolder.choice_D=(ImageView)convertView.findViewById(R.id.choice_D);
			viewHolder.choice_E=(ImageView)convertView.findViewById(R.id.choice_E);
			viewHolder.wrong_answer=(ImageView)convertView.findViewById(R.id.wrong_answer);
			
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
			
			if (sheet_.Answer.get(position).equals(StudentSheetListAdapter.answerList.get(position))) {
					viewHolder.setAnswer(position, true);
			}
			else {
				viewHolder.setAnswer(position, false);
			}

		}
		return convertView;
	}

	public  class ViewHolder{
		public ImageView choice_A;
		public ImageView choice_B;
		public ImageView choice_C;
		public ImageView choice_D;
		public ImageView choice_E;
		public ImageView wrong_answer;
		
		public void setAnswer(int number,boolean result){
			Drawable drawable=null;
			if (result) {
				drawable=context.getResources().getDrawable(R.drawable.choice_selected);
			}else {
				drawable=context.getResources().getDrawable(R.drawable.right_answer);
				wrong_answer.setVisibility(View.VISIBLE);
			}
			switch (number) {
			case 0:
				choice_A.setImageDrawable(drawable);
				choice_B.setImageDrawable(context.getResources().getDrawable(R.drawable.choice_b));
				choice_C.setImageDrawable(context.getResources().getDrawable(R.drawable.choice_c));
				choice_D.setImageDrawable(context.getResources().getDrawable(R.drawable.choice_d));
				choice_E.setImageDrawable(context.getResources().getDrawable(R.drawable.choice_e));
				break;
			case 1:
				choice_A.setImageDrawable(context.getResources().getDrawable(R.drawable.choice_a));
				choice_B.setImageDrawable(drawable);
				choice_C.setImageDrawable(context.getResources().getDrawable(R.drawable.choice_c));
				choice_D.setImageDrawable(context.getResources().getDrawable(R.drawable.choice_d));
				choice_E.setImageDrawable(context.getResources().getDrawable(R.drawable.choice_e));
				break;
			case 2:
				choice_A.setImageDrawable(context.getResources().getDrawable(R.drawable.choice_a));
				choice_B.setImageDrawable(context.getResources().getDrawable(R.drawable.choice_b));
				choice_C.setImageDrawable(drawable);
				choice_D.setImageDrawable(context.getResources().getDrawable(R.drawable.choice_d));
				choice_E.setImageDrawable(context.getResources().getDrawable(R.drawable.choice_e));
				break;
			case 3:
				choice_A.setImageDrawable(context.getResources().getDrawable(R.drawable.choice_a));
				choice_B.setImageDrawable(context.getResources().getDrawable(R.drawable.choice_b));
				choice_C.setImageDrawable(context.getResources().getDrawable(R.drawable.choice_c));
				choice_D.setImageDrawable(drawable);
				choice_E.setImageDrawable(context.getResources().getDrawable(R.drawable.choice_e));
				break;
			case 4:
				choice_A.setImageDrawable(context.getResources().getDrawable(R.drawable.choice_a));
				choice_B.setImageDrawable(context.getResources().getDrawable(R.drawable.choice_b));
				choice_C.setImageDrawable(context.getResources().getDrawable(R.drawable.choice_c));
				choice_D.setImageDrawable(context.getResources().getDrawable(R.drawable.choice_d));
				choice_E.setImageDrawable(drawable);
				break;
			default:
				break;
			}
			
		}
	}
	
	
}
