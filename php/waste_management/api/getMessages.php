<?php
 
include("connection.php");

$communityId = $_REQUEST['cid'];

$sql = "SELECT * FROM community_chat_tbl WHERE cid='$communityId'";
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