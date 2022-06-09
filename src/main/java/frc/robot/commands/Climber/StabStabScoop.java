// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Climber;

import frc.robot.subsystems.Shooter;
import frc.robot.commands.Climber.LowerArms;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

/** An example command that uses an example subsystem. */
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
