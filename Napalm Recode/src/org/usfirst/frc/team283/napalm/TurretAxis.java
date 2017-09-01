package org.usfirst.frc.team283.napalm;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PWMSpeedController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

/**
 * A class for controlling a turret in one axis <br>
 * Could use an encoder, a potentiometer, or no feedback
 * 		<b>USAGE GUIDE</b> <br>
 * <p>
 * 		Setting a speed cancels the current position <br>
 * 		Setting a position cancels the current speed <br>
 * </p>
 * TODO: Allow power control? Timeout for p-control? Generalize feedback sensors
 * @author Benjamin
 */
public class TurretAxis
{
	//True Constants for Class Tuning
	/**  Number of times per second the target position is updated via the speed. hertz. */
	private final int CHANGE_FREQUENCY = 8;
	
	
	//Component Objects
	/** Controls motion of axis */
	private PWMSpeedController controller;
	
	/** Provides feedback on axis */
	private Encoder encoder;
	
	/** Provides feedback on axis */
	private Potentiometer pot;
	
	/** Limit switch at smallest end of axis */
	private DigitalInput minLimit;
	
	/** Limit switch at largest end of axis */
	private DigitalInput maxLimit;
	
	/** Timing for changing of target position via speed */
	private Timer speedTimer;
	
	
	//Variables
	/** True while calibrating. No other actions can take place while calibrating. */
	private boolean isCalibrating = false;
	
	/** When true, overrides all other settings to control by power */
	private boolean isPowerControlling = false;
	
	/** Last set target tick */
	private int target;
	
	/** Current power set to controller */
	private double power = 0;
	
	/** Current velocity in ticks added/sec */
	private double speed = 0;
	
	/** Whether or not home has been set */
	private boolean homeIsSet = false;
	
	/** The number of ticks between the limit switches, as detected by calibration */
	private int switchRange;
	
	
	//"Constants" - set only once at initialization
	/** If true, this system has a feedback sensor */
	private boolean hasFeedback;
	
	/** Maximum safe encoder position above the lower limit switch */
	private int maxPosition;
	
	/** Min safe encoder position above the lower Limit switch. */
	private int minPosition;
	
	/** Controls default direction of controller */
	private int controllerPolarity = 1;
	
	/** Controls default direction of encoder */
	private int encoderPolarity = 1;
	
	/** P-Coefficient */
	private double pConstant;
	
	/** Allowable error for p-control */
	private double pDeadzone;
	
	/** Default tick to return to after calibration */
	private int home;
	
	/** Power set during calibration */
	private double calibrationPower;
	
	/**
	 * Use this constructor if you have a continuous encoder on your axis
	 * @param c - main motor controller
	 * @param e - main feedback encoder
	 * @param min - minimum SAFE encoder position
	 * @param max - maximum SAFE encoder position
	 */
	public TurretAxis(PWMSpeedController c, Encoder e, int min, int max)
	{
		this.controller = c;
		this.encoder = e;
		this.hasFeedback = true;
		this.encoder.setDistancePerPulse(1);
		this.speedTimer = new Timer();
	}
	
	/**
	 * Use this constructor if you have a continuous potentiometer on your axis
	 * @param c - main motor controller
	 * @param p - main feedback pot
	 * @param min - minimum SAFE encoder position
	 * @param max - maximum SAFE encoder position
	 */
	public TurretAxis(PWMSpeedController c, Potentiometer p, int min, int max)
	{
		this.controller = c;
		this.pot = p;
		this.hasFeedback = true;
		this.speedTimer = new Timer();
	}

	/**
	 * Use this constructor if you have no continuous feedback on your axis
	 * @param c - main motor controller
	 */
	public TurretAxis(PWMSpeedController c)
	{
		this.controller = c;
		this.speedTimer = new Timer();
		this.hasFeedback = false;
	}
	
	/**
	 * This function needs to be called on your turretAxis if your actual turret has limit switches on your axis
	 * @param min
	 * @param max
	 */
	public void addLimits(DigitalInput min, DigitalInput max)
	{
		this.minLimit = min;
		this.maxLimit = max;
	}
	
	public void periodic()
	{
		if (this.hasFeedback == true)
		{
			if (this.isCalibrating == true)
			{
				if (this.minLimit.get() == true)
				{
					this.encoder.reset(); //Label the minimum as 0
					this.power = this.calibrationPower; //Move towards max
				}
				if (this.maxLimit.get() == true)
				{
					this.power = 0;
					this.switchRange = this.encoder.get(); 
					System.out.println("TurretAxis: Calibrated! The max position is positive " + this.maxPosition);
				}
			}
			else
			{
				if (this.isPowerControlling == false) //If we are not power controlling
				{
					if (this.speedTimer.get() >= 1/CHANGE_FREQUENCY) //If a certain period has passed
					{
						this.speedTimer.reset();
						this.speedTimer.start();
						
						//Add onto the target the speed per sec modified by the frequency
						this.target += this.speed/CHANGE_FREQUENCY; //If we are not changing target position, speed = 0
					}
					int err = this.encoder.get() - this.target; //Calculate error
					if (Math.abs(err) <= this.pDeadzone) //If in allowable bounds
					{
						this.power = 0;
						this.speed = 0;
					}
					else //If we still need to adjust
					{
						this.power = err * this.pConstant;
					}
				}
				//If we ARE power controlling, we already set the power!
			}
			
			//LIMITING CHECKS
			int p = this.encoder.get(); //Current tick or position
			if ((p > this.maxPosition && this.power > 0) ||
			(p < this.minPosition && this.power < 0) ||
			(this.minLimit.get() && this.power < 0) ||
			(this.maxLimit.get() && this.power > 0))
			{
				this.power = 0;
				System.out.print("TurretAxis: You are out of bounds or you are on a limit!");
			}
		} //End of requiring feedback
		
		//No feedback? Set power to whatever we got!
		
		this.controller.set(this.power * this.controllerPolarity);
	}
	
