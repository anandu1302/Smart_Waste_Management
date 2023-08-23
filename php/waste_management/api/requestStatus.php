<?php
 
include("connection.php");

$uid = $_REQUEST['uid'];


$sql = "SELECT * FROM request_tbl WHERE uid='$uid' ORDER BY id DESC";
$result = mysqli_query($con,$sql);

if(mysqli_num_rows($result) > 0){

	while($row = mysqli_fetch_assoc($result)){

		$sql2 = "SELECT * FROM worker_tbl WHERE id ='$row[wid]'";
		$result2 = mysqli_query($con,$sql2);
		$roww = mysqli_fetch_assoc($result2);


		$data['data'][] = array('id'=>$row['id'],'date'=>$row['date'],'time'=>$row['time'],'requested_date'=>$row['requested_date'],'status'=>$row['status'],'worker_name' => $roww['name']);
	}
	echo json_encode($data);
}
else{
	echo "failed";
}

?>