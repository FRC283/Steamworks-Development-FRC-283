
package org.usfirst.frc.team283.napalm;
import org.usfirst.frc.team283.napalm.Scheme.Schema;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveSubsystem
{				
	//Constants
	/** Minimum value for driving on logitech axis */
	private static final double DEADZONE = 0.1;
	/** Minimum value for logitech trigger for climb */
	private static final double TRIGGER_DEADZONE = 0;
	/** The value motor speeds are multiplied by when slowspeed is enabled */
	private static final double SLOWSPEED = 0.5;
	/** Controls the power to the motor based on the current error */
	private static final double P_CONSTANT = 1/1500;
	/** When the encoder is within this value of the target, we can cease controlling */
	private static final int MINIMUM_ERROR = 20;
	/** */
	private static final int INCHES_TO_TICKS = 0;
	//Vars
	/** Stores the previous (last-cycle) value of the gear shift button's state */
	private boolean gearShiftBuffer = false;
	/** Current target encoder value on left*/
	private int leftDriveTarget;
	/** Current target encoder value on right*/
	private int rightDriveTarget;
	/** Whether or not the robot is currently p-controlling to its target on left */
	private boolean isLeftControlling = false;
	/** Whether or not the robot is currently p-controlling to its target on right */
	private boolean isRightControlling = false;
	
	Spark leftController;
	Spark rightController;
	Spark climbSpark;
	Solenoid gearShift;
	/** Encoder on left side of bot */
	Encoder leftEnc;
	/** Encoder on right side of bot */
	Encoder rightEnc;
	
	public DriveSubsystem()
	{
		leftController = new Spark(Constants.LEFT_DRIVE_CONTROLLER_PORT);
		rightController = new Spark(Constants.RIGHT_DRIVE_CONTROLER_PORT);
		climbSpark = new Spark(Constants.CLIMB_CONTROLLER_PORT);
		
		gearShift = new Solenoid(Constants.SPEED_SHIFT_SOLENOID_PORT);
		
		leftEnc = new Encoder(Constants.LEFT_DRIVE_ENCODER_PORT_A, Constants.LEFT_DRIVE_ENCODER_PORT_B);
		leftEnc = new Encoder(Constants.RIGHT_DRIVE_ENCODER_PORT_A, Constants.RIGHT_DRIVE_ENCODER_PORT_B);
		
	}
	
	/**
	 * Called once per cycle to update various values
	 */
	public void periodic()
	{	
		System.out.println("Encoder Read:" + leftEnc.get());
		//P-Controlling
		if (this.isLeftControlling == true)
		{
			int lErr = leftEnc.get() - this.leftDriveTarget; //Calculating left and right error
			if (Math.abs(lErr) < MINIMUM_ERROR)
			{
				leftController.set(0);
				this.isLeftControlling = false;
			}
			else //If we havent reached within the minimum error
			{
				rightController.set((P_CONSTANT) * lErr); //Scaled by constant	
			}
		}
		if (this.isRightControlling == true) //
		{
			int rErr = rightEnc.get() - this.rightDriveTarget;
			if (Math.abs(rErr) < MINIMUM_ERROR)
			{
				rightController.set(0);
				this.isRightControlling = false;
			}
			else //If we havent reached within the minimum error
			{
				rightController.set(-1 * (P_CONSTANT) * rErr); //Scaled by constant	
			}
		} 
		//End of P-Controlling
		
		SmartDashboard.putNumber("Left Drive Encoder Value", this.leftEnc.get());
		SmartDashboard.putNumber("Right Drive Encoder Value", this.rightEnc.get());
	}
	
	@Schema(Scheme.LEFT_Y)
	@Schema(Scheme.RIGHT_Y)
	@Schema(value = Scheme.RIGHT_TRIGGER, desc = SLOWSPEED + " speed")
	/**
	 * 
	 * @param leftMagnitude
	 * @param rightMagnitude
	 * @param slowSpeed
	 */
	public void drive(double leftMagnitude, double rightMagnitude, boolean slowSpeed)
	{
		leftController.set(-1 * (Rescaler.rescale(DEADZONE, 1.0, 0.0, 1.0, leftMagnitude)) * (slowSpeed ? SLOWSPEED : 1));
		rightController.set( (Rescaler.rescale(DEADZONE, 1.0, 0.0, 1.0, rightMagnitude)) * (slowSpeed ? SLOWSPEED : 1));
	}
	
	@Schema(Scheme.LEFT_BUMPER)
	/**
	 * 
	 * @param gearButtonState
	 */
	public void shiftGear(boolean gearButtonState)
	{
		if(gearButtonState == true && gearShiftBuffer == false)
		{
			gearShift.set(!gearShift.get());
		}
		gearShiftBuffer = gearButtonState;
	}
	
	@Schema(Scheme.XBOX_RIGHT_TRIGGER)
	/**
	 * 
	 * @param triggerMagnitude
	 */
	public void lift(double triggerMagnitude)
	{
		climbSpark.set(triggerMagnitude > TRIGGER_DEADZONE ? -1 * triggerMagnitude : 0); //Set to climb the value of the trigger (Reverse-wired motor)
	}
	
	/**
	 * uses p control to drive set distance on left side
	 * @param distance - distance to drive in inches
	 */
	public void driveLeftDistance(double distance)
	{
		if (this.isLeftControlling == false) //Only choose a new target if we dont have a current target
		{
			this.leftEnc.reset();
			this.leftDriveTarget = distance;
			this.isLeftControlling = true;
		}
		else
		{
			//Do nothing, we already have a target
		}
	}
	
	/**
	 * uses p control to drive set distance on right side
	 * @param distance - distance to drive in inches
	 */
	public void driverightDistance(double distance)
	{
		if (this.isRightControlling == false) //Only choose a new target if we dont have a current target
		{
			this.rightEnc.reset();
			this.rightDriveTarget = distance;
			this.isRightControlling = true;
		}
		else
		{
			//Do nothing, we already have a target
		}
	}
}
