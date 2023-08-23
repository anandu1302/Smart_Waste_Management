<?php
 
include("connection.php");

$cid = $_REQUEST['cid'];

$sql = "SELECT * FROM campaigns_tbl WHERE id = $cid";
$result = mysqli_query($con,$sql);

if(mysqli_num_rows($result) > 0){

	while($row = mysqli_fetch_assoc($result))
		$data["data"][] = $row;
	echo json_encode($data);
}
else{
	echo "failed";
}

?>