-- phpMyAdmin SQL Dump
-- version 4.1.6
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Aug 05, 2023 at 07:01 AM
-- Server version: 5.6.16
-- PHP Version: 5.5.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `waste_management`
--

-- --------------------------------------------------------

--
-- Table structure for table `account_tbl`
--

CREATE TABLE IF NOT EXISTS `account_tbl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` varchar(50) NOT NULL,
  `accname` varchar(100) NOT NULL,
  `accountno` varchar(100) NOT NULL,
  `bank` varchar(100) NOT NULL,
  `pin` varchar(100) NOT NULL,
  `amount` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `account_tbl`
--

INSERT INTO `account_tbl` (`id`, `uid`, `accname`, `accountno`, `bank`, `pin`, `amount`) VALUES
(1, '1', 'Anandu', '12345678', 'SBI', '12345', '36155'),
(2, '2', 'Meenakshi', '22334455', 'federal', '2580', '494506'),
(3, '3', 'cheriyan', '1234567', 'federal', '1245', '500000');

-- --------------------------------------------------------

--
-- Table structure for table `bins_tbl`
--

CREATE TABLE IF NOT EXISTS `bins_tbl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `image` varchar(100) NOT NULL,
  `description` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `bins_tbl`
--

INSERT INTO `bins_tbl` (`id`, `name`, `image`, `description`) VALUES
(1, 'Wet Waste', 'wetwaste.png', 'Refers to all items that are organic like food items, soiled food wrappers, hygiene products, yard waste, tissues and paper towels, as well as any other soiled item that would contaminate the recyclabes'),
(2, 'Dry Waste', 'drywaste.png', 'Refers to all items that are not considered wet/soiled items. This includes both recyclable and non-recyclable materials. Dry waste includes items such as bottles, cans, clothing, plastic, wood, glass, metals and paper.');

-- --------------------------------------------------------

--
-- Table structure for table `campaigns_tbl`
--

CREATE TABLE IF NOT EXISTS `campaigns_tbl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `image` varchar(255) NOT NULL,
  `description` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `campaigns_tbl`
--

INSERT INTO `campaigns_tbl` (`id`, `title`, `image`, `description`) VALUES
(1, 'SOLID WASTE MANAGEMENT', 'solidwaste.jpg', 'Solid Waste Management is used to describe the wastes like the solid and semi-solid materials that are discarded by a community. The solid waste generated through various domestic, commercial, constructional and industrial activities. The safe disposal of the solid waste was not a problem until the population is very small but as the population is increasing day by day then solid waste management had become the issue of threatening to human lives and also the management of this waste had become difficult in modern times.'),
(2, 'REDUCE REUSE RECYCLE', 'reduce.jpg', 'Reduce, Reuse, Recycle â€“ these three ''R'' words are an important part of sustainable living, as they help to cut down on the amount of waste we have to throw away.\r\n\r\nIt''s Really simple!\r\n\r\nReduce the amount of waste you produce.\r\nReuse items as much as you can before replacing them.\r\nRecycle items wherever possible.'),
(3, 'Plastic Waste Management Campaign', 'plastic.jpg', 'Plastic waste in our oceans and seas is a growing problem, affecting wildlife, ecosystems and people. About 10 million tonnes of litter â€“ 80% of it plastic â€“ finds its way into the water each year. These products take hundreds of years to decompose in the sea.\r\nSingle-use plastics, like carrier bags, drinks bottles, coffee cups and packaging, are a big part of the issue. Cheap and disposable, they are carried to the oceans from rivers and coastal towns and cities, or simply dumped at sea.');

-- --------------------------------------------------------

--
-- Table structure for table `community_chat_tbl`
--

CREATE TABLE IF NOT EXISTS `community_chat_tbl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` varchar(50) NOT NULL,
  `cid` varchar(50) NOT NULL,
  `message` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=30 ;

--
-- Dumping data for table `community_chat_tbl`
--

