package com.pharmacystore.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.pharmacystore.connection.DbConnection;
import com.pharmacystore.dao.OrderDao;
import com.pharmacystore.pojo.Order;

public class OrderDAOImpl implements OrderDao {

	@Override
	public boolean placeOrder(Order order) {
		try(Connection con = DbConnection.getDatabaseConnection()){
			
			PreparedStatement pst = 
					con.prepareStatement("insert into productorder"
+ "(orderdunits,address,ordereddate,requesteddate,accepted,cancelled,"
+ "confirmed,billamount,customerid,productid) "
+ "values(?,?,?,?,?,?,?,?,?,?)");
			
			pst.setInt(1, order.getOrderedunits());
			pst.setString(2, order.getAddress());
			pst.setDate(3, order.getOrdereddate());
			pst.setDate(4, order.getRequesteddate());
			pst.setBoolean(5, true);
			pst.setBoolean(6, false);
			pst.setBoolean(7, false);
			pst.setInt(8, order.getBillamount());
			pst.setString(9, order.getCustomerid());
			pst.setInt(10, order.getProductid());
			
			int count = pst.executeUpdate();
			
			if(count > 0)
				return true;
			else
				return false;
		}
		catch(SQLException | NullPointerException ex)
		{
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Order> getAllOrders() {
		List<Order> orderList = new ArrayList<>();
		
		try(Connection con = DbConnection.getDatabaseConnection()){
			
			PreparedStatement pst = 
					con.prepareStatement(
							"select * from productorder");
			
			ResultSet rs = pst.executeQuery();
			
			if(rs.isBeforeFirst())
			{
				while(rs.next())
				{
					Order order = new Order();
					order.setOrderid(rs.getInt("orderid"));
					order.setOrderedunits(rs.getInt("orderdunits"));
					order.setAddress(rs.getString("address"));
					order.setOrdereddate(rs.getDate("ordereddate"));
					order.setRequesteddate(
							rs.getDate("requesteddate"));
					order.setAccepted(rs.getBoolean("accepted"));
					order.setCancelled(rs.getBoolean("cancelled"));
					order.setConfirmed(rs.getBoolean("confirmed"));
					order.setBillamount(rs.getInt("billamount"));
					order.setCustomerid(rs.getString("customerid"));
					order.setProductid(rs.getInt("productid"));
					
					orderList.add(order);
				}
			}
		}
		catch(SQLException | NullPointerException ex)
		{
			ex.printStackTrace();
			orderList.clear();
			return orderList;
		}
		
		return orderList;
	}

	@Override
	public Order searchOrder(int orderId) {
		Order order = null;
		
		try(Connection con = DbConnection.getDatabaseConnection()){
			
			PreparedStatement pst = 
					con.prepareStatement(
							"select * from productorder where"
							+ " orderid = ?");
			pst.setInt(1, orderId);
			ResultSet rs = pst.executeQuery();
			
			if(rs.isBeforeFirst())
			{
				rs.next();
					order = new Order();
					order.setOrderid(rs.getInt("orderid"));
					order.setOrderedunits(rs.getInt("orderdunits"));
					order.setAddress(rs.getString("address"));
					order.setOrdereddate(rs.getDate("ordereddate"));
					order.setRequesteddate(
							rs.getDate("requesteddate"));
					order.setAccepted(rs.getBoolean("accepted"));
					order.setCancelled(rs.getBoolean("cancelled"));
					order.setConfirmed(rs.getBoolean("confirmed"));
					order.setBillamount(rs.getInt("billamount"));
					order.setCustomerid(rs.getString("customerid"));
					order.setProductid(rs.getInt("productid"));
			}
		}
		catch(SQLException | NullPointerException ex)
		{
			ex.printStackTrace();
			return null;
		}
		
		return order;
	}

	@Override
	public boolean changeOrderStatusToConfirmed(int orderId) {
		boolean status = false;
		
		try(Connection con = DbConnection.getDatabaseConnection()){
			
			PreparedStatement pst = 
					con.prepareStatement(
"update productorder set confirmed = true where orderid = ?");
			
			pst.setInt(1, orderId);
			
			int count = pst.executeUpdate();
			
			if(count > 0)
				status = true;
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
			return false;
		}
		
		return status;
	}

	@Override
	public boolean changeOrderStatusToCancelled(int orderId) {
		boolean status = false;
		
		try(Connection con = DbConnection.getDatabaseConnection()){
			
			PreparedStatement pst = 
					con.prepareStatement(
"update productorder set cancelled = 1 where orderid = ?");
			
			pst.setInt(1, orderId);
			
			int count = pst.executeUpdate();
			
			if(count > 0)
				status = true;
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
			return false;
		}
		
		return status;
	}	
	
	
	@Override
	public List<Order> getAllOrdersForUser(String customerid) {
		List<Order> orderList = new ArrayList<>();
		
		try(Connection con = DbConnection.getDatabaseConnection()){
			
			PreparedStatement pst = 
					con.prepareStatement(
							"select * from productorder"
							+ " where customerid = ?");
			
			pst.setString(1, customerid);
			
			ResultSet rs = pst.executeQuery();
			
			if(rs.isBeforeFirst())
			{
				while(rs.next())
				{
					Order order = new Order();
					order.setOrderid(rs.getInt("orderid"));
					order.setOrderedunits(rs.getInt("orderdunits"));
					order.setAddress(rs.getString("address"));
					order.setOrdereddate(rs.getDate("ordereddate"));
					order.setRequesteddate(
							rs.getDate("requesteddate"));
					order.setAccepted(rs.getBoolean("accepted"));
					order.setCancelled(rs.getBoolean("cancelled"));
					order.setConfirmed(rs.getBoolean("confirmed"));
					order.setBillamount(rs.getInt("billamount"));
					order.setCustomerid(rs.getString("customerid"));
					order.setProductid(rs.getInt("productid"));
					
					orderList.add(order);
				}
			}
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
			orderList.clear();
			return orderList;
		}
		
		return orderList;
	}

	@Override
	public boolean updateOrderAddress(int orderid,
			String address) {
		boolean status = false;
		
		try (Connection con = DbConnection.getDatabaseConnection())
		{
			PreparedStatement pst = 
con.prepareStatement("UPDATE productorder SET address = ? "
		+ "WHERE orderid = ?");
			
			pst.setString(1, address);
			pst.setInt(2, orderid);
			
			int count = pst.executeUpdate();
			
			if(count > 0)
				status = true;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return status;
	}
	
	@Override
	public int[] getProductDetailsFromOrderId(Order o) {
        int[] details = new int[2]; // Assuming you want to return two integers
        
        String query = "SELECT productid, quantity FROM product WHERE productid = ?";
        
        try (Connection connection = DbConnection.getDatabaseConnection()){
             PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, o.getOrderid()); // Assuming there's a getOrderId() method in Order
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    details[0] = resultSet.getInt("productid");
                    details[1] = resultSet.getInt("quantity");
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
        
        return details;
    }
	
	@Override
	public boolean getStatus(int orderId) {
        boolean status = false;
        String query = "SELECT order_status FROM productorder WHERE productid = ?";
        
        try (Connection connection = DbConnection.getDatabaseConnection()){
             PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, orderId);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    status = resultSet.getBoolean("order_status");
                }
            }
        }catch (Exception e) {
            e.printStackTrace(); // Handle the exception appropriately
          }
        
        return status;
    }
	
	 @Override
	    public boolean cancelOrder(Order o) {
	        boolean success = false;
	        String updateQuery = "UPDATE productorder SET order_status = ? WHERE orderid = ?";
	        
	        try (Connection connection = DbConnection.getDatabaseConnection()){
	             PreparedStatement statement = connection.prepareStatement(updateQuery);
	            statement.setString(1, "cancelled"); // Assuming 'cancelled' is the status for cancelled orders
	            statement.setInt(2, o.getOrderid()); // Assuming there's a getOrderId() method in Order
	            
	            int rowsAffected = statement.executeUpdate();
	            
	            if (rowsAffected > 0) {
	                success = true;
	            }
	        } catch (Exception e) {
	            e.printStackTrace(); // Handle the exception appropriately
	        }
	        
	        return success;
	    }
}
