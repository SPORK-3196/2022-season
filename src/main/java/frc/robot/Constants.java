// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    public static final class Field {
        public static double UPPER_HUB_HEIGHT_M = Units.inchesToMeters(104); 
    }

    public static final class Robot {
        public static double SparkWheelRadiusInches = 3;
        public static double SparkWheelDiameterInches = 6;

        public static double DrivetrainWheelDiameterIn = 6;
        public static double DrivetrainWheelRadiusIn = 3;

        public static double DrivetrainTrackWidthMeters = Units.inchesToMeters(28);

        public static double countsPerRevolution = 2048;
        public static double gearRatio = (2500/288);

        public static double CAMERA_ANGLE_RADIANS = Units.degreesToRadians(35);
        public static double CAMERA_HEIGHT_M = Units.inchesToMeters(19.5);

        public static double CAMERA_TARGET_OFFSET_CLOSE_IN = 8;
        public static double CAMERA_TARGET_OFFSET_CLOSE_M = Units.inchesToMeters(CAMERA_TARGET_OFFSET_CLOSE_IN);

        
        public static double CAMERA_TARGET_OFFSET_FAR_IN = 4;
        public static double CAMERA_TARGET_OFFSET_FAR_M = Units.inchesToMeters(CAMERA_TARGET_OFFSET_FAR_IN);

        public static double TestHub = Units.inchesToMeters(103.5);
    }
    
    
    public static final class Drivetrain { 

        public static double AutoP = 0.008;
        public static DifferentialDriveKinematics driveKinematics = new DifferentialDriveKinematics(Robot.DrivetrainTrackWidthMeters);
        
    }
    
    public static final class AutoDriveConstants {
        public static double ksVolts = 0.67962;
        public static double kvVoltSecondsPerMeter = 1.8334;
        public static double kaVoltSecondsSquaredPerMeter = 0.45089;

        public static double kMaxSpeedMetersPerSeocond = 0.5;
        public static double kMaxAccelerationMetersPerSecondSquared = 1;


        public static double kP = 2.7381;
    }
    
}