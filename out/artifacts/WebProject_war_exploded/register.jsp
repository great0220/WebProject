<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head></head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>会员注册</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/bootstrap.min.css" type="text/css" />
<script src="${pageContext.request.contextPath }/js/jquery-1.11.3.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath }/js/bootstrap.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.validate.min.js"></script>
<!-- 引入自定义css文件 style.css -->
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/style.css" type="text/css" />
<script type="text/javascript">
	function changeImg(obj) {
		obj.src="${pageContext.request.contextPath}/checkImgServlet?random=" + Math.random();
	}
	$(function(){
		$("#register").validate({
			rules:{
				"username":{
					"required":true
				},
				"password":{
					"required":true,
					"rangelength":[6,12]
				},
				"repassword":{
					"required":true,
					"rangelength":[6,12],
					"equalTo":"#password"
				},
				"email":{
					"required":true,
					"email":true
				},
				"sex":{
					"required":true
				},
				"birthday":{
					"required":true
				}

			},
			messages:{
				"username":{
					"required":"用户名不能为空"
				},
				"password":{
					"required":"密码不能为空",
					"rangelength":"密码必须是6-12位"
				},
				"repassword":{
					"required":"密码不能为空",
					"rangelength":"密码必须是6-12位",
					"equalTo":"两次输入的密码不一致"
				},
				"email":{
					"required":"邮箱不能为空",
					"email":"邮箱格式不正确"
				},
				"sex":{
					"required":"性别不能为空"
				},
				"birthday":{
					"required":"生日不能为空"
				}
			}

		});
	});

</script>
<style>
	body {
		margin-top: 20px;
		margin: 0 auto;
	}

	.carousel-inner .item img {
		width: 100%;
		height: 300px;
	}

	font {
		color: #3164af;
		font-size: 18px;
		font-weight: normal;
		padding: 0 10px;
	}
	.error{
		color:red
	}
</style>
</head>
<body>

<!-- 引入header.jsp -->
<jsp:include page="/header.jsp"></jsp:include>

<div class="container"
	 style="width: 100%; background: url('image/regist_bg.jpg');">
	<div class="row">
		<div class="col-md-2"></div>
		<div class="col-md-8"
			 style="background: #fff; padding: 40px 80px; margin: 30px; border: 7px solid #ccc;">
			<font>会员注册</font>USER REGISTER
			<form id="register" class="form-horizontal" style="margin-top: 5px;" method="post" action="${pageContext.request.contextPath }/userServlet?method=register">
				<div class="form-group">
					<label for="username" class="col-sm-2 control-label">用户名</label>
					<div class="col-sm-6">
						<input type="text" class="form-control" id="username" name="username"
							   placeholder="请输入用户名">
					</div>
				</div>
				<div class="form-group">
					<label for="password" class="col-sm-2 control-label">密码</label>
					<div class="col-sm-6">
						<input type="password" class="form-control" id="password" name="password"
							   placeholder="请输入密码">
					</div>
				</div>
				<div class="form-group">
					<label for="repassword" class="col-sm-2 control-label">确认密码</label>
					<div class="col-sm-6">
						<input type="password" class="form-control" id="repassword" name="repassword"
							   placeholder="请输入确认密码">
					</div>
				</div>
				<div class="form-group">
					<label for="email" class="col-sm-2 control-label">Email</label>
					<div class="col-sm-6">
						<input type="email" class="form-control" id="email" name="email"
							   placeholder="Email">
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">姓名</label>
					<div class="col-sm-6">
						<input type="text" class="form-control" id="name" name="name"
							   placeholder="请输入姓名">
					</div>
				</div>
				<div class="form-group opt">
					<label for="inlineRadio1" class="col-sm-2 control-label">性别</label>
					<div class="col-sm-6">
						<label class="radio-inline"> <input type="radio"
															name="sex" id="inlineRadio1" value="1">
							男
						</label> <label class="radio-inline"> <input type="radio"
																	 name="sex" id="sex" value="0">
						女
					</label>
						<label for="sex" generated="true" class="error"></label>
					</div>
				</div>
				<div class="form-group">
					<label for="date" class="col-sm-2 control-label">出生日期</label>
					<div class="col-sm-6">
						<input type="date" id="date" class="form-control" name="birthday">
					</div>
				</div>

				<div class="form-group">
					<label for="date" class="col-sm-2 control-label">验证码</label>
					<div class="col-sm-3">
						<input type="text" class="form-control" name="checkCode">
						<span style="color: #b31d04">${registerInfo}</span>
					</div>
					<div class="col-sm-2">
						<img id="checkImg" onclick="changeImg(this)" src="${pageContext.request.contextPath}/checkImgServlet"/>
					</div>

				</div>

				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<input type="submit" width="100" value="注册" name="submit"
							   style="background: url('./images/register.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0); height: 35px; width: 100px; color: white;">
					</div>
				</div>
			</form>
		</div>

		<div class="col-md-2"></div>

	</div>
</div>

<!-- 引入footer.jsp -->
<jsp:include page="/footer.jsp"></jsp:include>

</body>
</html>




