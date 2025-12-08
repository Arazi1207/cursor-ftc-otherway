package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.MotorEx;


public class shoulder implements Subsystem {
    public static final shoulder INSTANCE = new shoulder();

    private static final String MASTER_NAME = "master";
    private static final String SLAVE_NAME = "slave";

    public static final int MIN_TICKS = 0;
    public static final int MAX_TICKS = 3000;

    public static final int LOW_TICKS = 0;
    public static final int MID_TICKS = 1000;
    public static final int HIGH_TICKS = 2000;

    private static final double KP = 0.002;
    private static final double MAX_POWER = 0.6;

    private final MotorEx master;
    private final MotorEx slave;

    private int targetTicks = 0;

    private shoulder() {
        master = new MotorEx(MASTER_NAME).zeroed();
        master.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        slave = new MotorEx(SLAVE_NAME).zeroed();
        slave.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void initialize() {
        targetTicks = 0;
    }

    @Override
    public void periodic() {
        int currentTicks = master.getCurrentPosition();
        int error = targetTicks - currentTicks;

        double power = KP * error;
        if (power > MAX_POWER) power = MAX_POWER;
        if (power < -MAX_POWER) power = -MAX_POWER;

        master.setPower(power);
        slave.setPower(power);
    }

    public void setTargetTicks(int ticks) {
        if (ticks < MIN_TICKS) ticks = MIN_TICKS;
        if (ticks > MAX_TICKS) ticks = MAX_TICKS;
        targetTicks = ticks;
    }

    public int getTargetTicks() {
        return targetTicks;
    }

    public int getCurrentTicks() {
        return master.getCurrentPosition();
    }

    public void telemetryLoop(Telemetry telemetry) {
        telemetry.addData("shoulder target (ticks)", targetTicks);
        telemetry.addData("shoulder current (ticks)", getCurrentTicks());
        telemetry.addData("shoulder error (ticks)", targetTicks - getCurrentTicks());
    }
}
