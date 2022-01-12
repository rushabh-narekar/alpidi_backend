package com.alpidi.security.sevices;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alpidi.model.AssignOrdersPrintHouse;
import com.alpidi.model.ListingResult;
import com.alpidi.model.OrderDetails;
import com.alpidi.repository.AssignedPrintHouseRepository;
import com.alpidi.repository.OrdersRepository;

@Service
public class AssignPrinthouseServices {
	@Autowired
	AssignedPrintHouseRepository printhouseRepository;
	@Autowired
	OrdersRepository orderRepository;
	
	public Boolean AssignOrderToPrinhouse(AssignOrdersPrintHouse assignOrdersPrintHouse) {
	
		Boolean checkStatus = printhouseRepository.existsByOrderid(assignOrdersPrintHouse.getOrderid());
		if(checkStatus==true)
		{
			printhouseRepository.deleteAssignedOrdersPrintHouseByOrderid(assignOrdersPrintHouse.getOrderid());
		}
		printhouseRepository.save(assignOrdersPrintHouse);
		
		return true;
	}
	
	public AssignOrdersPrintHouse getAssignPrinthouseFromOrderId(String orderid)
	{
		Optional<AssignOrdersPrintHouse> objAssignPrinthouse= printhouseRepository.findByOrderid(orderid);
		
		return objAssignPrinthouse.get();
	}
	
	public List<OrderDetails> getPrintHouseOrders(String printhouseid,Boolean isShipped,String query)
	{		
		List<AssignOrdersPrintHouse> listAssignPrinthouse= printhouseRepository.findByPrintHouseUserId(printhouseid);
		System.out.println(listAssignPrinthouse);
		List<OrderDetails> listOrders = new ArrayList<OrderDetails>();
		for(int i=0;i<listAssignPrinthouse.size();i++)
		{
			
			Optional<OrderDetails> objOrder = orderRepository.findByreceiptid(listAssignPrinthouse.get(i).getOrderid());
			if(!objOrder.isEmpty())
			{
				listOrders.add(objOrder.get());
			}
			//listAssignPrinthouse.get(i).setPrinthouseOrders(listOrders);
		}
		
		return listOrders;
	}
}
