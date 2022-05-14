package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class User {

	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://localhost:3305/new","root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertUser(String Username, String Email, String Phoneno, String Nic) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into user(`Userid`,`Username`,`Email`,`Phoneno`,`Nic`)" + " values (?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, Username);
			preparedStmt.setString(3, Email);
			preparedStmt.setString(4, Phoneno);
			preparedStmt.setString(5, Nic);

			// execute the statement
			preparedStmt.execute();
			con.close();
			//output = "Inserted successfully";
			
			String newUsers = readUser();
			 output = "{\"status\":\"success\", \"data\": \"" + newUsers + "\"}"; 

		} catch (Exception e) {
			output =  "{\"status\":\"error\", \"data\":\"Error while inserting the user.\"}"; 
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String readUser() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			
			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>ID</th><th>Name</th><th>Email</th><th>Phone NO</th><th>NIC</th></tr>";
			String query = "select * from user";
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
			
			// iterate through the rows in the result set
			while (rs.next())
			{
				String Userid = Integer.toString(rs.getInt("Userid"));
				String Username = rs.getString("Username");
				String Email = rs.getString("Email");
				String Phoneno = rs.getString("Phoneno");
				String Nic = rs.getString("Nic");

				//Add into the html table
				//output += "<tr><td>" + Userid + "</td>";
				
				output += "<tr><td><input id='hidUserUpdate' name='hidUserUpdate' type='hidden' value='" + Userid
						 + "'>" + Userid + "</td>";
				output += "<td>" + Username + "</td>";
				output += "<td>" + Email + "</td>";
				output += "<td>" + Phoneno + "</td>";
				output += "<td>" + Nic + "</td>";
				
				//buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' "
						+ "class='btnUpdate btn btn-secondary' data-Userid='" + Userid + "'></td>"
						+ "<td><input name='btnRemove' type='button' value='Remove' "
						+ "class='btnRemove btn btn-danger' data-userID='" + Userid + "'></td></tr>";
			}
			con.close();

			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the User.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updateUser(String Userid, String Username, String Email, String Phoneno, String Nic) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement
			String query = "UPDATE user SET Username=?,Email=?,Phoneno=?,Nic=? WHERE Userid=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, Username);
			preparedStmt.setString(2, Email);
			preparedStmt.setString(3, Phoneno);
			preparedStmt.setString(4, Nic);
			preparedStmt.setInt(5, Integer.parseInt(Userid));

			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newUsers = readUser();
			 output = "{\"status\":\"success\", \"data\": \"" + newUsers + "\"}"; 

			//output = "Updated successfully";
			
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while updating the user.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deleteUser(String Userid) {

		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from user where Userid=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(Userid));

			// execute the statement
			preparedStmt.execute();
			con.close();

			String newUsers = readUser();
			 output = "{\"status\":\"success\", \"data\": \"" + newUsers + "\"}"; 

			output = "Deleted successfully";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while deleting the user.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}
}

