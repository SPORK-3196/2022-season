// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Shooter;

import frc.robot.subsystems.Shooter;
import static frc.robot.GlobalVars.Vision.*;
import static frc.robot.GlobalVars.Shooter.*;
import edu.wpi.first.wpilibj2.command.CommandBase;



/** An AutoShoot command that uses a shooting subsystem. */
public class AutoShoot extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  
  Shooter shooter;
  double avg;
  double curveOffsetRPM;

  /**
   * Creates a new AutoShoot.
   *
   * @param shooter The shooter subsystem used by this command.
   */

  public AutoShoot(Shooter shooter) {
    this.shooter = shooter;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
    RUN_VISION = true;
    shooter.feedForwardShoot(AutoComputedRPM);
  }
    

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    RUN_VISION = true;
    if (primaryHasTargets) {
      shooter.feedForwardShoot(AutoComputedRPM);
    }
    else {
      shooter.feedForwardShoot(2000);
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
