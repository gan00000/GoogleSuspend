package com.zero.channelsdk;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

import org.json.JSONObject;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Environment;
import android.util.Log;

import com.core.base.utils.SStringUtil;
import com.starpy.base.bean.SGameLanguage;
import com.starpy.base.bean.SPayType;
import com.starpy.data.login.ILoginCallBack;
import com.starpy.data.login.response.SLoginResponse;
import com.starpy.sdk.out.IStarpy;
import com.starpy.sdk.out.StarpyFactory;
import com.zero.defenders.UnityPlayerNativeActivity;
import com.zero.defenders.UnityPlayerNativeActivity.SdkType;


public class GameMain extends BaseMain {

	UnityPlayerNativeActivity me = null;
	boolean mLogined = false;
	int mServerID = 0;
	private IStarpy iStarpy;
	public GameMain(UnityPlayerNativeActivity activity) {		
		me = activity;		
		iStarpy = StarpyFactory.create();
		iStarpy.setGameLanguage(activity, SGameLanguage.zh_TW);
		iStarpy.initSDK(me);	
		iStarpy.onCreate(me);
	}

	public void SwitchAccount() {
		sdkLogin();		
	}
	
	public void reconnection() {
	}

	public void onKeyDown() {
	}

	public void Init() {
//		 iStarpy.initSDK(me);		 
	}

	
	public void sdkLogin() {
		iStarpy.login(me, new ILoginCallBack() {
            @Override
            public void onLogin(SLoginResponse sLoginResponse) {
                if (sLoginResponse != null){
                    String uid = sLoginResponse.getUserId();//鐢ㄦ埛id
                    String accessToken = sLoginResponse.getAccessToken();
                    String timestamp = sLoginResponse.getTimestamp();
                    Log.i("IStarpy","uid:" + uid + "  accessToken:" + accessToken + "  yanz:" +  SStringUtil.toMd5("DF7D80A64433C90E263F146315E17A79" +
                            uid + sLoginResponse.getGameCode() + timestamp));
                    me.sdklogin("taiwan_"+uid);
                }
            }
        });
	}

	public void sdkLogout() {
	
	}

	String roleName;
	String roleId;
	String userId;
	String zoneId;
	String qudaoId;
	String roleLevel;
	String zoneName;

	public void SdkSubmitExtendData(String jsonString) {
		try {
			JSONObject json = new JSONObject(jsonString);
			roleName = json.getString("roleName");
			roleId = json.getString("roleId");
			zoneId = String.valueOf(json.getInt("zoneId"));
			roleLevel = String.valueOf(json.getInt("roleLevel"));
			zoneName = json.getString("zoneName");
			iStarpy.registerRoleInfo(me, roleId, roleName, roleLevel, zoneId, zoneName);
		} catch (Exception e) {

		}		

	}

	private String readTextFromSDcard(InputStream is) throws Exception {
		InputStreamReader reader = new InputStreamReader(is);
		BufferedReader bufferedReader = new BufferedReader(reader);
		StringBuffer buffer = new StringBuffer("");
		String str;
		while ((str = bufferedReader.readLine()) != null) {
			buffer.append(str);
			buffer.append("\n");
		}
		return buffer.toString();
	}

	public void sdkExit() {
	
	}

	@Override
	public void create(String data) {			
		try {				
			JSONObject roleInfo = new JSONObject();		
			roleInfo.put("roleId", roleId);
			roleInfo.put("roleName", roleName);
			roleInfo.put("roleLevel", roleLevel);
			roleInfo.put("zoneId", zoneId);
			roleInfo.put("zoneName", zoneName);
			roleInfo.put("balance", "0");
			roleInfo.put("vip", "1");
			roleInfo.put("partyName", "None");
			roleInfo.put("roleCTime", String.valueOf(new Date().getTime()/1000));
			roleInfo.put("roleLevelMTime", String.valueOf(new Date().getTime()/1000));					
		
		} catch (Exception e) {
			// 濠㈣泛瀚幃濠傤嚕閸屾氨鍩�
		}
	}

