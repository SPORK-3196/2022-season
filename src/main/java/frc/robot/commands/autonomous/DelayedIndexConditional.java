// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autonomous;

import frc.robot.subsystems.Index;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import static frc.robot.Constants.Status.*;
import static frc.robot.Constants.Index.*;


/** An example command that uses an example subsystem. */
public class DelayedIndexConditional extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  
  Index index;
  boolean runIndex = false;
  double ballCondition = 0;
 
  public Timer indexTimer = new Timer();

  /**
   * Creates a new DelayedIndexConditional.
   *
   * @param index The index subsystem used by this command.
   * @param ballCondition The number of balls detected before this command stops.
   */
  public DelayedIndexConditional(Index index, double ballCondition) {
    this.index = index;
    this.ballCondition = ballCondition;
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
      index.BallInTransit = true;
      indexTimer.reset();
      indexTimer.start();
    }

    if (index.getMidSensor()) {
      index.ballPassed = true;
    }


    if (!index.getMidSensor() && index.ballPassed) {
      runIndex = false;
      index.ballPassed = false;
      index.BallInTransit = false;
      index.ballCounter++;
    }

    if (index.getTopSensor() && index.BallInTransit) {
      index.ballCounter++;
    }

    if (index.getTopSensor()) {
      runIndex = false;
      index.BallInTransit = false;
      index.BallExiting = true;
    }

  
    if (!index.getTopSensor() && index.BallExiting) {
      index.ballCounter--;
      index.BallExiting = false;
    }

    if (index.BallInTransit) {
      runIndex = true;
    }

    if (runIndex) {
      index.runIndex();
      indexing = true;
    }
    else if (!runIndex) {
      index.stopIndex();
      indexing = false;
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
    return BallCounter_Entry.getDouble(0) == ballCondition;
  }
}
