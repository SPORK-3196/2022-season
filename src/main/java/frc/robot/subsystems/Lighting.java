// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Lighting extends SubsystemBase {
  
  public static AddressableLED LIGHTS = new AddressableLED(8);
  public static AddressableLEDBuffer LIGHT_BUFFER;

  public static int firstPixelHue = 0;

  
  /** Creates a new SparkTest. */
  public Lighting() {
    LIGHT_BUFFER = new AddressableLEDBuffer(300);
    LIGHTS.setLength(LIGHT_BUFFER.getLength());
    LIGHTS.setData(LIGHT_BUFFER);
    // LIGHTS.start();
  }

  public void start() {
    LIGHTS.start();
  }
  
  public void FullRed() {
    for (int i = 0; i < LIGHT_BUFFER.getLength(); i++) {
      // LIGHT_BUFFER.setRGB(i, 255, 0, 0);
      LIGHT_BUFFER.setHSV(i, 0, 255, 75);
    }
    LIGHTS.setData(LIGHT_BUFFER);
    // LIGHTS.start();
  }

  public void FullYellow() {
    for (int i = 0; i < LIGHT_BUFFER.getLength(); i++) {
      // LIGHT_BUFFER.setRGB(i, 255, 255, 0);
      LIGHT_BUFFER.setHSV(i, 60, 255, 75);
    }
    LIGHTS.setData(LIGHT_BUFFER);
    // LIGHTS.start();
  }

  public void FullGreen() {
    for (int i = 0; i < LIGHT_BUFFER.getLength(); i++) {
      // LIGHT_BUFFER.setRGB(i, 0, 255, 0);
      LIGHT_BUFFER.setHSV(i, 120, 255, 75);
    }
    LIGHTS.setData(LIGHT_BUFFER);
    // LIGHTS.start();
  }

  public void FullBlue() {
    for (int i = 0; i < LIGHT_BUFFER.getLength(); i++) {
      // LIGHT_BUFFER.setRGB(i, 0, 0, 255);
      LIGHT_BUFFER.setHSV(i, 240, 255, 75);
    }
    LIGHTS.setData(LIGHT_BUFFER);
    // LIGHTS.start();
    
  }

  public void FullWhite() {
    for (int i = 0; i < LIGHT_BUFFER.getLength(); i++) {
      LIGHT_BUFFER.setRGB(i, 255, 255, 255);
    }
    LIGHTS.setData(LIGHT_BUFFER);
    // LIGHTS.start();
  }

  public void FullRainbow() {
    for (int i = 0; i < LIGHT_BUFFER.getLength(); i++) {
      final int hue = (firstPixelHue + (i * 180 / LIGHT_BUFFER.getLength())) % 180;
      LIGHT_BUFFER.setHSV(i, hue, 169, 35);
    }
    firstPixelHue += 3;

    firstPixelHue %= 180;
    LIGHTS.setData(LIGHT_BUFFER);
    // LIGHTS.start();
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
