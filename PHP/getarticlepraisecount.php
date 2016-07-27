<?php 
header("Content-Type:text/html;charset=utf-8");

$id = "";

if ( is_array ($_GET) && count ($_GET) > 0 ) { //判断是否有Get参数 
	$id = isset($_GET['id']) ? $_GET['id'] : '0';
}

$connect = mysql_connect("localhost", "数据库", "密码");
if($connect) {

	mysql_query("set character set 'utf8'");//读库 
	mysql_query("set names 'utf8'");//写库 

	mysql_select_db("库", $connect);  
  
	$sql = sprintf("
		SELECT 
			praise_count 
		FROM
			is_article 
		WHERE id = {$id}
	");
	$result = mysql_query($sql, $connect);  
	$row = mysql_fetch_array($result, MYSQL_ASSOC);  
	
	if (mysql_num_rows($result)) {
		echo $row['praise_count'];
	}
	else {
		echo 0;
	}

} else {
	echo "error"; 
}

mysql_close($connect);
?>