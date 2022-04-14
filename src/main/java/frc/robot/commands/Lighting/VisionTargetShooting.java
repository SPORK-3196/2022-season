// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Lighting;
import frc.robot.subsystems.Lighting;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import static frc.robot.Constants.Vision.*;


/** An example command that uses an example subsystem. */
public class VisionTargetShooting extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  
  Lighting lights;
  double value;
  Timer lightTimer = new Timer();
   
  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public VisionTargetShooting (Lighting lights, double offset) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.lights = lights;
    this.value = offset;
    addRequirements(lights);
  }


  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
    lights.redGreenOffset(Math.abs(value), 12);
    lights.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    lights.redGreenOffset(Math.abs(value), 12);
    lights.start();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    lights.FullRainbow();
  }

  @Override
  public boolean runsWhenDisabled() {
    return true;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
