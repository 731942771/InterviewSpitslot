<?php 
header("Content-Type:text/html;charset=utf-8");
 
$company_id = "";

if ( is_array ($_GET) && count ($_GET) > 0 ) { //判断是否有Get参数 
	$company_id = isset($_GET['company_id']) ? $_GET['company_id'] : '0';
} else {
	echo "";
	
	return;
}

$connect = mysql_connect("localhost", "数据库", "密码");
if($connect) {

	mysql_query("set character set 'utf8'");//读库 
	mysql_query("set names 'utf8'");//写库 

	mysql_select_db("库", $connect);  
  
	//读取表中纪录条数  
	$sql = sprintf("
		SELECT 
			id,
			name,
			company_id,
			description,
			note 
		FROM
			is_station
		WHERE 
			company_id = {$company_id};
	");
	$result = mysql_query($sql, $connect);  
	if ($result) {
		$json = "[";
		//表中的内容  
		while ($row = mysql_fetch_array($result, MYSQL_ASSOC)) { // 遍历查询到的每一行记录。与$row=mysql_fetch_assoc($result)等价 
			$count = count($row); // 每条记录的字段数量，查询了10个字段，这里得到10
			$json = $json.json_encode($row).","; // array转json
		}
		$json = substr($json, 0, strlen($json)-1); 
		$json = $json."]";
		echo $json;
	}
	
	mysql_free_result($result);
} else {
	echo "error"; 
}

mysql_close($connect);
?>