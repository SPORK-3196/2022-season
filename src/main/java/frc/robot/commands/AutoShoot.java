// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.Shooter;
import static frc.robot.Constants.Vision.*;
import static frc.robot.Constants.Shooter.*;
import edu.wpi.first.wpilibj2.command.CommandBase;



/** An example command that uses an example subsystem. */
public class AutoShoot extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  
  Shooter shooter;
  double avg;
  double curveOffsetRPM;

  /**
   * Creates a new AutoShoot.
   *
   * @param subsystem The subsystem used by this command.
   */

  public AutoShoot(Shooter Subsystem) {
    this.shooter = Subsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
    RUN_VISION = true;
    /*
    for (int i = 0; i < 50; i++) {
      primaryCameraResult = primaryCamera.getLatestResult();
      primaryHasTargets = primaryCameraResult.hasTargets();
      if (primaryCameraResult.hasTargets()) {
        primaryTrackedTarget = primaryCameraResult.getBestTarget();
        primaryYaw = primaryTrackedTarget.getYaw();
        // System.out.println(primaryYaw);
        primaryPitch = primaryTrackedTarget.getPitch();
        primaryPitchRadians = Units.degreesToRadians(primaryPitch);
      }
      DISTANCE_FROM_TARGET = PhotonUtils.calculateDistanceToTargetMeters(CAMERA_HEIGHT_M, TestHub, CAMERA_ANGLE_RADIANS, primaryPitchRadians);
      
      avg += DISTANCE_FROM_TARGET;
     ln
    }
    avg = avg/50;
    System.out.println("Average: " + avg);
    */
    // AutoComputedRPM = (1372) * (Math.pow(Math.E, (0.118 * (DISTANCE_FROM_TARGET))));
    // AI_DISTANCE_ENTRY.setDouble(DISTANCE_FROM_TARGET);
    // shooter.setSetpoint(1000);
    shooter.feedForwardShoot(AutoComputedRPM);
  }
    

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (primaryHasTargets) {
      if (primaryYaw < 0) {
        curveOffsetRPM = -3 * primaryYaw;
        shooter.setRightSetpoint(AutoComputedRPM + curveOffsetRPM);
        shooter.setLeftSetpoint(AutoComputedRPM);
      }
      else if (primaryYaw > 0) {
        curveOffsetRPM = 3 * primaryYaw;
        shooter.setRightSetpoint(AutoComputedRPM);
        shooter.setLeftSetpoint(AutoComputedRPM + curveOffsetRPM);
      }
      
      // shooter.setSetpoint(AutoComputedRPM);
    }
    else {
      shooter.setSetpoint(2000);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.stopShooter();
    RUN_VISION = false;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
