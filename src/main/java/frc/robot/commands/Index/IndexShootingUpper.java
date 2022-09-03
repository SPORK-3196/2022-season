// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Index;

import static frc.robot.GlobalVars.Shooter.SHOOTER_READY;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Index;


/** A IndexShootingUpper command that uses an index subsystem. */
public class IndexShootingUpper extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  
  Index index;
  boolean runIndex = true;


  /**
   * Creates a new IndexShootingUpper.
   *
   * @param index The subsystem used by this command.
   */
  public IndexShootingUpper(Index index) {
    this.index = index;
    
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(index);
  }


  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // Automatically starts running the index
    runIndex = true;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // When cargo is detected exiting, the index is stopped
    if (index.getTopSensor()) {
      runIndex = false;
      index.CargoExiting = true;
    }

    // When cargo is no longer detected and was exiting before, reduce cargo count by 1
    if (!index.getTopSensor() && index.CargoExiting) {
      index.CargoCounter--;
      index.CargoExiting = false;
    }

   
    /*
    if (SHOOTER_READY && Math.abs(primaryYaw) < 6) {
      runIndex = true;
    }
    */

    // When flywheel is up to speed, run index
    if (SHOOTER_READY) {
      runIndex = true;
    }

    // If the index should run, feed cargo into flywheels, otherwise, do nothing
    if (runIndex) {
      index.feedCargo();
    }
    else if (!runIndex) {
      index.stopIndex();
    }
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    index.stopIndex();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
