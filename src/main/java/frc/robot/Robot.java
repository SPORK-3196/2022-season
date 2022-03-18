// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.cscore.HttpCamera;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import static frc.robot.Constants.*;
import static frc.robot.Constants.XboxController.*;
import static frc.robot.Constants.Drivetrain.*;
import static frc.robot.Constants.Limelight.*;
import static frc.robot.Constants.Shooter.*;
import static frc.robot.Constants.Robot.*;
import static frc.robot.Constants.Field.*;



/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;

  public static XboxController X1_CONTROLLER = new XboxController(0);
  public static XboxController X2_CONTROLLER = new XboxController(1);


  public static JoystickButton X1J_A = new JoystickButton(X1_CONTROLLER, XboxController.Button.kA.value);
  public static JoystickButton X1J_B = new JoystickButton(X1_CONTROLLER, XboxController.Button.kB.value);
  public static JoystickButton X1J_X = new JoystickButton(X1_CONTROLLER, XboxController.Button.kX.value);
  public static JoystickButton X1J_Y = new JoystickButton(X1_CONTROLLER, XboxController.Button.kY.value);
  public static JoystickButton X1J_LB = new JoystickButton(X1_CONTROLLER, XboxController.Button.kLeftBumper.value);
  public static JoystickButton X1J_RB = new JoystickButton(X1_CONTROLLER, XboxController.Button.kRightBumper.value);
  
  public static JoystickButton X2J_A = new JoystickButton(X2_CONTROLLER, XboxController.Button.kA.value);
  public static JoystickButton X2J_B = new JoystickButton(X2_CONTROLLER, XboxController.Button.kB.value);
  public static JoystickButton X2J_X = new JoystickButton(X2_CONTROLLER, XboxController.Button.kX.value);
  public static JoystickButton X2J_Y = new JoystickButton(X2_CONTROLLER, XboxController.Button.kY.value);
  public static JoystickButton X2J_LB = new JoystickButton(X2_CONTROLLER, XboxController.Button.kLeftBumper.value);
  public static JoystickButton X2J_RB = new JoystickButton(X2_CONTROLLER, XboxController.Button.kRightBumper.value);
  
  public static JoystickButton X2J_LS = new JoystickButton(X2_CONTROLLER, XboxController.Button.kLeftStick.value);
  public static JoystickButton X2J_RS = new JoystickButton(X2_CONTROLLER, XboxController.Button.kRightStick.value);
  private HttpCamera LimelightVideoFeed;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();
    LimelightTable = NetworkTableInstance.getDefault().getTable("limelight");

    LimelightVideoFeed = new HttpCamera("limelight", "http://10.31.96.11:5800/stream.mjpg");	
    AI_TAB.add("LimeLight Video", LimelightVideoFeed);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();

    LimelightTable = NetworkTableInstance.getDefault().getTable("limelight");

    TX = LimelightTable.getEntry("tx").getDouble(TX);
    TY = LimelightTable.getEntry("ty").getDouble(TY);
    TA = LimelightTable.getEntry("ta").getDouble(TA);
    TV = LimelightTable.getEntry("tv").getDouble(TV);

    DISTANCE_FROM_TARGET = (UPPER_HUB_HEIGHT_CM - LIMELIGHT_HEIGHT_CM) / Math.tan(Math.toRadians(LimelightAngle + TY));
    AI_DISTANCE_ENTRY.setDouble(DISTANCE_FROM_TARGET);

    if (X1_CONTROLLER.isConnected())
    {
      X1_RTValue = X1_CONTROLLER.getRightTriggerAxis();
      X1_LTValue = X1_CONTROLLER.getLeftTriggerAxis();
      
      X1_RB = X1_CONTROLLER.getRightBumper();
      X1_LB = X1_CONTROLLER.getLeftBumper();

      X1_LJX = X1_CONTROLLER.getLeftX();
      X1_LJY = X1_CONTROLLER.getLeftY();
      X1_RJX = X1_CONTROLLER.getRightX();
      X1_RJY = X1_CONTROLLER.getRightY();      
      
      X1_XButton = X1_CONTROLLER.getXButton();
      X1_YButton = X1_CONTROLLER.getYButton();
      X1_AButton = X1_CONTROLLER.getAButton();
      X1_BButton = X1_CONTROLLER.getBButton(); 
      
      X1_RT_Entry.setDouble(X1_RTValue);
      X1_LT_Entry.setDouble(X1_LTValue);

      X1_RB_Entry.setBoolean(X1_RB);
      X1_LB_Entry.setBoolean(X1_LB);

      X1_LJX_Entry.setDouble(X1_LJX);
      X1_LJY_Entry.setDouble(X1_LJY);
      X1_RJX_Entry.setDouble(X1_RJX);
      X1_RJY_Entry.setDouble(X1_RJY);

      X1_XButtonEntry.setBoolean(X1_XButton);
      X1_YButtonEntry.setBoolean(X1_YButton);
      X1_AButtonEntry.setBoolean(X1_AButton);
      X1_BButtonEntry.setBoolean(X1_BButton);
    }
    if (X2_CONTROLLER.isConnected())
    {
      X2_RTValue = X2_CONTROLLER.getRightTriggerAxis();
      X2_LTValue = X2_CONTROLLER.getLeftTriggerAxis();
      
      X2_RB = X2_CONTROLLER.getRightBumper();
      X2_LB = X2_CONTROLLER.getLeftBumper();

      X2_LJX = X2_CONTROLLER.getLeftX();
      X2_LJY = X2_CONTROLLER.getLeftY();
      X2_RJX = X2_CONTROLLER.getRightX();
      X2_RJY = X2_CONTROLLER.getRightY();      
      
      X2_XButton = X2_CONTROLLER.getXButton();
      X2_YButton = X2_CONTROLLER.getYButton();
      X2_AButton = X2_CONTROLLER.getAButton();
      X2_BButton = X2_CONTROLLER.getBButton(); 
      
      X2_RT_Entry.setDouble(X2_RTValue);
      X2_LT_Entry.setDouble(X2_LTValue);

      X2_RB_Entry.setBoolean(X2_RB);
      X2_LB_Entry.setBoolean(X2_LB);

      X2_LJX_Entry.setDouble(X2_LJX);
      X2_LJY_Entry.setDouble(X2_LJY);
      X2_RJX_Entry.setDouble(X2_RJX);
      X2_RJY_Entry.setDouble(X2_RJY);

      X2_XButtonEntry.setBoolean(X2_XButton);
      X2_YButtonEntry.setBoolean(X2_YButton);
      X2_AButtonEntry.setBoolean(X2_AButton);
      X2_BButtonEntry.setBoolean(X2_BButton);
    }
    
    DT_PowerConstant = DT_PowerConstantEntry.getDouble(100) * 0.01;
    
    ComputedRPM = (1700) * (Math.pow(Math.E, (0.00116 * DISTANCE_FROM_TARGET)));
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {
    LimelightTable.getEntry("camMode").setDouble(1); // Set's Limelight camera mode to Driver Camera
    LimelightTable.getEntry("ledMode").setDouble(1); // Set's Limelight LED mode to off
  }

  @Override
  public void disabledPeriodic() {
    LimelightTable.getEntry("camMode").setDouble(1); // Set's Limelight camera mode to Driver Camera
    LimelightTable.getEntry("ledMode").setDouble(1); // Set's Limelight LED mode to off

    
  }

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
    LimelightTable.getEntry("camMode").setDouble(0); // Set's Limelight camera mode to Vision Processing
    LimelightTable.getEntry("ledMode").setDouble(3); // Set's Limelight LED mode to on
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
    LimelightTable.getEntry("camMode").setDouble(1); // Set's Limelight camera mode to Vision Processing
    LimelightTable.getEntry("ledMode").setDouble(1); // Set's Limelight LED mode to off
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    if (RUN_LIMELIGHT_VISON) {
      LimelightTable.getEntry("camMode").setDouble(0); // Set's Limelight camera mode to Vision Processing
      LimelightTable.getEntry("ledMode").setDouble(3); // Set's Limelight LED mode to on
    }
    else {
      LimelightTable.getEntry("camMode").setDouble(1); // Set's Limelight camera mode to Vision Processing
      LimelightTable.getEntry("ledMode").setDouble(1); // Set's Limelight LED mode to off
    }
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
