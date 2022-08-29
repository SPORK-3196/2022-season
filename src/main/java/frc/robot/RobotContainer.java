// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.Shooter.AutoShoot;
import frc.robot.commands.Shooter.BabyShoot;
import frc.robot.commands.Drivetrain.BallOrientation;
import frc.robot.commands.Lighting.ExtendClimbLighting;
import frc.robot.commands.Index.DelayedIndex;
import frc.robot.commands.Climber.ExtendClimber;
import frc.robot.commands.Index.IndexShootingLower;
import frc.robot.commands.Index.IndexShootingUpper;
import frc.robot.commands.Intake.IntakeActivation;
import frc.robot.commands.Intake.IntakeBalls;
import frc.robot.commands.Lighting.IntakeLighting;
import frc.robot.commands.Drivetrain.JoystickDrive;
import frc.robot.commands.Lighting.LightingControl;
import frc.robot.commands.Climber.LowerArms;
import frc.robot.commands.Climber.RaiseArms;
import frc.robot.commands.Intake.OuttakeBalls;
import frc.robot.commands.Drivetrain.PlayMusic;
import frc.robot.commands.Lighting.RetractClimbLighting;
import frc.robot.commands.Climber.RetractClimber;
import frc.robot.commands.Climber.StabStabScoop;
import frc.robot.commands.Lighting.ShootLighting;
import frc.robot.commands.Drivetrain.TargetOrientation;
import frc.robot.commands.Climber.ToggleArms;
import frc.robot.commands.Lighting.ToggleClimberLighting;
import frc.robot.commands.Shooter.TweenShoot;
import frc.robot.commands.Lighting.VisionTargetShooting;
import frc.robot.commands.autonomous.TwoBallAuto;
import frc.robot.commands.autonomous.DriveForwardTimed;
import frc.robot.commands.autonomous.FourBallAuto;
import frc.robot.commands.autonomous.OneBallAuto;
import frc.robot.commands.autonomous.ThreeBallAuto;
import frc.robot.commands.autonomous.TurnDegreesCCW;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Lighting;
import frc.robot.subsystems.Shooter;
import static frc.robot.Robot.*;
import static frc.robot.GlobalVars.Vision.*;

