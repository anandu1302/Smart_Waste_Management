<?php

include("connection.php");

$name = $_POST['name'];
$address = $_POST['address'];
$location = $_POST['location'];
$email = $_POST['email'];
$number = $_POST['number'];
$username = $_POST['username'];
$password = $_POST['password'];


$sql ="INSERT INTO user_tbl (name, address, location,number,email,username, password) VALUES ('$name','$address','$location','$number','$email','$username','$password')";

if(mysqli_query($con,$sql)){
	
	echo"success";
}
else{
	
	echo"failed";
}


?>