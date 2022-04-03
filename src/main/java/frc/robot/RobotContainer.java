// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.commands.AutoShoot;
import frc.robot.commands.BabyShoot;
import frc.robot.commands.DelayedIndex;
import frc.robot.commands.ExtendClimber;
import frc.robot.commands.ExtendClimberLeft;
import frc.robot.commands.IndexControl;
import frc.robot.commands.IndexShooting;
import frc.robot.commands.IntakeBalls;
import frc.robot.commands.JoystickDrive;
import frc.robot.commands.JoystickDriveInit;
import frc.robot.commands.LightingControl;
import frc.robot.commands.LowerArms;
import frc.robot.commands.OuttakeBalls;
import frc.robot.commands.PlayMusic;
import frc.robot.commands.RaiseArms;
import frc.robot.commands.RetractClimber;
import frc.robot.commands.RetractClimberLeft;
import frc.robot.commands.ToggleArms;
import frc.robot.commands.autonomous.AutoHorizontalAim;
import frc.robot.commands.autonomous.AutonomousProtocol;
import frc.robot.commands.autonomous.AutonomousShoot;
import frc.robot.commands.autonomous.AutonomousShootUno;
import frc.robot.commands.autonomous.DriveForwardTimed;
import frc.robot.commands.autonomous.TurnDegrees;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Lighting;
import frc.robot.subsystems.Shooter;
import static frc.robot.Robot.*;
import static frc.robot.Constants.Autonomous.*;
import edu.wpi.first.wpilibj2.command.Command;

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
  private final Shooter Shooter = new Shooter(10);
  private final Intake Intake = new Intake();

  private final Climber Climber = new Climber();
  private final Lighting Lighting = new Lighting();
  
  private final IndexControl IndexOperation = new IndexControl(Index);
  private final JoystickDrive DrivetrainControl = new JoystickDrive(Drivetrain);

  private final AutonomousProtocol AutoCommand = new AutonomousProtocol(Drivetrain, Shooter, Intake, Index, Climber);

 
  
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    Drivetrain.setDefaultCommand(DrivetrainControl);
    Index.setDefaultCommand(new DelayedIndex(Index));
    // Climber.setDefaultCommand(new LowerArms(Climber));
    Lighting.setDefaultCommand(new LightingControl(Lighting));
    autoChooser.setDefaultOption("2 Ball Backup", new DelayedIndex(Index).alongWith(new AutonomousProtocol(Drivetrain, Shooter, Intake, Index, Climber)));
    autoChooser.addOption("1 Ball Taxi", new LowerArms(Climber).andThen(new DriveForwardTimed(Drivetrain, 4.0, -0.4).andThen(new AutonomousShootUno(Shooter, Index, 5.0))));
    autoChooser.addOption("Taxi, No Shoot", new LowerArms(Climber).andThen(new DriveForwardTimed(Drivetrain, 4.0, -0.4)));
    
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

    X1J_Y.toggleWhenActive(new PlayMusic(Drivetrain));
    // whenHeld(new PlayMusic(Drivetrain), false);
    // .whenActive(new PlayMusic(Drivetrain));
    

    X2J_X.whenHeld(new IntakeBalls(Intake));
    X2J_B.whenHeld(new OuttakeBalls(Intake, Index));
    X2J_A.whenHeld(new AutoShoot(Shooter)).whenHeld(new IndexShooting(Index));
    X2J_Y.whenHeld(new ToggleArms(Climber));

    X2J_RB.whenHeld(new ExtendClimberLeft(Climber, 0.45));
    X2J_LB.whenHeld(new RetractClimberLeft(Climber, 0.45));

    X2J_LS.whenHeld(new BabyShoot(Shooter)).whenHeld(new IndexShooting(Index));
    // X2J_LS.whenHeld(new LowerArms(Climber));
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
}
