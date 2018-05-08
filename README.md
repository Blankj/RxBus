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

网上使用的 RxBus 大多停留在 RxJava1 版本，且 RxJava2 版本的 RxBus 实现的粘性事件很多都是有问题的，且同类型事件需自己再次封装 Bean 进行区别，所以，我还是自己亲手封装一个简洁的供大家使用，库已经依赖了 RxAndroid 和 RxJava，所以导入了该库的就不需要再额外导入那两库了。

当然，如果通信频率比较高追求效率的话还是推荐使用 [EventBus](https://github.com/greenrobot/EventBus)。



[logo]: https://raw.githubusercontent.com/Blankj/RxBus/master/art/logo.png

[rbsvg]: https://img.shields.io/badge/RxBus-v1.0-brightgreen.svg
[rb]: https://github.com/Blankj/RxBus

[licensesvg]: https://img.shields.io/badge/License-Apache--2.0-brightgreen.svg
[license]: https://github.com/Blankj/RxBus/blob/master/LICENSE