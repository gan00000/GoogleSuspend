package com.zero.channelsdk;

//import com.tendcloud.appcpa.Order;
//import com.tendcloud.appcpa.TalkingDataAppCpa;

import android.content.Context;
import android.util.Log;

public class TalkingDataInterface {
	static Context current=null;
	static String userid="";
	static String orderid="";
	static int curamount;
    //��ʼ�� AD T RACKING SDK
	 public static void init(Context context,String appid,String channelled)
	 {
		 current=context;
//		 TalkingDataAppCpa.init(context, appid, channelled);
	 }

	 //�û��ʺ�ע��ɹ�
	 public  static  void onRegister(String userId)
	 {
//		 TalkingDataAppCpa.onRegister(userId);
		 userid=userId;
		 Log.e("unity","Talking Data SDK�û��ʺ�ע��ɹ�  "+userId);
	 }

	 //�û��ʺŵ�¼�ɹ�
	 public  static  void onLogin(String userId)
	 {
//		 TalkingDataAppCpa.onLogin(userId);
		 userid=userId;
		 Log.e("unity","Talking Data SDK �û��ʺŵ�¼�ɹ�  "+userId);
	 }

	 //�µ��¼�
	 public  static  void onPlaceOrder(String orderId , int amount , String itemname)
	 {
		 orderid=orderId;
		 curamount=amount;
//		 Order order = Order.createOrder(orderid, curamount, "CNY").addItem("cost", itemname, curamount*100, 1);
		// TalkingDataAppCpa.onPlaceOrder(userid, order);
		 Log.e("unity","Talking Data SDK �µ��¼�  "+orderId+amount+itemname);
	 }

	 //֧��
	 public  static  void onPay()
	 {
//		 TalkingDataAppCpa.onPay(userid, orderid, curamount*100, "CNY","AliPay");
		 Log.e("unity","Talking Data SDK ֧��  ");
	 }

	 //�µ�֧���ɹ����ô˽ӿ�
	 public  static  void onOrderPaySucc()
	 {
		 //TalkingDataAppCpa.onOrderPaySucc(userid, orderid, curamount*100, "CNY","AliPay");
		 Log.e("unity","Talking Data SDK �µ�֧���ɹ����ô˽ӿ�  ");
	 }

	 //��ɫ���������
	 public static void onCreateRole(String roleName)
	 {
//		 TalkingDataAppCpa.onCreateRole(roleName);
		 Log.e("unity","Talking Data SDK ��ɫ���������  ");
	 }
}
