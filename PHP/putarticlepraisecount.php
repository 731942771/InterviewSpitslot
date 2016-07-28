<?php 
header("Content-Type:text/html;charset=utf-8");

$id = ""; 
$count = ""; 

if ( is_array ($_GET) && count ($_GET) > 0 ) { //判断是否有Get参数 
	$id = isset($_GET['id']) ? $_GET['id'] : '0';
	$count = isset($_GET['count']) ? $_GET['count'] : '0';
}

$connect = mysql_connect("localhost", "数据库", "密码");
if($connect) {
	mysql_query("set character set 'utf8'");//读库 
	mysql_query("set names 'utf8'");//写库 
	mysql_select_db("库", $connect);

	$sql = sprintf("
		UPDATE is_article
		SET praise_count = {$count}
		WHERE id = {$id}
	");
	$ok = mysql_query($sql, $connect);
	//echo $sql."<br/>刚刚插入的id: " . mysql_insert_id(). ",成功：".$ok; // 1为成功
	
	echo $ok;

} else {
	echo "error"; 
}

mysql_close($connect);
?>