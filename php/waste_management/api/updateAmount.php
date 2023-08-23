<?php

include("connection.php");

$wid = $_POST['wid'];
$amount = $_POST['amount'];

$sql = "UPDATE request_tbl SET amount = '$amount',status = 'collected' WHERE id = '$wid'";
	mysqli_query($con,$sql);

echo "success";


?>