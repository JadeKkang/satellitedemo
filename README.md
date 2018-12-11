# 效果展示
![](https://github.com/JadeKkang/satellitedemo/blob/master/image/state.gif)
# 使用
    1.在项目gradle中添加
      allprojects {
        repositories { 
            ...
            maven { url 'https://jitpack.io' }
        }
      } 
    2.添加依赖
      {
        'com.github.JadeKkang:satellitedemo:v1.0.1'
      }
    3.xml中使用
         左上<
      <com.example.satellite.SatelliteMenu
        android:id="@+id/left_top" 
        app:orientation="left_top" 
        app:radius="100"
        app:itemSize="35dp"
        android:layout_width="match_parent"
        android:layout_height="match_parent" /> 
         左下	
      <com.example.satellite.SatelliteMenu
        app:orientation="left_bottom"
        app:radius="50"
        app:itemSize="30dp"
        android:layout_width="match_parent" 
        android:layout_height="match_parent" />
        右上
       <com.example.satellite.SatelliteMenu
        android:id="@+id/right_top" 
        app:orientation="right_top" 
        app:radius="100"
        app:isText="true"
        app:textColor="#1546e9" 
        app:textSize="10sp"
        app:itemSize="35dp" 
        android:layout_width="match_parent" 
        android:layout_height="match_parent" />
        右下
        <com.example.satellite.SatelliteMenu 
        android:id="@+id/right_bottom"
        app:orientation="right_bottom"
        app:radius="100"
        app:itemSize="30dp" 
        android:layout_width="match_parent"
        android:layout_height="match_parent" /> 
# 自定义属性
| 属性 | 值 | 描述 | 
| ------------- |:-------------:| -----:| 
| orientation |left_bottom、left_top、right_bottom、right_top| 控制显示的位置 | 
| bitmap | @mipmap/heart | 显示的图片 | 
| radius | 50 | 扩散圆的基准半径 | 
| isText | true |控制图片下面是否显示文字 | 
| textSize | 10sp | 图片下面文字的大小 | 
| itemSize | 40dp | 控制扩散view的大小 | 
| textColor | #1546e9 | 图片下面文字的颜色| 

# 预留方法

	1.setImg(int[] img)设置子View的图片资源

	2.setTexts(String[] text)当文字显示时,所显示的文字

	3.setClickItem(ClickItem clickItem)点击子View监听回调
# 注意
    1.设置子View数组的大小，就是点击扩散出去的Item数量，建议不要太多，要适当的改变radius属性
    2.当有文字显示时，一定要设置显示文字数组，一定要和设置子View的数组大小一样
    3.设置文字字体大小，不宜过大，文字长度不宜过长
    3.如不能满足要求，可自行扩展

