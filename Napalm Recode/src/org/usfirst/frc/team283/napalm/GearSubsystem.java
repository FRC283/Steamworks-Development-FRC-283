package org.usfirst.frc.team283.napalm;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;

public class GearSubsystem 
{
	//Solenoids
	Solenoid gate;
	Solenoid push;
	Solenoid pouch;
	//Timers
	Timer pushTimer;
	Timer closeTimer;
	
	boolean storedState = false;
	public GearSubsystem()
	{
		gate = new Solenoid(Constants.GEAR_GATE_SOLENOID_PORT);
		push = new Solenoid(Constants.GEAR_PUSH_SOLENOID_PORT);
		pouch = new Solenoid(Constants.GEAR_POUCH_SOLENOID_PORT);
		pushTimer = new Timer();
		closeTimer = new Timer();
	}
	public void periodic(boolean gateSol, boolean pushSol, boolean pouchSol)
	{
		if(pushSol == false && storedState == false)
		{
			if(gateSol == true)
			{
				gate.set(true);
			}
			else
			{
				gate.set(false);
			}
			if(pouchSol == true)
			{
				pouch.set(true);
			}
			else
			{
				pouch.set(false);
			}
		}
		else if(pushSol == true)
		{
			
			if(storedState == false && pushTimer.get() <= 0.5)
			{
				pushTimer.start();
				gate.set(true);
			}
			else if(storedState == true && pushTimer.get() >= 0.5)
			{
				push.set(true);
				pushTimer.stop();
				pushTimer.reset();
			}
		}
		else if(pushSol == false && storedState == true)
		{
			if(closeTimer.get() <= 0.5)
			{
				push.set(false);
				pouch.set(true);
				closeTimer.start();
			}
			else if(closeTimer.get() >= 0.5)
			{
				pouch.set(false);
				closeTimer.stop();
				closeTimer.reset();
			}
		}
		storedState = pushSol;
	}
	
	
	
}
