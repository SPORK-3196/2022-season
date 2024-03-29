// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static frc.robot.Constants.Field.UPPER_HUB_HEIGHT_M;
import static frc.robot.Constants.Robot.CAMERA_ANGLE_RADIANS;
import static frc.robot.Constants.Robot.CAMERA_HEIGHT_M;
import static frc.robot.GlobalVars.Autonomous.autoChooser;
import static frc.robot.GlobalVars.Drivetrain.DT_PowerConstant;
import static frc.robot.GlobalVars.Drivetrain.DT_PowerConstantEntry;
import static frc.robot.GlobalVars.Drivetrain.songChooser;
import static frc.robot.GlobalVars.Field.MatchTimeEntry;
import static frc.robot.GlobalVars.Shooter.AutoComputedRPM;

import static frc.robot.GlobalVars.Vision.AI_DISTANCE_ENTRY;
import static frc.robot.GlobalVars.Vision.BlueAllianceCargoPipeline;
import static frc.robot.GlobalVars.Vision.DISTANCE_FROM_TARGET;
import static frc.robot.GlobalVars.Vision.RUN_BACKUP_VISION;
import static frc.robot.GlobalVars.Vision.RUN_VISION;
import static frc.robot.GlobalVars.Vision.RedAllianceCargoPipeline;
import static frc.robot.GlobalVars.Vision.backupCamera;
import static frc.robot.GlobalVars.Vision.backupCameraResult;
import static frc.robot.GlobalVars.Vision.backupHasTargets;
import static frc.robot.GlobalVars.Vision.backupPitch;
import static frc.robot.GlobalVars.Vision.backupPitchRadians;
import static frc.robot.GlobalVars.Vision.backupTrackedTarget;
import static frc.robot.GlobalVars.Vision.backupYaw;
import static frc.robot.GlobalVars.Vision.primaryCamera;
import static frc.robot.GlobalVars.Vision.primaryCameraResult;
import static frc.robot.GlobalVars.Vision.primaryHasTargets;
import static frc.robot.GlobalVars.Vision.primaryPitch;
import static frc.robot.GlobalVars.Vision.primaryPitchRadians;
import static frc.robot.GlobalVars.Vision.primaryTrackedTarget;
import static frc.robot.GlobalVars.Vision.primaryYaw;

import static frc.robot.GlobalVars.XboxController.X1_AButton;
import static frc.robot.GlobalVars.XboxController.X1_AButtonEntry;
import static frc.robot.GlobalVars.XboxController.X1_BButton;
import static frc.robot.GlobalVars.XboxController.X1_BButtonEntry;
import static frc.robot.GlobalVars.XboxController.X1_LB;
import static frc.robot.GlobalVars.XboxController.X1_LB_Entry;
import static frc.robot.GlobalVars.XboxController.X1_LJX;
import static frc.robot.GlobalVars.XboxController.X1_LJX_Entry;
import static frc.robot.GlobalVars.XboxController.X1_LJY;
import static frc.robot.GlobalVars.XboxController.X1_LJY_Entry;
import static frc.robot.GlobalVars.XboxController.X1_LTValue;
import static frc.robot.GlobalVars.XboxController.X1_LT_Entry;
import static frc.robot.GlobalVars.XboxController.X1_RB;
import static frc.robot.GlobalVars.XboxController.X1_RB_Entry;
import static frc.robot.GlobalVars.XboxController.X1_RJX;
import static frc.robot.GlobalVars.XboxController.X1_RJX_Entry;
import static frc.robot.GlobalVars.XboxController.X1_RJY;
import static frc.robot.GlobalVars.XboxController.X1_RJY_Entry;
import static frc.robot.GlobalVars.XboxController.X1_RTValue;
import static frc.robot.GlobalVars.XboxController.X1_RT_Entry;
import static frc.robot.GlobalVars.XboxController.X1_XButton;
import static frc.robot.GlobalVars.XboxController.X1_XButtonEntry;
import static frc.robot.GlobalVars.XboxController.X1_YButton;
import static frc.robot.GlobalVars.XboxController.X1_YButtonEntry;
import static frc.robot.GlobalVars.XboxController.X2_AButton;
import static frc.robot.GlobalVars.XboxController.X2_AButtonEntry;
import static frc.robot.GlobalVars.XboxController.X2_BButton;
import static frc.robot.GlobalVars.XboxController.X2_BButtonEntry;
import static frc.robot.GlobalVars.XboxController.X2_LB;
import static frc.robot.GlobalVars.XboxController.X2_LB_Entry;
import static frc.robot.GlobalVars.XboxController.X2_LJS;
import static frc.robot.GlobalVars.XboxController.X2_LJX;
import static frc.robot.GlobalVars.XboxController.X2_LJX_Entry;
import static frc.robot.GlobalVars.XboxController.X2_LJY;
import static frc.robot.GlobalVars.XboxController.X2_LJY_Entry;
import static frc.robot.GlobalVars.XboxController.X2_LTValue;
import static frc.robot.GlobalVars.XboxController.X2_LT_Entry;
import static frc.robot.GlobalVars.XboxController.X2_RB;
import static frc.robot.GlobalVars.XboxController.X2_RB_Entry;
import static frc.robot.GlobalVars.XboxController.X2_RJX;
import static frc.robot.GlobalVars.XboxController.X2_RJX_Entry;
import static frc.robot.GlobalVars.XboxController.X2_RJY;
import static frc.robot.GlobalVars.XboxController.X2_RJY_Entry;
import static frc.robot.GlobalVars.XboxController.X2_RTValue;
import static frc.robot.GlobalVars.XboxController.X2_RT_Entry;
import static frc.robot.GlobalVars.XboxController.X2_XButton;
import static frc.robot.GlobalVars.XboxController.X2_XButtonEntry;
import static frc.robot.GlobalVars.XboxController.X2_YButton;
import static frc.robot.GlobalVars.XboxController.X2_YButtonEntry;

