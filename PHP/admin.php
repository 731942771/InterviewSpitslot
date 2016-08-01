<?php

$connect = mysql_connect("localhost", "数据库", "密码");
if($connect) {
	mysql_query("set character set 'utf8'");//读库 
	mysql_query("set names 'utf8'");//写库 
	mysql_select_db("库", $connect);

} else {
	die('连接失败：' . mysql_error());
}

function getUserCount(){
	global $connect;
	$sql = sprintf("SELECT COUNT(id) AS count FROM is_users;"); // 用户数量
	$result = mysql_query($sql, $connect);
	if($result){
		$row = mysql_fetch_array($result, MYSQL_ASSOC);  
		if (mysql_num_rows($result)) {
			echo $row['count'];
		}

		mysql_free_result($result);
	}
}

function getArticleCount(){
	global $connect;
	$sql = sprintf("SELECT COUNT(id) AS count FROM is_article;"); // 骚文数量
	$result = mysql_query($sql, $connect);
	if($result){
		$row = mysql_fetch_array($result, MYSQL_ASSOC);  
		if (mysql_num_rows($result)) {
			echo $row['count'];
		}

		mysql_free_result($result);
	}
}

function getSpitslotCount(){
	global $connect;
	$sql = sprintf("SELECT COUNT(id) as count FROM is_spitslot;"); // 口水数量
	$result = mysql_query($sql, $connect);
	if($result){
		$row = mysql_fetch_array($result, MYSQL_ASSOC);  
		if (mysql_num_rows($result)) {
			echo $row['count'];
		}

		mysql_free_result($result);
	}
}

//mysql_close($connect);
?>

<html>
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>面试吐槽后台</title>
</head>
<body style="height: 100%; margin: 0px; padding: 0px;">
基本信息
<p>
	<li>当前用户数量：<?php getUserCount(); ?>
	<li>当前骚文数量：<?php getArticleCount(); ?>
	<li>当前口水数量：<?php getSpitslotCount(); ?>
</p>
<hr/>
<p>
	<li><a href = "content.php?func=showUsers" target = "contentframe">查看全部昵称</a></li>
	<li><a href = "content.php?func=showArticles" target = "contentframe">查看全部骚文</a></li>
	<li><a href = "content.php?func=showSpitslots" target = "contentframe">查看全部口水</a></li>
</p>
<p>
	<li><a href = "content.php?func=showBugs" target = "contentframe">查看bug</a></li>
	<li><a href = "content.php?func=showFeedbacks" target = "contentframe">查看反馈</a></li>
</p>
</body>
</html>