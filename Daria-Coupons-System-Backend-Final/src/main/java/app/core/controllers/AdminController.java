package app.core.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import app.core.entities.Company;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;
import app.core.services.AdminService;
import app.core.util.JwtUtil;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin
public class AdminController {

	@Autowired
	public AdminService adminService;
	@Autowired
	private JwtUtil jwtutil;

//	=============================Companies service=============================

	@PostMapping("/companies/addCompany/")
	public int addcompany(@RequestBody Company company, @RequestHeader String Autorization) {
		try {
			return this.adminService.addCompany(company);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PutMapping("/companies/updateCompany")
	public void updateCompany(@RequestBody Company company, @RequestHeader String Autorization) {
		try {
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@DeleteMapping("/companies/deleteCompany")
	public void deleteCompany(@RequestBody int companyId, @RequestHeader String Autorization) {
		System.out.println("admin controller delete company");
		try {
			this.adminService.deleteCompany(companyId);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/companies/getAllCompanies")
	public List<Company> getAllCompanies(@RequestHeader String Autorization) {
		try {
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		return adminService.getAllCompanies();

	}

	@GetMapping("/companies/getCompany")
	public void getCompany(@RequestBody int id, @RequestHeader String Autorization) {
		try {
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	// =============================Customers service=============================

	@PostMapping("/customers/addCustomer")
	public int addCustomer(@RequestBody Customer customer, @RequestHeader String Autorization) {
		try {
			String token = cleanAuth(Autorization);
			return this.adminService.addCustomer(customer, jwtutil.extractId(token));
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PutMapping("/customers/updateCustomer/{customerId}")
	public Customer updateCustomer(@RequestBody Customer customer, @PathVariable int customerId,
			@RequestHeader String Autorization) {
		try {
			String token = cleanAuth(Autorization);
			return this.adminService.updateCustomer(customer, customerId, jwtutil.extractId(token));
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/customers/getAllCustomers")
	public List<Customer> getAllCustomers(@RequestHeader String Autorization) {
		try {
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		return adminService.getAllCustomers();
	}

	@DeleteMapping("/customers/deleteCustomer")
	public void deleteCustomer(@RequestHeader String Autorization) {
		try {
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/customers/getOneCustomer")
	public void getOneCustomer(@RequestBody int id, @RequestHeader String Autorization) {
		try {
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	// =============================Token Rescue=============================
	public String cleanAuth(String auth) {
		String cleanToken = auth;
		if (cleanToken.contains("Bearer "))
			cleanToken = cleanToken.replace("Bearer ", "");
		return cleanToken;
	}
}
