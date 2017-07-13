package org.usfirst.frc.team283.napalm;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Napalm extends IterativeRobot //Post-Release merge comment
{
	//Subsystems
	DriveSubsystem drivetrain;
	GearSubsystem gearSubsystem;
	//Functions
	boolean driveTriggerBool;
	//Controllers
	Joystick xbox;
	Joystick logitech;
	//Driver
		//Axes
	double driveLeftAxis;
	double driveRightAxis;
	double driveRightTrigger;
		//Buttons
	boolean driveLeftBumper;
	//Operator
		//Axes
	float opRightTigger;
	float opLeftTrigger;
		//Buttons
	boolean opAButton;
	boolean opYButton;
	boolean opXButton;
	boolean opLeftBumper;
	boolean opRightBumper;	
	
	@Override
	public void robotInit() 
	{
		drivetrain = new DriveSubsystem();
		gearSubsystem = new GearSubsystem();
		logitech = new Joystick(0);	
		xbox = new Joystick(1);
		driveLeftAxis = logitech.getRawAxis(1);
		driveRightAxis = logitech.getRawAxis(5);
		driveRightTrigger = logitech.getRawAxis(3);
		driveTriggerBool = false;
		
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
		if(driveRightTrigger >= 0.5)
		{
			driveTriggerBool = true;
		}
		else
		{
			driveTriggerBool = false;
		}
		drivetrain.periodic(driveLeftAxis, driveRightAxis, driveRightTrigger, driveTriggerBool, buttonState);
		gearSubsystem.periodic(gateSol, pushSol, pouchSol);
	}
}

