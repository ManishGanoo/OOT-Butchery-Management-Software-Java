INSERT INTO tblSales(Invoice_No,sDate,Pid,quantity,Weight_Kg,Total,Paid,DeliveredBy,Cid)
    VALUES (1001,'2017/02/24',1,10,150.2,15921.2,'y','sandooyea',1)

INSERT INTO tblProduct(Name, Stock, buyingPrice, sellingPrice)
    VALUES ('whole chicken',100,86.0,114.0)

INSERT INTO tblCustomer(Name, telephone, email, balanceDue)
    VALUES ('sumida',123456,NULL ,10000)

SELECT * FROM tblSales
ORDER BY Invoice_No

SELECT * FROM tblProduct
SELECT * FROM tblCustomer

DELETE FROM tblProduct WHERE ProductID=2

SELECT Invoice_No,sDate,Pid,quantity,Weight_Kg,Total,Paid,DeliveredBy,Cid FROM tblSales

SELECT Name FROM tblProduct WHERE ProductID=1


SELECT Name FROM tblCustomer WHERE CustID=1

INSERT INTO tblSales(Invoice_No,sDate,Pid,quantity,Weight_Kg,Total,Paid,DeliveredBy,Cid)
VALUES (1001,'2017/02/24',1,10,150.2,15921.2,'y','sandooyea',1)

INSERT INTO tblSales(Invoice_No,sDate,Pid,quantity,Weight_Kg,Total,Paid,DeliveredBy,Cid)
VALUES (9012,'2017/01/14',4,15,50,30000,'y','sandooyea',4)

INSERT INTO tblSales(Invoice_No,sDate,Pid,quantity,Weight_Kg,Total,Paid,DeliveredBy,Cid)
VALUES (2003,'2017/02/21',4,20,100,10021.5,'y','sandooyea',5)

INSERT INTO tblSales(Invoice_No,sDate,Pid,quantity,Weight_Kg,Total,Paid,DeliveredBy,Cid)
VALUES (3203,'2017/02/21',4,5,75,11321.2,'y','sandooyea',4)

INSERT INTO tblSales(Invoice_No,sDate,Pid,quantity,Weight_Kg,Total,Paid,DeliveredBy,Cid)
VALUES (3303,'2017/02/21',1,50,80,15000.7,'y','sandooyea',5)

INSERT INTO tblProduct(Name, Stock, buyingPrice, sellingPrice)
VALUES ('wings',100,86.0,114.0)

INSERT INTO tblCustomer(Name, telephone, email, balanceDue)
VALUES ('sumida',123456,NULL ,10000)

INSERT INTO tblCustomer(Name, telephone, email, balanceDue)
VALUES ('Alice',123456,'alice@hotmail.com',10000)

SELECT * FROM tblSales
WHERE  SalesID=14

UPDATE tblSales
SET


SET Invoice_No,sDate,Pid,quantity,Weight_Kg,Total,Paid,DeliveredBy,Cid
VALUES (1799,'2017-03-11',1,50,0.0,0.0,'y','sando',1)
WHERE SalesID=14



