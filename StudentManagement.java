package com;

import java.sql.*;
import java.util.Scanner;

public class StudentManagement {

    static final String URL = "jdbc:mysql://localhost:3306/student_db";
    static final String USER = "your_username";
    static final String PASSWORD = "your_password";

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try {
            // Load driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to database
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Connected successfully!");

            while (true) {
            	System.out.println("1. Add Student");
            	System.out.println("2. View Students");
            	System.out.println("3. Update Student");
            	System.out.println("4. Delete Student");
            	System.out.println("5. Search Student");
            	System.out.println("6. Exit");
                System.out.print("Enter choice: ");
                int choice = sc.nextInt();

                switch (choice) {

                    case 1:
                        System.out.print("Enter Name: ");
                        String name = sc.next();

                        System.out.print("Enter Age: ");
                        int age = sc.nextInt();

                        System.out.print("Enter Course: ");
                        String course = sc.next();

                        String insert = "INSERT INTO students(name, age, course) VALUES (?, ?, ?)";
                        PreparedStatement ps = con.prepareStatement(insert);
                        ps.setString(1, name);
                        ps.setInt(2, age);
                        ps.setString(3, course);
                        ps.executeUpdate();

                        System.out.println("✅ Student Added!");
                        break;

                    case 2:
                        String select = "SELECT * FROM students";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(select);

                        System.out.println("\n--- Student List ---");
                        while (rs.next()) {
                            System.out.println(
                                    rs.getInt("id") + " | " +
                                    rs.getString("name") + " | " +
                                    rs.getInt("age") + " | " +
                                    rs.getString("course"));
                        }
                        break;

                    case 3:
                        sc.nextLine(); // clear buffer

                        System.out.print("Enter ID to update: ");
                        int updateId = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Enter new Name: ");
                        String newName = sc.nextLine();

                        System.out.print("Enter new Age: ");
                        int newAge = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Enter new Course: ");
                        String newCourse = sc.nextLine();

                        String updateQuery = "UPDATE students SET name=?, age=?, course=? WHERE id=?";
                        PreparedStatement psUpdate = con.prepareStatement(updateQuery);
                        psUpdate.setString(1, newName);
                        psUpdate.setInt(2, newAge);
                        psUpdate.setString(3, newCourse);
                        psUpdate.setInt(4, updateId);

                        int rows = psUpdate.executeUpdate();

                        if (rows > 0) {
                            System.out.println("✅ Student Updated!");
                        } else {
                            System.out.println("❌ Student not found!");
                        }
                        break;
                    case 4:

                        System.out.print("Enter Student ID to delete: ");
                        int deleteId = sc.nextInt();

                        String deleteQuery = "DELETE FROM students WHERE id = ?";

                        PreparedStatement psDelete = con.prepareStatement(deleteQuery);

                        psDelete.setInt(1, deleteId);

                        int rowsDeleted = psDelete.executeUpdate();

                        if(rowsDeleted > 0)
                        {
                            System.out.println("✅ Student Deleted!");
                        }
                        else
                        {
                            System.out.println("❌ Student not found!");
                        }

                        break;

                    case 5:
                    	System.out.print("Enter Student ID to search: ");
                    	int searchId = sc.nextInt();
                    	String searchQuery = "SELECT * FROM students WHERE id = ?";
                    	PreparedStatement psSearch = con.prepareStatement(searchQuery);
                    	psSearch.setInt(1, searchId);
                    	ResultSet rsSearch = psSearch.executeQuery();
                    	if(rsSearch.next())
                    	{
                    	    System.out.println("ID: " + rsSearch.getInt("id"));
                    	    System.out.println("Name: " + rsSearch.getString("name"));
                    	    System.out.println("Age: " + rsSearch.getInt("age"));
                    	    System.out.println("Course: " + rsSearch.getString("course"));
                    	}
                    	else
                    	{
                    	    System.out.println("❌ Student not found!");
                    	}

                        break;
                    case 6:
                        System.out.println("Thank you!");
                        con.close();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice!");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        sc.close();
    }
}