ArcView[ ![Download](https://api.bintray.com/packages/hfutxqd/maven/ArcView/images/download.svg) ](https://bintray.com/hfutxqd/maven/ArcView/_latestVersion)
===============

An android custom view for draw configurable arc

![ArcView](https://raw.github.com/hfutxqd/ArcView/master/art/demo.gif)

Gradle
------
```
allprojects {
	repositories {
		...
		maven { url 'https://dl.bintray.com/hfutxqd/maven' }
	}
}

dependencies {
    ...
    implementation 'xyz.imxqd:arcview:1.0.0'
}
```

Usage
-----
```xml
<xyz.imxqd.arcview.ArcView
    android:id="@+id/demo"
    android:layout_gravity="center"
    android:layout_width="170dp"
    android:layout_height="170dp"
    app:stroke_width="5.7dp"
    app:start_angle="270"
    app:sweep_angle="270"
    app:stroke_color_start="#0018B0F2"
    app:stroke_color_end="#FF18B0F2"/>
```
