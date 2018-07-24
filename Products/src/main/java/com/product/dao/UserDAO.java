package com.product.dao;

import com.product.entity.Users;
import com.product.model.UserLogin;

public interface UserDAO {
	
	public void register(Users user);
	
	public Users validateUser(UserLogin userLogin);
	

}
