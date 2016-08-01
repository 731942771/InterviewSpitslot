<?php

$connect = mysql_connect("localhost", "数据库", "密码");
if($connect) {
	mysql_query("set character set 'utf8'");//读库 
	mysql_query("set names 'utf8'");//写库 
	mysql_select_db("库", $connect);

} else {
	die('连接失败：' . mysql_error());
}

if ( is_array ($_GET) && count ($_GET) > 0 ) { //判断是否有Get参数 
	$func = isset($_GET['func']) ? $_GET['func'] : '';
	
	if($func == "showUsers"){
		showUsers();
	} else if($func == "showArticles"){
		showArticles();
	} else if($func == "showSpitslots"){
		showSpitslots();
	} else if($func == "showBugs"){
		showBugs();
	} else if($func == "showFeedbacks"){
		showFeedbacks();
	}
}

///////////////////////////////////////////////////////////////////////////////////////////

function showUsers(){
	global $connect;
	
	$sql = sprintf("
		SELECT 
			id,
			name,
			pswd,
			age,
			sex,
			alone,
			address,
			phone,
			email,
			qq,
			weixin,
			momo,
			weibo,
			blog 
		FROM
			is_users
	");
	$result = mysql_query($sql, $connect);  
	if ($result) {
		echo "<table border='1' width='100%' style='table-layout:fixed;word-break:break-all; word-wrap:break-word;'>";
		echo "<tr>";
		echo "
		<td width='3%'>ID</td>
		<td width='10%'>昵称</td>
		<td width='10%'>密码</td>
		<td width='3%'>年龄</td>
		<td width='3%'>性别</td>
		<td width='3%'>单身</td>
		<td style='word-break:break-all; word-wrap:break-word;'>地址</td>
		<td width='5%'>电话</td>
		<td width='5%'>电邮</td>
		<td width='5%'>QQ</td>
		<td width='5%'>微信</td>
		<td width='5%'>陌陌</td>
		<td width='10%'>微博</td>
		<td width='10%'>博客</td>
		<td width='3%'>操作</td>
		";
		echo "</tr>";
		
		while ($row = mysql_fetch_array($result, MYSQL_ASSOC)) { // 遍历查询到的每一行记录。与$row=mysql_fetch_assoc($result)等价 
			//$count = count($row); // 每条记录的字段数量，查询了10个字段，这里得到10
			//$json = $json.json_encode($row).","; // array转json
			echo "<tr>";
			foreach ($row as $line) { 
				echo "<td>".$line."</td>"; 
			}
			echo "<td><button>删除</button></td>";
			echo "</tr>";
		}
		echo "</table>";
	
		mysql_free_result($result);
	}
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

function showArticles(){
	global $connect;
	
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
		ORDER BY a.date_add DESC 
	");
	$result = mysql_query($sql, $connect);  
	if ($result) {
		echo "<table border='1' width='100%' style='table-layout:fixed;word-break:break-all; word-wrap:break-word;'>";
		echo "<tr>";
		echo "
		<td width='3%'>骚文<br/>id</td>
		<td width='3%'>昵称<br/>id</td>
		<td width='5%'>昵称</td>
		<td width='10%'>标题</td>
		<td style='word-break:break-all; word-wrap:break-word;'>正文</td>
		<td width='6.5%'>发布日期</td>
		<td width='3%'>like<br/>数量</td>
		<td width='6%'>描述</td>
		<td width='6%'>备注</td>
		<td width='3%'>推荐</td>
		<td width='3%'>可看</td>
		<td width='3%'>操作</td>
		";
		echo "</tr>";
		
		while ($row = mysql_fetch_array($result, MYSQL_ASSOC)) { // 遍历查询到的每一行记录。与$row=mysql_fetch_assoc($result)等价 
			//$count = count($row); // 每条记录的字段数量，查询了10个字段，这里得到10
			//$json = $json.json_encode($row).","; // array转json
			echo "<tr>";
			foreach ($row as $line) { 
				echo "<td>".$line."</td>"; 
			} 
			echo "<td><input type='checkbox'>推</input></td>"; 
			echo "<td><input type='checkbox'>可</input></td>"; 
			echo "<td><button>删除</button></td>";
			echo "</tr>";
		}
		echo "</table>";
	
		mysql_free_result($result);
	}
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

function showSpitslots(){
	global $connect;
	
	$sql = sprintf("
		SELECT 
			s.id,
			s.company_id,
			c.name AS company_name,
			c.address,
			s.station_id,
			t.name AS station_name,
			s.user_id,
			u.name AS user_name,
			s.date_view,
			s.description,
			s.praise_count,
			s.record_time,
			s.note 
		FROM
			is_spitslot s 
		LEFT JOIN is_company c 
			ON s.company_id = c.id 
		LEFT JOIN is_station t 
			ON s.station_id = t.id
		LEFT JOIN is_users u 
			ON s.user_id = u.id 
		ORDER BY 
			s.date_view DESC
		LIMIT 50
		");
	$result = mysql_query($sql, $connect);  
	if ($result) {
		echo "<table border='1' width='100%' style='table-layout:fixed;word-break:break-all; word-wrap:break-word;'>";
		echo "<tr>";
		echo "
		<td width='3%'>口水<br/>ID</td>
		<td width='3%'>公司<br/>ID</td>
		<td width='10%'>公司名称</td>
		<td width='6%'>地址</td>
		<td width='3%'>职位<br/>ID</td>
		<td width='10%'>职位名称</td>
		<td width='3%'>昵称<br/>ID</td>
		<td width='10%'>昵称</td>
		<td width='6.5%'>面试日期</td>
		<td style='word-break:break-all; word-wrap:break-word;'>口水正文</td>
		<td width='3%'>like<br/>数量</td>
		<td width='6.5%'>吐槽时间</td>
		<td width='3%'>备注</td>
		<td width='3%'>操作</td>
		";
		echo "</tr>";
		
		while ($row = mysql_fetch_array($result, MYSQL_ASSOC)) { // 遍历查询到的每一行记录。与$row=mysql_fetch_assoc($result)等价 
			//$count = count($row); // 每条记录的字段数量，查询了10个字段，这里得到10
			//$json = json_encode($row).","; // array转json
			echo "<tr>";
			foreach ($row as $line) { 
				echo "<td>".$line."</td>"; 
			} 
			echo "<td><button>删除</button></td>"; 
			echo "</tr>";
		}
		echo "</table>";
	
		mysql_free_result($result);
	}
}

/////////////////////////////////////////////////////////////////////////////////////////

function showBugs(){
	global $connect;
	//id  error   baseinfo  time    totalmemory  availmemory  cpuinfo  otherinfo  description  note
	$sql = sprintf("
	SELECT 
		id,
		error,
		baseinfo,
		time,
		totalmemory,
		availmemory,
		cpuinfo,
		otherinfo,
		description,
		note 
	FROM
		is_buglogs
	");
	$result = mysql_query($sql, $connect);  
	if ($result) {
		echo "<table border='1' width='100%' style='table-layout:fixed;word-break:break-all; word-wrap:break-word;'>";
		echo "<tr>";
		echo "
		<td width='3%'>ID</td>
		<td style='word-break:break-all; word-wrap:break-word;'>错误</td>
		<td width='10%'>基本<br/>信息</td>
		<td width='3%'>发生<br/>时间</td>
		<td width='3%'>总内存</td>
		<td width='3%'>可用<br/>内存</td>
		<td width='3%'>CPU<br/>信息</td>
		<td width='10%'>其它<br/>信息</td>
		<td width='6.5%'>描述</td>
		<td width='6.5%'>备注</td>
		<td width='3%'>操作</td>
		";
		echo "</tr>";
		
		while ($row = mysql_fetch_array($result, MYSQL_ASSOC)) {
			echo "<tr>";
			foreach ($row as $line) { 
				echo "<td>".$line."</td>"; 
			} 
			echo "<td><button>删除</button></td>"; 
			echo "</tr>";
		}
		echo "</table>";
	
		mysql_free_result($result);
	}
}

/////////////////////////////////////////////////////////////////////////////////////////

function showFeedbacks(){
	global $connect;
	
	$sql = sprintf("
	SELECT 
		id,
		feed,
		postdate,
		user_id,
		description,
		note 
	FROM
		is_feedback
	");
	$result = mysql_query($sql, $connect);  
	if ($result) {
		echo "<table border='1' width='100%' style='table-layout:fixed;word-break:break-all; word-wrap:break-word;'>";
		echo "<tr>";
		echo "
		<td width='3%'>ID</td>
		<td style='word-break:break-all; word-wrap:break-word;'>反馈</td>
		<td width='6.5%'>提交<br/>时间</td>
		<td width='3%'>用户<br/>ID</td>
		<td width='6.5%'>描述</td>
		<td width='6.5%'>备注</td>
		<td width='3%'>操作</td>
		";
		echo "</tr>";
		
		while ($row = mysql_fetch_array($result, MYSQL_ASSOC)) {
			echo "<tr>";
			foreach ($row as $line) { 
				echo "<td>".$line."</td>"; 
			} 
			echo "<td><button>删除</button></td>"; 
			echo "</tr>";
		}
		echo "</table>";
	
		mysql_free_result($result);
	}
}


?>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
</body>
</html>
