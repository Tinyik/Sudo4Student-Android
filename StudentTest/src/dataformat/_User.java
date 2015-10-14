package dataformat;

import java.io.Serializable;
import java.util.ArrayList;

import cn.bmob.v3.BmobUser;

public class _User extends BmobUser implements Serializable{
	
	/**  
	 *  
	 */  
	
	private static final long serialVersionUID = 1L;
	

	public ArrayList<String> Class=new ArrayList<String>();
	
	public Integer Experience;
	public String School;
	public String Subject;
	public Boolean isTeacher;
	
	//public static Integer classCount=0;
}
