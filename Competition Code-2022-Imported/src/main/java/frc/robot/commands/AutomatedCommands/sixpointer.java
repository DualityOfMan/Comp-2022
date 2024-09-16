// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.AutomatedCommands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.ReleaseCatapultCommand;
import frc.robot.subsystems.*;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class sixpointer extends SequentialCommandGroup {
  /** Creates a new autonomous command sequence. */
  /*
  move, shoot, move, intake, move shoot, */
  public Gyro gyroAuto;

  public sixpointer(
      SwerveRotaters rotators,
      SwerveSpinners spinners,
      Gyro gyro,
      Catapult catapult,
      Intake intake) {
    // This is the sequential commands within our autonomous sequence
    gyroAuto = gyro;
    rotators.resetEncoders();
    addCommands(
        new SequentialCommandGroup(
            new WaitCommand(3.0),
            new IntakeAuto(intake, true),
            new MoveForward(rotators, spinners, 0.6, 1, 0.4),
            new WaitCommand(0.5),
            new ReleaseCatapultCommand(catapult),
            new WaitCommand(0.5),
            new MoveForward(rotators, spinners, 0.3, 1, 0.4),
            new Rotate(rotators, spinners, gyro, 120),
            new MoveForward(rotators, spinners, 0.4, 1, 0.2),
            new SetRotatorsfortELEOP(rotators),
            new MoveForward(rotators, spinners, 1.0, 1, 0.7),
            new GyroReset(gyro)));
  }
}

// move forward, shoot, rotate 15 degrees anti conclic, move direction with 5
