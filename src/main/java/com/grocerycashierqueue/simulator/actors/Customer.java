package com.grocerycashierqueue.simulator.actors;

import java.util.Comparator;

/**
 * One of the actor in the grocery store cashier queue simulator.<br>
 * Customer are of two type
 * <ul>
 * 	<li>{@link CustomerType} A
 * 	<li>{@link CustomerType} B
 * </ul>
 * Based on the customer type they are added to the {@link Register} queue
 * @author hekedar.
 *         Created Aug 12, 2018.
 */
public class Customer{
	private static final Integer NO_OF_ITEMS_SERVED_IN_TWO_MINUTES = 1;
	
	private CustomerType type;
	private Integer numberOfItems;
	/**
	 * This variable will be used when customer is served by trainee cashier to double the time taken for serving each item.
	 */
	private int itemSeveredByTrainee = NO_OF_ITEMS_SERVED_IN_TWO_MINUTES;
	
	/**
	 * Constructor <br>
	 * Create a customer object based on its type and number of items he is purchasing
	 *
	 * @param type (required){@link Customer}}
	 * @param numberOfItems (required) number of item the customer is purchasing
	 */
	public Customer(CustomerType type, Integer numberOfItems) {
		this.type = type;
		this.numberOfItems = numberOfItems;
	}
	
	/**
	 * Returns the type of Customer passed to the constructor
	 * @return Returns the type.
	 */
	public CustomerType getType() {
		return this.type;
	}
	/**
	 * Returns the number of items customer is purchasing passed to the constructor
	 * @return Returns the numberOfItems.
	 */
	public Integer getNumberOfItems() {
		return this.numberOfItems;
	}

	/**
	 * When a customer is being served by an expert register number of item is decrement by 1 at every serve.
	 *
	 * @return total number of remaining items
	 */
	public Integer itemsServedByExpert(){
        return --this.numberOfItems;
    }
	
	/**
	 * <p>
	 * 	Regular registers take one minute to process each customer's item. The register staffed by the <br>
	 *  cashier in training takes two minutes for each item
	 * </p>
	 * <p>
	 * So when the customer is served by the trainee cashier then the time taken by the cashier to serve an item <br>
	 * is doubled. For first call, itemServerdByTrainee is decremented and during next call actual numberOfItems<br>
	 * are decremented
	 * </p>
	 * @return total number of remaining items
	 */
	public Integer itemsServedByTrainee(){
		if(this.itemSeveredByTrainee > 0){
			--this.itemSeveredByTrainee;
		} else {
			this.itemSeveredByTrainee = NO_OF_ITEMS_SERVED_IN_TWO_MINUTES;
			--this.numberOfItems;
		}
		return this.numberOfItems;
    }
	
	/**
	 * <p>
	 *If two or more customers arrive at the same time, those with fewer items choose 
	 *registers before those with more, and if they have the same number of items then 
	 *sorting is based on the type of customer.</p>
	 *<p>
	 *	So the number of items are compared first, if no of items are same then customers are sorted based on {@link CustomerType} 
	 *</p>
	 */
	
	public static Comparator<Customer> sortByItemNumbersOrType  = new Comparator<Customer>() {
		@Override
        public int compare(Customer cust1, Customer cust2) {
			int compVal = cust1.numberOfItems.compareTo(cust2.numberOfItems);
		    if (compVal == 0) { //has same number of items
		        compVal = cust1.type.compareTo(cust2.type);
		    }
		    return compVal;
        }
	};
}
