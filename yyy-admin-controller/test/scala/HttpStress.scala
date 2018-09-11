
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

/**
  * 如何引入gatling: https://gatling.io/docs/2.3/extensions/sbt_plugin/?highlight=sbt
  *
Snapshots are available on Sonatype.

In project/plugins.sbt, add:

addSbtPlugin("io.gatling" % "gatling-sbt" % "2.2.0")
You’ll also need those two dependencies:

"io.gatling.highcharts" % "gatling-charts-highcharts" % "2.2.2" % "test"
"io.gatling"            % "gatling-test-framework"    % "2.2.2" % "test"
And then, in your .scala build:

import io.gatling.sbt.GatlingPlugin

lazy val project = Project(...)
                   .enablePlugins(GatlingPlugin)
                   .settings(libraryDependencies ++= /* Gatling dependencies */)
or in your .sbt file, for SBT up to 0.13.5:

val test = project.in(file("."))
  .enablePlugins(GatlingPlugin)
  .settings(libraryDependencies ++= /* Gatling dependencies */)
or for 0.13.6 and later:

enablePlugins(GatlingPlugin)

libraryDependencies ++= /* Gatling dependencies */
  ×*/


/**
  * 这是个基于gatling包装的压测工具
  * 因为gatling的setUp()函数在一次测试中只能调用１次，所以使用时不能连续多次调测试函数
  */
class HttpStress() extends Simulation{

    /**
      * 最简单的http　get压测，仅仅检查返回的status code是不是200
      * @param urls: 被测试接口的url
      * @param paramMap: 参数列表,将被拼接到url里, 例如 Map("vendor" -> "xiaomi", "location" -> "Beijing")
      * @param userHeaders: 放置在http header里, 例如 Map("user" -> "xiaomi-ai")
      * @param userCount: 模拟多少个并发用户；
      * @param repeat：　每个用户重复多少次；
      * @param caseName：　测试用例名，用于在报表里展示;
      */
    def get(urls:List[String],
            paramMap:Map[String,String] = Map(),
            userHeaders:Map[String, String] = Map(),
            userCount:Int = 1,
            repeat:Int = 1,
            caseName: String = "test-get") : Unit = {
        val httpConf = http.baseURLs(urls)
        val scn = scenario("httpget")
                .repeat(repeat, "n"){
                    exec(
                        http(caseName)
                                .get("")
                                .queryParamMap(paramMap)
                                .headers(userHeaders)
                                .check(status.is(200), bodyString.saveAs("resp"))

                    )
                            .exec(session => {
                                val resp: String = session("resp").as[String]
                                println("server resp: " + resp)
                                session
                            })
                }
        setUp(scn.inject(atOnceUsers(userCount)).protocols(httpConf))
    }

    /**
      * 简单的http get,假设服务端返回一个字符串，只要字符串里包含某些字符串就算成功
      * @param urls: 被测试接口的url
      * @param paramMap: 参数列表,将被拼接到url里, 例如 Map("vendor" -> "xiaomi", "location" -> "Beijing")
      * @param userHeaders: 放置在http header里, 例如 Map("user" -> "xiaomi-ai")
      * @param userCount: 模拟多少个并发用户；
      * @param repeat：　每个用户重复多少次；
      * @param caseName：　测试用例名，用于在报表里展示;
      */
    def getAndCheck(urls:List[String],
                    expect:String,
                    paramMap:Map[String,String] = Map(),
                    userHeaders:Map[String, String] = Map(),
                    userCount:Int = 1,
                    repeat:Int = 1,
                    caseName: String = "test-get") : Unit = {
        val httpConf = http.baseURLs(urls)
        val scn = scenario("httpget")
                .repeat(repeat, "n"){
                    exec(
                        http(caseName)
                                .get("")
                                .queryParamMap(paramMap)
                                .headers(userHeaders)
                                .check(status.is(200), bodyString.saveAs("resp"))
                                .check(substring(expect).exists)
                    )
                            .exec(session => {
                                val resp: String = session("resp").as[String]
                                println("server resp: " + resp)
                                session
                            })
                }
        setUp(scn.inject(atOnceUsers(userCount)).protocols(httpConf))
    }

