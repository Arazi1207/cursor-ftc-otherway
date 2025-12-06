package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Telemetry;

import dev.nextftc.hardware.MotorEx;

public class shoulder {
    public static final String SHOULDER_MOTOR_NAME = "shoulderMotor";
    public static final int MIN_TICKS = 0;
    public static final int MAX_TICKS = 3000;

    private final MotorEx motor;
    private int targetTicks = 0;

    public shoulder() {
        motor = new MotorEx(SHOULDER_MOTOR_NAME);
        motor.zeroed();
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void setTargetTicks(int ticks) {
        targetTicks = Math.max(MIN_TICKS, Math.min(MAX_TICKS, ticks));
        motor.setTargetPosition(targetTicks);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(0.5);
    }

    public int getCurrentTicks() {
        return motor.getCurrentPosition();
    }

    public int getTargetTicks() {
        return targetTicks;
    }

    public void telemetryLoop(Telemetry telemetry) {
        telemetry.addData("shoulder real height (ticks)", getCurrentTicks());
        telemetry.addData("shoulder target height (ticks)", targetTicks);
    }
}
