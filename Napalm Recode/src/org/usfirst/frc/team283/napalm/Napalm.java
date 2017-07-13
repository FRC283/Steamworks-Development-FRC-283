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
		logitech = new Joystick(0);	//replace with constant
		xbox = new Joystick(1);

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
		drivetrain.periodic(driveLeftAxis, driveRightAxis, driveRightTrigger, (logitech.getRawAxis(3) >= 0.5), buttonState);
		gearSubsystem.periodic(gateSol, pushSol, pouchSol);
	}
}

