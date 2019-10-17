/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;


import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.TimedRobot;
/*import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.PathfinderFRC;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower; */

import frc.robot.Lib.Loop.MultiLooper;
import frc.robot.Subsystems.*;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  //Loopers
  private MultiLooper DriveLooper = new MultiLooper("DriveLooper", 1 / 200.0, false); // Drivetrain looper
  private MultiLooper SSLooper = new MultiLooper("SSLooper", 1 / 200.0, false); // Subsystems looper
  
  // Subsystems
	private Drivetrain myDrive = new Drivetrain(Hardware.rightMasterMotor, Hardware.rightSlaveMotor,
											                        Hardware.leftMasterMotor, Hardware.leftSlaveMotor,
                                              Hardware.shifter);
  private Elevator elevator = new Elevator(Hardware.elevatorMotor1, Hardware.elevatorMotor2,Hardware.elevatorMotor3, Constants.Elevator_Average_Ticks_Per_Inch, 
                                           Constants.Elevator_Kf, Constants.Elevator_Kp, Constants.Elevator_Ki, 
                                           Constants.Elevator_Kd);                         
  private ClawRotation clawRotation = new ClawRotation(Hardware.clawRotationMotor, Constants.ClawRotation_Average_Ticks_Per_Degree,
                                                       Constants.Claw_Rotation_Kf, Constants.Claw_Rotation_Kp,
                                                       Constants.Claw_Rotation_Ki, Constants.Claw_Rotation_Kd);
  private ClawWheels clawWheels = new ClawWheels(Hardware.clawWheelsMotor);   
 // private HatchPickup hatchPickup = new HatchPickup(Hardware.HatchMotor, Constants.Hatch_Average_Ticks_Per_Degree, 
 //                                                   Constants.Hatch_Kf, Constants.Hatch_Kp, Constants.Hatch_Ki, Constants.Hatch_Kd);
  private WheelTurn wheelTurn = new WheelTurn(Hardware.wheelTurnMotor, Constants.WheelTurn_Average_Ticks_Per_Degree, 
                                              Constants.Wheel_Turn_Kf, Constants.Wheel_Turn_Kd, Constants.Wheel_Turn_Ki, 
                                              Constants.Wheel_Turn_Kp);                                                                                                    

  
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    SSLooper.addLoopable(clawWheels);
    
    myDrive.setupSensors();
    DriveLooper.addLoopable(myDrive);

    elevator.setupSensors();
    SSLooper.addLoopable(elevator);

    clawRotation.setupSensors();
    SSLooper.addLoopable(clawRotation);

    wheelTurn.setupSensors();
    SSLooper.addLoopable(wheelTurn);
    
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {

  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    SSLooper.start();
  	DriveLooper.start();

    SSLooper.update(); 
		DriveLooper.update();
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
  }

  /**
   * This function is called periodically during operator control.
   */
   @Override
   public void teleopInit() {
    SSLooper.start();
  	DriveLooper.start();

    SSLooper.update(); 
		DriveLooper.update();

   }
  
   @Override
  public void teleopPeriodic() {
  
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
