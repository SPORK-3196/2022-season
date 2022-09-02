// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Intake;

import frc.robot.subsystems.Index;
import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.CommandBase;


/** A OuttakeCargo command that uses an intake subsystem & climber subsystem. */
public class OuttakeCargo extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  
  Intake intake;
  Index index;
  
  /**
   * Creates a new OuttakeCargo.
   *
   * @param intake The intake subsystem used by this command.
   * @param index The index subsystem used by this command.
   */
  public OuttakeCargo(Intake intake, Index index) {
    this.intake = intake;
    this.index = index;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(intake);
    addRequirements(index);
  }


  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    intake.outtakeCargo();

    // Sets CargoExiting variable to true when ball is detected leaving index
    if (index.getIntakeSensor()) {
      index.CargoExiting = true;
    }
    
    // When ball was detcted leaving, but is no longer detected, reduce cargo counter variable
    if (!index.getIntakeSensor() && index.CargoExiting) {
      index.CargoCounter--;
      index.CargoExiting = false;
    }
    
    index.removeCargo();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    intake.stopIntake();
    index.stopIndex();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
