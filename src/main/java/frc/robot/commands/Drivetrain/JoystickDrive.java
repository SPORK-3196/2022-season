// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Drivetrain;

import static frc.robot.GlobalVars.Drivetrain.DT_PowerConstant;
import static frc.robot.GlobalVars.XboxController.X1_LJX;
import static frc.robot.GlobalVars.XboxController.X1_LJY;
import static frc.robot.Robot.X1_CONTROLLER;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;


/** A JoystickDrive command that uses a drivetrain subsystem. */
public class JoystickDrive extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Drivetrain drivetrain;


  double steering_adjust;
  double heading_error;
  DifferentialDrive.WheelSpeeds wheelSpeeds;

  
  double speedControl;
  double rotationControl;

  /**
   * Creates a new JoystickDrive. Tele-Operated command for controlling drivetrain with primary xbox controller.
   *
   * @param drivetrain The drivetrain subsystem used by this command.
   */
  public JoystickDrive(Drivetrain drivetrain) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.drivetrain = drivetrain;
    
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // Set neutral mode of drive motors to Coast
    drivetrain.frontLeft.setNeutralMode(NeutralMode.Coast);
    drivetrain.rearLeft.setNeutralMode(NeutralMode.Coast);
    drivetrain.frontRight.setNeutralMode(NeutralMode.Coast);
    drivetrain.rearRight.setNeutralMode(NeutralMode.Coast);
    drivetrain.driveModeSet = true;

    // Set deadband of drive motors to ignore inputs under a certain number
    drivetrain.drivetrain.setDeadband(0.08);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    // Set driving values to joystick values multiplied by drivetrain power 
    speedControl = X1_LJY * -DT_PowerConstant; 
    rotationControl = X1_LJX * -DT_PowerConstant;
    
    // Invert value of speed control if Y button is held
    if (X1_CONTROLLER.getYButton()) {
      speedControl = -speedControl;
    }

    // Set speeds of each side of drivetrain based off driving values
    drivetrain.arcadeDrive(speedControl, rotationControl);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // Set drive motor mode to coast when command ends
    drivetrain.frontLeft.setNeutralMode(NeutralMode.Coast);
    drivetrain.rearLeft.setNeutralMode(NeutralMode.Coast);
    drivetrain.frontRight.setNeutralMode(NeutralMode.Coast);
    drivetrain.rearRight.setNeutralMode(NeutralMode.Coast);
    drivetrain.driveModeSet = false;
    // Set deadband to 0, for small, incremental vision powered movement
    drivetrain.drivetrain.setDeadband(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
