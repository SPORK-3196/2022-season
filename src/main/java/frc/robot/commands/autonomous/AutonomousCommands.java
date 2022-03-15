// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autonomous;

import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

/** An example command that uses an example subsystem. */
public class AutonomousCommands extends SequentialCommandGroup {
    public AutonomousCommands(Drivetrain drivetrain, Shooter shooter, Index index, Intake intake) {
      super(
        new DriveForwardTimed(drivetrain, 2.0, -0.6),
        new AutonomousShoot(shooter, 5.0),
        new DriveToPickup(drivetrain, shooter, index, intake, 3.0, -0.6),
        new AutonomousShoot(shooter, 5.0),
        new TurnDegrees(drivetrain, 5.0)
      );
    }
}
