<?php

include("connection.php");

$uid = $_POST['uid'];
$description = $_POST['description'];
$wasteImage = $_POST['image'];
$latitude = $_POST['latitude'];
$longitude = $_POST['longitude'];
$date = date('Y-m-d');


$sql ="INSERT INTO report_tbl (uid,description,latitude,longitude,date,status) VALUES ('$uid','$description','$latitude','$longitude','$date','reported')";

if(mysqli_query($con,$sql))
{
	$id = mysqli_insert_id($con);
	$fileName = "image".$id.".jpg";
	file_put_contents("../report_tbl/uploads/".$fileName, base64_decode($wasteImage));

	$sql = "UPDATE report_tbl SET image = '$fileName' WHERE id = '$id'";
	mysqli_query($con,$sql);


	$sql2 = "INSERT INTO points_tbl(uid,points,description) VALUES ('$uid','25','waste reported')";
	mysqli_query($con,$sql2);

	echo "success";

}	
else{
	
	echo"failed";
}
?>