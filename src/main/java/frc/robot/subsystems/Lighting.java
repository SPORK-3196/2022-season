// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Lighting extends SubsystemBase {
  
  public static AddressableLED lightsAlpha = new AddressableLED(8);
  // public static AddressableLED lightsBeta = new AddressableLED(7);

  public static AddressableLEDBuffer lightBufferAlpha;
  public static AddressableLEDBuffer lightBufferBeta;

  public static int firstPixelHue = 0;

  double alphaLightCounter;
  boolean alphaLightUp = true;

  double betaLightCounter;
  boolean betaLightUp = true;

  
  /** Creates a new Lighting. */
  public Lighting() {
    lightBufferAlpha = new AddressableLEDBuffer(300);
    lightBufferBeta = new AddressableLEDBuffer(300);

    alphaLightCounter = lightBufferAlpha.getLength();
    betaLightCounter = lightBufferBeta.getLength();

    lightsAlpha.setLength(lightBufferAlpha.getLength());
    // lightsBeta.setLength(lightBufferBeta.getLength());

    lightsAlpha.setData(lightBufferAlpha);
    // lightsBeta.setData(lightBufferBeta);

  }

  public void alphaStart() {
    lightsAlpha.start();
  }

  public void betaStart() {
    // lightsBeta.start();
  }

  public void start() {
    alphaStart();
    betaStart();
  }
  
  public void redGreenOffset(double offset, double offset_factor) {
    offset = Math.abs(offset);
    int val = (int) (offset * offset_factor);

    for (int i = 0; i < lightBufferAlpha.getLength(); i++) {
      lightBufferAlpha.setRGB(i, val, 200 - val, 0);
    }

    lightsAlpha.setData(lightBufferAlpha);
    // lightsBeta.setData(lightBufferBeta);
  }

  public void alphaRed() {
    for (int i = 0; i < lightBufferAlpha.getLength(); i++) {
      lightBufferAlpha.setRGB(i, 255, 0, 0);
    }
    lightsAlpha.setData(lightBufferAlpha);
  }
  

  public void fullRed() {
    alphaRed();
  }

  public void alphaYellow() {
    for (int i = 0; i < lightBufferAlpha.getLength(); i++) {
      lightBufferAlpha.setRGB(i, 255, 255, 0);
    }
    lightsAlpha.setData(lightBufferAlpha);
  }

  public void fullYellow() {
    alphaYellow();
  }

  public void alphaGreen() {
    for (int i = 0; i < lightBufferAlpha.getLength(); i++) {
      lightBufferAlpha.setRGB(i, 0, 255, 0);
    }
    lightsAlpha.setData(lightBufferAlpha);
  }


  public void fullGreen() {
    alphaGreen();
  }

  public void alphaBlue() {
    for (int i = 0; i < lightBufferAlpha.getLength(); i++) {
      lightBufferAlpha.setRGB(i, 0, 0, 255);
    }
    lightsAlpha.setData(lightBufferAlpha);
  }

  public void fullBlue() {
    alphaBlue();
  }

  public void alphaWhite() {
    for (int i = 0; i < lightBufferAlpha.getLength(); i++) {
      lightBufferAlpha.setRGB(i, 255, 255, 255);
    }
    lightsAlpha.setData(lightBufferAlpha);
  }


  public void fullWhite() {
    alphaWhite();
  }



  public void alphaRainbow() {
    for (int i = 0; i < lightBufferAlpha.getLength(); i++) {
      final int hue = (firstPixelHue + (i * 180 / lightBufferAlpha.getLength())) % 180;
      lightBufferAlpha.setHSV(i, hue, 169, 35);
    }
    firstPixelHue += 3;

    firstPixelHue %= 180;
    lightsAlpha.setData(lightBufferAlpha);

  }

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

  public void FullRainbow() {
    alphaRainbow();
  }

  public void alphaGreenRun() {
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

  public void betaGreenRun() {
    if (betaLightUp) {
      for (int i = 0; i < lightBufferBeta.getLength() - betaLightCounter; i++) {
        lightBufferBeta.setRGB(i, 0, 255, 0);
      }

      // lightsBeta.setData(lightBufferBeta);


      if (betaLightCounter == 0) {
        betaLightCounter = lightBufferBeta.getLength();
        betaLightUp = false;
      }
  
    }

    if (!betaLightUp) {
      for (int i = 0; i < lightBufferBeta.getLength() - betaLightCounter; i++) {
        lightBufferBeta.setHSV(i, 0, 0, 255);
      }

      lightsAlpha.setData(lightBufferBeta);
  

      if (betaLightCounter == 0) {
        betaLightCounter = lightBufferBeta.getLength();
        betaLightUp = true;
      }
    }

    alphaLightCounter -= 10;
  }

  public void fullGreenRun() {
    alphaGreenRun();
    // betaGreenRun();
  }


  public void alphaRedRun() {
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

      lightsAlpha.setData(lightBufferBeta);
  

      if (alphaLightCounter == 0) {
        alphaLightCounter = lightBufferBeta.getLength();
        alphaLightUp = true;
      }
    }

    alphaLightCounter -= 10;
  }

  public void betaRedRun() {
    if (betaLightUp) {
      for (int i = 0; i < lightBufferBeta.getLength() - betaLightCounter; i++) {
        lightBufferBeta.setRGB(i, 0, 255, 0);
      }

      // lightsBeta.setData(lightBufferBeta);


      if (betaLightCounter == 0) {
        betaLightCounter = lightBufferBeta.getLength();
        betaLightUp = false;
      }
  
    }

    if (!betaLightUp) {
      for (int i = 0; i < lightBufferBeta.getLength() - betaLightCounter; i++) {
        lightBufferBeta.setHSV(i, 0, 0, 255);
      }

      lightsAlpha.setData(lightBufferBeta);
  

      if (betaLightCounter == 0) {
        betaLightCounter = lightBufferBeta.getLength();
        betaLightUp = true;
      }
    }

    alphaLightCounter -= 10;
  }

  public void fullRedRun() {
    alphaGreenRun();
    // betaGreenRun();
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
