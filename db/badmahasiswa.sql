-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 22 Okt 2024 pada 02.13
-- Versi server: 10.4.32-MariaDB
-- Versi PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `badmahasiswa`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `mahasiswa`
--

CREATE TABLE `mahasiswa` (
  `id` int(11) NOT NULL,
  `nim` bigint(11) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `nilai_tugas` double NOT NULL,
  `nilai_kuis` double NOT NULL,
  `nilai_uts` double NOT NULL,
  `nilai_uas` double NOT NULL,
  `rerata` double NOT NULL,
  `grade` varchar(100) NOT NULL,
  `keterangan` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `mahasiswa`
--

INSERT INTO `mahasiswa` (`id`, `nim`, `nama`, `nilai_tugas`, `nilai_kuis`, `nilai_uts`, `nilai_uas`, `rerata`, `grade`, `keterangan`) VALUES
(2, 291810189, 'Laili', 99, 90, 98, 82, 92.25, 'A', 'Dinyatakan Lulus'),
(3, 291810182, 'Tata', 80, 92, 85, 83, 85, 'B', 'Dinyatakan Lulus'),
(4, 291810179, 'Titi', 91, 89, 80, 95, 88.75, 'B', 'Dinyatakan Lulus'),
(5, 291810181, 'Lavia', 79, 80, 90, 90, 84.75, 'B', 'Dinyatakan Lulus'),
(6, 291810188, 'Kino', 90, 98, 90, 92, 92.5, 'A', 'Dinyatakan Lulus'),
(7, 291810172, 'Tara', 88, 80, 86, 95, 87.25, 'B', 'Dinyatakan Lulus'),
(8, 291810173, 'Kaira', 90, 98, 91, 90, 92.25, 'A', 'Dinyatakan Lulus'),
(9, 291810174, 'Azta', 88, 80, 83, 82, 83.25, 'B', 'Dinyatakan Lulus'),
(10, 291810175, 'Kino', 90, 92, 88, 83, 88.25, 'B', 'Dinyatakan Lulus');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `mahasiswa`
--
ALTER TABLE `mahasiswa`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `mahasiswa`
--
ALTER TABLE `mahasiswa`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
