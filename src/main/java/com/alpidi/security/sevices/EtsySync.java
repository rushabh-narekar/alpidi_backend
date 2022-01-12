package com.alpidi.security.sevices;

import com.alpidi.model.ListingResult;
import com.alpidi.model.User;

public interface EtsySync {
	void asyncInventoryMethod() throws InterruptedException;

	//void asyncStoreOrders(User userdata) throws InterruptedException;

	void asyncStoreOrders(String userid, Boolean isupdateinventory) throws InterruptedException;

	void asyncListingImagesMethod(String shopid, Boolean isupdateinventory) throws InterruptedException;

	void getInvetory(ListingResult listing) throws InterruptedException;
	
}
