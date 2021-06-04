# cmr-sdk-demo
车型画圈选择配件
> **CarModelRecognition 车型识别sdk打包接入**

- 下载 `cmr-release.aar`

  ```
  https://github.com/KangarooHangzhou/cmr-sdk-demo/blob/master/app/libs/cmr-release.aar
  ```



- 在项目的  `build.gradle` 添加

```groovy
repositories {
    flatDir {
       dirs 'libs'  // 声明添加libs文件夹为库
    }
}
```

- 打开对应`module`的根路径，新建libs文件夹，并且复制 `cmr-release.aar`到 `libs`文件夹
- 打开对应 `module`的 `build.gradle` ,添加对 `aar 的引用`

```groovy
dependencies {
   implementation (name:'cmr-release', ext:'aar')
}
```

- 添加如下依赖

```groovy
implementation 'androidx.navigation:navigation-fragment-ktx:2.3.0'
implementation 'androidx.navigation:navigation-ui-ktx:2.3.0'
implementation 'com.github.lzyzsd:jsbridge:1.0.4'

```


## 车型画圈

> **关于网址的特殊说明**
> 必传的几个参数：token: String?, vinCode: String?, optionCode: String?, body: String?, findVehicleWay: String?
> 其中 findVehicleWay 字段，普通车型传 A ，销售车型传 B
> 在传入网址时，必须先通过库中提供的 DrawPartWebView.jointURL(token: String?, vinCode: String?, optionCode: String?, body: String?, findVehicleWay: String?)
> 方法拼接成完整的网址。

### 方法一：通过 DrawPartWebView 接入
1. 直接在 xml 布局中使用：

```
<com.tongji.cmr.DrawPartWebView
    android:id="@+id/myWebView"
    android:layout_width="match_parent"
    android:layout_height="0dp"
    android:layout_weight="1" />
```

2. 给 DrawPartWebView 设置画圈结果回调监听器

```
findViewById<DrawPartWebView>(R.id.myWebView).apply {
    drawPartListener = object : DrawPartListener {
        override fun onDrawParted(data: String?) {
            data?.let {
                // 处理数据
            }
        }
    }
}
```

3. 给 DrawPartWebView 设置网页地址

```
webView.loadUrl(token, vinCode, optionCode, body)
```

### 方法二：通过 fragment 的形式接入

1. 给 fragment 所在的 Activity 实现回调监听接口 CarCircledListener
2. 给 fragment 设置网址：
可以通过 `CarModelFragment.newInstance("url", "")` 在初始化以参数的形式传入；
也可以调用 `carModelFragment.setNewData(url)` 方法传入

## 返回数据格式

