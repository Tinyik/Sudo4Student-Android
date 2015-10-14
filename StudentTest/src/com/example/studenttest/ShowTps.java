package com.example.studenttest;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class ShowTps {
	
	private static ShowTps instance;
	
	private static Toast toast;
	
	private Context context;
	
	private static boolean ISPUBLISH=false;
	
	public  void setContext(Context context){
		this.context=context;
	}
	
	public synchronized static ShowTps getInstance(){
		if (instance==null) {
			instance=new ShowTps();
		}
		return instance;
	}
	
	public void showToast(String msg) {
		if (toast==null) {
			toast=Toast.makeText(context, msg, Toast.LENGTH_SHORT);
			toast.show();
		}
		else {
			toast.setText(msg);
			toast.show();
		}
	}
	
	public static void log_E(String tag,String msg) {
		if (!ISPUBLISH) {
			Log.e(tag, msg);
		}
	}
}
