package com.grocerycashierqueue.simulator.main;

import com.grocerycashierqueue.simulator.buss.InputFileDataReaderHelper;
import com.grocerycashierqueue.simulator.buss.RegisterBuss;
import com.grocerycashierqueue.simulator.buss.TimeCalculator;
import com.grocerycashierqueue.simulator.exceptions.ApplicationException;
import com.grocerycashierqueue.simulator.exceptions.ErrorCode;

/**
 * TODO Put here a description of what this class does.
 *
 * @author hekedar. Created Aug 12, 2018.
 */
public class SimulatorMain {
	/**
	 * Main method to execute the run the simulator
	 *
	 * @param args (required) Data file name as first parameter
	 */
	public static void main(String[] args) {
		try {
			if (args == null || args.length == 0) {
				throw new ApplicationException(ErrorCode.FILE_NOT_PROVIDED_EXCEPTION);
			}
			SimulatorMain simulator = new SimulatorMain();
			int time = simulator.runSimulator(args[0]);
			String output = String.format("Finished at : t=%d minutes", time);
			System.out.print(output);
		} catch (ApplicationException e) {
			System.out.print("Error: " + e.getErrorMessage());
		}
	}

	/**
	 * Run simulator 
	 *
	 * @param fileName (required) Data file name as first parameter
	 * @return total time required by cashier to serve all customers.
	 */
	public int runSimulator(String fileName) {
		InputFileDataReaderHelper readFileData = new InputFileDataReaderHelper();
		readFileData.readData(fileName);
		RegisterBuss registerHelper = new RegisterBuss(readFileData.getNumOfRegisters());

		TimeCalculator timeCalculator = new TimeCalculator();
		return timeCalculator.calculate(registerHelper, readFileData.getTimeAndCustomerMapping());
	}
}
