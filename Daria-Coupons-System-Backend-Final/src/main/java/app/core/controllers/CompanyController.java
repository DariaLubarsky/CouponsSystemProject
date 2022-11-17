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
import app.core.entities.Coupon;
import app.core.exceptions.CouponSystemException;
import app.core.services.CompanyService;
import app.core.util.JwtUtil;

@RestController
@RequestMapping("/api/company")
@CrossOrigin
public class CompanyController {

	@Autowired
	private JwtUtil jwtutil;

	@Autowired
	public CompanyService companyService;

	@PostMapping("/coupons/addCoupon")
	public Coupon addCoupon(@RequestBody Coupon coupon, @RequestHeader String Autorization) {
		try {
			String token = cleanAuth(Autorization);
			return this.companyService.addCoupon(coupon, jwtutil.extractId(token));
		} catch (CouponSystemException e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PutMapping("/coupons/updateCoupon/{couponId}")
	public Coupon updateCoupon(@RequestBody Coupon coupon, @PathVariable int couponId,
			@RequestHeader String Autorization) {
		try {
			String token = cleanAuth(Autorization);
			return this.companyService.updateCoupon(coupon, couponId, jwtutil.extractId(token));
		} catch (CouponSystemException e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@DeleteMapping("/coupons/deleteCoupon/{couponId}")
	public void deleteCoupon(@PathVariable int couponId, @RequestHeader String Autorization) {
		try {
			String token = cleanAuth(Autorization);
			this.companyService.deleteCoupon(couponId, jwtutil.extractId(token));
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@GetMapping("/companies/getCompanyDetailes/{companyId}")
	public Company getCompanyDetailes(@PathVariable int companyId, @RequestHeader String Autorization) {
		try {
			return this.companyService.getCompanyDetails(companyId);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/companies/getCompanyCoupons/{companyId}")
	public List<Coupon> getCompanyCoupons(@PathVariable int companyId, @RequestHeader String Autorization) {
		try {
			String token = cleanAuth(Autorization);
			return this.companyService.getCompanyCoupons(companyId, jwtutil.extractId(token));
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
