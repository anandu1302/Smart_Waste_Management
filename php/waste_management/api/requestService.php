<?php

include("connection.php");

$uid = $_POST['uid'];
$requestedDate = $_POST['requestedDate'];
$time = $_POST['time'];

$date = date('d-m-y');

$sql ="INSERT INTO request_tbl (uid, date, time,requested_date,status) VALUES ('$uid','$date','$time','$requestedDate','requested')";

if(mysqli_query($con,$sql)){
	
	echo"success";
}
else{
	
	echo"failed";
}



?>