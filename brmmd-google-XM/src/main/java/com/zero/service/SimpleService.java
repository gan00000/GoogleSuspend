package com.zero.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.star.brmmd.xm.R;
import com.zero.defenders.Localization;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
//import net.sf.json.JSONObject;

public class SimpleService extends Service{
	int id;
    private MessageThread messageThread=null;
    SimpleService service;
    PendingIntent pendingIntent;
    NotificationManager manager;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	 @Override  
	 public void onCreate() {    
			Intent intent = new Intent(this,ShowNotificationReceiver.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
	    	pendingIntent =PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
	    	manager = (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
	        
		    
	        super.onCreate();  
	    }  
	      
	@Override  
	public int onStartCommand(Intent intent, int flags, int startId) { 
		messageThread=new MessageThread();
        messageThread.start();
        service=this;
		flags = START_STICKY;
	    return super.onStartCommand(intent, flags, startId);
	 }  
	
    class MessageThread extends Thread{
        public void run() {
            while(true){
				Calendar calendar = Calendar.getInstance();           
                int w = calendar.get(Calendar.DAY_OF_WEEK)-1;  
				int hour = calendar.get(Calendar.HOUR_OF_DAY); 
                int minute =calendar.get(Calendar.MINUTE); 
                int second = calendar.get(Calendar.SECOND); 
                //Log.e("unity","閻熸粎澧楅幐鍛婃櫠閻樿鍙婃い鏍ㄧ♁琚濋梺鍝勭墳閹凤拷  "+w);
            	long ti=System.currentTimeMillis()/1000;
            	long weeks=w;
            	long days=hour*60*60+minute*60+second;
				boolean sended=false;
				if(pushInfos.size()>0 || speciallypush.size()>0)
				{
		            	if(pushInfos.size()>0)
		         	    {
		         		  for(String key : pushInfos.keySet())
		         		  {
		         			 int length=pushInfos.get(key).size();
		       			     for(int i=0;i<length;i++)
		       			     {
			       				   if(pushInfos.get(key).get(i).daystamp==days && pushInfos.get(key).get(i).week==weeks)
			       				   {
			       					   if(pushInfos.get(key).get(i).open)
			       					   {
				         				     SendMessage((CharSequence)Localization.m_shouhuzhe,(CharSequence)pushInfos.get(key).get(i).name,(CharSequence)pushInfos.get(key).get(i).content);
				         				     Log.e("unity", "闂佸憡鐟﹂崹鍧楁晸閹存帞鍔嶉悽顖氱秺閺屽懏鎷呴悜妯兼殸濠电偞鍨甸悧濠冨閸涱喚鈻曢悗锝庝簼閸婏拷  闂佹寧绋戞總鏃�绌辨繝鍥х畳妞ゆ牗鐟ㄧ粈锟� "+weeks+days+pushInfos.get(key).get(i).name);
											 sended=true;
			       					   }
			       				   }
		       			     }
		         				  
		         		  }
		         	   }
		         	   if(speciallypush.size()>0)
		         	   {
		         		   for(int i=0;i<speciallypush.size();i++)
		         		   {
		         			   if(ti==speciallypush.get(i).stamp)
		         			   {
		         				   if(speciallypush.get(i).open)
		         				   {
		         				     SendMessage((CharSequence)Localization.m_shouhuzhe,(CharSequence)speciallypush.get(i).name,speciallypush.get(i).content);
		         				    Log.e("unity", "闂佸憡鐟﹂崹鍧楁晸閹存帞鍔嶉悽顖氱秺閺屽懏鎷呴悜妯兼殸濠电偞鍨甸悧濠冨閸涱喚鈻曢悗锝庝簼閸婏拷  闂佹寧绋戞總鏃�绌辨繝鍥х畳妞ゆ牗鐟ㄧ粈锟� "+speciallypush.get(i).stamp+speciallypush.get(i).name);
									sended=true;
		         				   }
		         			   }
		         		   }
		         	   }
				}
				else
				{
					//Log.e("unity", "push infos length is 0");
					File file=new File("/data/data/"+ShowNotificationReceiver.packgename+"/files/push.txt");
					if(file.exists())
					{

						try {
						BufferedReader br=null;
							br = new BufferedReader(new FileReader(file));
						String temp=null;
							temp=br.readLine();
							//Log.e("unity", "闂佽浜介崝鎴︽晸閹存帞鍔嶉柡瀣暞缁傛帞鎹勯崫鍕殽闁诲骸缍婇敓鑺ョ缁�锟�  闂佹寧鍐婚幏锟�"+temp);
				         while(temp!=null){
				        	 JSONObject jj=null;
								jj = new JSONObject(temp);
				        	 if(jj.getBoolean("open"))
				        	 {
				        		 if(jj.has("timesatmp"))
				        		 {
				        			 if(jj.getInt("timesatmp")==(int)ti)
				        			 {
				        				 SendMessage((CharSequence)Localization.m_shouhuzhe,(CharSequence)jj.get("name"),(CharSequence)jj.get("content"));
				        				 sended=true;
				        			 }
				        		 }
				        		 else if(jj.has("week") && jj.has("daystamp"))
				        		 {
				        			 if(jj.getInt("week")==weeks && jj.getInt("daystamp")==days)
				        			 {
				        				 SendMessage((CharSequence)Localization.m_shouhuzhe,(CharSequence)jj.get("name"),(CharSequence)jj.get("content"));
				        				 sended=true;
				        			 }
				        		 }
				        	 }
				        	 temp=br.readLine();
				         }
                         br.close();
							} catch (JSONException e1) {
								e1.printStackTrace();
							}
							catch (IOException e) {
								e.printStackTrace();
							}
				}
			}
				
		         	  try {
						  Thread.sleep(1000);
						 if(sended)
						  {
								 Log.e("unity", "缂備焦宕樺▔鏇㈠煝婵傚憡鍎撻柍銉ㄥ皺鐎瑰绱掓径瀣瑨濠⒀呭█閺佸秵绗熸繝鍕紲闂佹寧鍐婚幏锟�");
							Thread.sleep(5000);
							Log.e("unity", "缂備焦宕樺▔鏇㈠煝婵傚憡鍎撻柍銉ㄥ皺鐎瑰绱掓径瀣瑨闁诡喗顨婇弫宥嗙瑹婵犲嫮锛滈梺鎸庡喕閹凤拷");
	                      }
					  } catch (InterruptedException e) {
						  Log.e("unity", "缂備焦宕樺▔鏇㈠煝婵傜姹查柛灞剧煯缁鈽夐幙鍕缂備礁顦扮敮鎺旀閹捐埖鏆滈梻鍫氭櫇绾惧ジ鏌ㄥ☉妤冪瓘缂佹唻鎷�");
						e.printStackTrace();
					  }
					 
            }
		}
    }


	@SuppressLint("NewApi")
	public void SendMessage(CharSequence tickerText,CharSequence contentTitle,CharSequence contentText)
	{
		//if(!isRunningApp())
		//{
		 Log.e("unity","闁哄鏅滅粙鎴﹀矗閸℃稑鐭楅柟杈捐閹峰嘲顫濇潏鈺佸绩闂侀潧妫楅崐鍫曟晸閽樺锟藉爼鏁撻挊澶婏拷鍫曟晸閽樺锟藉爼鏁撻弬銈嗗");
		  long when= System.currentTimeMillis();
		  Builder builder=new Notification.Builder(service);
			   builder.setContentIntent(pendingIntent)
			          .setSmallIcon(R.drawable.app_icon)
			          .setWhen(when)
			          .setTicker(tickerText)
			          .setContentTitle(contentTitle)
			          .setContentText(contentText)
			          .setAutoCancel(true);
		   Notification notification=builder.getNotification();
		   notification.defaults=Notification.DEFAULT_SOUND;
		   manager.notify(id, notification);
	       id++;
	      Log.e("unity", "闂佸憡鐟﹂崹鐢告偩椤掑嫭鐒绘慨妯夸含閸欙拷闂侀潧妫楅崐鍫曟晸閽樺锟藉爼鏁撻挊澶婏拷鐟帮耿娴ｇ硶鏌﹂柍鈺佸暞缁犳帡鏌ㄥ☉姘珢缂佹唻鎷�");
		//}
	}
	
	 boolean isRunningApp()
	    {
	    	boolean isAppRunning=false;
	    	ActivityManager am = (ActivityManager)service.getSystemService(Context.ACTIVITY_SERVICE);
	        List<RunningTaskInfo> list = am.getRunningTasks(1000);
	        for(RunningTaskInfo info:list)
	        {
	        	if(info.topActivity.getPackageName().equals(ShowNotificationReceiver.packgename))
	        	{
	        		isAppRunning=true;
	        	    break;
	        	}
	        }
	        return isAppRunning;
	    }
  static class PushData
   {
	   public int week;
	   public int daystamp;
	   public boolean open;
	   public String name;
	   public String content;
	   
	   public PushData(int weeks,int days,String name,String con,boolean op)
	   {
	        content=con;
	        open=op;
	        week=weeks;
			  daystamp=days;
			  this.name=name;
	   }
   }
  static class SpeciallyPushData
  {
	  public String key;
	  public int stamp;
	  public String name;
	  public String content;
	  public boolean open;
	  public SpeciallyPushData(String key,int stam,String name,String content,boolean op)
	  {
		  this.key=key;
		  stamp=stam;
		  this.name=name;
		  this.content=content;
		  open=op;
	  }
  }
   static List<SpeciallyPushData> speciallypush=new ArrayList<SpeciallyPushData>();
   static Map<String, List<PushData>> pushInfos=new HashMap<String, List<PushData>>();
  
   public static void setOnePush(String kk,int week,int daystamp,String name,String content,boolean state)
   {
	   Calendar calendar = Calendar.getInstance();           
       int w = calendar.get(Calendar.DAY_OF_WEEK);
	   Log.e("unity", "閻熸粎澧楅幐鍛婃櫠閻樿鍙婇柣鏂挎啞閸╁倸鈽夐幙鍕  闂佹寧鍐婚幏锟�"+w);
	   Log.e("unity", "1111111111");
	   //if(week==0) week=7;
	   PushData push=new PushData(week,daystamp,name,content,state);
	   Log.e("unity", "");
	   boolean exits=false;
	   if(pushInfos.containsKey(kk))
	   {
		   Log.e("unity", "222222222");
			int length=pushInfos.get(kk).size();
			for(int i=0;i<length;i++)
		    {
				if(pushInfos.get(kk).get(i).daystamp==daystamp && pushInfos.get(kk).get(i).week==week && pushInfos.get(kk).get(i).name.equals(name))
				{
					exits=true;
				}
			}
			Log.e("unity", "3333333333");
			  if(!exits) 
			  {
				  Log.e("unity", "666666666");
				 pushInfos.get(kk).add(push);
				 Log.e("unity", "5555555");
			  }
			  Log.e("unity", "44444444");
	   }
	   else
	   {
		   Log.e("unity", "77777777");
		   List<PushData> datas=new ArrayList<PushData>();
		   datas.add(push);
		   pushInfos.put(kk, datas);
	   }
	   Log.e("unity","婵烇絽娲︾换鍌炴偤閵娾晛瑙﹂幖杈剧悼婢跺嫰鎮楀☉娅虫垿寮抽悢鐓庣妞ゆ梻鍋撻悾閬嶆⒒閿熶粙宕归纰卞敽婵炴垶鎼幏锟� 闂佹寧鍐婚幏锟�"+pushInfos.get(kk).size());
   }
   public static void closePush(String kk)
   {
	   if(pushInfos.size()>0)
	   {
		  for(String key : pushInfos.keySet())
		  {
			  if(key.equals(kk))
			  {
				  int length=pushInfos.get(key).size();
				  for(int i=0;i<length;i++)
				  {
					  pushInfos.get(key).get(i).open=false;
				  }
			  }
		  }
	   }
	   if(speciallypush.size()>0)
	   {
		   for(int i=0;i<speciallypush.size();i++)
		   {
			   if(speciallypush.get(i).key.equals(kk))
			   {
				   speciallypush.get(i).open=false;
			   }
		   }
	   }
   }
   public static void openPush(String kk)
   {
	   if(pushInfos.size()>0)
	   {
		  for(String key : pushInfos.keySet())
		  {
			  if(key.equals(kk))
			  {
				  int length=pushInfos.get(key).size();
				  for(int i=0;i<length;i++)
				  {
					  pushInfos.get(key).get(i).open=true;
				  }
			  }
		  }
	   }
	   if(speciallypush.size()>0)
	   {
		   for(int i=0;i<speciallypush.size();i++)
		   {
			   if(speciallypush.get(i).key.equals(kk))
			   {
				   speciallypush.get(i).open=true;
			   }
		   }
	   }
   }
   public static void setDisposablePush(String kk,int timestamp,String name,String content,boolean state)
   {
	   boolean exits=false;
	   if(speciallypush.size()>0)
	   {
		   for(int i=0;i<speciallypush.size();i++)
		   {
			   if(speciallypush.get(i).key.equals(kk) && speciallypush.get(i).name.equals(name) && speciallypush.get(i).stamp==timestamp)
			   {
				   exits=true;
			   }
		   }
		   if(!exits) speciallypush.add(new SpeciallyPushData(kk,timestamp,name,content,state));
	   }
	   else
	   {
		   speciallypush.add(new SpeciallyPushData(kk,timestamp,name,content,state));
	   }
   }
   public static void RemovePushMessage(String kk)
   {
	   if(pushInfos.size()>0)
	   {
		  for(String key : pushInfos.keySet())
		  {
			  if(key.equals(kk))
			  {
			    pushInfos.get(key).clear();
			  }
		  }
	   }
	   /*
	   List<SpeciallyPushData> specially=new ArrayList<SpeciallyPushData>(speciallypush);
	   if(specially.size()>0)
	   {
		   for(int i=0;i<specially.size();i++)
		   {
			   if(specially.get(i).key.equals(kk))
			   {
				   speciallypush.remove(i);
			   }
		   }
	   }
	   */
	   Iterator<SpeciallyPushData> iterators=speciallypush.iterator();
	   while(iterators.hasNext())
	   {
		   SpeciallyPushData s=iterators.next();
		   if(s.key.equals(kk))
		   {
			   iterators.remove();
		   }
	   }
   }
   public static void ClearNotifications()
   {
	   if(pushInfos.size()>0)
	   {
		   pushInfos.clear();
	   }
	   if(speciallypush.size()>0)
	   {
		   speciallypush.clear();
	   }
	   File dir=new File("/data/data/"+ShowNotificationReceiver.packgename+"/files/push.txt");
       if(dir != null && dir.exists()) dir.delete();
	}
   
   public static void ActivityDestroy()
   {
	    if(pushInfos.size()>0 || speciallypush.size()>0)
		{
	   String path="/data/data/"+ShowNotificationReceiver.packgename+"/files/push.txt";//Environment.getDownloadCacheDirectory().getPath()+"/push.txt";//"/data/data/files/push.txt";
	   Log.e("unity","activity destroy check push length is "+pushInfos.size());
	   Log.e("unity", "闂佺懓鍢查崥瀣暜閹绢喖绀勯柣鎴炆戦梽宥夋煟閵娿儱顏繛鍙夊閵囨劙寮村宕囶槹         "+path);
	   File dir=new File(path);
       Log.e("unity", "111111111");
		try {
	       if(!dir.exists())
	       {
	    	   Log.e("unity", "22222222222");
			   dir.createNewFile();
			   Log.e("unity", "闂佽浜介崝鎴︽晸閹存帞鍔嶉柡瀣暞缁傛帡寮甸悽鐢殿啍闁诲孩绋掗…鍥р枔閹达箑鎹堕柡澶嬪缁诧拷 婵炴垶鎼幏锟� 闂佹寧鍐婚幏锟�"+dir.getAbsolutePath()+"   "+path);
	       }
	       else
	       {
	    	   Log.e("unity", "3333333333");
	    	 dir.delete();
			 dir.createNewFile();
			 Log.e("unity", "闂佽浜介崝鎴︽晸閹存帞鍔嶉柡瀣暞缁傛帡寮甸悽鐢殿啍闁诲孩绋掗…鍥р枔閹达箑鎹堕柡澶嬪缁诧拷 婵炴垶鎼幏锟� 闂佹寧鍐婚幏锟�"+dir.getAbsolutePath()+"   "+path);
	       }
		} catch (IOException e) {
			Log.e("unity", "444444444");
			e.printStackTrace();
		}
       FileOutputStream out=null;
       try {
    	   Log.e("unity", "5555555555");
		out = new FileOutputStream(path);
	   } catch (FileNotFoundException e) {
		   Log.e("unity", "66666666666");
		e.printStackTrace();
	   }
	   
	   if(out == null)
	   {
		  Log.e("unity", "SimpleService::ActivityDestroy error!!!!config file is not create!!!!path=" + path);

		   return;
	   }
       PrintStream p=new PrintStream(out);
       if(pushInfos.size()>0)
	   {
		  for(String key : pushInfos.keySet())
		  {
			  int length=pushInfos.get(key).size();
				  for(int i=0;i<length;i++)
				  {
					  JSONObject o1=new JSONObject();
					  try {
						o1.put("key", key);
						   o1.put("week", pushInfos.get(key).get(i).week);
						   o1.put("daystamp", pushInfos.get(key).get(i).daystamp);
						   o1.put("open", pushInfos.get(key).get(i).open);
						  o1.put("name", pushInfos.get(key).get(i).name);
						  o1.put("content", pushInfos.get(key).get(i).content);
					} catch (JSONException e) {
						Log.e("unity", "7777777777777");
						e.printStackTrace();
					}
					  Log.e("unity", "8888888888888");
                    p.println(o1.toString());
				  }
		  }
	   }
	   if(speciallypush.size()>0)
	   {
		   for(int i=0;i<speciallypush.size();i++)
		   {
			   JSONObject o1=new JSONObject();
				  try {
					o1.put("key", speciallypush.get(i).key);
					   o1.put("timesatmp", speciallypush.get(i).stamp);
					   o1.put("open", speciallypush.get(i).open);
					  o1.put("name", speciallypush.get(i).name);
					  o1.put("content", speciallypush.get(i).content);
				} catch (JSONException e) {
					Log.e("unity", "99999999999");
					e.printStackTrace();
				}
				  Log.e("unity", "aaaaaaaaaaa");
                p.println(o1.toString());
		   }
	   }
	   p.close();
	   try {
		out.close();
	} catch (IOException e) {
		Log.e("unity", "bbbbbbbbbbb");
		e.printStackTrace();
	}
		}
   }
    @Override  
   public void onDestroy() {  
     
        Intent sevice = new Intent(this, SimpleService.class);  
        this.startService(sevice);  
     
       super.onDestroy();  
   } 
}