INSERT INTO `community_chat_tbl` (`id`, `uid`, `cid`, `message`) VALUES
(1, '1', '1', 'hy'),
(2, '2', '1', 'hlo'),
(3, '1', '1', 'how r u'),
(4, '1', '2', 'hyy'),
(5, '1', '2', 'group 2'),
(7, '1', '2', 'test'),
(8, '1', '2', 'testing'),
(9, '1', '1', 'hy'),
(10, '1', '1', 'hy'),
(11, '1', '2', 'hy'),
(12, '2', '1', 'hello'),
(13, '1', '1', 'testing'),
(14, '2', '1', 'hlo'),
(15, '1', '1', 'test'),
(16, '1', '1', 'hy'),
(17, '2', '1', 'test'),
(18, '1', '1', 'reached'),
(19, '2', '1', 'hy'),
(20, '1', '1', 'hlo'),
(21, '1', '1', 'hlo'),
(22, '1', '1', 'good'),
(23, '3', '1', 'hlo'),
(24, '1', '1', 'Learn how to build an app to manage food waste. Explore this app idea, features, process to develop '),
(25, '2', '2', 'Explore this app idea, features, process to develop '),
(26, '1', '1', 'hy'),
(27, '2', '1', 'new'),
(28, '1', '2', 'ok'),
(29, '1', '2', 'ok');

-- --------------------------------------------------------

--
-- Table structure for table `community_tbl`
--

CREATE TABLE IF NOT EXISTS `community_tbl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `image` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `community_tbl`
--

INSERT INTO `community_tbl` (`id`, `title`, `image`) VALUES
(1, 'Zero Waste Foundation', 'zerowaste.png'),
(2, 'Clean City', 'city.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `feedback_tbl`
--

CREATE TABLE IF NOT EXISTS `feedback_tbl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` varchar(100) NOT NULL,
  `worker_name` varchar(100) NOT NULL,
  `rating` varchar(100) NOT NULL,
  `feedback` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `feedback_tbl`
--

INSERT INTO `feedback_tbl` (`id`, `uid`, `worker_name`, `rating`, `feedback`) VALUES
(1, '1', 'Rahul', '5.0', 'good TextFree is a mobile application and web service that allows users to send and receive text mes'),
(2, '1', 'Cheriyan', '3', 'gud'),
(3, '2', 'Rahul', '4.0', 'I think you did a great job when you ran the all-hands meeting. It showed that you are capable of getting people to work together and communicate effectively. I admire your communication skills.'),
(4, '2', 'Cheriyan', '2.0', 'The service was too late and not so happy with itðŸ˜');

-- --------------------------------------------------------

--
-- Table structure for table `history_tbl`
--

CREATE TABLE IF NOT EXISTS `history_tbl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` varchar(100) NOT NULL,
  `pid` varchar(100) NOT NULL,
  `price` varchar(100) NOT NULL,
  `date` varchar(100) NOT NULL,
  `orderid` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=12 ;

--
-- Dumping data for table `history_tbl`
--

INSERT INTO `history_tbl` (`id`, `uid`, `pid`, `price`, `date`, `orderid`) VALUES
(1, '1', '1', '371', '2023-07-12', '149200'),
(2, '1', '2', '1200', '2023-07-12', '781050'),
(3, '1', '1', '371', '2023-07-12', '988926'),
(4, '1', '2', '1200', '2023-07-12', '332681'),
(5, '1', '4', '2999', '2023-07-13', '187720'),
(6, '1', '1', '371', '2023-07-24', '297975'),
(7, '1', '4', '2999', '2023-07-28', '396393'),
(8, '1', '3', '2495', '2023-07-28', '152141'),
(9, '1', '2', '1200', '2023-07-28', '747532'),
(10, '1', '4', '2999', '2023-08-01', '975720'),
(11, '1', '3', '2495', '2023-08-01', '401041');

-- --------------------------------------------------------

--
-- Table structure for table `points_tbl`
--

CREATE TABLE IF NOT EXISTS `points_tbl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` varchar(100) NOT NULL,
  `points` varchar(100) NOT NULL,
  `description` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=15 ;

--
-- Dumping data for table `points_tbl`
--

INSERT INTO `points_tbl` (`id`, `uid`, `points`, `description`) VALUES
(1, '1', '25', 'waste reporting'),
(2, '1', '25', 'waste reporting'),
(3, '1', '25', 'waste reporting'),
(4, '1', '25', 'waste reporting'),
(5, '1', '25', 'waste reporting'),
(6, '1', '10', 'Joined a Campaign'),
(7, '1', '25', 'waste reporting'),
(8, '1', '10', 'Joined a Campaign'),
(9, '2', '10', 'Joined a Campaign'),
(10, '1', '10', 'Joined a Campaign'),
(11, '1', '25', 'waste reported'),
(12, '1', '10', 'Joined a Campaign'),
(13, '1', '10', 'Joined a Campaign'),
(14, '1', '10', 'Joined a Campaign');

-- --------------------------------------------------------

--
-- Table structure for table `products_tbl`
--

CREATE TABLE IF NOT EXISTS `products_tbl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` varchar(50) NOT NULL,
  `pname` varchar(100) NOT NULL,
  `description` text NOT NULL,
  `image` varchar(100) NOT NULL,
  `price` varchar(100) NOT NULL,
  `status` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `products_tbl`
--

INSERT INTO `products_tbl` (`id`, `uid`, `pname`, `description`, `image`, `price`, `status`) VALUES
(1, '1', 'Multi Purpose Dustbins', 'Mini Dustbin -For Car, Home or office - Blue and Red (Pack of 2)', 'dustbin.jpg', '371', 'available'),
(2, '1', 'Boat Rockerz 450', 'Headphones with Mic, Upto 15 Hours Playback, 40MM Drivers, Padded Ear Cushions, Integrated Controls ', 'rockers.jpg', '1200', 'available'),
(3, '2', 'FOSSIL Grant Analog Watch ', 'Keep track of your meetings in style with this vintage-looking timepiece.\r\nWater Resistant   Yes\r\nDisplay Type   Analog\r\nStyle Code  FS4735\r\nSeries   Grant\r\nOccasion  Casual', 'fossil.png', '2495', 'available'),
(4, '2', 'Kelp Lunar Daypack', 'Deskbound or travel-bound, Lunar is an all-time essential daypack, designed to meet most of your carry requirements with fitting storage solutions in a nice compact backpack. Made from 14 recycled PET bottles, the bag features high-utility, seamless functioning and clean aesthetic that lets you experience synergy with the world around you as you go about your day.', 'lunar.png', '2999', 'available');

-- --------------------------------------------------------

--
-- Table structure for table `publicbins_tbl`
--

CREATE TABLE IF NOT EXISTS `publicbins_tbl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `location` varchar(100) NOT NULL,
  `latitude` varchar(100) NOT NULL,
  `longitude` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `publicbins_tbl`
