package imag.util;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;

public class ImageUtil {

	public static Bitmap getRes(Activity context, String name) {
		if (context == null)
			return null;
		int maxH = 200;
		int maxW = 200;
		ApplicationInfo appInfo = context.getApplicationInfo();
		int resID = context.getResources().getIdentifier(name, "drawable",appInfo.packageName);
		Options option = new Options();  
		//option.inJustDecodeBounds = true; 
		BitmapFactory.decodeResource(context.getResources(), resID,option);
		float hScale = option.outHeight/maxH;
		float wScale = option.outWidth/maxW;
		if(hScale > 1.0 || wScale > 1.0)
		{
			float scale = Math.max(hScale, wScale);
			option.outHeight = (int)(option.outHeight/scale);
			option.outWidth = (int)(option.outWidth/scale);
		}
		option.inJustDecodeBounds = false;
		option.inSampleSize = computeSampleSize(option,-1,maxH*maxW);
		option.inSampleSize=-1;
		option.inPurgeable = true;  
		option.inInputShareable = true; 
		return BitmapFactory.decodeResource(context.getResources(), resID,option);
	}
	
	public static Bitmap getRes(Context context, String name,int maxW,int maxH) {
		if (context == null)
			return null;
		ApplicationInfo appInfo = context.getApplicationInfo();
		int resID = context.getResources().getIdentifier(name, "drawable",appInfo.packageName);
		Options option = new Options();  
		option.inJustDecodeBounds = true; 
		BitmapFactory.decodeResource(context.getResources(), resID,option);
		option.inJustDecodeBounds = false;
		option.inSampleSize = calculateInSampleSize(option,maxW,maxH);
		option.inPurgeable = true;  
		option.inInputShareable = true; 
		Bitmap map=null;
		try {
			map=BitmapFactory.decodeResource(context.getResources(), resID,option);
		} catch (OutOfMemoryError e) {
			// TODO: handle exception
		}
		return map;
	}
	

	
	public static Bitmap getAsset(Context context, String name,int maxW,int maxH) {
		InputStream is=null;
		if (context == null)
			return null;
		try {
			Bitmap result = null;
//			String fileName = "";
			StringBuffer sb=new StringBuffer(name);
			if(sb.indexOf(".png") == -1)
				sb.append(".png");
//			else
//				fileName = name;
			is = context.getResources().getAssets().open(sb.toString());
			 BitmapFactory.Options option = new Options();  
			option.inJustDecodeBounds = true; 
			BitmapFactory.decodeStream(is,null, option);
			
			option.inJustDecodeBounds = false;
			option.inSampleSize = calculateInSampleSize(option,maxW,maxW);
			option.inPurgeable = true;  
			option.inInputShareable = true; 
			try {
				is = context.getResources().getAssets().open(sb.toString());
				result = BitmapFactory.decodeStream(is,null, option);
			} catch (OutOfMemoryError e) {
				// TODO: handle exception
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(is!=null){
				try {
					is.close();
					is=null;
				} catch (IOException e) {
					// TODO Auto-generated catch block
				}
			}
		}
	}
	
	public static Bitmap getAsset(Context context, String name,int maxW,int maxH,Bitmap.Config config) {
		InputStream is=null;
		if (context == null)
			return null;
		try {
			Bitmap result = null;
//			String fileName = "";
			StringBuffer sb=new StringBuffer(name);
			if(sb.indexOf(".png") == -1)
				sb.append(".png");
//			else
//				fileName = name;
			is = context.getResources().getAssets().open(sb.toString());
			Options option = new Options();  
			option.inJustDecodeBounds = true; 
			option.inPreferredConfig=config;
			BitmapFactory.decodeStream(is,null, option);
			
			option.inJustDecodeBounds = false;
			option.inSampleSize = calculateInSampleSize(option,maxW,maxW);
			option.inPurgeable = true;  
			option.inInputShareable = true; 
			try {
				is = context.getResources().getAssets().open(sb.toString());
				result = BitmapFactory.decodeStream(is,null, option);
			} catch (OutOfMemoryError e) {
				// TODO: handle exception
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(is!=null){
				try {
					is.close();
					is=null;
				} catch (IOException e) {
					// TODO Auto-generated catch block
				}
			}
		}
	}
	
	private static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (reqWidth > 0 && reqHeight > 0) {
			if (height > reqHeight || width > reqWidth) {

				final int halfHeight = height / 2;
				final int halfWidth = width / 2;

				// Calculate the largest inSampleSize value that is a power of 2
				// and
				// keeps both
				// height and width larger than the requested height and width.
				while ((halfHeight / inSampleSize) > reqHeight
						&& (halfWidth / inSampleSize) > reqWidth) {
					inSampleSize *= 2;
				}
			}
		}
		return inSampleSize;
	}
	
	public static Bitmap getAsset(Activity context, String name) {
		if (context == null)
			return null;
		try {
			Bitmap result = null;
			String fileName = "";
			int maxH =200;
			int maxW =200;
			if(name.indexOf(".png") == -1)
				fileName = name + ".png";
			else
				fileName = name;
			InputStream is = context.getResources().getAssets().open(fileName);
			Options option = new Options();  
			option.inJustDecodeBounds = true; 
			BitmapFactory.decodeStream(is,null, option);
			float hScale = option.outHeight/maxH;
			float wScale = option.outWidth/maxW;
			if(hScale > 1.0 || wScale > 1.0)
			{
				float scale = Math.max(hScale, wScale);
				option.outHeight = (int)(option.outHeight/scale);
				option.outWidth = (int)(option.outWidth/scale);
			}
			option.inJustDecodeBounds = false;
			option.inSampleSize = computeSampleSize(option,-1,maxH*maxW);
			option.inSampleSize=-1;
			option.inPurgeable = true;  
			option.inInputShareable = true; 
			is = context.getResources().getAssets().open(fileName);
			result = BitmapFactory.decodeStream(is,null, option);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	//鎸夊師鍥�
	public static Bitmap getAssetByOri(Activity context, String name) {
		if (context == null)
			return null;
		try {
			Bitmap result = null;
			String fileName = "";
			int maxH = 200;
			int maxW = 200;
			if(name.indexOf(".png") == -1)
				fileName = name + ".png";
			else
				fileName = name;
			InputStream is = context.getResources().getAssets().open(fileName);
			Options option = new Options();  
//			option.inJustDecodeBounds = true; 
//			BitmapFactory.decodeStream(is,null, option);
//			float hScale = option.outHeight/maxH;
//			float wScale = option.outWidth/maxW;
//			if(hScale > 1.0 || wScale > 1.0)
//			{
//				float scale = Math.max(hScale, wScale);
//				option.outHeight = (int)(option.outHeight/scale);
//				option.outWidth = (int)(option.outWidth/scale);
//			}
			option.inJustDecodeBounds = false;
			option.inSampleSize = computeSampleSize(option,-1,maxH*maxW);
			option.inSampleSize=-1;
			option.inPurgeable = true;  
			option.inInputShareable = true; 
			is = context.getResources().getAssets().open(fileName);
			result = BitmapFactory.decodeStream(is,null, option);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	private static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
	    int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
	    int roundedSize;
	    if (initialSize <= 8) {
	        roundedSize = 1;
	        while (roundedSize < initialSize) {
	            roundedSize <<= 1;
	        }
	    } else {
	        roundedSize = (initialSize + 7) / 8 * 8;
	    }
	    return roundedSize;
	}

	private static int computeInitialSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;
		int lowerBound = (maxNumOfPixels == -1) ? 1 :
		(int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 :
		(int) Math.min(Math.floor(w / minSideLength),
		Math.floor(h / minSideLength));
		if (upperBound < lowerBound) {
			// return the larger one when there is no overlapping zone.
			return lowerBound;
		}
		if ((maxNumOfPixels == -1) &&
		(minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
	//add by wj 
	public interface AnimationToolListener {
		public void onAnimationEnd();
	}
	public static void moveView(View target, int xOff, int yOff, long duration,
			AnimationToolListener listener) {
		final View targetView = target;
		final int xOffView = xOff;
		final int yOffView = yOff;
		int left = targetView.getLeft();
		int top = targetView.getTop();
		final AnimationToolListener listenerView = listener;
		TranslateAnimation move = new TranslateAnimation(left, xOffView, top,yOffView);
		move.setDuration(duration);
		move.setRepeatCount(0);
		move.setFillAfter(true);
		move.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationEnd(Animation arg0) {
				// TODO Auto-generated method stub
				targetView.clearAnimation();
				int left = targetView.getLeft() + xOffView;
				int top = targetView.getTop() + yOffView;
				targetView.layout(left,
						top, targetView.getWidth()+left,
						targetView.getHeight()+top);
				if (listenerView != null)
					listenerView.onAnimationEnd();
			}

			@Override
			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationStart(Animation arg0) {
				// TODO Auto-generated method stub

			}
		});
		targetView.startAnimation(move);
	}
}
