-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Dec 27, 2024 at 03:10 PM
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
-- Database: `notification`
--

-- --------------------------------------------------------

--
-- Table structure for table `message`
--

CREATE TABLE `message` (
  `id` int(11) NOT NULL,
  `khmer_text` varchar(255) DEFAULT NULL,
  `english_text` varchar(255) DEFAULT NULL,
  `sender` varchar(50) DEFAULT NULL,
  `receiver` varchar(50) DEFAULT NULL,
  `messageType` varchar(50) DEFAULT NULL,
  `sessionId` varchar(255) DEFAULT NULL,
  `date_time` datetime DEFAULT NULL,
  `serviceType` varchar(100) DEFAULT NULL,
  `is_read` tinyint(1) DEFAULT 0,
  `status` varchar(50) DEFAULT NULL,
  `attachments` text DEFAULT NULL,
  `priority` varchar(20) DEFAULT NULL,
  `edited_at` datetime DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `message`
--

INSERT INTO `message` (`id`, `khmer_text`, `english_text`, `sender`, `receiver`, `messageType`, `sessionId`, `date_time`, `serviceType`, `is_read`, `status`, `attachments`, `priority`, `edited_at`, `deleted_at`) VALUES
(1, NULL, 'Ah blair', 'Hello', NULL, 'CHAT', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL),
(2, NULL, 'Hello world', 'Sing', NULL, 'GENERAL', NULL, '2024-11-18 14:02:47', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(3, NULL, 'Hello world', 'Sing', NULL, 'GENERAL', NULL, '2024-11-18 14:02:47', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(52, NULL, 'Hello', 'HR', NULL, 'GENERAL', NULL, '2024-11-19 20:21:21', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(53, NULL, 'Hello', 'HR', NULL, 'GENERAL', NULL, '2024-11-19 20:21:53', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(54, NULL, 'Hello', 'HR', NULL, 'GENERAL', NULL, '2024-11-19 20:24:13', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(55, NULL, 'Hello', 'HR2', NULL, 'GENERAL', NULL, '2024-11-19 20:24:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(56, NULL, 'Hello', 'HR2', NULL, 'GENERAL', NULL, '2024-11-19 20:25:07', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(57, NULL, 'Hello', 'Sing', NULL, 'GENERAL', NULL, '2024-11-19 20:25:52', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(58, NULL, 'Hello', 'Sing', NULL, 'GENERAL', NULL, '2024-11-19 20:27:46', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(59, NULL, 'Hello', 'Ah blair', NULL, 'GENERAL', NULL, '2024-11-19 20:28:07', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(60, NULL, 'Hello', 'Ah blair2', NULL, 'GENERAL', NULL, '2024-11-19 20:28:44', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(102, NULL, 'Hello', 'Manger', NULL, 'GENERAL', NULL, '2024-11-19 22:51:39', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(152, NULL, 'Welcome, Mr. asa kim to CATS! We\'re excited to have you on board.', 'Human Resources', NULL, 'GENERAL', NULL, '2024-11-20 20:52:15', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(153, NULL, 'Welcome, Mr. asa kim to CATS! We\'re excited to have you on board.', 'Human Resources', NULL, 'GENERAL', NULL, '2024-11-20 21:02:12', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(202, NULL, 'Hello world!', 'Human Resources', NULL, 'GENERAL', NULL, '2024-11-23 13:59:51', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(252, NULL, 'Hello world!', 'Visal KH', NULL, 'GENERAL', NULL, '2024-11-23 15:04:56', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(253, NULL, 'Hello world!', 'Visal KH', NULL, 'GENERAL', NULL, '2024-11-23 15:05:27', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(254, NULL, 'Hello world!', 'Visal KH', NULL, 'GENERAL', NULL, '2024-11-23 15:08:49', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(302, NULL, 'Hello new staff', 'Visal', NULL, 'GENERAL', NULL, '2024-11-24 13:31:46', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(303, NULL, 'Welcome, Mr. Visal Ratanak to CATS! We\'re excited to have you on board.', 'Human Resources', NULL, 'GENERAL', NULL, '2024-11-24 14:29:12', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(352, NULL, 'Welcome, Mr. kim visal to CATS! We\'re excited to have you on board.', 'Human Resources', NULL, 'GENERAL', NULL, '2024-11-26 19:05:15', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(402, NULL, 'Hello everyone!', 'Hr', NULL, 'GENERAL', NULL, '2024-11-30 11:59:45', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(403, NULL, 'Hello everyone!', 'Hr', NULL, 'GENERAL', NULL, '2024-11-30 12:00:01', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(452, NULL, 'Hello', 'Hr', NULL, 'GENERAL', NULL, '2024-12-01 14:34:34', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(453, NULL, 'Hello', 'Hr', NULL, 'GENERAL', NULL, '2024-12-01 14:34:34', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(454, NULL, 'Hello', 'Hr', NULL, 'GENERAL', NULL, '2024-12-01 14:34:34', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(455, NULL, 'Hello', 'Hr', NULL, 'GENERAL', NULL, '2024-12-01 14:34:34', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(456, NULL, 'Hello', 'Hr', NULL, 'GENERAL', NULL, '2024-12-01 14:34:34', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(457, NULL, 'Hello', 'Hr', NULL, 'GENERAL', NULL, '2024-12-01 14:34:34', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(458, NULL, 'Hello', 'Hr', NULL, 'GENERAL', NULL, '2024-12-01 14:34:34', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(459, NULL, 'Hello', 'Hr', NULL, 'GENERAL', NULL, '2024-12-01 14:34:34', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(460, NULL, 'Hello', 'Hr', NULL, 'GENERAL', NULL, '2024-12-01 14:34:34', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(461, NULL, 'Hello', 'Hr', NULL, 'GENERAL', NULL, '2024-12-01 14:34:34', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(502, NULL, 'Hello', 'Hr', NULL, 'GENERAL', NULL, '2024-12-01 14:34:34', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(552, NULL, 'Hello everyone', 'Hr', '1000', 'GENERAL', NULL, '2024-12-01 14:34:34', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(553, NULL, 'Hello everyone', 'Hr', '1002', 'GENERAL', NULL, '2024-12-01 14:34:34', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(554, NULL, 'Hello everyone', 'Hr', '1003', 'GENERAL', NULL, '2024-12-01 14:34:34', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(555, NULL, 'Hello everyone', 'Hr', '1004', 'GENERAL', NULL, '2024-12-01 14:34:34', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(556, NULL, 'Hello everyone', 'Hr', '1005', 'GENERAL', NULL, '2024-12-01 14:34:34', NULL, 1, NULL, NULL, NULL, '2024-12-07 15:15:52', NULL),
(557, NULL, 'Hello everyone', 'Hr', '1006', 'GENERAL', NULL, '2024-12-01 14:34:34', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(558, NULL, 'Hello everyone', 'Hr', '1008', 'GENERAL', NULL, '2024-12-01 14:34:34', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(559, NULL, 'Hello everyone', 'Hr', '1009', 'GENERAL', NULL, '2024-12-01 14:34:34', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(560, NULL, 'Hello everyone', 'Hr', '1010', 'GENERAL', NULL, '2024-12-01 14:34:34', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(561, NULL, 'Hello everyone', 'Hr', '1011', 'GENERAL', NULL, '2024-12-01 14:34:34', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(562, NULL, 'Hello everyone', 'Hr', '1012', 'GENERAL', NULL, '2024-12-01 14:34:34', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(563, NULL, 'Hello everyone', 'Hr', '1013', 'GENERAL', NULL, '2024-12-01 14:34:34', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(564, NULL, 'Hello everyone', 'Hr', '1014', 'GENERAL', NULL, '2024-12-01 14:34:34', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(565, NULL, 'Hello everyone', 'Hr', '1015', 'GENERAL', NULL, '2024-12-01 14:34:34', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(566, NULL, 'Hello everyone', 'Hr', '1016', 'GENERAL', NULL, '2024-12-01 14:34:34', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(567, NULL, 'Hello everyone', 'Hr', '1017', 'GENERAL', NULL, '2024-12-01 14:34:34', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(568, NULL, 'Hello everyone', 'Hr', '1018', 'GENERAL', NULL, '2024-12-01 14:34:34', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(569, NULL, 'Hello everyone', 'Hr', '1020', 'GENERAL', NULL, '2024-12-01 14:34:34', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(570, NULL, 'Hello everyone', 'Hr', '1021', 'GENERAL', NULL, '2024-12-01 14:34:34', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(571, NULL, 'Hello everyone', 'Hr', '1022', 'GENERAL', NULL, '2024-12-01 14:34:34', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(572, NULL, 'Hello everyone', 'Hr', '1086', 'GENERAL', NULL, '2024-12-01 14:34:34', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(573, NULL, 'Hello everyone', 'Hr', '2222', 'GENERAL', NULL, '2024-12-01 14:34:34', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(574, NULL, 'Hello everyone', 'Hr', '2431', 'GENERAL', NULL, '2024-12-01 14:34:34', NULL, 1, NULL, NULL, NULL, '2024-12-04 20:54:30', NULL),
(575, NULL, 'Hello everyone', 'Hr', '2433', 'GENERAL', NULL, '2024-12-01 14:34:34', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(576, NULL, 'Hello everyone', 'Hr', '2434', 'GENERAL', NULL, '2024-12-01 14:34:34', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(577, NULL, 'Hello everyone', 'Hr', '2435', 'GENERAL', NULL, '2024-12-01 14:34:34', NULL, 1, NULL, NULL, NULL, '2024-12-04 20:52:51', NULL),
(578, NULL, 'Hello everyone', 'Hr', '2436', 'GENERAL', NULL, '2024-12-01 14:34:34', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(579, NULL, 'Hello everyone', 'Hr', '2501', 'GENERAL', NULL, '2024-12-01 14:34:34', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(602, NULL, 'Hello everyone', 'Hr', '1000', 'GENERAL', NULL, '2024-12-04 13:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(603, NULL, 'Hello everyone', 'Hr', '1002', 'GENERAL', NULL, '2024-12-04 13:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(604, NULL, 'Hello everyone', 'Hr', '1003', 'GENERAL', NULL, '2024-12-04 13:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(605, NULL, 'Hello everyone', 'Hr', '1004', 'GENERAL', NULL, '2024-12-04 13:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(606, NULL, 'Hello everyone', 'Hr', '1005', 'GENERAL', NULL, '2024-12-04 13:59:24', NULL, 1, NULL, NULL, NULL, '2024-12-07 15:15:52', NULL),
(607, NULL, 'Hello everyone', 'Hr', '1006', 'GENERAL', NULL, '2024-12-04 13:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(608, NULL, 'Hello everyone', 'Hr', '1008', 'GENERAL', NULL, '2024-12-04 13:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(609, NULL, 'Hello everyone', 'Hr', '1009', 'GENERAL', NULL, '2024-12-04 13:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(610, NULL, 'Hello everyone', 'Hr', '1010', 'GENERAL', NULL, '2024-12-04 13:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(611, NULL, 'Hello everyone', 'Hr', '1011', 'GENERAL', NULL, '2024-12-04 13:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(612, NULL, 'Hello everyone', 'Hr', '1012', 'GENERAL', NULL, '2024-12-04 13:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(613, NULL, 'Hello everyone', 'Hr', '1013', 'GENERAL', NULL, '2024-12-04 13:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(614, NULL, 'Hello everyone', 'Hr', '1014', 'GENERAL', NULL, '2024-12-04 13:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(615, NULL, 'Hello everyone', 'Hr', '1015', 'GENERAL', NULL, '2024-12-04 13:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(616, NULL, 'Hello everyone', 'Hr', '1016', 'GENERAL', NULL, '2024-12-04 13:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(617, NULL, 'Hello everyone', 'Hr', '1017', 'GENERAL', NULL, '2024-12-04 13:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(618, NULL, 'Hello everyone', 'Hr', '1018', 'GENERAL', NULL, '2024-12-04 13:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(619, NULL, 'Hello everyone', 'Hr', '1020', 'GENERAL', NULL, '2024-12-04 13:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(620, NULL, 'Hello everyone', 'Hr', '1021', 'GENERAL', NULL, '2024-12-04 13:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(621, NULL, 'Hello everyone', 'Hr', '1022', 'GENERAL', NULL, '2024-12-04 13:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(622, NULL, 'Hello everyone', 'Hr', '1086', 'GENERAL', NULL, '2024-12-04 13:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(623, NULL, 'Hello everyone', 'Hr', '2222', 'GENERAL', NULL, '2024-12-04 13:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(624, NULL, 'Hello everyone', 'Hr', '2431', 'GENERAL', NULL, '2024-12-04 13:59:24', NULL, 1, NULL, NULL, NULL, '2024-12-04 20:59:57', NULL),
(625, NULL, 'Hello everyone', 'Hr', '2433', 'GENERAL', NULL, '2024-12-04 13:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(626, NULL, 'Hello everyone', 'Hr', '2434', 'GENERAL', NULL, '2024-12-04 13:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(627, NULL, 'Hello everyone', 'Hr', '2435', 'GENERAL', NULL, '2024-12-04 13:59:24', NULL, 1, NULL, NULL, NULL, '2024-12-04 21:05:45', NULL),
(628, NULL, 'Hello everyone', 'Hr', '2436', 'GENERAL', NULL, '2024-12-04 13:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(629, NULL, 'Hello everyone', 'Hr', '2501', 'GENERAL', NULL, '2024-12-04 13:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(630, NULL, 'Hello everyone', 'Hr', '1000', 'GENERAL', NULL, '2024-12-04 13:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(631, NULL, 'Hello everyone', 'Hr', '1002', 'GENERAL', NULL, '2024-12-04 13:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(632, NULL, 'Hello everyone', 'Hr', '1003', 'GENERAL', NULL, '2024-12-04 13:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(633, NULL, 'Hello everyone', 'Hr', '1004', 'GENERAL', NULL, '2024-12-04 13:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(634, NULL, 'Hello everyone', 'Hr', '1005', 'GENERAL', NULL, '2024-12-04 13:59:24', NULL, 1, NULL, NULL, NULL, '2024-12-07 15:15:52', NULL),
(635, NULL, 'Hello everyone', 'Hr', '1006', 'GENERAL', NULL, '2024-12-04 13:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(636, NULL, 'Hello everyone', 'Hr', '1008', 'GENERAL', NULL, '2024-12-04 13:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(637, NULL, 'Hello everyone', 'Hr', '1009', 'GENERAL', NULL, '2024-12-04 13:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(638, NULL, 'Hello everyone', 'Hr', '1010', 'GENERAL', NULL, '2024-12-04 13:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(639, NULL, 'Hello everyone', 'Hr', '1011', 'GENERAL', NULL, '2024-12-04 13:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(640, NULL, 'Hello everyone', 'Hr', '1012', 'GENERAL', NULL, '2024-12-04 13:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(641, NULL, 'Hello everyone', 'Hr', '1013', 'GENERAL', NULL, '2024-12-04 13:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(642, NULL, 'Hello everyone', 'Hr', '1014', 'GENERAL', NULL, '2024-12-04 13:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(643, NULL, 'Hello everyone', 'Hr', '1015', 'GENERAL', NULL, '2024-12-04 13:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(644, NULL, 'Hello everyone', 'Hr', '1016', 'GENERAL', NULL, '2024-12-04 13:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(645, NULL, 'Hello everyone', 'Hr', '1017', 'GENERAL', NULL, '2024-12-04 13:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(646, NULL, 'Hello everyone', 'Hr', '1018', 'GENERAL', NULL, '2024-12-04 13:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(647, NULL, 'Hello everyone', 'Hr', '1020', 'GENERAL', NULL, '2024-12-04 13:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(648, NULL, 'Hello everyone', 'Hr', '1021', 'GENERAL', NULL, '2024-12-04 13:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(649, NULL, 'Hello everyone', 'Hr', '1022', 'GENERAL', NULL, '2024-12-04 13:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(650, NULL, 'Hello everyone', 'Hr', '1086', 'GENERAL', NULL, '2024-12-04 13:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(651, NULL, 'Hello everyone', 'Hr', '2222', 'GENERAL', NULL, '2024-12-04 13:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(652, NULL, 'Hello everyone', 'Hr', '2431', 'GENERAL', NULL, '2024-12-04 13:59:24', NULL, 1, NULL, NULL, NULL, '2024-12-04 21:04:31', NULL),
(653, NULL, 'Hello everyone', 'Hr', '2433', 'GENERAL', NULL, '2024-12-04 13:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(654, NULL, 'Hello everyone', 'Hr', '2434', 'GENERAL', NULL, '2024-12-04 13:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(655, NULL, 'Hello everyone', 'Hr', '2435', 'GENERAL', NULL, '2024-12-04 13:59:24', NULL, 1, NULL, NULL, NULL, '2024-12-04 21:05:45', NULL),
(656, NULL, 'Hello everyone', 'Hr', '2436', 'GENERAL', NULL, '2024-12-04 13:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(657, NULL, 'Hello everyone', 'Hr', '2501', 'GENERAL', NULL, '2024-12-04 13:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(658, NULL, 'Hello everyone', 'Hr', '1000', 'GENERAL', NULL, '2024-12-04 21:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(659, NULL, 'Hello everyone', 'Hr', '1002', 'GENERAL', NULL, '2024-12-04 21:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(660, NULL, 'Hello everyone', 'Hr', '1003', 'GENERAL', NULL, '2024-12-04 21:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(661, NULL, 'Hello everyone', 'Hr', '1004', 'GENERAL', NULL, '2024-12-04 21:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(662, NULL, 'Hello everyone', 'Hr', '1005', 'GENERAL', NULL, '2024-12-04 21:59:24', NULL, 1, NULL, NULL, NULL, '2024-12-07 15:15:52', NULL),
(663, NULL, 'Hello everyone', 'Hr', '1006', 'GENERAL', NULL, '2024-12-04 21:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(664, NULL, 'Hello everyone', 'Hr', '1008', 'GENERAL', NULL, '2024-12-04 21:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(665, NULL, 'Hello everyone', 'Hr', '1009', 'GENERAL', NULL, '2024-12-04 21:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(666, NULL, 'Hello everyone', 'Hr', '1010', 'GENERAL', NULL, '2024-12-04 21:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(667, NULL, 'Hello everyone', 'Hr', '1011', 'GENERAL', NULL, '2024-12-04 21:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(668, NULL, 'Hello everyone', 'Hr', '1012', 'GENERAL', NULL, '2024-12-04 21:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(669, NULL, 'Hello everyone', 'Hr', '1013', 'GENERAL', NULL, '2024-12-04 21:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(670, NULL, 'Hello everyone', 'Hr', '1014', 'GENERAL', NULL, '2024-12-04 21:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(671, NULL, 'Hello everyone', 'Hr', '1015', 'GENERAL', NULL, '2024-12-04 21:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(672, NULL, 'Hello everyone', 'Hr', '1016', 'GENERAL', NULL, '2024-12-04 21:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(673, NULL, 'Hello everyone', 'Hr', '1017', 'GENERAL', NULL, '2024-12-04 21:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(674, NULL, 'Hello everyone', 'Hr', '1018', 'GENERAL', NULL, '2024-12-04 21:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(675, NULL, 'Hello everyone', 'Hr', '1020', 'GENERAL', NULL, '2024-12-04 21:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(676, NULL, 'Hello everyone', 'Hr', '1021', 'GENERAL', NULL, '2024-12-04 21:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(677, NULL, 'Hello everyone', 'Hr', '1022', 'GENERAL', NULL, '2024-12-04 21:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(678, NULL, 'Hello everyone', 'Hr', '1086', 'GENERAL', NULL, '2024-12-04 21:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(679, NULL, 'Hello everyone', 'Hr', '2222', 'GENERAL', NULL, '2024-12-04 21:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(680, NULL, 'Hello everyone', 'Hr', '2431', 'GENERAL', NULL, '2024-12-04 21:59:24', NULL, 1, NULL, NULL, NULL, '2024-12-04 21:05:25', NULL),
(681, NULL, 'Hello everyone', 'Hr', '2433', 'GENERAL', NULL, '2024-12-04 21:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(682, NULL, 'Hello everyone', 'Hr', '2434', 'GENERAL', NULL, '2024-12-04 21:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(683, NULL, 'Hello everyone', 'Hr', '2435', 'GENERAL', NULL, '2024-12-04 21:59:24', NULL, 1, NULL, NULL, NULL, '2024-12-04 21:05:45', NULL),
(684, NULL, 'Hello everyone', 'Hr', '2436', 'GENERAL', NULL, '2024-12-04 21:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(685, NULL, 'Hello everyone', 'Hr', '2501', 'GENERAL', NULL, '2024-12-04 21:59:24', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(702, NULL, 'Welcome sokmeng on board', 'Human Resourse', '1000', 'GENERAL', NULL, '2024-12-07 15:00:04', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(703, NULL, 'Welcome sokmeng on board', 'Human Resourse', '1002', 'GENERAL', NULL, '2024-12-07 15:00:04', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(704, NULL, 'Welcome sokmeng on board', 'Human Resourse', '1003', 'GENERAL', NULL, '2024-12-07 15:00:04', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(705, NULL, 'Welcome sokmeng on board', 'Human Resourse', '1004', 'GENERAL', NULL, '2024-12-07 15:00:04', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(706, NULL, 'Welcome sokmeng on board', 'Human Resourse', '1005', 'GENERAL', NULL, '2024-12-07 15:00:04', NULL, 1, NULL, NULL, NULL, '2024-12-07 15:15:52', NULL),
(707, NULL, 'Welcome sokmeng on board', 'Human Resourse', '1006', 'GENERAL', NULL, '2024-12-07 15:00:04', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(708, NULL, 'Welcome sokmeng on board', 'Human Resourse', '1008', 'GENERAL', NULL, '2024-12-07 15:00:04', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(709, NULL, 'Welcome sokmeng on board', 'Human Resourse', '1009', 'GENERAL', NULL, '2024-12-07 15:00:04', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(710, NULL, 'Welcome sokmeng on board', 'Human Resourse', '1010', 'GENERAL', NULL, '2024-12-07 15:00:04', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(711, NULL, 'Welcome sokmeng on board', 'Human Resourse', '1011', 'GENERAL', NULL, '2024-12-07 15:00:04', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(712, NULL, 'Welcome sokmeng on board', 'Human Resourse', '1012', 'GENERAL', NULL, '2024-12-07 15:00:04', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(713, NULL, 'Welcome sokmeng on board', 'Human Resourse', '1013', 'GENERAL', NULL, '2024-12-07 15:00:04', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(714, NULL, 'Welcome sokmeng on board', 'Human Resourse', '1014', 'GENERAL', NULL, '2024-12-07 15:00:04', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(715, NULL, 'Welcome sokmeng on board', 'Human Resourse', '1015', 'GENERAL', NULL, '2024-12-07 15:00:04', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(716, NULL, 'Welcome sokmeng on board', 'Human Resourse', '1016', 'GENERAL', NULL, '2024-12-07 15:00:04', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(717, NULL, 'Welcome sokmeng on board', 'Human Resourse', '1017', 'GENERAL', NULL, '2024-12-07 15:00:04', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(718, NULL, 'Welcome sokmeng on board', 'Human Resourse', '1018', 'GENERAL', NULL, '2024-12-07 15:00:04', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(719, NULL, 'Welcome sokmeng on board', 'Human Resourse', '1020', 'GENERAL', NULL, '2024-12-07 15:00:04', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(720, NULL, 'Welcome sokmeng on board', 'Human Resourse', '1021', 'GENERAL', NULL, '2024-12-07 15:00:04', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(721, NULL, 'Welcome sokmeng on board', 'Human Resourse', '1022', 'GENERAL', NULL, '2024-12-07 15:00:04', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(722, NULL, 'Welcome sokmeng on board', 'Human Resourse', '1086', 'GENERAL', NULL, '2024-12-07 15:00:04', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(723, NULL, 'Welcome sokmeng on board', 'Human Resourse', '2222', 'GENERAL', NULL, '2024-12-07 15:00:04', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(724, NULL, 'Welcome sokmeng on board', 'Human Resourse', '2431', 'GENERAL', NULL, '2024-12-07 15:00:04', NULL, 1, NULL, NULL, NULL, '2024-12-07 15:01:57', NULL),
(725, NULL, 'Welcome sokmeng on board', 'Human Resourse', '2433', 'GENERAL', NULL, '2024-12-07 15:00:04', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(726, NULL, 'Welcome sokmeng on board', 'Human Resourse', '2434', 'GENERAL', NULL, '2024-12-07 15:00:04', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(727, NULL, 'Welcome sokmeng on board', 'Human Resourse', '2435', 'GENERAL', NULL, '2024-12-07 15:00:04', NULL, 1, NULL, NULL, NULL, '2024-12-07 15:02:42', NULL),
(728, NULL, 'Welcome sokmeng on board', 'Human Resourse', '2436', 'GENERAL', NULL, '2024-12-07 15:00:04', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(729, NULL, 'Welcome sokmeng on board', 'Human Resourse', '2501', 'GENERAL', NULL, '2024-12-07 15:00:04', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(752, NULL, 'Hello everyone!', 'Hr', '1000', 'GENERAL', NULL, '2024-12-14 10:36:23', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(753, NULL, 'Hello everyone!', 'Hr', '1002', 'GENERAL', NULL, '2024-12-14 10:36:23', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(754, NULL, 'Hello everyone!', 'Hr', '1003', 'GENERAL', NULL, '2024-12-14 10:36:23', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(755, NULL, 'Hello everyone!', 'Hr', '1004', 'GENERAL', NULL, '2024-12-14 10:36:23', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(756, NULL, 'Hello everyone!', 'Hr', '1005', 'GENERAL', NULL, '2024-12-14 10:36:23', NULL, 1, NULL, NULL, NULL, '2024-12-14 10:37:10', NULL),
(757, NULL, 'Hello everyone!', 'Hr', '1006', 'GENERAL', NULL, '2024-12-14 10:36:23', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(758, NULL, 'Hello everyone!', 'Hr', '1008', 'GENERAL', NULL, '2024-12-14 10:36:23', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(759, NULL, 'Hello everyone!', 'Hr', '1009', 'GENERAL', NULL, '2024-12-14 10:36:23', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(760, NULL, 'Hello everyone!', 'Hr', '1010', 'GENERAL', NULL, '2024-12-14 10:36:23', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(761, NULL, 'Hello everyone!', 'Hr', '1011', 'GENERAL', NULL, '2024-12-14 10:36:23', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(762, NULL, 'Hello everyone!', 'Hr', '1012', 'GENERAL', NULL, '2024-12-14 10:36:23', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(763, NULL, 'Hello everyone!', 'Hr', '1013', 'GENERAL', NULL, '2024-12-14 10:36:23', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(764, NULL, 'Hello everyone!', 'Hr', '1014', 'GENERAL', NULL, '2024-12-14 10:36:23', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(765, NULL, 'Hello everyone!', 'Hr', '1015', 'GENERAL', NULL, '2024-12-14 10:36:23', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(766, NULL, 'Hello everyone!', 'Hr', '1016', 'GENERAL', NULL, '2024-12-14 10:36:23', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(767, NULL, 'Hello everyone!', 'Hr', '1017', 'GENERAL', NULL, '2024-12-14 10:36:23', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(768, NULL, 'Hello everyone!', 'Hr', '1018', 'GENERAL', NULL, '2024-12-14 10:36:23', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(769, NULL, 'Hello everyone!', 'Hr', '1020', 'GENERAL', NULL, '2024-12-14 10:36:23', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(770, NULL, 'Hello everyone!', 'Hr', '1021', 'GENERAL', NULL, '2024-12-14 10:36:23', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(771, NULL, 'Hello everyone!', 'Hr', '1022', 'GENERAL', NULL, '2024-12-14 10:36:23', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(772, NULL, 'Hello everyone!', 'Hr', '1086', 'GENERAL', NULL, '2024-12-14 10:36:23', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(773, NULL, 'Hello everyone!', 'Hr', '2222', 'GENERAL', NULL, '2024-12-14 10:36:23', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(774, NULL, 'Hello everyone!', 'Hr', '2431', 'GENERAL', NULL, '2024-12-14 10:36:23', NULL, 1, NULL, NULL, NULL, '2024-12-16 10:36:55', NULL),
(775, NULL, 'Hello everyone!', 'Hr', '2433', 'GENERAL', NULL, '2024-12-14 10:36:23', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(776, NULL, 'Hello everyone!', 'Hr', '2434', 'GENERAL', NULL, '2024-12-14 10:36:23', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(777, NULL, 'Hello everyone!', 'Hr', '2435', 'GENERAL', NULL, '2024-12-14 10:36:23', NULL, 1, NULL, NULL, NULL, '2024-12-21 10:00:38', NULL),
(778, NULL, 'Hello everyone!', 'Hr', '2436', 'GENERAL', NULL, '2024-12-14 10:36:23', NULL, 0, NULL, NULL, NULL, NULL, NULL),
(779, NULL, 'Hello everyone!', 'Hr', '2501', 'GENERAL', NULL, '2024-12-14 10:36:23', NULL, 0, NULL, NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `message_SEQ`
--

CREATE TABLE `message_SEQ` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `message_SEQ`
--

INSERT INTO `message_SEQ` (`next_val`) VALUES
(851);

-- --------------------------------------------------------

--
-- Table structure for table `user_notifications`
--

CREATE TABLE `user_notifications` (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `message_id` int(11) NOT NULL,
  `is_read` tinyint(1) DEFAULT 0,
  `read_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `user_notifications_SEQ`
--

CREATE TABLE `user_notifications_SEQ` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `user_notifications_SEQ`
--

INSERT INTO `user_notifications_SEQ` (`next_val`) VALUES
(101);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `message`
--
ALTER TABLE `message`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_notifications`
--
ALTER TABLE `user_notifications`
  ADD PRIMARY KEY (`id`),
  ADD KEY `message_id` (`message_id`),
  ADD KEY `user_id` (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `message`
--
ALTER TABLE `message`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=780;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `user_notifications`
--
ALTER TABLE `user_notifications`
  ADD CONSTRAINT `user_notifications_ibfk_1` FOREIGN KEY (`message_id`) REFERENCES `message` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
