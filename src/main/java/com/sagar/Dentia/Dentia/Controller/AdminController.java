package com.sagar.Dentia.Dentia.Controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sagar.Dentia.Dentia.Dao.ClientDao;
import com.sagar.Dentia.Dentia.Dao.ServiceDao;
import com.sagar.Dentia.Dentia.Entities.Client;
import com.sagar.Dentia.Dentia.Entities.Service;

@Controller
@RequestMapping("/adminpage")
public class AdminController {
	
	
	@Autowired
	private ClientDao clientRepo;
	
	@Autowired
	private ServiceDao serviceRepo;
	
	
	
	@RequestMapping(value="/index", method=RequestMethod.GET)
	private String homeAdmin()
	{
		return "admin/index";
	}
	

	
	@RequestMapping(value="/client-list", method=RequestMethod.GET)
	private String clientDetail(Model model)
	{
		List<Client> clientlist = clientRepo.findAll();
		model.addAttribute("allClients", clientlist );
		return "admin/client-list";
	}
	
	@RequestMapping(value="/detail/{id}", method=RequestMethod.GET)
	private String clientDetail(@PathVariable("id") int id, Model model, Principal principal)
	{
		
		
		Optional<Client> clientOptional = clientRepo.findById(id);
		Client client = clientOptional.get();
		
		List<Service> serviceOptional = serviceRepo.findServiceById(client.getId());
		
		
		
		model.addAttribute("client", client);
		model.addAttribute("services", serviceOptional);
		
		return "/admin/client-detail";
	}
	
	
	
	

}
