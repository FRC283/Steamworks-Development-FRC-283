package org.usfirst.frc.team283.napalm;

import edu.wpi.first.wpilibj.DigitalInput;
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
	
	//Autonomous Vars
	/** Holds the values of the triple switches on the side of the robot */
	DigitalInput autoSwitches[];
	/** General timer */
	Timer autoTimer;
	/** Determines what phase of each autonomous we're on */
	int autoStep = 0;
	/** Possible kinds of autonomous. See definition (control-click) for descriptions */
	private enum AutoMode
	{
		kForwards, //Drives over base line
		kForwardGear, //Drives forwards to spike, deposits gear, and backs up
		kDone //Intentionally does nothing
	}
	/** Controls which autonomous we use
	 * <br>If you initialize a value here, then that WILL be the autonomous. If you don't it will select based on switches
	 * */
	private AutoMode aM = AutoMode.kForwards;
	
	@Override
	public void robotInit() 
	{
		drivetrain = new DriveSubsystem();
		gearSubsystem = new GearSubsystem();
		shooterSubsystem = new ShooterSubsystem();
		cvData = NetworkTable.getTable("cv_data");
		autoTimer = new Timer();
		//autoSwitches[0] = new DigitalInput(Constants.AUTO_SWITCH_LEFT); //Left, middle and right switches
		//autoSwitches[1] = new DigitalInput(Constants.AUTO_SWITCH_MIDDLE);
		//autoSwitches[2] = new DigitalInput(Constants.AUTO_SWITCH_RIGHT);
		logitech = new Joystick(Constants.DRIVER_CONTROLLER_PORT);
		xbox = new Joystick(Constants.OPERATOR_CONTROLLER_PORT);
	}
	@Override
	public void autonomousInit()
	{
		/*if (aM == null) //If no value for aM was defined
		{
			System.out.println("No value for autoMode was defined. Checking switches...");
			//All three switches off
			if (autoSwitches[0].get() == false && autoSwitches[1].get() == false && autoSwitches[2].get() == false)
				aM = AutoMode.kDone;
			//Left switch on
			if (autoSwitches[0].get() == true && autoSwitches[1].get() == false && autoSwitches[2].get() == false)
				aM = AutoMode.kForwards;
			//Middle switch on
			if (autoSwitches[0].get() == false && autoSwitches[1].get() == true && autoSwitches[2].get() == false)
				aM = AutoMode.kForwardGear;
		}
		else
		{
			System.out.println("autoMode was defined as =" + aM);
		}
		*/
		autoTimer.reset();
		autoTimer.start(); //Reset and start the auto timer so that if the first step requires waiting it can wait
	}
	
	@Override
	public void autonomousPeriodic() 
	{
		System.out.println("AUTONOMOUS STATUS:");
		System.out.println("    autoMode = " + aM);
		System.out.println("    autoStep = " + autoStep);
		switch (aM) //Executes an autonomous based on the values of aM
		{
			case kForwards:
				switch (autoStep) //Determines the phase of the autonomous
				{
					case 0:
						drivetrain.driveLeftDistance(150); //Drive forwards
						drivetrain.driveRightDistance(150);
						autoStep++;
					break;
					case 1:
						 if (drivetrain.periodic() == false) //Wait for the forward motion to finish (When it is false, it is done)
						 {
							 autoStep++; //When we reach target, advance step
						 }
					break;
					case 2:
						//Do nothing, end of this autonomous.
					break;
				}
			break;
			case kForwardGear:
				switch (autoStep) //Determines the phase of the autonomous
				{
					case 0:
						drivetrain.driveLeftDistance(85); //Drive forwards 
						drivetrain.driveRightDistance(85);
						autoStep++;
					break;
					case 1:
						 if (drivetrain.periodic() == false) //Wait for the forward motion to finish (When it is false, it is done)
						 {
							 autoStep++; //When we reach target, advance step
						 }
					break;
					case 2:
						if (gearSubsystem.release(true) == true) //Opens the manibles for the pouch, begins waiting. If it returns true, gear is ejected.
						{
							autoStep++; //Gear was ejected, advance to next step
						}
					break;
					case 3:
						if (gearSubsystem.release(false) == true) //Closes the manibles and retracts. Returns true when successfully finished retracting.
						{
							autoStep++; //System retracted, advance to next step
						}
					break;
					case 4:
						drivetrain.driveLeftDistance(-25); //Drive back - this is so the human player can pull the gear up.
						drivetrain.driveRightDistance(-25);
						autoStep++;
					break;
					case 5:
						 if (drivetrain.periodic() == false) //Wait for the forward motion to finish (When it is false, it is done)
						 {
							 autoStep++; //When we reach target, advance step
						 }
					case 6:
						//Finished.
					break;
				}
			break;
			case kDone:
			break;
		}
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
		System.out.println("====================");
	}
}

