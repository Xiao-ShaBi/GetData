# GetData
将请求数据与获取数据分开

## 旨在减少数据请求等待时间

## 使用
* 存储数据使用StoreData中的setDatas方法，也可以直接联网设置，在jiexiJson中将字符串转化为json对象存储起来
* 其中回调中refalshOrNot返回为true，一直监控数据变化，为false只获取一次

## 注意
* 当不需要或者存入的对象要销毁之前需要清除掉其中的数据
