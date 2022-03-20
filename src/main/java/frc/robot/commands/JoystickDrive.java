// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.subsystems.Drivetrain;
import static frc.robot.subsystems.Drivetrain.*;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.CommandBase;
import static frc.robot.Constants.XboxController.*;
import static frc.robot.Constants.Limelight.*;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import static frc.robot.Constants.Drivetrain.*;

/** An example command that uses an example subsystem. */
public class JoystickDrive extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Drivetrain drivetrain;


  double steering_adjust;
  double heading_error;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public JoystickDrive(Drivetrain subsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    drivetrain = subsystem;
    drivetrain.drivetrain.setDeadband(0.05);
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // drivetrain.drivetrain = new DifferentialDrive(drivetrain.leftSide, drivetrain.rightSide);
    Auto_PIDController.setSetpoint(0);
    Auto_PIDController.setTolerance(1.0);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    drivetrain.drivetrain.arcadeDrive(X1_LJY * -DT_PowerConstant , X1_LJX * -DT_PowerConstant);
    // drivetrain.drivetrain.arcadeDrive(X1_RJY * -DT_PowerConstant, X1_RJY * -DT_PowerConstant);

    if (Robot.X1_CONTROLLER.getAButton()) {
      RUN_LIMELIGHT_VISON = true;
      heading_error = -1 * TX;
      
      steering_adjust = drivetrain.Auto_PIDController.calculate(heading_error);
      // System.out.println(steering_adjust);
      drivetrain.leftSide.set(-steering_adjust);
      drivetrain.rightSide.set(steering_adjust);
    }
    else {
      RUN_LIMELIGHT_VISON = false;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // drivetrain.drivetrain = null;
    drivetrain.frontLeft.setNeutralMode(NeutralMode.Coast);
    drivetrain.rearLeft.setNeutralMode(NeutralMode.Coast);
    drivetrain.frontRight.setNeutralMode(NeutralMode.Coast);
    drivetrain.rearRight.setNeutralMode(NeutralMode.Coast);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
