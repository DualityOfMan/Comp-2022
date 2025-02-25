// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.AutoCommands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.ReleaseCatapultCommand;
import frc.robot.subsystems.Gyro;
import frc.robot.subsystems.SwerveRotaters;
import frc.robot.subsystems.SwerveSpinners;
import frc.robot.subsystems.*;


// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AutoCommand1 extends SequentialCommandGroup {
  /** Creates a new autonomous command sequence. */
  public Gyro gyroAuto;

  public AutoCommand1(SwerveRotaters rotators, SwerveSpinners spinners, Gyro gyro, Catapult catapult, Intake intake) {
    // This is the sequential commands within our autonomous sequence
    gyroAuto = gyro;
    addCommands(
        new MoveWithSetWheelDirectionsWithGyro(rotators, spinners, gyroAuto, 0, 150),
        new Rotate(rotators, spinners, gyro, 180),
        new MoveWithSetWheelDirectionsWithGyro(rotators, spinners, gyro, 180, 150));
        new ReleaseCatapultCommand(catapult);

  }
}
