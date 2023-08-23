<?php

include("connection.php");


$uid= $_POST['uid'];
$cid = $_POST['cid'];
$pin= $_POST['pin'];
$acc= $_POST['accountNo'];
$am= $_POST['amount']; 


$date=date('Y-m-d');

 $sel1 = "SELECT * FROM account_tbl WHERE accountno='$acc' and uid='$uid'";
 //echo $sel1;
 $res1 = mysqli_query($con,$sel1);
 
 if(mysqli_num_rows($res1)>0){

 	while($row1=mysqli_fetch_array($res1)){

 		$p= $row1["pin"];

 		if($p==$pin){
		    $amount=$row1["amount"];
			if($amount>=$am){
			  $a=$amount-$am;

			  //echo $a;
			  $sql2 = "UPDATE account_tbl SET amount='$a' WHERE accountno='$acc'";
			  mysqli_query($con,$sql2);

			  $sql3 = "UPDATE request_tbl SET status='paid' WHERE id='$cid'";
			  mysqli_query($con,$sql3);


		
			  echo "success";

			}

			else{
				echo "failed";	
				}
			}

			else{
					echo "pin";
				}
		}

 	}
  
  else{
	echo "accerror";	
	}	   
?>