	/**
	 * Calling this function allows you to set values for two variables used in targeting (p-Control)
	 * @param k - P-Constant, multiplied by error. A higher value means faster but more inaccurate targeting
	 * @param e - Cutoff value, the acceptable rpm-error to stop tageting.
	 */
	public void configureTargeting(double k, double e)
	{
		if (this.hasFeedback) 
		{
			this.pConstant = k;
			this.pDeadzone = e;
		}
		else
		{
			System.out.println();
		}
	}
	
	/**
	 * Adjusts target speed of yaw motor based on input of turret <br>
	 * The target position literally changes by the specified amount per second <br>
	 * Calling this can modify other control calls
	 * @param ticksPerSec - Change in position per second
	 */
	public void setSpeed(double ticksPerSec)
	{
		this.speed = ticksPerSec;
		this.isPowerControlling = false;
	}
	
	/**
	 * @return - number of ticks being added to target position each second
	 */
	public double getSetSpeed()
	{
		return this.speed;
	}
	
	/**
	 * @return - current, true ticks/sec of axis
	 */
	public double getSpeed()
	{
		return this.encoder.getRate() * this.encoderPolarity;
	}
	
	/**
	 * Moves the turret until it reads the specified tick on the feedback sensor <br>
	 * Calling this cancels any other controls
	 * @param position - target tick
	 */
	public void setPosition(int position)
	{
		//Can change target even when it is not finished with current target
		this.target = position;
		this.speed = 0;
		this.isPowerControlling = false;
	}
	
	/**
	 * @return - current tick position
	 */
	public int getPosition()
	{
		return this.encoder.get();
	}
	
	/**
	 * @return - last tick-position set to the axis
	 */
	public int getSetPosition()
	{
		return this.target;
	}
	
	/**
	 * Controls the motor via the power <br>
	 * Calling this cancels any other controls
	 * @param p - take a guess
	 * */
	public void setPower(double p)
	{
		this.power = p;
		this.isPowerControlling = true;
	}
	
	/**
	 * Calling this function sets a position for the axis to consider home after calibration
	 * @param homeTick - the position for home
	 */
	public void setHome(int homeTick)
	{
		if (this.homeIsSet == false)
		{
			this.home = homeTick;
			this.homeIsSet = true;
		}
		else
		{
			System.out.println("TurretAxis: You have already set a home!");
		}
	}
	
	/**
	 * @return - the set home
	 */
	public int getHome()
	{
		return this.home;
	}
	
	/**
	 * Sets the position to the "home" position set in the system
	 */
	public void home()
	{
		if (this.homeIsSet)
		{
			this.setPosition(this.home);
		}
		else
		{
			System.out.println("TurretAxis: Please call \"setHome(#)\" before homing.");	
		}	
	}
	
	/**
	 * Call once to reverse all values set to the controller <br>
	 * Subsequent calls have no effect
	 */
	public void reverseController()
	{
		this.controllerPolarity = -1;
	}
	
	/**
	 * Call once to reverse all values grabbed from the encoder <br>
	 * Subsequent calls have no effect
	 */
	public void reverseEncoder()
	{
		this.encoderPolarity = -1;
	}
	
	/**
	 * Moves the turret to the lowermost limit, then highermost limit, in order to determine its calibration
	 * @param cPower - power used during calibration
	 */
	public void calibrate(double cPower)
	{
		this.calibrationPower = cPower;
		if (this.maxLimit == null || this.minLimit == null)
		{
			System.out.println("TurretAxis: Please add limit switches before attempting to calibrate.");
		}
		else
		{
			this.isCalibrating = true;
			this.power = -1 * this.calibrationPower; //Begin moving to lower limit
		}
	}
	
	/**
	 * Returns the tick-value where the max limit switch is located <br>
	 * The min limit switch is located at 0. <br>
	 * Returns 0 if the axis has not been calibrated
	 * @return - the tick-value where the max limit switch is located
	 */
	public int getSwitchRange()
	{
		return this.switchRange;
	}
	
	/**
	 * @return - current power set to motors
	 */
	public double getPower()
	{
		return this.power;
	}
	
	/**
	 * This function returns a string that explains the tick limits of the current setup
	 * @return - a string representation of the axis limits
	 */
	public String printLimits()
	{
		// ["LOWER_LIMIT"---"LOWER_SAFE"======{"HOME"}======"UPPER_SAFE"---"UPPER_LIMIT"]
		return "[0---" + this.minPosition + "======{" + (this.homeIsSet? this.home:"NO_HOME") + "}======" + this.maxPosition + "---" + this.switchRange + "]";
	}
}
