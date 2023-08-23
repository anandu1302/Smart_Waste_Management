<?php
 
include("connection.php");


$uid = $_REQUEST['uid'];

/*for selecting the name of worker from worker
 table as feedbackmis available against name  */
$sql2 ="SELECT name FROM worker_tbl WHERE id ='$uid'";
$result2 = mysqli_query($con,$sql2);
$roww = mysqli_fetch_assoc($result2);

/* for selecting the corresponding feedbacks for a specific worker */
$sql = "SELECT * FROM feedback_tbl WHERE worker_name ='$roww[name]'";
$result = mysqli_query($con,$sql);

//echo $sql;

if(mysqli_num_rows($result) > 0){

	while($row = mysqli_fetch_assoc($result)){

        /* for selecting username from user_tbl as only
         userid is available in feedaback tbl*/
      
		$sql3 = "SELECT * FROM user_tbl WHERE id ='$row[uid]'";
		$result3 = mysqli_query($con,$sql3);
		$row3 = mysqli_fetch_assoc($result3); 


		$data['data'][] = array('id'=>$row['id'],'rating'=>$row['rating'],'feedback'=>$row['feedback'],'name' => $row3['name']);
	}
	echo json_encode($data);
}
else{
	echo "failed";
}

?>