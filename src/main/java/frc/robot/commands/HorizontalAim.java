/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import static frc.robot.Constants.*;
import static frc.robot.Constants.Limelight.*;

import frc.robot.Robot;
import frc.robot.subsystems.Drivetrain;



public class HorizontalAim extends CommandBase {

  private final Drivetrain drivetrain;


  public Timer autoTimer = new Timer();
  public double time = 5.0;

  public static PIDController Auto_PIDController = new PIDController(0.007, 0, 0);
  

  double leftInput;
  double rightInput;
  double steering_adjust;
  double heading_error;

  /**
   * Creates a new DriveWithJoystick.
   */
  public HorizontalAim(Drivetrain drivetrain) {
    // Use addRequirements() here to declare subsystem dependencies.
    Auto_PIDController.setSetpoint(0);
    this.drivetrain = drivetrain;
    addRequirements(drivetrain);
  }


  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Auto_PIDController.setSetpoint(0);
    Auto_PIDController.setTolerance(1.0);
    RUN_LIMELIGHT_VISON = true;
    drivetrain.drivetrain.setDeadband(0);
    drivetrain.frontRight.setNeutralMode(NeutralMode.Coast);
    drivetrain.rearRight.setNeutralMode(NeutralMode.Coast);
    drivetrain.frontLeft.setNeutralMode(NeutralMode.Coast);
    drivetrain.rearLeft.setNeutralMode(NeutralMode.Coast);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    RUN_LIMELIGHT_VISON = true;
      heading_error = -1 * TX;
      steering_adjust = Auto_PIDController.calculate(heading_error);
      // System.out.println(steering_adjust);
      drivetrain.leftSide.set(-steering_adjust);
      drivetrain.rightSide.set(steering_adjust);


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
