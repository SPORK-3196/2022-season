// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

/** Add your docs here. */

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMaxLowLevel.PeriodicFrame;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.Index.*;

public class Index extends SubsystemBase { // Made By Caputo & Oguntola
  
  public CANSparkMax indexMotor = new CANSparkMax(7, MotorType.kBrushless);


  public boolean BallInTransit = false;
  public boolean BallExiting = false;
  public boolean ballPassed = false;

  public double ballCounter = 0;
  
  private static DigitalInput sensorAlpha = new DigitalInput(0);
  private static DigitalInput sensorBeta = new DigitalInput(1);
  private static DigitalInput sensorSigma = new DigitalInput(2);

  private static DigitalInput[] sensors = new DigitalInput[3];

  
  
  
  /** Creates a new SparkTest. */
  public Index() {
    sensors[0] = sensorAlpha;
    sensors[1] = sensorBeta;
    sensors[2] = sensorSigma;
    indexMotor.setInverted(true);
    indexMotor.setPeriodicFramePeriod(PeriodicFrame.kStatus1, 30);
    indexMotor.setPeriodicFramePeriod(PeriodicFrame.kStatus2, 30);
   
  }
  
  public boolean getSensor(int sensorNumber) {
    return !sensors[sensorNumber].get();
  }

  public boolean getIntakeSensor() {
    return !sensorSigma.get();
  }

  public boolean getMidSensor() {
    return !sensorBeta.get();
  }

  public boolean getTopSensor() {
    return !sensorAlpha.get();
  } 
 
  public void runIndex() {
    indexMotor.set(0.65);
  }

  public void removeBalls() {
    indexMotor.set(-0.6);
  }

  public void feedBalls() {
    indexMotor.set(0.4);
  }

  public void stopIndex() {
    indexMotor.stopMotor();
  }

  public boolean isRunning() {
    return (Math.abs(indexMotor.get()) > 0.1);
  }

  public void startWithTwoBalls() {
    ballCounter = 2;
  }

  public void startWithOneBall() {
    ballCounter = 1;
  }

  public void startWithNoBalls() {
    ballCounter = 0;
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    BallCounter_Entry.setDouble(ballCounter);

    if (Math.abs(indexMotor.get()) > 0.1) {
      IndexRunning = true;
    }
    else {
      IndexRunning = false;
    }

  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }


}




