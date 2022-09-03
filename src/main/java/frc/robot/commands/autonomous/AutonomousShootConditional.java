// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autonomous;

import static frc.robot.GlobalVars.Shooter.AutoComputedRPM;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;


/** An AutonomousShootConditional command that uses a shooter subsystem. */
public class AutonomousShootConditional extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  
  Shooter shooter;
  public Timer shooterTimer = new Timer();
  public double time = 5.0;
  public double CargoCondition = 0;
  public double RPM = 2000;
  /**
   * Creates a new AutonomousShootConditional.
   *
   * @param shooter The shooter subsystem used by this command.
   */

  public AutonomousShootConditional(Shooter shooter, double CargoCondition, double RPM) {
    this.shooter = shooter;
    this.CargoCondition = CargoCondition;
    this.RPM = RPM;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(shooter);
    // addRequirements(index);
  }

  public AutonomousShootConditional(Shooter shooter, double CargoCondition) {
    this.shooter = shooter;
    this.CargoCondition = CargoCondition;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(shooter);
    // addRequirements(index);
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
    // shooter.setSetpoint(AutoComputedRPM);
    shooter.feedForwardShoot(RPM);

    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.stopShooter();
    // System.out.println(shooterTimer.get());
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // return shooterTimer.get() > time;
    return shooterTimer.get() > 3.5;
  }
}
