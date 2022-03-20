// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autonomous;

import frc.robot.subsystems.Shooter;
import frc.robot.commands.IndexControl;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

/** An example command that uses an example subsystem. */
public class AutonomousProtocol extends ParallelCommandGroup {
    public AutonomousProtocol(Drivetrain drivetrain, Shooter shooter, Index index, Intake intake, Climber climber) {
      super(
        new AutonomousCommands(drivetrain, shooter, index, intake, climber)
      );
    }
}
