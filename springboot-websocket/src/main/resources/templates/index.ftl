<!DOCTYPE html>
<html>
<head>
    <title>websocket 发送信息</title>
</head>
<body>
<div>
    <input type="text" name="message" id="message">
    <button id="sendBtn">发送</button>
</div>
<div style="width:100px;height: 500px;" id="content">
</div>
<script src="https://cdn.bootcdn.net/ajax/libs/jquery/3.6.0/jquery.js"></script>
<script type="text/javascript">
    var ws = new WebSocket("ws://127.0.0.1:8080/message");
    ws.onopen = function(evt) {
        console.log("Connection open ...");
    };

    ws.onmessage = function(evt) {
        console.log( "Received Message: " + evt.data);
        var p = $("<p>"+evt.data+"</p>")
        $("#content").prepend(p);
        $("#message").val("");
    };

    ws.onclose = function(evt) {
        console.log("Connection closed.");
    };

    $("#sendBtn").click(function(){
        var aa = $("#message").val();
        ws.send(aa);
    })

</script>
</body>
</html>