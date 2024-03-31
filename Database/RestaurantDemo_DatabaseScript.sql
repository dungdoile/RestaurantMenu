DROP DATABASE RestaurantDemo;
CREATE DATABASE RestaurantDemo;
USE RestaurantDemo;

BEGIN TRANSACTION;
BEGIN TRY

CREATE TABLE [DishTypes]
(
  DishTypeID INT IDENTITY(1,1),
  DishTypeName NVARCHAR(50) NOT NULL,
  PRIMARY KEY (DishTypeID)
);

CREATE TABLE [Customers]
(
  CustomerPhoneNumber VARCHAR(10),
  CustomerName NVARCHAR(50) NOT NULL
  PRIMARY KEY (CustomerPhoneNumber)
);

CREATE TABLE [Dishes]
(
  DishID INT IDENTITY(1,1),
  DishName NVARCHAR(50) NOT NULL,
  Description NVARCHAR(200) NOT NULL,
  Price FLOAT NOT NULL,
  ImageLink NVARCHAR(100) DEFAULT NULL,
  DishTypeID INT NOT NULL,
  PRIMARY KEY (DishID),
  FOREIGN KEY (DishTypeID) REFERENCES [DishTypes](DishTypeID) ON DELETE CASCADE
);

CREATE TABLE [Employees]
(
  EmployeeID INT IDENTITY(1,1),
  EmployeeName NVARCHAR(50) NOT NULL,
  EmployeePhoneNumber VARCHAR(10) NOT NULL UNIQUE,
  EmployeeEmail VARCHAR(20) NOT NULL UNIQUE,
  LoginPassword VARCHAR(64) NOT NULL,
  Position VARCHAR(10),
  PRIMARY KEY (EmployeeID)
);

