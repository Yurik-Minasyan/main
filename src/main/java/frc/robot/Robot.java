// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//we added the imports below this comment
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.Timer;

import edu.wpi.first.wpilibj.XboxController;

/**
 * The methods in this class are called automatically corresponding to each mode, as described in
 * the TimedRobot documentation. If you change the name of this class or the package after creating
 * this project, you must also update the Main.java file in the project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCenterCoral = "Center and Coral";
  private static final String kJustDrive = "Just Drive";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  private final SparkMax rollerMotor = new SparkMax(5, MotorType.kBrushed);
  private final SparkMax catchMotor = new SparkMax(7, MotorType.kBrushed);
  private final SparkMax leftLeader = new SparkMax(1, MotorType.kBrushed);
  private final SparkMax leftFollower = new SparkMax(2, MotorType.kBrushed);
  private final SparkMax rightLeader = new SparkMax(4, MotorType.kBrushed);
  private final SparkMax rightFollower = new SparkMax(6, MotorType.kBrushed);
  
  private final DifferentialDrive myDrive = new DifferentialDrive(leftLeader, rightLeader);

  private final SparkMaxConfig driveConfig = new SparkMaxConfig();
  private final SparkMaxConfig rollerConfig = new SparkMaxConfig();

  private final Timer timer1 = new Timer();
  // private final Timer timer2 = new Timer();


  private double driveSpeed = 1;
  private double rollerOut = 0;

  private final XboxController gamepad0 = new XboxController(0); //driver Controller
  private final XboxController gamepad1 = new XboxController(1); //operator controller

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  public Robot() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("Center and Coral", kCenterCoral); //add all new auto modes below the setDefaultOption line
    m_chooser.addOption("Just Drive", kJustDrive);
    SmartDashboard.putData("Auto choices", m_chooser);
    driveConfig.smartCurrentLimit(60);
    driveConfig.voltageCompensation(12);
    
    driveConfig.follow(leftLeader);
    leftFollower.configure(driveConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    driveConfig.follow(rightLeader);
    rightFollower.configure(driveConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    driveConfig.disableFollowerMode();
    rightLeader.configure(driveConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    // driveConfig.inverted(true);
    leftLeader.configure(driveConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    rollerConfig.smartCurrentLimit(60);
    rollerConfig.voltageCompensation(10);
    rollerMotor.configure(rollerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    timer1.start();
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {} 

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
    timer1.restart();
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    /*switch (m_autoSelected) {
      case kCenterCoral:
        //Drive forward to the reef
        //stop and deposit the coral
        //stop everything
        if(timer1.get() < 1.85){ //this is drive to the reef
          myDrive.tankDrive(.5, .5);
        }
        else if(timer1.get() < 3){ //wait 1.15 seconds for everything to settle
          myDrive.tankDrive(0, 0);
        }
        else if(timer1.get() < 5.5){ //this is stop moving and spit out coral
          myDrive.tankDrive(0, 0);
          
        }
        else if(timer1.get() < 6){ //back up a little bit
          myDrive.tankDrive(-.5, -.5);
          rollerMotor.set(0);
        }
        else if(timer1.get() < 7){ //turn about 90 degrees
          myDrive.tankDrive(.5, -.5);
          rollerMotor.set(0);
        }
        else{ //end of auto turn everything off
          myDrive.tankDrive(0, 0);
          rollerMotor.set(0);
        }
        break;
      case kJustDrive:
        if(timer1.get() < .9){
          myDrive.tankDrive(.5, .5);
        }
        else{
          myDrive.tankDrive(0, 0);
        }
        break;
      case kDefaultAuto:
      default:
        
        break;
    }*/
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    driveSpeed = 2;
    if(gamepad1.getYButton() == true){
        // timer2.start();
        // while (timer2.get()<=4) {
          rollerMotor.set(0.8);
        // }
        // rollerMotor.set(0);
        // timer2.reset();
    }
   else if(gamepad1.getAButton() == true){
      // timer2.start();
      // while (timer2.get()<=4) {
        rollerMotor.set(-1);
      // }
      // rollerMotor.set(0);
      // timer2.reset();
  }
  else 
  {
    rollerMotor.set(0);
  }

  if(gamepad1.getBButton() == true){
    // timer2.start();
    // while (timer2.get()<=4) {
      catchMotor.set(0.8);
    // }
    // rollerMotor.set(0);
    // timer2.reset();
}
else if(gamepad1.getXButton() == true){
  // timer2.start();
  // while (timer2.get()<=4) {
    catchMotor.set(-1);
  // }
  // rollerMotor.set(0);
  // timer2.reset();
}
else 
{
catchMotor.set(0);
}
    
  //   if(gamepad0.getRightBumperButton()){
  //     driveSpeed = 1.3; //set back to full speed.
  // }

    //Uncomment the line below if you want tank style controls
    myDrive.tankDrive(gamepad0.getLeftY()/driveSpeed, gamepad0.getRightY()/driveSpeed);
    //Uncomment the line below if you want arcade style controls
    //myDrive.arcadeDrive(-gamepad0.getLeftY()/driveSpeed, -gamepad0.getRightX()/driveSpeed);

}

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
