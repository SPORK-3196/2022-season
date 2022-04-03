// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Lighting;
import static frc.robot.Constants.Status.*;
import static frc.robot.Constants.Shooter.*;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;


/** An example command that uses an example subsystem. */
public class RetractClimbLighting extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  
  Lighting lights;
  Timer lightTimer = new Timer();
  double lightCounter = lights.LIGHT_BUFFER.getLength();
   
  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public RetractClimbLighting (Lighting lights) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.lights = lights;
    addRequirements(lights);
  }


  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    lightTimer.reset();
    lightTimer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    

    
    for (int i = 0; i < lights.LIGHT_BUFFER.getLength() - lightCounter; i++) {
      lights.LIGHT_BUFFER.setHSV(i, 0, 255, 130);
    }

    lights.LIGHTS.setData(lights.LIGHT_BUFFER);

   

    lightCounter -= 2;

    if (lightCounter == 0) {
      lightCounter = lights.LIGHT_BUFFER.getLength();
      for (int i = 0; i < lights.LIGHT_BUFFER.getLength(); i++) {
        // LIGHT_BUFFER.setRGB(i, 255, 0, 0);
        lights.LIGHT_BUFFER.setHSV(i, 0, 0, 0);
      }
      lights.LIGHTS.setData(lights.LIGHT_BUFFER);
    }

    lights.start();
  
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // lights.FullRed();
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
