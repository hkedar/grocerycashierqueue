package com.grocerycashierqueue.simulator.actors;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Cashiers are placed at Registers for serving {@link Customer}. 
 * Registers are numbered as 1,2,3...n
 * There are two types of Cashier
 * <ul>
 * 	<li>Expert: Serves each item of a customer in a <i>n</i> minutes</li>
 * 	<li>Trainee: Serves each item of a customer in a <i>2*n</i> minutes</li>
 * </ul>
 * <b>Grocery store always has a trainee cashier placed at the nth Register<b><br>
 * @author HEKEDAR.
 *         Created Aug 12, 2018.
 */
public class Register{
	private Queue<Customer> customerQueue = null;
    private Integer index;
    private boolean cashierTrainee;
    
    /**
     * Constructor<br>
     * Initialize a Register with an index number to maintain the order so that customers <br>
     * can be added to the CustomerQueue 
     *
     * @param index (required)number of the register 1,2,3...n
     * @param isCashierTrainee (required)set as true if the cashier is trainee
     */
    public Register(int index, boolean isCashierTrainee) {
        this.index = index;
        this.customerQueue = new LinkedList<Customer>();
        this.cashierTrainee = isCashierTrainee;
    }

	/**
	 * Get the list of customer serving at the register<br>
	 * 
	 * @return customerQueue.
	 */
	public Queue<Customer> getCustomerQueue() {
		return this.customerQueue;
	}

	/**
	 * Returns the sequence number of the register passed to the constructor
	 *  
	 * @return sequence number of the register
	 */
	public int getIndex() {
		return this.index;
	}

	/**
	 * True: Cashier is trainee
	 * False: Cashier is expert
	 * 
	 * @return cashier is trainee or expert
	 */
	public boolean isCashierTrainee() {
		return this.cashierTrainee;
	}
	
	/**
	 * Can be used for sorting list of register based on their sequence number they are placed.<br>
	 * The order is always in ascending order.
	 */
	public static Comparator<Register> sortByRegisterIndex = new Comparator<Register>() {
		@Override
        public int compare(Register regObj1, Register regObj2) {
			return regObj1.index.compareTo(regObj2.index);
        }
	};
	
	/**
	 * Can be used for sorting list of register based on the number of customer they are serving
	 * Register with less number of customer comes first
	 */
	public static Comparator<Register> sortByCustomerQueueSize = new Comparator<Register>() {
        @Override
        public int compare(Register regObj1, Register regObj2) {
            Integer size = regObj1.customerQueue.size();
            Integer size1 = regObj2.customerQueue.size();
            return size.compareTo(size1);
        }
    };
    
}
