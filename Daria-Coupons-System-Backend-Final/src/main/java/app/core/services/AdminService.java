package app.core.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;

@Service
@Transactional
public class AdminService extends ClientService {
	
	@Value("${client.admin.email}")
	private String email;
	@Value("${client.admin.password}")
	private String password;

	@Override
	public boolean login(String email, String password) {
		return email.equals(this.email) && password.equals(this.password);
	}

	public int addCompany(Company company) throws CouponSystemException {
		if (this.companyRepository.existsByName(company.getName())) {
			throw new CouponSystemException("add company faild - name already exist: " + company.getName());
		}
		if (this.companyRepository.existsByEmail(company.getEmail())) {
			throw new CouponSystemException("add company faild - email already exist: " + company.getEmail());

		}
		return this.companyRepository.save(company).getId();
	}

	public int addCustomer(Customer customer, int customerId ) throws CouponSystemException {
		if (this.customerRepository.existsByFirstNameAndLastName(customer.getFirstName(), customer.getLastName())) {
			throw new CouponSystemException(
					"add customer faild - name already exist: " + customer.getFirstName() + customer.getLastName());
		}
		return this.customerRepository.save(customer).getId();
	}

	public void updateCompany(Company company) throws CouponSystemException {
		Optional<Company> opt = this.companyRepository.findById(company.getId());
		if (opt.isPresent()) {
			Company companyFromDb = opt.get();
			companyFromDb.setEmail(company.getEmail());
			companyFromDb.setPassword(company.getPassword());
		} else {
			throw new CouponSystemException("update company faild - not found: " + company.getEmail());
		}
	}

	public void deleteCompany(int companyId) throws CouponSystemException {
		System.out.println("admin server delete company");
		if (this.companyRepository.existsById(companyId)) {
//			this.companyRepository.deleteById(companyId);
		} else {
			throw new CouponSystemException("delete company faild - not found: " + companyId);
		}
		this.companyRepository.deleteById(companyId);
	}

	public List<Company> getAllCompanies() {
		return this.companyRepository.findAll();

	}

	public Company getCompany(int companyId) throws CouponSystemException {
		Optional<Company> opt = this.companyRepository.findById(companyId);
		if (opt.isPresent()) {
			return opt.get();

		} else {
			throw new CouponSystemException("get company faild - not found: " + companyId);
		}
	}

	public List<Coupon> getCompanyCoupons(int companyID) throws CouponSystemException {
		return this.couponRepository.findAll();

	}

	
	public boolean isCustomerExists(Customer customer) throws CouponSystemException {
		if (this.customerRepository.existsByFirstNameAndLastName(customer.getFirstName(), customer.getLastName())) {
			return true;
		} else {
			throw new CouponSystemException("customer not found: " + customer.getFirstName(), customer.getLastName());
		}
	}
	
	public Customer updateCustomer(Customer customer, int customerId, int extractId) throws CouponSystemException {
		Optional<Customer> opt = this.customerRepository.findByFirstNameAndLastName(customer.getFirstName(),
				customer.getLastName());
		if (opt.isPresent()) {
			Customer customerFromDb = opt.get();
			customerFromDb.setFirstName(customer.getFirstName());
			customerFromDb.setPassword(customer.getLastName());
		} else {
			throw new CouponSystemException("update customer faild - not found: " + customer.getFirstName(),
					customer.getLastName());
		}
		return customerRepository.save(customer);
	}

	public Customer getOneCustomer(Customer customer) throws CouponSystemException {
		Optional<Customer> opt = this.customerRepository.findByFirstNameAndLastName(customer.getFirstName(),
				customer.getLastName());
		if (opt.isPresent()) {
			return opt.get();

		} else {
			throw new CouponSystemException(
					"Customer not found check your spelling: " + customer.getFirstName() + customer.getLastName());
		}
	}

	public List<Customer> getAllCustomers() {
		return this.customerRepository.findAll();

	}

	public void deleteCustomer(String firstName, String lastName) {
		if (this.customerRepository.existsByFirstNameAndLastName(firstName, lastName)) {
			this.customerRepository.deleteByFirstNameAndLastName(firstName, lastName);

		} else {
			throw new CouponSystemException("delete customer faild - not found: " + firstName, lastName);
		}
	}

	public void updateCoupon(Coupon coupon) throws CouponSystemException {
		Optional<Coupon> opt = this.couponRepository.findById(coupon.getId());
		if (opt.isPresent()) {
			Coupon couponFromB = opt.get();
			couponFromB.setId(coupon.getId());
		} else {
			throw new CouponSystemException("update coupon faild - not found: " + coupon.getId());
		}
	}

	public Coupon getOneCoupon(Coupon coupon) throws CouponSystemException {
		Optional<Coupon> opt = this.couponRepository.findById(coupon.getId());
		if (opt.isPresent()) {
			return opt.get();

		} else {
			throw new CouponSystemException("Coupon not found: " + coupon.getId());
		}
	}

	public List<Coupon> getAllCoupons() {
		return this.couponRepository.findAll();

	}

	public void deleteCoupon(int couponId) {
		if (this.couponRepository.existsById(couponId)) {
			this.couponRepository.deleteById(couponId);

		} else {
			throw new CouponSystemException("delete coupon faild - not found: " + couponId);
		}
	}

	public boolean isCouponExists(Coupon coupon) throws CouponSystemException {
		if (this.customerRepository.existsById(coupon.getId())) {
			return true;
		} else {
			throw new CouponSystemException("coupon not found: " + coupon.getId());
		}
	}

}
