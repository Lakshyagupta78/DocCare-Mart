package com.pharmacystore.dao;

import com.pharmacystore.pojo.User;
import java.util.List;
import com.pharmacystore.pojo.Order;

public interface UserDao {

	 boolean cancelOrder(Order o);
	 User getUserById(String userId);
	 boolean updateUser(User user);
	
	boolean register(User user);
	boolean checkUser(User user);
	List<Order> displaymycancelledOrder(User u);

}
