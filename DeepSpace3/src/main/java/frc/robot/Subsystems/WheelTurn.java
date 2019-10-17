package frc.robot.Subsystems;

import frc.robot.Constants;
import frc.robot.Hardware;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import frc.robot.Util.PID;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.robot.Lib.SubsystemFramework;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class WheelTurn extends SubsystemFramework{

    //Hardware
	private static WPI_TalonSRX wheelTurnMotor;
	
	//PID
	private PID WheelTurn_PI;

	// Constants
	private static double Average_Ticks_Per_Deegre;
	private double WheelTurn_Kf;
	private double WheelTurn_Kp;
	private double WheelTurn_Ki;
	private double WheelTurn_Kd;
	
	public static WheelTurnState state = WheelTurnState.HatchClose;

	public WheelTurn(WPI_TalonSRX wheelTurnMotor, double Average_Ticks_Per_Deegre,
					double WheelTurn_Kf, double WheelTurn_Kd, 
					double WheelTurn_Ki, double WheelTurn_Kp){
		
		this.wheelTurnMotor = wheelTurnMotor;

		this.Average_Ticks_Per_Deegre = Average_Ticks_Per_Deegre;

		this.WheelTurn_Kd = WheelTurn_Kd;
		this.WheelTurn_Kf = WheelTurn_Kf;
		this.WheelTurn_Ki = WheelTurn_Ki;
		this.WheelTurn_Kp = WheelTurn_Kp;

	}

	public WheelTurn(WPI_TalonSRX wheelTurnMotor, double Average_Ticks_Per_Deegre,
					double wheelTurn_Kp, double wheelTurn_Ki){
		
		this.wheelTurnMotor = wheelTurnMotor;

		this.Average_Ticks_Per_Deegre = Average_Ticks_Per_Deegre;

		this.WheelTurn_PI = new PID(WheelTurn_Kp, WheelTurn_Ki);
	
	}

	public enum WheelTurnState{
		Close, Open,  HatchOpen, HatchClose;
	}

	public double setPosition(double angle) {
	
		return angle * Average_Ticks_Per_Deegre;
	}

	public static boolean testSafeAngle(double Safe_Angle) {
		return (Safe_Angle * Average_Ticks_Per_Deegre) <= wheelTurnMotor.getSelectedSensorPosition(0);
	}
	@Override
	public void update() {
		SmartDashboard.putNumber("Claw Turn", wheelTurnMotor.getSelectedSensorPosition(0));


		if(Hardware.driverPad.getRawButton(5)){
			wheelTurnMotor.set(0.7);
		}else if(Hardware.driverPad.getRawButton(6)){
			wheelTurnMotor.set(-0.7);
		}else{
			wheelTurnMotor.set(0);
		} 
		/*WheelTurnState newState =state;

		switch(state){
			case Close :
				SmartDashboard.putString("Wheel Turn", "Close");
				wheelTurnMotor.set(ControlMode.Position, setPosition(0));//-60

				if(Hardware.driverPad.getRawButtonPressed(Constants.OPEN)){
					newState = WheelTurnState.Open;
				}else if(Hardware.driverPad.getRawButton(4)){
					newState = WheelTurnState.HatchClose;
				}	

	break;
	
			case Open :
				SmartDashboard.putString("Wheel Turn", "Open"); 
				wheelTurnMotor.set(ControlMode.Position, setPosition(0));//-100
				
				if(Hardware.driverPad.getRawButtonReleased(Constants.OPEN)){
					newState = WheelTurnState.Close;
				}
	break;

			case HatchClose :
				SmartDashboard.putString("Wheel Turn", "Open"); 
				wheelTurnMotor.set(ControlMode.Position, setPosition(0));
				
				if(Hardware.driverPad.getRawButtonPressed(Constants.OPEN)){
					newState = WheelTurnState.Open;
				}
		break;

			
		default:
			newState = WheelTurnState.Close;
		break;	
	}

	if (newState != state){
		state = newState;
	}
	outputToSmartDashboard();
*/} 
    @Override
	public void outputToSmartDashboard() {
		SmartDashboard.putNumber("Claw Turn", wheelTurnMotor.getSelectedSensorPosition(0));

	}
	
	@Override
	public void setupSensors() {

			//Set Position To Zero When Started
			wheelTurnMotor.setSelectedSensorPosition(0, 0, 10);
				
			//Absolute Allows Position To State The Same After Restart
			wheelTurnMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 10);
			
			//Set peak and nominal outputs
			wheelTurnMotor.configNominalOutputForward(0, 10);
			wheelTurnMotor.configNominalOutputReverse(0, 10);
			wheelTurnMotor.configPeakOutputForward(0.5, 10);
			wheelTurnMotor.configPeakOutputReverse(-0.5, 10);
			
			//Set allowable closed-loop error
			wheelTurnMotor.configAllowableClosedloopError(0, 0, 10);
			
			//Set closed loop gains in PID slot 0
			wheelTurnMotor.config_kF(0, WheelTurn_Kf, 10);
			wheelTurnMotor.config_kP(0, WheelTurn_Kp, 10);
			wheelTurnMotor.config_kI(0, WheelTurn_Ki, 10);
			wheelTurnMotor.config_kD(0, WheelTurn_Kd, 10);
	}
}