import org.photonvision.PhotonCamera;
import org.photonvision.common.hardware.VisionLEDMode;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.util.net.PortForwarder;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;



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

  public static JoystickButton X1J_A =  new JoystickButton(X1_CONTROLLER, XboxController.Button.kA.value);
  public static JoystickButton X1J_B =  new JoystickButton(X1_CONTROLLER, XboxController.Button.kB.value);
  public static JoystickButton X1J_X =  new JoystickButton(X1_CONTROLLER, XboxController.Button.kX.value);
  public static JoystickButton X1J_Y =  new JoystickButton(X1_CONTROLLER, XboxController.Button.kY.value);
  public static JoystickButton X1J_LB = new JoystickButton(X1_CONTROLLER, XboxController.Button.kLeftBumper.value);
  public static JoystickButton X1J_RB = new JoystickButton(X1_CONTROLLER, XboxController.Button.kRightBumper.value);
 
  public static JoystickButton X1J_LS = new JoystickButton(X1_CONTROLLER, XboxController.Button.kLeftStick.value);
  public static JoystickButton X1J_RS = new JoystickButton(X1_CONTROLLER, XboxController.Button.kRightStick.value);
  
  public static JoystickButton X2J_A =  new JoystickButton(X2_CONTROLLER, XboxController.Button.kA.value);
  public static JoystickButton X2J_B =  new JoystickButton(X2_CONTROLLER, XboxController.Button.kB.value);
  public static JoystickButton X2J_X =  new JoystickButton(X2_CONTROLLER, XboxController.Button.kX.value);
  public static JoystickButton X2J_Y =  new JoystickButton(X2_CONTROLLER, XboxController.Button.kY.value);
  public static JoystickButton X2J_LB = new JoystickButton(X2_CONTROLLER, XboxController.Button.kLeftBumper.value);
  public static JoystickButton X2J_RB = new JoystickButton(X2_CONTROLLER, XboxController.Button.kRightBumper.value);
  public static JoystickButton X2J_LS = new JoystickButton(X2_CONTROLLER, XboxController.Button.kLeftStick.value);
  public static JoystickButton X2J_RS = new JoystickButton(X2_CONTROLLER, XboxController.Button.kRightStick.value);

  public static double [][] dataPoints = {
    {5.5, 2700},  
    {5.997, 2775},
    {6.18, 2800},  
    {6.68, 3000}, 
    {6.22, 2900}, 
    {5.3, 2600}, 
    {5, 2450},
    {4.5, 2300},
    {4, 2150},
    {3.5, 2050},
    {3.04, 1950},  
    {2.5, 1850}, 
    {2, 1775}, 
  };


  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();

    // PrimaryVideoFeed = new HttpCamera("Front Camera", "http://10.31.96.11:5800");
    // BackupVideoFeed = new HttpCamera("Backup Camera", "http://10.31.96.12:5800");

    primaryCamera = new PhotonCamera("Primary Camera");
    backupCamera = new PhotonCamera("Backup Camera");

  
    PortForwarder.add(5800, "10.31.96.11", 5800);

    PhotonCamera.setVersionCheckEnabled(false);

    // AI_TAB.add("LimeLight Video", PrimaryVideoFeed);
    // AI_TAB.add("Secondary Video", BackupVideoFeed);

    Shuffleboard.getTab("Autonomous Controls")
      .add(autoChooser);

    Shuffleboard.getTab("Drivetrain Info")
      .add(songChooser);

    
    


    NetworkTableInstance.getDefault().getTable("photonvision").getEntry("ledMode").setDouble(0);
    DriverStation.silenceJoystickConnectionWarning(true);

    
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


    
    
    primaryCameraResult = primaryCamera.getLatestResult();
    primaryHasTargets = primaryCameraResult.hasTargets();
    if (primaryCameraResult.hasTargets()) {
      primaryTrackedTarget = primaryCameraResult.getBestTarget();
      primaryYaw = primaryTrackedTarget.getYaw();
      // System.out.println(primaryYaw);
      primaryPitch = primaryTrackedTarget.getPitch();
      primaryPitchRadians = Units.degreesToRadians(primaryPitch);
    }
    
    backupCameraResult = backupCamera.getLatestResult();
    backupHasTargets = backupCameraResult.hasTargets();
    if (backupCameraResult.hasTargets()) {
      backupTrackedTarget = backupCameraResult.getBestTarget();
      backupYaw = backupTrackedTarget.getYaw();
      // System.out.println(backupYaw);
      backupPitch = backupTrackedTarget.getPitch();
      backupPitchRadians = Units.degreesToRadians(backupPitch);
    }
    
    DISTANCE_FROM_TARGET = (UPPER_HUB_HEIGHT_M - CAMERA_HEIGHT_M) / Math.tan(CAMERA_ANGLE_RADIANS + primaryPitchRadians);
    // DISTANCE_FROM_TARGET = PhotonUtils.calculateDistanceToTargetMeters(CAMERA_HEIGHT_M, TestHub, CAMERA_ANGLE_RADIANS, primaryPitchRadians);
    // System.out.println(DISTANCE_FROM_TARGET);

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


      if (!DriverStation.isFMSAttached()) {
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
      
      X2_LJS = X2_CONTROLLER.getLeftStickButton();
      
      X2_XButton = X2_CONTROLLER.getXButton();
      X2_YButton = X2_CONTROLLER.getYButton();
      X2_AButton = X2_CONTROLLER.getAButton();
      X2_BButton = X2_CONTROLLER.getBButton(); 
      
      if (!DriverStation.isFMSAttached()) {
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
    
    DT_PowerConstant = DT_PowerConstantEntry.getDouble(100) * 0.01;

    // TeleCom putedRPM = SH_SHOOTER_RPM_Entry.getDouble(TeleComputedRPM);
    
    // SH_SHOOTER_RPM_Entry.setDouble(TeleComputedRPM);

    AutoComputedRPM = 1537 + (66.6 * DISTANCE_FROM_TARGET) + 23.3 * (Math.pow(DISTANCE_FROM_TARGET, 2));

    if (DISTANCE_FROM_TARGET < 2) {
      AutoComputedRPM = (1390) * (Math.pow(Math.E, (0.118 * (DISTANCE_FROM_TARGET))));
    }
    else if (2 < DISTANCE_FROM_TARGET && DISTANCE_FROM_TARGET < 4) {
      AutoComputedRPM = (1400) * (Math.pow(Math.E, (0.118 * (DISTANCE_FROM_TARGET))));
    }
    else if (4 < DISTANCE_FROM_TARGET && DISTANCE_FROM_TARGET < 4.2) {
      AutoComputedRPM = (1420) * (Math.pow(Math.E, (0.118 * (DISTANCE_FROM_TARGET))));
    }
    else if (4.2 < DISTANCE_FROM_TARGET && DISTANCE_FROM_TARGET < 5) {
      AutoComputedRPM = (1440) * (Math.pow(Math.E, (0.118 * (DISTANCE_FROM_TARGET))));
    }
    

    
    for (double[] data: dataPoints) {
      if (DISTANCE_FROM_TARGET < data[0] + 0.1 && DISTANCE_FROM_TARGET > data[0] - 0.1 ) {
        AutoComputedRPM = data[1];
      }
    }

    // AutoP = DT_AutoPEntry.getDouble(AutoP);
    // DT_AutoPEntry.setDouble(AutoP);

    // SH_SHOOTER_POWER_Entry.setDouble(AutoComputedRPM);

    MatchTimeEntry.setDouble(DriverStation.getMatchTime());
    CommandScheduler.getInstance().run();
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {
    primaryCamera.setDriverMode(true); // Set's Limelight camera mode to Driver Camera
    primaryCamera.setLED(VisionLEDMode.kOff); // Set's Limelight LED mode to off
    NetworkTableInstance.getDefault().getTable("photonvision").getEntry("ledMode").setDouble(0);
  }

  @Override
  public void disabledPeriodic() {
    primaryCamera.setDriverMode(true); // Set's Limelight camera mode to Driver Camera
    primaryCamera.setLED(VisionLEDMode.kOff); // Set's Limelight LED mode to offf
    NetworkTableInstance.getDefault().getTable("photonvision").getEntry("ledMode").setDouble(0);   
  }

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();
     

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
    primaryCamera.setDriverMode(false); // Set's Limelight camera mode to Driver Camera
    primaryCamera.setLED(VisionLEDMode.kOn); // Set's Limelight LED mode to off
    NetworkTableInstance.getDefault().getTable("photonvision").getEntry("ledMode").setDouble(1);

    setCargoTrackingPipeline();
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
    // primaryCamera.setDriverMode(false); // Set's Limelight camera mode to Driver Camera
    primaryCamera.setLED(VisionLEDMode.kOn); // Set's Limelight LED mode to off
    
    NetworkTableInstance.getDefault().getTable("photonvision").getEntry("ledMode").setDouble(1);
    
    setCargoTrackingPipeline();

  } 

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {

    // primaryCamera.setDriverMode(false); // Set's Limelight camera mode to Vision Processing
    // primaryCamera.setPipelineIndex(0);
    primaryCamera.setLED(VisionLEDMode.kOn); // Set's Limelight LED mode to On
    
    if (RUN_VISION) {
      NetworkTableInstance.getDefault().getTable("photonvision").getEntry("ledMode").setDouble(1);
      primaryCamera.setDriverMode(false);
    }
    else {
      NetworkTableInstance.getDefault().getTable("photonvision").getEntry("ledMode").setDouble(0);
      primaryCamera.setDriverMode(true);
    }

    if (RUN_BACKUP_VISION) {
      backupCamera.setDriverMode(false);
    }
    else {
      backupCamera.setDriverMode(true);
    }
    
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
    primaryCamera.setDriverMode(false); // Set's Limelight camera mode to Vision Processing
    primaryCamera.setLED(VisionLEDMode.kOn); // Set's Limelight LED mode to On
    NetworkTableInstance.getDefault().getTable("photonvision").getEntry("ledMode").setDouble(1);
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
    primaryCamera.setDriverMode(false); // Set's Limelight camera mode to Vision Processing
    primaryCamera.setLED(VisionLEDMode.kOn); // Set's Limelight LED mode to On
    NetworkTableInstance.getDefault().getTable("photonvision").getEntry("ledMode").setDouble(1);
  }

  public void setCargoTrackingPipeline() {
    if (DriverStation.getAlliance().compareTo(DriverStation.Alliance.Blue) == 0) {
      backupCamera.setPipelineIndex(BlueAllianceCargoPipeline);
    }
    else if (DriverStation.getAlliance().compareTo(DriverStation.Alliance.Red) == 0) {
      backupCamera.setPipelineIndex(RedAllianceCargoPipeline);
    }
  }
}
