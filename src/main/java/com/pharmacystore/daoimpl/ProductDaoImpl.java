package com.pharmacystore.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.pharmacystore.connection.DbConnection;
import com.pharmacystore.dao.ProductDao;
import com.pharmacystore.pojo.Product;

public class ProductDaoImpl implements ProductDao
{
	@Override
	public boolean addProduct(Product product) {
		try (Connection con = 
DbConnection.getDatabaseConnection()){
			
			PreparedStatement pst = 
con.prepareStatement("INSERT INTO product("
+ "productname,price,quantity,description,categoryId)"
+ " VALUES(?,?,?,?,?)");
			
			pst.setString(1, 
					product.getProductname());
			pst.setInt(2, product.getPrice());
			pst.setInt(3, product.getQuantity());
			pst.setString(4,product.getDescription());
			pst.setInt(5, product.getCategoryid());
			
			int count = pst.executeUpdate();
			
			if(count > 0)
				return true;
			else
				return false;
			
		} catch (NullPointerException |  
				SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteProduct(int productId) {
	    Connection con = null;
	    try {
	        con = DbConnection.getDatabaseConnection();

	        PreparedStatement pst = con.prepareStatement("DELETE FROM product WHERE productid = ?");
	        pst.setInt(1, productId);

	        int count = pst.executeUpdate();

	        return count > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    } catch (NullPointerException e) {
	        e.printStackTrace();
	        return false;
	    } finally {
	        try {
	            if (con != null) {
	                con.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}


	@Override
	public boolean updateProduct(Product product) {
		try (Connection con = 
				DbConnection.getDatabaseConnection()){
							
							PreparedStatement pst = 
				con.prepareStatement("UPDATE product SET "
				+ "productname = ? , price = ? , "
				+ "quantity = ? , description = ?"
				+ " WHERE productid = ?");
							
							pst.setString(1, 
									product.getProductname());
							pst.setInt(2, product.getPrice());
							pst.setInt(3, product.getQuantity());
							pst.setString(4,product.getDescription());
							pst.setInt(5, product.getProductid());
							
							int count = pst.executeUpdate();
							
							if(count > 0)
								return true;
							else
								return false;
							
						} catch (NullPointerException |  
								SQLException e) {
							e.printStackTrace();
							return false;
						}	
	}

	@Override
	public List<Product> getAllProducts(int start,
			int total) {
List<Product> productList = new ArrayList<>();
		
		try(Connection con = 
				DbConnection.getDatabaseConnection())
		{
			
			PreparedStatement pst = 
					con.prepareStatement(
			"SELECT * FROM product LIMIT ? , ?");
			
			pst.setInt(1, start-1);
			pst.setInt(2, total);
			
			ResultSet rs = pst.executeQuery();
			
			if(rs.isBeforeFirst())
			{
				while(rs.next())
				{
					Product product = new Product();
					product.setProductid(rs.getInt("productid"));
					product.setProductname(
							rs.getString("productname"));
					product.setPrice(rs.getInt("price"));
					product.setQuantity(rs.getInt("quantity"));
					product.setDescription(
							rs.getString("description"));
					product.setCategoryid(rs.getInt("categoryId"));
					
					productList.add(product);
				}
			}
			
			return productList;
		}
		catch(NullPointerException | SQLException
				 exc) {
			exc.printStackTrace();
			productList.clear();
			return productList;
		}
	}

	@Override
	public List<Product> getAllProducts() {
List<Product> productList = new ArrayList<>();
		
		try(Connection con = 
				DbConnection.getDatabaseConnection())
		{
			
			PreparedStatement pst = 
					con.prepareStatement(
			"SELECT * FROM product");
			
			ResultSet rs = pst.executeQuery();
			
			if(rs.isBeforeFirst())
			{
				while(rs.next())
				{
					Product product = new Product();
					product.setProductid(rs.getInt("productid"));
					product.setProductname(
							rs.getString("productname"));
					product.setPrice(rs.getInt("price"));
					product.setQuantity(rs.getInt("quantity"));
					product.setDescription(
							rs.getString("description"));
					product.setCategoryid(rs.getInt("categoryId"));
					
					productList.add(product);
				}
			}
			
			return productList;
		}
		catch(NullPointerException | SQLException
				 exc) {
			exc.printStackTrace();
			productList.clear();
			return productList;
		}
	}

	@Override
	public Product searchProduct(int productId) {
		Product pr = null;	
		
		try(Connection con = 
				DbConnection.getDatabaseConnection())
		{	
			PreparedStatement pst = 
					con.prepareStatement(
			"SELECT * FROM product WHERE "
			+ "productid = ?");
			
			pst.setInt(1, productId);
			
			ResultSet rs = pst.executeQuery();
			
			if(rs.isBeforeFirst())
			{
				rs.next();
				
				Product product = new Product();
				product.setProductid(rs.getInt("productid"));
				product.setProductname(
						rs.getString("productname"));
				product.setPrice(rs.getInt("price"));
				product.setQuantity(rs.getInt("quantity"));
				product.setDescription(
						rs.getString("description"));
				product.setCategoryid(rs.getInt("categoryId"));
			
				return product;
			}
		else 
			return null;
		}
		catch(NullPointerException | SQLException
				 exc) {
			exc.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean updateQuantityAfterOrder(int pid,
			int new_quantity) {
		
		try(Connection con = DbConnection.getDatabaseConnection()){
			PreparedStatement pst = 
con.prepareStatement("UPDATE product SET "
		+ "quantity = ? where productid = ?");
		
			pst.setInt(1, new_quantity);
			pst.setInt(2, pid);
			
			int count = pst.executeUpdate();
			
			if(count > 0) 
				return true;
			else
				return false;
		}
		catch(NullPointerException | SQLException ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	@Override
    public int getQuantityOfProduct(Product p) {
        int quantity = 0;
        String query = "SELECT quantity FROM product WHERE productid = ?";
        
        try (Connection connection = DbConnection.getDatabaseConnection()){
             PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, p.getProductid()); // Assuming there's a getProductId() method in Product
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    quantity = resultSet.getInt("quantity");
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
        
        return quantity;
    }
	
	@Override
    public void updateQuantityAfterCancellation(Product p) {
        String updateQuery = "UPDATE product SET quantity = ? WHERE productid = ?";
        
        try (Connection connection = DbConnection.getDatabaseConnection()){
             PreparedStatement statement = connection.prepareStatement(updateQuery);
            statement.setInt(1, p.getQuantity()); // Assuming there's a getQuantity() method in Product
            statement.setInt(2, p.getProductid()); // Assuming there's a getProductId() method in Product
            
            int rowsAffected = statement.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Quantity updated successfully.");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }
	
	 @Override
	    public List<Product> displayProductsCategoryWise(int categoryId) {
	        List<Product> productList = new ArrayList<>();

	        try (Connection con = DbConnection.getDatabaseConnection()){
	             PreparedStatement ps = con.prepareStatement("SELECT * FROM product WHERE categoryId = ?");
	            ps.setInt(1, categoryId);
	            ResultSet rs = ps.executeQuery();

	            while (rs.next()) {
	                Product product = new Product();
	                product.setProductid(rs.getInt("productid"));
	                product.setProductname(rs.getString("productname"));
	                // Set other attributes as needed
	                productList.add(product);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            // Handle the exception as needed
	        }

	        return productList;
	    }
}
