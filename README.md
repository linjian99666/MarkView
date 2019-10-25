# MarkView
书签样式view

##1.How to use(如何使用)

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
	   implementation 'com.github.linjian99666:MarkView:v1.0.0'
	}
```

##2.Parameter interpretation(参数解释)

|    参数     |   解释    |    备注   |
| :-------- | :--------| :----- |
| mv_textSize  | 字体大小 |  与textSize使用一致,默认(14sp)   |
| mv_maxTextNum |   最多显示的字数 |  设置后,超出部分将显示省略号(...) ,默认4个 |
| mv_backgroundColor |    背景色 | 书签背景颜色,默认 0xFF0066FF  |
| mv_textColor      |    字体颜色| 文字颜色,默认 0xFFFFFFFF  |
| mv_text      |   文字内容 | 你要显示的书签内容,默认无  |
| mv_isAutoHeight      |   是否根据文字自适应高度 |  当你的文字内容<= 最多显示字数时,高度自适应,当你的文字内容>最多显示字数时,无效果.默认true |

###3.截图展示
[![截图](sss "截图")](/screenshots/Screenshot_2019-10-25-09-22-56-150_com.test.markviewexample.png "截图")
