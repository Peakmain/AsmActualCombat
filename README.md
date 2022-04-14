# AsmActualCombat
ASM实战——埋点
- 可动态设置方法对点击事件处理之前进行拦截
- 可获取方法的耗时时间
- 默认包含防止多次点击事件的处理
- 可动态配置是否开启插件，默认是开启
#### 怎样使用
**ASM插件依赖**
Add it in your root build.gradle at the end of repositories:
```
buildscript {
  dependencies {
    classpath "io.github.peakmain:plugin:1.0.1"
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
	       implementation 'com.github.Peakmain:AsmActualCombat:1.0.0'
	}
```
#### 使用文档
- 1、Application中初始化
```
  SensorsDataAPI.init(this);
```
- 2、动态设置是否开启插件
在gradle.properties中设置以下参数，false代表不关闭插件，true表示关闭插件，默认是false
```
peakmainPlugin.disableAppClick=false
```

- 3、在要拦截的页面设置拦截事件
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
- 4、获取方法耗时时间
在需要获取耗时时间的方法上方添加以下注解
```
 @LogMessageTime
```
