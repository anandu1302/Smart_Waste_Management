<?php

include("connection.php");

$wid = $_POST['uid'];
$rid = $_POST['rid'];

$sql = "UPDATE request_tbl SET status = 'accepted' , wid='$wid' WHERE id = '$rid'";
	mysqli_query($con,$sql);

echo "success";


?>