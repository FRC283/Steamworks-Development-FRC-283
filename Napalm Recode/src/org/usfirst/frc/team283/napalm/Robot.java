package org.usfirst.frc.team283.napalm;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;

public class Robot extends IterativeRobot //Post-Release merge comment
{
	DriveSubsystem drivetrain;
	GearSubsystem gearSubsystem;
	ShooterSubsystem shooterSubsystem;
	
	Joystick xbox;
	Joystick logitech;
	
	@Override
	public void robotInit() 
	{
		drivetrain = new DriveSubsystem();
		gearSubsystem = new GearSubsystem();
		shooterSubsystem = new ShooterSubsystem();
		
		logitech = new Joystick(Constants.DRIVER_CONTROLLER_PORT);
		xbox = new Joystick(Constants.OPERATOR_CONTROLLER_PORT);
	}

	@Override
	public void autonomousInit() 
	{
		
	}

	@Override
	public void autonomousPeriodic() 
	{
		
	}

	@Override
	public void teleopInit() 
	{
		
	}
	
	
	@Override
	public void teleopPeriodic() 
	{
		drivetrain.drive(logitech.getRawAxis(Constants.LEFT_Y), logitech.getRawAxis(Constants.RIGHT_Y),(logitech.getRawAxis(Constants.RIGHT_TRIGGER) >= 0.5));
		drivetrain.lift(xbox.getRawAxis(Constants.RIGHT_TRIGGER));
		drivetrain.shiftGear(logitech.getRawButton(Constants.LEFT_BUMPER));
		gearSubsystem.pouch(xbox.getRawButton(Constants.RIGHT_BUMPER));
		//UNKNOWN IF LOCKOUT IS CORRECT
		gearSubsystem.release(xbox.getRawButton(Constants.LEFT_BUMPER));
		shooterSubsystem.aim(xbox.getRawAxis(Constants.RIGHT_X));
		
		//Printouts:
		System.out.println("State of Shift Button: " + xbox.getRawButton(Constants.LEFT_BUMPER));
		System.out.println("====================");
	}
}

