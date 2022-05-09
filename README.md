# AsmActualCombat
### 功能介绍
#### ASM全埋点功能
-  $AppStart事件：应用程序启动启动事件。
-  $AppEnd事件:应用程序退出事件。
- $AppViewScreen事件:应用程序页面浏览事件
- $AppClick 事件:应用程序控件（View）点击事件,如:ImageView,Button,Dialog等
- 默认包含防止多次点击事件的处理
- 可动态设置方法对点击事件处理之前进行拦截，目前只支持对setOnClickListener进行拦截
#### 隐私方法调用处理
- 对调用隐私方法的方法体清空，如：设备id,Mac地址等
- 对调用隐私方法的方法体替换成自己的方法(支持动态替换方法)，如：设备id,Mac地址等

#### 辅助功能
- 可获取方法的耗时时间
- 打印方法的参数和返回值
- 打印方法的Frame
- 可动态配置是否开启插件，默认是开启
### 怎样使用
**ASM插件依赖**
Add it in your root build.gradle at the end of repositories:
```
buildscript {
  dependencies {
    classpath "io.github.peakmain:plugin:1.0.8"
  }
}

apply plugin: "com.peakmain.plugin"
```
**拦截事件sdk的依赖**
- Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:
```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
- Step 2. Add the dependency
```
	dependencies {
	       implementation 'com.github.Peakmain:AsmActualCombat:1.0.5'
	}
```
### 使用文档
#### Application中初始化
```
  SensorsDataAPI.init(this);
```
#### 动态设置是否开启插件
在gradle.properties中设置以下参数，false代表不关闭插件，true表示关闭插件，默认是false
```
peakmainPlugin.disableAppClick=false
```

#### 数据全埋点接口上传
```
SensorsDataAPI.getInstance().setOnUploadSensorsDataListener { state: Int, data: String ->
    when (state) {
            //$AppStart事件
        SensorsDataConstants.APP_START_EVENT_STATE,
            //$AppEnd事件
        SensorsDataConstants.APP_END__EVENT_STATE,
            //$AppViewScreen事件
        SensorsDataConstants.APP_VIEW_SCREEN__EVENT_STATE,
            //$AppClick 事件
        SensorsDataConstants.APP_VIEW_CLICK__EVENT_STATE
        -> if (BuildConfig.DEBUG) {
            Log.e("TAG", "埋点\n$data")
        }
        else -> if (BuildConfig.DEBUG) {
            Log.e("TAG", data)
        }
    }
}
```
#### 在要拦截的页面设置拦截事件
目前只支持setOnClickListener事件拦截
```
 SensorsDataAPI.getInstance().setOnUserAgreementListener(new SensorsDataAPI.OnUserAgreementListener() {
           @Override
            public boolean onUserAgreement() {
            
        }
 })
