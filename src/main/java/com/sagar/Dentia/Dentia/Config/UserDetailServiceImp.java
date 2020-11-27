package com.sagar.Dentia.Dentia.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.sagar.Dentia.Dentia.Dao.ClientDao;
import com.sagar.Dentia.Dentia.Entities.Client;

public class UserDetailServiceImp implements UserDetailsService {

	@Autowired
	private ClientDao clientRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Client client = clientRepo.getEmailByUserName(username);
		
		if(client==null)
		{
			throw new UsernameNotFoundException("User does not exitst");
		}
		
		CustomUserDetail customUserDetail = new CustomUserDetail(client);
		
		
		return customUserDetail;
	}

}
