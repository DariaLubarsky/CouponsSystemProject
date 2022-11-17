package app.core.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.exceptions.CouponSystemException;

@Service
//@Scope("prototype")
@Transactional
public class CompanyService extends ClientService {

	public int getCompanyId() {
		return companyId;
	}

	private int companyId;

	@Override
	public boolean login(String email, String password) {
		Optional<Company> opt = this.companyRepository.findByEmailAndPassword(email, password);
		if (opt.isPresent()) {
			this.companyId = opt.get().getId();
			return true;
		}
		return false;
	}

	public List<Coupon> getCompanyCoupons(int companyId, int extractId) throws CouponSystemException {
		return this.couponRepository.findByCompanyId(this.companyId);
	}

	public Coupon addCoupon(Coupon coupon, int companyId) throws CouponSystemException {
		if (couponRepository.existsByCompanyIdAndName(companyId, coupon.getName())) {
			throw new CouponSystemException("failed to add coupon - coupon: " + coupon.getName() + " already exists");
		}
		System.out.printf("coupon %d added successfully \n", coupon.getId());
		coupon.setCompany(getCompanyDetails(companyId));
		return couponRepository.save(coupon);
	}

	public Company getCompanyDetails(int id ) throws CouponSystemException {
		return companyRepository.findById(id)
				.orElseThrow(() -> new CouponSystemException("failed to get company " + id));
	}

	public Coupon updateCoupon(Coupon coupon, int couponId, int extractId) throws CouponSystemException {
		Optional<Coupon> opt = this.couponRepository.findById(couponId);
		if (opt.isPresent()) {
			Coupon couponFromDb = opt.get();
			couponFromDb.setCompany(coupon.getCompany());
			couponFromDb.setCategory(coupon.getCategory());
			couponFromDb.setDescription(coupon.getDescription());
			couponFromDb.setStartDate(coupon.getStartDate());
			couponFromDb.setEndDate(coupon.getEndDate());
			couponFromDb.setStock(coupon.getStock());
			couponFromDb.setPrice(coupon.getPrice());
		} else {
			throw new CouponSystemException("update company faild - not found: " + couponId);
		}
		return couponRepository.save(coupon);
	}
	

	public void deleteCoupon(int couponId, int extractId) throws CouponSystemException {
		if (!this.couponRepository.existsById(couponId)) {
			throw new CouponSystemException("delete coupon " + couponId + "faild.");
		} else {

			this.couponRepository.deleteById(couponId);
		}
	}

	public Company getCompanyByEmail(String email) {
		return companyRepository.findByEmail(email);
	}

}