    /**
      * http get,检查返回结果中是否存在某个json的key, 如果key位置不是最外层的话请遵循这个规范http://goessner.net/articles/JsonPath/
      * 例如: {"person": {"name": "xiaomi", "age": 7}} 那么要判断name是否存在，jsonKey传入 "person.name"
      * @param urls: 被测试接口的url
      * @param paramMap: 参数列表,将被拼接到url里, 例如 Map("vendor" -> "xiaomi", "location" -> "Beijing")
      * @param userHeaders: 放置在http header里, 例如 Map("user" -> "xiaomi-ai")
      * @param userCount: 模拟多少个并发用户；
      * @param repeat：　每个用户重复多少次；
      * @param caseName：　测试用例名，用于在报表里展示;
      */
    def getAndCheckJsonKey(urls:List[String],
                           jsonKey:String,
                           paramMap:Map[String,String] = Map(),
                           userHeaders:Map[String, String] = Map(),
                           userCount:Int = 1,
                           repeat:Int = 1,
                           caseName: String = "test-get") : Unit = {
        val httpConf = http.baseURLs(urls)
        val strKey = "$." +jsonKey
        val scn = scenario("httpget")
                .repeat(repeat, "n"){
                    exec(
                        http(caseName)
                                .get("")
                                .queryParamMap(paramMap)
                                .headers(userHeaders)
                                .asJSON
                                .check(status.is(200), bodyString.saveAs("resp"))
                                .check(jsonPath(strKey).exists)

                    )
                            .exec(session => {
                                val resp: String = session("resp").as[String]
                                println("server resp: " + resp)
                                session
                            })
                }
        setUp(scn.inject(atOnceUsers(userCount)).protocols(httpConf))
    }

    /**
      * http get,检查返回结果中是否存在某个json的key和value是否正确, Gatling的限制了只能检查String类型的value
      * 如果key位置不是最外层的话请遵循这个规范http://goessner.net/articles/JsonPath/
      * 例如: {"person": {"name": "xiaomi", "age": 7}} 那么要判断name是否存在，jsonKey传入 "person.name"
      * @param urls: 被测试接口的url
      * @param paramMap: 参数列表,将被拼接到url里, 例如 Map("vendor" -> "xiaomi", "location" -> "Beijing")
      * @param userHeaders: 放置在http header里, 例如 Map("user" -> "xiaomi-ai")
      * @param userCount: 模拟多少个并发用户；
      * @param repeat：　每个用户重复多少次；
      * @param caseName：　测试用例名，用于在报表里展示;
      */
    def getAndCheckJsonKeyValue(urls:List[String],
                                jsonKey:String,
                                jsonValue:String,
                                paramMap:Map[String,String] = Map(),
                                userHeaders:Map[String, String] = Map(),
                                userCount:Int = 1,
                                repeat:Int = 1,
                                caseName: String = "test-get") : Unit = {
        val httpConf = http.baseURLs(urls)
        val strKey = "$." +jsonKey
        val scn = scenario("httpget")
                .repeat(repeat, "n"){
                    exec(
                        http(caseName)
                                .get("")
                                .queryParamMap(paramMap)
                                .headers(userHeaders)
                                .asJSON
                                .check(status.is(200), bodyString.saveAs("resp"))
                                .check(jsonPath(strKey).exists, jsonPath(strKey).is(jsonValue))

                    )
                            .exec(session => {
                                val resp: String = session("resp").as[String]
                                println("server resp: " + resp)
                                session
                            })
                }
        setUp(scn.inject(atOnceUsers(userCount)).protocols(httpConf))
    }

