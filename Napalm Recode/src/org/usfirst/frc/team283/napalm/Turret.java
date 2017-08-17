package org.usfirst.frc.team283.napalm;

public class Turret 
{
	/** Reads true if currently moving the turret */
	private boolean moveState;
	/** Last set target voltage */
	private double targetVoltage;
	
	Turret()
	{
		
	}
	
	/**
	 * Adjusts speed of yaw motor based on input of turret
	 * @param stickPosition - Adjusts the rate of yaw turning
	 */
	public void aim(double stickPosition)
	{
		
	}
	
	/**
	 * Moves the turret until it reads the specified voltage on the feedback sensor
	 * @param voltage
	 */
	public void moveInit(double voltage)
	{
		//Can change target even when it is not finished with current target
		targetVoltage = voltage;
	}
	
	public void movePeriodic()
	{
		if (moveState == true) //If we have a target
		{
			
		}
	}
}
