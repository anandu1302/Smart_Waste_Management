<?php

include("connection.php");

$uid = $_POST['uid'];
$name = $_POST['name'];
$email = $_POST['email'];
$username = $_POST['username'];
$number = $_POST['number'];
$address = $_POST['address'];
$location = $_POST['location'];


$sql = "UPDATE user_tbl SET name='$name',address='$address',location='$location',number='$number',email='$email',username='$username' WHERE id='$uid'";

//echo $sql;
        
 if(mysqli_query($con,$sql)){
    echo "Successfully Updated";
}else{
	echo"Failed to Update Profile";
}

?>