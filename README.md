# AsmActualCombat
**使用文档链接：**https://github.com/Peakmain/AsmActualCombat/wiki
#### How To
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


#### 关于我
- 我的Github:https://github.com/peakmain
- 我的简书:https://www.jianshu.com/u/3ff32f5aea98
- 我的掘金:https://juejin.cn/user/175532853176152
