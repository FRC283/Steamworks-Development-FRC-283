package org.usfirst.frc.team283.napalm;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Robot extends IterativeRobot 
{
	DriveSubsystem drivetrain;
	GearSubsystem gearSubsystem;
	ShooterSubsystem shooterSubsystem;
	
	Joystick xbox;
	Joystick logitech;
	
	NetworkTable cvData;
	Timer autoTimer;
	Timer autoTimer2;
	
	boolean notReleased = true;
	boolean autoDone = false;
	private enum AutoMode
	{
		kForwards,
		kForwardGear
	}
	
	private final AutoMode aM = AutoMode.kForwardGear;
	
	@Override
	public void robotInit() 
	{
		drivetrain = new DriveSubsystem();
		gearSubsystem = new GearSubsystem();
		shooterSubsystem = new ShooterSubsystem();
		cvData = NetworkTable.getTable("cv_data");
		autoTimer = new Timer();
		autoTimer2 = new Timer();
		
		logitech = new Joystick(Constants.DRIVER_CONTROLLER_PORT);
		xbox = new Joystick(Constants.OPERATOR_CONTROLLER_PORT);
	}
	@Override
	public void autonomousInit()
	{/*
		autoTimer.reset();
		drivetrain.driveLeftDistance(85);
		drivetrain.driveRightDistance(85);
		autoTimer.start();
		(autoTimer.get() < 2) {}
		gearSubsystem.release(true);
		
		switch (aM)
		{
			case kForwards:
				drivetrain.driveLeftDistance(150);
				drivetrain.driveRightDistance(150);
			break;
			case kForwardGear:
				drivetrain.driveLeftDistance(112);
				drivetrain.driveRightDistance(112);
				gearSubsystem.release(true);
			break;
		}
		*/
		
	}
	
	@Override
	public void autonomousPeriodic() 
	{
		/*
		switch (aM)
		{
			case kForwards:
				drivetrain.driveLeftDistance(150);
				drivetrain.driveRightDistance(150);
			break;
			case kForwardGear: */
				
				if (autoDone == false)
				{
					autoTimer2.reset();
					autoTimer.reset();
					drivetrain.driveLeftDistance(85);
					drivetrain.driveRightDistance(85);
					autoTimer.start();
					autoDone = true;
				}
				if (autoTimer.get() > 2 && notReleased == true)
				{
					gearSubsystem.release(true);
					
					notReleased = false;
				}/*
			break;
		}
		*/
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
		//if (logitech.getRawButton(Constants.RIGHT_BUMPER)) drivetrain.centerInit();
		
		//Periodics
		shooterSubsystem.periodic();
		drivetrain.periodic();
		//drivetrain.centerPeriodic(cvData.getNumber("dx", 0));
		
		//Printouts:
		cvData.putNumber("testNum", 88);
		System.out.println("cvData.dx: " + cvData.getNumber("dx", 0));
		System.out.println("cvData.dy: " + cvData.getNumber("dy", 0));
		System.out.println("====================");
	}
}