    /**
      * 最简单的http　post压测，仅仅检查返回的status code是不是200
      * @param urls: 被测试接口的url
      * @param userData: 形如"k1=v1&k2=v2" 或 """{ "myContent": "Hi, I am From Xiaomi" }""" 格式的字符串
      * @param paramMap: 参数列表,将被拼接到url里, 例如 Map("vendor" -> "xiaomi", "location" -> "Beijing")
      * @param userHeaders: 放置在http header里, 例如 Map("user" -> "xiaomi-ai")
      * @param userCount: 模拟多少个并发用户；
      * @param repeat：　每个用户重复多少次；
      * @param caseName：　测试用例名，用于在报表里展示;
      */
    def post(urls:List[String],
             userData:String,
             paramMap:Map[String,String] = Map(),
             userHeaders:Map[String, String] = Map(),
             userCount:Int = 1,
             repeat:Int = 1,
             caseName: String = "test-post") : Unit = {
        val httpConf = http.baseURLs(urls)
        //judge user data is string or json
        if(userData.contains("{") && userData.contains("}")) {
            val scn = scenario("httppost")
                    .repeat(repeat, "n") {
                        exec(
                            http(caseName)
                                    .post("")
                                    .queryParamMap(paramMap)
                                    .headers(userHeaders)
                                    .body(StringBody(userData)).asJSON
                                    .check(status.is(200), bodyString.saveAs("resp"))

                        )
                                .exec(session => {
                                    val resp: String = session("resp").as[String]
                                    println("resp: " + resp)
                                    session
                                })
                    }
            setUp(scn.inject(atOnceUsers(userCount)).protocols(httpConf))
        }
        else {
            val scn = scenario("httppost")
                    .repeat(repeat, "n") {
                        exec(
                            http(caseName)
                                    .post("")
                                    .queryParamMap(paramMap)
                                    .headers(userHeaders)
                                    .body(StringBody(userData)).asFormUrlEncoded
                                    .check(status.is(200), bodyString.saveAs("resp"))

                        )
                                .exec(session => {
                                    val resp: String = session("resp").as[String]
                                    println("resp: " + resp)
                                    session
                                })
                    }
            setUp(scn.inject(atOnceUsers(userCount)).protocols(httpConf))
        }
    }


    /**
      * 简单的http post,假设服务端返回一个字符串，只要字符串里包含某些字符串就算成功
      * @param urls: 被测试接口的url
      * @param userData: 形如"k1=v1&k2=v2" 或 """{ "myContent": "Hi, I am From Xiaomi" }""" 格式的字符串
      * @param paramMap: 参数列表,将被拼接到url里, 例如 Map("vendor" -> "xiaomi", "location" -> "Beijing")
      * @param userHeaders: 放置在http header里, 例如 Map("user" -> "xiaomi-ai")
      * @param userCount: 模拟多少个并发用户；
      * @param repeat：　每个用户重复多少次；
      * @param caseName：　测试用例名，用于在报表里展示;
      */
    def postAndCheck(urls:List[String],
                     userData:String,
                     expect:String,
                     paramMap:Map[String,String] = Map(),
                     userHeaders:Map[String, String] = Map(),
                     userCount:Int = 1,
                     repeat:Int = 1,
                     caseName: String = "test-post") : Unit = {
        val httpConf = http.baseURLs(urls)
        //judge user data is string or json
        if(userData.contains("{") && userData.contains("}")) {
            val scn = scenario("httppost")
                    .repeat(repeat, "n") {
                        exec(
                            http(caseName)
                                    .post("")
                                    .queryParamMap(paramMap)
                                    .headers(userHeaders)
                                    .body(StringBody(userData)).asJSON
                                    .check(status.is(200), bodyString.saveAs("resp"))
                                    .check(substring(expect).exists)
                        )
                                .exec(session => {
                                    val resp: String = session("resp").as[String]
                                    println("server resp: " + resp)
                                    session
                                })
                    }
            setUp(scn.inject(atOnceUsers(userCount)).protocols(httpConf))
        }
        else {
            val scn = scenario("httppost")
                    .repeat(repeat, "n") {
                        exec(
                            http(caseName)
                                    .post("")
                                    .queryParamMap(paramMap)
                                    .headers(userHeaders)
                                    .body(StringBody(userData)).asFormUrlEncoded
                                    .check(status.is(200), bodyString.saveAs("resp"))
                                    .check(substring(expect).exists)
                        )
                                .exec(session => {
                                    val resp: String = session("resp").as[String]
                                    println("server resp: " + resp)
                                    session
                                })
                    }
            setUp(scn.inject(atOnceUsers(userCount)).protocols(httpConf))
        }
    }

