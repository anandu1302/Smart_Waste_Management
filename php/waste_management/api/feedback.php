<?php

include("connection.php");

$uid = $_POST['uid'];
$workerId = $_POST['workerId'];
$rating = $_POST['rating'];
$feedback = $_POST['feedback'];


$sql ="INSERT INTO feedback_tbl (uid, worker_name, rating,feedback) VALUES ('$uid','$workerId','$rating','$feedback')";

if(mysqli_query($con,$sql)){
	
	echo"success";
}
else{
	
	echo"failed";
}


?>