<?php 
header("Content-Type:text/html;charset=utf-8");

$company_id = "0";

if ( is_array ($_GET) && count ($_GET) > 0 ) { //判断是否有Get参数 
	$company_id = isset($_GET['company_id']) ? $_GET['company_id'] : '0';
}

if($company_id == "" || $company_id == "0"){
	echo "0";
	
	return;
}

$connect = mysql_connect("localhost", "数据库", "密码");
if($connect) {
	mysql_query("set character set 'utf8'");//读库 
	mysql_query("set names 'utf8'");//写库 
	mysql_select_db("库", $connect);

	//读取表中纪录条数  
	$sql = sprintf( "
		SELECT 
			COUNT(company_id) AS count
		FROM
			is_spitslot
		WHERE company_id = {$company_id}
	");

	$result = mysql_query($sql, $connect);
	if($result){
		$row = mysql_fetch_array($result, MYSQL_ASSOC);  
		
		if (mysql_num_rows($result)) {
			echo $row['count'];
		}
	}
	else {
		echo 0;
	}
	
	if($result)
		mysql_free_result($result);
} else {
	echo "error"; 
}

mysql_close($connect);
?>