    /**
      * http post,检查返回结果中是否存在某个json的key, 如果key位置不是最外层的话请遵循这个规范http://goessner.net/articles/JsonPath/
      * 例如: {"person": {"name": "xiaomi", "age": 7}} 那么要判断name是否存在，jsonKey传入 "person.name"
      * @param urls: 被测试接口的url
      * @param userData: 形如"k1=v1&k2=v2" 或 """{ "myContent": "Hi, I am From Xiaomi" }""" 格式的字符串
      * @param paramMap: 参数列表,将被拼接到url里, 例如 Map("vendor" -> "xiaomi", "location" -> "Beijing")
      * @param userHeaders: 放置在http header里, 例如 Map("user" -> "xiaomi-ai")
      * @param userCount: 模拟多少个并发用户；
      * @param repeat：　每个用户重复多少次；
      * @param caseName：　测试用例名，用于在报表里展示;
      */
    def postAndCheckJsonKey(urls:List[String], userData:String, jsonKey:String, paramMap:Map[String,String] = Map(), userHeaders:Map[String, String] = Map(),
                            userCount:Int = 1, repeat:Int = 1,  caseName: String = "test-post") : Unit = {
        val httpConf = http.baseURLs(urls)
        val strKey = "$." +jsonKey
        //judge user data is string or json
        if(userData.contains("{") && userData.contains("}")) {
            val scn = scenario("httppost")
                    .repeat(repeat, "n") {
                        exec(
                            http(caseName)
                                    .post("")
                                    .queryParamMap(paramMap)
                                    .headers(userHeaders)
                                    .body(StringBody(userData)).asJSON
                                    .check(status.is(200), bodyString.saveAs("resp"))
                                    .check(jsonPath(strKey).exists)

                        )
                                .exec(session => {
                                    val resp: String = session("resp").as[String]
                                    println("server resp: " + resp)
                                    session
                                })
                    }
            setUp(scn.inject(atOnceUsers(userCount)).protocols(httpConf))
        }
        else {
            val scn = scenario("httppost")
                    .repeat(repeat, "n") {
                        exec(
                            http(caseName)
                                    .post("")
                                    .queryParamMap(paramMap)
                                    .headers(userHeaders)
                                    .body(StringBody(userData)).asFormUrlEncoded
                                    .check(status.is(200), bodyString.saveAs("resp"))
                                    .check(jsonPath(strKey).exists)

                        )
                                .exec(session => {
                                    val resp: String = session("resp").as[String]
                                    println("server resp: " + resp)
                                    session
                                })
                    }
            setUp(scn.inject(atOnceUsers(userCount)).protocols(httpConf))
        }
    }

    /**
      * http post,检查返回结果中是否存在某个json的key和value是否正确, Gatling的限制了只能检查String类型的value
      * 如果key位置不是最外层的话请遵循这个规范http://goessner.net/articles/JsonPath/
      * 例如: {"person": {"name": "xiaomi", "age": 7}} 那么要判断name是否存在，jsonKey传入 "person.name"
      * @param urls: 被测试接口的url
      * @param userData: 形如"k1=v1&k2=v2" 或 """{ "myContent": "Hi, I am From Xiaomi" }""" 格式的字符串
      * @param paramMap: 参数列表,将被拼接到url里, 例如 Map("vendor" -> "xiaomi", "location" -> "Beijing")
      * @param userHeaders: 放置在http header里, 例如 Map("user" -> "xiaomi-ai")
      * @param userCount: 模拟多少个并发用户；
      * @param repeat：　每个用户重复多少次；
      * @param caseName：　测试用例名，用于在报表里展示;
      */
    def postAndCheckJsonKeyValue(urls:List[String],
                                 userData:String,
                                 jsonKey:String,
                                 jsonValue:String,
                                 paramMap:Map[String,String] = Map(),
                                 userHeaders:Map[String, String] = Map(),
                                 userCount:Int = 1,
                                 repeat:Int = 1,
                                 caseName: String = "test-post") : Unit = {
        val httpConf = http.baseURLs(urls)
        val strKey = "$." +jsonKey
        //judge user data is string or json
        if(userData.contains("{") && userData.contains("}")){
            val scn = scenario("httppost")
                    .repeat(repeat, "n"){
                        exec(
                            http(caseName)
                                    .post("")
                                    .queryParamMap(paramMap)
                                    .headers(userHeaders)
                                    .body(StringBody(userData)).asJSON
                                    .check(status.is(200), bodyString.saveAs("resp"))
                                    .check(jsonPath(strKey).exists,jsonPath(strKey).is(jsonValue))

                        )
                                .exec(session => {
                                    val resp: String = session("resp").as[String]
                                    println("server resp: " + resp)
                                    session
                                })
                    }
            setUp(scn.inject(atOnceUsers(userCount)).protocols(httpConf))
        }
        else {
            val scn = scenario("httppost")
                    .repeat(repeat, "n"){
                        exec(
                            http(caseName)
                                    .post("")
                                    .queryParamMap(paramMap)
                                    .headers(userHeaders)
                                    .body(StringBody(userData)).asFormUrlEncoded
                                    .check(status.is(200), bodyString.saveAs("resp"))
                                    .check(jsonPath(strKey).exists,jsonPath(strKey).is(jsonValue))

                        )
                                .exec(session => {
                                    val resp: String = session("resp").as[String]
                                    println("server resp: " + resp)
                                    session
                                })
                    }
            setUp(scn.inject(atOnceUsers(userCount)).protocols(httpConf))
        }
    }

