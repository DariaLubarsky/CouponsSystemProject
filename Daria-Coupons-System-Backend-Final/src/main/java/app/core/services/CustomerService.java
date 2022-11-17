package app.core.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.persistence.EnumType;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import app.core.entities.Coupon;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;

@Service
//@Scope("prototype")
@Transactional
public class CustomerService extends ClientService {

	public int getCustomerId() {
		return customerId;
	}

	private int customerId;
	
	private EnumType couponCategory;

	@Override
	public boolean login(String email, String password) {
		Optional<Customer> opt = this.customerRepository.findByEmailAndPassword(email, password);
		if (opt.isPresent()) {
			this.customerId = opt.get().getId();
			return true;
		}
		return false;
	}

	public List<Coupon> getCustomerCoupons(int customerId, int extractId) {
		return this.couponRepository.findByCustomersId(this.customerId);

	}

	public List<Coupon> getCoupondByCategory(int customerId, int extractId, EnumType couponCategory) {
		return this.couponRepository.findByCustomersIdAndCategory(this.customerId, this.couponCategory);
	}


	public Customer getCustomerDetails() throws CouponSystemException {
		Optional<Customer> opt = customerRepository.findById(this.customerId);
		if (opt.isPresent()) {
			return opt.get();
		} else {
			throw new CouponSystemException("failed to get customer " + this.customerId);
		}

	}

	public Coupon purchaseCoupon(int couponId, int customerId, int extractId) throws CouponSystemException {
		if (!couponRepository.existsById(couponId)) {
			Coupon coupon = couponRepository.findById(couponId).get();
			if (couponRepository.existsByIdAndCustomersId(coupon.getId(), customerId)) {
				if (coupon.getStock() > 0 && coupon.getEndDate().isAfter(LocalDate.now())) {
					getCustomerDetails().addCoupon(coupon);
					coupon.setStock(coupon.getStock() - 1);
					couponRepository.save(coupon);
					System.out.printf("coupon %d purchased successfully \n", coupon.getId());
					return (coupon);
				} else {
					throw new CouponSystemException(
							"failed to purchase coupon " + coupon.getId() + " - coupon expired or is out of stock");
				}
			} else {
				throw new CouponSystemException(
						"failed to purchase coupon " + coupon.getId() + " - coupon already purchased");
			}
		} else {
			throw new CouponSystemException("failed to purchase coupon " + couponId + " - coupon is not exist");
		}
	}

	public Customer getCustomerByEmail(String email) {
		return customerRepository.findByEmail(email);
	}

	
}
