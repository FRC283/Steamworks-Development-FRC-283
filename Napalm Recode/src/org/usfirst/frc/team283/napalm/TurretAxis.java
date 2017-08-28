package org.usfirst.frc.team283.napalm;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PWMSpeedController;

/**
 * A class for controlling a turret in one axis using an encoder (not potentiometer)
 * 
 * @author Benjamin
 */
public class TurretAxis
{
	//Component Objects
	/** Controls motion of axis */
	private PWMSpeedController controller;
	
	/** Provides feedback on axis */
	private Encoder encoder;
	
	/** Limit switch at smallest end of axis */
	private DigitalInput minLimit;
	
	/** Limit switch at largest end of axis */
	private DigitalInput maxLimit;
	
	
	//Variables
	/** Reads true if currently moving the turret */
	private boolean moveState = false;
	
	/** Last set target tick */
	private int target;
	
	/** Current power set to controller */
	private double power = 0;
	
	/** Current target velocity in ticks/sec */
	private double targetSpeed = 0;
	
	
	//"Constants" - set only once at initialization
	/** Maximum safe encoder position */
	private int maxPosition;
	
	/** Min safe encoder position */
	private int minPosition;
	
	/** Controls default direction of controller */
	private int controllerPolarity = 1;
	
	/** Controls default direction of encoder */
	private int encoderPolarity = 1;
	
	/**
	 * 
	 * @param c
	 * @param e
	 * @param min
	 * @param max
	 */
	public TurretAxis(PWMSpeedController c, Encoder e, int min, int max)
	{
		this.encoder = e;
		this.encoder.setDistancePerPulse(1);
	}
	
	/**
	 * 
	 * @param left
	 * @param right
	 */
	public void addLimits(DigitalInput left, DigitalInput right)
	{
		
	}
	
	public void periodic()
	{
		if (moveState == true) //If we have a target
		{
			
		}
		this.controller.set(this.power);
	}
	
	/**
	 * Adjusts speed of yaw motor based on input of turret
	 * @param stickPosition - Adjusts the rate of yaw turning
	 */
	public void setSpeed(double ticksPerSec)
	{
		this.targetSpeed = ticksPerSec;
	}
	
	/**
	 * Moves the turret until it reads the specified tick on the feedback sensor
	 * @param position
	 */
	public void setPosition(int position)
	{
		//Can change target even when it is not finished with current target
		this.target = position;
	}
	
	public void home()
	{
		
	}
	
	public void zero()
	{
		
	}
	
	public void reverseController()
	{
		
	}
	
	public void reverseEncoder()
	{
		
	}
	
	public void calibrate(int centerTick)
	{
		
	}
	
	public double getPower()
	{
		return this.power;
	}
}
