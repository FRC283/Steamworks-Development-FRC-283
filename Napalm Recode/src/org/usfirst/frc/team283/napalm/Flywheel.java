package org.usfirst.frc.team283.napalm;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PWMSpeedController;

/**
 * A class that controls a flywheel. Reusable and reconfigurable.
 * @author Ben Ranson
 */
public class Flywheel 
{
	//Physical Components
	/** Motor controller that controls the actual wheel. Can be any class that extends PWMSpeedController */
	private PWMSpeedController controller;
	
	/** Reads the speed and position of the wheel. "tach" means tachometer. */
	private Encoder tach;
	
	
	//"Constants"
	/** Use .reverseController() to change. Only holds 1 or -1 */
	private double controllerPolarity = 1;
	
	/** Use .reverseTach() to change. Only holds 1 or -1 */
	private double tachPolarity = 1;
	
	/***/
	private double minRPM;
	/***/
	private double maxRPM;
	

	//Variables
	/** Value set to controller once per cycle. */
	private double power;
	
	/** Value added per robot cycle to flywheel power */
	private double powerAccel;
	
	/** The speed value we are attempting to approach. */
	private double targetSpeed;
	
	/** Value added per robot cycle to flywheel speed target */
	private double speedAccel;
	
	/** Whether or not we are currently trying to approach a target. */
	private boolean isTargeting = false;
	
	
	public Flywheel()
	{
		
	}

	/** Called once per robot cycle */
	public void periodic()
	{
		
	}
	
	/**
	 * Sets the motor power to the given number
	 * @param power - number to set power to
	 */
	public void setPower(double power)
	{
		this.power = power;
	}
	
	/**
	 * The power to the flywheel changes by the given amount per cycle
	 * @param delta - change in power per cycle
	 */
	public void setPowerAccel(double accel)
	{
		this.powerAccel = accel;
	}
	
	public void setTargetSpeed(double rpm)
	{
		this.targetSpeed = rpm;
		this.isTargeting = true;
	}
	
	public void setTargetSpeedAccel(double rpmChange)
	{
		this.speedAccel = rpmChange;
	}
	
	/**
	 * Calling once reverses all values set and received from the controller <br>
	 * Subsequent calls have no effect
	 */
	public void reverseController()
	{
		this.controllerPolarity = -1; //Do not change to "!value"
	}
	
	/**
	 * Calling once reverses all values set and received from the tachometer <br>
	 * Subsequent calls have no effect
	 */
	public void reverseTach()
	{
		this.tachPolarity = -1; //Do not change to "!value"
	}
	
}
