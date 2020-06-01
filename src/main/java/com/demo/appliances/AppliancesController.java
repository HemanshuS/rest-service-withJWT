/**
 * 
 */
package com.demo.appliances;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.appliances.jwt.JwtUtil;
import com.demo.entities.Appliance;
import com.demo.entities.AuthenticationRequest;
import com.demo.entities.AuthenticationResponse;
import com.demo.entities.Status;

/**
 * @author Himanshu
 *
 */
@CrossOrigin
@RestController
@Retryable(
        value = {RuntimeException.class},
        maxAttempts = 2, backoff = @Backoff(2000))
public class AppliancesController {
	
	 private static final Logger LOGGER = LoggerFactory.getLogger(AppliancesController.class);
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtTokenUtil;

	@Autowired
	private MyUserDetailsService userDetailsService;
	
	static Set<Appliance> appliances = new HashSet<Appliance>();
	private static Integer idGen = 0;
	private static String getNewId() {
		idGen++;
		return idGen.toString();
	}
	static {
		
		//DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
		//Date dateStr = new Date();
		String date = new SimpleDateFormat("yyyy-MM-dd",   Locale.getDefault()).format(new Date());
		
		Appliance  appliance1 = new Appliance(getNewId(),"1A","LG","LGfridge",date,Status.Inuse);
		Appliance  appliance2 = new Appliance(getNewId(),"2A","Daikin","Aircon1",date,Status.Inuse);
		appliances.add(appliance1);
		appliances.add(appliance2);
		
	}
	
	
	@GetMapping(path="/getAppliances")
	public List<Appliance> getAppliances() {
		

		//System.out.println(">>>"+ header);
		List<Appliance> namesList = new ArrayList<>(appliances);
		
		return namesList;
		
	}
	
	
	@PostMapping(path="/addAppliance",headers = "Accept=application/json")
	public Appliance addAppliance(@RequestBody Appliance appliance ) {
		
		if(appliances.contains(appliance)) {
			
			return null;
		}
		appliance.setId(getNewId()+"A");
		appliances.add(appliance);
		
		
		return appliance;	
		
	}
	
	
	@PostMapping(path="/passAnything")
	public String passAnything() {
		
		return "received";
	}
	/*@DeleteMapping(path="/deleteAppliance" ,headers = "Accept=application/json")
	public ResponseEntity<Void> removeAppliance(@RequestBody Appliance appliance ) {
		
		if (appliances.contains(appliance)) {
			appliances.remove(appliance);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();		
		
	}*/
	
	@PutMapping(path="/updateAppliance",headers = "Accept=application/json")
	public List<Appliance> updateAppliance(@RequestBody Appliance appliance ) {
		
		
		appliances.stream().forEach(x -> {
			if(x.getId().equals("")) {
				x.setBrand(appliance.getBrand());
			}
		});
			
		Set<Appliance> appSet = appliances.stream().filter(x -> !(x.getId().equals(appliance.getId()))).collect(Collectors.toSet());
		
		appSet.add(appliance);
		appliances = appSet;
		/*if(appliances.contains(appliance)) {
			
			appliances.remove(appliance);
			appliances.add(appliance);
		}
			*/	
		
		return new ArrayList<>(appliances);	
		
	}
	
	@PostMapping(value = "/authenticate" ,headers = "Accept=application/json")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
			);
		}
		catch (BadCredentialsException e) {
			LOGGER.error("Incorrect username or password");
			throw new Exception("Incorrect username or password", e);
		}


		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String jwt = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}

	
	@Recover
    public String recover(Throwable t) {
        LOGGER.info("SampleRetryService.recover");
        return "Error Class :: " + t.getClass().getName();
    }

}
