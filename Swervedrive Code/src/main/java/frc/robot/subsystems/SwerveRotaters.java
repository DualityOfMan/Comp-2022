// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SwerveRotaters extends SubsystemBase {
  /** These are the variables that are created for this subsytem.. */
  private TalonFX encoder1, encoder2, encoder3, encoder4;
  public final double ENCODER_PULSES_PER_ROTATION = 256;
  public final double TURN_POWER = 0.1;
  //This is the constructor where the rotater motors are created (named encoders) and are reset.
  public SwerveRotaters() {
    encoder1 = new TalonFX(ROTATOR_PORT_1);
    encoder2 = new TalonFX(ROTATOR_PORT_2);
    encoder3 = new TalonFX(ROTATOR_PORT_3);
    encoder4 = new TalonFX(ROTATOR_PORT_4);
    resetEncoders();

  }
  //This function resets the encoders when the subsytem is initialized
  public void resetEncoders(){
    encoder1.setSelectedSensorPosition(0);
    encoder2.setSelectedSensorPosition(0);
    encoder3.setSelectedSensorPosition(0);
    encoder4.setSelectedSensorPosition(0);
  }

  //This function returns the position of the encoder that is provided...
  //...to the function. 
  public double getPosition(TalonFX encoder){
    return encoder.getSelectedSensorPosition();
  }
  
  //This function is for determining which direction the wheel is pointing towards (+ being Q1&2, - being Q3&4)
  public double getError(double goal, TalonFX encoder){
    return goal - getAngle(encoder);
  }

  //This function gives the current angle that the provided encoder is pointing.
  public double getAngle(TalonFX encoder){
    double angle = (360*(getPosition(encoder)/ENCODER_PULSES_PER_ROTATION))%360;
    return angle;
  }

  // This function provides the goal angle that is trying to be reached by the wheels.
  private double angle(double horizontal, double vertical){
    double angle = 0;
   
    angle = Math.toDegrees(Math.atan(-horizontal/vertical));

    boolean horizontalIsPositive = (horizontal>0);
    boolean verticalIsPositive = (vertical>0);

    if (!horizontalIsPositive && !verticalIsPositive){
      angle = (90-(-angle)) + 90; 
    }
    else if (horizontalIsPositive && !verticalIsPositive){
      angle = (angle + 180);
    }
    else if (horizontalIsPositive && verticalIsPositive){
      angle = 360 + angle; 
    }
    System.out.println(angle);
    return (angle);
  }
// Start:
// ANgle from the controller
// Convert the angle from the controller into a position that corr. to an angle on the motor
// CEncoder.set (ControlMode.Position, pass position in )
  public void pid(double goal, TalonFX encoder){
    double error = getError(goal, encoder);
    double percent = error*P_CONSTANT;
    encoder.set(ControlMode.Position, percent/1.0);
    // System.out.println("percent" + percent);
    // System.out.println(" motor "+ error);
  }

  public void rotateMotors(double horizontal, double vertical){
    vertical *= -1;
    double goal = angle(horizontal, vertical);

    if (Math.sqrt((Math.pow(vertical, 2) + Math.pow(horizontal, 2))) >= CONTROLLER_SENSITIVITY){
      pid(goal, encoder1);
      pid(goal, encoder2);
      pid(goal, encoder3);
      pid(goal, encoder4);

    }
    else{
      encoder1.set(ControlMode.PercentOutput, 0);
      encoder2.set(ControlMode.PercentOutput, 0);
      encoder3.set(ControlMode.PercentOutput, 0);
      encoder4.set(ControlMode.PercentOutput, 0);
    }
  }
  
  //These 4 functions provide the angles that the 4 encoders within the subsytem are pointing at.
  public double getCurrentAngleE1(){
    return getAngle(encoder1);
  }
  public double getCurrentAngleE2(){
    return getAngle(encoder2);
  }
  public double getCurrentAngleE3(){
    return getAngle(encoder3);
  }
  public double getCurrentAngleE4(){
    return getAngle(encoder4);
  }

  //This returns the values obtained from the 4 functions above as one array. 
  public double [] getCurrentAngles(){
    return new double [] {getCurrentAngleE1(), getCurrentAngleE2(), getCurrentAngleE3(), getCurrentAngleE4()};
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