import java.util.List;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import static frc.robot.GlobalVars.Autonomous.*;
import frc.robot.Constants.AutoDriveConstants;
import static frc.robot.Constants.Drivetrain.*;
import static frc.robot.GlobalVars.Drivetrain.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Drivetrain Drivetrain = new Drivetrain();
  private final Index Index = new Index();
  private final Shooter Shooter = new Shooter(50);
  private final Intake Intake = new Intake();

  private final Climber Climber = new Climber();
  private final Lighting Lighting = new Lighting();

  private final JoystickDrive DrivetrainControl = new JoystickDrive(Drivetrain);

 
  
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    Drivetrain.setDefaultCommand(DrivetrainControl);
    Index.setDefaultCommand(new DelayedIndex(Index));
    Lighting.setDefaultCommand(new LightingControl(Lighting));
    // autoChooser.addOption("4 Ball Auto (In Beta)", new FourBallAuto(Drivetrain, Shooter, Intake, Index, Climber));
    autoChooser.addOption("3 Ball Auto (In Beta)", new ThreeBallAuto(Drivetrain, Shooter, Intake, Index, Climber));
    autoChooser.setDefaultOption("2 Ball Backup", new TwoBallAuto(Drivetrain, Shooter, Intake, Index, Climber));
    // autoChooser.addOption("1 Ball Taxi", new LowerArms(Climber).andThen(new DriveForwardTimed(Drivetrain, 4.0, -0.4).andThen(new AutoHorizontalAim(Drivetrain, 3)).andThen(new AutonomousShootUno(Shooter, Index, 5.0))));
    autoChooser.addOption("1 Ball Taxi", new OneBallAuto(Drivetrain, Shooter, Intake, Index, Climber));
    autoChooser.addOption("Taxi, No Shoot", new RaiseArms(Climber).andThen(new DriveForwardTimed(Drivetrain, 3.5, -0.15)));
    autoChooser.addOption("Stab Stab Scoop", new StabStabScoop(Climber));
    // autoChooser.addOption("CCW Turn", new TurnDegreesCCW(Drivetrain, 3, 90));
    // autoChooser.addOption("Ramsete Test", returnRamseteCommand());
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link\
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // Robot.X1J_AButton.whenHeld(new PlayMusic(Drivetrain));
    // X1J_A.whenHeld(new HorizontalAim(Drivetrain));

    X1J_B.toggleWhenActive(new PlayMusic(Drivetrain));
    // whenHeld(new PlayMusic(Drivetrain), false);
    // .whenActive(new PlayMusic(Drivetrain));

    X1J_A.whenHeld(new TargetOrientation(Drivetrain)).whenHeld(new VisionTargetShooting(Lighting, primaryYaw));
    X1J_X.whenHeld(new BallOrientation(Drivetrain)).whenHeld(new VisionTargetShooting(Lighting, backupYaw));

    // X2J_X.whenHeld(new IntakeBalls(Intake)).whenHeld(new IntakeLighting(Lighting));

    X2J_X.whenHeld(new IntakeActivation(Climber, Intake)).whenHeld(new IntakeLighting(Lighting));
    X2J_X.whenReleased(new RaiseArms(Climber));

    X2J_B.whenHeld(new OuttakeBalls(Intake, Index)).whenHeld(new IntakeLighting(Lighting));
    X2J_A.whenHeld(new AutoShoot(Shooter)).whenHeld(new IndexShootingUpper(Index)).whenHeld(new VisionTargetShooting(Lighting, primaryYaw));
    // X2J_A.whenHeld(new TweenShoot(Shooter)).whenHeld(new IndexShooting(Index)).whenHeld(new ShootLighting(Lighting));
    X2J_Y.whenHeld(new ToggleArms(Climber)).whenPressed(new ToggleClimberLighting(Lighting));

    X2J_RB.whenHeld(new ExtendClimber(Climber, 0.45)).whenHeld(new ExtendClimbLighting(Lighting));
    X2J_LB.whenHeld(new RetractClimber(Climber, 0.45)).whenHeld(new RetractClimbLighting(Lighting));

    X2J_LS.whenHeld(new BabyShoot(Shooter)).whenHeld(new IndexShootingLower(Index)).whenHeld(new ShootLighting(Lighting));
    
    X2J_RS.whenHeld(new TweenShoot(Shooter)).whenHeld(new IndexShootingLower(Index)).whenHeld(new ShootLighting(Lighting));


  }
 
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomousP
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in asutonomous
    // return new AutoHorizontalAim(Drivetrain, 30);
    return autoChooser.getSelected();
    // return (Command) autoChooser.getSelected();
  }
  
  public Command returnRamseteCommand() {
    Drivetrain.frontLeft.setNeutralMode(NeutralMode.Brake);
    Drivetrain.frontRight.setNeutralMode(NeutralMode.Brake);
    Drivetrain.rearLeft.setNeutralMode(NeutralMode.Brake);
    Drivetrain.rearRight.setNeutralMode(NeutralMode.Brake);

    DifferentialDriveVoltageConstraint autoDriveVoltageConstraint = 
      new DifferentialDriveVoltageConstraint(
        new SimpleMotorFeedforward(
          AutoDriveConstants.ksVolts, 
          AutoDriveConstants.kvVoltSecondsPerMeter,
          AutoDriveConstants.kaVoltSecondsSquaredPerMeter), 
        driveKinematics, 
        7);
    TrajectoryConfig trajectoryConfig = 
      new TrajectoryConfig(
        AutoDriveConstants.kMaxSpeedMetersPerSeocond, 
        AutoDriveConstants.kMaxAccelerationMetersPerSecondSquared)
      .setKinematics(driveKinematics)
      .addConstraint(autoDriveVoltageConstraint);
    
    Trajectory testTrajectory = 
      TrajectoryGenerator.generateTrajectory(
        new Pose2d(3, 5, new Rotation2d(0)), 
        List.of(
          // new Translation2d(0.5, 0), 
          // new Translation2d(1, 0)
          ),
        new Pose2d(4.5, 5, new Rotation2d(0)), 
        trajectoryConfig);

    RamseteCommand ramseteCommand = new RamseteCommand(
      testTrajectory, 
      Drivetrain::getPose, 
      new RamseteController(), 
      new SimpleMotorFeedforward(
        AutoDriveConstants.ksVolts, 
        AutoDriveConstants.kvVoltSecondsPerMeter,
        AutoDriveConstants.kaVoltSecondsSquaredPerMeter), 
      driveKinematics, 
      Drivetrain::getWheelSpeeds, 
      new PIDController(0, 0, 0), 
      new PIDController(0, 0, 0), 
      Drivetrain::tankDriveVolts, 
      Drivetrain);

    Drivetrain.resetOdometry(testTrajectory.getInitialPose());

    return ramseteCommand.andThen(() -> Drivetrain.tankDriveVolts(0, 0));
  }
  
}
