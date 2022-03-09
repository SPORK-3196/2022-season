// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.AutoShoot;
import frc.robot.commands.IndexControl;
import frc.robot.commands.IntakeBalls;
import frc.robot.commands.JoystickDrive;
import frc.robot.commands.OuttakeBalls;
import frc.robot.commands.autonomous.AutonomousProtocol;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
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
  
  private final AutoShoot ComputedShoot = new AutoShoot(Shooter);
  private final IndexControl OperateIntake = new IndexControl(Index);

  

  private final JoystickDrive DrivetrainControl = new JoystickDrive(Drivetrain);
  private final AutonomousProtocol AutoCommand = new AutonomousProtocol(Drivetrain);

  
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    Drivetrain.setDefaultCommand(DrivetrainControl);
    Shooter.setDefaultCommand(ComputedShoot);
    Index.setDefaultCommand(OperateIntake);
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    Robot.X2J_XButton.whenHeld(new IntakeBalls(Intake));
    Robot.X2J_BButton.whenHeld(new OuttakeBalls(Intake));
    Robot.X2J_AButton.whenHeld(new AutoShoot(Shooter));
  
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return AutoCommand;
  }
}
