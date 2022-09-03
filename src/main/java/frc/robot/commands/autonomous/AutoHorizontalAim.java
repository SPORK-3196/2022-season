/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.autonomous;

import static frc.robot.Constants.Drivetrain.AutoP;
import static frc.robot.GlobalVars.Vision.DISTANCE_FROM_TARGET;
import static frc.robot.GlobalVars.Vision.RUN_VISION;
import static frc.robot.GlobalVars.Vision.primaryHasTargets;
import static frc.robot.GlobalVars.Vision.primaryYaw;
import static frc.robot.subsystems.Drivetrain.Auto_PIDController;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;



public class AutoHorizontalAim extends CommandBase {

  private final Drivetrain drivetrain;


  public Timer autoTimer = new Timer();
  public double time = 5.0;


  double steering_adjust;

  /**
   * Creates a new AutoHorizontalAim.
   */
  public AutoHorizontalAim(Drivetrain drivetrain, double duration) {
    // Use addRequirements() here to declare subsystem dependencies.
    Auto_PIDController.setSetpoint(0);
    this.time = duration;
    this.drivetrain = drivetrain;
    addRequirements(drivetrain);
  }


  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Auto_PIDController.setTolerance(0.5);
    RUN_VISION = true;
    drivetrain.drivetrain.setDeadband(0);
    drivetrain.frontRight.setNeutralMode(NeutralMode.Coast);
    drivetrain.rearRight.setNeutralMode(NeutralMode.Coast);
    drivetrain.frontLeft.setNeutralMode(NeutralMode.Coast);
    drivetrain.rearLeft.setNeutralMode(NeutralMode.Coast);
    autoTimer.reset();
    autoTimer.start();
    Auto_PIDController.setP(AutoP);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (DISTANCE_FROM_TARGET > 2.5) {
      Auto_PIDController.setSetpoint(drivetrain.getFarTargetOffset());
    }
    else {
      Auto_PIDController.setSetpoint(drivetrain.getCloseTargetOffset());
    }

    // Changes target setpoint based off robot distance from vision target
    if (primaryHasTargets) {
      steering_adjust = Auto_PIDController.calculate(primaryYaw);
    }

   drivetrain.arcadeDriveAI(0, steering_adjust);
    

  }


  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    RUN_VISION = false;
    // drivetrain.drivetrain = null;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return autoTimer.get() >= time;
    // return false;
  }
}
