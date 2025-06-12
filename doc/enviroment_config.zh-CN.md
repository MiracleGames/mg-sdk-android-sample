# Android SDK环境配置

## 1、 简介
本文介绍了在Android Studio环境下配置Miracle Games Android SDK。

## 2、 开发环境配置
### 1、添加 Maven 仓库
　　在项目 `build.gradle` 中添加：
```groovy
repositories {
    mavenCentral()
}
```

### 2、添加依赖
　　在module的build.gradle中添加依赖
```groovy
dependencies {
    implementation 'com.mguwp.sdk:android:1.0.2'
}
```
> sdk最新版本可在[mvnrepository](https://mvnrepository.com/artifact/com.mguwp.sdk/android)中查看

### 3、游戏AndroidManifest.xml的配置
> 在 Android11 及以上系统中，需要申请"查询已安装App"的权限，否则无法打开微信支付

　　在`<application></application>`里面加入Activity声明： (注：除MG SDK主工程声明配置以外其他第三方登录配置均为
当前项目需求进行选择配置)  
- **AppKey：** 需将参数值替换为MG开发者后台分配的游戏AppKey  
- **ChannelID：** 渠道号（保留字段默认传”mg”）  
- **com.mg.game.jqyct.wxapi.WXEntryActivity** 需要将com.mg.game.jqyct替换为最终游戏包名  
- **wx_appid：** 需将参数值替换为微信后台申请的APPID  
- **wx_secret：** 需将参数值替换为微信后台申请的wx_secret  
- **qq_appid值以及android:scheme="tencent000000"** 需要将qq_appid参数值以及tencent000000换为QQ  
- **android:value="@string/facebook_app_id"** 需要将@string/facebook_app_id替换为facebook申请的应用编号；  
- **android:scheme="@string/fb_login_protocol_scheme"** 需要将@string/fb_login_protocol_scheme替换为facebo
自定义选项卡所需的 Facebook 应用编号；  
- **SERVER_CLIENT_ID：** 需将参数值替换为Google后台申请的客户端

### 4、游戏AndroidManifest.xml的配置
```xml
    <uses-permission
        android:name="android.permission.QUERY_ALL_PACKAGES"
        tools:ignore="QueryAllPackagesPermission" />

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name">

        <!-- MG用户中心所需activity -->
        <activity
            android:name="com.mg.usercentersdk.widget.WrapperActivity"
            android:configChanges="fontScale|orientation|keyboardHidden|locale|navigation|screenSize|uiMode"
            android:excludeFromRecents="true"
            android:hardwareAccelerated="false"
            android:launchMode="standard"
            android:theme="@android:style/Theme.Translucent.NoTitleBar.Fullscreen"
            android:windowSoftInputMode="stateAlwaysHidden|adjustResize" >
        </activity>

        <!-- MG用户中心AppKey -->
        <meta-data
            android:name="AppKey"
            android:value="YOUR_AppKey" >
        </meta-data>

        <!-- MG用户中心渠道号 -->
        <meta-data
            android:name="ChannelID"
            android:value="${CHANNEL_VALUE}" > <!--不同渠道出包配置，可以在build.gradle里指定该值/-->
        </meta-data>

        <!-- Unity中用于处理SDK回调消息的脚本对象 -->
        <meta-data
            android:name="MIRACLE_GAMES_UNITY3D_OBJECT_NAME"
            android:value="MiracleGames" />

        <!-- MG用户中心当前游戏类型标识 true为单机游戏，false为网游 -->
        <meta-data
            android:name="SINGLE_GAME"
            android:value="true" />

        <!-- 微信登录 所需配置 -->
        <activity
            android:name="com.mg.game.jqyct.wxapi.WXEntryActivity"
            android:theme="@android:style/Theme.Translucent.NoTitleBar"
            android:exported="true"
            android:launchMode="singleTop">
        </activity>
        <!-- 微信登录所需参数 -->
        <meta-data
            android:name="wx_appid"
            android:value="YOUR_wx_appid" >
        </meta-data>
        <meta-data
            android:name="wx_secret"
            android:value="YOUR_wx_secret" >
        </meta-data>

        <!-- QQ登录 所需配置 可根据项目需求选择配置-->
        <activity
            android:name="com.tencent.tauth.AuthActivity"
            android:configChanges="orientation|keyboardHidden"
            android:noHistory="true"
            android:exported="true"
            android:launchMode="singleTask" >
            <intent-filter>
                <action android:name="android.intent.action.VIEW" />
                <category android:name="android.intent.category.DEFAULT" />
                <category android:name="android.intent.category.BROWSABLE" />
                <data android:scheme="tencent000000" /> <!--同下面的腾讯的appid-->
            </intent-filter>
        </activity>
        <activity
            android:name="com.tencent.connect.common.AssistActivity"
            android:configChanges="orientation|keyboardHidden|screenSize"
            android:screenOrientation="behind"
            android:theme="@android:style/Theme.Translucent.NoTitleBar" />
        <meta-data
            android:name="qq_appid"
            android:value="tencent000000" >
        </meta-data>

        <!-- Facebook 所需Activity，可根据项目需求选择配置 -->
        <meta-data
            android:name="com.facebook.sdk.ApplicationId"
            android:value="@string/facebook_app_id"/> <!--Facebook 应用编号-->
        <activity android:name="com.facebook.FacebookActivity"
            android:configChanges="keyboard|keyboardHidden|screenLayout|screenSize|orientation"
            android:label="@string/app_name" />
        <activity
            android:name="com.facebook.CustomTabActivity"
            android:exported="true">
            <intent-filter><action android:name="android.intent.action.VIEW" />
                <category android:name="android.intent.category.DEFAULT" />
                <category android:name="android.intent.category.BROWSABLE" />
                <data android:scheme="@string/fb_login_protocol_scheme" />  <!--Facebook 启用 Chrome 自定义选项卡所需的 Facebook 应用编号-->
            </intent-filter>
        </activity>

        <!-- Google登录 所需配置，可根据项目需求选择配置 -->
        <!-- Google 客户端ID -->
        <meta-data
            android:name="SERVER_CLIENT_ID"
            android:value="" >
        </meta-data>
    </application>
```

### 5、配置微信登录所需WXEntryActivity类
　　如游戏需要微信登录功能，需在你的包名相应目录下新建一个 wxapi 目录，并复制Demo工程下WXEntryActivity类到该目录

### 6、生命周期接口
　　提示：在游戏各个Activity生命周期中调用SDK生命周期接口。
```java
public void onStart() {
    super.onStart();
    MGSdkPlatform.getInstance().onStart();
}
public void onResume() {
    MGSdkPlatform.getInstance().onResume();
}
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
public void onDestroy() {
    super.onDestroy();
    MGSdkPlatform.getInstance().onDestroy();
}
public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    MGSdkPlatform.getInstance().onActivityResult(requestCode, resultCode, data);
}
```