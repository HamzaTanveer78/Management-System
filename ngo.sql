-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 21, 2017 at 06:19 PM
-- Server version: 5.7.14
-- PHP Version: 5.6.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ngo`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbl_accounts`
--

CREATE TABLE `tbl_accounts` (
  `account_id` int(11) NOT NULL,
  `account_title` longtext NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbl_accounts`
--

INSERT INTO `tbl_accounts` (`account_id`, `account_title`) VALUES
(1, 'Event Management Expenses'),
(2, 'Office Expenses'),
(3, 'Hot and Cold'),
(4, 'Purchase'),
(5, 'Electricity'),
(6, 'Internet'),
(7, 'Oil and Gas'),
(8, 'Fund Raising Program');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_appointments`
--

CREATE TABLE `tbl_appointments` (
  `app_id` int(11) NOT NULL,
  `app_date` longtext NOT NULL,
  `app_time` longtext NOT NULL,
  `app_agenda` longtext NOT NULL,
  `contact_person` longtext NOT NULL,
  `contact_phone` longtext NOT NULL,
  `contact_email` longtext NOT NULL,
  `reminder_date` longtext NOT NULL,
  `reminder_status` int(11) NOT NULL DEFAULT '0'
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_donations`
--

CREATE TABLE `tbl_donations` (
  `entery_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `amount` longtext,
  `date_time` longtext,
  `txn_id` longtext,
  `donation_date` longtext,
  `donation_mode` longtext COMMENT 'Paypal, Cash, Cheque',
  `fr_id` longtext,
  `approved_status` int(11) NOT NULL DEFAULT '0'
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_expenses`
--

CREATE TABLE `tbl_expenses` (
  `expense_id` int(11) NOT NULL,
  `account_id` int(11) NOT NULL,
  `expense_detail` longtext NOT NULL,
  `expense_amount` int(11) NOT NULL,
  `expense_date` longtext NOT NULL,
  `expense_entery_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_frp`
--

CREATE TABLE `tbl_frp` (
  `fr_id` int(11) NOT NULL,
  `fr_title` longtext NOT NULL,
  `fr_banner` longtext,
  `fr_detail` longtext NOT NULL,
  `publish_status` int(11) NOT NULL DEFAULT '0'
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_manager`
--

CREATE TABLE `tbl_manager` (
  `id` int(11) NOT NULL,
  `manager_id` longtext NOT NULL,
  `manager_password` longtext NOT NULL,
  `manager_email` longtext NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbl_manager`
--

INSERT INTO `tbl_manager` (`id`, `manager_id`, `manager_password`, `manager_email`) VALUES
(1, 'admin', '0192023a7bbd73250516f069df18b500', 'mirzababarhussain@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_registration`
--

CREATE TABLE `tbl_registration` (
  `user_id` int(11) NOT NULL,
  `user_type` varchar(25) NOT NULL,
  `first_name` longtext,
  `last_name` longtext,
  `user_email` longtext,
  `cell_number` longtext,
  `postal_address` longtext,
  `user_name` longtext,
  `user_password` longtext,
  `reg_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `password_request` int(11) NOT NULL DEFAULT '0',
  `disable_status` int(11) NOT NULL DEFAULT '0'
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_tangible_dontation`
--

CREATE TABLE `tbl_tangible_dontation` (
  `tangible_id` int(11) NOT NULL,
  `donated_by` int(11) NOT NULL,
  `donation_time` longtext,
  `item` longtext,
  `desc_item` longtext,
  `address` longtext,
  `contact_person` longtext,
  `contact_phone` longtext,
  `assign_to_volunteer` int(11) DEFAULT NULL,
  `collected_status` int(11) DEFAULT NULL,
  `collected_date` longtext,
  `collected_by_admin` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_tasks`
--

CREATE TABLE `tbl_tasks` (
  `task_id` int(11) NOT NULL,
  `task_date` longtext NOT NULL,
  `task_detail` longtext NOT NULL,
  `reminder_date` longtext NOT NULL,
  `complete_status` int(11) NOT NULL DEFAULT '0',
  `reminder_status` int(11) NOT NULL DEFAULT '0',
  `task_for` longtext NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tbl_accounts`
--
ALTER TABLE `tbl_accounts`
  ADD PRIMARY KEY (`account_id`);

--
-- Indexes for table `tbl_appointments`
--
ALTER TABLE `tbl_appointments`
  ADD PRIMARY KEY (`app_id`);

--
-- Indexes for table `tbl_donations`
--
ALTER TABLE `tbl_donations`
  ADD PRIMARY KEY (`entery_id`);

--
-- Indexes for table `tbl_expenses`
--
ALTER TABLE `tbl_expenses`
  ADD PRIMARY KEY (`expense_id`);

--
-- Indexes for table `tbl_frp`
--
ALTER TABLE `tbl_frp`
  ADD PRIMARY KEY (`fr_id`);

--
-- Indexes for table `tbl_manager`
--
ALTER TABLE `tbl_manager`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_registration`
--
ALTER TABLE `tbl_registration`
  ADD PRIMARY KEY (`user_id`);

--
-- Indexes for table `tbl_tangible_dontation`
--
ALTER TABLE `tbl_tangible_dontation`
  ADD PRIMARY KEY (`tangible_id`);

--
-- Indexes for table `tbl_tasks`
--
ALTER TABLE `tbl_tasks`
  ADD PRIMARY KEY (`task_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tbl_accounts`
--
ALTER TABLE `tbl_accounts`
  MODIFY `account_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT for table `tbl_appointments`
--
ALTER TABLE `tbl_appointments`
  MODIFY `app_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `tbl_donations`
--
ALTER TABLE `tbl_donations`
  MODIFY `entery_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `tbl_expenses`
--
ALTER TABLE `tbl_expenses`
  MODIFY `expense_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `tbl_frp`
--
ALTER TABLE `tbl_frp`
  MODIFY `fr_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `tbl_manager`
--
ALTER TABLE `tbl_manager`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `tbl_registration`
--
ALTER TABLE `tbl_registration`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `tbl_tangible_dontation`
--
ALTER TABLE `tbl_tangible_dontation`
  MODIFY `tangible_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `tbl_tasks`
--
ALTER TABLE `tbl_tasks`
  MODIFY `task_id` int(11) NOT NULL AUTO_INCREMENT;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
