package org.usfirst.frc.team283.napalm;

import edu.wpi.first.wpilibj.Talon;

public class DriveSubsystem
{
	//Constants
	private static final double DEADZONE = 0.1;
	private static final double SLOWSPEED = 0.5;
	
	Talon leftController;
	Talon rightController;
	
	public DriveSubsystem()
	{
		
		leftController = new Talon(Constants.LEFT_DRIVE_CONTROLLER_PORT);
		leftController = new Talon(Constants.RIGHT_DRIVE_CONTROLLER_PORT);
	}
	
	public void periodic(float leftMagnitude, float rightMagnitude, boolean checkSlowSpeed, boolean check)
	{
		if((leftMagnitude >= DEADZONE || leftMagnitude <= (-1 * DEADZONE)) || )
		
	}
	
	public void checkSlowmode()
	{
		
	}
	
	public void checkGearshit()
	{
		
	}
	
	
	
}
