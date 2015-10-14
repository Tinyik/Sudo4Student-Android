package com.example.studenttest;



import java.util.List;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import sudo.LeftFragment;
import sudo.TestResultFragment;

/**
 * @description 主界面
 */
public class MainActivity extends SlidingFragmentActivity implements
		OnClickListener {

	private ImageView topButton;
	private Fragment mContent;
	private TextView topTextView;

	private int back_count=0;
	private long clickTime;
	
//	private GridLayout gridLayout;
//	
//	private LayoutInflater gridInflater;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ShowTps.getInstance().setContext(this);
		topButton = (ImageView) findViewById(R.id.topButton);
		topButton.setOnClickListener(this);
		topTextView = (TextView) findViewById(R.id.topTv);
		initSlidingMenu(savedInstanceState);
		
		
		
	}

	/**
	 * 初始化侧边栏
	 */
	private void initSlidingMenu(Bundle savedInstanceState) {
		// 如果保存的状态不为空则得到之前保存的Fragment，否则实例化MyFragment
		if (savedInstanceState != null) {
			mContent = getSupportFragmentManager().getFragment(
					savedInstanceState, "mContent");
		}

		if (mContent == null) {
			mContent = new TestResultFragment();
			switchConent(mContent,"DashBoard");
		}

		// 设置左侧滑动菜单
		setBehindContentView(R.layout.menu_frame_left);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame, new LeftFragment()).commit();

		// 实例化滑动菜单对象
		SlidingMenu sm = getSlidingMenu();
		// 设置可以左右滑动的菜单
		sm.setMode(SlidingMenu.LEFT);
		// 设置滑动阴影的宽度
		sm.setShadowWidthRes(R.dimen.shadow_width);
		// 设置滑动菜单阴影的图像资源
		sm.setShadowDrawable(null);
		// 设置滑动菜单视图的宽度
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		// 设置渐入渐出效果的值
		sm.setFadeDegree(0.35f);
		// 设置触摸屏幕的模式,这里设置为全屏
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		// 设置下方视图的在滚动时的缩放比例
		sm.setBehindScrollScale(0.0f);

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (mContent!=null) {
			getSupportFragmentManager().putFragment(outState, "mContent", mContent);
		}
		
	}

	/**
	 * 切换Fragment
	 * 
	 * @param fragment
	 */
	public void switchConent(Fragment fragment, String title) {
		mContent = fragment;
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, fragment).commit();
		//getSupportFragmentManager().beginTransaction().addToBackStack(null);
		getSlidingMenu().showContent();
		topTextView.setText(title);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.topButton:
			toggle();
			break;
		default:
			break;
		}
	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Fragment fragment=getCurrentFragment();
		if (fragment!=null) {
			if (fragment.getClass().toString().equals("class sudo.TestResultFragment")) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					back_count++;
					if (back_count==1) {
						clickTime=System.currentTimeMillis();
						ShowTps.getInstance().showToast("再次点击退出程序！");
					}
					else if(back_count==2){
						if (System.currentTimeMillis()-clickTime<1500) {
							//SysApplication.getInstance().exit();
							System.exit(0);
						}
						back_count=0;
						ShowTps.getInstance().showToast("再次点击退出程序！");
					}
					return true;
				}
			}
			else {
				switchConent(new TestResultFragment(), "DashBoard");
				return true;
			}
		}
		
		return super.onKeyDown(keyCode, event);
	}
	
	public void setActivityTitle(String titleString){
		topTextView.setText(titleString);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		
		super.onDestroy();
	}
	
	private Fragment getCurrentFragment() {
		List<Fragment> fragments=getSupportFragmentManager().getFragments();
		for (Fragment fragment : fragments) {
			if (fragment!=null&&fragment.isVisible()) {
				return fragment;
			}
		}
		return null;
	}
	
	public void initLeftMenu() {
		// 设置左侧滑动菜单
				setBehindContentView(R.layout.menu_frame_left);
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.menu_frame, new LeftFragment()).commit();

				// 实例化滑动菜单对象
				SlidingMenu sm = getSlidingMenu();
				// 设置可以左右滑动的菜单
				sm.setMode(SlidingMenu.LEFT);
				// 设置滑动阴影的宽度
				sm.setShadowWidthRes(R.dimen.shadow_width);
				// 设置滑动菜单阴影的图像资源
				sm.setShadowDrawable(null);
				// 设置滑动菜单视图的宽度
				sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
				// 设置渐入渐出效果的值
				sm.setFadeDegree(0.35f);
				// 设置触摸屏幕的模式,这里设置为全屏
				sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
				// 设置下方视图的在滚动时的缩放比例
				sm.setBehindScrollScale(0.0f);
	}
	
	
}
