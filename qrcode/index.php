<?php
$servername = "localhost";
$username = "root";
$dbname = "qrcode";
$password = "";



try {
    $conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
    // set the PDO error mode to exception
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch (PDOException $e) {
    echo "Connection failed: " . $e->getMessage();
}
//program tampil data barang
if ($_SERVER['REQUEST_METHOD'] === 'GET') {
    $msql = "select * from data_barang order by kode";
    $stmt = $conn->prepare($msql);
    $stmt->execute();
    $data = $stmt->fetchAll(PDO::FETCH_ASSOC);
    echo json_encode($data);
} else if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $kode = $_POST['kode'];
    $nama = $_POST['nama_barang'];
    $harga = $_POST['harga'];
    $satuan = $_POST['satuan'];
    $jumlah = $_POST['jumlah'];
    $msql = "insert into data_barang (kode,nama_barang,harga, satuan, jumlah) values (?,?,?,?,?)";
    $stat = $conn->prepare($msql);
    $res = $stat->execute([$kode, $nama, $harga, $satuan, $jumlah]);

    require_once('phpqrcode/qrlib.php');
    $nama = $kode;
    $namafile = $nama . '.png';
    $tempDir = 'qr/';
    $pngAbsoluteFilePath = $tempDir . $namafile;
    $content = $nama;
    $urlRelativeFilePath = $pngAbsoluteFilePath;
    if (!file_exists($pngAbsoluteFilePath)) {
        QRcode::png($content, $pngAbsoluteFilePath);
    }

    if ($res) {
        $data = ['kode' => $kode, 'nama' => $nama, 'harga' => $harga, 'satuan' => $satuan, 'jumlah' => $jumlah];
        echo json_encode($data);
    } else {
        echo json_encode(['error' => $stat->errorCode()]);
    }
}
