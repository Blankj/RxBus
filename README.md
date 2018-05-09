![logo][logo]

[![rb][rbsvg]][rb] [![License][licensesvg]][license]


## Download

Gradle:
```groovy
implementation "com.blankj:rxbus:1.0"
```


## How to use

### 非粘性事件
1. 注册事件
  ```java
  public class YourActivity extends Activity {
      @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_main);

          // 注册 String 类型事件
          RxBus.getDefault().subscribe(this, new RxBus.Callback<String>() {
              @Override
              public void onEvent(String s) {
                  Log.e("eventTag", s);
              }
          });

          // 注册带 tag 为 "my tag" 的 String 类型事件
          RxBus.getDefault().subscribe(this, "my tag", new RxBus.Callback<String>() {
              @Override
              public void onEvent(String s) {
                  Log.e("eventTag", s);
              }
          });
      }

      @Override
      protected void onDestroy() {
          super.onDestroy();
          // 注销
          RxBus.getDefault().unregister(this);
      }
  }
  ```
2. 发送事件
  ```java
  // 发送 String 类型事件
  RxBus.getDefault().post("without tag");

  // 发送带 tag 为 "my tag" 的 String 类型事件
  RxBus.getDefault().post("with tag", "my tag");
  ```


### 粘性事件（也就是先发送事件，在之后注册的时候便会收到之前发送的事件）
1. 发送事件
  ```java
  // 发送 String 类型的粘性事件
  RxBus.getDefault().postSticky("without tag");

  // 发送带 tag 为 "my tag" 的 String 类型的粘性事件
  RxBus.getDefault().postSticky("with tag", "my tag");
  ```
2. 注册事件
  ```java
  public class YourActivity extends Activity {
      @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_main);

          // 注册 String 类型事件
          RxBus.getDefault().subscribeSticky(this, new RxBus.Callback<String>() {
              @Override
              public void onEvent(String s) {
                  Log.e("eventTag", s);
              }
          });

          // 注册带 tag 为 "my tag" 的 String 类型事件
          RxBus.getDefault().subscribeSticky(this, "my tag", new RxBus.Callback<String>() {
              @Override
              public void onEvent(String s) {
                  Log.e("eventTag", s);
              }
          });
      }

      @Override
      protected void onDestroy() {
          super.onDestroy();
          // 注销
          RxBus.getDefault().unregister(this);
      }
  }
  ```


## How it comes

网上现有 RxBus 存有的问题：

1. 使用的 RxBus 大多停留在 RxJava1 版本
2. RxBus 实现的粘性事件很多都是有问题的
3. 如果事件抛了异常，之后便再也无法接收到的问题
4. 同类型事件需自己再次封装 Bean 进行区别。

介于以上问题，我还是亲自封装一个简洁的供大家使用，库已经依赖了 RxAndroid 和 RxJava，所以导入了该库的就不需要再额外导入那两库了。

当然，如果通信频率比较高追求效率的话还是推荐使用 [EventBus](https://github.com/greenrobot/EventBus)。


## Principle

1. 利用 FlowableProcessor 既可以作为观察者又可以作为被观察者来实现事件总线
2. 粘性事件原理就是发送的时候把事件存到一个 hash 表中，在注册的时候查询下 hash 表中是否存在符合的事件，有的话就消费掉即可
3. 替换原有 LambdaSubscriber 来让抛了异常之后可以继续接收到后续事件
4. 封装了 TagMessage 来区分不同类别的 tag
5. 动态识别范型对象来省去传入 Type 类型

还有一些细节就自己看源码去了解吧，总共有用的代码不超过 300 行哈。


## [Change log](https://raw.githubusercontent.com/Blankj/RxBus/master/CHANGELOG.md)



[logo]: https://raw.githubusercontent.com/Blankj/RxBus/master/art/logo.png

[rbsvg]: https://img.shields.io/badge/RxBus-v1.0-brightgreen.svg
[rb]: https://github.com/Blankj/RxBus

[licensesvg]: https://img.shields.io/badge/License-Apache--2.0-brightgreen.svg
[license]: https://github.com/Blankj/RxBus/blob/master/LICENSE