	@Override
	public void RoleUpgrade_Normal(String level) {

		try {
			if (level != roleLevel) {
				roleLevel = level;
				JSONObject roleInfo = new JSONObject();

				roleInfo.put("roleId", roleId);
				roleInfo.put("roleName", roleName);
				roleInfo.put("roleLevel", level);
				
			}
		} catch (Exception e) {
			
		}
	}
String[] pro_ids=new String[]{"","com.brmmd.3.99.month","com.brmmd.19.99.month","py.brmmd.1.99","py.brmmd.4.99","py.brmmd.9.99","py.brmmd.29.99","py.brmmd.49.99","py.brmmd.99.99"};
	@Override
	public void charge(String orderid, int amount, int pro_id, String pro_name) {

        /*
         
 充值接口
 PayType PayType.OTHERS为第三方储值，PayType.GOOGLE为Google储值
 cpOrderId cp订单号，请保持每次的值都是不会重复的
 productId 充值的商品id
 roleLevel 觉得等级
 customize 自定义透传字段（从服务端回调到cp）
         */
         iStarpy.pay(me, SPayType.GOOGLE,orderid ,pro_ids[pro_id], roleLevel, orderid);
		
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		iStarpy.onActivityResult(me, requestCode, resultCode, data);
		
	}
	// onStart Unity
	public void onStart() {
		iStarpy.onStop(me);
	}

	// Restart
	public void onRestart() {
		
	}

	// Resume Unity
	public void onResume() {
		iStarpy.onResume(me);
	}

	public void onPause() {
		iStarpy.onPause(me);
	}

	// Stop
	public void onStop() {
		iStarpy.onStop(me);
	}

	public void onDestroy() {
		iStarpy.onDestroy(me);

	}

	public void onConfigurationChanged(Configuration newConfig) {

	}

	public boolean UseFeiliuSdk() {// 闂佸搫瀚烽崹浼村箚娴ｇ儤瀚柛鎰典簼閺嗗繐顪冪�ｎ剙浠︾紒渚婄畵瀵偊鎮ч崼婵堛偊缂傚倷鑳堕崰鏇㈩敇閿燂拷 婵犵锟藉啿锟介绮径灞惧闁告劦浜濋弳寮恊turn false
		return false;
	}

	public SdkType GetSdkType() {
		// return
		// null;//濠殿喗蓱閹搁箖鎯侀鍕闁靛骏绱曞В姗�姊洪悙钘夊姦闁稿簶鏅犻幆鍫ョ嵁閸喖濮夐梺璺ㄥ枙婵倝骞愰幘顔芥櫢闁兼亽鍎查悗顓㈡煃閸︻厼浠滅紒璺虹仛鐎靛吋绗熼崹顔煎闂侀潧顦崐浠嬵敋濮樿泛唯闁稿本鑹剧敮銊╂煥閻旂粯顥夋い顐ｇ墵閸╃偤鏁撻悩铏櫝闂佺懓鍟块…顒勫疮閹惧瓨濯撮悹鍥囧嫬顏堕梺鎰佸弮椤ゅ倿鎮樻惔銊ノч柛宀嬮檮鐎氱懓鈹戦钘夌亶闁哄倷绶氶弫鎾绘嚍閵夛箑顎忓┑鐐存綑缁ㄦ椽宕曞畝鍕ㄦい顐枟鐎氬綊鏌熼崘宸劸濞寸媭鍣ｅ畷锝夋煥鐎ｎ偄顎涘銈庡墰婵挳顢樻禒瀣祦婵懓娲ｉ崚鎺楁⒑濞嗘儳鐏犲ù婊勫劤椤繈骞橀懡銈囨殯闂備胶鍋ㄩ崕鏌ヮ敂椤忓牆鐓橀柣蹇氼嚙椤ㄥ酣鎮橀幇鎵筹拷鏍偑閿熶粙宕濋悽鍛婂仏妞ゆ挴锟藉磭妯侀梻浣哄仺閸庢煡骞忛崘顔惧祦濞村吋鐟у畝娑㈡煥閻旂儤娅曞ù鍏肩矒婵＄兘宕橀敃锟介崜顒併亜韫囨棏鍎ラ柛鏂跨埣閸╃偤鎮欓弶鎴濆釜閻庣數澧楃缓鍧楀磿椤曪拷閺佹挻绂掔�ｎ亜濮傚銈庡幖椤戞劙鎮樻惔銊ノ╂い顐枟鐎氬綊鏌熼崘鎵嶅牓寮笟锟介弫鎾绘嚍閵壯囥偔闁荤姴娲㈤崕娲吹椤忓牆钃熸い鏍ュ�栫�氾拷
		return SdkType.Sdk_taiwan;
	}
}
