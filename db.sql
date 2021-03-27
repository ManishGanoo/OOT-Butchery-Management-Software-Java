CREATE DATABASE S_Sandooyea
GO

CREATE TABLE tblUser (
  UserID		INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
  Name		VARCHAR(25),
  l_password	VARCHAR(10)
);


CREATE TABLE tblCustomer (
  CustID		INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
  Name		VARCHAR(25),
  telephone	INT,
  email		VARCHAR(50),
  balanceDue	DOUBLE PRECISION
);



CREATE TABLE tblProduct (
  ProductID	 INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
  Name		 VARCHAR(25),
  Stock		 INT NOT NULL,
  buyingPrice	 DOUBLE PRECISION NOT NULL,
  sellingPrice DOUBLE PRECISION NOT NULL
);

CREATE TABLE tblSupplier (
  SupplierID	INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
  Name		VARCHAR(25),
  telephone	INT,
  email		VARCHAR(50),
  balanceDue	DOUBLE PRECISION
);

CREATE TABLE tblSales (
  SalesID		INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
  Invoice_No  INT NOT NULL,
  sDate		DATE,
  Pid			INT NOT NULL,
  quantity	INT NOT NULL,
  Weight_Kg	DOUBLE PRECISION NOT NULL,
  Total		DOUBLE PRECISION NOT NULL,
  Paid		CHAR(1),
  DeliveredBy VARCHAR(50),
  Cid			INT NOT NULL
);

ALTER TABLE tblSales
  ADD CONSTRAINT fk_Sales
FOREIGN KEY (Pid)
REFERENCES tblProduct(ProductID)

ALTER TABLE tblSales
  ADD CONSTRAINT fk_Sales_Cust
FOREIGN KEY (Cid)
REFERENCES tblCustomer(CustID)

CREATE TABLE tblPurchases (
  PurchasesID	  INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
  Invoice_No  INT NOT NULL,
  P_Date		DATE,
  Pid			INT NOT NULL,
  quantity	INT NOT NULL,
  Weight_Kg	DOUBLE PRECISION NOT NULL,
  Total		DOUBLE PRECISION NOT NULL,
  S_id		INT NOT NULL
);

ALTER TABLE tblPurchases
  ADD CONSTRAINT fk_Purchase_Pr
FOREIGN KEY (Pid)
REFERENCES tblProduct(ProductID)

ALTER TABLE tblPurchases
  ADD CONSTRAINT fk_Purchase_Sp
FOREIGN KEY (S_id)
REFERENCES tblSupplier(SupplierID)



CREATE TABLE tblPaymentSup(
  PaymentID	INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
  supID			INT,
  Invoice_No  INT,
  Date_Paid	DATE NOT NULL,
  Amount_Paid DOUBLE PRECISION NOT NULL
);

CREATE TABLE tblPaymentCust(
  PaymentID	INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
  custID			INT,
  Invoice_No  INT,
  Date_Paid	DATE NOT NULL,
  Amount_Paid DOUBLE PRECISION NOT NULL
);

ALTER TABLE tblPaymentCust
  ADD CONSTRAINT fk_Customer
FOREIGN KEY (custID)
REFERENCES tblCustomer(CustID)

ALTER TABLE tblPaymentSup
  ADD CONSTRAINT fk_Supplier
FOREIGN KEY (supID)
REFERENCES tblSupplier(SupplierID)

DROP TABLE tblCustomer
DROP TABLE tblSupplier
DROP TABLE tblSales
DROP TABLE tblPayments
DROP TABLE tblPurchases
DROP TABLE tblProduct
