// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Index;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Index;


/** A DelayedIndex command that uses an index  subsystem. */
public class DelayedIndex extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  
  Index index;
  boolean runIndex = false;
 

  /**
   * Creates a new DelayedIndex.
   *
   * @param index The index subsystem used by this command.
   */
  public DelayedIndex(Index index) {
    this.index = index;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(index);
  }


  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // When cargo is detected at the intake, the index is run and the cargo is mid-transit
    if (index.getIntakeSensor()) {
      runIndex = true;
      index.CargoInTransit = true;
    }

    // When cargo is detected at the middle sensor, it has passed a certain point
    if (index.getMidSensor()) {
      index.CargoPassed = true;
    }


    /* 
    When cargo is no longer detected at the mid sensor, and has already passed the point,
    the index stops running, and adds to the cargo counter
    */
    if (!index.getMidSensor() && index.CargoPassed) {
      runIndex = false;
      index.CargoPassed = false;
      index.CargoInTransit = false;
      index.CargoCounter++;
    }

    // When cargo is detected at the top, and was in transit, the cargo counter incerements by one
    if (index.getTopSensor() && index.CargoInTransit) {
      index.CargoCounter++;
    }

    // When cargo is detected at the top, the index stops running, and the cargo is known to be exiting soon
    if (index.getTopSensor()) {
      runIndex = false;
      index.CargoInTransit = false;
      index.CargoExiting = true;
    }

    // When cargo is no longer detected and the cargo was exiting, the cargo counter decreases by 1
    if (!index.getTopSensor() && index.CargoExiting) {
      index.CargoCounter--;
      index.CargoExiting = false;
    }

    // When cargo is in transit, tell stop the index
    if (index.CargoInTransit) {
      runIndex = true;
    }

    // When the index should run, it will, and when it shouldn't, it won't
    if (runIndex) {
      index.runIndex();
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
