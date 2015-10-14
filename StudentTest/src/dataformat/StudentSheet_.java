package dataformat;

import java.io.Serializable;
import java.util.ArrayList;

import cn.bmob.v3.BmobObject;

public class StudentSheet_ extends BmobObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public  ArrayList<Integer> Answer=new ArrayList<Integer>();
	public String SheetID;
	public String StudentName;
	public Boolean isRequireChoiceE;

}
