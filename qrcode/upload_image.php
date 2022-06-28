<?php
include_once('koneksiku.php');

class emp
{
}

$image = $_POST['gambar'];
$kode = $_POST['kode'];

$nama_file = $kode . ".png";

$path = "image/" . $kode;

// sesuiakan ip address laptop/pc atau URL server
$actualpath = "http://172.20.10.3/android/upload_image/$path";

$query = mysqli_query($koneksi, "UPDATE `data_barang` SET `image`='$nama_file' WHERE `kode`=$kode ");

if ($query) {
	file_put_contents($path, base64_decode($image));

	$response = new emp();
	$response->success = 1;
	$response->message = "Gambar disimpan";
	die(json_encode($response));
} else {
	$response = new emp();
	$response->success = 0;
	$response->message = "Upload gambar gagal";
	die(json_encode($response));
}
mysqli_close($con);
