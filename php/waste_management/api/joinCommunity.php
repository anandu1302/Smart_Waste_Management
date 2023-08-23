<?php

include("connection.php");

$uid = $_POST['uid'];
$cid = $_POST['cid'];

$sql2 ="SELECT * FROM user_community_tbl WHERE uid ='$uid' and community_id='$cid'";
$result = mysqli_query($con,$sql2);

if(mysqli_num_rows($result) > 0){

 echo "Already Joined";
}else {

	$sql ="INSERT INTO user_community_tbl (uid,community_id) VALUES ('$uid','$cid')";

	if(mysqli_query($con,$sql)){

	
		echo"success";
	}
	else{
	
		echo"failed";
	}

}


?>