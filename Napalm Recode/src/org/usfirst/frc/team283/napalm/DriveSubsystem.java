package org.usfirst.frc.team283.napalm;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;

public class DriveSubsystem
{				//Test
	//Constants
	private static final double DEADZONE = 0.1;
	private static final double SLOWSPEED = 0.5;
	//Variable States
	private static boolean storedState = false;	
	//Motors
	Spark leftController;
	Spark rightController;
	//Solenoids
	Solenoid gearShift;
	//Subsystems
	Lift Lift;
	public DriveSubsystem()
	{
		leftController = new Spark(Constants.LEFT_DRIVE_CONTROLLER_PORT);
		rightController = new Spark(Constants.RIGHT_DRIVE_CONTROLER_PORT);
		gearShift = new Solenoid(Constants.SPEED_SHIFT_SOLENOID_PORT);
		Lift = new Lift();
	}
	
	public void periodic(float leftMagnitude, float rightMagnitude, float triggerMagnitude, boolean slowSpeed, boolean buttonState)
	{
		Lift.periodic(triggerMagnitude);
		if(buttonState == true && storedState == false)
		{
			gearShift.set(!gearShift.get());
		}
		storedState = buttonState;
		if(((leftMagnitude >= DEADZONE) || (leftMagnitude <= (-1 * DEADZONE))) && slowSpeed == false)
		{
			leftController.set(leftMagnitude);
		}
		else if(((leftMagnitude >= DEADZONE) || (leftMagnitude <= (-1 * DEADZONE))) && slowSpeed == true)
		{
			leftController.set(SLOWSPEED * leftMagnitude);
		}
		else
		{
			leftController.set(0);
		}
		if(((rightMagnitude >= DEADZONE)||(rightMagnitude <= (-1 * DEADZONE))) && slowSpeed == false)
		{
			rightController.set(rightMagnitude);
		}
		else if(((rightMagnitude >= DEADZONE)||(rightMagnitude <= (-1 * DEADZONE))) && slowSpeed == true)
		{
			rightController.set(SLOWSPEED * rightMagnitude);
		}
		else
		{
			rightController.set(0);
		}
	}
}
