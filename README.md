[![](https://jitpack.io/v/linjian99666/MarkView.svg)](https://jitpack.io/#linjian99666/MarkView)
# MarkView
一个书签样式view,可以直接使用
只支持androidx,如果你还未支持androidx 建议你直接拷贝源码(MarkView.class + attrs),然后再根据你的实际需求修改使用.

## 截图展示
<div>
<img src="/screenshots/screenshots1.png" width="400px"</img>
</div>

## 1.How to use(如何使用)

##### Step 1. Add the JitPack repository to your build file

```java
allprojects {
    	repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
##### Step 2. Add the dependency(Android X)
  
```java
 dependencies {
	   implementation 'com.github.linjian99666:MarkView:v1.0.1'
	}
```

## 2.Parameter interpretation(参数解释)

|    参数            |   解释    |    备注   |
| :--------          | :-------- | :----- |
| mv_textSize        | 字体大小  |  与textSize使用一致,默认(14sp)   |
| mv_maxTextNum      | 最多显示的字数 |  设置后,超出部分将显示省略号(...) ,默认4个 |
| mv_backgroundColor | 背景色     | 书签背景颜色,默认 0xFF0066FF  |
| mv_textColor       | 字体颜色   | 文字颜色,默认 0xFFFFFFFF  |
| mv_text            | 文字内容   | 你要显示的书签内容,默认无  |
| mv_isAutoHeight    | 是否自适应高度 |  当你的文字内容<= 最多显示字数时,高度自适应,当你的文字内容>最多显示字数时,无效果.默认true |
| mv_ratio           | 比率       | 底部三角形高度与 markview宽度的比值,取值范围[0,1],默认0.4  |


## 3.use in xml
```java
 <com.github.markview.MarkView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:padding="10dp"
        app:mv_backgroundColor="#0000ff"
        app:mv_isAutoHeight="false"
        app:mv_maxTextNum="4"
        app:mv_ratio="0.5"
        app:mv_text="降龙十八掌"
        app:mv_textColor="#ffffff"
        app:mv_textSize="14sp" />
```

## 4.Thanks
	your attention!!
	if you feel good,please give me a star.
