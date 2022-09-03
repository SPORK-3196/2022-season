// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autonomous;

import static frc.robot.GlobalVars.Vision.RUN_BACKUP_VISION;
import static frc.robot.GlobalVars.Vision.backupHasTargets;
import static frc.robot.GlobalVars.Vision.backupYaw;
import static frc.robot.subsystems.Drivetrain.Auto_PIDController;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;



/** An AutoCargoOrientation command that uses a drivetrain subsystem. */
public class AutoCargoOrientation extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Drivetrain drivetrain;
  

  double steering_adjust;
  double heading_error;
  DifferentialDrive.WheelSpeeds wheelSpeeds;

  
  double speedControl;
  double rotationControl;
  Timer lockon = new Timer();
  double duration;

  /**
   * Creates a new JoystickDrive.
   *
   * @param drivetrain The drivetrain used by this command.
   */
  public AutoCargoOrientation(Drivetrain drivetrain, double duration) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.drivetrain = drivetrain;
    this.duration = duration;
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Auto_PIDController.setSetpoint(0);
    drivetrain.frontLeft.setNeutralMode(NeutralMode.Coast);
    drivetrain.rearLeft.setNeutralMode(NeutralMode.Coast);
    drivetrain.frontRight.setNeutralMode(NeutralMode.Coast);
    drivetrain.rearRight.setNeutralMode(NeutralMode.Coast);
    drivetrain.driveModeSet = true;

    RUN_BACKUP_VISION = true;
    Auto_PIDController.setP(0.0075);
    lockon.reset();
    lockon.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    RUN_BACKUP_VISION = true;
    
    // When backup camera can detect an allied cargo target, adjust drivetrain towards
    if (backupHasTargets) {
      steering_adjust = Auto_PIDController.calculate(backupYaw);
      rotationControl = steering_adjust;
    }

  
    drivetrain.arcadeDriveAI(-0.3, rotationControl);
  
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // drivetrain.drivetrain = null;
    RUN_BACKUP_VISION = false;
    drivetrain.frontLeft.setNeutralMode(NeutralMode.Coast);
    drivetrain.rearLeft.setNeutralMode(NeutralMode.Coast);
    drivetrain.frontRight.setNeutralMode(NeutralMode.Coast);
    drivetrain.rearRight.setNeutralMode(NeutralMode.Coast);
    drivetrain.driveModeSet = false;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return lockon.get() > duration;
  }
}
