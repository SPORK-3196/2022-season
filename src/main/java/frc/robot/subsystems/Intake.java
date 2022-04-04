// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.Status.*;
import static frc.robot.Constants.Index.*;

public class Intake extends SubsystemBase {
  
  private CANSparkMax intakeMotor = new CANSparkMax(8, MotorType.kBrushless);


  
  /** Creates a new SparkTest. */
  public Intake() {
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

    if (Math.abs(intakeMotor.get()) > 0.1) {
      indexing = true;
    }
    else {
      indexing = false;
    }
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
  


}
