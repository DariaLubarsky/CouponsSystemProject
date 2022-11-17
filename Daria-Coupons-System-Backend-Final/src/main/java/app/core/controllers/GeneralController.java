package app.core.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.core.entities.Coupon;
import app.core.services.AdminService;

@RestController
@RequestMapping("/api/general")
@CrossOrigin
public class GeneralController {

	@Autowired
	AdminService adminService;

	@GetMapping("/coupons/getAllCouponsNoTo")
	public List<Coupon> getAllCoupons() {
		return adminService.getAllCoupons();

	}


}
