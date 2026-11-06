# TodoApp JavaFX - Aplikasi Daftar Kegiatan Harian

TodoApp JavaFX adalah prototipe aplikasi desktop untuk mencatat, mengelola, dan memantau kegiatan harian. Aplikasi ini dibangun untuk memenuhi Tugas Pemantapan 2 mata kuliah Pemrograman Berbasis Platform, dengan menerapkan pola arsitektur Model-View-Controller (MVC), desain antarmuka menggunakan JavaFX Scene Builder dan FXML, serta prinsip antarmuka desktop WIMP (Window, Icon, Menu, Pointer).

## Fitur Utama

- Menampilkan daftar kegiatan dalam ListView.
- Menambahkan kegiatan baru melalui TextField dan Button.
- Validasi input: peringatan muncul jika pengguna mencoba menambah kegiatan kosong.
- Menghapus kegiatan yang dipilih dari daftar.
- Fitur status kegiatan menggunakan CheckBox: kegiatan yang dicentang akan berstatus selesai dan teksnya dicoret.
- Menu File dengan item Exit untuk menutup aplikasi.
- Integrasi dengan database MySQL: data tersimpan permanen dan tersinkronisasi dengan proyek web sebelumnya.

## Teknologi yang Digunakan

| Kategori | Teknologi |
|----------|-----------|
| Bahasa | Java (JDK 21 LTS dengan JavaFX dari Azul Zulu FX) |
| GUI Framework | JavaFX (Scene Builder dan FXML) |
| Database | MySQL (via JDBC Driver mysql-connector-j) |
| IDE | Visual Studio Code dengan Extension Pack for Java |

## Prasyarat

Sebelum menjalankan aplikasi, pastikan perangkat Anda sudah terpasang:

1. JDK 21 LTS versi FX (Azul Zulu FX atau Liberica JDK Full).
2. MySQL Server (bisa menggunakan XAMPP, Laragon, atau instalasi mandiri).
3. Visual Studio Code dengan Extension Pack for Java.

## Cara Instalasi

1. Clone repository ini atau ekstrak file proyek ke direktori lokal Anda.
git clone https://github.com/rifanafandi12/TodoAppJavaFX.git
cd TodoAppJavaFX


2. Pastikan database `taskflow` sudah dibuat (sama dengan proyek web sebelumnya). Jika belum, jalankan perintah SQL berikut.
CREATE DATABASE taskflow;
USE taskflow;

CREATE TABLE tasks (
id INT AUTO_INCREMENT PRIMARY KEY,
title VARCHAR(255) NOT NULL,
description TEXT,
status ENUM('todo', 'in_progress', 'done') DEFAULT 'todo',
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


3. Sesuaikan konfigurasi database pada file `src/application/DatabaseHelper.java`.

```java
private static final String URL = "jdbc:mysql://localhost:3306/taskflow";
private static final String USER = "root";
private static final String PASSWORD = "";
Unduh MySQL Connector/J dari situs resmi MySQL, lalu salin file mysql-connector-j-8.x.x.jar ke dalam folder lib/ di proyek.

Buka folder proyek di Visual Studio Code. Tunggu hingga proses indexing selesai.

Cara Menjalankan
Buka panel Run and Debug di sidebar kiri Visual Studio Code.

Pilih konfigurasi Launch TodoApp dari dropdown.

Klik tombol segitiga hijau untuk menjalankan aplikasi.

Pastikan Anda tidak menggunakan tombol Run di pojok kanan atas file Main.java, karena tombol tersebut mengabaikan konfigurasi launch.json yang sudah disiapkan.

Struktur Proyek

TodoAppJavaFX/
├── lib/                          # Driver MySQL dan library tambahan
├── src/
│   ├── application/
│   │   ├── Main.java             # Entry point aplikasi
│   │   ├── TodoController.java   # Controller (logika event dan interaksi)
│   │   ├── TodoModel.java        # Model (logika database)
│   │   ├── TodoItem.java         # Objek data kegiatan
│   │   └── DatabaseHelper.java   # Koneksi JDBC ke MySQL
│   └── views/
│       └── MainView.fxml         # View (tata letak antarmuka)
├── .vscode/
│   ├── launch.json               # Konfigurasi run dan debug
│   └── settings.json             # Konfigurasi Java project
├── .gitignore
└── README.md

Penerapan Pola MVC
Model (TodoModel.java): Mengelola koneksi database, mengambil data (SELECT), menambah (INSERT), menghapus (DELETE), dan mengubah status (UPDATE). Menggunakan ObservableList<TodoItem> agar UI otomatis ter-update.

View (MainView.fxml): Mendefinisikan tata letak antarmuka dan diberi gaya dengan CSS inline.

Controller (TodoController.java): Menghubungkan View dan Model, menangani event tombol, memvalidasi input, dan membuat CellFactory kustom untuk menampilkan CheckBox.

Catatan Penggunaan
Pastikan MySQL Server dalam keadaan berjalan sebelum menjalankan aplikasi.

File DatabaseHelper.java berisi kredensial database. Jika proyek ini diunggah ke repositori publik, pertimbangkan untuk memindahkan kredensial ke file konfigurasi terpisah.

Untuk menguji fitur status, centang CheckBox di samping kegiatan. Teks akan tercoret dan status di database berubah menjadi "done".

Pengembang
Tugas Pemantapan 2 - Mata Kuliah Pemrograman Berbasis Platform