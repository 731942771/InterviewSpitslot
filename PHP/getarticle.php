<?php 
header("Content-Type:text/html;charset=utf-8");

$recommend=0;

if ( is_array ($_GET) && count ($_GET) > 0 ) { //判断是否有Get参数 
	$recommend = isset($_GET['recommend']) ? $_GET['recommend'] : '';
}

$connect = mysql_connect("localhost", "数据库", "密码");
if($connect) {
	mysql_query("set character set 'utf8'");//读库 
	mysql_query("set names 'utf8'");//写库 
	mysql_select_db("库", $connect);

	//读取表中纪录条数  
	$sql = sprintf("
		SELECT 
			a.id,
			a.user_id,
			u.name AS user_name,
			a.title,
			a.article,
			a.date_add,
			a.praise_count,
			a.description,
			a.note 
		FROM
			is_article a 
		LEFT JOIN is_users u 
			ON a.user_id = u.id 
		WHERE a.recommend = {$recommend}
			AND a.enable = 1 
		ORDER BY a.date_add DESC 
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
		if($json <> ''){
			$json = $json."]";
		}
		echo $json;
	}
	
	mysql_free_result($result);
} else {
	echo "error"; 
}

mysql_close($connect);
?>