package frc.robot.Subsystems;

import frc.robot.Constants;
import frc.robot.Hardware;
import frc.robot.Lib.SubsystemFramework;
import frc.robot.Util.doubleSolenoid;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;

public class Drivetrain extends SubsystemFramework {
    //Hardware

    private DifferentialDrive myRobot;
	private WPI_TalonSRX rightMasterMotor;
	private WPI_TalonSRX leftMasterMotor;
	private WPI_VictorSPX rightSlaveMotor;
	private WPI_VictorSPX leftSlaveMotor;
	private doubleSolenoid shifter;

	// State
	public DrivetrainStates state = DrivetrainStates.HighGear;
	public DrivetrainStates prevState;

	public Drivetrain(WPI_TalonSRX rightMasterMotor, WPI_VictorSPX rightSlaveMotor,
			WPI_TalonSRX leftMasterMotor, WPI_VictorSPX leftSlaveMotor, 
			doubleSolenoid shifter) {
	

				// Setup Drivetrain
		myRobot = new DifferentialDrive(rightMasterMotor, leftMasterMotor);

		// Motor Controllers
		this.rightMasterMotor = rightMasterMotor;
		this.rightSlaveMotor = rightSlaveMotor;
		this.leftMasterMotor = leftMasterMotor;
		this.leftSlaveMotor = leftSlaveMotor;

		// Shifter
		this.shifter = shifter;

	}

	public enum DrivetrainStates {
		LowGear, HighGear;
	}


	@Override
	public void update() {

		if (Hardware.driverPad.getRawAxis(1) < 0.1 && Hardware.driverPad.getRawAxis(1) > -0.1){
			myRobot.arcadeDrive(0,(Hardware.driverPad.getRawAxis(4) * -1) );
		}
		if (Hardware.driverPad.getRawAxis(4) < 0.1 && Hardware.driverPad.getRawAxis(4) > -0.1){
			myRobot.arcadeDrive((Hardware.driverPad.getRawAxis(1) * -1), 0);
		}

        DrivetrainStates newState = state;
        
        	// Setup Master Slave Relationship
		rightSlaveMotor.follow(rightMasterMotor);
		leftSlaveMotor.follow(leftMasterMotor);
	

		switch (state) {
			case LowGear:

				
				// Shift Into LowGear
				shifter.close();
	
				// Joystick Control
				myRobot.arcadeDrive((Hardware.driverPad.getRawAxis(1) * -1), (Hardware.driverPad.getRawAxis(4) * -1), false);
	
				// Switch To HighGear When Asked
				if (Hardware.driverPad.getRawButton(Constants.DRIVETRAIN_HIGHGEAR)) {
					newState = DrivetrainStates.HighGear;
				
				}
	break;		
			case HighGear:
				// Shift Into HighGear
				shifter.open();
	
				// Joystick Control
				myRobot.arcadeDrive((Hardware.driverPad.getRawAxis(1) * -1), (Hardware.driverPad.getRawAxis(4) * -1),
						false);
	
				// Switch To LowGear When Asked
				if (Hardware.driverPad.getRawButton(Constants.DRIVETRAIN_LOWGEAR)) {
					newState = DrivetrainStates.LowGear;
				
				}
				
	break;
		
			default:
				newState = state;
			break;
		}

		if (newState != state) {
			state = newState;
		}

	}

	@Override
	public void outputToSmartDashboard() {

	}
	
	@Override
	public void setupSensors() {


		
				// Setup Master Slave Relationship
				rightSlaveMotor.follow(rightMasterMotor);
				leftSlaveMotor.follow(leftMasterMotor);
						
				// Setup Master Encoders
				rightMasterMotor.setSensorPhase(true);
				leftMasterMotor.setSensorPhase(true);
						
				rightMasterMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
				leftMasterMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
		
				rightMasterMotor.setSelectedSensorPosition(0, 0, 10);
				leftMasterMotor.setSelectedSensorPosition(0, 0, 10);
				
				rightMasterMotor.configNominalOutputForward(0, 10);
				leftMasterMotor.configNominalOutputForward(0, 10);
				rightMasterMotor.configNominalOutputReverse(0, 10);
				leftMasterMotor.configNominalOutputReverse(0, 10);
				rightMasterMotor.configPeakOutputForward(0.9, 10);
				leftMasterMotor.configPeakOutputForward(0.9, 10);
				rightMasterMotor.configPeakOutputReverse(-0.9, 10);
				leftMasterMotor.configPeakOutputReverse(-0.9, 10);
    }
}