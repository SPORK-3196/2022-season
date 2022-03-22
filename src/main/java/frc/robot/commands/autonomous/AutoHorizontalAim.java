/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.autonomous;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import static frc.robot.Constants.Vision.*;
import static frc.robot.subsystems.Drivetrain.*;

import frc.robot.subsystems.Drivetrain;



public class AutoHorizontalAim extends CommandBase {

  private final Drivetrain drivetrain;


  public Timer autoTimer = new Timer();
  public double time = 5.0;

  

  double leftInput;
  double rightInput;
  double steering_adjust;

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
    Auto_PIDController.setSetpoint(0);
    Auto_PIDController.setTolerance(1.0);
    RUN_VISION = true;
    drivetrain.drivetrain.setDeadband(0);
    drivetrain.frontRight.setNeutralMode(NeutralMode.Coast);
    drivetrain.rearRight.setNeutralMode(NeutralMode.Coast);
    drivetrain.frontLeft.setNeutralMode(NeutralMode.Coast);
    drivetrain.rearLeft.setNeutralMode(NeutralMode.Coast);
    autoTimer.reset();
    autoTimer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // boolean isTargetVisible = false;
    // boolean targetNotVisible = true;

    // double aimControlConstant = -0.07;
    // double distanceControlConstant = -0.1;
    // double min_aim_command = 0.03;
    
    double heading_error = -1 * primaryYaw;
    // double distance_error = -1 * ty;

    steering_adjust = Auto_PIDController.calculate(heading_error);
    System.out.println(steering_adjust);
    drivetrain.drivetrain.arcadeDrive(0, 0.1);


  }


  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    RUN_VISION = false;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return autoTimer.get() >= time;
    // return false;
  }
}
