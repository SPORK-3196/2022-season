// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autonomous;

import static frc.robot.GlobalVars.Shooter.AutoComputedRPM;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;


/** An AutonomousShoot command that uses a shooter subsystem & index subsystem. */
public class AutonomousShoot extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  
  Shooter shooter;
  public Timer shooterTimer = new Timer();
  public double time = 5.0;

  /**
   * Creates a new AutonomousShoot.
   *
   * @param shooter The shooter subsystem used by this command.
   * @param index The index subsystem used by this command.
   */

  public AutonomousShoot(Shooter shooter, double duration) {
    this.shooter = shooter;
    this.time = duration;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    shooter.feedForwardShoot(AutoComputedRPM);
    shooterTimer.reset();
    shooterTimer.start();
  }
    

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    shooter.feedForwardShoot(2000);

  
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.stopShooter();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return shooterTimer.get() > time;
  }
}