<details>
<summary>展开查看[示例]</summary>
<pre><code>
{
  "context": "eyJ2aW4iOiIiLCJicmFuZCI6IuWunemprCIsImJvZHkiOiLkuInljqI06ZeoIiwibWpWZWhpY2xlU3lzIjoiNSBTZXJpZXMvNeezuyIsImVuY29kZWRBdmFpbGFibGVQYXJ0cyI6IjEgNTc3IDU2MiAxMiAyMyAyNSAzNTMgNTAgMTI5IDEzMyA4MyA5OCA1OTYgMzAwIDgwIDg4NCAyMzUgMjAxIDM4IDIgMzE4IDIzNiAyOSA4MjggNTMgMjYzIDkxMCAzMTkgMjI4IDI1MyAxOTggNjQgNTUgMjY1IDg2MSA5MzAgMjc2IDY5OCAzODkgNDA5IDQ3MCA4NDkgODUzIDEzNjkgNTY3IDE3NzggMTk4MiA5MTggNDQzIDQzMSAxNzc5IDM5IDE2MjcgMjA4IDcyMiA4MDggMTUzNSA0OCAyMDcgNTE3IDcwMyA1NTUgMTIzMiAxNzg4IDYgNyAyMiAxOSA4NTUgODg1IDE0OCAxMzkgOTQ5IDkxNiAyMTUgMTM2IDggMTIzIDM4OCA0MDAgMTI3NSAxNzY4IDc4NyA1NDkgNTY4IDUyNCAyMjkgODEyIDQyMCAzNiAzNSAzMDkgMjc0IDEzNyAxMjUgMTEyOCAxMDYxIDI1MCAyMTIgNzg2IDc3NCA2NzQgNjUxIDk3NyA5NzggMTEgMTMgMjQwIDIwMCAyMjAgMTg0IDUwNCAzNzcgMzY5IDMwNCAxMDYzIDExNzkgMjU3IDI2NiA0NDAgMzkwIDE1NjEgMTU4MCA5OTggMTA1NiA5NTUgMTQ1IDEzOCA0MzQgMzU5IDE1NjIgMTI5MyAxNzIgMTc0IDczMCA2ODkgMTY4IDE1NyA1NzUgNjQ2IDQ4NSA0OTQgMjk4IDI1NCA4NzUgMTA2MCA5NzEgNDQ5IDQ1OSAxNjI4IDE2NDUgMTAxNSA5NDMgMzQwIDMyMCAxNjEwIDE2NDYgNzQxIDc0MyAxNjM4IDE5MTcgMjA3NiAyMDc3IDIwNzggMTQ1NiA5NzIgMTAyMiA5IDE0IDMxNSAyOTMgMzAzIDI4NCA2MjQgNTg0IDQ0NCAzNjYgMTI1NiAxMjMzIDIyNCAyNjQgNDYzIDQ4NiAxODE4IDE5MDQgNzg4IDIyMiAxODAgNTczIDE0NzIgMTQ4OSAyNTkgMjM5IDEzMjYgMTM2NyA0MzIgNDI0IDI5MCAyNDUgOTIxIDg5OCA1MzkgNTE5IDQ0MSA0MDEgNjM5IDU5NSA0MjcgMzMxIDEwMjkgOTg4IDEzMTggMTI1NSAxNjc4IDE1MTQgNTkyIDYxOCAxNjU4IDE3NzMgMTcwNCAxNjc5IDQ3OSA0MzMgMTEyMCAxMTExIDE1MjUgMTcyNiA5OTQgOTk1IDIwMDggMjAwOSAxMjgzIDg3IDY3IDEwMDcgODU4IDc3OCA0NzMgNTEgNDgzIDQ3OCAxNDE0IDUzNCAxNTc4IDE0NjAgNjY0IDI1NiAxMzUwIDIxIDY5MiA2MDEgNTkgMjg3IDI3OSA4MjAgNzQ4IDg4NiA3OTEgMzEwIDMyMyA4MzIgMTUxNiAxNDQgMTAxMyAyMDIyIDE5NDQgMTU3OSAxNTA5IDMzIDI4OCAzOTMgMzk0IDEyMzggNjYzIDc3NSA3NjAgMTE0MCAyNDMgMjM0IDY0OSAxNDA4IDE3MyAxMjE2IDk4NSAxNjE2IDg2NiA5NzQgMTY5OSAxMzgyIDE5MyAxNjE3IDEzNzcgNDIzIDQ1MiA4MjIgODI1IDE2NDggNjg3IDI2NyAyNTIgNjY4IDY5NyA0ODggNjczIDc3MSA3MDkgMTM1NyAxMzIyIDg2IDc5IDg0OCA5MjMgNzEyIDc3MyAxMjIzIDEyMTUgNzY5IDgzNSA3NzAgOTIyIDkwOCA4NzQgNjkwIDY3MCA5ODMgOTc2IDEzNCAxODYgMTQwNiAxNjM3IDY3OSA3NDIgMTY4MCAxOTM3IDcwNiA4NjcgNDU2IDQwMiA1NTMgMTUgMTcgMTM4NCAxMTEwIDM2MyAxNzUgMTQ2IDY5MSA1OTEgNDkzIDY4MSA2NTUgMTI1MCAxMjkwIDExMTggMjAyNiAxMTU2IDcyOSAxODc2IDE5MTUgMjA2MiA5NTIgNTUyIDYyNiA1ODIgMTIwIDY2NiAxMTAyIDExMzYgMzc5IDE2MTEgMTY0OSAxNjk2IDU5NyAxMjkxIDE3MTYgMTA2NCAxMDI4IDE2ODcgODA0IDE5OTQgODM0IDIwOTggMjEwOSAxMTgzIDEzNDUgMTM5OCAxNDUzIDE1MTIgMTYzMiAxOTI1IDE3MTcgMTU0MSAxNjE5IDExNTcgMTM5NCAxMzg2IDE0NDggMTU1OCAxODUzIDExMzIgMTI1NCAxNTQyIDE2MzQgMTY1NiAxODIxIDE2MDMgMjEzMSAyMTMyIDc2IDU1NiA4MiA2MzggMTc5OCAxNjYyIDE4NzQgNjI3IDEzNzkgMTQxMiAxNTA3IDE1NDYgMjEzNCAyMTM1IDIxMzYgMjEzNyA0MjggMzk3IDEyNDggMTUyNyAxNDU4IDE4NzMgOTkzIDIwOTcgNzI1IDY4MCAxMTI1IDUyNyA5MDYgODkyIDE4ODQgMTUzNCAxNjMwIDcxMSA3NTQgNjg4IDE5MTggMTE0MSAxMTUwIDEyNzcgMTQxMSAxNTA2IDEwMDkgMTgxOSAxOTc1IDIwNjYgMTM0MCAxMzE3IDEwMzcgMjE1NyA3MzEgNzY1IDE0MzUgODgwIDE5OTcgMTMzOCAyMTYzIDE5MjYgMTgzNyAxMjk2IDEyNjIgMTQ5OCA0MjYgNzA1IDE2NzQgNTcwIDU3MSAxMjA1IDExODQgMjE3MyAyMDM5IDE0MDAgMjE3NCAxODI4IDIxNzcgNjM3IDE2MjEgMTUwIDIxNiAxODIgMTk1IDE4OCAxMDM1IDExMzAgMTYxMiA2OTMgMTQ1NCAxNDE5IDIwOTkgMzk1IDEwNzUgMTgwOCAxMjQyIDQ2NyAxODkwIDEzMDcgNzEwIDg5NCA3MzkgNzAgMTAzIDEwMSAxMDE0IDEwMzkgNTE1IDQ1OCAxMTA2IDEwNTkgNzI4IDY3NyA0MzkgMTIxIDExMyA2NiA2NSAxNTgyIDEzMzYgMjEwIDExODAgMjExIDE5NCA5NjQgMTExNiAxODI5IDE3ODAgMTUzIDIxOTkgMTAzNiA5NDUgMTM2MSAxNDI2IDExODggMTI2NCAyMDA3IDIyMDUgMTM4MyAzNjcgMzUxIDk4NyAxNzAzIDIwNjMgNTc0IDU0NyA1MjkgNDY4IDM4MCAzNjggNTY1IDg1OSAxNTI2IDE1MTUgMTE5OSAyMCAyODkgMzEgMTE2MCAzMTQgMjg2IDYzMSA0NzEgOTgwIDc3OSA5MDkgODM4IDMgNCAxNDIgMTMxIDM1MCAxMDc2IDE2MjYgMTUzMCA5MTcgOTEyIDIwMjEgMTk4NyAxNDczIDEyMzkgOTQ3IDEyODkgMTMyMCAxMzQ3IDU0MSAxNDkgMTUyIDI2IDI3IDM2MCA0MiA0MCA5MCA4OCAxNjA3IDI5NCAzODYgMTQxNyAxMzk3IDg1IDkxIDUwMiA1NjEgNDgwIDUxNiA3MyAxOTEwIDczNyAxNDEgMTMzMyAxMzU0IDQ3NSA1NTcgMTM5NiAxNDY2IDE0NDYgMTg1OCAxMjE0IDExMzggMTExOSAxMzAyIDE2MTggNDA1IDU3MiA1OTAgMTAyNCAxNDcwIDk0NCAyMjM5IDEwOTggMTE0IDE0MyA1NTEgMTM0MiAxMjMxIDEyMDYgMjI0MyAxNDE4IDExNTUgMTQ5NiAxOTk4IDIwNjkgMjggODcwIDI0NCA2NjkgMTA1NyA4NjQgMTE5IDE4MyAxNDcgOTMxIDEzNzAgMTQ2OSAzMTYgMTA0NyAxMTA5IDgwMSA5MTMgMTQzNiAxMzAzIDEzNDQgM zAgMTU4IDEyNjAgOTMyIDIwOSAyMjMgMjQxIDg0IDc3IDY4NCAyNjIgMjc4IDM3NiAzNzIgNTM2IDc4NCAxMTI0IDE0MzMgODc3IDEyNCA5OTcgNDI1IDExMCAxNzI3IDk5NiA3NDcgMTE1MSAyNDYgODgyIDgyMyAxNjkgNjE5IDE1MTMgODg3IDEwNTEgODkwIDExMzkgMzc1IDE2NSA2MTMgODE3IDUwMyAxMTA1IDU4NiA2MTIgNzQ5IDk5OSA1MzAgNDQ3IDUzNyA1MjggNDEgNDkyIDU5OSAzMTcgMTAwNCA1ODMgNTUwIDgxNiAxMTA4IDc5MiA4OTEgMjk5IDEyMTcgMTM3MyAxODUgMTA3NyAxNjAxIDUzMiAxMjM3IDkyNiAxMDEyIDgxMSAxNzAyIDQ0MiAzNDggMTIyNyAxMjc5IDE4NzggMTgyMiAxNTY2IDE1MTggMTMyOSAxMzIxIDUxMSAyMDE5IDEyMDkgMTcyOSAyMjg5IDE4NTYgMTQ4NSAxMTEyIDk5MSAxMzY4IDEyMTIgMTM4MCA4NzggMTM0MSAxMzE0IDEwNDUgMTE5MiAxNTAyIDkwMyAxMTUyIDE4MDAgMTE0NCA4OTkgMTgyMCAzMjIgOTgxIDEwMTggMTMxOSAxNjgyIDcyMyA2NTcgMTAyMSAxODUxIDIzNyA1NDUgMTU5OSA1MzggMTYxIDg0MiAxNzEgODkzIDM4NCA4MjEgMTM4NyAxMzU2IDEyNTcgOTQwIDE0NzQgMjMyNyAzMjcgNzk4IDgxOCAxMTAwIDExMTcgODg5IDEyMjkgNjU4IDE1MTEgMTQzMiAxMDUzIDcwOCAxMjczIDQxMCA2MTQgNTQ2IDY3OCAxMTA3IDE0NjggNzk5IDE0NTkgMTA2MiAxNTc3IDE3NDEgMTMyOCAxMjQ5IDE1NTkgMTM1MyAyMDE4IDE2NDMgMTQ0OSIsIm1vZGUiOiJhbGwiLCJtZXRob2QiOiJyZWdpb24iLCJhbGdvcml0aG0iOiIxYzFwIiwicmVnaW9ucyI6W3sicmVnaW9uQ29kZSI6InEiLCJyZWdpb25OYW1lIjoicSJ9LHsicmVnaW9uQ29kZSI6Im8iLCJyZWdpb25OYW1lIjoibyJ9LHsicmVnaW9uQ29kZSI6ImEiLCJyZWdpb25OYW1lIjoiYSJ9LHsicmVnaW9uQ29kZSI6InAiLCJyZWdpb25OYW1lIjoicCJ9LHsicmVnaW9uQ29kZSI6InIiLCJyZWdpb25OYW1lIjoiciJ9LHsicmVnaW9uQ29kZSI6Im4iLCJyZWdpb25OYW1lIjoibiJ9XSwic2hvd24iOlt7InBhcnROYW1lIjoi5Y+R5Yqo5py6572pIiwicGFydENvZGUiOiJBMDYwMTAwMSIsIm9wZXJhdGlvbiI6IiIsInBvc2l0aW9ucyI6W10sInNlbGVjdGVkIjpmYWxzZX0seyJwYXJ0TmFtZSI6IuWGt+WHneWZqCIsInBhcnRDb2RlIjoiQzA2MDEwMDEiLCJvcGVyYXRpb24iOiIiLCJwb3NpdGlvbnMiOltdLCJzZWxlY3RlZCI6ZmFsc2V9LHsicGFydE5hbWUiOiLmlaPng63lmagiLCJwYXJ0Q29kZSI6IkQwMTAxMDAxIiwib3BlcmF0aW9uIjoiIiwicG9zaXRpb25zIjpbXSwic2VsZWN0ZWQiOmZhbHNlfSx7InBhcnROYW1lIjoi5Y+R5Yqo5py65LiL5oqk5p2\/IiwicGFydENvZGUiOiJEMDQwMTAwMyIsIm9wZXJhdGlvbiI6IiIsInBvc2l0aW9ucyI6W10sInNlbGVjdGVkIjpmYWxzZX0seyJwYXJ0TmFtZSI6IuaVo+eDreWZqOahhuaetiIsInBhcnRDb2RlIjoiQTEyMDEwMDEiLCJvcGVyYXRpb24iOiIiLCJwb3NpdGlvbnMiOltdLCJzZWxlY3RlZCI6ZmFsc2V9LHsicGFydE5hbWUiOiLmlaPng63lmajkuIrmqKrmooEiLCJwYXJ0Q29kZSI6IkExMjAxMDA5Iiwib3BlcmF0aW9uIjoiIiwicG9zaXRpb25zIjpbXSwic2VsZWN0ZWQiOmZhbHNlfSx7InBhcnROYW1lIjoi5YmN57+85a2Q5p2\/77yI5Y+z77yJIiwicGFydENvZGUiOiJBMDUwMTAwMSIsIm9wZXJhdGlvbiI6IiIsInBvc2l0aW9ucyI6W10sInNlbGVjdGVkIjpmYWxzZX1dLCJpdGVtc1BlclBhZ2UiOjd9",
  "partList": [
    {
      "stdPartName": "发动机罩",
      "partCode": "A0601001",
      "partType": "normal",
      "image": "8nFTxIIEDA6TzNiAnNFmkFSPlWdzdfdIqBW8pu\/Q0zQ=",
      "comment": "",
      "srcPartNumber": "",
      "srcPartName": "车前盖",
      "substitute": "",
      "partPrice": "11800.00",
      "position": "",
      "partNumber": "41617111385",
      "qty": "1",
      "partRefOnImage": "01",
      "parent": null,
      "child": null,
      "operables": [
        {
          "operation": "replace",
          "severity": [

          ],
          "condition": [

          ],
          "chooseFlag": false,
          "paintLevel": [

          ],
          "accessoryStdPartName": null,
          "accessoryPartCode": null,
          "accessoryFlag": false,
          "countLimit": "1"
        },
        {
          "operation": "fit",
          "severity": [

          ],
          "condition": [

          ],
          "chooseFlag": false,
          "paintLevel": [

          ],
          "accessoryStdPartName": null,
          "accessoryPartCode": null,
          "accessoryFlag": false,
          "countLimit": "1"
        },
        {
          "operation": "paint",
          "severity": [

          ],
          "condition": [
            "repair"
          ],
          "chooseFlag": false,
          "paintLevel": [

          ],
          "accessoryStdPartName": null,
          "accessoryPartCode": null,
          "accessoryFlag": false,
          "countLimit": "1"
        },
        {
          "operation": "panel",
          "severity": [
            "middle"
          ],
          "condition": [

          ],
          "chooseFlag": false,
          "paintLevel": [

          ],
          "accessoryStdPartName": null,
          "accessoryPartCode": null,
          "accessoryFlag": false,
          "countLimit": "1"
        }
      ],
      "reqPartName": "发动机罩",
      "reqPartCode": "",
      "jhmPartName": "",
      "prefix": "Bmw",
      "subChainFlag": "0",
      "nameSource": "0",
      "reqPartPrice": null,
      "containFlag": null,
      "reqPartNumber": null,
      "assemblyFlag": "",
      "assemblyChildren": null,
      "imgUrl": null,
      "firstLevelId": null,
      "secondLevelId": null
    },
    {
      "stdPartName": "冷凝器",
      "partCode": "C0601001",
      "partType": "normal",
      "image": "vaW3RiEZEwY0R1lilZPUAImOdT7I60Od",
      "comment": "",
      "srcPartNumber": "",
      "srcPartName": "空调冷凝器 带干燥器",
      "substitute": "",
      "partPrice": "6730.00",
      "position": "",
      "partNumber": "64509122827",
      "qty": "1",
      "partRefOnImage": "01",
      "parent": null,
      "child": [
        "64536907403"
      ],
      "operables": [
        {
          "operation": "replace",
          "severity": [

          ],
          "condition": [

          ],
          "chooseFlag": false,
          "paintLevel": [

          ],
          "accessoryStdPartName": null,
          "accessoryPartCode": null,
          "accessoryFlag": false,
          "countLimit": "1"
        },
        {
          "operation": "fit",
          "severity": [

          ],
          "condition": [

          ],
          "chooseFlag": false,
          "paintLevel": [

          ],
          "accessoryStdPartName": null,
          "accessoryPartCode": null,
          "accessoryFlag": false,
          "countLimit": "1"
        }
      ],
      "reqPartName": "冷凝器",
      "reqPartCode": "",
      "jhmPartName": "",
      "prefix": "Bmw",
      "subChainFlag": "1",
      "nameSource": "0",
      "reqPartPrice": null,
      "containFlag": null,
      "reqPartNumber": null,
      "assemblyFlag": "",
      "assemblyChildren": null,
      "imgUrl": null,
      "firstLevelId": null,
      "secondLevelId": null
    },
    {
      "stdPartName": "散热器",
      "partCode": "D0101001",
      "partType": "normal",
      "image": "ElEroCqKN4m66MReu4BjKGn\/8NvmOa40",
      "comment": "",
      "srcPartNumber": "",
      "srcPartName": "冷却液冷却器",
      "substitute": "",
      "partPrice": "5002.74",
      "position": "",
      "partNumber": "17117795878",
      "qty": "1",
      "partRefOnImage": "01",
      "parent": null,
      "child": null,
      "operables": [
        {
          "operation": "replace",
          "severity": [

          ],
          "condition": [

          ],
          "chooseFlag": false,
          "paintLevel": [

          ],
          "accessoryStdPartName": null,
          "accessoryPartCode": null,
          "accessoryFlag": false,
          "countLimit": "99"
        },
        {
          "operation": "fit",
          "severity": [

          ],
          "condition": [

          ],
          "chooseFlag": false,
          "paintLevel": [

          ],
          "accessoryStdPartName": null,
          "accessoryPartCode": null,
          "accessoryFlag": false,
          "countLimit": "99"
        }
      ],
      "reqPartName": "散热器",
      "reqPartCode": "",
      "jhmPartName": "",
      "prefix": "Bmw",
      "subChainFlag": "0",
      "nameSource": "0",
      "reqPartPrice": null,
      "containFlag": null,
      "reqPartNumber": null,
      "assemblyFlag": "",
      "assemblyChildren": null,
      "imgUrl": null,
      "firstLevelId": null,
      "secondLevelId": null
    },
    {
      "stdPartName": "发动机下护板",
      "partCode": "D0401003",
      "partType": "normal",
      "image": "OEtOs+gb5XNc+ydeaeLoevLimxB\/3da4+E0zB7blIXY=",
      "comment": "",
      "srcPartNumber": "",
      "srcPartName": "发动机室屏蔽 前部",
      "substitute": "",
      "partPrice": "1249.99",
      "position": "",
      "partNumber": "51757159659",
      "qty": "1",
      "partRefOnImage": "05",
      "parent": null,
      "child": null,
      "operables": [
        {
          "operation": "replace",
          "severity": [

          ],
          "condition": [

          ],
          "chooseFlag": false,
          "paintLevel": [

          ],
          "accessoryStdPartName": null,
          "accessoryPartCode": null,
          "accessoryFlag": false,
          "countLimit": "99"
        },
        {
          "operation": "fit",
          "severity": [

          ],
          "condition": [

          ],
          "chooseFlag": false,
          "paintLevel": [

          ],
          "accessoryStdPartName": null,
          "accessoryPartCode": null,
          "accessoryFlag": false,
          "countLimit": "99"
        },
        {
          "operation": "panel",
          "severity": [
            "middle"
          ],
          "condition": [

          ],
          "chooseFlag": false,
          "paintLevel": [

          ],
          "accessoryStdPartName": null,
          "accessoryPartCode": null,
          "accessoryFlag": false,
          "countLimit": "99"
        }
      ],
      "reqPartName": "发动机下护板",
      "reqPartCode": "",
      "jhmPartName": "",
      "prefix": "Bmw",
      "subChainFlag": "1",
      "nameSource": "0",
      "reqPartPrice": null,
      "containFlag": null,
      "reqPartNumber": null,
      "assemblyFlag": "",
      "assemblyChildren": null,
      "imgUrl": null,
      "firstLevelId": null,
      "secondLevelId": null
    },
    {
      "stdPartName": "散热器框架",
      "partCode": "A1201001",
      "partType": "normal",
      "image": "ElEroCqKN4m66MReu4BjKGn\/8NvmOa40",
      "comment": "",
      "srcPartNumber": "",
      "srcPartName": "模块支架",
      "substitute": "",
      "partPrice": "1230.00",
      "position": "",
      "partNumber": "17117787443",
      "qty": "1",
      "partRefOnImage": "03",
      "parent": null,
      "child": null,
      "operables": [
        {
          "operation": "replace",
          "severity": [

          ],
          "condition": [

          ],
          "chooseFlag": false,
          "paintLevel": [

          ],
          "accessoryStdPartName": null,
          "accessoryPartCode": null,
          "accessoryFlag": false,
          "countLimit": "1"
        },
        {
          "operation": "paint",
          "severity": [

          ],
          "condition": [
            "repair"
          ],
          "chooseFlag": false,
          "paintLevel": [

          ],
          "accessoryStdPartName": null,
          "accessoryPartCode": null,
          "accessoryFlag": false,
          "countLimit": "1"
        },
        {
          "operation": "panel",
          "severity": [
            "serious"
          ],
          "condition": [

          ],
          "chooseFlag": false,
          "paintLevel": [

          ],
          "accessoryStdPartName": null,
          "accessoryPartCode": null,
          "accessoryFlag": false,
          "countLimit": "1"
        }
      ],
      "reqPartName": "散热器框架",
      "reqPartCode": "",
      "jhmPartName": "",
      "prefix": "Bmw",
      "subChainFlag": "0",
      "nameSource": "0",
      "reqPartPrice": null,
      "containFlag": null,
      "reqPartNumber": null,
      "assemblyFlag": "",
      "assemblyChildren": null,
      "imgUrl": null,
      "firstLevelId": null,
      "secondLevelId": null
    },
    {
      "stdPartName": "散热器上横梁",
      "partCode": "A1201009",
      " partType": "normal",
      "image": "X\/GTRzh5JxssyKsQnQE2zsg4W4JeG2XrFTtbcqNmAGk=",
      "comment": "",
      "srcPartNumber": "",
      "srcPartName": "连接件 前部车身 \/ 侧窗框",
      "substitute": "",
      "partPrice": "2010.00",
      "position": "",
      "partNumber": "51647033741",
      "qty": "1",
      "partRefOnImage": "01",
      "parent": null,
      "child": null,
      "operables": [
        {
          "operation": "replace",
          "severity": [

          ],
          "condition": [

          ],
          "chooseFlag": false,
          "paintLevel": [

          ],
          "accessoryStdPartName": null,
          "accessoryPartCode": null,
          "accessoryFlag": false,
          "countLimit": "1"
        },
        {
          "operation": "fit",
          "severity": [

          ],
          "condition": [

          ],
          "chooseFlag": false,
          "paintLevel": [

          ],
          "accessoryStdPartName": null,
          "accessoryPartCode": null,
          "accessoryFlag": false,
          "countLimit": "1"
        },
        {
          "operation": "paint",
          "severity": [

          ],
          "condition": [
            "repair"
          ],
          "chooseFlag": false,
          "paintLevel": [

          ],
          "accessoryStdPartName": null,
          "accessoryPartCode": null,
          "accessoryFlag": false,
          "countLimit": "1"
        },
        {
          "operation": "panel",
          "severity": [
            "middle"
          ],
          "condition": [

          ],
          "chooseFlag": false,
          "paintLevel": [

          ],
          "accessoryStdPartName": null,
          "accessoryPartCode": null,
          "accessoryFlag": false,
          "countLimit": "1"
        }
      ],
      "reqPartName": "散热器上横梁",
      "reqPartCode": "",
      "jhmPartName": "",
      "prefix": "Bmw",
      "subChainFlag": "0",
      "nameSource": "0",
      "reqPartPrice": null,
      "containFlag": null,
      "reqPartNumber": null,
      "assemblyFlag": "",
      "assemblyChildren": null,
      "imgUrl": null,
      "firstLevelId": null,
      "secondLevelId": null
    },
    {
      "stdPartName": "前翼子板（右）",
      "partCode": "A0501001",
      "partType": "normal",
      "image": "TL\/U9p9ZboGSQV7al8v2UZxSklsXkg8lJAsKcVJWPuE=",
      "comment": "",
      "srcPartNumber": "",
      "srcPartName": "右前侧围",
      "substitute": "",
      "partPrice": "4070.00",
      "position": "",
      "partNumber": "41357111430",
      "qty": "1",
      "partRefOnImage": "01",
      "parent": null,
      "child": null,
      "operables": [
        {
          "operation": "replace",
          "severity": [

          ],
          "condition": [

          ],
          "chooseFlag": false,
          "paintLevel": [

          ],
          "accessoryStdPartName": null,
          "accessoryPartCode": null,
          "accessoryFlag": false,
          "countLimit": "1"
        },
        {
          "operation": "fit",
          "severity": [

          ],
          "condition": [

          ],
          "chooseFlag": false,
          "paintLevel": [

          ],
          "accessoryStdPartName": null,
          "accessoryPartCode": null,
          "accessoryFlag": false,
          "countLimit": "1"
        },
        {
          "operation": "paint",
          "severity": [

          ],
          "condition": [
            "repair"
          ],
          "chooseFlag": false,
          "paintLevel": [

          ],
          "accessoryStdPartName": null,
          "accessoryPartCode": null,
          "accessoryFlag": false,
          "countLimit": "1"
        },
        {
          "operation": "panel",
          "severity": [
            "light"
          ],
          "condition": [

          ],
          "chooseFlag": false,
          "paintLevel": [

          ],
          "accessoryStdPartName": null,
          "accessoryPartCode": null,
          "accessoryFlag": false,
          "countLimit": "1"
        }
      ],
      "reqPartName": "前翼子板（右）",
      "reqPartCode": "",
      "jhmPartName": "",
      "prefix": "Bmw",
      "subChainFlag": "1",
      "nameSource": "0",
      "reqPartPrice": null,
      "containFlag": null,
      "reqPartNumber": null,
      "assemblyFlag": "",
      "assemblyChildren": null,
      "imgUrl": null,
      "firstLevelId": null,
      "secondLevelId": null
    }
      ],
      "minCollisionInfo": null,
      "maxCollisionInfo": null,
      "hasMore": true
    }
## 更详细的使用方法

请参考 Demo 源码。[https://github.com/KangarooHangzhou/CarModelRecognition](https://github.com/KangarooHangzhou/CarModelRecognition)

---

[点选](https://github.com/KangarooHangzhou/CarModelRecognition/blob/master/selectcar.md)



