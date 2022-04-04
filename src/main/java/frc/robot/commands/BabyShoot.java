// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.Shooter;
import static frc.robot.Constants.Vision.*;
import edu.wpi.first.wpilibj2.command.CommandBase;



/** An example command that uses an example subsystem. */
public class BabyShoot extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  
  Shooter shooter;
  double avg;

  /**
   * Creates a new AutoShoot.
   *
   * @param subsystem The subsystem used by this command.
   */

  public BabyShoot(Shooter Subsystem) {
    this.shooter = Subsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    shooter.setSetpoint(600);
  }
    

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    shooter.setSetpoint(500);
    // shooter.setSetpoint(TeleComputedRPM);
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
