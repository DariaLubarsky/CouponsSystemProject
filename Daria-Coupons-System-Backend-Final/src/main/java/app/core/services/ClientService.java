package app.core.services;

import org.springframework.beans.factory.annotation.Autowired;

import app.core.repository.CompanyRepository;
import app.core.repository.CouponRepository;
import app.core.repository.CustomerRepository;

public abstract class ClientService {

	@Autowired
	protected CompanyRepository companyRepository;
	@Autowired
	protected CouponRepository couponRepository;
	@Autowired
	protected CustomerRepository customerRepository;

	public abstract boolean login(String email, String password);

}