--

INSERT INTO `publicbins_tbl` (`id`, `location`, `latitude`, `longitude`) VALUES
(1, 'Palarivattom', '10.0052248', '76.3029858'),
(2, 'Lulu Edapally', '10.0279384', '76.2707902'),
(3, 'Kaloor', '9.9964561', '76.2982894');

-- --------------------------------------------------------

--
-- Table structure for table `report_tbl`
--

CREATE TABLE IF NOT EXISTS `report_tbl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` varchar(100) NOT NULL,
  `image` tinytext NOT NULL,
  `description` varchar(100) NOT NULL,
  `latitude` varchar(100) NOT NULL,
  `longitude` varchar(100) NOT NULL,
  `date` varchar(100) NOT NULL,
  `status` varchar(100) NOT NULL,
  `worker_id` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `report_tbl`
--

INSERT INTO `report_tbl` (`id`, `uid`, `image`, `description`, `latitude`, `longitude`, `date`, `status`, `worker_id`) VALUES
(1, '1', 'image1.jpg', 'need help', '10.00584767896585', '76.30512692034245', '2023-07-12', 'cleared', '1'),
(2, '1', 'image2.jpg', 'bin is fulll', '10.0068009', '76.304421', '2023-07-14', 'cleared', '1'),
(3, '1', 'image3.jpg', 'empty can', '10.006931666666667', '76.30469833333333', '2023-07-24', 'cleared', '2');

-- --------------------------------------------------------

--
-- Table structure for table `request_tbl`
--

CREATE TABLE IF NOT EXISTS `request_tbl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` varchar(50) NOT NULL,
  `date` varchar(100) NOT NULL,
  `time` varchar(100) NOT NULL,
  `requested_date` varchar(100) NOT NULL,
  `status` varchar(100) NOT NULL,
  `wid` varchar(50) NOT NULL,
  `amount` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `request_tbl`
--

INSERT INTO `request_tbl` (`id`, `uid`, `date`, `time`, `requested_date`, `status`, `wid`, `amount`) VALUES
(1, '1', '4-8-2023', '3 PM - 6 PM', '4-8-2023', 'paid', '1', '200'),
(2, '1', '01-08-23', '12 PM - 3 PM', '5-8-2023', 'paid', '1', '650'),
(3, '2', '03-08-23', '3 PM - 6 PM', '5-8-2023', 'accepted', '1', ''),
(4, '1', '03-08-23', '12 PM - 3 PM', '13-8-2023', 'requested', '', ''),
(5, '1', '05-08-23', '12 PM - 3 PM', '7-8-2023', 'paid', '1', '500');

