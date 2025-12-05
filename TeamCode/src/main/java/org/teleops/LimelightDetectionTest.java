package org.teleops;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.subsystems.LimelightSubsystem;

@TeleOp(name = "Limelight Detection Test", group = "Test")
public class LimelightDetectionTest extends LinearOpMode {
    
    private LimelightSubsystem limelight;
    
    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize
        telemetry.addData("Status", "Initializing...");
        telemetry.update();
        
        limelight = new LimelightSubsystem(hardwareMap, telemetry);
        limelight.init();
        
        telemetry.addData("Status", "Ready - Press START");
        telemetry.update();
        
        // Detection during INIT phase
        int initTag = -1;
        
        while (!isStarted() && !isStopRequested()) {
            limelight.update();
            telemetry.clear();
            
            telemetry.addData("Phase", "INIT");
            
            if (limelight.hasTarget()) {
                initTag = limelight.getAprilTagID();
                telemetry.addData("Detected Tag", initTag);
            } else {
                telemetry.addData("Status", "No tag detected");
            }
            
            telemetry.update();
            sleep(50);
        }
        
        waitForStart();
        
        // Detection during match
        while (opModeIsActive()) {
            limelight.update();
            telemetry.clear();
            
            telemetry.addData("Phase", "RUNNING");
            
            if (initTag != -1) {
                telemetry.addData("Init Tag", initTag);
            }
            
            limelight.displayTelemetry();
            
            telemetry.update();
            sleep(50);
        }
        
        limelight.stop();
    }
}

