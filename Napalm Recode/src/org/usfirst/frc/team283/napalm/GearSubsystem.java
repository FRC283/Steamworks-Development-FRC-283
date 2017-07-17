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
	
	boolean storedState = false;
	public GearSubsystem()
	{
		gateSol = new Solenoid(Constants.GEAR_GATE_SOLENOID_PORT);
		pushSol = new Solenoid(Constants.GEAR_PUSH_SOLENOID_PORT);
		pouchSol = new Solenoid(Constants.GEAR_POUCH_SOLENOID_PORT);
		pushTimer = new Timer();
		closeTimer = new Timer();
	}
	public void periodic(boolean gateSolState, boolean pushSolState, boolean pouchSolState)
	{
		if(pushSolState == false && storedState == false)
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
		storedState = pushSolState;
	}
}
