[# mg-sdk-android-sample]()

**Read this in other languages: [English](README.md), [中文](README.zh-CN.md).**

# MG SDK Android

`mg-sdk-android` is a unified login and payment SDK designed for mobile game developers, supporting various mainstream login methods and payment channels.

---

## 📌 Features

### 🔐 Login Methods

- WeChat Login
- QQ Login
- Facebook Login
- Google Login

### 💰 Payment Channels

- WeChat Pay
- Alipay
- MG Pay (Official)

---

## ⚙️ Quick Integration

### 1. Add Maven Repository

Add to your project `build.gradle`:

```groovy
repositories {
    mavenCentral()
}
```

### 2. Add SDK Dependency
```groovy
implementation 'com.mguwp.sdk:android:1.0.1'
```

### 3. Configure Channel & Application ID
```groovy
mg {
    applicationId "com.mg.game.jqyct" // Dynamic package name
    manifestPlaceholders = [CHANNEL_VALUE: "mg"] // Replace ${CHANNEL_VALUE} in the manifest
    dimension "custom"
}
```

## 🧪 Sample Code
See MainActivity.java for full examples of SDK usage: initialization, login, payment, and more.

### ✅ Initialize SDK
```java
MGSdkPlatform.getInstance().init(this, new MGInitListener() {
    @Override
    public void onSuccess(String msg) {
        // Init success
    }

    @Override
    public void onFailed(String msg) {
        // Init failed
    }
});
```

### 🔐 Login
```java
MGSdkPlatform.getInstance().login(this, new MGLoginListener() {
    @Override
    public void onSuccess(String msg) {
        // Login success
    }

    @Override
    public void onFailed(String msg) {
        // Login failed
    }
});
```

### 💳 Payment
```java
MGSdkPlatform.getInstance().pay(this, "PRODUCT_ID", "ORDER_INFO", "UNIQUE_USER_ID", new MGPayListener() {
    @Override
    public void onSuccess(String msg) {
        // Payment success
    }

    @Override
    public void onFailed(String msg) {
        // Payment failed
    }
});
```

### 👤 Real-Name Verification
```java
MGSdkPlatform.getInstance().antiAddition(this, new MGAntiAdditionListener() {
    @Override
    public void onSuccess(String msg) {
        // Verified
    }

    @Override
    public void onFailed(String msg) {
        // Verification failed
    }
});
```

### 🧩 AndroidManifest Setup
The SDK relies on several configuration parameters, including:

API Keys

Channel placeholder replacement using ${CHANNEL_VALUE}

Refer to the included AndroidManifest.xml for detailed configuration.

### 🔁 Lifecycle Binding
The SDK requires lifecycle binding within your Activity. For example:
```java
@Override
protected void onResume() {
    super.onResume();
    MGSdkPlatform.getInstance().onResume();
}
```
For other lifecycle methods, please refer to the sample MainActivity.java.

## 📞 Contact Us
If you encounter issues during integration, feel free to contact the MG SDK tech support team.