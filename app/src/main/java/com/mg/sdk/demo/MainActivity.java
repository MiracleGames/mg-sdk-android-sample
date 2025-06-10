package com.mg.sdk.demo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


import com.mg.usercentersdk.MGSdkPlatform;
import com.mg.usercentersdk.demo.R;
import com.mg.usercentersdk.h5.H5AppManager;
import com.mg.usercentersdk.h5.MgSdkConfigH5;
import com.mg.usercentersdk.listener.MGInitListener;
import com.mg.usercentersdk.listener.MGLoginListener;
import com.mg.usercentersdk.listener.MGLogoutListener;
import com.mg.usercentersdk.listener.MGPayListener;
import com.mg.usercentersdk.listener.MGAssetsListener;
import com.mg.usercentersdk.listener.MGAntiAdditionListener;
import com.mg.usercentersdk.platform.model.AssetsInfo;
import com.mg.usercentersdk.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends FragmentActivity implements OnClickListener {
	private String TAG = "MGAndroidSDK";
	private Activity mActivity;

	private Button initButton;
	private Button LoginButton;
	private Button logoutButton;
	private Button payButton;
	private Button antiAdditionButton;

	private TextView responseText;
	String desc="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		mActivity = this;

		boolean h5=false;
		if(h5) {
//			initH5App();
			String url = "http://mdc.mguwp.net/test/flash_index_test.html";
			H5AppManager.initH5App(this,url);
		}
		else{

			initButton = (Button) findViewById(R.id.btn_init);
			LoginButton = (Button) findViewById(R.id.btn_Login);
			logoutButton = (Button) findViewById(R.id.btn_logout);
			payButton = (Button) findViewById(R.id.btn_pay);
			antiAdditionButton = (Button) findViewById(R.id.btn_antiaddition);
			responseText = (TextView) findViewById(R.id.responseText);

			antiAdditionButton.setOnClickListener(this);
			initButton.setOnClickListener(this);
			LoginButton.setOnClickListener(this);
			logoutButton.setOnClickListener(this);
			payButton.setOnClickListener(this);
//		初始化
			MGSdkPlatform.getInstance().init(this, new MGInitListener() {

				@Override
				public void onSuccess(String msg) {
					Log.d(TAG, "初始化成功;" + "msg===" + msg);
					responseText.setText("初始化成功;msg===" + msg + desc);
					MGSdkPlatform.getInstance().Assets(new MGAssetsListener() {
						@Override
						public void onSuccess(ArrayList<AssetsInfo> assetsInfoList) {

							Log.d(TAG, "资产变更;" + "assetsInfoList===" + assetsInfoList.size());
							desc += "资产变更,assetsInfoList.size():=" + assetsInfoList.size() + "\n";
							responseText.setText(desc);
							//responseText.setText("资产变更,assetsInfoListOrg:=" + assetsInfoList.size());

							for (AssetsInfo assetInfo : assetsInfoList) {
								MGSdkPlatform.getInstance().ReportFulfillmentAsync(assetInfo.getId());
							}

							desc += "资产已核销";
							responseText.setText(desc);
						}
					});
				}

				@Override
				public void onFailed(String msg) {
					Log.d(TAG, "初始化失败;" + "msg===" + msg);
				}

			});
		}
	}

	public void initH5App() {
		Random random = new Random();
		int randomNumber = random.nextInt(100);
		//String url = "http://wjqfclient.6hwan.com/hsxf_xy/index_mg_h5_apk.html?a=mg&agent=mg&pf=mg_uwp_apk&platform=mg_uwp_apk&random=" + randomNumber; //修改url
		String url = "http://mdc.mguwp.net/test/flash_index_test.html";
//		String url = "https://tqsy2-res.ymtnet.com/u2game/u2game_mqw_cn/WebGL-PC/index-back.html";
		MgSdkConfigH5 config=new MgSdkConfigH5();
		H5AppManager.getInstance().startup(this, url, config);
	}

	@Override
	public void onClick(View v) {

		int id = v.getId();
		if (id == R.id.btn_init) {
			this.runOnUiThread(new Runnable() {
				@Override
				public void run(){
					MGSdkPlatform.getInstance().init(MainActivity.this, new MGInitListener() {

						@Override
						public void onSuccess(String msg) {
							Log.d(TAG, "初始化成功;" + "msg===" + msg);
							responseText.setText("初始化成功;msg===" + msg+desc);
						}

						@Override
						public void onFailed(String msg) {
							Log.d(TAG, "初始化失败;" + "msg===" + msg);
						}

					});

				};
			});
		}

		else if (id == R.id.btn_Login) {
			this.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					MGSdkPlatform.getInstance().login(MainActivity.this, new MGLoginListener() {

						@Override
						public void onSuccess(String msg) {
							Log.d(TAG, "登录成功;" + "msg===" + msg);
							responseText.setText("登录成功;msg===" + msg);
							try {
								JSONObject json = new JSONObject(msg);
								String userId = json.getString("userId"); //用户唯一标识
								String token = json.getString("token");   //验证token
								Log.d(TAG, "userId===" + userId + ";token=" + token);
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}

						@Override
						public void onFailed(String msg) {
							Log.d(TAG, "登录失败;" + "msg===" + msg);
							responseText.setText("登录失败;msg===" + msg);
						}

					});
				}
			});
		}

		else if (id == R.id.btn_antiaddition) {
			this.runOnUiThread(new Runnable() {

				@Override
				public void run() {

					MGSdkPlatform.getInstance().antiAddition(MainActivity.this, new MGAntiAdditionListener() {

						@Override
						public void onSuccess(String msg) {
							Log.d(TAG, "实名认证成功;" + "msg===" + msg);
							responseText.setText("实名认证成功;msg===" + msg);
						}

						@Override
						public void onFailed(String msg) {
							Log.d(TAG, "实名认证败;" + "msg===" + msg);
							responseText.setText("实名认证败;msg===" + msg);
						}

					});
				}
			});
		}

		else if (id == R.id.btn_logout) {
			this.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					MGSdkPlatform.getInstance().logout(mActivity, new MGLogoutListener() {

						@Override
						public void onSuccess(String msg) {
							Log.d(TAG, "登出成功;" + "msg===" + msg);
							responseText.setText("登出成功;msg===" + msg);
						}

						@Override
						public void onFailed(String msg) {
							Log.d(TAG, "登出失败;" + "msg===" + msg);
							responseText.setText("登出失败;msg===" + msg);
						}

					});
				}
			});
		}

		else if (id == R.id.btn_pay) {
			desc="";
			MGSdkPlatform.getInstance().pay(this, "368567787F8A434", "{\\\"orderNo\\\":\\\"20241014070003B0E67FD18B40E4CE\\\"}", "a155d9c9-8044-11ed-abc3-8e40952d2019",
					new MGPayListener() {
						@Override
						public void onSuccess(String msg) {
							Log.d(TAG, "支付成功;" + "msg===" + msg);
							desc+="支付成功,msg:=" + msg+"\n";
							responseText.setText(desc);
						}

						@Override
						public void onFailed(String msg) {
							Log.d(TAG, "支付失败;" + "msg===" + msg);
							responseText.setText("支付失败,msg:" + msg+desc);
						}
					});
		}


	}

	
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Log.i(TAG, " click back key");
            mActivity.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                }
            });
        }
        return super.onKeyDown(keyCode, event);
    }

	private void ShowDialog(Context context, String text) {
		new AlertDialog.Builder(context).setTitle("提示")//设置对话框标题
				.setMessage(text)//设置显示的内容
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
					@Override
					public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
						// TODO Auto-generated method stub
					}
				}).show();//在按键响应事件中显示此对话框
	}

	public void onStart() {
		super.onStart();
		
		MGSdkPlatform.getInstance().onStart();
	}

	//Activity开始和用户交互的时候调用
	public void onResume() {
		super.onResume();

		MGSdkPlatform.getInstance().onResume();
	}

	//Activity被暂停时调用(一个Activity转到另一个Activity调用) 
	public void onPause() {
		super.onPause();

		MGSdkPlatform.getInstance().onPause();
	}

	public void onStop() {
		super.onStop();
		
		MGSdkPlatform.getInstance().onStop();
	}

	public void onRestart() {
		super.onRestart();
		
		MGSdkPlatform.getInstance().onRestart();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		
		MGSdkPlatform.getInstance().onNewIntent(intent);
	}

	//Activity被从内存中移除，一般发生在执行finish方法时或者Android回收内存的时候
	public void onDestroy() {
		super.onDestroy();
		
		MGSdkPlatform.getInstance().onDestroy();
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		MGSdkPlatform.getInstance().onActivityResult(requestCode, resultCode, data);
	}
}
