<?php 
header("Content-Type:text/html;charset=utf-8");

$user_id = "";
$title = "";
$article = "";
$date_add = "";
$description = "";
$note = "";

if($_SERVER['REQUEST_METHOD']=="POST"){

	$str = file_get_contents("php://input"); 
	$arr = array(); 
	parse_str($str, $arr); // 把str的字符串数据拆分到arr数组
	
	$user_id = isset($arr['user_id']) ? $arr['user_id'] : ""; 
	$title = isset($arr['title']) ? $arr['title'] : ""; 
	$article = isset($arr['article']) ? $arr['article'] : ""; 
	$date_add = isset($arr['date_add']) ? $arr['date_add'] : "2016-07-27";
	$description = isset($arr['description']) ? $arr['description'] : "_";
	$note = isset($arr['note']) ? $arr['note'] : "_";

} else {
	echo "0";
	
	return;
}

$connect = mysql_connect("localhost", "数据库", "密码");
if($connect) {
	mysql_query("set character set 'utf8'");//读库 
	mysql_query("set names 'utf8'");//写库 
	mysql_select_db("库", $connect);

	$sql = sprintf("
		INSERT INTO 
			is_article 
			(user_id, title, article, date_add, praise_count, recommend, enable, description, note) 
		VALUES
			('{$user_id}', '{$title}', '{$article}', '{$date_add}', 0, 0, 0, '{$description}', '{$note}');
	");
	$ok = mysql_query($sql, $connect);
	//$ii = mysql_insert_id();
	//echo "正文长度：". strlen($article)."，sql语句：".$sql."，返回id：" . $ii . "，执行结果：".$ok; // 1为成功
	
	echo mysql_insert_id(); // 返回0表示没查到

} else {
	echo "error"; 
}

mysql_close($connect);
?>