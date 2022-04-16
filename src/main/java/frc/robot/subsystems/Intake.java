// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMaxLowLevel.PeriodicFrame;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.Status.*;

public class Intake extends SubsystemBase {
  
  private CANSparkMax intakeMotor = new CANSparkMax(8, MotorType.kBrushless);


  
  /** Creates a new SparkTest. */
  public Intake() {
    intakeMotor.setPeriodicFramePeriod(PeriodicFrame.kStatus1, 30);
    intakeMotor.setPeriodicFramePeriod(PeriodicFrame.kStatus2, 30);
  }
  
  public void intakeBalls() {
    intakeMotor.set(-0.5);
  }

  public void outtakeBalls() {
    intakeMotor.set(0.5);
  }


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
