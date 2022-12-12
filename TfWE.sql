create database TfWE;

use TfWE;

 -- Customer 

create table Customer(
userId int auto_increment primary key,
userName varchar(40) not null,
userPassword varchar(10) not null,
userAddress varchar(40) not null,
userEmail varchar(40) not null,
userPhone char(11) not null,
cardBalance double);

describe Customer;

insert into Customer(userName, userPassword, userAddress, userEmail, userPhone, cardBalance)
values("John Doe", "Password1", "Address1", "j.doe@gmail.com", "07877878761", 100),
("Alice Smith", "Password2", "Address2", "a.smithe@gmail.com", "07877878762", 100),
("Barry Helm", "Password3", "Address3", "b.helm@gmail.com", "07877878763", 100),
("Steph Greg", "Password4", "Address4", "s.greg@gmail.com", "07877878764", 100);

select * from Customer;

 -- Stations 

create table Station(
stationId int primary key,
stationName varchar(20) not null);

describe Station;

insert into Station(stationId, stationName)
values(01, "L1"),
(02, "L2");

select * from Station;

-- Transaction

create table Transaction(
transactionId int auto_increment primary key,
swipeIn datetime,
swipeInStationId varchar(20),
swipeOut datetime,
swipeOutStationId varchar(20),
fareCost double not null);

describe Transaction;

insert into Transaction(swipeIn,swipeInStationId,swipeOut,swipeOutStationId,fareCost)
values("2022-12-07 14:06:30", "L1", "2022-12-07 15:10:30", "L2",5.00), 
("2022-12-07 05:06:30", "L1", "2022-12-07 12:10:30", "L3",10.00);

select * from Transaction;