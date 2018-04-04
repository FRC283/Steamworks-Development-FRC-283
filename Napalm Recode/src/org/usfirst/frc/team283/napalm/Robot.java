package org.usfirst.frc.team283.napalm;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;

public class Robot extends IterativeRobot 
{
	//DriveSubsystem drivetrain;
	//GearSubsystem gearSubsystem;
	//ShooterSubsystem shooterSubsystem;
	//ArmSubsystem armSubsystem;
	Joystick xbox;
	Joystick logitech;
	DriveSubsystem drivetrain;
	ShooterSubsystem shooterSubsystem;
	
	@Override
	public void robotInit() 
	{
		//armSubsystem = new ArmSubsystem();
		//drivetrain = new DriveSubsystem();
		//gearSubsystem = new GearSubsystem();
		//shooterSubsystem = new ShooterSubsystem();
		logitech = new Joystick(Constants.DRIVER_CONTROLLER_PORT);
		xbox = new Joystick(Constants.OPERATOR_CONTROLLER_PORT);
		drivetrain = new DriveSubsystem();
		shooterSubsystem = new ShooterSubsystem();
	}
	
	@Override
	public void teleopPeriodic() 
	{
		
		System.out.println("!===================!");
		//drivetrain.drive(-1 * logitech.getRawAxis(Constants.RIGHT_Y), -1 * logitech.getRawAxis(Constants.LEFT_Y),logitech.getRawButton(Constants.RIGHT_BUMPER), false);
		//drivetrain.lift(xbox.getRawAxis(Constants.RIGHT_TRIGGER));
		//drivetrain.shiftGear(logitech.getRawButton(Constants.RIGHT_STICK_BUTTON));
		//gearSubsystem.pouch(xbox.getRawButton(Constants.RIGHT_BUMPER));
		//gearSubsystem.release(xbox.getRawButton(Constants.LEFT_BUMPER));
		shooterSubsystem.manualAim(xbox.getRawAxis(Constants.RIGHT_X));
		//shooterSubsystem.speed(xbox.getRawAxis(Constants.RIGHT_Y));
		shooterSubsystem.feedIn(xbox.getRawAxis(Constants.LEFT_X), xbox.getRawAxis(Constants.LEFT_Y));
		shooterSubsystem.wheel(xbox.getRawAxis(Constants.RIGHT_Y));
		
		
		//Periodics
		//shooterSubsystem.periodic();
		//drivetrain.periodic();
		
		//Printouts:
		//System.out.println("====================");
	}
}

