# Android SDK Environment Configuration

## 1. Introduction
This document describes how to configure the Miracle Games Android SDK in Android Studio.

## 2. Development Environment Configuration
### 1. Add Maven Repository
Add to project `build.gradle`:
```groovy
repositories {
    mavenCentral()
}
```
### 2. Add Dependencies
Add the dependency to your module's build.gradle:
```groovy
dependencies {
    implementation 'com.mguwp.sdk:android:1.0.3'
}
```

### 3. Game AndroidManifest.xml Configuration
> For Android 11 and above, you need to apply for the "QUERY_ALL_PACKAGES" permission, otherwise WeChat Pay cannot be opened.

