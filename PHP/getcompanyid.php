<?php 
header("Content-Type:text/html;charset=utf-8");

$name = "";
$address = "";
$description = "";
$note = "";

if ( is_array ($_GET) && count ($_GET) > 0 ) { //判断是否有Get参数 
	$name = isset($_GET['name']) ? $_GET['name'] : '';
	$address = isset($_GET['address']) ? $_GET['address'] : '';
	$description = isset($_GET['description']) ? $_GET['description'] : '';
	$note = isset($_GET['note']) ? $_GET['note'] : '';
}

$connect = mysql_connect("localhost", "数据库", "密码");
if($connect) {
	mysql_query("set character set 'utf8'");//读库 
	mysql_query("set names 'utf8'");//写库 
	mysql_select_db("库", $connect);

	//读取表中纪录条数  
	$sql = sprintf("SELECT id FROM is_company WHERE name = '{$name}';");
	$result = mysql_query($sql, $connect); 
	if($result){
		$row = mysql_fetch_array($result, MYSQL_ASSOC);  
		
		if (mysql_num_rows($result)) {
			echo $row['id'];
		} else {
			$sql = sprintf("INSERT INTO is_company (name, address, description, note) VALUES ('{$name}', '{$address}', '{$description}', '{$note}');");
			$ok = mysql_query($sql, $connect);
			//echo "刚刚插入的条目id: " . mysql_insert_id(). ",成功：".$ok; // 1为成功
			echo mysql_insert_id();
		} 
	}
	else {
		$sql = sprintf("INSERT INTO is_company (name, address, description, note) VALUES ('{$name}', '{$address}', '{$description}', '{$note}');");
		$ok = mysql_query($sql, $connect);
		//echo "刚刚插入的条目id: " . mysql_insert_id(). ",成功：".$ok; // 1为成功
		echo mysql_insert_id();
	} 
	
	mysql_free_result($result);

} else {
	echo "error"; 
}

mysql_close($connect);
?>