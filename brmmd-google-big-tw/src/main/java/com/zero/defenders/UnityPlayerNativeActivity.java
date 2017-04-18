package com.zero.defenders;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NativeActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.core.base.utils.PL;
import com.unity3d.player.UnityPlayer;
import com.zero.channelsdk.BaseMain;
import com.zero.channelsdk.GameMain;
import com.zero.channelsdk.TalkingDataInterface;
import com.zero.service.SimpleService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

//import com.testin.agent.TestinAgent;
public class UnityPlayerNativeActivity extends NativeActivity {
	protected UnityPlayer mUnityPlayer; // don't change the name of this
	// variable; referenced from native code

	public static UnityPlayerNativeActivity myActivity;

	final UnityPlayerNativeActivity me = this;
	//private GTActivityHandler mHandler;
	BaseMain m_GameActivity = null;
	Localization m_Localiza = null;
	boolean is_exit  = false;
	String appKey="4ad386eef199d094ecbbe969973da0fe";
	boolean isExternalStorageExist(){
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}

	public  String getObbAddress(){
		Log.e("unity"," String getObbAddress()");
		if (isExternalStorageExist()){
			String s1=Environment.getExternalStorageDirectory().getAbsolutePath()+"/Android/obb/";

			String version;
			try {
				PackageInfo info = this.getPackageManager().getPackageInfo(this.getPackageName(), 0);
				version = String.valueOf(info.versionCode);
				return	s1+this.getPackageName()+"/main."+version+"."+this.getPackageName()+".";
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return "";
	}
	public String getObbName(){
		Log.e("unity"," getObbName()");
		String version="";
		try {
			PackageInfo info = this.getPackageManager().getPackageInfo(this.getPackageName(), 0);
			version = String.valueOf(info.versionCode);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "main."+version+"."+this.getPackageName()+".";

	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);

		if((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT)!=0)
		{
			finish();
			return;
		}

		getWindow().takeSurface(null);
		setTheme(android.R.style.Theme_NoTitleBar_Fullscreen);
		getWindow().setFormat(PixelFormat.RGBX_8888); // <--- This makes xperia
		// play happy

		mUnityPlayer = new UnityPlayer(this);
		if (mUnityPlayer.getSettings().getBoolean("hide_status_bar", true))
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
					WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(mUnityPlayer);
		mUnityPlayer.requestFocus();
		if(UseSdk() == true)
		{
			new Thread(){
				@Override
				public void run(){

				}
			}.start();
		}

		myActivity = this;

		startService(new Intent(this,SimpleService.class));
//		TestinAgent.init(this, appKey);

		if(m_GameActivity == null)
		{
			m_GameActivity = new GameMain(me);
		}

		if(m_Localiza == null)
		{
			m_Localiza = new Localization(me);
		}
		if(UseSdk() == true)
		{
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Log.e("Unity", "Unity_Login_sucess");
					m_GameActivity.Init();
				}
			});
		}
		TalkingDataInterface.init(myActivity, "24663f5829f3430c805d9b09bd04b03e", "defenders_1");

	}


	public enum SdkType {
		Sdk_None ,
		Sdk_uc,          //娑旀繃鐖�
		Sdk_360,        //360
		Sdk_oppo ,     //oppo
		Sdk_baidu,     //
		Sdk_downjoy,
		Sdk_xiaomi,
		Sdk_Lenovo,
		Sdk_Huawei,
		Sdk_Gfan,
		Sdk_m4399,
		Sdk_Vivo,
		Sdk_Mzw,
		Sdk_meizu,
		Sdk_guopan,
		Sdk_jinli,
		Sdk_cc,
		Sdk_Kaopu,
		Sdk_zhuoyi,
		Sdk_yyh,
		Sdk_CoolCloud,
		Sdk_pptv, //pptv
		Sdk_Qm,//閸忋劌鎮�
		Sdk_TTgame,//tt鐠囶參鐓�,youyi,閹靛鐖堕崥锟�
		Sdk_baofeng,//閺嗘挳顥撶粔鎴炲Η
		Sdk_Sogou,
		Sdk_m8868,
		Sdk_jiucheng,
		Sdk_linyou,
		Sdk_kugou,
		Sdk_guanghu,
		Sdk_Paojiao,//濞夆剝顦�
		Sdk_htc,
		Sdk_anzhi,
		Sdk_Feiliu,
		Sdk_leshi,//娑旀劘顫�
		Sdk_Papa,
		Sdk_Moyoyo,
		Sdk_youku,
		Sdk_Dianhun,
		Sdk_wandoujia,
		Sdk_yingyongbao,
		Sdk_Dangle,
		Sdk_pps,
		Sdk_ouwan,
		Sdk_pengyouwan,
		Sdk_itools,
		Sdk_liebao,
		Sdk_YiJie,
		Sdk_yshy,
		Sdk_sy185,
		Sdk_shouyoumi,
		Sdk_mhzgol,
		Sdk_taiwan,
	}
	public String GetinitChannel()
	{

		return "brmmd";
	}
	public boolean showContactWay()
	{
		if(m_GameActivity != null)
		{
			return m_GameActivity.showContactWay();
		}
		return false;
	}
	public SdkType GetSdkType()
	{
		if(m_GameActivity != null)
		{
			Log.e("unity","GetSdkType");
			return m_GameActivity.GetSdkType();
			//return SdkType.Sdk_None;
		}
		Log.e("unity","GetSdkType  m_GameActivity == null");
		return SdkType.Sdk_None;
	}

	public boolean UseSdk()
	{
		if(GetSdkType() ==SdkType.Sdk_None )
		{
			return false;
		}
		return true;
	}

	private boolean m_showAccount = true;
	public boolean GetShowAccount()
	{
		return m_showAccount;
	}

	public void SetShowAccount(boolean showAccount)
	{
		m_showAccount = showAccount;
	}

	public String GetInternalPath()
	{
		try
		{
			File file = this.getFilesDir();
			if(file != null)
			{
				String dir = file.getAbsolutePath();
				if(dir != null)
				{
					return dir;
				}
			}
		}
		catch(Exception e)
		{
//			Log.e("UnityPlayerNativeActivity", "GetInternalPath error:" + e.getMessage());
		}

		return "";
	}

	public String GetDeviceName()
	{
		return "" + android.os.Build.BRAND + " " + android.os.Build.MODEL;
	}

	public String GetDeviceID()
	{
		TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		if(tm != null)
		{
			String ret = tm.getDeviceId();
			if(ret != null)
			{
				return ret;
			}
		}

		return "unknown device id";
	}

	public String GetMac()
	{
		WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
		if(wifi != null)
		{
			String ret = wifi.getConnectionInfo().getMacAddress();
			if(ret != null)
			{
				return ret;
			}
		}

		return "unknown mac";
	}

	public int GetNetState()
	{
		ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if(manager == null)
		{
			return 0;
		}

		NetworkInfo info = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if(info != null)
		{
			State wifi = info.getState();
			if(wifi == State.CONNECTED)
			{
				return 1;
			}
		}

		info = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if(info != null)
		{
			State mobile = info.getState();
			if(mobile == State.CONNECTED)
			{
				return 2;
			}
		}

		return 0;
	}
	//閼惧嘲褰囩拋鎯ь槵IModel
	public String GetIModel()
	{
		return android.os.Build.MODEL;
	}
	//閼惧嘲褰囩拋鎯ь槵閸掑棜椴搁悳锟�
	public String GetMetrics()
	{
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		int width = metric.widthPixels; // 鐎硅棄瀹抽敍鍦閿涳拷
		int height = metric.heightPixels; // 妤傛ê瀹�
		return height+"x"+width;
	}

	//閼惧嘲褰囩拋鎯ь槵imei娣団剝浼�
	public String GetIMEI()
	{
		TelephonyManager tm = (TelephonyManager) this.getSystemService(Activity.TELEPHONY_SERVICE);
		if(null!=tm)
		{
			return tm.getDeviceId();
		}
		return "unknow";
	}
	// 閸欐牕绶遍崜鈺�缍戦惃鍕敶鐎涙鈹栭梻锟�
	public long getUsableSpace() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		long total=availableBlocks * blockSize;
		return total/1024/1024;
	}

	public void OpenDialog(final String title, final String message, final DialogInterface.OnClickListener click_listener)
	{
		this.runOnUiThread(new Runnable(){
			public void run() {
				AlertDialog.Builder builder = new AlertDialog.Builder(myActivity);
				String m_title = message;
				String m_sure = "绾喖鐣�";
				String m_cancel = "閸欐牗绉�";

				String[] messagetitle = message.split("_", 3);
				if(messagetitle.length > 1){
					m_title = messagetitle[0];
					m_sure = messagetitle[1];
					m_cancel = messagetitle[2];
				}
				if(message != null && !message.isEmpty())
				{
					builder.setTitle(title);
				}

				builder.setMessage(m_title)
						.setCancelable(false)
						.setPositiveButton(m_sure, click_listener)
						.setNegativeButton(m_cancel, click_listener);

				AlertDialog dialog = builder.create();
				dialog.show();
			}});
	}

	public void Exit()
	{
		this.finish();
	}

	// *

	/**/
	void ExitBySDK(){
		if(UseSdk() == true)
		{
			m_GameActivity.sdkExit();
		}
	}

	// Quit Unity
	@Override
	protected void onDestroy() {
		SimpleService.ActivityDestroy();
		Log.e("SuperSdk", "onDestroy..............");
		super.onDestroy();
		if(UseSdk() == true)
		{
			m_GameActivity.onDestroy();
		}
		if (mUnityPlayer != null) {
			mUnityPlayer.quit();
		}
	}

	// NewInit
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		if(UseSdk() == true)
		{
			m_GameActivity.onNewIntent(intent);
		}
		PL.i("mUnityPlayer.onNewIntent()");
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		if(UseSdk() == true)
		{
			m_GameActivity.onBackPressed();
		}
	}

	// Pause Unity
	@Override
	protected void onPause() {
		//SuperPlatform.getInstance().onPause(this);
		super.onPause();
		if(UseSdk() == true)
		{
			m_GameActivity.onPause();
		}
		mUnityPlayer.pause();

		PL.i("mUnityPlayer.onPause()");

	}

	// onStart Unity
	@Override
	protected void onStart() {
		super.onStart();
		if(UseSdk() == true)
		{
			m_GameActivity.onStart();
		}

		PL.i("mUnityPlayer.onStart()");
	}

	// Restart
	@Override
	protected void onRestart() {
		super.onRestart();
		if(UseSdk() == true)
		{
			m_GameActivity.onRestart();
		}
		PL.i("mUnityPlayer.onRestart()");
	}

	// Resume Unity
	@Override
	protected void onResume() {
		super.onResume();
		if(UseSdk() == true)
		{
			m_GameActivity.onResume();
		}
		PL.i("mUnityPlayer.resume()");
		mUnityPlayer.resume();
		mUnityPlayer.windowFocusChanged(true);
	}

	// Stop
	@Override
	protected void onStop() {
		super.onStop();
		if(UseSdk() == true)
		{
			m_GameActivity.onStop();
		}
	}

	// This ensures the layout will be correct.
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mUnityPlayer.configurationChanged(newConfig);
		if(UseSdk() == true)
		{
			m_GameActivity.onConfigurationChanged(newConfig);
		}
	}

	// Notify Unity of the focus change.
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		mUnityPlayer.windowFocusChanged(hasFocus);
		PL.i("mUnityPlayer windowFocusChanged:" + hasFocus);
	}

	// For some reason the multiple keyevent type is not supported by the ndk.
	// Force event injection by overriding dispatchKeyEvent().
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_MULTIPLE)
			return mUnityPlayer.injectEvent(event);
		return super.dispatchKeyEvent(event);
	}

	// Pass any events not handled by (unfocused) views straight to UnityPlayer
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		return mUnityPlayer.injectEvent(event);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (KeyEvent.KEYCODE_BACK == keyCode && event.getRepeatCount() == 0) {
			m_GameActivity.onKeyDown();
			return true;
		}
		return mUnityPlayer.injectEvent(event);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return mUnityPlayer.injectEvent(event);
	}

	/* API12 */public boolean onGenericMotionEvent(MotionEvent event) {
		return mUnityPlayer.injectEvent(event);
	}

	public void Login() {
		Log.e("SuperSdk", "Login..................");

		((Activity) this).runOnUiThread(new Runnable() {

			@Override
			public void run() {
				if(UseSdk() == true)
				{
					Log.e("SuperSdk", "LoginUseSdk..................");
					m_GameActivity.sdkLogin();
				}
			}
		});
	}
	public void extInfo1() {
		Log.e("SuperSdk", "Login..................");

		((Activity) this).runOnUiThread(new Runnable() {

			@Override
			public void run() {
				if(UseSdk() == true)
				{
					Log.e("SuperSdk", "LoginUseSdk..................");
					m_GameActivity.extInfo1();
				}
			}
		});
	}

	public void extInfo2() {
		Log.e("SuperSdk", "Login..................");

		((Activity) this).runOnUiThread(new Runnable() {

			@Override
			public void run() {
				if(UseSdk() == true)
				{
					Log.e("SuperSdk", "LoginUseSdk..................");
					m_GameActivity.extInfo2();
				}
			}
		});
	}
	public void createRole(final String data)
	{
		if(UseSdk() == true)
		{

			m_GameActivity.create(data);
		}

	}

	public void Quit() {

		new Thread() {

			@Override
			public void run() {
				Log.e("SuperSdk", "Unity_Login_sucess.....");
				if(UseSdk() == true)
				{
					m_GameActivity.sdkExit();
				}
			}
		}.start();
	}

	public void charge(String chargetype) {
		Log.e("Unity", "Charge......................start"  + chargetype);
		String[] message = chargetype.split("_", 4);
		final int m_amount = Integer.parseInt(message[0]);
		final String m_orderId = message[1];
		final int m_pro_id =  Integer.parseInt(message[2]);
		final String m_pro_name = message[3];
		m_GameActivity.charge(m_orderId,  m_amount ,m_pro_id ,m_pro_name);

	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if(UseSdk() == true)
		{
			m_GameActivity.onActivityResult(requestCode,resultCode,data);
		}
	}
	public void submitExtendData(String data) {

		m_GameActivity.SdkSubmitExtendData(data);

	}

	public void SetChargeUrl(String url)
	{
		Log.e("Unity", "SetChargeUrl......" + url);
		if(UseSdk() == true)
		{
			// m_MainGame.collectData(data);
			m_GameActivity.SetChargeUrl(url);
		}
	}

	public void quits() {
		if (is_exit == false) {
			//is_exit = true;
			((Activity) this).runOnUiThread(new Runnable() {

				@Override
				public void run()
				{
					if(UseSdk() == true)
					{
						m_GameActivity.sdkExit();
					}
				}
			});
		}
	}

	public void Unity_Account()
	{
		((Activity) this).runOnUiThread(new Runnable() {

			@Override
			public void run()
			{
				if(UseSdk() == true)
				{
					//m_GameActivity.sdkExit();
					m_GameActivity.SwitchAccount();
				}
			}
		});
	}

	public void setbool() {
		is_exit = false;
	}
	//unity  閺屾劒绨洪崢鐔锋礈闂囷拷鐟曪拷
	public void Unity_finish()
	{
		((Activity) this).runOnUiThread(new Runnable() {

			@Override
			public void run()
			{
				if(UseSdk() == true)
				{
					Log.e("unity"," unity 閺屾劒绨洪崢鐔锋礈闂囷拷鐟曪拷 finish");
					finish();
				}
			}
		});

	}
	public void LoginExit()
	{

	}

	//unity  缂冩垹绮舵稉宥呫偨
	public void reconnection()
	{

		((Activity) this).runOnUiThread(new Runnable() {

			@Override
			public void run()
			{
				if(UseSdk() == true)
				{
					Log.e("unity"," 閺傤厾鍤庨柌宥堢箾");
					m_GameActivity.reconnection();
				}
			}
		});


	}

	//unity  缂冩垹绮舵稉宥呫偨
	public void SDkLoginout()
	{

		((Activity) this).runOnUiThread(new Runnable() {

			@Override
			public void run()
			{
				if(UseSdk() == true)
				{
					Log.e("unity"," SDkLoginout");
					m_GameActivity.sdkLogout();
				}
			}
		});
	}

	//婵″倹鐏夊▽鈩冩箒缁楊兛绗侀弬褰掞拷锟介崙铏规畱閺冭泛锟芥瑨鐨熼悽銊︻劃閺傝纭�
	public void GameQuit()
	{
		if(UseSdk() == true)
		{
			UnityPlayer.UnitySendMessage("Platform","GameQuit" ,"");
		}

	}


	//閼惧嘲褰噑dk閻у妾伴惃鍒焎countId 閸氾拷 鐠嬪啰鏁ゅ銈嗘煙濞夋洝绻樼悰宀�娅ヨぐ锟�
	//娴肩姴鍙唅d  c#閻ㄥ嫮娅ラ梽锟�
	public void sdklogin(String accountId)
	{
		if(UseSdk() == true)
		{
			Log.e("Unity","閼惧嘲褰� ID閹存劕濮� sdklogin account = " +  accountId);
			UnityPlayer.UnitySendMessage("Platform","Android_login",accountId);
		}
	}

	//闁拷閸戠儤鐖堕幋锟� 濞屸剝婀佺拹锕�褰块惃鍕閸婃瑥鍨忛幑锟�
	public void GameSwitchAccount(){
		if(UseSdk() == true)
		{
			Log.e("Unity","GameSwitchAccount" );
			UnityPlayer.UnitySendMessage("Platform","GameSwitchAccount" ,"");
		}
	}
	//鐟欐帟澹婇崡鍥╅獓
	public void RoleUpgrade(final String level) {

		if(UseSdk() == true)
		{
			m_GameActivity.RoleUpgrade_Normal(level);
		}

		((Activity) this).runOnUiThread(new Runnable() {

			@Override
			public void run() {
				if(UseSdk() == true)
				{
					m_GameActivity.RoleUpgrade(level);

				}
			}
		});

	}
	//閺堝澶勯崣椋庢畱閺冭泛锟芥瑥鍨忛幑锟�
	public void SDKSwitchAccount(String accountId){
		if(UseSdk() == true)
		{
			Log.e("Unity","SDKSwitchAccount  &&  accountId = " + accountId );
			UnityPlayer.UnitySendMessage("Platform","SDKSwitchAccount" ,accountId);
		}
	}

	FrameLayout m_webLayout;
	WebView m_webView;
	LinearLayout m_topLayout;
	String mWeburl;
	final int OPENWEB = 0, CLOSEWEB = 1;

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			switch (msg.what) {
				case OPENWEB:
					OpenWebViews(mWeburl);
					break;
				case CLOSEWEB:
					CloseWebViews();
					break;
			}
		}
	};

	public void CloseWebView() {

		handler.sendEmptyMessage(CLOSEWEB);
	}

	public void OpenWebView(String url) {
		mWeburl = url;

		handler.sendEmptyMessage(OPENWEB);
	}

	void OpenWebViews(final String url) {
		int w = (int)(840* (float)this.getWindowManager().getDefaultDisplay().getWidth()/1024);
		int h=(int)(550* (float)this.getWindowManager().getDefaultDisplay().getHeight()/768);
		// 閸掓繂顫愰崠鏍︾娑擃亞鈹栫敮鍐ㄧ湰
		m_webLayout = new FrameLayout(this);
		m_webLayout.setPadding(5, 15, 5, 5);
		FrameLayout.LayoutParams lytp = new FrameLayout.LayoutParams(w, h);
		lytp.gravity = Gravity.CENTER;
		addContentView(m_webLayout, lytp);
		this.runOnUiThread(new Runnable() {// 閸︺劋瀵岀痪璺ㄢ柤闁插本鍧婇崝鐘插焼閻ㄥ嫭甯舵禒锟�
			public void run() {
				// 閸掓繂顫愰崠鏉bView
				m_webView = new WebView(myActivity);
				// 鐠佸墽鐤唚ebView閼宠棄顧勯幍褑顢慾avascript閼存碍婀�
				m_webView.getSettings().setJavaScriptEnabled(true);
				// 鐠佸墽鐤嗛崣顖欎簰閺�顖涘瘮缂傗晜鏂�
				m_webView.getSettings().setSupportZoom(false);// 鐠佸墽鐤嗛崙铏瑰箛缂傗晜鏂佸銉ュ徔
				m_webView.getSettings().setBuiltInZoomControls(false);

				// 鐠佸墽鐤唚ebview闁繑妲�
				m_webView.setBackgroundColor(0);

				// 鏉炶棄鍙哢RL
				m_webView.loadUrl(url);
				// 娴ｅ潡銆夐棃銏ｅ箯瀵版鍔嶉悙锟�
				m_webView.requestFocus();
				// 婵″倹鐏夋い鐢告桨娑擃參鎽奸幒銉礉婵″倹鐏夌敮灞炬箿閻愮懓鍤柧鐐复缂佈呯敾閸︺劌缍嬮崜宄況owser娑擃厼鎼锋惔锟�
				m_webView.setWebViewClient(new WebViewClient() {
					public boolean shouldOverrideUrlLoading(WebView view,
															String url) {
						if (url.indexOf("tel:") < 0) {
							view.loadUrl(url);
						}
						return true;
					}
				});

				m_topLayout = new LinearLayout(myActivity);

				m_topLayout.setOrientation(LinearLayout.VERTICAL);

				LinearLayout.LayoutParams lypt = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				lypt.gravity = Gravity.TOP;
				m_topLayout.addView(m_webView);
				m_webLayout.addView(m_topLayout);
			}
		});
	}

	/**
	 * 缁夊娅庨崗顒�鎲ebview
	 * */
	void CloseWebViews() {


		m_webLayout.removeView(m_topLayout);
		m_topLayout.destroyDrawingCache();

		m_topLayout.removeView(m_webView);
		m_webView.destroy();

		// m_topLayout.removeView(m_backButton);
		// m_backButton.destroyDrawingCache();
	}

	//濞戝牊浼呴幒銊╋拷浣鼓侀崸锟�
	public void setTwelveSprite(boolean get)
	{

	}

	public void setEighteenSprite(boolean get)
	{

	}

	public void setShopFresh(boolean get)
	{

	}

	public void setArenaAndCamp(boolean get)
	{

	}

	public void setFullSprite(boolean get,int lasttime)
	{

	}

	public void setFullToughening(boolean get,int lasttime)
	{

	}

	public void setFreeBox(boolean get,int lasttime)
	{

	}



	public String getEquipmentinfo()
	{
		String info="";
		String st1,st2,st3,st4,st5;
		st1=getEquipmentVersion();
		st2=getCPUVersion();
		st3=getMinCpuFreq();
		st4=getTotalMemory();
		st5=String.valueOf(getCpuCores());
		info=st1+"&"+st2+"&"+st3+"&"+st4+"&"+st5;
		Log.e("SuperSdk", "閼惧嘲褰囩拋鎯ь槵娣団剝浼呮稉锟� : "+info);
		return info;
	}

	int getCpuCores()
	{
		class CpuFilter implements FileFilter {
			@Override
			public boolean accept(File pathname) {
				if(Pattern.matches("cpu[0-9]", pathname.getName())) {
					return true;
				}
				return false;
			}
		}

		try {
			File dir = new File("/sys/devices/system/cpu/");
			File[] files = dir.listFiles(new CpuFilter());
			if(files.length==0)
			{
				Log.e( "unity","閼惧嘲褰嘽pu閺嶅憡鏆熺�涙顔岄懢宄板絿婢惰精瑙�");
				return 1;
			}
			Log.e("SuperSdk", "CPU Count: "+files.length);
			return files.length;
		} catch(Exception e) {
			Log.e("SuperSdk", "CPU Count: Failed.");
			Log.e( "unity","閼惧嘲褰嘽pu閺嶅憡鏆熸径杈Е");
			e.printStackTrace();
			return 1;
		}
	}
	//閼惧嘲褰囩拋鎯ь槵閹鍞寸�涳拷
	String getTotalMemory()
	{
		String Memory;
		//閼惧嘲绶辩化鑽ょ埠閸愬懎鐡ㄦ穱鈩冧紖
		String str1 = "/proc/meminfo";// 缁崵绮洪崘鍛摠娣団剝浼呴弬鍥︽
		String str2;
		String[] arrayOfString;
		long initial_memory = 0;

		try {
			FileReader localFileReader = new FileReader(str1);
			if(null!=localFileReader)
			{
				BufferedReader localBufferedReader = new BufferedReader(
						localFileReader, 8192);
				str2 = localBufferedReader.readLine();// 鐠囪褰噈eminfo缁楊兛绔寸悰宀嬬礉缁崵绮洪幀璇插敶鐎涙ê銇囩亸锟�

				arrayOfString = str2.split("\\s+");
				if(arrayOfString.length==0)
				{
					arrayOfString[1]="0";
					Log.e("Unity","閼惧嘲褰囬幍瀣簚閹鍞寸�涙ê鐡у▓鍏歌礋缁岋拷");
				}
				try
				{
					initial_memory = Integer.valueOf(arrayOfString[1]).intValue() * 1024;// 閼惧嘲绶辩化鑽ょ埠閹鍞寸�涙﹫绱濋崡鏇氱秴閺勭枩B閿涘奔绠绘禒锟�1024鏉烆剚宕叉稉绡墆te
				}
				catch(NumberFormatException e)
				{
					Log.e("unity", "閼惧嘲褰囬幀璇插敶鐎涙ê鐡у▓浣冩祮閹广垹绱撶敮闈╃磼閿涗緤绱掗敍锟�");
					initial_memory=0;
				}
				localBufferedReader.close();

			}
		}
		catch (IOException e) {
			Log.e("Unity","閼惧嘲褰囬幍瀣簚閹鍞寸�涙ê銇戠拹锟�,閹舵稑绱撶敮锟�");
		}

		Memory= Formatter.formatFileSize(getBaseContext(), initial_memory);// Byte鏉烆剚宕叉稉绡旴閹存牞锟藉尐B閿涘苯鍞寸�涙ê銇囩亸蹇氼潐閺嶇厧瀵�


		Log.e("SuperSdk", "TotalMemory's space is "+Memory);
		return Memory;

	}


	//閼惧嘲褰嘽pu閸ㄥ褰�
	public String getCPUVersion() {
		String str = "", cpuVersion = "unkonw";
		String[] strCPU=null;
		try {
			//鐠囪褰嘋PU娣団剝浼�
			Process pp = Runtime.getRuntime().exec("cat /proc/cpuinfo");
			if(null!=pp)
			{
				InputStreamReader ir = new InputStreamReader(pp.getInputStream());
				LineNumberReader input = new LineNumberReader(ir);
				//閺屻儲澹楥PU鎼村繐鍨崣锟�
				for (int i = 1; i < 100; i++) {
					str = input.readLine();
					if (str != null) {
						if (str.contains("Hardware")) {
							strCPU =  str.split("\\:", 2);
							//閸樿崵鈹栭弽锟�
							cpuVersion = strCPU[1].trim();
							break;
						}
					}
					else{
						continue;
					}
				}
			}
			else{
				Log.e("Unity","閼惧嘲褰嘽pu閸ㄥ褰挎径杈Е");
			}
		} catch (IOException ex) {
			//鐠у绨ｆ妯款吇閸婏拷
			ex.printStackTrace();
			Log.e("Unity","閼惧嘲褰嘽pu閸ㄥ褰块幎娑樼磽鐢拷");
		}
		Log.e("Unity","cpu 閸ㄥ褰挎稉锟�: "+cpuVersion);
		return cpuVersion;
	}
	//閼惧嘲褰嘽pu閺堬拷婢堆囶暥閻滐拷
	public String getMinCpuFreq() {
		String result = "";
		ProcessBuilder cmd;
		try {
			String[] args = { "/system/bin/cat",
					"/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq" };
			cmd = new ProcessBuilder(args);
			Process process = cmd.start();
			InputStream in = process.getInputStream();
			byte[] re = new byte[24];
			while (in.read(re) != -1) {
				result = result + new String(re);
			}
			in.close();
		} catch (IOException ex) {
			ex.printStackTrace();
			result = "0";
			Log.e("Unity","閼惧嘲褰嘽pu閺堬拷婢堆囶暥閻滃洦濮忓鍌氱埗");
		}
		if(""==result) result = "0";
		int num=0;
		try
		{
			num=Integer.valueOf(result.trim()).intValue();
		}
		catch(NumberFormatException e)
		{
			Log.e("unity", "閼惧嘲褰嘽pu閺堬拷婢堆囶暥閻滃洦鏆熼幑顔挎祮閹广垹绱撶敮锟�");
		}
		float nu=(float)num/1000000;
		BigDecimal b=new BigDecimal(nu);
		float fin=b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
		result=String.valueOf(fin)+"GHZ";
		Log.e("Unity","cpu 閺堬拷婢堆囶暥閻滃洣璐熸稉锟�: "+result.trim());
		return result;
	}
	void FinishLocalization()
	{

	}
	//閼惧嘲褰囩拋鎯ь槵閸ㄥ褰�
	String getEquipmentVersion()
	{
		String version="";
		version=android.os.Build.MODEL;
		Log.e("Unity", "鐠佹儳顦崹瀣娇娑擄拷: "+version);
		return version;
	}

	//閼惧嘲褰囩仦蹇撶鐏忓搫顕�
	public String getScreenInches()
	{
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);

		float density  = dm.density;      // 鐏炲繐绠风�靛棗瀹抽敍鍫濆剼缁辩姵鐦笟瀣剁窗0.75/1.0/1.5/2.0閿涳拷
		int densityDPI = dm.densityDpi;     // 鐏炲繐绠风�靛棗瀹抽敍鍫熺槨鐎电鍎氱槐鐙呯窗120/160/240/320閿涳拷
		float xdpi = dm.xdpi;
		float ydpi = dm.ydpi;

		Log.e("unity", "xdpi=" + xdpi + "; ydpi=" + ydpi);
		Log.e("unity", "density=" + density + "; densityDPI=" + densityDPI);

		int screenWidthDip = dm.widthPixels;        // 鐏炲繐绠风�规枻绱檇ip閿涘苯顩ч敍锟�320dip閿涳拷
		int screenHeightDip = dm.heightPixels;      // 鐏炲繐绠风�规枻绱檇ip閿涘苯顩ч敍锟�533dip閿涳拷

		Log.e("unity", "screenWidthDip=" + screenWidthDip + "; screenHeightDip=" + screenHeightDip);
		double size=Math.sqrt(Math.pow(screenWidthDip, 2)+Math.pow(screenHeightDip,2))/densityDPI;
		Log.e("unity", "Inches is  "+size);
		return String.valueOf(size);
	}

	//妞嬬偞绁﹂惂璇茬秿濞夈劌鍞介幋鎰缂佺喕顓�
	public void FeiLiuLoginStatistics(String data)
	{

	}

	//妞嬬偞绁︾敮鎰娇娣団剝浼呯紒鐔活吀
	public void FeiLiuAccountInformationStatistics(String data)
	{

	}

	//妞嬬偞绁﹂悳鈺侇啀閺�顖欑帛缂佺喕顓�
	public void FeiLiuOrderStatistics(String data)
	{


	}
	//閺�顖欑帛閹存劕濮涙禍瀣╂缂佺喕顓�
	public void FeiLiuPaySuccessStatistics(String data)
	{

	}

	//閺�顖欑帛婢惰精瑙︽禍瀣╂缂佺喕顓�
	public void FeiLiuPayFailureStatistics(String data)
	{

	}

	//閾忔碍瀚欑拹褍绔垫總鏍уС缂佺喕顓�
	public void FeiLiuMoneyRewardStatistics(String data)
	{

	}

	//濞撳憡鍨欓崘鍛Х鐠愬湱鍋ｇ紒鐔活吀
	public void FeiLiuConSumptionStatistics(String data)
	{

	}

	//闁挸鍙垮☉鍫ｏ拷妤冪埠鐠侊拷
	public void FeiLiuPropConsumptionStatistics(String data)
	{

	}

	//閹恒儱褰堟禒璇插缂佺喕顓�
	public void FeiLiuAcceptTaskStatistics(String data)
	{

	}

	//娴犺濮熺�瑰本鍨�
	public void FeiLiuCompleteTaskStatistics(String data)
	{
	}

	//娴犺濮熸径杈Е
	public void FeiLiuTaskFailureStatistics(String data)
	{

	}

	public static String Md5Secret(String str)
	{
		if (str == null) {
			return null;
		}

		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("utf-8"));
		} catch (NoSuchAlgorithmException e) {

			return str;
		} catch (UnsupportedEncodingException e) {
			return str;
		}

		byte[] byteArray = messageDigest.digest();

		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return md5StrBuff.toString();
	}
	public void SetPushMessage(String key,int week,int daystamp,String name,String content,boolean state)
	{

		SimpleService.setOnePush(key,week, daystamp, name, content,state);
	}
	public void ClosePushMessage(String key)
	{

		SimpleService.closePush(key);
	}
	public void OpenPushMessage(String key)
	{

		SimpleService.openPush(key);
	}
	public void SetSpecialPushMessage(String key,int timestamp,String name,String content,boolean state)
	{

		SimpleService.setDisposablePush(key,timestamp, name, content,state);
	}
	public void RemovePushMessage(String key)
	{

		SimpleService.RemovePushMessage(key);
	}
	public void ClearNotifications()
	{

		SimpleService.ClearNotifications();
	}

	//Talking Data SDK閹恒儱鍙嗛幒銉ュ經
	//閻€劍鍩涚敮鎰娇濞夈劌鍞介幋鎰
	public void onRegister(String userId)
	{
		TalkingDataInterface.onRegister(userId);
	}
	//閻€劍鍩涚敮鎰娇閻ц缍嶉幋鎰
	public void onLogin(String userId)
	{
		TalkingDataInterface.onLogin(userId);
	}
	//娑撳宕熸禍瀣╂
	public void onPlaceOrder(String orderId, int amount, String itemname)
	{
		TalkingDataInterface.onPlaceOrder(orderId, amount, itemname);
	}



	//鐟欐帟澹婇崚娑樼紦閸氬氦鐨熼悽锟�
	public void onCreateRole(String roleName)
	{
		TalkingDataInterface.onCreateRole(roleName);
	}
	//Talking Data SDK闁规亽鍎遍崣鍜礜D

	public void PurchaseSuccess(int level)  {

	}

	public void AchievedLevel(int level)  {

	}

	public void GuideFinish()
	{

	}
	public void popCommunityView()
	{
	}

	public void facebookFriendsInvite()
	{
	}

	public void shareToFacebook()
	{

	}

	public void getFacebookFriends()
	{

	}

	public void showFacebook()
	{

	}
}
