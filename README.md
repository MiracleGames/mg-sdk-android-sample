[# mg-sdk-android-sample]()

# MG SDK Android / MG SDK 安卓版

`mg-sdk-android` 是为游戏开发者提供的一站式登录与支付解决方案，支持多种主流平台账号接入和支付方式。  
`mg-sdk-android` is a unified login and payment SDK designed for mobile game developers, supporting various mainstream login methods and payment channels.

---

## 📌 功能简介 | Features

### 🔐 登录方式 | Login Methods

- 微信账号登录 / WeChat Login
- QQ账号登录 / QQ Login
- Facebook账号登录 / Facebook Login
- 谷歌账号登录 / Google Login

### 💰 支付渠道 | Payment Channels

- 微信支付 / WeChat Pay
- 支付宝支付 / Alipay
- MG支付（官方渠道）/ MG Pay (Official)

---

## ⚙️ 快速集成 | Quick Integration

### 1. 添加 Maven 仓库 / Add Maven Repository

在项目 `build.gradle` 中添加：  
Add to your project `build.gradle`:

```groovy
repositories {
    mavenCentral()
}
```

### 2. 添加依赖 / Add SDK Dependency
```groovy
implementation 'com.mguwp.sdk:android:1.0.1'
```

### 3. 配置动态渠道和包名 / Configure Channel & Application ID
```groovy
mg {
    applicationId "com.mg.game.jqyct" // 动态包名 / Dynamic package name
    manifestPlaceholders = [CHANNEL_VALUE: "mg"] // 替换 manifest 中的 ${CHANNEL_VALUE}
    dimension "custom"
}
```

## 🧪 示例代码 | Sample Code
示例代码在 MainActivity.java 中展示了完整的初始化、登录、支付调用流程。
See MainActivity.java for full examples of SDK usage: initialization, login, payment, and more.

### ✅ 初始化 SDK / Initialize SDK
```java
MGSdkPlatform.getInstance().init(this, new MGInitListener() {
    @Override
    public void onSuccess(String msg) {
        // 初始化成功 / Init success
    }

    @Override
    public void onFailed(String msg) {
        // 初始化失败 / Init failed
    }
});
```

### 🔐 登录接口 / Login
```java
MGSdkPlatform.getInstance().login(this, new MGLoginListener() {
    @Override
    public void onSuccess(String msg) {
        // 登录成功 / Login success
    }

    @Override
    public void onFailed(String msg) {
        // 登录失败 / Login failed
    }
});
```

### 💳 支付接口 / Payment
```java
MGSdkPlatform.getInstance().pay(this, "PRODUCT_ID", "ORDER_INFO", "UNIQUE_USER_ID", new MGPayListener() {
    @Override
    public void onSuccess(String msg) {
        // 支付成功 / Payment success
    }

    @Override
    public void onFailed(String msg) {
        // 支付失败 / Payment failed
    }
});
```

### 👤 实名认证 / Real-Name Verification
```java
MGSdkPlatform.getInstance().antiAddition(this, new MGAntiAdditionListener() {
    @Override
    public void onSuccess(String msg) {
        // 实名认证成功 / Verified
    }

    @Override
    public void onFailed(String msg) {
        // 实名认证失败 / Verification failed
    }
});
```

### 🧩 AndroidManifest 配置说明 | Manifest Setup
SDK 依赖多个参数配置，包括：
The SDK relies on several configuration parameters, including:

IP 地址 / IP Address

Key 值 / API Keys

渠道占位符 ${CHANNEL_VALUE} 替换 / Channel placeholder replacement using ${CHANNEL_VALUE}

请参考 sample 中的 AndroidManifest.xml 示例进行配置。
Refer to the included AndroidManifest.xml for detailed configuration.

### 🔁 生命周期绑定 / Lifecycle Binding
SDK 在 Activity 生命周期中需调用对应方法，例如：
```java
@Override
protected void onResume() {
    super.onResume();
    MGSdkPlatform.getInstance().onResume();
}
```
其余方法请参考 sample 项目中的 MainActivity.java。

## 📞 联系我们 | Contact Us
如果你在集成过程中遇到问题，欢迎联系 MG SDK 技术支持团队。
If you encounter issues during integration, feel free to contact the MG SDK tech support team.