// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autonomous;

import frc.robot.subsystems.Index;
import frc.robot.subsystems.Shooter;
import static frc.robot.Constants.Shooter.*;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;


/** An example command that uses an example subsystem. */
public class AutonomousShoot extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  
  Shooter shooter;
  Index index;
  public Timer shooterTimer = new Timer();
  public double time = 5.0;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */

  public AutonomousShoot(Shooter shooter, Index index, double duration) {
    this.shooter = shooter;
    this.index = index;
    this.time = duration;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(shooter);
    addRequirements(index);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    shooter.setSetpoint(AutoComputedRPM);
    shooterTimer.reset();
    shooterTimer.start();
  }
    

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    shooter.setSetpoint(AutoComputedRPM);
    // shooter.setSetpoint(3000);

    if (shooter.atSetpoint()) {
      index.runIndex();
    }
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.stopShooter();
    System.out.println(shooterTimer.get());
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return shooterTimer.get() > time;
  }
}
