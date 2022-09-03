// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autonomous;

import frc.robot.subsystems.Shooter;
import frc.robot.commands.Climber.LowerArms;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

/** A OneCargoAuto command that uses the shooter, drivetrain, intake, index, and climber subsystems. */
public class OneCargoAuto extends SequentialCommandGroup {


    public OneCargoAuto(Drivetrain drivetrain, Shooter shooter, Intake intake, Index index, Climber climber) {
      super(
        new InstantCommand(index::startWithOneCargo, index),
        new LowerArms(climber),
        new DriveForwardTimed(drivetrain, 3.5, -0.15),
        new AutoHorizontalAim(drivetrain, 1),
        // new AutonomousShootUno(shooter, index, 5.0)
        new AutonomousShootConditional(shooter, 0).alongWith(new IndexShootingUpperConditional(index, 0))
        // .alongWith(new IndexShootingUpper(index))
      );
    }
}
