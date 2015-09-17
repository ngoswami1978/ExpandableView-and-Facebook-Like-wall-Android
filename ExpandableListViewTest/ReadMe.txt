If you want to run Facebook Wall Please chenge the AndroidManifest.xml file to following
Just Copy and Pest it  : 

<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.neerajweb.expandablelistviewtest" >

    <uses-sdk
        android:minSdkVersion="16"
        android:targetSdkVersion="23" />

    <uses-permission android:name="android.permission.INTERNET" />

    <application
        android:name="com.neerajweb.expandablelistviewtest.CustomFaceBook.AppController"
        android:icon="@drawable/user"
        android:label="@string/app_name"
        android:theme="@style/AppTheme" >
        <activity
            android:name="com.neerajweb.expandablelistviewtest.CustomFaceBook.myFaceBoookMainActivity"
            android:label="@string/app_name" >
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>
</manifest>

