-- Create the database
CREATE DATABASE IF NOT EXISTS company;
USE company;

-- Create Employee table
CREATE TABLE Employee (
  EmpID INT PRIMARY KEY,
  Name VARCHAR(100),
  Salary DECIMAL(10,2)
);

-- Insert sample employee records
INSERT INTO Employee (EmpID, Name, Salary) VALUES
(101, 'Alice', 50000.00),
(102, 'Bob', 60000.00),
(103, 'Carol', 55000.00);
