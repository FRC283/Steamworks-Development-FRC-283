package org.usfirst.frc.team283.napalm;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PWMSpeedController;
import edu.wpi.first.wpilibj.Timer;

/**
 * A class that controls a flywheel. Reusable and reconfigurable. 
 * <br>
 * 		<b>USAGE GUIDE</b>
 * <p>
 * 		Create an instance: 
 * 			<dd> new Flywheel(new Spark(PORT), new Encoder(PORT,PORT), 360); </dd> <br>
 * 
 * 		Configuring targeting:
 * 			<dd> flywheelObj.configureTargeting(1/1000, 20); </dd> <br>
 * 
 * 		There are two distinct systems for controlling the flywheel - setting a power and setting an rpm. <br>
 * 		Setting a power is instant. After an rpm is set, however, it will take a few seconds for the flywheel to tune itself to that rpm. <br>
 * 		<br>
 * 		There is a system of acceleration for both modes. Setting an rpm acceleration changes the target rpm each cycle <br> 
 * 		<br>
 * 		Setting an acceleration cancels the current rpm target. Setting a target cancels the current acceleration. <br>
 * 		<br>
 * 		Braking kills the power in the input number of seconds. Braking cancels any targeting or acceleration. Targeting or accelerating cancels any braking.
 * 
 * </p>
 * 
 * @author Ben Ranson
 */
public class Flywheel 
{
	//True Constants for Class Tuning
	/** The frequency that numbers are changed by in hertz. */
	private final int CHANGE_FREQUENCY = 8;
	
	
	//Component Objects
	/** Motor controller that controls the actual wheel. Can be any class that extends PWMSpeedController */
	private PWMSpeedController controller;
	
	/** Reads the speed and position of the wheel. "tach" means tachometer */
	private Encoder tach;
	
	/** Keeps track of braking and accelerating intervals */
	private Timer accelTimer;
	
	
	//"Constants" - set only once at intialization
	/** Use .reverseController() to change. Only holds 1 or -1 */
	private double controllerPolarity = 1;
	
	/** Use .reverseTach() to change. Only holds 1 or -1 */
	private double tachPolarity = 1;
	
	/** The number of ticks around the encoder */
	private int totalTicks;
	
	/** The p-control constant of proportionality */
	private double pConstant = 1/1000;
	
	/** Acceptable error for stopping p-control */
	private double pDeadzone = 50;

	
	//Variables
	/** Value set to controller once per cycle. */
	private double power = 0;
	
	/** Value added per second to flywheel power <br>
	 * also used for braking <br>
	 * units: power/sec */
	private double powerAccel = 0;
	
	/** The speed value we are attempting to approach. */
	private double targetRPM = 0;
	
	/** Value added per robot cycle to flywheel speed target <br>
	 * units: rpm/second */
	private double rpmAccel = 0;
	
	/** Whether or not we are currently trying to approach a target rpm. While this is true, p-control is active */
	private boolean isTargeting = false;
	
	/** True while the robot is currently braking. Setting this to false cancels braking */
	private boolean isBraking = false;
	
	/**
	 * Creates a flywheel instance
	 * @param c - Motor controller that controls flywheel - can be any class that extends this type
	 * @param e - Encoder that is attached to flywheel
	 * @param ticks - The number of ticks around the above encoder
	 */
	public Flywheel(PWMSpeedController c, Encoder e, int ticks)
	{
		this.controller = c;
		this.tach = e;
		this.totalTicks = ticks;
		this.accelTimer = new Timer();
		this.tach.setDistancePerPulse(1);
	}

