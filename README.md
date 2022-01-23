# AsmActualCombat
ASM实战——埋点
#### 怎样使用
**ASM插件依赖**
```
 implementation 'io.github.peakmain:plugin:1.0.0'
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
	       implementation 'com.github.Peakmain:AsmActualCombat:0.1.0'
	}
```
#### 使用文档
Application中初始化
```
  SensorsDataAPI.init(this);
```
在要拦截的页面设置拦截事件
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
