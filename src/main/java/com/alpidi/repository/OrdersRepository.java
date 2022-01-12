package com.alpidi.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.alpidi.model.ListingResult;
import com.alpidi.model.OrderDetails;

public interface OrdersRepository extends MongoRepository<OrderDetails, String> {
	//delete orders
	Long deleteOrdersByreceiptid(String receiptid);
	Boolean existsByReceiptid(String orderid);
	/** Fetch Order by paginated */

	//Page<OrderDetails> findByShopsectionidAndStateAndTitleContainingOrderByLastmodifiedtimestampDesc(String shop_section_id,String state,String title,  Pageable pageable);
	Page<OrderDetails> findBySelleruseridInAndCountryisoAndTransactionsTitleContainingOrderByUpdatetimestampDesc(List<String> selleruserid, String countryiso,String title,Pageable pageable);	
	
	Page<OrderDetails> findBySelleruseridInAndIsshippedAndIsapprovedAndTransactionsTitleContainingOrderByUpdatetimestampDesc(List<String> selleruserid,Boolean isshipped,Boolean IsApproved,String title, Pageable pageable); 
	Page<OrderDetails> findBySelleruseridInAndIsshippedAndTransactionsTitleContainingOrderByUpdatetimestampDesc(List<String> selleruserid,Boolean isshipped,String title, Pageable pageable); 
	
	Page<OrderDetails> findBySelleruseridInAndIsshippedAndShipdateBetweenAndTransactionsTitleContainingOrderByUpdatetimestampDesc(List<String> selleruserid,Boolean isshipped,Date startdate,Date enddate,String title, Pageable pageable);
	
	List<OrderDetails> findByShipdateBetween(Date startDate, Date endDate);
	List<OrderDetails> findByShipdate(Date shipdate);
	
	/**End of pagination related functions*/

	List<OrderDetails> findBySelleruseridInAndIsshippedOrderByUpdatetimestampDesc(List<String> selleruserid,Boolean isShipped);
	Boolean existsByTransactionsListingid(String listingid);
	Optional<OrderDetails> findByreceiptid(String orderid);
	Optional<OrderDetails> findByReceiptidAndIsshippedAndTransactionsTitleContainingOrderByUpdatetimestampDesc(String receiptid,Boolean isShipped,String title);
	Page<OrderDetails> findByselleruseridOrderByUpdatetimestampDesc(String selleruserid,Pageable pageable);
	
}
