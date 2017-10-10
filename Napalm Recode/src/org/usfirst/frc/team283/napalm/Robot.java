package org.usfirst.frc.team283.napalm;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Robot extends IterativeRobot 
{
	DriveSubsystem drivetrain;
	GearSubsystem gearSubsystem;
	ShooterSubsystem shooterSubsystem;
	
	Joystick xbox;
	Joystick logitech;
	
	NetworkTable cvData;
	
	@Override
	public void robotInit() 
	{
		drivetrain = new DriveSubsystem();
		gearSubsystem = new GearSubsystem();
		shooterSubsystem = new ShooterSubsystem();
		cvData = NetworkTable.getTable("cv_data");
		
		logitech = new Joystick(Constants.DRIVER_CONTROLLER_PORT);
		xbox = new Joystick(Constants.OPERATOR_CONTROLLER_PORT);
	}
	@Override
	public void autonomousInit()
	{
		drivetrain.driveLeftDistance(70);
		drivetrain.driveRightDistance(70);
	}
	
	@Override
	public void autonomousPeriodic() 
	{
		//Your Auto Code Here
		drivetrain.periodic();
		System.out.println("cvData.dx: " + cvData.getNumber("dx", 0));
		System.out.println("cvData.dy: " + cvData.getNumber("dy", 0));
		System.out.println("====================");
	}
	
	@Override
	public void teleopInit()
	{
		
	}
	
	@Override
	public void teleopPeriodic() 
	{
		drivetrain.drive(logitech.getRawAxis(Constants.LEFT_Y), logitech.getRawAxis(Constants.RIGHT_Y),(logitech.getRawAxis(Constants.RIGHT_TRIGGER) >= 0.5), logitech.getRawButton(Constants.A));
		drivetrain.lift(xbox.getRawAxis(Constants.RIGHT_TRIGGER));
		drivetrain.shiftGear(logitech.getRawButton(Constants.LEFT_BUMPER));
		gearSubsystem.pouch(xbox.getRawButton(Constants.RIGHT_BUMPER));
		gearSubsystem.release(xbox.getRawButton(Constants.LEFT_BUMPER));
		shooterSubsystem.manualAim(xbox.getRawAxis(Constants.RIGHT_X));
		shooterSubsystem.speed(xbox.getRawAxis(Constants.RIGHT_Y));
		shooterSubsystem.feedIn(xbox.getRawAxis(Constants.LEFT_X), xbox.getRawAxis(Constants.LEFT_Y));
		
		//Periodics
		shooterSubsystem.periodic();
		drivetrain.periodic();
		
		//Printouts:
		cvData.putNumber("testNum", 66);
		System.out.println("cvData.dx: " + cvData.getNumber("dx", 0));
		System.out.println("cvData.dy: " + cvData.getNumber("dy", 0));
		System.out.println("====================");
	}
}

