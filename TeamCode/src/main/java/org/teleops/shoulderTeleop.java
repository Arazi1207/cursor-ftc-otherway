package org.teleops;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import dev.nextftc.ftc.NextFTCOpMode;

import org.firstinspires.ftc.teamcode.subsystems.shoulder;

@TeleOp(name = "shoulder teleop")
public class shoulderTeleop extends NextFTCOpMode {

    private shoulder shoulderSubsystem;

    private static final int LOW_TICKS = 0;
    private static final int MID_TICKS = 1000;
    private static final int HIGH_TICKS = 2000;

    @Override
    public void onInit() {
        shoulderSubsystem = new shoulder();
    }

    @Override
    public void onUpdate() {
        if (gamepad1.dpad_down) {
            shoulderSubsystem.setTargetTicks(LOW_TICKS);
        } else if (gamepad1.dpad_left) {
            shoulderSubsystem.setTargetTicks(MID_TICKS);
        } else if (gamepad1.dpad_up) {
            shoulderSubsystem.setTargetTicks(HIGH_TICKS);
        }

        shoulderSubsystem.telemetryLoop(telemetry);
        telemetry.update();
    }
}

