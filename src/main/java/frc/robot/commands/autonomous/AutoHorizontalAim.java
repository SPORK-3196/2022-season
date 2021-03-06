/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.autonomous;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.CommandBase;
import static frc.robot.Constants.Vision.*;
import static frc.robot.subsystems.Drivetrain.*;
import static frc.robot.Constants.Drivetrain.*;

import frc.robot.subsystems.Drivetrain;



public class AutoHorizontalAim extends CommandBase {

  private final Drivetrain drivetrain;


  public Timer autoTimer = new Timer();
  public double time = 5.0;

  

  double leftInput;
  double rightInput;
  double steering_adjust;
  DifferentialDrive.WheelSpeeds wheelSpeeds;

  /**
   * Creates a new DriveWithJoystick.
   */
  public AutoHorizontalAim(Drivetrain drivetrain, double duration) {
    // Use addRequirements() here to declare subsystem dependencies.
    Auto_PIDController.setSetpoint(0);
    this.time = duration;
    this.drivetrain = drivetrain;
    addRequirements(drivetrain);
  }


  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // Auto_PIDController.setSetpoint(drivetrain.getTargetOffset());
    Auto_PIDController.setTolerance(0.5);
    RUN_VISION = true;
    // drivetrain.drivetrain = new DifferentialDrive(drivetrain.leftSide, drivetrain.rightSide);
    drivetrain.drivetrain.setDeadband(0);
    drivetrain.frontRight.setNeutralMode(NeutralMode.Coast);
    drivetrain.rearRight.setNeutralMode(NeutralMode.Coast);
    drivetrain.frontLeft.setNeutralMode(NeutralMode.Coast);
    drivetrain.rearLeft.setNeutralMode(NeutralMode.Coast);
    autoTimer.reset();
    autoTimer.start();
    Auto_PIDController.setP(AutoP);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (DISTANCE_FROM_TARGET > 2.5) {
      Auto_PIDController.setSetpoint(drivetrain.getFarTargetOffset());
    }
    else {
      Auto_PIDController.setSetpoint(drivetrain.getCloseTargetOffset());
    }
    // boolean isTargetVisible = false;
    // boolean targetNotVisible = true;

    // double aimControlConstant = -0.07;
    // double distanceControlConstant = -0.1;
    // double min_aim_command = 0.03;
    
    // double heading_error = -1 * primaryYaw;
    // double distance_error = -1 * ty;

    if (primaryHasTargets) {
      steering_adjust = Auto_PIDController.calculate(primaryYaw);
    }

   drivetrain.arcadeDriveAI(0, steering_adjust);
    

  }


  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    RUN_VISION = false;
    // drivetrain.drivetrain = null;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return autoTimer.get() >= time;
    // return false;
  }
}
