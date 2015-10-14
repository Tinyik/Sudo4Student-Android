package sudo;

import javax.crypto.spec.GCMParameterSpec;

import com.example.studenttest.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;
import dataformat.SudoSheet_;

public class TestResultFragment extends Fragment {
	
	private GridLayout gridLayout;
	private View testView;
	private TextView resultTest;
	private TextView sheetName;
	
	private LayoutInflater gridInflater;
	
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
		View view = inflater.inflate(R.layout.frag_mycomments, null);
		gridInflater=LayoutInflater.from(getActivity());
		initView(view);
		
		return view;
	}
	
	private void initView(View view) {
	
		gridLayout=(GridLayout)view.findViewById(R.id.grid_layout);
				
				gridLayout.removeAllViews();
				for(String key : SudoSheet_.resultMap.keySet()){
					testView=gridInflater.inflate(R.layout.test_result, null);
					resultTest=(TextView)testView.findViewById(R.id.result_text);
					sheetName=(TextView)testView.findViewById(R.id.sheet_name);
					
					resultTest.setText(SudoSheet_.resultMap.get(key).toString());
					sheetName.setText(key);
					
					gridLayout.addView(testView);
				}
	}
	
	@Override
	public void onDestroyView() {
		gridLayout.removeAllViews();
		System.gc();
		super.onDestroyView();
	}
	
	
	@Override
	public void onDestroy() {
		System.gc();
		super.onDestroy();
	}
	


	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
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
