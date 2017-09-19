package org.usfirst.frc.team283.napalm;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 * Handles communications between the robot and drive station, as well as the robot and acer
 * @author BenR
 */
public class CommSubsystem 
{
	/** Internal table */
	private NetworkTable table;
	
	/** Used to find the cycle frequency */
	private Timer cycleTimer;
	
	/** Number of robot cycles completed per second */
	private double cycleFreq;
	
	/** Take a guess. */
	private int cycleCount = 0;
	
	
	CommSubsystem(String tableName)
	{
		cycleTimer = new Timer();
		cycleTimer.start();
		table = NetworkTable.getTable(tableName);
	}
	
	public void periodic()
	{
		if (this.cycleTimer.get() >= 1) //If one second has past
		{
			this.cycleTimer.reset();
			this.cycleTimer.start(); //Restart the timer
			this.cycleFreq = cycleCount; //Assign the current cycles that passed to the frequency
			this.cycleCount = 0; //Restart the count
		}
		else //If one second is still going on
		{
			this.cycleCount++; //Up the cycle count
		}
	}
	
	/**
	 * Used to diagnose the robot.
	 * @return - the loop frequency of the robot
	 */
	public double getCyclesPerSec()
	{
		return this.cycleFreq;
	}
	
	public void putNumber(){}
	
	/**
	 * Returns the specified number off the internal table
	 * @param key - key of value to be retrieved
	 * @return - value, -999999999999 if key doesnt exist
	 */
	public double getNumber(String key)
	{
		return this.table.getNumber(key, -999999999); //Returns max negative integer value if no response
	}
	
	public void putString(){}
	
	/**
	 * Returns the specified bool off the internal table
	 * @param key - key of value to be retrieved
	 * @return - value, NO_VALUE if key doesnt exist
	 */
	public String getString(String key)
	{
		return this.table.getString(key, "NO_VALUE_FOR_KEY \'" + key + "\'"); //Returns an error string when invalid call
	}
	
	public void putBoolean(){}
	
	/**
	 * Returns the specified bool off the internal table
	 * @param key - key of value to be retrieved
	 * @return - value, false if key doesnt exist
	 */
	public boolean getBoolean(String key)
	{
		return this.table.getBoolean(key, false);
	}

	public void putNumArray(){}
	
	/**
	 * Returns the specified double array off the internal table
	 * @param key
	 * @return - value, empty array if doesnt exist
	 */
	private double[] getNumArray(String key)
	{
		double[] a = {};
		return this.table.getNumberArray(key, a);
	}
}
