# flow-support

Include:
* MarkerSocketAppender
* StreamPerEventSocketAppender

## MarkerSocketAppender
Is for [markering](http://logback.qos.ch/manual/filters.html) outcoming logs
```
appender("SOCKET", MarkerSocketAppender) {
    remoteHost = "localhost"
    port = 4561
    marker = "test"
}
```
