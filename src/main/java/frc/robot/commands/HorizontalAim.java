/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.CommandBase;
import static frc.robot.Constants.Limelight.*;

import frc.robot.subsystems.Drivetrain;



public class HorizontalAim extends CommandBase {

  private final Drivetrain drivetrain;

  public static PIDController Auto_PIDController = new PIDController(0.007, 0, 0);

  double leftInput;
  double rightInput;
  double steering_adjust;

  /**
   * Creates a new DriveWithJoystick.
   */
  public HorizontalAim(Drivetrain drivetrain) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.drivetrain = drivetrain;
    addRequirements(drivetrain);
  }


  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Auto_PIDController.setSetpoint(0);
    Auto_PIDController.setTolerance(5.0);
    RUN_LIMELIGHT_VISON = true;
    drivetrain.drivetrain =  new DifferentialDrive(drivetrain.leftSide, drivetrain.rightSide);
    drivetrain.frontRight.setNeutralMode(NeutralMode.Coast);
    drivetrain.rearRight.setNeutralMode(NeutralMode.Coast);
    drivetrain.frontLeft.setNeutralMode(NeutralMode.Coast);
    drivetrain.rearLeft.setNeutralMode(NeutralMode.Coast);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // double distance_error = -1 * ty;

    steering_adjust = Auto_PIDController.calculate(-1 * TX);
    // System.out.println(steering_adjust);
    leftInput += steering_adjust;
    rightInput -= steering_adjust;

    drivetrain.drivetrain.arcadeDrive(0, steering_adjust);
    
  }


  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    RUN_LIMELIGHT_VISON = false;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // return autoTimer.get() >= time;
    return false;
  }
}
