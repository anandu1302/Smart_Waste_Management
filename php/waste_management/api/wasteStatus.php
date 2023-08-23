<?php

include("connection.php");

$workerId = $_POST['wid'];
$wasteId = $_POST['wasteId'];

$sql = "UPDATE report_tbl SET status = 'cleared' , worker_id='$workerId' WHERE id = '$wasteId'";
	mysqli_query($con,$sql);

echo "success";

?>