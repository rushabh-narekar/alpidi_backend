package com.alpidi.security.sevices;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.alpidi.model.ListingResult;
import com.alpidi.repository.ListingRepository;
import com.mongodb.client.result.UpdateResult;

@Component
public class ListingImpl implements ListingRepository {
	@Autowired
    MongoTemplate mongoTemplate;
	
	@Override
	public <S extends ListingResult> List<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ListingResult> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ListingResult> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends ListingResult> S insert(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends ListingResult> List<S> insert(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends ListingResult> List<S> findAll(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends ListingResult> List<S> findAll(Example<S> example, Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<ListingResult> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends ListingResult> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<ListingResult> findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existsById(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<ListingResult> findAllById(Iterable<String> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteById(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(ListingResult entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAllById(Iterable<? extends String> ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll(Iterable<? extends ListingResult> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <S extends ListingResult> Optional<S> findOne(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends ListingResult> Page<S> findAll(Example<S> example, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends ListingResult> long count(Example<S> example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <S extends ListingResult> boolean exists(Example<S> example) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Page<ListingResult> findByShopsectionidAndStateAndTitleContainingOrderByPriorityDescLastmodifiedtimestampDesc(
			String shop_section_id, String state, String title, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<ListingResult> findByStateAndTitleContainingOrderByPriorityDesc(String state, String title,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<ListingResult> findByTitleContainingOrderByPriorityDesc(String title, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long deleteListingByListingidAndShopid(String listingid, String shopid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long deleteByListingidIn(List<String> listingids) {
		return null;
		// TODO Auto-generated method stub
		
	}

	@Override
	public Boolean existsByListingidAndShopid(String listingid, String shopid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<ListingResult> findByListingidAndShopid(String listingid, String shopid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<ListingResult> findByListingid(String listingid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<ListingResult> findOneByListingid(String listingid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ListingResult> findByShopsectionidAndState(String shop_section_id, String state) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object> findlistingidAndExcludeIdByShopid(String shopid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ListingResult> findByShopidAndUserid(String shopid, String userid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ListingResult> findByShopid(String shopid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ListingResult> findByShopidAndState(String shopid, String state) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void updateItemSkuNumber(String listingid, String skunumber) {
        Query query = new Query(Criteria.where("listingid").is(listingid));
        Update update = new Update();
        update.set("skunumber", skunumber);
        
        UpdateResult result = mongoTemplate.updateFirst(query, update, ListingResult.class);
        
        if(result == null)
            System.out.println("No documents updated");
        else
            System.out.println(result.getModifiedCount() + " document(s) updated..");

    }

}
