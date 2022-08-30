// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Lighting extends SubsystemBase {
  
  // Create AddressableLED object based off DIO port on RoboRIO
  public static AddressableLED lightsAlpha = new AddressableLED(8);

  // Create AddressableLEDBuffer object to store information to set LEDs
  public static AddressableLEDBuffer lightBufferAlpha;

  public static int firstPixelHue = 0;

  double alphaLightCounter;
  boolean alphaLightUp = true;


  
  /** Creates a new Lighting subsystem. */
  public Lighting() {
    lightBufferAlpha = new AddressableLEDBuffer(300);
    
    alphaLightCounter = lightBufferAlpha.getLength();

    lightsAlpha.setLength(lightBufferAlpha.getLength());

    lightsAlpha.setData(lightBufferAlpha);

  }

  /** Start setting light buffer of lighting system */
  public void start() {
    lightsAlpha.start();
  }
  
  /** Set lights to no color */
  public void noColor() {
    for (int i = 0; i < lightBufferAlpha.getLength(); i++) {
      lightBufferAlpha.setRGB(i, 0, 0, 0);
    }
    lightsAlpha.setData(lightBufferAlpha);
  }

  /** Set lights between red, yellow, and green, based off an offset from a target. */
  public void redGreenOffset(double offset, double offset_factor) {
    offset = Math.abs(offset);
    int val = (int) (offset * offset_factor);

    for (int i = 0; i < lightBufferAlpha.getLength(); i++) {
      lightBufferAlpha.setRGB(i, val, 200 - val, 0);
    }

    lightsAlpha.setData(lightBufferAlpha);
    // lightsBeta.setData(lightBufferBeta);
  }

  /** Set lights to red */
  public void fullRed() {
    for (int i = 0; i < lightBufferAlpha.getLength(); i++) {
      lightBufferAlpha.setRGB(i, 255, 0, 0);
    }
    lightsAlpha.setData(lightBufferAlpha);
  }

  /** Set lights to yellow */
  public void fullYellow() {
    for (int i = 0; i < lightBufferAlpha.getLength(); i++) {
      lightBufferAlpha.setRGB(i, 200, 200, 0);
    }
    lightsAlpha.setData(lightBufferAlpha);
  }

  /** Set lights to green */
  public void fullGreen() {
    for (int i = 0; i < lightBufferAlpha.getLength(); i++) {
      lightBufferAlpha.setRGB(i, 0, 255, 0);
    }
    lightsAlpha.setData(lightBufferAlpha);
  }

  /** Set lights to blue */
  public void fullBlue() {
    for (int i = 0; i < lightBufferAlpha.getLength(); i++) {
      lightBufferAlpha.setRGB(i, 0, 0, 255);
    }
    lightsAlpha.setData(lightBufferAlpha);
  }

  /** Set lights to white */
  public void fullWhite() {
    for (int i = 0; i < lightBufferAlpha.getLength(); i++) {
      lightBufferAlpha.setHSV(i, 0, 0, 70);
    }
    lightsAlpha.setData(lightBufferAlpha);
  }

  /** Set lights to rainbow */
  public void FullRainbow() {
    for (int i = 0; i < lightBufferAlpha.getLength(); i++) {
      final int hue = (firstPixelHue + (i * 180 / lightBufferAlpha.getLength())) % 180;
      lightBufferAlpha.setHSV(i, hue, 169, 35);
    }
    firstPixelHue += 3;

    firstPixelHue %= 180;
    lightsAlpha.setData(lightBufferAlpha);

  }

  /** Set lights to rainbow, constantly changing */
  public void rainbowRun() {
    for (int i = 0; i < lightBufferAlpha.getLength() - alphaLightCounter; i++) {
        final int hue = (firstPixelHue + (i * 180 / lightBufferAlpha.getLength())) % 180;
        lightBufferAlpha.setHSV(i, hue, 169, 100);
    }

    lightsAlpha.setData(lightBufferAlpha);


    if (alphaLightCounter == 0) {
      alphaLightCounter = lightBufferAlpha.getLength();
    }
    

    
    firstPixelHue += 3;
    firstPixelHue %= 180;
    alphaLightCounter -= 10;
  }

  /** Set lights to a ramping green across LED strip */
  public void fullGreenRun() {
    if (alphaLightUp) {
      for (int i = 0; i < lightBufferAlpha.getLength() - alphaLightCounter; i++) {
        lightBufferAlpha.setRGB(i, 0, 255, 0);
      }

      lightsAlpha.setData(lightBufferAlpha);


      if (alphaLightCounter == 0) {
        alphaLightCounter = lightBufferAlpha.getLength();
        alphaLightUp = false;
      }
  
    }

    if (!alphaLightUp) {
      for (int i = 0; i < lightBufferAlpha.getLength() - alphaLightCounter; i++) {
        lightBufferAlpha.setHSV(i, 0, 0, 255);
      }

      lightsAlpha.setData(lightBufferAlpha);
  

      if (alphaLightCounter == 0) {
        alphaLightCounter = lightBufferAlpha.getLength();
        alphaLightUp = true;
      }
    }

    alphaLightCounter -= 10;
  }

  /** Set lights to a ramping red across LED strip */
  public void fullRedRun() {
    if (alphaLightUp) {
      for (int i = 0; i < lightBufferAlpha.getLength() - alphaLightCounter; i++) {
        lightBufferAlpha.setRGB(i, 255, 0, 0);
      }

      lightsAlpha.setData(lightBufferAlpha);


      if (alphaLightCounter == 0) {
        alphaLightCounter = lightBufferAlpha.getLength();
        alphaLightUp = false;
      }
  
    }

    if (!alphaLightUp) {
      for (int i = 0; i < lightBufferAlpha.getLength() - alphaLightCounter; i++) {
        lightBufferAlpha.setHSV(i, 0, 0, 255);
      }

      lightsAlpha.setData(lightBufferAlpha);
  

      if (alphaLightCounter == 0) {
        alphaLightCounter = lightBufferAlpha.getLength();
        alphaLightUp = true;
      }
    }

    alphaLightCounter -= 10;
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
