package dataformat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import cn.bmob.v3.BmobObject;

public class SudoSheet_ extends BmobObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//public String objectId;
	public boolean isRequireChoiceE;
	public Integer requiredResponseNo;
	public Integer QuestionNumber;
	public String TeacherIndentifier;
	public String School;
	
	public String SheetName;
	public Boolean isAnswerVisible;
	public Boolean isSessionEnded;
	public ArrayList<Integer> Answer=new ArrayList<Integer>();
	public ArrayList<String> Class=new ArrayList<String>();
	
	public static SudoSheet_ choosenSheet=null;
	public static HashMap<String, Integer> resultMap=new HashMap<String, Integer>();
//	public String createdAtDate;
//	public String updateAtDate;

//	public SudoSheet_(){
//		this.setTableName("SudoSheet_");
//	}
}
