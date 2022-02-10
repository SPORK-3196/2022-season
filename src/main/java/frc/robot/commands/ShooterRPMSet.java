// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.DigitalInput;

/** An example command that uses an example subsystem. */
public class ShooterRPMSet extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Shooter shooter;
  int targetRPM;

  /**
   * Creates a new ShooterValueRun.
   *
   * @param subsystem The subsystem used by this command.
   */
  public ShooterRPMSet(Shooter subsystem, int targetRPM) {
    shooter = subsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(shooter);
    this.targetRPM = targetRPM;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    shooter.shooterController.setP(5e-5);
    shooter.shooterController.setI(1e-6);
    shooter.shooterController.setD(0.0); 
    shooter.shooterController.setSetpoint(targetRPM);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    shooter.run(shooter.shooterController.calculate(-1 * shooter.shooterVelocity));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
