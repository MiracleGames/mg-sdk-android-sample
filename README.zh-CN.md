[# mg-sdk-android-sample]()

**其他语言版本: [English](README.md), [中文](README.zh-CN.md).**

# MG SDK Android 

`mg-sdk-android` 是为游戏开发者提供的一站式登录与支付解决方案，支持多种主流平台账号接入和支付方式。  

---

## 📌 功能简介

### 🔐 登录方式

- 微信账号登录
- QQ账号登录
- Facebook账号登录
- 谷歌账号登录

### 💰 支付渠道

- 微信支付
- 支付宝支付
- MG支付（官方渠道）

---

## ⚙️ 快速集成

### 1. 添加 Maven 仓库

在项目 `build.gradle` 中添加：  

```groovy
repositories {
    mavenCentral()
}
```

### 2. 添加依赖
```groovy
implementation 'com.mguwp.sdk:android:1.0.1'
```

### 3. 配置动态渠道和包名 / Configure Channel & Application ID
```groovy
mg {
    applicationId "com.mg.game.jqyct" // 动态包名
    manifestPlaceholders = [CHANNEL_VALUE: "mg"] // 替换 manifest 中的 ${CHANNEL_VALUE}
    dimension "custom"
}
```

## 🧪 示例代码
示例代码在 MainActivity.java 中展示了完整的初始化、登录、支付调用流程。

### ✅ 初始化 SDK
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

### 🔐 登录接口
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

### 💳 支付接口
```java
MGSdkPlatform.getInstance().pay(this, "PRODUCT_ID", "ORDER_INFO", "UNIQUE_USER_ID", new MGPayListener() {
    @Override
    public void onSuccess(String msg) {
        // 支付成功
    }

    @Override
    public void onFailed(String msg) {
        // 支付失败
    }
});
```

### 👤 实名认证
```java
MGSdkPlatform.getInstance().antiAddition(this, new MGAntiAdditionListener() {
    @Override
    public void onSuccess(String msg) {
        // 实名认证成功
    }

    @Override
    public void onFailed(String msg) {
        // 实名认证失败
    }
});
```

### 🧩 AndroidManifest 配置说明
SDK 依赖多个参数配置，包括：

Key 值

渠道占位符 ${CHANNEL_VALUE} 替换

请参考 sample 中的 AndroidManifest.xml 示例进行配置。

### 🔁 生命周期绑定
SDK 在 Activity 生命周期中需调用对应方法，例如：
```java
@Override
protected void onResume() {
    super.onResume();
    MGSdkPlatform.getInstance().onResume();
}
```
其余方法请参考 sample 项目中的 MainActivity.java。

## 📞 联系我们
如果你在集成过程中遇到问题，欢迎联系 MG SDK 技术支持团队。