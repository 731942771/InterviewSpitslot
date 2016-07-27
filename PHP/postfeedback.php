<?php 
header("Content-Type:text/html;charset=utf-8");

$feed = "";
$postdate = "";
$user_id = "";
$description = "";
$note = "";

if($_SERVER['REQUEST_METHOD']=="POST"){

	$str = file_get_contents("php://input"); 
	$arr = array(); 
	parse_str($str, $arr); // 把str的字符串数据拆分到arr数组
	
	$feed = isset($arr['feed']) ? $arr['feed'] : ""; // post过来的数据key
	$postdate = isset($arr['postdate']) ? $arr['postdate'] : "";
	$user_id = isset($arr['user_id']) ? $arr['user_id'] : "";
	$description = isset($arr['description']) ? $arr['description'] : "";
	$note = isset($arr['note']) ? $arr['note'] : "";
	
//	$feed = $arr['feed'] ; 
//	$postdate = $arr['postdate'] ; 
//	$user_id = $arr['user_id'] ; 
//	$description = $arr['description'] ; 
//	$note = $arr['note'] ; 
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
		INSERT INTO is_feedback (
			feed,
			postdate,
			user_id,
			description,
			note
		) 
		VALUES
			('{$feed}', '{$postdate}', '{$user_id}', '{$description}', '{$note}') ;
	");
	$ok = mysql_query($sql, $connect);
	//echo $sql."<br/>刚刚插入的id: " . mysql_insert_id(). ",成功：".$ok; // 1为成功
	
	echo mysql_insert_id(); // 返回0表示没查到

} else {
	echo "error"; 
}

mysql_close($connect);
?>