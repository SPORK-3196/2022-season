// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autonomous;

import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

/** An example command that uses an example subsystem. */
public class AutonomousProtocol extends SequentialCommandGroup {
    public AutonomousProtocol(Drivetrain drivetrain, Shooter shooter, Index index, Intake intake, Climber climber) {
      super(
        new DriveToPickup(drivetrain, shooter, index, climber, intake, 4.0, -0.4),
        new AutoHorizontalAim(drivetrain, 2.5),
        new AutonomousShoot(shooter, index, 7.5)
      );
    }
}