```
完整的举例代码如下：
```
    private void setUserAgreementListener(Activity activity) {
        isAcceptUserPrivacy = (Boolean) PreferencesUtils.getInstance(this).getParam(Constants.HAS_ACCEPT_USER_PRIVACY, false);
        SensorsDataAPI.getInstance().setOnUserAgreementListener(new SensorsDataAPI.OnUserAgreementListener() {
            @Override
            public boolean onUserAgreement() {
                if (!isAcceptUserPrivacy) {
                    //没有同意
                    AlertDialog dialog = new AlertDialog.Builder(activity)
                            .setContentView(R.layout.dialog_user_agreement)
                            .setCancelable(false)//点击空白不可取消
                            .show();
                     //设置用户协议的拦截事件为NULL
                    SensorsDataAPI.getInstance().setOnUserAgreementListener(null);
                    dialog.setOnClickListener(R.id.stv_refuse, v -> {
                        //取消按钮的点击事件
                        dialog.dismiss();
                        setUserAgreementListener(activity);
                    });
                    dialog.setOnClickListener(R.id.stv_agreement, v -> {
                    //同意按钮的点击事件
                        com.peakmain.ui.utils.ToastUtils.showLong("同意了");
                        PreferencesUtils.getInstance(activity).saveParams(Constants.HAS_ACCEPT_USER_PRIVACY, true);
                        dialog.dismiss();
                      
                    });
                }
                return isAcceptUserPrivacy;
            }
        });
```
#### 获取方法耗时时间和打印方法参数和返回值
在需要获取耗时时间的方法上方添加以下注解
```
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogMessage {
    /**
     * 是否打印方法耗时时间
     */
    boolean isLogTime() default false;

    /**
     *
     * 是否打印方法的参数和返回值
     */
    boolean isLogParametersReturnValue() default false;

}
```
##### 获取方法耗时时间
如果想获取方法耗时时间，则需要设置注解参数isLogTime为true，如：
```
@LogMessage(isLogTime = true)
public String getMethodTime(long l) {
    return "getMethod";
}
```
![image.png](https://upload-images.jianshu.io/upload_images/9387746-041cb906cc4483d7.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
##### 打印方法参数和返回值
如果想打印方法参数和返回值，则需要设置注解参数isLogParametersReturnValue,如:
```
@LogMessage(isLogParametersReturnValue = true)
public String getMethodParametersReturnValue(String name) {
    return "treasure";
}
```
![image.png](https://upload-images.jianshu.io/upload_images/9387746-98fad5908ea174bf.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
##### 获取方法耗时的同时打印方法参数和返回值
也可支持获取方法耗时的同时打印方法参数和返回值
```
@LogMessage(isLogTime = true, isLogParametersReturnValue = true)
public String test(int a) {
    return "peakmain";
}
```
![image.png](https://upload-images.jianshu.io/upload_images/9387746-19e418360bb87327.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
#### 打印方法的Frame
- 1、开启堆栈分析，默认是禁用,false表示开启，true表示禁用
```
monitorPlugin {
    disableStackMapFrame = false
}
```
- 2、如果想打印方法的Frame，只需要在方法上添加注解@LogFrameInfo，如:
```
@LogFrameInfo
public String testLogMethodStackMapFrame(int a) {
    try {
        Thread.sleep(1000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    return "peakmain";
}
```

#### 清空/替换方法体
##### 默认支持清空或替换的方法
- 获取设备id
- 获取getMeid
- 获取connectionInfo
- 获取Mac地址
- IP地址
- 获取AndroidId
##### 白名单
如果想让某个类不被替换或者不被清空，可以在whiteList中添加，如：
```
monitorPlugin {
    whiteList = [
            "com.peakmain.asmactualcombat.utils.TestUtils",
            "com.peakmain.plugin"
    ]
}
```
>**必须写到具体某个类**,只写到包名不可用，如：上面代码写到com.peakmain.asmactualcombat.utils并不能起到白名单作用

##### 清空方法体
###### 使用方式
- 使用方式app.gradle中设置methodStatus为2
```
monitorPlugin {
    methodStatus = 2
}
```
##### 替换方法体
###### 使用方式
- 使用方式app.gradle中设置methodStatus为3
```
monitorPlugin {
    methodStatus = 3
}
```
###### 自定义替换方法的返回值
```
SensorsDataAPI.getInstance().setOnReplaceMethodListener(new OnReplaceMethodListener() {
    @Override
    public String onReplaceMethodListener(int telephoneState, TelephonyManager manager, int slotIndex) {
        switch (telephoneState) {
            case SensorsDataConstants.GET_DEVICE_ID:
                LogUtils.e("替换GET_DEVICE_ID");
                break;
            case SensorsDataConstants.GET_MEID:
                LogUtils.e("替换GET_MEID");
                break;
            case SensorsDataConstants.GET_IMEI:
                LogUtils.e("替换GET_IMEI");
                break;
            case SensorsDataConstants.GET_SUBSCRIBER_ID:
                LogUtils.e("替换GET_SUBSCRIBER_ID");
                break;
            case SensorsDataConstants.GET_SIM_SERIAL_NUMBER:
                LogUtils.e("替换GET_SIM_SERIAL_NUMBER");
                break;
            default:
                break;
        }
        return "";
    }

    @Override
    public String onReplaceMethodListener(int wifiInfoState, WifiInfo wifiInfo) {
        switch (wifiInfoState) {
            case SensorsDataConstants.GET_MAC_ADDRESS:
                LogUtils.e("替换GET_MAC_ADDRESS");
                break;
            case SensorsDataConstants.GET_SSID:
                LogUtils.e("替换GET_SSID");
                break;
            case SensorsDataConstants.GET_BSSID:
                LogUtils.e("替换GET_SSIDGET_BSSID");
                break;
            case SensorsDataConstants.GET_IP_ADDRESS:
                LogUtils.e("替换GET_IP_ADDRESS");
                break;
            default:
                break;
        }
        return "";
    }

    @Override
    public WifiInfo onReplaceMethodListener(WifiManager wifiManager) {
        LogUtils.e("替换WifiManager");
        return null;
    }

    @Override
    public String onReplaceMethodListener(SubscriptionInfo subscriptionInfo) {
        LogUtils.e("替换SubscriptionInfo");
        return "";
    }

    @Override
    public List<PackageInfo> onReplaceMethodListener(PackageManager manager) {
        LogUtils.e("替换PackageManager");
        return new ArrayList<>();
    }

    @Override
    public String onReplaceMethodListener(ContentResolver resolver, String name) {
        LogUtils.e("替换ContentResolver");
        return "onReplaceMethodListener";
    }
});
```
##### 验证清空/替换方法体
我这里就以清空方法体为例了，为了验证，我APP引入了极光推送的
###### 未使用前
**自身的APP**
![image.png](https://upload-images.jianshu.io/upload_images/9387746-201740ee78a34d60.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![image.png](https://upload-images.jianshu.io/upload_images/9387746-f7e51bac0ac5bb47.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
**极光**
关于极光，大家可以看这几个类的相关方法
- cn/jiguang/an/d的getConnectionInfo
- cn/jiguang/ap/f的getConnectionInfo
- cn/jiguang/l/b的getConnectionInfo
- cn/jiguang/w/c的getDeviceId
- cn/jiguang/f/a的getDeviceId
- cn/jiguang/f/a的getConnectionInfo
- cn/jiguang/f/a的getMacAddress
- cn/jiguang/w/d的getDeviceId

- 我就不带大家看所有的了，我们挑几个
看cn/jiguang/f/a的getConnectionInfo和cn/jiguang/f/a的getDeviceId
- 因为我们看不到源码，我们只能通过字节码去看
![image.png](https://upload-images.jianshu.io/upload_images/9387746-d21483ddfbd84339.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![image.png](https://upload-images.jianshu.io/upload_images/9387746-94df9d33ca3ea45a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
##### 使用清空后
- 添加依赖,app的build.gradle
```
monitorPlugin {
    whiteList = [
            "com.peakmain.asmactualcombat.utils.TestUtils",
            "com.peakmain.plugin"
    ]
    methodStatus = 2//清空方法体
    disableStackMapFrame = false
}
```
**自身App**

![image.png](https://upload-images.jianshu.io/upload_images/9387746-6dcdcf3e292c1663.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![image.png](https://upload-images.jianshu.io/upload_images/9387746-844b1e34b899abd0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

可以发现白名单生效了

**极光推送**
![image.png](https://upload-images.jianshu.io/upload_images/9387746-8c4fd56ada253721.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![image.png](https://upload-images.jianshu.io/upload_images/9387746-b67180aea3a5c541.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


#### 关于我
- 我的Github:https://github.com/peakmain
- 我的简书:https://www.jianshu.com/u/3ff32f5aea98
- 我的掘金:https://juejin.cn/user/175532853176152