	/** 
	 * Must be called once per robot cycle for this class to function <br>
	 * Handles p-control and acceleration <br>
	 * This function respects controller reversals
	 */
	public void periodic()
	{
		double prevPower = this.power;
		if (this.accelTimer.get() >= 1/CHANGE_FREQUENCY) //After ever certain period of time
		{
			if (this.isTargeting == true && this.isBraking == false) //If we are targeting and not braking
			{
				this.targetRPM += this.rpmAccel/CHANGE_FREQUENCY; //Change the RPM by the accel
				//Although it updates X times per second, it only changes by 1/X'th of the actual accel
				
				//P-Control
				/////
				double err = this.getUnfixedRPM() - this.targetRPM; //Discrepancy between target and current
				if (Math.abs(err) < this.pDeadzone) //If our error is within acceptable range
				{
					//POWER IS MAINTAINED, NOT SET TO 0.
					this.isTargeting = false; //Having reached rpm, we are done targeting
					//We could still be accelerating - leave that how it is
				}
				else //If we're not there yet
				{
					this.power += err * this.pConstant; //Our change in power is great for a greater discrepency in rpm
				}
				//////
			}
			else //If we are not targeting OR we are braking
			{
				this.power += this.powerAccel/CHANGE_FREQUENCY; //Used for braking as well as regular accel
			}
		}
		else if (this.isBraking == true && this.isTargeting == false) //Then, if we are braking
		{
			if ((prevPower > 0 && power < 0) || (prevPower < 0 && power > 0) || (power == 0)) //If the accel changed reached 0
			{
				//Stop everything! We braked!
				accelTimer.stop();
				this.isBraking = false;
				this.isTargeting = false;
				this.powerAccel = 0;
				this.power = 0;
			}
		}
		this.accelTimer.reset();
		this.accelTimer.start();
		
		controller.set(this.power * this.controllerPolarity);
		
		}
	
	/**
	 * Calling this function allows you to set values for two variables used in targeting (p-Control)
	 * @param k - P-Constant, multiplied by error. A higher value means faster but more inaccurate targeting
	 * @param e - Cutoff value, the acceptable rpm-error to stop tageting.
	 */
	public void configureTargeting(double k, double e)
	{
		this.pConstant = k;
		this.pDeadzone = e;
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
		this.isBraking = false;
	}
	
	/**
	 * The power to the flywheel changes by the given amount per second <br>
	 * Calling this function stops any speed targeting <br>
	 * @param delta - change in power per sec
	 */
	public void setPowerAccel(double accel)
	{
		this.powerAccel = accel;
		this.isTargeting = false;
		this.isBraking = false;
	}
	
	/**
	 * Causes the robot to use p-control to achieve the given RPM <br>
	 * Kills any current speedAccel <br>
	 * @param rpm - the RPM to approach ("target")
	 */
	public void setTargetRPM(double rpm)
	{
		this.targetRPM = rpm;
		this.rpmAccel = 0;
		this.isTargeting = true;
		this.isBraking = false;
	}
	
	/**
	 * Each robot cycle, the robot will add the given amount onto the <b>target</b> RPM <br>
	 * @param rpmChange - RPM added per sec
	 */
	public void setTargetRPMAccel(double rpmChange)
	{
		this.rpmAccel = rpmChange;
		this.isTargeting = true;
		this.isBraking = false;
	}
	
	/**
	 * Returns the value that the flywheel is attempting to achieve for rpm
	 * @return - the rpm the flywheel is approaching
	 */
	public double getTargetRPM()
	{
		return this.targetRPM;
	}
	
	/**
	 * Returns the current RPM <br>
	 * This function respects tachometer reversals
	 * @return - rotation rate in rpm
	 */
	public double getTrueRPM()
	{
		return (tach.getRate() / this.totalTicks) * this.tachPolarity;
	}
	
	/**
	 * RPM, doesn't respect reversals <br>
	 * Not callable externally
	 * @return - rotation rate in rpm
	 */
	private double getUnfixedRPM()
	{
		return (tach.getRate() / this.totalTicks);
	}
	
	/**
	 * Current power, from 0 to 1, being set to motor <br>
	 * This function respects controller reversals
	 * @return - the current power
	 */
	public double getPower()
	{
		return controller.get() * this.controllerPolarity;
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
	
	/**
	 * Calling this function eventually kills all power to the flywheel <br>
	 * Power will be stepped down by an even continuously until it is 0
	 * @param brakeTime - the time, in seconds, for all power to be cut to the motor. the motor will not be stopped at this end of this time - it will have momentum still. It's not perfect
	 */
	public void brake(double brakeTime)
	{
		this.powerAccel = -1 * controller.get() / brakeTime; //Power step-down per second
		this.targetRPM = 0;
		this.rpmAccel = 0;
		this.isBraking = true;
		this.isTargeting = false;
		accelTimer.reset();
		accelTimer.start();
	}
}
