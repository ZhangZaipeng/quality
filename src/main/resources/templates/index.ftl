<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
  <title>全付通抢单系统</title>
  <script src="/static/jquery-min.js"></script>
  <style type="text/css">
    .LockOff {
      display: none;
      visibility: hidden;
    }
  </style>
  <script type="text/javascript">

    function aa(){
      var token = $("#token1").val();
      $("#start").hide();
      $("#end").show();
      $("#DataToSend").html($("#DataToSend").html() + token+"<br/>"+"扫描开始...");
      $.ajax({
        type: "GET",
        url: "/start.json?type=2&token=" + token,

        dataType: "text",
        success: function (data) {
          $("#DataToSend").html($("#DataToSend").html() + "持续扫描中...");
        },
        error: function(){
          $("#DataToSend").html($("#DataToSend").html() + "登录失败");
        }
      });
    }

    function bb(){
      var token = $("#token1").val();
      $("#start").show();
      $("#end").hide();
      $.ajax({
        type: "GET",
        url: "/start.json?type=1&token=" + token,

        dataType: "text",
        success: function (data) {
          $("#DataToSend").html($("#DataToSend").html() + token+"扫描结束");
        },
        error: function(){
          $("#DataToSend").html($("#DataToSend").html() + "登录失败");
        }
      });
    }
  </script>
</head>

<body style="background-color: #cccccc">
<div id="skm_LockPane" class="LockOff"></div>
<form id="form1" runat="server">
  <h1>全付通抢单系统</h1>
  <br />
  <div style="float: left"><span style="font-family: 新宋体">X-Auth-Token: </span>&nbsp;&nbsp;<input type="text" id='token1' /></div>&nbsp;&nbsp;&nbsp;&nbsp;

  <button id='start' value="2" onclick="aa()" style="border-radius: 25px;background-color: white">开始</button>
  <button id='end' value="1" onclick="bb()" style="border-radius: 25px;background-color: white">暂停</button>

  <br />
  <br />
  <div style="border: 1px solid blanchedalmond;width: 500px;height: 500px;background-color: blanchedalmond">
    <extarea  id="DataToSend" cols="100" rows="10" readonly="true"/></textarea>
  </div>
  <br />
</form>
</body>
</html>
