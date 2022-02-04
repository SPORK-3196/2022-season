// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import static frc.robot.Constants.*;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;

  public XboxController X1_CONTROLLER = new XboxController(0);
  public XboxController X2_CONTROLLER = new XboxController(1);


  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();
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
  }


  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
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
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {}

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {

  }
}
