package org.teleops;

import static dev.nextftc.bindings.Bindings.button;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import dev.nextftc.bindings.Button;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

import org.firstinspires.ftc.teamcode.subsystems.shoulder;

@TeleOp(name = "shoulder teleop")
public class shoulderTeleop extends NextFTCOpMode {

    public shoulderTeleop() {
        addComponents(
                new SubsystemComponent(shoulder.INSTANCE),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE
        );
    }

    private static final int LOW_TICKS = 0;
    private static final int MID_TICKS = 1000;
    private static final int HIGH_TICKS = 2000;

    @Override
    public void onInit() {

    }

    @Override
    public void onUpdate() {
        Button LOW = button(()-> gamepad1.a);
        LOW.whenTrue(()-> shoulder.INSTANCE.setTargetTicks(LOW_TICKS));

        Button MIDDLE = button(()-> gamepad1.a);
        MIDDLE.whenTrue(()-> shoulder.INSTANCE.setTargetTicks(MID_TICKS));

        Button HIGH = button(()-> gamepad1.a);
        HIGH.whenTrue(()-> shoulder.INSTANCE.setTargetTicks(HIGH_TICKS));

        telemetry.update();
    }
}

