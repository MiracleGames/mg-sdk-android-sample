package com.mg.game.jqyct.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.mg.usercentersdk.platform.Constants;
import com.mg.usercentersdk.thirdparty.WeiXinLogin;
import com.mg.usercentersdk.util.LogUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler{
	private static String TAG = "MicroMsg.WXEntryActivity";

    private IWXAPI api;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    	api = WXAPIFactory.createWXAPI(this, Constants.WX_APP_ID, false);
    	LogUtil.d(TAG, "WX_APP_ID=="+Constants.WX_APP_ID);
        try {
            Intent intent = getIntent();
        	api.handleIntent(intent, this);
			LogUtil.d(TAG, "handleIntent==");
        } catch (Exception e) {
        	e.printStackTrace();
        }
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) { //微信发送的请求回调
		switch (req.getType()) {
		case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
			LogUtil.d(TAG, "onReq///ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX");
			break;
		case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
			LogUtil.d(TAG, "onReq///ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX");
			break;
		default:
			break;
		}
        finish();
	}

	@Override
	public void onResp(BaseResp resp) { //发送到微信请求的响应结果回调
		String result = "";
		
		switch (resp.errCode) {
		case BaseResp.ErrCode.ERR_OK: //用户同意
			result = "发送成功";
			String code = ((SendAuth.Resp) resp).code; //用户换取 access_token 的 code，仅在 ErrCode 为 0 时有效
			WeiXinLogin.getInstance().getAccessToken(code);
			break;
		case BaseResp.ErrCode.ERR_USER_CANCEL:
			result = "发送取消";
			break;
		case BaseResp.ErrCode.ERR_AUTH_DENIED:
			result = "发送被拒绝";
			break;
		case BaseResp.ErrCode.ERR_UNSUPPORT:
			result = "不支持错误";
			break;
		default:
			result = "发送返回";
			break;
		}
		WeiXinLogin.getInstance().hideProgressDialog();
		//Toast.makeText(this, result + ", type=" + resp.getType(), Toast.LENGTH_SHORT).show();
		LogUtil.d(TAG, "onResp///result===" + resp.getType());

        finish();
	}
}