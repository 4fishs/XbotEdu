package competition.subsystems.drive.commands;

import javax.inject.Inject;

import xbot.common.command.BaseCommand;
import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.pose.PoseSubsystem;
import xbot.common.properties.DoubleProperty;

public class DriveToPositionCommand extends BaseCommand {

    DriveSubsystem drive;
    PoseSubsystem pose;
    double targetPosition;
    double previousPosition;
    double error;
    double speed;
    DoubleProperty pProperty;

    @Inject
    public DriveToPositionCommand(DriveSubsystem driveSubsystem, PoseSubsystem pose) {
        this.drive = driveSubsystem;
        this.pose = pose;
    }

    public void setTargetPosition(double position) {
        // This method will be called by the test, and will give you a goal distance.
        // You'll need to remember this target position and use it in your calculations.
        targetPosition = position;
    }

    public void setPreviousPosition() {
        previousPosition = pose.getPosition();
    }

    @Override
    public void initialize() {
        // If you have some one-time setup, do it here.
    }

    @Override
    public void execute() {
        // Here you'll need to figure out a technique that:
        // - Gets the robot to move to the target position
        // - Hint: use pose.getPosition() to find out where you are
        // - Gets the robot stop (or at least be moving really really slowly) at the
        // target position

        // How you do this is up to you. If you get stuck, ask a mentor or student for
        // some hints!
        this.error = targetPosition - pose.getPosition();
        this.speed = pose.getPosition() - previousPosition;
        double fattyPower = error / 2.5 - speed * 12;
        drive.tankDrive(fattyPower, fattyPower);
        previousPosition = pose.getPosition();
    }

    @Override
    public boolean isFinished() {
        if (Math.abs(error) < 0.01 && Math.abs(speed) < 0.01) {
            return true;
        }
        return false;
        // Modify this to return true once you have met your goal,
        // and you're moving fairly slowly (ideally stopped)
    }
}
