# 安卓开发规范(updating)

## 摘要

* [1 前言](#1-前言)
* [2 AS规范](#2-as规范)
* [3 命名规范](#3-命名规范)
* [4 资源文件规范](#4-资源文件规范)
* [5 版本统一规范](#5-版本统一规范)
* [6 注释规范](#6-注释规范)
* [7 代码管理规范](#7-代码管理规范)
* [8 RN规范](#8-RN规范)
* [9 其他的一些规范](#9-其他的一些规范)


### 1 前言

**深圳市幸福商城有限公司,关于android开发的代码规范,主要用于大家沟通和协作,快速熟悉环境,务必请大家查看和遵守**.


### 2 AS规范

工欲善其事，必先利其器。

1. 尽量使用最新版的IDE进行开发；
	- 1、目前AndroidStudio 稳定版本3.0.0
	- 2、使用IDE开发，BUILD：IU-181.4445.78
	- 3、Gradle 推荐使用的 4.1-all
	
2. 编码格式统一为**UTF-8**；
3. 编辑完`.java`、 `.xml`等文件后一定要**格式化**（基本格式方面使用 AS 默认模板即可）；
4. 删除多余的import，减少警告出现，可利用AS的`Optimize Imports`(Settings → Keymap → Optimize Imports)快捷键；



### 3 命名规范

代码中的命名严禁使用拼音与英文混合的方式,更不允许直接使用中文的方式。正确的英文拼写和语法可以让阅读者易于理解,避免歧义。

> 注意：即使纯拼音命名方式也要避免采用。但`alibaba`、`taobao`、`youku`、`hangzhou`等国际通用的名称,可视同英文。

#### 3.1 包名

包名全部小写，连续的单词只是简单地连接起来，不使用下划线，采用反域名命名规则，全部使用小写字母。一级包名是顶级域名，通常为`com`,`edu`,`gov`,`net`,`org`等,本公司采用的PBF(按功能分包Package By Feature)按功能分包（PBF)和MVP的分包每个模块结构参考如下
featureA相关的所有东西都在featureA包，feature内高内聚高度模块化，不同feature之间低耦合，相关的东西都放在一起，还好找

    


参考Google I/O 2015的代码结构，按功能分包具体可以这样做：



     src
      └─com
       └─domain
          └─app
            │  AppApplication.java  定义Application类
            │  Config.java          定义配置数据（常量）
            │
            ├─framework
            │      定义interface以及相关基类
            │
            ├─debug
            │    debug使用的文件件,在release的时候会忽略掉,不影响功能
            │
            ├─di
            │    定义Dagger2使用的注解Component ,以及Arouter使用的service,供apt使用
            │       
            ├─ui
            │  └─ widget
            │        自定义的一些控件View
            │  └─ Adapter 一些baseAdapter    
           以及自定义view和widget，
            │
            ├─receiver
            │      实现Receiver
            │
            ├─service
            │      实现Service（比如IntentService），用于在独立线程中异步do stuff
            │
            │
            ├─util
            │      实现工具类，提供静态方法
            │
            ├─feature1
            │ ├─model
            │ │存放MVP中的Modle层,提供便于操作model│的api,以及Retrofit的service和存放bean
            │ ├─presente
            │ │存放MVP中的presente层,用于操作View显示和数据获取
            │ ├─view
            │ └─  实现BaseActivity，相关的Adapter也放这里
            ├─...其它feature





#### 3.2 类名

类名都以`UpperCamelCase`风格编写。

| 类                  | 描述                                       | 例如                                       |
| :----------------- | :--------------------------------------- | :--------------------------------------- |
| Activity 类         | Activity为后缀标识                            | 欢迎页面类WelcomeActivity                     |
| Adapter类           | Adapter 为后缀标识                            | 新闻详情适配器 NewDetailAdapter                 |
| 解析类                | Parser为后缀标识                              | 首页解析类HomePosterParser                    |
| 工具方法类              | Utils或Manager为后缀标识（与系统或第三方的Utils区分）或功能+Utils | 线程池管理类：ThreadPoolManager日志工具类：LogUtils（Logger也可）打印工具类：PrinterUtils |
| 数据库类               | 以DBHelper后缀标识                            | 新闻数据库：NewDBHelper                        |
| Service类           | 以Service为后缀标识                            | 时间服务TimeService                          |
| BroadcastReceiver类 | 以Receiver为后缀标识                           | 推送接收JPushReceiver                        |
| ContentProvider类   | 以Provider为后缀标识                           | ShareProvider                            |
| 自定义的共享基础类          | 以Base开头                                  | BaseActivity,BaseFragment                |

测试类的命名以它要测试的类的名称开始，以Test结束。例如：`HashTest`或`HashIntegrationTest`。

接口（interface）：命名规则与类一样采用大驼峰命名法，多以able或ible结尾，如
`interface Runnable`、`interface Accessible`。

> 注意：如果项目采用MVP，所有Model、View、Presenter的接口都以I为前缀，不加后缀，其他的接口采用上述命名规则。


#### 3.3 方法名

方法名都以`lowerCamelCase`风格编写。

方法名通常是动词或动词短语。

| 方法                     | 说明                                   |
| :--------------------- | ------------------------------------ |
| initXX()               | 初始化相关方法,使用init为前缀标识，如初始化布局initView() |
| isXX() checkXX()       | 方法返回值为boolean型的请使用is或check为前缀标识      |
| getXX()                | 返回某个值的方法，使用get为前缀标识                  |
| setXX()                | 设置某个属性值                              |
| handleXX()/processXX() | 对数据进行处理的方法                           |
| displayXX()/showXX()   | 弹出提示框和提示信息，使用display/show为前缀标识       |
| updateXX()             | 更新数据                                 |
| saveXX()               | 保存数据                                 |
| resetXX()              | 重置数据                                 |
| clearXX()              | 清除数据                                 |
| removeXX()             | 移除数据或者视图等，如removeView();             |
| drawXX()               | 绘制数据或效果相关的，使用draw前缀标识                |

#### 3.4 常量名

常量名命名模式为`CONSTANT_CASE`，全部字母大写，用下划线分隔单词。那，到底什么算是一个常量？

每个常量都是一个静态`final`字段，但不是所有静态`final`字段都是常量。在决定一个字段是否是一个常量时，考虑它是否真的感觉像是一个常量。例如，如果任何一个该实例的观测状态是可变的，则它几乎肯定不会是一个常量。只是永远不打算改变对象一般是不够的，它要真的一直不变才能将它示为常量。

```java
// Constants
static final int NUMBER = 5;
static final ImmutableListNAMES = ImmutableList.of("Ed", "Ann");
static final Joiner COMMA_JOINER = Joiner.on(','); // because Joiner is immutable
static final SomeMutableType[] EMPTY_ARRAY = {};
enum SomeEnum { ENUM_CONSTANT }

// Not constants
static String nonFinal = "non-final";
final String nonStatic = "non-static";
static final SetmutableCollection = new HashSet();
static final ImmutableSetmutableElements = ImmutableSet.of(mutable);
static final Logger logger = Logger.getLogger(MyClass.getName());
static final String[] nonEmptyArray = {"these", "can", "change"};
```


#### 3.5 非常量字段名

非常量字段名以`lowerCamelCase`风格的基础上改造为如下风格：基本结构为`scopeVariableNameType`。

**scope：范围**

非公有，非静态字段命名以m开头。


例子：

```java
public class MyClass {
      int mPackagePrivate;  
      private int mPrivate;  
      protected int mProtected;
}
```


**Type：类型**


> 注意：如果项目中使用`ButterKnife`，则不添加m前缀，以`lowerCamelCase`风格命名。

请使用`mCustomerStrFirst`和`mCustomerStrLast`，而不要使用`mFirstCustomerStr`和`mLastCustomerStr`。

| 量词列表  | 量词后缀说明      |
| ----- | ----------- |
| First | 一组变量中的第一个   |
| Last  | 一组变量中的最后一个  |
| Next  | 一组变量中的下一个变量 |
| Prev  | 一组变量中的上一个   |
| Cur   | 一组变量中的当前变量  |

说明：

集合添加如下后缀：List、Map、Set
数组添加如下后缀：Arr

> 注意：所有的VO（值对象）统一采用标准的lowerCamelCase风格编写，所有的DTO（数据传输对象）就按照接口文档中定义的字段名编写。


#### 3.6 参数名

参数名以`lowerCamelCase`风格编写。
参数应该避免用单个字符命名。


#### 3.7 局部变量名

局部变量名以`lowerCamelCase`风格编写，比起其它类型的名称，局部变量名可以有更为宽松的缩写。

虽然缩写更宽松，但还是要避免用单字符进行命名，除了临时变量和循环变量。

即使局部变量是final和不可改变的，也不应该把它示为常量，自然也不能用常量的规则去命名它。


#### 3.8 临时变量

临时变量通常被取名为`i`、`j`、`k`、`m`和`n`，它们一般用于整型；`c`、`d`、`e`，它们一般用于字符型。 如：`for (int i = 0; i < len ; i++)`。


#### 3.9 类型变量名

类型变量可用以下两种风格之一进行命名：

1. 单个的大写字母，后面可以跟一个数字(如：`E`, `T`, `X`, `T2`)。
2. 以类命名方式(参考[3.2 类名](#32-类名))，后面加个大写的`T`(如：`RequestT`, `FooBarT`)。


更多还可参考～**[阿里巴巴Java开发手册][阿里巴巴Java开发手册]**


### 4 资源文件规范

#### 4.1 资源布局文件（XML文件（layout布局文件））

全部小写，采用下划线命名法

##### 4.1.1 contentView命名

必须以全部单词小写，单词间以下划线分割，使用名词或名词词组。

所有Activity或Fragment的contentView必须与其类名对应，对应规则为：将所有字母都转为小写，将类型和功能调换（也就是后缀变前缀）。

例如：`activity_main.xml`


##### 4.1.2 Dialog命名

规则：`dialog_描述.xml`

例如：`dialog_hint.xml`


##### 4.1.3 PopupWindow命名

规则：`ppw_描述.xml`

例如：`ppw_info.xml`


##### 4.1.4 列表项命名

规则：`item_描述.xml`

例如：`item_city.xml`


##### 4.1.5 包含项命名

规则：`模块_(位置)描述.xml`

例如：`activity_main_head.xml`、`activity_main_bottom.xml`

注意：通用的包含项命名采用：`项目名称缩写_描述.xml`

例如：`xxxx_title.xml`


#### 4.2 资源文件（图片drawable文件夹下）

全部小写，采用下划线命名法，加前缀区分

命名模式：可加后缀 `_small` 表示小图， `_big` 表示大图，逻辑名称可由多个单词加下划线组成，采用以下规则：

* `用途_模块名_逻辑名称`
* `用途_模块名_颜色`
* `用途_逻辑名称`
* `用途_颜色`

说明：用途也指控件类型（具体见附录[UI控件缩写表](#ui控件缩写表)）

例如：

| 名称                      | 说明                      |
| ----------------------- | ----------------------- |
| btn_main_home.png       | 按键`用途_模块名_逻辑名称`         |
| divider_maket_white.png | 分割线`用途_模块名_颜色`          |
| ic_edit.png             | 图标`用途_逻辑名称`             |
| bg_main.png             | 背景`用途_逻辑名称`             |
| btn_red.png             | 红色按键`用途_颜色`             |
| btn_red_big.png         | 红色大按键`用途_颜色`            |
| ic_head_small.png       | 小头像`用途_逻辑名称`            |
| bg_input.png            | 输入框背景`用途_逻辑名称`          |
| divider_white.png       | 白色分割线`用途_颜色`            |
| bg_main_head            | 主模块头部背景图片`用途_模块名_逻辑名称`  |
| def_search_cell         | 默认搜索界面单元图片`用途_模块名_逻辑名称` |
| ic_more_help            | 更多帮助图标`用途_逻辑名称`         |
| divider_list_line       | 列表分割线`用途_逻辑名称`          |
| selector_search_ok      | 搜索界面确认选择器`用途_模块名_逻辑名称`  |
| shape_music_ring        | 音乐界面环形形状`用途_模块名_逻辑名称`   |

如果有多种形态，如按钮选择器：`btn_xx.xml`(selector)

| 名称                   | 说明                           |
| -------------------- | ---------------------------- |
| btn_xx               | 按钮图片使用`btn_整体效果`（selector）   |
| btn_xx_normal        | 按钮图片使用`btn_正常情况效果`           |
| btn_xx_pressed       | 按钮图片使用`btn_点击时候效果`           |
| btn_xx_focused       | `state_focused`聚焦效果          |
| btn_xx_disabled      | `state_enabled` (false)不可用效果 |
| btn_xx_checked       | `state_checked`选中效果          |
| btn_xx_selected      | `state_selected`选中效果         |
| btn_xx_hovered       | `state_hovered`悬停效果          |
| btn_xx_checkable     | `state_checkable`可选效果        |
| btn_xx_activated     | `state_activated`激活的         |
| btn_xx_windowfocused | `state_window_focused`       |

> 注意：使用AndroidStudio的插件`SelectorChapek`可以快速生成selector，前提是命名要规范。


#### 4.3 动画文件（anim文件夹下）

全部小写，采用下划线命名法，加前缀区分。

具体动画采用以下规则：`模块名_逻辑名称`。

例如：`refresh_progress.xml`、`market_cart_add.xml`、`market_cart_remove.xml`。

普通的tween动画采用如下表格中的命名方式：`动画类型_方向`

| 名称                | 说明      |
| ----------------- | ------- |
| fade_in           | 淡入      |
| fade_out          | 淡出      |
| push_down_in      | 从下方推入   |
| push_down_out     | 从下方推出   |
| push_left         | 推向左方    |
| slide_in_from_top | 从头部滑动进入 |
| zoom_enter        | 变形进入    |
| slide_in          | 滑动进入    |
| shrink_to_middle  | 中间缩小    |


#### 4.4 values中name命名

##### 4.4.1 colors.xml

`colors`的`name`命名使用下划线命名法，在你的`colors.xml`文件中应该只是映射颜色的名称一个ARGB值，而没有其它的。不要使用它为不同的按钮来定义ARGB值。

**不要这样做**

```xml
  <resources>
      <color name="button_foreground">#FFFFFF</color>
      <color name="button_background">#2A91BD</color>
      <color name="comment_background_inactive">#5F5F5F</color>
      <color name="comment_background_active">#939393</color>
      <color name="comment_foreground">#FFFFFF</color>
      <color name="comment_foreground_important">#FF9D2F</color>
      ...
      <color name="comment_shadow">#323232</color>
```

使用这种格式，你会非常容易的开始重复定义ARGB值，这使如果需要改变基本色变的很复杂。同时，这些定义是跟一些环境关联起来的，如`button`或者`comment`, 应该放到一个按钮风格中，而不是在`color.xml`文件中。

**相反，这样做**

```xml
  <resources>

      <!-- grayscale -->
      <color name="clr_FFFFFF">#FFFFFF</color>
      <color name="clr_DBDBDB">#DBDBDB</color>
      <color name="clr_939393">#939393</color>
      <color name="clr_5F5F5F" >#5F5F5F</color>
      <color name="clr_323232">#323232</color>

      <!-- basic colors -->
      <color name="green">#27D34D</color>
      <color name="blue">#2A91BD</color>
      <color name="orange">#FF9D2F</color>
      <color name="red">#FF432F</color>

  </resources>
```

向应用设计者那里要这个调色板，名称不需要跟`"green"`、`"blue"`等等相同。`"brand_primary"`、`"brand_secondary"`、`"brand_negative"`这样的名字也是完全可以接受的。 像这样规范的颜色很容易修改或重构，会使应用一共使用了多少种不同的颜色变得非常清晰。 通常一个具有审美价值的UI来说，减少使用颜色的种类是非常重要的。

> 注意：如果某些颜色和主题有关，那就独立出去写。


##### 4.4.2 dimens.xml

像对待`colors.xml`一样对待`dimens.xml`文件 与定义颜色调色板一样，你同时也应该定义一个空隙间隔和字体大小的“调色板”。 一个好的例子，如下所示：

```xml
<resources>

    <!-- font sizes -->
    <dimen name="sp_22">22sp</dimen>

    <!-- typical spacing between two views -->
    <dimen name="dp_40">40dp</dimen>


</resources>

```

布局时在写`margins`和`paddings`时，你应该使用`spacing_xxxx`尺寸格式来布局，而不是像对待`string`字符串一样直接写值。 这样写会非常有感觉，会使组织和改变风格或布局是非常容易。


##### 4.4.3 strings.xml

`strings`的`name`命名使用下划线命名法，采用以下规则：`模块名+逻辑名称`，这样方便同一个界面的所有string都放到一起，方便查找。

| 名称                | 说明      |
| ----------------- | ------- |
| main_menu_about   | 主菜单按键文字 |
| friend_title      | 好友模块标题栏 |
| friend_dialog_del | 好友删除提示  |
| login_check_email | 登录验证    |
| dialog_title      | 弹出框标题   |
| button_ok         | 确认键     |
| loading           | 加载文字    |


##### 4.4.4 styles.xml

`style`的`name`命名使用大驼峰命名法，几乎每个项目都需要适当的使用`style`文件，因为对于一个视图来说有一个重复的外观是很常见的，将所有的外观细节属性（`colors`、`padding`、`font`）放在`style`文件中。 在应用中对于大多数文本内容，最起码你应该有一个通用的`style`文件，例如：

```
<style name="ContentText">
    <item name="android:textSize">@dimen/font_normal</item>
    <item name="android:textColor">@color/basic_black</item>
</style>
```

应用到`TextView`中:

```
<TextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="@string/price"
    style="@style/ContentText"
    />
```

你或许需要为按钮控件做同样的事情，不要停止在那里。将一组相关的和重复`android:****`的属性放到一个通用的`style`中。

**将一个大的`style`文件分割成多个文件**， 你可以有多个`styles.xml` 文件。Android SDK支持其它文件，`styles`这个文件名称并没有作用，起作用的是在文件 里xml的`<style>`标签。因此你可以有多个style文件`styles.xml`、`style_home.xml`、`style_item_details.xml`、`styles_forms.xml`。 不同于资源文件路径需要为系统构建起的有意义，在`res/values`目录下的文件可以任意命名。


#### 4.5 layout中的id命名

命名模式为：`view缩写_模块名_逻辑名`，比如`btn_main_search`
使用`AndroidStudio`的插件`ButterKnife Zelezny`，生成注解非常方便，原生的话可以使用`Android Code Generator`插件。

### 5 版本统一规范

Android开发存在着众多版本的不同，比如`compileSdkVersion`、`minSdkVersion`、`targetSdkVersion`以及项目中依赖第三方库的版本，不同的module及不同的开发人员都有不同的版本，所以需要一个统一版本规范的文件。

  请查看 version.gradle 文件




### 6 注释规范

为了减少他人阅读你代码的痛苦值，请在关键地方做好注释。

#### 6.1 类注释

每个类完成后应该有作者姓名和联系方式的注释，对自己的代码负责。

```java
/**
 * <pre>
 *     author : Blankj
 *     e-mail : xxx@xx
 *     time   : 2017/03/07
 *     desc   : //TODOxxxx描述
 *     version: 1.0
 * </pre>
 */
public class WelcomeActivity {
       ...
}
```

具体可以在AS中自己配制，Settings → Editor → File and Code Templates → Includes → File Header，输入

```java
/**
 * <pre>
 *     author : ${USER}
 *     e-mail : xxx@xx
 *     time   : ${YEAR}/${MONTH}/${DAY}
 *     desc   :  //TODO
 *     version: 1.0
 * </pre>
 */
```

这样便可在每次新建类的时候自动加上该头注释。


#### 6.2 方法注释

每一个成员方法（包括自定义成员方法、覆盖方法、属性方法）的方法头都必须做方法头注释，在方法前一行输入`/** + 回车`或者设置`Fix doc comment`(Settings → Keymap → Fix doc comment)快捷键，AS便会帮你生成模板，我们只需要补全参数即可，如下所示。

```java
/**
 * bitmap转byteArr
 *
 * @param bitmap bitmap对象
 * @param format 格式
 * @return 字节数组
 */
public static byte[] bitmap2Bytes(Bitmap bitmap, CompressFormat format) {
    if (bitmap == null) return null;
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    bitmap.compress(format, 100, baos);
    return baos.toByteArray();
}
```

#### 6.3 块注释

块注释与其周围的代码在同一缩进级别。它们可以是`/* ... */`风格，也可以是`// ...`风格(**`//`后最好带一个空格**）。对于多行的`/* ... */`注释，后续行必须从`*`开始， 并且与前一行的`*`对齐。以下示例注释都是OK的。

```java
/*
 * This is          // And so           /* Or you can
 * okay.            // is this.          * even do this. */
 */
```

注释不要封闭在由星号或其它字符绘制的框架里。

> Tip：在写多行注释时，如果你希望在必要时能重新换行(即注释像段落风格一样)，那么使用`/* ... */`。

#### 6.4 其他一些注释

AS已帮你集成了一些注释模板，我们只需要直接使用即可，在代码中输入`todo`、`fixme`等这些注释模板，回车后便会出现如下注释

``` java
// TODO: 17/3/14 需要实现，但目前还未实现的功能的说明
// FIXME: 17/3/14 需要修正，甚至代码是错误的，不能工作，需要修复的说明
```


### 7 代码管理规范


1. 代码管理使用SVN或者Git,提交之前请先update,提交时必须简要说明,修改和增加内容,有必要是打上tag.

2. 代码冲突解决,要保证master或者trunk上的代码能够releases或者能够运行版本,后者不能不经过协商就覆盖前者代码.
3. 


### 8 RN规范

// TODO...


### 9 其他的一些规范

1. 合理布局，有效运用`<merge>`、`<ViewStub>`、`<include>`标签；
2. `Activity`和`Fragment`里面有许多重复的操作以及操作步骤，所以我们都需要提供一个`BaseActivity`和`BaseFragment`，让所有的`Activity`和`Fragment`都继承这个基类。


3. 数据提供统一的入口。无论是在 MVP、MVC 还是 MVVM 中，提供一个统一的数据入口，都可以让代码变得更加易于维护。比如可使用一个`DataManager`，把 `http`、`preference`、`eventpost`、`database` 都放在`DataManger`里面进行操作，我们只需要与`DataManger`打交道；
4. 可引入 Dagger2 减少模块之间的耦合性。Dagger2 是一个依赖注入框架，使用代码自动生成创建依赖关系需要的代码。减少很多模板化的代码，更易于测试，降低耦合，创建可复用可互换的模块；
5. 项目引入`RxJava` + `RxAndroid`这些响应式编程，可以极大的减少逻辑代码；
6. 通过引入事件总线，如：`EventBus`、`AndroidEventBus`、`RxBus`，它允许我们在`DataLayer`中发送事件，以便`ViewLayer`中的多个组件都能够订阅到这些事件，减少回调；
7. 尽量采用懒加载的策略，即在需要的时候才创建；

  例如：

  ``` java
  String str = "aaa";
  if (i == 1) {
      list.add(str);
  }
  ```

  建议替换为：

  ``` java
  if (i == 1) {
      String str = "aaa";
      list.add(str);
  }
  ```


8. 尽量使用HashMap、ArrayList、StringBuilder，除非线程安全需要，否则不推荐使用Hashtable、Vector、StringBuffer，后三者由于使用同步机制而导致了性能开销；
9. 尽量在合适的场合使用单例；

  使用单例可以减轻加载的负担、缩短加载的时间、提高加载的效率，但并不是所有地方都适用于单例，简单来说，单例主要适用于以下三个方面：

  （1）控制资源的使用，通过线程同步来控制资源的并发访问

  （2）控制实例的产生，以达到节约资源的目的

  （3）控制数据的共享，在不建立直接关联的条件下，让多个不相关的进程或线程之间实现通信

10. 把一个基本数据类型转为字符串，`基本数据类型.toString()`是最快的方式、`String.valueOf(数据)`次之、`数据 + ""`最慢；
21. 使用AS自带的Lint来优化代码结构（什么，你不会？右键module、目录或者文件，选择Analyze → Inspect Code）；

---

最后啰嗦几句：

* 好的命名规则能够提高代码质量，使得新人加入项目的时候降低理解代码的难度；
* 规矩终究是死的，适合团队的才是最好的；
* 命名规范需要团队一起齐心协力来维护执行，在团队生活里，谁都不可能独善其身；
* 一开始可能会有些不习惯，持之以恒，总会成功的。

## 附录

### UI控件缩写表

| 名称             | 缩写   |
| -------------- | ---- |
| TextView       | tv   |
| EditText       | et   |
| ImageButton    | ib   |
| Button         | btn  |
| ImageView      | iv   |
| ListView       | lv   |
| GridView       | gv   |
| ProgressBar    | pb   |
| SeekBar        | sb   |
| RadioButtion   | rb   |
| CheckBox       | cb   |
| ScrollView     | sv   |
| LinearLayout   | ll   |
| FrameLayout    | fl   |
| RelativeLayout | rl   |
| RecyclerView   | rv   |
| WebView        | wv   |
| VideoView      | vv   |
| Spinner        | spn  |
| ToggleButton   | tb   |

### 常见的英文单词缩写表

| 名称                   | 缩写                |
| -------------------- | ----------------- |
| icon                 | ic （主要用在app的图标）   |
| color                | cl（主要用于颜色值）       |
| average              | avg               |
| background           | bg（主要用于布局和子布局的背景） |
| buffer               | buf               |
| control              | ctrl              |
| default              | def               |
| delete               | del               |
| document             | doc               |
| error                | err               |
| escape               | esc               |
| increment            | inc               |
| infomation           | info              |
| initial              | init              |
| image                | img               |
| Internationalization | I18N              |
| length               | len               |
| library              | lib               |
| message              | msg               |
| password             | pwd               |
| position             | pos               |
| server               | srv               |
| string               | str               |
| temp                 | tmp               |
| window               | wnd(win)          |

程序中使用单词缩写原则：不要用缩写，除非该缩写是约定俗成的。





## 版本日志
* 20180929: 发布初版: 仕明 

　　　　　　　　　　　　　　
