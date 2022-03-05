// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.Climber;

public class ExtendClimber extends ParallelCommandGroup {
    /**
     * Creates a new ExtendClimber.
     */
    public ExtendClimber(Climber climber, double value) {
        super(
            new ExtendClimberLeft(climber, value),
            new ExtendClimberRight(climber, value)
        );
    }
  }