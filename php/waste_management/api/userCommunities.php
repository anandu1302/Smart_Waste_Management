<?php
 
include("connection.php");

$uid = $_REQUEST['uid'];
//$uid="1";

$sql = "SELECT * FROM user_community_tbl WHERE uid='$uid' ORDER BY id DESC";
$result = mysqli_query($con,$sql);

if(mysqli_num_rows($result) > 0){

	while($row = mysqli_fetch_assoc($result)){

		$sql2 = "SELECT * FROM community_tbl WHERE id ='$row[community_id]'";
		$result2 = mysqli_query($con,$sql2);
		$roww = mysqli_fetch_assoc($result2);

		$data["data"][] = $roww;
	}
	echo json_encode($data);
}
else{
	echo "failed";
}

?>