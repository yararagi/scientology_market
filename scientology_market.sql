-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Feb 18, 2025 at 07:27 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `scientology_market`
--

-- --------------------------------------------------------

--
-- Table structure for table `persona`
--

CREATE TABLE `persona` (
  `id` int(11) NOT NULL,
  `nome` varchar(64) NOT NULL,
  `cognome` varchar(64) NOT NULL,
  `mail` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `persona`
--

INSERT INTO `persona` (`id`, `nome`, `cognome`, `mail`) VALUES
(1, 'Mario', 'Rossi', 'mario.rossi@example.com'),
(2, 'Luigi', 'Verdi', 'luigi.verdi@example.com'),
(3, 'Giovanna', 'Bianchi', 'giovanna.bianchi@example.com'),
(4, 'Anna', 'Neri', 'anna.neri@example.com');

-- --------------------------------------------------------

--
-- Table structure for table `sede`
--

CREATE TABLE `sede` (
  `id` int(11) NOT NULL,
  `nome` varchar(64) NOT NULL,
  `indirizzo` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `sede`
--

INSERT INTO `sede` (`id`, `nome`, `indirizzo`) VALUES
(1, 'Sede Centrale', 'Via Roma 123, Milano'),
(2, 'Sede Nord', 'Via Torino 45, Torino'),
(3, 'Sede Sud', 'Via Napoli 67, Napoli');

-- --------------------------------------------------------

--
-- Table structure for table `tessera`
--

CREATE TABLE `tessera` (
  `id` int(11) NOT NULL,
  `sede_creazione_id` int(11) NOT NULL,
  `punti` int(11) NOT NULL,
  `cliente_id` int(11) NOT NULL,
  `data_creazione` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tessera`
--

INSERT INTO `tessera` (`id`, `sede_creazione_id`, `punti`, `cliente_id`, `data_creazione`) VALUES
(1, 1, 100, 1, '2023-10-01 10:00:00'),
(2, 2, 50, 2, '2023-10-02 11:30:00'),
(3, 3, 200, 3, '2023-10-03 09:15:00'),
(4, 1, 75, 4, '2023-10-04 14:45:00');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `persona`
--
ALTER TABLE `persona`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `sede`
--
ALTER TABLE `sede`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tessera`
--
ALTER TABLE `tessera`
  ADD PRIMARY KEY (`id`),
  ADD KEY `sede_creazione` (`sede_creazione_id`),
  ADD KEY `cliente` (`cliente_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `persona`
--
ALTER TABLE `persona`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `sede`
--
ALTER TABLE `sede`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `tessera`
--
ALTER TABLE `tessera`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `persona`
--
ALTER TABLE `persona`
  ADD CONSTRAINT `persona_ibfk_1` FOREIGN KEY (`id`) REFERENCES `tessera` (`cliente_id`);

--
-- Constraints for table `tessera`
--
ALTER TABLE `tessera`
  ADD CONSTRAINT `tessera_ibfk_1` FOREIGN KEY (`sede_creazione_id`) REFERENCES `sede` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
