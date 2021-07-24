## 框架介绍

MianActivity是本框架应用的demo

1 框架引入Evenbus
EventBus是一种用于Android的事件发布-订阅总线，它简化了应用程序内各个组件之间进行通信的复杂度，尤其是碎片之间进行通信的问题，可以避免由于使用广播通信而带来的诸多不便。

2 框架引入XUI（UI框架)。XUI框架涵盖绝大部分的UI组件：TextView、Button、EditText、ImageView、Spinner、Picker、Dialog、PopupWindow、ProgressBar、LoadingView、StateLayout、FlowLayout、Switch、Actionbar、TabBar、Banner、GuideView、BadgeView、MarqueeView、WebView、SearchView等一系列的组件和丰富多彩的样式主题。

3 Fresco，facebook推出的图片加载框架。

4 GSYVideoPlayer，基于IJKPlayer，实现了多功能的视频播放器。

5 MPAndroidChart，快速制作开发中需要的图表，如:折线图、柱形图、饼图、雷达图。

6 SmartTable，快速配置自动生成表格。可通过注解或自定义Java代码实现

7 okhttp、commons-lang3 等大家熟悉的框架。

8 common，作者自定义BaseActivity。此activity实现功能有
8.1 检测多语言环境
8.2 应用授权
关于本框架授权：
    *   第一步：请求权限   requestPermission
    *   第二步：检测所有的权限是否都已授权   checkPermissions
    *   第三步：获取权限集中需要申请权限的列表  getDeniedPermissions
    *   第四步：处理权限请求回调   onRequestPermissionsResult
    *   第五步：确认所有的权限是否都已授权  verifyPermissions
    *   第六步：授权成功处理函数or授权失败处理函数   permissionSuccess   or   permissionFail 
8.3 如需再加入其他的功能，请自行加入BaseActivity中。


9 common--->base，作者自定义MyAdapter。此MyAdapter为通用Adapter，ListView、GridView等均可通用。

10 common--->base，自定义SelectorFragmentPagerAdapter。通用viewPagerAdapter

11 common--->MultiLanguage，MultiLanguage，搭建了多语言环境，此环境支持国语，英文，粤语。环境已经搭好，粘贴代码直接用即可。

12 common--->OkHttp，OkHttp搭建了Http 环境。
LoggingInterceptor为登录的拦截，劫持工具包，本框架在此工具包中实现了 “若依框架” 的 “登录超时” 功能。如需修改请自行修改

13 common--->VideoPlayer，VideoPlayer为多功能播放器，CustomVideoView为自定义播放器模板。如需修改请自行修改

14 CipherUtils 加密与解密的工具类。其中有最常见的MD5加密，也有 根据指定的密钥及算法对指定字符串进行可逆加密。

15 DensityUtils 系统屏幕的一些操作。获取屏幕宽度、高度等

16 FileUtils 文件与流处理工具类。

17 PingUtil   ping工具类

18 ShareUtils  作者自定义分享功能工具类，可改成分享文档、图片、文件等等，此工具类中包含文件下载(自行在activity中实现)，保存等等

19 StringUtils  SystemTool ViewUtils   常用工具类

20 作者非Android科班出身，对于Android开发模式还停留在MVC层面上，因此本项目还是简单的MVC模式，MVC开发模式开发中小项目足矣，如果大项目，会显得Model层特别臃肿
这是本框架的初版，作者会不定期更新框架开发模式，暂定年前完成本框架的MVP MVVM版本

