# utils
工具lib库

gradle使用：

一、Project下的build.gradle文件下添加

allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
}

二、Module下的build.gradle文件下添加

dependencies {
	        compile 'com.github.Lvluffy:utils:1.0.0'
}

或者

dependencies {
	        implementation 'com.github.Lvluffy:utils:1.0.0'
}
