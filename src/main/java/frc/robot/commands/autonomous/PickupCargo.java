// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autonomous;

import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;


/** A PickupCargo command that uses an example subsystem. */
public class PickupCargo extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  
  Intake intake;
  public Timer intakeTimer = new Timer();
  public double time = 2.0;
  
  /**
   * Creates a new PickupCargo.
   *
   * @param intake The intake subsystem used by this command.
   */
  public PickupCargo(Intake intake, double duration) {
    this.intake = intake;
    this.time = duration;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(intake);
  }


  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    intakeTimer.reset();
    intakeTimer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    intake.intakeCargo();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    intake.stopIntake();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return intakeTimer.get() > time;
  }
}
