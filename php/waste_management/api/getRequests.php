<?php
 
include("connection.php");


$sql = "SELECT * FROM request_tbl WHERE status='requested'";
$result = mysqli_query($con,$sql);

if(mysqli_num_rows($result) > 0){

	while($row = mysqli_fetch_assoc($result)){

		$sql2 = "SELECT * FROM user_tbl WHERE id ='$row[uid]'";
		$result2 = mysqli_query($con,$sql2);
		$roww = mysqli_fetch_assoc($result2);


		$data['data'][] = array('id'=>$row['id'],'date'=>$row['date'],'time'=>$row['time'],'requested_date'=>$row['requested_date'],'status'=>$row['status'],'name' => $roww['name'], 'location' => $roww['location']);
	}
	echo json_encode($data);
}
else{
	echo "failed";
}

?>