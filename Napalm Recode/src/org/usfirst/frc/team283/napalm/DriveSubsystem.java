
package org.usfirst.frc.team283.napalm;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;

public class DriveSubsystem
{				
	//Constants
	/** Minimum value for driving on logitech axis */
	private static final double DEADZONE = 0.1;
	/** Minimum value for logitech trigger for climb */
	private static final double TRIGGER_DEADZONE = 0.1;
	/** The value motor speeds are multiplied by when slowspeed is enabled */
	private static final double SLOWSPEED = 0.5;
	
	/** Stores the previous (last-cycle) value of the gear shift button's state */
	private boolean gearShiftBuffer = false;	

	Spark leftController;
	Spark rightController;
	Spark climbSpark;
	Solenoid gearShift;
	
	public DriveSubsystem()
	{
		leftController = new Spark(Constants.LEFT_DRIVE_CONTROLLER_PORT);
		rightController = new Spark(Constants.RIGHT_DRIVE_CONTROLER_PORT);
		climbSpark = new Spark(Constants.CLIMB_CONTROLLER_PORT);
		
		gearShift = new Solenoid(Constants.SPEED_SHIFT_SOLENOID_PORT);
		
	}
	
	/**
	 * 
	 * @param leftMagnitude
	 * @param rightMagnitude
	 * @param slowSpeed
	 */
	public void drive(double leftMagnitude, double rightMagnitude, boolean slowSpeed)
	{
		leftController.set( (Rescaler.rescale(DEADZONE, 1.0, 0.0, 1.0, leftMagnitude)) * (slowSpeed ? SLOWSPEED : 1));
		rightController.set( (Rescaler.rescale(DEADZONE, 1.0, 0.0, 1.0, rightMagnitude)) * (slowSpeed ? SLOWSPEED : 1));
	}
	
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
	
	/**
	 * 
	 * @param triggerMagnitude
	 */
	public void lift(double triggerMagnitude)
	{
		if(triggerMagnitude > TRIGGER_DEADZONE) //If the trigger is pushed down past a certain point
		{
			climbSpark.set(-1 * triggerMagnitude); //Set to climb the value of the trigger (Reverse-wired motor)
		}
	}
}
