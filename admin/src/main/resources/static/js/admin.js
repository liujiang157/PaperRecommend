$(function () {
    //处理管理员登录
    $("#adminlogin-submit").submit(function () {
        var admin = $("#name").val();
        var password = $("#adminpsw").val();
        var data = {
            "name": admin,
            "password": password,
        };
        url = "adminlogin.do";
        $.ajax({
            type: "POST",
            url: url,
            data: data,
            success: function (data) {
                var res = JSON.parse(data);
                if (res.status == 200) {
                    window.location.href = "/admin"
                } else {
                    alert("管理员密码错误")
                }
            }
        });
        return false;

    });
    //添加用户
    $("#adduser").submit(function () {
        var firstName = $("#firstName").val();
        var lastName = $("#lastName").val();
        var email = $("#email").val();
        var pwd = $("#pwd").val();
        var confirmpwd = $("#confirmpwd").val();
        if (pwd != confirmpwd) {
            alert('两次密码不一致');
            return false;
        }
        var data = {
            "firstName": firstName,
            "lastName": lastName,
            "password": pwd,
            "email": email
        };
        url = "adduser.do";
        $.ajax({
            type: "POST",
            url: url,
            data: data,
            success: function (data) {
                var res = JSON.parse(data);
                if (res.status == 200) {
                    alert("添加成功");
                    window.location.href = "/userlist"
                } else {
                    alert("添加失败")
                }
            }
        });
        return false;

    });

    //修改用户
    $("#updateuser").submit(function () {
        var firstName = $("#firstName").val();
        var lastName = $("#lastName").val();
        var email = $("#email").val();
        var pwd = $("#pwd").val();
        var id = $("#id").val();
        var data = {
            "userId": id,
            "firstName": firstName,
            "lastName": lastName,
            "password": pwd,
            "email": email
        };
        url = "updateuser.do";
        $.ajax({
            type: "POST",
            url: url,
            data: data,
            success: function (data) {
                var res = JSON.parse(data);
                if (res.status == 200) {
                    alert("修改成功");
                    window.location.href = "/userlist"
                } else {
                    alert("修改失败")
                }
            }
        });
        return false;

    });

});
