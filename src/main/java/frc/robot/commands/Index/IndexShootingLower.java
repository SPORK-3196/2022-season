// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Index;

import frc.robot.subsystems.Index;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import static frc.robot.GlobalVars.Shooter.*;


/** An IndexShootingLower command that uses an index subsystem. */
public class IndexShootingLower extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  
  Index index;
  boolean runIndex = true;
  public Timer indexTimer = new Timer();

  /**
   * Creates a new IndexShootingLower.
   *
   * @param index The index subsystem used by this command.
   */
  public IndexShootingLower(Index index) {
    this.index = index;
    
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(index);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // Start running index
    runIndex = true;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // When cargo is at the top sensor, stop running the index, and consider it exiting
    if (index.getTopSensor()) {
      runIndex = false;
      index.CargoExiting = true;
    }

    // When cargo is exiting and is no longer detected at the top, reduce cargo counter by 1
    if (!index.getTopSensor() && index.CargoExiting) {
      index.CargoCounter--;
      index.CargoExiting = false;
    }
   
    // If the shooter is at target speed, run the index
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
