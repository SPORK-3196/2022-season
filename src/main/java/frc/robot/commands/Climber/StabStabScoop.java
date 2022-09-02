// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Climber;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Climber;

/** A StabStabScoop command that uses a climber subsystem. Uses climber arms to create a noise similar to the SPORK Stab, Stab, Scoop chant. */
public class StabStabScoop extends SequentialCommandGroup {


    public StabStabScoop(Climber climber) {
      super(
        new ExtendClimber(climber, 0.6).withTimeout(0.5),
        new WaitCommand(0.7),
        new ExtendClimber(climber, 0.6).withTimeout(0.5),
        new RetractClimber(climber, 0.4).withTimeout(2)
      );
    }
}