    /**
      * http post,检查返回结果中是否存在某个json的key和value是否正确, Gatling的限制了只能检查String类型的value
      * 如果key位置不是最外层的话请遵循这个规范http://goessner.net/articles/JsonPath/
      * 例如: {"person": {"name": "xiaomi", "age": 7}} 那么要判断name是否存在，jsonKey传入 "person.name"
      * @param url: 被测试接口的url
      * @param userDataList: 形如"k1=v1&k2=v2" 或 """{ "myContent": "Hi, I am From Xiaomi" }""" 格式的字符串列表,两种格式不能混着用,每次只能一种;
      * @param paramMap: 参数列表,将被拼接到url里, 例如 Map("vendor" -> "xiaomi", "location" -> "Beijing")
      * @param userHeaders: 放置在http header里, 例如 Map("user" -> "xiaomi-ai")
      * @param userCount: 模拟多少个并发用户；
      * @param repeat：　每个用户重复多少次；
      * @param isJson: 数据格式是否是Json,true--是,false-否
      * @param caseName：　测试用例名，用于在报表里展示;
      */
    def postDataList(url:String,
                     userDataList:List[String],
                     paramMap:Map[String,String] = Map(),
                     userHeaders:Map[String, String] = Map(),
                     userCount:Int = 1,
                     repeat:Int = 1,
                     isJson:Boolean=true,
                     caseName: String = "test-post") : Unit = {
        val httpConf = http.baseURL(url)
        //judge user data is string or json
        if(isJson){
            val scn = scenario("httppost")
                    .repeat(repeat, "n"){
                        exec(
                            http(caseName)
                                    .post("")
                                    .queryParamMap(paramMap)
                                    .headers(userHeaders)
                                    .body(StringBody(session => """""" + userDataList(scala.util.Random.nextInt(userDataList.length)) +"""""")).asJSON
                                    .check(status.is(200), bodyString.saveAs("resp"))

                        )
                                .exec(session => {
                                    val resp: String = session("resp").as[String]
                                    println("server resp: " + resp)
                                    session
                                })
                    }
            setUp(scn.inject(atOnceUsers(userCount)).protocols(httpConf))
        }
        else {
            val scn = scenario("httppost")
                    .repeat(repeat, "n"){
                        exec(
                            http(caseName)
                                    .post("")
                                    .queryParamMap(paramMap)
                                    .headers(userHeaders)
                                    .body(StringBody(session => """""" + userDataList(scala.util.Random.nextInt(userDataList.length)) +"""""")).asFormUrlEncoded
                                    .check(status.is(200), bodyString.saveAs("resp"))

                        )
                                .exec(session => {
                                    val resp: String = session("resp").as[String]
                                    println("server resp: " + resp)
                                    session
                                })
                    }
            setUp(scn.inject(atOnceUsers(userCount)).protocols(httpConf))
        }
    }

