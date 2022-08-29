// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMaxLowLevel.PeriodicFrame;

import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Intake extends SubsystemBase {
  
  // Initialize SparkMax Object
  private CANSparkMax intakeMotor = new CANSparkMax(8, MotorType.kBrushless);


  
  /** Creates a new Intake subsystem. */
  public Intake() {
    // Reduce CANFrame frequency of the intake
    intakeMotor.setPeriodicFramePeriod(PeriodicFrame.kStatus1, 30);
    intakeMotor.setPeriodicFramePeriod(PeriodicFrame.kStatus2, 30);
  }


  
  /** Set intake motor speed to intake cargo into index */
  public void intakeCargo() {
    intakeMotor.set(-0.5);
  }

  /** Set intake motor speed to remove cargo from index */
  public void outtakeCargo() {
    intakeMotor.set(0.5);
  }

  /** Stop intake motor */
  public void stopIntake() {
    intakeMotor.stopMotor();
  }




  @Override
  public void periodic() {
    // This method will be called once per scheduler run

  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
  


}
