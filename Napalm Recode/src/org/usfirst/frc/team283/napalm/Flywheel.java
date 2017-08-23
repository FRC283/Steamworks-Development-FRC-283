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
	private double targetRPM;
	
	/** Value added per robot cycle to flywheel speed target */
	private double rpmAccel;
	
	/** Whether or not we are currently trying to approach a target rpm. */
	private boolean isTargeting = false;
	
	/**
	 * 
	 * @param c - Motor controller that controls flywheel - can be any class that extends this type
	 * @param e - Encoder that is attached to flywheel
	 */
	public Flywheel(PWMSpeedController c, Encoder e)
	{
		this.controller = c;
		this.tach = e;
	}

	/** Called once per robot cycle */
	public void periodic()
	{
		if (this.isTargeting == true) //Performs p-control to approach given rpm
		{
			this.targetRPM += this.rpmAccel; //Add on speed accel
			//Put p-control here
		}
		else
		{
			this.power += this.powerAccel; //If we are not targeting a speed, add power accel
		}
		this.controller.set(this.power);
	}
	
	/**
	 * Sets the motor power to the given number <br>
	 * Calling this function stops any speed targeting and stops power acceleration
	 * @param power - number to set power to
	 */
	public void setPower(double power)
	{
		this.power = power;
		this.powerAccel = 0;
		this.isTargeting = false;
	}
	
	/**
	 * The power to the flywheel changes by the given amount per cycle <br>
	 * Calling this function stops any speed targeting
	 * @param delta - change in power per cycle
	 */
	public void setPowerAccel(double accel)
	{
		this.powerAccel = accel;
		this.isTargeting = false;
	}
	
	/**
	 * Causes the robot to use p-control to achieve the given RPM <br>
	 * Kills any current speedAccel
	 * @param rpm - the RPM to approach ("target")
	 */
	public void setTargetRPM(double rpm)
	{
		this.targetSpeed = rpm;
		this.speedAccel = 0;
		this.isTargeting = true;
	}
	
	/**
	 * Each robot cycle, the robot will add the given amount onto the target RPM
	 * @param rpmChange - RPM added per cycle
	 */
	public void setTargetRPMAccel(double rpmChange)
	{
		this.speedAccel = rpmChange;
		this.isTargeting = true;
	}
	
	public void brake()
	{
		
	}
	
	public void setBrake()
	{
		
	}
	
	public double getTargetRPM()
	{
		this.targetSpeed;
	}
	
	public double getRPM()
	{
		
	}
	
	
	/**
	 * Calling once reverses all values set and received from the controller <br>
	 * Subsequent calls have no effect
	 */
	public void reverseController()
	{
		this.controllerPolarity = -1; //Do not change to "!value" or similar
	}
	
	/**
	 * Calling once reverses all values set and received from the tachometer <br>
	 * Subsequent calls have no effect
	 */
	public void reverseTach()
	{
		this.tachPolarity = -1; //Do not change to "!value" or similar
	}
	
}
