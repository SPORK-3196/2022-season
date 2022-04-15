// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Drivetrain;

import frc.robot.subsystems.Drivetrain;
import static frc.robot.subsystems.Drivetrain.*;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.CommandBase;
import static frc.robot.Constants.XboxController.*;
import static frc.robot.Constants.Vision.*;
import static frc.robot.Robot.*;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import static frc.robot.Constants.Drivetrain.*;



/** An example command that uses an example subsystem. */
public class TargetOrientation extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Drivetrain drivetrain;


  double steering_adjust;
  double heading_error;
  DifferentialDrive.WheelSpeeds wheelSpeeds;

  
  double speedControl;
  double rotationControl;

  /**
   * Creates a new JoystickDrive.
   *
   * @param subsystem The drivetrain used by this command.
   */
  public TargetOrientation(Drivetrain subsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    drivetrain = subsystem;
    
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // drivetrain.drivetrain = new DifferentialDrive(drivetrain.leftSide, drivetrain.rightSide);
    Auto_PIDController.setSetpoint(drivetrain.getTargetOffset());
    drivetrain.frontLeft.setNeutralMode(NeutralMode.Coast);
    drivetrain.rearLeft.setNeutralMode(NeutralMode.Coast);
    drivetrain.frontRight.setNeutralMode(NeutralMode.Coast);
    drivetrain.rearRight.setNeutralMode(NeutralMode.Coast);
    drivetrain.driveModeSet = true;
    RUN_VISION = true;
    Auto_PIDController.setP(0.0075);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    RUN_VISION = true;

    speedControl = X1_LJY * -DT_PowerConstant; 
    rotationControl = X1_LJX * -DT_PowerConstant;

    primaryCamera.setDriverMode(false);
    
    if (primaryHasTargets) {
      steering_adjust = Auto_PIDController.calculate(primaryYaw);
      rotationControl = steering_adjust;
    }

    wheelSpeeds = DifferentialDrive.arcadeDriveIK(speedControl, rotationControl, false);
    drivetrain.leftSide.set(wheelSpeeds.left);
    drivetrain.rightSide.set(wheelSpeeds.right);
    // drivetrain.drivetrain.curvatureDrive(speedControl, rotationControl, true);
    // drivetrain.drivetrain.arcadeDrive(speedControl, rotationControl); 
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // drivetrain.drivetrain = null;
    RUN_VISION = false;
    drivetrain.frontLeft.setNeutralMode(NeutralMode.Coast);
    drivetrain.rearLeft.setNeutralMode(NeutralMode.Coast);
    drivetrain.frontRight.setNeutralMode(NeutralMode.Coast);
    drivetrain.rearRight.setNeutralMode(NeutralMode.Coast);
    drivetrain.driveModeSet = false;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
