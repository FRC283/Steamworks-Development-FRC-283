package org.usfirst.frc.team283.napalm;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;

public class Robot extends IterativeRobot 
{
	DriveSubsystem drivetrain;
	GearSubsystem gearSubsystem;
	ShooterSubsystem shooterSubsystem;
	
	Joystick xbox;
	Joystick logitech;
	
	//SCOOBYDOOBY sd; //ZIP-ZAP ZAHBAHBAH DOO-DEH DOO-DEE YEAAAAHHHHHH
	
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
	public void autonomousPeriodic() 
	{
		//Your Auto Code Here
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
		shooterSubsystem.manualAim(xbox.getRawAxis(Constants.RIGHT_X));
		shooterSubsystem.speed(xbox.getRawAxis(Constants.RIGHT_Y));
		
		//Periodics
		shooterSubsystem.periodic();
		
		//Printouts:
		System.out.println("====================");
	}
}

