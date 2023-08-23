<?php

include("connection.php");

$uid = $_POST['uid'];
$cid = $_POST['cid'];

$sql2 ="SELECT * FROM user_campaign_tbl WHERE uid ='$uid' and cid='$cid'";
$result = mysqli_query($con,$sql2);

if(mysqli_num_rows($result) > 0){

    echo "Already Joined";
}else {

$sql ="INSERT INTO user_campaign_tbl (uid, cid) VALUES ('$uid','$cid')";

if(mysqli_query($con,$sql)){

	$sql2 = "INSERT INTO points_tbl(uid,points,description) VALUES ('$uid','10','Joined a Campaign')";
	mysqli_query($con,$sql2);
	
	echo"success";
}
else{
	
	echo"failed";
}
}


?>