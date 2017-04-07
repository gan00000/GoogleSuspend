package com.zero.defenders;


import com.star.mrmmd.tw.R;

public class Localization {
	UnityPlayerNativeActivity me = null;
	
	static public String m_sure = null;
	static public String m_cancel = null;
	static public String m_shouhuzhe = null;
	
	static public String TuiSong_title0;
	static public String TuiSong_content0;
	static public String TuiSong_title1;
	static public String TuiSong_content1;
	static public String TuiSong_title2;
	static public String TuiSong_content2;
	static public String TuiSong_title3;
	static public String TuiSong_content3;
	static public String TuiSong_title4;
	static public String TuiSong_content4;
	static public String TuiSong_title5;
	static public String TuiSong_content5;
	
	static public String m_paysucce = null;
	static public String m_paycal = null;
	static public String m_payfail = null;
	
	
	public Localization(UnityPlayerNativeActivity activity)
	{
		me = activity;
		m_sure=(String) me.getResources().getText(R.string.queding); 
		m_cancel = (String) me.getResources().getText(R.string.quxiao);
		m_shouhuzhe = (String) me.getResources().getText(R.string.Shouhuzhemen);
		
		
		TuiSong_title0 = (String) me.getResources().getText(R.string.TuiSong_title0); 
		TuiSong_content0 = (String) me.getResources().getText(R.string.TuiSong_content0); 
		TuiSong_title1 = (String) me.getResources().getText(R.string.TuiSong_title1); 
		TuiSong_content1 = (String) me.getResources().getText(R.string.TuiSong_content1); 
		TuiSong_title2 = (String) me.getResources().getText(R.string.TuiSong_title2);
		TuiSong_content2 = (String) me.getResources().getText(R.string.TuiSong_content2); 
		TuiSong_title3 = (String) me.getResources().getText(R.string.TuiSong_title3); 
		TuiSong_content3 = (String) me.getResources().getText(R.string.TuiSong_content3); 
		TuiSong_title4 = (String) me.getResources().getText(R.string.TuiSong_title4); 
		TuiSong_content4 = (String) me.getResources().getText(R.string.TuiSong_content4); 
		TuiSong_title5 = (String) me.getResources().getText(R.string.TuiSong_title5); 
		TuiSong_content5 = (String) me.getResources().getText(R.string.TuiSong_content5); 
	    
		
	}
	
}