    /**
      * http post,检查返回结果中是否存在某个json的key和value是否正确, Gatling的限制了只能检查String类型的value
      * 如果key位置不是最外层的话请遵循这个规范http://goessner.net/articles/JsonPath/
      * 例如: {"person": {"name": "xiaomi", "age": 7}} 那么要判断name是否存在，jsonKey传入 "person.name"
      * @param url: 被测试接口的url
      * @param userDataList: 形如"k1=v1&k2=v2" 或 """{ "myContent": "Hi, I am From Xiaomi" }""" 格式的字符串列表,两种格式不能混着用,每次只能一种;
      * @param expect: 需要验证返回的字符串里是否存在的子串
      * @param paramMap: 参数列表,将被拼接到url里, 例如 Map("vendor" -> "xiaomi", "location" -> "Beijing")
      * @param userHeaders: 放置在http header里, 例如 Map("user" -> "xiaomi-ai")
      * @param userCount: 模拟多少个并发用户；
      * @param repeat：　每个用户重复多少次；
      * @param isJson: 数据格式是否是Json,true--是,false-否
      * @param caseName：　测试用例名，用于在报表里展示;
      */
    def postDataListCheckExpect(url:String,
                                userDataList:List[String],
                                expect:String,
                                paramMap:Map[String,String] = Map(),
                                userHeaders:Map[String, String] = Map(),
                                userCount:Int = 1,
                                repeat:Int = 1,
                                isJson:Boolean=true,
                                caseName: String = "test-post") : Unit = {
        val httpConf = http.baseURL(url)
        //judge user data is string or json
        if(isJson){
            val scn = scenario("httppost")
                    .repeat(repeat, "n"){
                        exec(
                            http(caseName)
                                    .post("")
                                    .queryParamMap(paramMap)
                                    .headers(userHeaders)
                                    .body(StringBody(session => """""" + userDataList(scala.util.Random.nextInt(userDataList.length)) +"""""")).asJSON
                                    .check(status.is(200), bodyString.saveAs("resp"))
                                    .check(substring(expect).exists)

                        )
                                .exec(session => {
                                    val resp: String = session("resp").as[String]
                                    println("server resp: " + resp)
                                    session
                                })
                    }
            setUp(scn.inject(atOnceUsers(userCount)).protocols(httpConf))
        }
        else {
            val scn = scenario("httppost")
                    .repeat(repeat, "n"){
                        exec(
                            http(caseName)
                                    .post("")
                                    .queryParamMap(paramMap)
                                    .headers(userHeaders)
                                    .body(StringBody(session => """""" + userDataList(scala.util.Random.nextInt(userDataList.length)) +"""""")).asFormUrlEncoded
                                    .check(status.is(200), bodyString.saveAs("resp"))
                                    .check(substring(expect).exists)

                        )
                                .exec(session => {
                                    val resp: String = session("resp").as[String]
                                    println("server resp: " + resp)
                                    session
                                })
                    }
            setUp(scn.inject(atOnceUsers(userCount)).protocols(httpConf))
        }
    }


    /**
      * http post,检查返回结果中是否存在某个json的key和value是否正确, Gatling的限制了只能检查String类型的value
      * 如果key位置不是最外层的话请遵循这个规范http://goessner.net/articles/JsonPath/
      * 例如: {"person": {"name": "xiaomi", "age": 7}} 那么要判断name是否存在，jsonKey传入 "person.name"
      * @param url: 被测试接口的url
      * @param userDataList: 形如"k1=v1&k2=v2" 或 """{ "myContent": "Hi, I am From Xiaomi" }""" 格式的字符串列表,两种格式不能混着用,每次只能一种;
      * @param jsonKey: 需要验证是否存在的json key
      * @param paramMap: 参数列表,将被拼接到url里, 例如 Map("vendor" -> "xiaomi", "location" -> "Beijing")
      * @param userHeaders: 放置在http header里, 例如 Map("user" -> "xiaomi-ai")
      * @param userCount: 模拟多少个并发用户；
      * @param repeat：　每个用户重复多少次；
      * @param isJson: 数据格式是否是Json,true--是,false-否
      * @param caseName：　测试用例名，用于在报表里展示;
      */
    def postDataListCheckJsonKey(url:String,
                                 userDataList:List[String],
                                 jsonKey:String,
                                 paramMap:Map[String,String] = Map(),
                                 userHeaders:Map[String, String] = Map(),
                                 userCount:Int = 1,
                                 repeat:Int = 1,
                                 isJson:Boolean=true,
                                 caseName: String = "test-post") : Unit = {
        val httpConf = http.baseURL(url)
        val strKey = "$." +jsonKey
        //judge user data is string or json
        if(isJson){
            val scn = scenario("httppost")
                    .repeat(repeat, "n"){
                        exec(
                            http(caseName)
                                    .post("")
                                    .queryParamMap(paramMap)
                                    .headers(userHeaders)
                                    .body(StringBody(session => """""" + userDataList(scala.util.Random.nextInt(userDataList.length)) +"""""")).asJSON
                                    .check(status.is(200), bodyString.saveAs("resp"))
                                    .check(jsonPath(strKey).exists)

                        )
                                .exec(session => {
                                    val resp: String = session("resp").as[String]
                                    println("server resp: " + resp)
                                    session
                                })
                    }
            setUp(scn.inject(atOnceUsers(userCount)).protocols(httpConf))
        }
        else {
            val scn = scenario("httppost")
                    .repeat(repeat, "n"){
                        exec(
                            http(caseName)
                                    .post("")
                                    .queryParamMap(paramMap)
                                    .headers(userHeaders)
                                    .body(StringBody(session => """""" + userDataList(scala.util.Random.nextInt(userDataList.length)) +"""""")).asFormUrlEncoded
                                    .check(status.is(200), bodyString.saveAs("resp"))
                                    .check(jsonPath(strKey).exists)

                        )
                                .exec(session => {
                                    val resp: String = session("resp").as[String]
                                    println("server resp: " + resp)
                                    session
                                })
                    }
            setUp(scn.inject(atOnceUsers(userCount)).protocols(httpConf))
        }
    }

