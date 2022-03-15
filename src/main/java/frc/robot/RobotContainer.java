// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.AutoShoot;
import frc.robot.commands.ExtendClimber;
import frc.robot.commands.HorizontalAim;
import frc.robot.commands.IndexControl;
import frc.robot.commands.IntakeBalls;
import frc.robot.commands.JoystickDrive;
import frc.robot.commands.LowerArms;
import frc.robot.commands.OuttakeBalls;
import frc.robot.commands.PlayMusic;
import frc.robot.commands.RaiseArms;
import frc.robot.commands.RetractClimber;
import frc.robot.commands.ToggleArms;
import frc.robot.commands.autonomous.AutonomousProtocol;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import static frc.robot.Robot.*;
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
  private final Shooter Shooter = new Shooter();
  private final Intake Intake = new Intake();
  private final Index Index = new Index();
  private final Climber Climber = new Climber();
  
  private final IndexControl IndexOperation = new IndexControl(Index);
  private final JoystickDrive DrivetrainControl = new JoystickDrive(Drivetrain);

  private final AutonomousProtocol AutoCommand = new AutonomousProtocol(Drivetrain, Shooter, Index, Intake);

  
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    Drivetrain.setDefaultCommand(DrivetrainControl);
    Index.setDefaultCommand(IndexOperation);
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link\
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // Robot.X1J_AButton.whenHeld(new PlayMusic(Drivetrain));
    X1J_A.whenHeld(new HorizontalAim(Drivetrain));

    X1J_LB.and(X1J_RB).whenActive(new PlayMusic(Drivetrain));
    X1J_RB.and(X1J_LB).whenActive(new PlayMusic(Drivetrain));

    X2J_X.whenHeld(new IntakeBalls(Intake));
    X2J_B.whenHeld(new OuttakeBalls(Intake, Index));
    X2J_A.whenHeld(new AutoShoot(Shooter));
    X2J_Y.whenHeld(new ToggleArms(Climber));

    X2J_RB.whenHeld(new ExtendClimber(Climber, 0.3));
    X2J_LB.whenHeld(new RetractClimber(Climber, 0.3));

    X2J_LS.whenHeld(new RaiseArms(Climber));
    X2J_LS.whenHeld(new LowerArms(Climber));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomousP
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return AutoCommand;
  }
}
