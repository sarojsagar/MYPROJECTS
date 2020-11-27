package com.sagar.Dentia.Dentia.Controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sagar.Dentia.Dentia.Dao.ClientDao;
import com.sagar.Dentia.Dentia.Dao.ServiceDao;
import com.sagar.Dentia.Dentia.Entities.Client;
import com.sagar.Dentia.Dentia.Entities.Service;
import com.sagar.Dentia.Dentia.Helper.Message;

@Controller
@RequestMapping("/clientpage")
public class ClientController {
	
	@Autowired
	private ClientDao clientRepo;
	
	@Autowired
	private ServiceDao serviceRepo;
	
	
	@RequestMapping(value="/index", method=RequestMethod.GET)
	private String index()
	{
		return "normal/index";
	}
	
	@ModelAttribute
	public void addCommon(Model model, Principal pricipal)
	{
		String userName = pricipal.getName();
	    
		model.addAttribute("User", clientRepo.getEmailByUserName(userName));
	}
	
	
	@RequestMapping(value="/bookappointment", method=RequestMethod.GET)
	public String bookAppointment(Model model)
	{
		model.addAttribute("service", new Service());
		return "normal/bookappointment";
		
	}
	
	@RequestMapping(value="/saveOppointment", method=RequestMethod.POST)
	public String saveAppointment(@ModelAttribute Service service, Model model, Principal principal
			,  HttpSession session)
	{
		try {
				String userName = principal.getName();
				Client client = clientRepo.getEmailByUserName(userName);
				
				service.setClient(client);
				client.getServices().add(service);
				clientRepo.save(client);
				System.out.println("data" + service);
				System.out.println("user added");
				session.setAttribute("message", new Message("Appointment booked successfully", "success"));
		
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
			session.setAttribute("message", new Message("Something went wrong", "danger"));
		}
		return "normal/bookappointment";
		
	}
	
	
	@RequestMapping(value="/show-appointment", method=RequestMethod.GET)
	public String showContacts(Model model, Principal pricipal) {
		
		String UserName = pricipal.getName();
		
		Client client = clientRepo.getEmailByUserName(UserName);
		
		List<Service> service = serviceRepo.findServiceById(client.getId());
		
		
		
		model.addAttribute("services", service);
		
		
		return "normal/appointment";
	}
	
	
	
	
	
	
	@RequestMapping(value="/cancel/{sid}", method=RequestMethod.GET)
	public String cancelAppointmentt(@PathVariable("sid") Integer sid, Model model, HttpSession session, Principal principal)
	{
		Optional<Service> serviceOptional = serviceRepo.findById(sid);
		Service service = serviceOptional.get();
		//contact.setUser(null);
		
		Client client = this.clientRepo.getEmailByUserName(principal.getName());
		client.getServices().remove(service);
		this.clientRepo.save(client);
		session.setAttribute("message", new Message("Contact deleted successfully", "success"));
		
		
		return "redirect:/clientpage/show-appointment/";
		
	}
	

}
