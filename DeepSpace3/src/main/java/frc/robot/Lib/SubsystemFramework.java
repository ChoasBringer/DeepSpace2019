package frc.robot.Lib;

import frc.robot.Lib.Loop.Loopable;

public abstract class SubsystemFramework implements Loopable{
	public abstract void outputToSmartDashboard();
	public abstract void setupSensors();
}