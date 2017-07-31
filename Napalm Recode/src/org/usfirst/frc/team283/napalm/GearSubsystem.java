package org.usfirst.frc.team283.napalm;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;

public class GearSubsystem 
{
	//Solenoids
	Solenoid gateSol;
	Solenoid pushSol;
	Solenoid pouchSol;
	//Timers
	Timer pushTimer;
	Timer closeTimer;
	//Constants
	double pushTime = 0.5;
	double closeTime = 1.0;
	//Variables
	boolean rButtonStateBuffer = false;
	boolean pushSequence = false;
	
	public GearSubsystem()
	{
		gateSol = new Solenoid(Constants.GEAR_GATE_SOLENOID_PORT);
		pushSol = new Solenoid(Constants.GEAR_PUSH_SOLENOID_PORT);
		pouchSol = new Solenoid(Constants.GEAR_POUCH_SOLENOID_PORT);
		pushTimer = new Timer();
		closeTimer = new Timer();
	}
	public void periodic(boolean pushSolState, boolean pouchSolState)
	{
	/*	if(pushSolState == false && storedState == false)
		{
			if(gateSolState == true)
			{
				gateSol.set(true);
			}
			else
			{
				gateSol.set(false);
			}
			if(pouchSolState == true)
			{
				pouchSol.set(true);
			}
			else
			{
				pouchSol.set(false);
			}
		}
		else if(pushSolState == true)
		{
			
			if(storedState == false && pushTimer.get() <= 0.5)
			{
				pushTimer.start();
				gateSol.set(true);
			}
			else if(storedState == true && pushTimer.get() >= 0.5)
			{
				pushSol.set(true);
				pushTimer.stop();
				pushTimer.reset();
			}
		}
		else if(pushSolState == false && storedState == true)
		{
			if(closeTimer.get() <= 0.5)
			{
				pushSol.set(false);
				pouchSol.set(true);
				closeTimer.start();
			}
			else if(closeTimer.get() >= 0.5)
			{
				pouchSol.set(false);
				gateSol.set(false);
				closeTimer.stop();
				closeTimer.reset();
			}
		}
		storedState = pushSolState;*/
	}
	
	/**
	 * toggle of the pouch of the robot
	 * @param pButtonState - The state of the button assigned to this function in the Napalm class
	 */
	public void pouch(boolean pButtonState)
	{
		if(pushSequence = false)
		{
			pouchSol.set(pButtonState);
		}
	}
	
	/**
	 * When the assigned button is pressed, this function controls the robot to release the gear
	 * When the button is released, it reverses the process
	 * @param rButtonState - THe button state for this function
	 */
	public void release(boolean rButtonState)
	{
		if (rButtonStateBuffer == false && rButtonState == true) //Press Event
		{
			if (pouchSol.get() == false) //If the pouch is closed (which it should be)
			{
				gateSol.set(true); //Open the 'gates' (pincers)
				pushTimer.start(); //Start waiting
				pushSequence = true;// Locks other functions in this class
			}
		}
		if (rButtonStateBuffer == true && rButtonState == true)
		{
			if (pushTimer.get() > pushTime && gateSol.get() == true) //After time has past, and the pincers/gate are open
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
			if (closeTimer.get() > closeTime) //After time has past
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
