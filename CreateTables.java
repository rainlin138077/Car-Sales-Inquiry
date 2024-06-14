import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;


public class CreateTables {
    private static final String URL = "jdbc:mariadb://0.tcp.jp.ngrok.io:11051/411177020";
    private static final String USER = "411177020";
    private static final String PASSWORD = "411177020";
    private static Connection connection;

    public static void main(String[] args) {
        try {
            // Load JDBC driver
            Class.forName("org.mariadb.jdbc.Driver");

            // Establish connection
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("成功連線");
            String Table;
            // Create tables
            Table = "CREATE TABLE IF NOT EXISTS Vehicle (" +
                    "VIN VARCHAR(17) PRIMARY KEY," +
                    "Color VARCHAR(20)," +
                    "Engine VARCHAR(20)," +
                    "Transmission VARCHAR(20)," +
                    "ModelID INT," +
                    "BrandID INT," +
                    "Price DECIMAL(10, 2),"+
                    "FOREIGN KEY (BrandID) REFERENCES Brand(BrandID)) ";
            executeUpdate(Table);
            System.out.println("Vehicle建立成功");

            Table = "CREATE TABLE IF NOT EXISTS Brand (" +
                    "BrandID INT PRIMARY KEY," +
                    "BrandName VARCHAR(50))";
            executeUpdate(Table);
            System.out.println("Brand建立成功");

            Table = "CREATE TABLE IF NOT EXISTS Model (" +
                    "ModelID INT PRIMARY KEY," +
                    "ModelName VARCHAR(50)," +
                    "BodyStyle VARCHAR(50),"+
                    "BrandID INT," +
                    "FOREIGN KEY (BrandID) REFERENCES Brand(BrandID))";
            executeUpdate(Table);
            System.out.println("Model建立成功");

            Table = "CREATE TABLE IF NOT EXISTS Dealer (" +
                    "DealerID INT PRIMARY KEY," +
                    "DealerName VARCHAR(50)," +
                    "Address VARCHAR(100)," +
                    "Phone VARCHAR(15))";
            executeUpdate(Table);
            System.out.println("Dealer建立成功");

            Table = "CREATE TABLE IF NOT EXISTS Customer (" +
                    "CustomerID INT PRIMARY KEY," +
                    "CustomerName VARCHAR(50)," +
                    "Address VARCHAR(100)," +
                    "Phone VARCHAR(15)," +
                    "Gender CHAR(1)," +
                    "AnnualIncome DECIMAL(10, 2)," +
                    "IsCompany BOOLEAN)";
            executeUpdate(Table);
            System.out.println("Customer建立成功");

            Table = "CREATE TABLE IF NOT EXISTS Supplier (" +
                    "SupplierID INT PRIMARY KEY," +
                    "SupplierName VARCHAR(50))";
            executeUpdate(Table);
            System.out.println("Supplier建立成功");

            Table = "CREATE TABLE IF NOT EXISTS Plant (" +
                    "PlantID INT PRIMARY KEY," +
                    "PlantName VARCHAR(50))";
            executeUpdate(Table);
            System.out.println("Plant建立成功");

            Table = "CREATE TABLE IF NOT EXISTS Sales (" +
                    "SalesID INT PRIMARY KEY," +
                    "SalesDate DATE," +
                    "DealerID INT," +
                    "CustomerID INT," +
                    "VIN VARCHAR(17)," +
                    "FOREIGN KEY (DealerID) REFERENCES Dealer(DealerID)," +
                    "FOREIGN KEY (CustomerID) REFERENCES Customer(CustomerID)," +
                    "FOREIGN KEY (VIN) REFERENCES Vehicle(VIN))";
            executeUpdate(Table);
            System.out.println("Sales建立成功");

            Table = "CREATE TABLE IF NOT EXISTS Inventory (" +
                    "InventoryID INT PRIMARY KEY," +
            		"DealerID INT,"+
                    "VIN CHAR(17),"+
            		"InventoryStatus VARCHAR(20),"+
                    "Date DATE,"+
                    "FOREIGN KEY (DealerID) REFERENCES Dealer(DealerID),"+
                    "FOREIGN KEY (VIN) REFERENCES Vehicle(VIN))";
            executeUpdate(Table);
            System.out.println("Inventory建立成功");
            
            Table = "CREATE TABLE IF NOT EXISTS Supply (" +
                    "SupplyID INT PRIMARY KEY," +
            		"SupplierID INT,"+
                    "ModelID INT,"+
            		"PartName VARCHAR(50),"+
                    "FOREIGN KEY (SupplierID) REFERENCES Supplier(SupplierID),"+
                    "FOREIGN KEY (ModelID) REFERENCES Model(ModelID))";
            executeUpdate(Table);
            System.out.println("Supply建立成功");
            
            Table = "CREATE TABLE IF NOT EXISTS Manufacture (" +
                    "ManufactureID INT PRIMARY KEY," +
            		"PlantID INT,"+
                    "ModelID INT,"+
            		"PartName VARCHAR(50),"+
                    "FOREIGN KEY (PlantID) REFERENCES Plant(PlantID),"+
                    "FOREIGN KEY (ModelID) REFERENCES Model(ModelID))";
            executeUpdate(Table);
            System.out.println("Manufacture建立成功");
            
            insertSampleData();
            
            connection.close();
        } catch (ClassNotFoundException e) {
            System.err.println("Error: MariaDB JDBC driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Error: Connection failed!");
            e.printStackTrace();
        }
    }

