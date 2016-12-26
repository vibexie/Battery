### Simple BatteryView for Android
 
![](http://qiniu.vibexie.com/github/battery.gif)

#### For gradle：
Step 1. Add the JitPack repository in your root build.gradle at the end of repositories:
``` xml
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

Step 2. Add the dependency
``` xml
compile 'com.github.vibexie:Battery:v1.0'
```

Step 3. Use like this
``` xml
<com.vibexie.batterylib.BatteryView
        android:id="@+id/bv"
        android:layout_width="100dp"
        android:layout_height="200dp"
        mv:shellWidth="10dp"
        mv:shellColor="#5C5C5C"
        mv:powerColor="#5CACEE"
        mv:amountColor="#EE4000"
        mv:amountTextSize="20sp"
        android:layout_centerInParent="true"/>
```
Then use setmPowerAmount(int Amount) method.
