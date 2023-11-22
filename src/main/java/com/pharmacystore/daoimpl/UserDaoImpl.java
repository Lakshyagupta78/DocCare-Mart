package com.pharmacystore.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import com.pharmacystore.pojo.User;
import com.pharmacystore.pojo.Order;

import com.pharmacystore.connection.DbConnection;
import com.pharmacystore.dao.UserDao;
import com.pharmacystore.pojo.User;

public class UserDaoImpl implements UserDao {
	@Override
	public boolean register(User user) {
		try (Connection con = 
				DbConnection.getDatabaseConnection())
		{
			PreparedStatement pst = 
		con.prepareStatement("INSERT INTO user"
				+ " VALUES(?,?,?,?,?,?,?,?)");
			
			pst.setString(1,user.getUserid());
			pst.setString(2,user.getPassword());
			pst.setString(3,user.getEmailid());
			pst.setInt(4,user.getAge());
			pst.setString(5,user.getContact());
			pst.setString(6,user.getCity());
			pst.setString(7,user.getState());
			pst.setString(8,user.getPincode());
			
			int count = pst.executeUpdate();
			
			if(count > 0)
				return true;
			else
				return false;
		}
		catch(SQLException | NullPointerException exc) {
			exc.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean checkUser(User user) {
		try (Connection con = 
				DbConnection.getDatabaseConnection())
		{
			PreparedStatement pst = 
		con.prepareStatement("SELECT * FROM user"
				+ " WHERE userid = ? AND"
				+ " password = ?");
			
			pst.setString(1,user.getUserid());
			pst.setString(2,user.getPassword());
			
			ResultSet rs = pst.executeQuery();
			
			if(rs.isBeforeFirst())
				return true;
			else
				return false;
		}
		catch(SQLException | NullPointerException exc) {
			exc.printStackTrace();
			return false;
		}
	}
	

	    @Override
	    public List<Order> displaymycancelledOrder(User u) {
	        List<Order> cancelledOrders = new ArrayList<>();
	        String query = "SELECT * FROM productorder WHERE customerid = ? AND cancelled = true";
	        
	        try (Connection connection = 
	        		DbConnection.getDatabaseConnection()){
	             PreparedStatement statement = connection.prepareStatement(query);
	            statement.setString(1, u.getUserid()); // Assuming there's a getUserId() method in User
	            
	            try (ResultSet resultSet = statement.executeQuery()) {
	                while (resultSet.next()) {
	                    int orderId = resultSet.getInt("orderid");
	                    // Retrieve other relevant order details
	                    
	                    Order order = new Order();
	                    cancelledOrders.add(order);
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace(); // Handle the exception appropriately
	        }
	        
	        return cancelledOrders;
	    }
	    
	    @Override
	    public boolean cancelOrder(Order o) {
	        boolean success = false;
	        String updateQuery = "UPDATE productorder SET order_status = ?, cancelled = true, accepted = false WHERE orderid = ?";
	        
	        try (Connection connection = DbConnection.getDatabaseConnection()){
	             PreparedStatement statement = connection.prepareStatement(updateQuery);
	            statement.setString(1, "cancelled"); 
	            statement.setInt(2, o.getOrderid()); 
	            
	            int rowsAffected = statement.executeUpdate();
	            
	            if (rowsAffected > 0) {
	                success = true;
	            }
	        } catch (Exception e) {
	            e.printStackTrace(); // Handle the exception appropriately
	        }
	        
	        return success;
	    }
	    
	    public User getUserById(String userId) {
	        User user = null;

	        try (Connection con = DbConnection.getDatabaseConnection()) {
	            String query = "SELECT * FROM user WHERE userid = ?";
	            try (PreparedStatement pst = con.prepareStatement(query)) {
	                pst.setString(1, userId);
	                try (ResultSet rs = pst.executeQuery()) {
	                    if (rs.next()) {
	                        user = new User();
	                        user.setUserid(rs.getString("userid"));
	                        user.setPassword(rs.getString("password"));
	                        user.setEmailid(rs.getString("emailid"));
	                        user.setAge(rs.getInt("age"));
	                        user.setContact(rs.getString("contact"));
	                        user.setCity(rs.getString("city"));
	                        user.setState(rs.getString("state"));
	                        user.setPincode(rs.getString("pincode"));
	                        // Set other properties as needed
	                    }
	                }
	            }
	        } catch (SQLException | NullPointerException exc) {
	            exc.printStackTrace();
	        }

	        return user;
	    }
	    
	    @Override
	    public boolean updateUser(User user) {
	        boolean success = false;
	        String updateQuery = "UPDATE user SET address = ? WHERE userid = ?";

	        try (Connection connection = DbConnection.getDatabaseConnection()) {
	            try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
	                statement.setString(1, user.getAddress());
	                statement.setString(2, user.getUserid());

	                int rowsAffected = statement.executeUpdate();

	                if (rowsAffected > 0) {
	                    success = true;
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace(); // Handle the exception appropriately
	        }

	        return success;
	    }

}
