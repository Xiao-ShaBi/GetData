# GetData
将请求数据与获取数据分开
app:           为简单的demo
GetDataLib：   为编写的库

## 旨在减少数据请求等待时间

## 使用
* 存储数据使用StoreData中的setDatas方法，也可以直接联网设置，在jiexiJson中将字符串转化为json对象存储起来
* 其中回调中refalshOrNot返回为true，一直监控数据变化，为false只获取一次

## 注意
* 当不需要或者存入的对象要销毁之前需要清除掉其中的数据

## 收获
*  想为自己的库添加一个绑定activity的方法，然后想到了glide使用加载的时候是可以的。
* 想到这里就去翻看了glide的源码发现他是使用一个很取巧的方法。
* 1.他不是直接监控activity的生命周期，在activity中有fragmentManage他使用这个在activity中的位置来监控activity的生命周期。
* 2.为每一个activity创建一个空白的fragment，通过监控这个fragment的生命周期来确定当前activity的生命周期
* 看到这里其实算是结束了，但是一个不小心看到了performCreate（）这个方法
* 这里实际上是创建activity的方法。调用时在Instrumentation中。这个是在创建应用时同步创建的，每个activity都要经过这一步
* 然后在网上看到了更改的方法，在App的onCreate中已经成功将自己写的Instrumentation插入其中，并且感觉可以使坏。
* 大概就这些东西。反射获得的方法以及ActivityThread我都没有找到在哪里
