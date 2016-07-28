<?php 
header("Content-Type:text/html;charset=utf-8");

$error = ""; 
$baseinfo = ""; 
$time = ""; 
$totalmemory = ""; 
$availmemory = ""; 
$cpuinfo = ""; 
$otherinfo = ""; 
$description = ""; 
$note = ""; 

if($_SERVER['REQUEST_METHOD']=="POST"){

	$str = file_get_contents("php://input"); 
	$arr = array(); 
	parse_str($str, $arr); // 把str的字符串数据拆分到arr数组
	
	$error = isset($arr['error']) ? $arr['error'] : "";
	$baseinfo = isset($arr['baseinfo']) ? $arr['baseinfo'] : "";
	$time = isset($arr['time']) ? $arr['time'] : "";
	$totalmemory = isset($arr['totalmemory']) ? $arr['totalmemory'] : "";
	$availmemory = isset($arr['availmemory']) ? $arr['availmemory'] : "";
	$cpuinfo = isset($arr['cpuinfo']) ? $arr['cpuinfo'] : "";
	$otherinfo = isset($arr['otherinfo']) ? $arr['otherinfo'] : "";
	$description = isset($arr['description']) ? $arr['description'] : "";
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
		INSERT INTO is_buglogs (
			error,
			baseinfo,
			time,
			totalmemory,
			availmemory,
			cpuinfo,
			otherinfo,
			description,
			note
		) 
		VALUES
			('{$error}', '{$baseinfo}', '{$time}', '{$totalmemory}', '{$availmemory}', '{$cpuinfo}', '{$otherinfo}', '{$description}', '{$note}') ;
	");
	$ok = mysql_query($sql, $connect);
	//echo $sql."<br/>刚刚插入的id: " . mysql_insert_id(). ",成功：".$ok; // 1为成功
	
	echo mysql_insert_id();

} else {
	echo "error"; 
}

mysql_close($connect);
?>