-- --------------------------------------------------------

--
-- Table structure for table `tips_tbl`
--

CREATE TABLE IF NOT EXISTS `tips_tbl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `description` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

--
-- Dumping data for table `tips_tbl`
--

INSERT INTO `tips_tbl` (`id`, `title`, `description`) VALUES
(1, 'Hire Waste Management Professionals', 'Hire a professional rubbish removal company to handle waste management. They will handle the waste from your business in an environmentally responsible manner.'),
(2, 'Store Your Commercial Waste Securely', 'Store your business waste securely in a proper container, and suitable covers must be used to protect the waste from weather conditions such as rain and wind.'),
(3, 'Monitor and Reduce the Waste You Generate', 'Monitor and evaluate your property''s waste levels. Track the amount of waste disposed of to determine where you are producing.'),
(4, 'Create an Internal Waste Management Team', 'Creating an internal waste management team is one of the best ways to reduce the amount of waste on your business property.'),
(5, 'Reuse', 'Reuse is one of the most effective waste management solutions. For example, contribute your employees'' food to local homeless shelters or other charitable organisations, and donate unneeded things to donation centres.'),
(6, 'Reduce', 'We live in the current corporate environment, particularly in this age of internet\r\ncommunication. So, avoid utilising printing\r\nmaterials by becoming paperless.'),
(7, 'Recycle', 'Recycling is an important part of our daily lives and our planet. If you pay attention to recycling, you will help to create new and useful products.');

-- --------------------------------------------------------

--
-- Table structure for table `user_campaign_tbl`
--

CREATE TABLE IF NOT EXISTS `user_campaign_tbl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` varchar(50) NOT NULL,
  `cid` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=10 ;

--
-- Dumping data for table `user_campaign_tbl`
--

INSERT INTO `user_campaign_tbl` (`id`, `uid`, `cid`) VALUES
(1, '1', '1'),
(2, '1', '2'),
(3, '1', '1'),
(4, '1', '3'),
(5, '2', '1'),
(6, '1', '3'),
(7, '1', '3'),
(8, '1', '1'),
(9, '1', '1');

-- --------------------------------------------------------

--
-- Table structure for table `user_community_tbl`
--

CREATE TABLE IF NOT EXISTS `user_community_tbl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` varchar(50) NOT NULL,
  `community_id` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

--
-- Dumping data for table `user_community_tbl`
--

INSERT INTO `user_community_tbl` (`id`, `uid`, `community_id`) VALUES
(5, '1', '2'),
(6, '1', '1'),
(7, '2', '1'),
(8, '3', '1');

-- --------------------------------------------------------

--
-- Table structure for table `user_tbl`
--

CREATE TABLE IF NOT EXISTS `user_tbl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `address` varchar(100) NOT NULL,
  `location` varchar(100) NOT NULL,
  `number` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `user_tbl`
--

INSERT INTO `user_tbl` (`id`, `name`, `address`, `location`, `number`, `email`, `username`, `password`) VALUES
(1, 'Anandu', 'Alappuzha', 'Alappuzha', '8943409211', 'ananduajithkumar123@gmail.com', 'anandu', '555'),
(2, 'Fayiz', 'Palarivattom', 'Ernakulam', '7538400258', 'fz@gmail.com', 'fayiz', '777'),
(3, 'cheriyan', 'ekm', 'ernakulam', '7885236908', 'cheriyan@gmail.com', 'cheriyan', '6666');

-- --------------------------------------------------------

--
-- Table structure for table `worker_tbl`
--

CREATE TABLE IF NOT EXISTS `worker_tbl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `address` varchar(100) NOT NULL,
  `number` varchar(100) NOT NULL,
  `work_type` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `worker_tbl`
--

INSERT INTO `worker_tbl` (`id`, `name`, `address`, `number`, `work_type`, `email`, `password`) VALUES
(1, 'Rahul', 'Ernakulam', '7558520256', 'Food', 'rahul@gmail.com', '777'),
(2, 'Cheriyan', 'palarivattom', '7558822033', 'Scrap', 'cheriyan@gmail.com', '8520');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
