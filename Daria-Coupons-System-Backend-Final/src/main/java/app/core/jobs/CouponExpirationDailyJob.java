//package app.core.jobs;
//
//import java.time.LocalDate;
//import java.util.concurrent.TimeUnit;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import app.core.repository.CouponRepository;
//
//@Component
//
//public class CouponExpirationDailyJob {
//
//	@Autowired
//	private CouponRepository couponRepository;
//
//	@Transactional
//	@Scheduled(timeUnit = TimeUnit.SECONDS, fixedRate = 5)
//	public void deleteExpired() {
//
//		try {
//
//			System.out.println("--- daily job started ---");
//			System.out.println("--- deleting coupons expired by " + LocalDate.now() + " ---");
//
//			couponRepository.deleteByEndDateBefore(LocalDate.now());
//
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//	}
//
//}