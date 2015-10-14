package sudo;

import com.example.studenttest.MainActivity;
import com.example.studenttest.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class SeeReportFragment extends Fragment {
	private ImageView seeReportButton;
	
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
		View view = inflater.inflate(R.layout.frag_discuss, null);
		seeReportButton=(ImageView)view.findViewById(R.id.check_button);
		
		seeReportButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View vie) {
				// TODO Auto-generated method stub
				switchFragment(new ReportFragment(), "Test");
			}
		});
		return view;
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
		}
	}
}
