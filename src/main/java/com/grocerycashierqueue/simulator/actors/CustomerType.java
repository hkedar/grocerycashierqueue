package com.grocerycashierqueue.simulator.actors;

/**
 * Type of Customer the cashier will be serving
 *
 * @author HEKEDAR.
 *         Created Aug 12, 2018.
 */
public enum CustomerType {
	/**
	 * Customer Type A always chooses the register with the shortest line <br>
	 * (fewest number of customers in line).
	 */
	A,
	
	/**
	 * Customer Type B looks at the last customer in each line, and always chooses <br>
	 * to be behind the customer with the fewest number of items left to check out, <br>
	 * regardless of how many other customers are in the line or how many items they have.<br>
	 * Customer Type B will always choose an empty line before a line with any customers in it.
	 */
	B
}
