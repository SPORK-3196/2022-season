// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autonomous;

import static frc.robot.GlobalVars.Index.CargoCounter_Entry;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Index;


/** A DelayedIndexConditional command that uses an index subsystem. */
public class DelayedIndexConditional extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  
  Index index;
  boolean runIndex = false;
  double CargoCondition = 0;
 
  public Timer indexTimer = new Timer();

  /**
   * Creates a new DelayedIndexConditional.
   *
   * @param index The index subsystem used by this command.
   * @param CargoCondition The number of cargo detected before this command stops.
   */
  public DelayedIndexConditional(Index index, double CargoCondition) {
    this.index = index;
    this.CargoCondition = CargoCondition;
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
    if (index.getIntakeSensor()) {
      runIndex = true;
      index.CargoInTransit = true;
      indexTimer.reset();
      indexTimer.start();
    }

    if (index.getMidSensor()) {
      index.CargoPassed = true;
    }


    if (!index.getMidSensor() && index.CargoPassed) {
      runIndex = false;
      index.CargoPassed = false;
      index.CargoInTransit = false;
      index.CargoCounter++;
    }

    if (index.getTopSensor() && index.CargoInTransit) {
      index.CargoCounter++;
    }

    if (index.getTopSensor()) {
      runIndex = false;
      index.CargoInTransit = false;
      index.CargoExiting = true;
    }

  
    if (!index.getTopSensor() && index.CargoExiting) {
      index.CargoCounter--;
      index.CargoExiting = false;
    }

    if (index.CargoInTransit) {
      runIndex = true;
    }

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
    return CargoCounter_Entry.getDouble(0) == CargoCondition;
  }
}