    private static void executeUpdate(String query) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        }
    }
    
    private static void insertSampleData() throws SQLException {
        String sql;
        
        sql = "INSERT INTO Brand (BrandID, BrandName) VALUES "
        		+ "(1, 'Toyota'), (2, 'Honda'), (3, 'Ford'), (4, 'Chevrolet'), (5, 'BMW')";
        executeUpdate(sql);
        System.out.println("Brand資料建立成功");
        
        sql = "INSERT INTO Model (ModelID, ModelName, BodyStyle,BrandID) VALUES "
        		+ "(1, 'Camry', 'Sedan',1), (2, 'Civic', 'Sedan',2), (3, 'F-150', 'Truck',4), "
        		+ "(4, 'Silverado', 'Truck',1), (5, 'X5', 'SUV',3), (6, 'Corolla', 'Sedan',5),"
        		+ "(7, 'CR-V', 'SUV',3), (8, 'Mustang', 'Coupe',4), (9, 'Tahoe', 'SUV',2), (10, 'X3', 'SUV',3)";
        executeUpdate(sql);
        System.out.println("Model資料建立成功");
        
        sql ="INSERT INTO Supplier (SupplierID, SupplierName) VALUES "
        		+ "(1, 'Getrag'), (2, 'Bosch'), (3, 'Continental'), (4, 'Denso'), (5, 'ZF')";
        executeUpdate(sql);
        System.out.println("Supplier資料建立成功");
        
        sql="INSERT INTO Plant (PlantID, PlantName) VALUES "
        		+ "(1, 'Toyota Plant'), (2, 'Honda Plant'), (3, 'Ford Plant'),"
        		+ "(4, 'Chevrolet Plant'), (5, 'BMW Plant'), (6, 'Getrag Plant')";
        executeUpdate(sql);
        System.out.println("Plant資料建立成功");
        
        sql="INSERT INTO Dealer (DealerID, DealerName, Address, Phone) VALUES "
        		+ "(1, 'ABC Motors', '123 Main St, City A', '1234567890'),"
        		+ "(2, 'XYZ Autos', '456 Oak Rd, City B', '9876543210'),"
        		+ "(3, 'Elite Cars', '789 Pine Ave, City C', '5555555555')";
        executeUpdate(sql);
        System.out.println("Dealer資料建立成功");
        
        sql="INSERT INTO Customer (CustomerID, CustomerName, Address, Phone, Gender, AnnualIncome, IsCompany) VALUES "
        		+ "(1, 'John Doe', '11 Maple Ln, City A', '1112223333', 'M', 60000, 0),"
        		+ "(2, 'Jane Smith', '22 Oak St, City B', '4445556666', 'F', 80000, 0),"
        		+ "(3, 'Bob Johnson', '33 Pine Rd, City C', '7778889999', 'M', 90000, 0),"
        		+ "(4, 'Acme Inc.', '44 Elm Ave, City A', '1234569870', 'M', 500000, 1),"
        		+ "(5, 'Beta Corp.', '55 Cedar Blvd, City B', '9630258741', 'F', 750000, 1)";
        executeUpdate(sql);
        System.out.println("Customer資料建立成功");
        
        sql="INSERT INTO Vehicle (VIN, Color, Engine, Transmission, ModelID, BrandID, Price) VALUES "
        		+ "('1G6AB5R33F0123456', 'Red', 'V6', 'Automatic', 1, 1, 25000),"
        		+ "('2HGFB2F89FH654321', 'Blue', 'I4', 'Manual', 2, 2, 20000),"
        		+ "('1FTFW1EF3FK987654', 'White', 'V8', 'Automatic', 3, 3, 35000),"
        		+ "('1GCVKREC4FZ123456', 'Black', 'V8', 'Automatic', 4, 4, 40000),"
        		+ "('WBAFD63597C654321', 'Silver', 'I6', 'Automatic', 5, 5, 50000),"
        		+ "('4T1BF1FK6FU987654', 'Gray', 'I4', 'Automatic', 6, 1, 22000),"
        		+ "('5J6RM4H32FL123456', 'White', 'I4', 'Automatic', 7, 2, 28000),"
        		+ "('1ZVBP8AM8F5654321', 'Red', 'V8', 'Manual', 8, 3, 30000),"
        		+ "('1GNSKCE09FZ987654', 'Black', 'V8', 'Automatic', 9, 4, 45000),"
        		+ "('WBAVB73547P123456', 'Blue', 'I4', 'Automatic', 10, 5, 35000),"
        		+ "('3FADP4BJ2FM654321', 'Red', 'V6', 'Automatic', 3, 3, 33000),"
        		+ "('1G6AB5R32F9123456', 'Silver', 'V6', 'Automatic', 1, 1, 26000),"
        		+ "('2HGFB2F88FH987654', 'Gray', 'I4', 'Manual', 2, 2, 21000),"
        		+ "('1FTFW1EF2FK321456', 'Black', 'V8', 'Automatic', 3, 3, 36000),"
        		+ "('1GCVKREC5FZ789456', 'White', 'V8', 'Automatic', 4, 4, 42000)";
        executeUpdate(sql);
        System.out.println("Vehicle資料建立成功");
        
        sql="INSERT INTO Supply (SupplyID, SupplierID, ModelID, PartName) VALUES "
        		+ "(1, 1, 1, 'Transmission'), (2, 1, 2, 'Transmission'),"
        		+ "(3, 1, 3, 'Transmission'), (4, 1, 4, 'Transmission'),"
        		+ "(5, 2, 5, 'Engine'), (6, 2, 6, 'Engine'),"
        		+ "(7, 3, 7, 'Brakes'), (8, 3, 8, 'Brakes'),"
        		+ "(9, 4, 9, 'Electronics'), (10, 4, 10, 'Electronics')";
        executeUpdate(sql);
        System.out.println("Supply資料建立成功");
        
        sql="INSERT INTO Manufacture (ManufactureID, PlantID, ModelID, PartName) VALUES "
        		+ "(1, 1, 1, 'Body'), (2, 1, 6, 'Body'),"
        		+ "(3, 2, 2, 'Body'), (4, 2, 7, 'Body'),"
        		+ "(5, 3, 3, 'Body'), (6, 3, 8, 'Body'),"
        		+ "(7, 4, 4, 'Body'), (8, 4, 9, 'Body'),"
        		+ "(9, 5, 5, 'Body'), (10, 5, 10, 'Body'),"
        		+ "(11, 6, 1, 'Transmission'), (12, 6, 2, 'Transmission'),"
        		+ "(13, 6, 3, 'Transmission'), (14, 6, 4, 'Transmission')";
        executeUpdate(sql);
        System.out.println("Manufacture資料建立成功");
        
        sql="INSERT INTO Inventory (InventoryID, DealerID, VIN, InventoryStatus, Date) VALUES "
        		+ "(1, 1, '1G6AB5R33F0123456', 'Available','2024-01-10'),"
        		+ "(2, 1, '2HGFB2F89FH654321', 'Sold', '2023-05-01'),"
        		+ "(3, 1, '1FTFW1EF3FK987654', 'Available', '2024-01-10'),"
        		+ "(4, 2, '1GCVKREC4FZ123456', 'Sold' , '2023-05-10'),"
        		+ "(5, 2, 'WBAFD63597C654321', 'Available', '2024-01-10'),"
        		+ "(6, 2, '4T1BF1FK6FU987654', 'Sold' , '2023-04-20'),"
        		+ "(7, 3, '5J6RM4H32FL123456', 'Available' , '2024-01-10'),"
        		+ "(8, 3, '1ZVBP8AM8F5654321', 'Sold' , '2023-07-15')";
        executeUpdate(sql);
        System.out.println("Inventory資料建立成功");
        
        sql="INSERT INTO Sales (SalesID, SalesDate, DealerID, CustomerID, VIN) VALUES "
        		+ "(1, '2023-05-01', 1, 1, '2HGFB2F89FH654321'), (2, '2023-05-10', 2, 2, '1GCVKREC4FZ123456'),"
        		+ "(3, '2023-07-15', 3, 3, '1ZVBP8AM8F5654321'), (4, '2023-04-20', 1, 4, '4T1BF1FK6FU987654'),"
        		+ "(5, '2023-05-25', 2, 5, 'WBAVB73547P123456'), (6, '2023-06-01', 3, 1, '2HGFB2F88FH987654'),"
        		+ "(7, '2023-08-05', 1, 2, '1G6AB5R32F9123456'), (8, '2023-03-10', 2, 3, '1FTFW1EF2FK321456'),"
        		+ "(9, '2023-11-15', 3, 4, '5J6RM4H32FL123456'), (10, '2023-06-20', 1, 5, '1GNSKCE09FZ987654')";
        executeUpdate(sql);
        System.out.println("Inventory資料建立成功");
        

    }
}
