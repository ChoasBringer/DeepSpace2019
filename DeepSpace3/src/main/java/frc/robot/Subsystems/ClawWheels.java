package frc.robot.Subsystems;

import frc.robot.Constants;
import frc.robot.Hardware;
import frc.robot.Lib.SubsystemFramework;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ClawWheels extends SubsystemFramework {
    private WPI_VictorSPX clawWheelsMotor;

    public static ClawWheelsState state = ClawWheelsState.Off;
    
    public ClawWheels(WPI_VictorSPX clawWheelsMotor){
        this.clawWheelsMotor = clawWheelsMotor;
    }

    
    public enum ClawWheelsState {
        Off, Intake, Spit;
    }

	@Override
	public void update() {    

        ClawWheelsState newState = state;

        switch (state) {
        case Off:
            clawWheelsMotor.set(0);
            SmartDashboard.putString("Wheel", "Off"); 
        if(Hardware.driverPad.getRawAxis(Constants.CLAW_SPIT) >= 0.2) {
            newState = ClawWheelsState.Spit;
        }else if(Hardware.driverPad.getRawAxis(Constants.CLAW_INTAKE) >= 0.2){
            newState = ClawWheelsState.Intake;
            }
break;
        case Intake:
        SmartDashboard.putString("Wheel", "Intake"); 
            clawWheelsMotor.set(-0.6);
        
            if(Hardware.driverPad.getRawAxis(Constants.CLAW_INTAKE) < 0.2){
            newState = ClawWheelsState.Off;
        }   
break;
        case Spit:
            clawWheelsMotor.set(1);
            SmartDashboard.putString("Wheel", "Spit"); 
        if(Hardware.driverPad.getRawAxis(Constants.CLAW_SPIT) < 0.2) {
            newState = ClawWheelsState.Off;
        }   
break;
        default:
            newState = ClawWheelsState.Off;
            break;

}
        if (newState != state) {
            state = newState;
        }
		outputToSmartDashboard();
    }

    @Override
	public void outputToSmartDashboard() {
        

	}
	
	@Override
	public void setupSensors() {

    }

}