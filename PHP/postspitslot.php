<?php 
header("Content-Type:text/html;charset=utf-8");

$company_id = ""; 
$station_id = ""; 
$user_id = ""; 
$date_view = ""; 
$description = ""; 
$praise_count = ""; 
$record_time = ""; 
$note = "";

if($_SERVER['REQUEST_METHOD']=="POST"){

	$str = file_get_contents("php://input"); 
	$arr = array(); 
	parse_str($str, $arr); // 把str的字符串数据拆分到arr数组
	
	$company_id = isset($arr['company_id']) ? $arr['company_id'] : ""; // post过来的数据key
	$station_id = isset($arr['station_id']) ? $arr['station_id'] : "";
	$user_id = isset($arr['user_id']) ? $arr['user_id'] : "";
	$date_view = isset($arr['date_view']) ? $arr['date_view'] : "";
	$description = isset($arr['description']) ? $arr['description'] : "";
	$praise_count = isset($arr['praise_count']) ? $arr['praise_count'] : "";
	$record_time = isset($arr['record_time']) ? $arr['record_time'] : "";
	$note = isset($arr['note']) ? $arr['note'] : "";

} else {
	echo 0;
	return;
}

$connect = mysql_connect("localhost", "数据库", "密码");
if($connect) {

	mysql_query("set character set 'utf8'");//读库 
	mysql_query("set names 'utf8'");//写库 

	mysql_select_db("库", $connect);  
  
	$sql = sprintf("
		INSERT INTO 
			is_spitslot 
			(company_id, station_id, user_id, date_view, description, praise_count, record_time, note) 
		VALUES 
			('{$company_id}', '{$station_id}', '{$user_id}','{$date_view}', '{$description}', '{$praise_count}', '{$record_time}', '{$note}');
	");
	$ok = mysql_query($sql, $connect);
	//echo $sql."<br/>刚刚插入的id: " . mysql_insert_id(). ",成功：".$ok; // 1为成功
	
	echo mysql_insert_id();

} else {
	echo "error"; 
}

mysql_close($connect);
?>