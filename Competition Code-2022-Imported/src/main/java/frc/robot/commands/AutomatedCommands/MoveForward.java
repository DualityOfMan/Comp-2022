// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.AutomatedCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.*;
import static frc.robot.Constants.*;

public class MoveForward extends CommandBase {

  SwerveRotaters rotators;
  SwerveSpinners spinners;
  private double moveTime;
  private double direction;
  private double power;
  private double startTime = 0;

  // This command sets the wheels to a specific angle and drives the robot a certain
  // distance in that direction.
  public MoveForward(
      SwerveRotaters rotators,
      SwerveSpinners spinners,
      double moveTime,
      double direction,
      double power) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.rotators = rotators;
    this.power = power;
    this.spinners = spinners;
    this.moveTime = moveTime;
    this.direction = direction;
    addRequirements(rotators, spinners);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    startTime = System.currentTimeMillis();
    System.out.println("MoveForward Initialized");
    spinners.resetEncoders();
    rotators.setWheelDirection(0, 0, 0, 0);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    rotators.setWheelDirection(0, 0, 0, 0);
    // if (rotators.reachedPosition(0, 0, 0, 0)) { enabling this has too low tolerance thuis wont
    // move second time
    spinners.runSpinners(direction * power);
    // }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    rotators.setWheelDirection(90, 90, 90, 90);
    rotators.stop();
    spinners.stop();
    rotators.setWheelDirection(0, 0, 0, 0);
    System.out.println("Finished");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    System.out.println("uwu!" + (System.currentTimeMillis() - startTime));
    return (System.currentTimeMillis() - startTime) > 1000 * moveTime;
  }
}
