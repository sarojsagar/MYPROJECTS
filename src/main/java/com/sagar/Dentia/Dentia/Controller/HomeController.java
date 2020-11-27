package com.sagar.Dentia.Dentia.Controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sagar.Dentia.Dentia.Dao.ClientDao;
import com.sagar.Dentia.Dentia.Entities.Client;

@Controller
public class HomeController {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private ClientDao clientRepo;
	
	
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String home()
	{
		return "index";
	}
	
	@RequestMapping(value="/register", method=RequestMethod.GET)
	public String Registration(Model model)
	{
		model.addAttribute("client", new Client());
		return "Registration";
	}
	
	@RequestMapping(value="/doregister")
	public String saveUser(@ModelAttribute("client") Client client, BindingResult result, @RequestParam(value="agreement", defaultValue = "false")boolean agreement, Model model, HttpSession session)
	{
		try
		{

			if(!agreement)
			{
				System.out.println("You have not accepted our terms and conditions");
				throw new Exception("Accept our terms and conditions first");
			}
			if(result.hasErrors())
			{
				System.out.println("Error" + result.toString());
				model.addAttribute("client", client);
				return "Registration";
			}
			
			client.setRole("ROLE_ADMIN");
			client.setEnabled(true);
			client.setPassword(passwordEncoder.encode(client.getPassword()));

			Client results = clientRepo.save(client);
			System.out.println(results);
			model.addAttribute("client", client);
			session.setAttribute("message", new com.sagar.Dentia.Dentia.Helper.Message("Registered Successfylly", "alert-success"));
			return "Registration";
			
		}
		catch(Exception e)
		{
			session.setAttribute("message", new com.sagar.Dentia.Dentia.Helper.Message("something went wrong" + e.getMessage(), "alert-danger"));
			return "Registration";
		}
		
		
		
	}
	
	
	
	
	@RequestMapping(value="/signin", method=RequestMethod.GET)
	public String login()
	{
		return "login";
	}
	
	@RequestMapping(value="/about", method=RequestMethod.GET)
	public String adminlogin()
	{
		return "about";
	}

	@RequestMapping(value="/services", method=RequestMethod.GET)
	public String about()
	{
		
		return "services";
	}
}
