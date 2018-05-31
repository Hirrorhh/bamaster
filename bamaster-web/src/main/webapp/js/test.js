$(function() {
    // 自适应窗口
    test01();

});

function test01(){
    //var url ="http://gs.amac.org.cn/amac-infodisc/api/pof/fund?rand=0.2456458179048766&page=0&size=20";
/*    var url = "updatePswd.master";
    var data = {pswd: "11111", newPswd: "2222"};

    $.post(url, data, function(data) {

    });*/
  var url = "http://gs.amac.org.cn/amac-infodisc/api/pof/manager?rand="+Math.random()+"page=0&size=20";
    var data = {rand: Math.random(), page: "0", size:"20"};

 /*   $.post(url, data, function(data) {
     alert(data)
    });*/


    $.ajax({
        type: "post",
        contentType: "application/json",
        url: url,
        dataType: "json",
        data: '{}',
        success: function(resp) {
           console.log(resp);
        },
        "error" :function() {
            alert("查询失败，请与系统管理员联系！");
        }
    });

}