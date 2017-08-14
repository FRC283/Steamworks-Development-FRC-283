package org.usfirst.frc.team283.napalm;
import org.usfirst.frc.team283.napalm.Scheme.Schema;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;

public class GearSubsystem 
{
	//Solenoids
	private Solenoid gateSol;
	private Solenoid pushSol;
	private Solenoid pouchSol;
	//Timers
	private Timer pushTimer;
	private Timer closeTimer;
	//Constants
	private static final double PUSH_TIME = 0.5;
	private static final double CLOSE_TIME = 1.0;
	//Variables
	private boolean rButtonStateBuffer = false;
	/** The lockout state for the sequence of pushing the gear out */
	private boolean pushSequence = false;
	
	public GearSubsystem()
	{
		gateSol = new Solenoid(Constants.GEAR_GATE_SOLENOID_PORT);
		pushSol = new Solenoid(Constants.GEAR_PUSH_SOLENOID_PORT);
		pouchSol = new Solenoid(Constants.GEAR_POUCH_SOLENOID_PORT);
		pushTimer = new Timer();
		closeTimer = new Timer();
	}
	
	@Schema(Scheme.XBOX_RIGHT_BUMPER)
	/**
	 * toggle of the pouch of the robot
	 * @param pButtonState - The state of the button assigned to this function in the Napalm class
	 */
	public void pouch(boolean pButtonState)
	{
		if(pushSequence == false)
		{
			pouchSol.set(pButtonState);
		}
	}
	
	@Schema(Scheme.XBOX_LEFT_BUMPER)
	/**
	 * When the assigned button is pressed, this function controls the robot to release the gear
	 * When the button is released, it reverses the process
	 * @param rButtonState - THe button state for this function
	 */
	public void release(boolean rButtonState)
	{
		System.out.println("Push Sequence: " + pushSequence);
		if (rButtonStateBuffer == false && rButtonState == true && pouchSol.get() == false) //Press Event
		{
			if (pouchSol.get() == false) //If the pouch is closed (which it should be)
			{
				gateSol.set(true); //Open the 'gates' (pincers)
				pushTimer.start(); //Start waiting
				pushSequence = true; //Locks other functions in this class
			}
		}
		if (rButtonStateBuffer == true && rButtonState == true)
		{
			if (pushTimer.get() > PUSH_TIME && gateSol.get() == true) //After time has past, and the pincers/gate are open
			{
				pushTimer.stop();
				pushTimer.reset();
				pushSol.set(true); //Extend push device
			}
		}
		if (rButtonStateBuffer == true && rButtonState == false) //Release Event
		{
			pushSol.set(false); //Retract push device
			pouchSol.set(true); //Open the pouch (Yes, this is unintuitive)
			closeTimer.start();
		}
		if (rButtonStateBuffer == false && rButtonState == false)
		{
			if (closeTimer.get() > CLOSE_TIME) //After time has past
			{
				closeTimer.stop();
				closeTimer.reset();
				gateSol.set(false); //Close the pincers/gate
				pouchSol.set(false); //CLose the pouch
				pushSequence = false; //Opens other functions in this class
			}
		}
		rButtonStateBuffer = rButtonState; //Update the buffer to the new position
	}
}
