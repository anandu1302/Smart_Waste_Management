<?php
 
include("connection.php");

$uid = $_REQUEST['uid'];

$sql = "SELECT * FROM history_tbl WHERE uid ='$uid' ORDER BY id DESC";
$result = mysqli_query($con,$sql);

if(mysqli_num_rows($result) > 0){

	while($row = mysqli_fetch_assoc($result)){

		$sql2 = "SELECT * FROM products_tbl WHERE id ='$row[pid]'";
		$result2 = mysqli_query($con,$sql2);
		$roww = mysqli_fetch_assoc($result2);

		$data['data'][] = array('id'=>$row['id'],'price'=>$row['price'],'orderid'=>$row['orderid'],'name' => $roww['pname'], 'image' => $roww['image']);
	}
	echo json_encode($data);
}
else{
	echo "failed";
}

?>