    /**
      * http post,检查返回结果中是否存在某个json的key和value是否正确, Gatling的限制了只能检查String类型的value
      * 如果key位置不是最外层的话请遵循这个规范http://goessner.net/articles/JsonPath/
      * 例如: {"person": {"name": "xiaomi", "age": 7}} 那么要判断name是否存在，jsonKey传入 "person.name"
      * @param url: 被测试接口的url
      * @param userDataList: 形如"k1=v1&k2=v2" 或 """{ "myContent": "Hi, I am From Xiaomi" }""" 格式的字符串列表,两种格式不能混着用,每次只能一种;
      * @param jsonKey: 需要验证是否存在的json key
      * @param jsonValue: 需要验证的json value
      * @param paramMap: 参数列表,将被拼接到url里, 例如 Map("vendor" -> "xiaomi", "location" -> "Beijing")
      * @param userHeaders: 放置在http header里, 例如 Map("user" -> "xiaomi-ai")
      * @param userCount: 模拟多少个并发用户；
      * @param repeat：　每个用户重复多少次；
      * @param isJson: 数据格式是否是Json,true--是,false-否
      * @param caseName：　测试用例名，用于在报表里展示;
      */
    def postDataListCheckJsonKeyValue(url:String,
                                      userDataList:List[String],
                                      jsonKey:String,
                                      jsonValue:String,
                                      paramMap:Map[String,String] = Map(),
                                      userHeaders:Map[String, String] = Map(),
                                      userCount:Int = 1,
                                      repeat:Int = 1,
                                      isJson:Boolean=true,
                                      caseName: String = "test-post") : Unit = {
        val httpConf = http.baseURL(url)
        val strKey = "$." +jsonKey
        //judge user data is string or json
        if(isJson){
            val scn = scenario("httppost")
                    .repeat(repeat, "n"){
                        exec(
                            http(caseName)
                                    .post("")
                                    .queryParamMap(paramMap)
                                    .headers(userHeaders)
                                    .body(StringBody(session => """""" + userDataList(scala.util.Random.nextInt(userDataList.length)) +"""""")).asJSON
                                    .check(status.is(200), bodyString.saveAs("resp"))
                                    .check(jsonPath(strKey).exists, jsonPath(strKey).is(jsonValue))

                        )
                                .exec(session => {
                                    val resp: String = session("resp").as[String]
                                    println("server resp: " + resp)
                                    session
                                })
                    }
            setUp(scn.inject(atOnceUsers(userCount)).protocols(httpConf))
        }
        else {
            val scn = scenario("httppost")
                    .repeat(repeat, "n"){
                        exec(
                            http(caseName)
                                    .post("")
                                    .queryParamMap(paramMap)
                                    .headers(userHeaders)
                                    .body(StringBody(session => """""" + userDataList(scala.util.Random.nextInt(userDataList.length)) +"""""")).asFormUrlEncoded
                                    .check(status.is(200), bodyString.saveAs("resp"))
                                    .check(jsonPath(strKey).exists, jsonPath(strKey).is(jsonValue))

                        )
                                .exec(session => {
                                    val resp: String = session("resp").as[String]
                                    println("server resp: " + resp)
                                    session
                                })
                    }
            setUp(scn.inject(atOnceUsers(userCount)).protocols(httpConf))
        }
    }
}