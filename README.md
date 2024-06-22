學校的期末作業，內容無法上傳，無法修改，僅供測試  
ConnectionDB為連線系統。  
CreateTable為上傳並建立資料表格，還有生成一些自己生成的隨機添加資料。  
openwindow為使用系統，裡面的內容大致為搜尋系統以及五個題目。  
mariadb-java-client為我所使用的sql連線資料庫，版本為3.4.0。

組員為：林睿煜411177020，李淮恩411177016  

### E-R Model
![hLPHQzim47xthn1vQbaBRQ4CGWbr76ypJPoGf7aTMgoj1baAabmmJV_xhFF5Yb7MEMnlqdsVxljEEPtpg0rLfYnu431XSbEHmLXHaGq8rUI7NP6tW91F1AB9X0n9agx9myFYwOg4p-lvzoIDbl4iJjUZKKsylWv8mqrfmMhs9bn6tyBbnVtDPV3kH6OoOnn42hj24OZs506kBsBbPLQjJCMFa](https://github.com/rainlin138077/Car-Sales-Inquiry/assets/152964060/a6561697-44b7-4519-8b2c-1be64d36d66e)

### Relational Schema
![image](https://github.com/rainlin138077/Car-Sales-Inquiry/assets/152964060/5d0ae96b-4749-4a83-96b1-68597b1f2a84)

### Sample Data Amount for Each Table
![image](https://github.com/rainlin138077/Car-Sales-Inquiry/assets/152964060/54fdb46d-6eb8-486b-a530-f2bf6fe3fea0)
![image](https://github.com/rainlin138077/Car-Sales-Inquiry/assets/152964060/a6f72499-2624-484c-a02f-4e0f1c74ef25)
![image](https://github.com/rainlin138077/Car-Sales-Inquiry/assets/152964060/d7f6d0e3-a0bd-4e4e-8410-6945ad649208)
![image](https://github.com/rainlin138077/Car-Sales-Inquiry/assets/152964060/042a2d3e-a826-4a57-ab6d-ac866ab2da46)
![image](https://github.com/rainlin138077/Car-Sales-Inquiry/assets/152964060/62645bdc-856e-4664-996a-32c2f1a4b5f0)
![image](https://github.com/rainlin138077/Car-Sales-Inquiry/assets/152964060/d5b3fdc1-dd92-44d2-8e81-a3e5bf9cc5c3)
![image](https://github.com/rainlin138077/Car-Sales-Inquiry/assets/152964060/5c795e97-24a3-4025-9539-30e7a2d27a8f)
![image](https://github.com/rainlin138077/Car-Sales-Inquiry/assets/152964060/cb065835-1f30-4dc1-9422-98906ce082d7)
![image](https://github.com/rainlin138077/Car-Sales-Inquiry/assets/152964060/5898fbaa-cc21-4c47-be63-418411ccca92)
![image](https://github.com/rainlin138077/Car-Sales-Inquiry/assets/152964060/4d157267-72a0-42db-85b4-ed44445b0797)
![image](https://github.com/rainlin138077/Car-Sales-Inquiry/assets/152964060/367145e1-0028-4acd-872d-120d4ef57ef7)

### Queries
1. Suppose that it is found that transmissions made by supplier Getrag between two given dates are defective. Find the VIN of each car containing such a transmission and the customer to which it was sold. If your design allows, suppose the defective transmissions all come from only one of Getrag’s plants.
```
SELECT v.VIN, c.CustomerName
FROM Vehicle v
JOIN Model m ON v.ModelID = m.ModelID
JOIN Brand b ON v.BrandID = b.BrandID
JOIN Supply s ON m.ModelID = s.ModelID
JOIN Supplier sup ON s.SupplierID = sup.SupplierID
JOIN Inventory i ON v.VIN = i.VIN
JOIN Sales sa ON i. DealerID = sa. DealerID
JOIN customer c ON sa.CustomerID = c.CustomerID
WHERE sup.SupplierName = 'Getrag' AND sa.SalesDate BETWEEN '2023-05-01' AND '2023-07-10'
```
![image](https://github.com/rainlin138077/Car-Sales-Inquiry/assets/152964060/3831a4f7-9ae8-4f6b-b775-e0acdd0ebd26)

2. Find the dealer who has sold the most (by dollar-amount) in the past year.
```
SELECT d.DealerName, SUM(v.Price) AS TotalSalesAmount
FROM Dealer d
JOIN Inventory i ON d.DealerID = i.DealerID
JOIN Vehicle v ON i.VIN = v.VIN
JOIN Sales s ON i.DealerID = s.DealerID
WHERE s.SalesDate BETWEEN DATE_SUB(CURDATE(), INTERVAL 1 YEAR) AND CURDATE()
GROUP BY d.DealerID
ORDER BY TotalSalesAmount DESC LIMIT 1
```
![image](https://github.com/rainlin138077/Car-Sales-Inquiry/assets/152964060/6cecac32-b630-474b-a5e9-c5f40e552388)

3. Find the top 2 brands by unit sales in the past year.
```
SELECT b.BrandName, COUNT(*) AS SalesCount
FROM Brand b
JOIN Vehicle v ON b.BrandID = v.BrandID
JOIN Inventory i ON v.VIN = i.VIN
JOIN Sales s
WHERE s.SalesDate BETWEEN DATE_SUB(CURDATE(), INTERVAL 1 YEAR) AND CURDATE()
GROUP BY b.BrandID
ORDER BY SalesCount DESC LIMIT 2
```
![image](https://github.com/rainlin138077/Car-Sales-Inquiry/assets/152964060/b3883863-a686-4487-a57c-630ece6ee57a)

4. In what month(s) do SUVs sell best?
```
SELECT MONTH(s.SalesDate) AS Month, COUNT(*) AS SalesCount
FROM Sales s
JOIN Inventory i
JOIN Vehicle v ON i.VIN = v.VIN
JOIN Model m ON v.ModelID = m.ModelID
WHERE m.BodyStyle = 'SUV'
GROUP BY MONTH(s.SalesDate)
ORDER BY SalesCount DESC LIMIT 1
```
![image](https://github.com/rainlin138077/Car-Sales-Inquiry/assets/152964060/81a94ff4-ddad-4517-a99f-3ce695df9162)

5. Find those dealers who keep a vehicle in inventory for the longest average time.
```
SELECT d.DealerName, AVG(DATEDIFF(CURDATE(), i.Date)) AS AvgDaysInInventory
FROM Dealer d
JOIN Inventory i ON d.DealerID = i.DealerID
JOIN Vehicle v ON i.VIN = v.VIN
WHERE i.InventoryStatus = 'Available'
GROUP BY d.DealerID
ORDER BY AvgDaysInInventory DESC LIMIT 1
```
![image](https://github.com/rainlin138077/Car-Sales-Inquiry/assets/152964060/1fee9de2-e7c7-465c-b423-b94bcfb890ae)