CREATE TABLE [Invoices]
(
  InvoiceID INT IDENTITY(1,1),
  CustomerPhoneNumber VARCHAR(10),
  DateTimeCreated DATETIME NOT NULL,
  GrossPrice FLOAT NOT NULL,
  Discount FLOAT NOT NULL,
  VAT FLOAT NOT NULL,
  NetPrice AS (GrossPrice + (GrossPrice*VAT/100) - (GrossPrice*Discount/100)),
  PaymentMethod VARCHAR(20),
  PRIMARY KEY (InvoiceID),
  FOREIGN KEY (CustomerPhoneNumber) REFERENCES [Customers](CustomerPhoneNumber)
  ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE [InvoiceDetails]
(
  InvoiceDetailID INT IDENTITY(1,1),
  Quantity INT NOT NULL,
  InvoiceID INT NOT NULL,
  DishID INT NOT NULL,
  PRIMARY KEY (InvoiceDetailID),
  FOREIGN KEY (InvoiceID) REFERENCES [Invoices](InvoiceID) ON DELETE CASCADE,
  FOREIGN KEY (DishID) REFERENCES [Dishes](DishID) ON DELETE CASCADE
);

CREATE TABLE [Tables]
(
  TableID INT IDENTITY(1,1),
  TableCapacity INT,
  CustomerPhoneNumber VARCHAR(10) DEFAULT NULL,
  DateTimeUsed DATETIME DEFAULT NULL,
  InvoiceRequest BIT DEFAULT 0, --0: not requested, 1: requested
  PRIMARY KEY (TableID),
  FOREIGN KEY (CustomerPhoneNumber) REFERENCES [Customers](CustomerPhoneNumber)
);

CREATE TABLE [DineInOrders] (
    TableID INT NOT NULL,
    DishID INT NOT NULL,
    Quantity INT NOT NULL,
    DateTimeOrdered DATETIME NOT NULL,
	ServiceStatus NVARCHAR(20) NOT NULL DEFAULT 'Pending', --Pending, Cooking, Serving, Served, Halted
	Rush BIT DEFAULT 0, -- 0: not rushed, 1: rushed
    FOREIGN KEY (DishID) REFERENCES Dishes(DishID),
    FOREIGN KEY (TableID) REFERENCES [Tables](TableID)
);

COMMIT TRANSACTION;
END TRY

BEGIN CATCH
ROLLBACK TRANSACTION;
PRINT ERROR_MESSAGE();
END CATCH;

BEGIN TRANSACTION;
BEGIN TRY

-- Insert data into DishTypes
INSERT INTO [DishTypes] (DishTypeName) VALUES
(N'Khai vị'),
(N'Khai vị : Súp'),
(N'Món chính : Món mặn'),
(N'Món chính : Món rau'),
(N'Món chính : Món canh'),
(N'Món chính: nếp, gạo'),
(N'Lẩu'),
(N'Tráng miệng'),
(N'Drinks');

-- Insert data into Dishes
INSERT INTO [Dishes] (DishTypeID, DishName, Description, Price, ImageLink) 
VALUES (1, N'Ba miền', N'Set cuốn tổng hợp 3 miền', 155, 'assets/images/Settonghop.PNG'),
(1, N'Đậu hũ non chiên giòn', N'Đậu hũ non,...', 65, 'assets/images/dauhunonchien.PNG'),
(1, N'Bánh tráng cuốn thịt heo', N'Thịt ba chỉ nướng, giò luộc, rau tổng hợp,...', 105 , 'assets/images/banhtrangcuonthit.PNG'),
(1, N'Gỏi tai heo', N'Tai heo, rau củ, măng', 75,'assets/images/goitaiheo.jpg'),
(1, N'Gỏi tôm thịt', N'Tôm, thịt heo, rau củ', 95,'assets/images/goitomthit.PNG'),
(1, N'Salad bắp bò', N'Bò, rau mầm', 105,'assets/images/saladbapbo.PNG'),
(1, N'Bò ngâm giấm', N'Bò , giấm, tiêu, ớt', 95,'assets/images/bongamgiam.PNG'),
(1, N'Bánh bèo chén', N'Bột gạo, tôm khô, tóp mỡ', 55,'assets/images/banhbeochen.PNG'),
(1, N'Bánh lọc', N'Thịt, tôm, mộc nhĩ, măng, bột năng,...', 55,'assets/images/banhloc.PNG'),
(1, N'Bánh xèo', N'Tôm, giá, thịt băm, nước cốt dừa,...', 85,'assets/images/banhxeo.PNG'),
(1, N'Hoa chuối xào me', N'Hoa chuối, me,...', 55,'assets/images/hoachuoixaome.jpg'),
(1, N'Cá chép chiên giòn', N'Cá chép', 75,'assets/images/cachepgion.PNG'),
(1, N'Bánh tôm thịt', N'Tôm, thịt, bột gạo,...', 65,'assets/images/banhtomthit.PNG'),
(2, N'Súp bào ngư thảo mộc', N'Bào ngư, tuyết yến, thảo mộc tổng hợp,...', 95,'assets/images/supbaonguthaomoc.PNG'),
(2, N'Súp bí ngô', N'Bí ngô, sữa, bánh mì,...', 75,'assets/images/supbingo.PNG'),
(2, N'Súp hải sản', N'Cua, tôm, trứng bắc thảo, trứng cút,...', 85,'assets/images/supcua.png'),
(3, N'Cá diếc kho tương', N'Cá diếc, tương bần, nước dừa,...', 105,'assets/images/cadieckhotuong.jpg'),
(3, N'Cá rô kho khế', N'Cá rô, khế,...', 95,'assets/images/carokhokhe.jpg'),
(3, N'Chả ốc đồng', N'Ốc đồng, giò sống,...', 95,'assets/images/chaocdong.jpg'),
(3, N'Chạch đồng kho tiêu', N'Chạch đồng, thịt ba chỉ,...', 95,'assets/images/chachdongkhotieu.jpg'),
(3, N'Thịt đông', N'Thịt ba chỉ, tai mũi heo,...', 85,'assets/images/thitdong.jpg'),
(3, N'Cà tím om', N'Cá tím,...', 65,'assets/images/catimom.jpg'),
(3, N'Gà nướng gừng', N'Gà, gừng,...', 105,'assets/images/ganuonggung.png'),
(3, N'Chả cá lã vọng', N'Chả cá, thì là,...', 95,'assets/images/chacalavong.PNG'),
(3, N'Gà nướng mắc mật', N'Gà, lá mắc mật,...', 105,'assets/images/ganuongmacmat.PNG'),
(3, N'Giả cầy', N'Giò heo,...', 95,'assets/images/giacay.jpg'),
(3, N'Ốc chuối đậu', N'Ốc, chuối, đậu, mắm tôm,...', 65,'assets/images/occhuoidau.jpg'),
(3, N'Thịt xào dưa chua', N'Thịt ba chỉ, cà chua, dưa muối,...', 95,'assets/images/thitxaoduachua.jpg'),
(3, N'Thịt kho tàu', N'Thịt ba chỉ, trứng cút,...', 105,'assets/images/thitkhotau.png'),
(4, N'Mướp luộc', N'Mướp', 45,'assets/images/muopluoc.png'),
(4, N'Mướp xào lòng gà', N'Mướp, lòng gà,...', 85,'assets/images/muopxaolongga.png'),
(4, N'Rau muống luộc', N'Rau muống', 45,'assets/images/raumuongluoc.png'),
(4, N'Rau muống xào', N'Rau muống, tóp mỡ,...', 65,'assets/images/raumuongxao.png'),
(4, N'Đậu que xào', N'Đậu que', 45,'assets/images/dauquexao.png'),
(5, N'Gà hầm thảo mộc', N'Gà, nấm hương, cải chíp, gừng', 105,'assets/images/gahamthaomoc.jpg'),
(5, N'Canh cá nấu dưa', N'Cá, dưa chua,...', 95,'assets/images/canhcanaudua.jpg'),
(5, N'Canh bầu nấu tôm', N'Bầu, tôm nõn,...', 95,'assets/images/canhbaunautom.png'),
(5, N'Canh bí đao nấu sườn', N'Bò, ngô, gừng', 95,'assets/images/canhbidaosuon.jpg'),
(5, N'Canh ngó khoai', N'Bí đao, sườn non,...', 95,'assets/images/canhngokhoainuoc.jpg'),
(5, N'Canh riêu cua', N'Mồng tơi, mướp, riêu cua,...', 95,'assets/images/canhrieucua.jpg'),
(5, N'Canh hoa thiên lý thịt bò', N'Hoa thiên lý, thịt bò băm,...', 95,'assets/images/canhthienlythitbo.png'),
(6, N'Cơm độn bắp', N'Cơm, bắp,...', 35,'assets/images/comdonbap.jpg'),
(6, N'Cơm hạt sen', N'Cơm, hạt sen,...', 35,'assets/images/comhatsen.PNG'),
(6, N'Cơm rang thịt xông khói', N'Cơm, thịt xông khói,...', 55,'assets/images/comrangthitxongkhoi.png'),
(6, N'Cơm trắng', N'Cơm', 25,'assets/images/comtrnag.png'),
(6, N'Xôi dừa hạt sen', N'Nếp, nước cốt dừa, hạt sen', 35,'assets/images/xoiduahatsen.png'),
(7, N'Lẩu vịt', N'Nước lẩu trường thọ, táo tàu, ngô, vịt, trứng non, rau ăn kèm', 650,'assets/images/lauvit.png'),
(7, N'Lẩu cua đồng', N'Gạch cua, đậu hũ, cà chua, bún, bắp bò, rau ăn kèm', 495,'assets/images/laucuadong.png'),
(7, N'Lẩu cua', N'Cua, nước lẩu cua, đồ ăn kèm,...', 650,'assets/images/laucua.png'),
(7, N'Lẩu uyên ương', N'Lẩu cà chua, lẩu trường thọ, đồ ăn kèm,...', 550,'assets/images/lauuyenuong.PNG'),
(7, N'Đồ ăn kèm', N'Rau củ tổng hợp, đồ ăn kèm lẩu,...', 205,'assets/images/doankemlau.PNG'),
(8, N'Chè sen', N'Hạt sen, nước đường,...', 45,'assets/images/chesen.PNG'),
(8, N'Cốm xào', N'Cốm, dừa tươi,...', 45,'assets/images/comxao.jpg'),
(8, N'Bánh trôi nước', N'Đỗ xanh, nước gừng,...', 45,'assets/images/banhtroinuoc.PNG'),
(8, N'Chè khoai', N'Khoai mật, nước gừng, cốt dừa,...', 45,'assets/images/chekhoaideo.png'),
(8, N'Chè thạch đen', N'Thạch đen, bột báng, dừa tươi', 45,'assets/images/chethachden.png'),
(9, N'Nước sâm', N'Nước thảo mộc, lá dứa,...', 35,'assets/images/nuocsam.png'),
(9, N'Nước ép cóc', N'Cóc, muối,...', 35,'assets/images/nuocepcoc.png'),
(9, N'Nước mía', N'Mía,...', 25,'assets/images/nuocmia.png'),
(9, N'Nước dâu tằm', N'Dâu tằm lên men,...', 35,'assets/images/nuocdautam.png'),
(9, N'Nước đỗ đen hạt sen', N'Đỗ đen rang, hạt sen,...', 35,'assets/images/nuocdodenhatsen.png'),
(9, N'Trà sen', N'Trà sen', 25,'assets/images/trasen.PNG');

-- Insert data into Employees
INSERT INTO [Employees] (EmployeeName, Position, EmployeePhoneNumber, EmployeeEmail, LoginPassword) VALUES
('Stuart Hickey', 'ADMIN', '0123456789', 'stuart@example.com', /*'password1'*/'0b14d501a594442a01c6859541bcb3e8164d183d32937b851835442f69d5c94e'),
('Theo Murray', 'STAFF', '0987654321', 'theo@example.com', /*'password2'*/'6cf615d5bcaac778352a8f1f3360d23f02f34ec182e259897fd6ce485d7870d4'),
('Max Booth', 'STAFF', '0112233445', 'max@example.com', /*'password3'*/'5906ac361a137e2d286465cd6588ebb5ac3f5ae955001100bc41577c3d751764'),
('Andrew Hawkins', 'STAFF', '0667788990', 'andrew@example.com', /*'password4'*/'b97873a40f73abedd8d685a7cd5e5f85e4a9cfb83eac26886640a0813850122b'),
('Quintin Hawkins', 'COOK', '0223344556', 'quintin@example.com', /*'password5'*/'8b2c86ea9cf2ea4eb517fd1e06b74f399e7fec0fef92e3b482a6cf2e2b092023');

-- Insert data into Tables
INSERT INTO [Tables] (TableCapacity) VALUES
(2),
(4),
(6),
(8),
(4);

COMMIT TRANSACTION;
END TRY

BEGIN CATCH
ROLLBACK TRANSACTION;
PRINT ERROR_MESSAGE();
END CATCH;

UPDATE [Tables] SET CustomerPhoneNumber = NULL, DateTimeUsed = NULL WHERE TableID = 1
SELECT * FROM [Tables]
SELECT * FROM [DineInOrders] ORDER BY Rush DESC, DateTimeOrdered DESC

DELETE FROM [Invoices]
DBCC CHECKIDENT ([Invoices], RESEED, 0);