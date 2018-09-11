
/**
  * 测试代码直接从HttpStress派生下来
  * 用这条命令执行: > sbt "gatling:testOnly SimpleStress"
  */
class SimpleStress extends HttpStress {
    //成语接龙接口(请不要随便打开来运行)
    //val url = "http://nlp-stress.ai.srv/2.0/answer?device_id=2967021242480432986&timestamp=1513321827349&queries=%5B%7B%22query%22%3A%22%E6%89%93%E5%BC%80%E6%88%90%E8%AF%AD%E6%8E%A5%E9%BE%99%22%2C%22confidence%22%3A0%2C%22query_vendor%22%3A-1%7D%5D&is_internal=true&version=2.2&token=5631740674018&session=%7B%22id%22%3A%22%22%2C%22suggestion%22%3A%7B%7D%7D&user_info=%7B%22id%22%3A%22123123%22%2C%22id_type%22%3A%22xiaomi_id%22%2C%22gender%22%3A%22unknown%22%7D&app_id=2882303761517406018&device=%7B%22ip%22%3A%22127.0.0.1%22%2C%22model%22%3A%22unknown%22%2C%22network%22%3A%22unknown%22%7D"
    //getAndCheck(List(url), "to_speak", userCount = 50, repeat = 1000)

    //参数map可以为空，也可以不传
    val queryParams = Map("begin" -> "201712150910")
    //header map可以为空，也可以不传
    val sentHeaders = Map("company" -> "Xiaomi")

    //[Http Get使用场景]
    //1.仅仅是发送请求并检查: 1)status=200
    //get(List("http://182.92.232.129/test/stressget"), queryParams, sentHeaders)

    //2.发送请求检查: 1)status=200; 2)并判断返回的字符串里是否包含'successful'
    //getAndCheck(List("http://182.92.232.129/test/stressget"), "successful", queryParams, sentHeaders)

    //3.发送请求检查: 1)status=200; 2)并判断返回的json里是否包含'reason'这个key
    //getAndCheckJsonKey(List("http://182.92.232.129/test/stressget"), "reason", queryParams, sentHeaders)

    //4.发送请求检查: 1)status=200; 2)并判断返回的json里是否包含'reason'这个key; 3)reason的值是否等于 "you are successful"
    //getAndCheckJsonKeyValue(List("http://182.92.232.129/test/stressget"), "reason", "you are successful", queryParams, sentHeaders)

    //[Http Post使用场景]
    //post data支持以下两种数据类型
    val postData = "name=AiCloud&age=7"
    //val postData = """{ "myContent": "Hi, I am From Xiaomi" }"""


    //1.仅仅是发送请求并检查: 1)status=200
    //post(List("http://182.92.232.129/test/stresspost"), postData, queryParams, sentHeaders)

    //2.发送请求检查: 1)status=200; 2)并判断返回的字符串里是否包含'successful'
    //postAndCheck(List("http://182.92.232.129/test/stresspost"), postData, "successful", queryParams, sentHeaders)

    //3.发送请求检查: 1)status=200; 2)并判断返回的json里是否包含'reason'这个key
    //postAndCheckJsonKey(List("http://182.92.232.129/test/stresspost"), postData, "reason", queryParams, sentHeaders)

    //4.发送请求检查: 1)status=200; 2)并判断返回的json里是否包含'reason'这个key; 3)reason的值是否等于 "you are successful"
    //postAndCheckJsonKeyValue(List("http://182.92.232.129/test/stresspost"), postData, "reason", "you are successful", queryParams, sentHeaders)



    //[Http Post使用场景]
    //需要发给服务器的数据列表，每次请求会随机挑选
    val postDataList = List("""{ "myContent": "Hi, I am From Xiaomi" }""", """{ "MyName": "Hi, I am From SiChuan" }""")
    //1.仅仅是发送请求并检查: 1)status=200
    //postDataList("http://182.92.232.129/test/stresspost", postDataList, queryParams, sentHeaders, repeat = 10)

    //2.发送请求检查: 1)status=200; 2)并判断返回的字符串里是否包含'successful'
    //postDataListCheckExpect("http://182.92.232.129/test/stresspost", postDataList, "successful", queryParams, sentHeaders, repeat = 10)

    //3.发送请求检查: 1)status=200; 2)并判断返回的json里是否包含'reason'这个key
    //postDataListCheckJsonKey("http://182.92.232.129/test/stresspost", postDataList, "reason", queryParams, sentHeaders, repeat = 10)

    //4.发送请求检查: 1)status=200; 2)并判断返回的json里是否包含'reason'这个key; 3)reason的值是否等于 "you are successful"
    //postDataListCheckJsonKeyValue("http://182.92.232.129/test/stresspost", postDataList, "reason", "you are successful", queryParams, sentHeaders, repeat = 10)
}


