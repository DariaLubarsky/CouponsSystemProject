package app.core.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import app.core.entities.Coupon;
import app.core.exceptions.CouponSystemException;
import app.core.services.CustomerService;
import app.core.util.JwtUtil;

@RestController
@RequestMapping("/api/customer")
@CrossOrigin
public class CustomerController {

	@Autowired
	public CustomerService customerService;

	@Autowired
	private JwtUtil jwtutil;

	@GetMapping("/customers/purchase/{couponId}/{customerId}")
	public Coupon purchaseCoupon(@PathVariable int couponId, @PathVariable int customerId,
			@RequestHeader String Autorization) {
		try {
			String token = cleanAuth(Autorization);
			return this.customerService.purchaseCoupon(couponId, customerId, jwtutil.extractId(token));
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/customers/getCustomerCoupons")
	public List<Coupon> getCustomerCoupons(@PathVariable int customerId, @RequestHeader String Autorization) {
		try {
			String token = cleanAuth(Autorization);
			return this.customerService.getCustomerCoupons(customerId, jwtutil.extractId(token));
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/customers/getCoupondByCategory")
	public List<Coupon> getCoupondByCategory(@PathVariable int customerId, @RequestHeader String Autorization) {
		try {
			String token = cleanAuth(Autorization);
			return this.customerService.getCustomerCoupons(customerId, jwtutil.extractId(token));
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/customers/getCustomerDetails")
	public void getCustomerDetails(@RequestHeader String Autorization) {
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
