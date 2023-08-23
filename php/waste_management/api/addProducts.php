<?php

include("connection.php");

$uid = $_POST['uid'];
$pname = $_POST['pname'];
$pdesc = $_POST['pdesc'];
$price = $_POST['price'];
$image = $_POST['image'];


$sql ="INSERT INTO products_tbl (uid,pname, description,price,status) VALUES ('$uid','$pname','$pdesc','$price','available')";

if(mysqli_query($con,$sql)){
	
	$id = mysqli_insert_id($con);
	$fileName = "product".$id.".jpg";
	file_put_contents("../products_tbl/uploads/".$fileName, base64_decode($image));

	$sql = "UPDATE products_tbl SET image = '$fileName' WHERE id = '$id'";
	mysqli_query($con,$sql);
	echo "success";
}
else{
	
	echo"failed";
}



?>