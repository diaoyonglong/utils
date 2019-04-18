# utils
工具lib库

## gradle使用：

一、Project下的build.gradle文件下添加

allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
}

二、Module下的build.gradle文件下添加

dependencies {
	        compile 'com.github.Lvluffy:utils:1.0.8'
}

或者

dependencies {
	        implementation 'com.github.Lvluffy:utils:1.0.8'
}

## 混淆文件

### RxJava RxAndroid

-dontwarn sun.misc.**

-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {

    long producerIndex;
    
    long consumerIndex;
    
}

-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {

    rx.internal.util.atomic.LinkedQueueNode producerNode;
    
}

-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {

    rx.internal.util.atomic.LinkedQueueNode consumerNode;
    
}

## 录屏

