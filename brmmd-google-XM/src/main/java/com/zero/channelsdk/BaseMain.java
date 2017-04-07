package com.zero.channelsdk;

import org.json.JSONObject;

import com.zero.defenders.UnityPlayerNativeActivity;
import com.zero.defenders.UnityPlayerNativeActivity.SdkType;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.util.Log;




public class BaseMain {
	

	public void Init() {
		// TODO Auto-generated method stub
	}

	public SdkType GetSdkType() {
		// TODO Auto-generated method stub
		return null;
	}

	public void sdkExit() {
		// TODO Auto-generated method stub
		
	}

	public void onPause() {
		// TODO Auto-generated method stub
		
	}

	public void onStart() {
		// TODO Auto-generated method stub
		
	}

	public void onRestart() {
		// TODO Auto-generated method stub
		
	}

	public void onResume() {
		// TODO Auto-generated method stub
		
	}

	public void onStop() {
		// TODO Auto-generated method stub
		
	}

	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		
	}

	public void onKeyDown() {
		// TODO Auto-generated method stub
		
	}

	public void sdkLogin() {
		// TODO Auto-generated method stub
		
	}

	public void charge(String m_orderId, int m_amount, int m_pro_id,
			String m_pro_name) {
		// TODO Auto-generated method stub
		
	}

	public void SdkSubmitExtendData(String data) {
		// TODO Auto-generated method stub
		try {
			JSONObject json= new JSONObject(data);
			//mServerID = json.getInt("areaId");
			JSONObject jsonExData = new JSONObject();
			jsonExData.put("roleId", String.valueOf(json.getInt("roleId")));// 鐜╁瑙掕壊ID
			jsonExData.put("roleName", json.getString("roleName"));// 鐜╁瑙掕壊鍚�
			jsonExData.put("roleLevel", String.valueOf(json.getInt("roleLevel")));// 鐜╁瑙掕壊绛夌骇
			jsonExData.put("zoneId", json.getInt("zoneId"));// 娓告垙鍖烘湇ID
			jsonExData.put("zoneName", json.getString("zoneName"));// 娓告垙鍖烘湇鍚嶇О
		} catch (Exception e) {
			// 澶勭悊寮傚父
		}
	}

	public void SwitchAccount() {
		// TODO Auto-generated method stub
		
	}

	public void sdkLogout() {
		// TODO Auto-generated method stub
		
	}

	public void reconnection() {
		// TODO Auto-generated method stub
		
	}
	
	public void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		
	}
	
	public void RoleUpgrade(String level) {
		// TODO Auto-generated method stub
		
	}
	
	public void create(String data) {
		
	}
	
    public void onBackPressed() {
	}
	public void extInfo1() {
		// TODO Auto-generated method stub
		
	}
	public void extInfo2() {
		// TODO Auto-generated method stub
		
	}
	public boolean showContactWay() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void RoleUpgrade_Normal(String level) {
		// TODO Auto-generated method stub
		
	}
	
	public void SetChargeUrl(String url) {
		// TODO Auto-generated method stub
		
	}
	
	public void create_Normal(String data) {
		// TODO Auto-generated method stub
		
	}
	public boolean UseFeiliuSdk(){//鏄惁璋冪敤椋炴祦鏁版嵁缁熻 濡備笉璋冪敤闇�鍦℅ameMain涓噸鍐欏苟return false
		return true;
	}
	public void onDestroy() {
		// TODO Auto-generated method stub
		
	}
}
