<?php

include("connection.php");

$uid = $_POST['uid'];
$communityId = $_POST['communityId'];
$message = $_POST['message'];


$sql ="INSERT INTO community_chat_tbl (uid, cid, message) VALUES ('$uid','$communityId','$message')";

if(mysqli_query($con,$sql)){
	
	echo"success";
}
else{
	
	echo"failed";
}


?>