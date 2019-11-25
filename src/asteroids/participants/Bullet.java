package asteroids.participants;

import java.awt.Shape;
import java.awt.geom.*;
import asteroids.destroyers.AsteroidDestroyer;
import asteroids.game.Controller;
import asteroids.game.Participant;
import asteroids.game.ParticipantCountdownTimer;

/**
 * Represents asteroids
 */
public class Bullet extends Participant implements AsteroidDestroyer {
    /** The game controller */
    private Shape outline;

    /**
     * Throws an IllegalArgumentException if size or variety is out of range.
     * 
     * Creates an asteroid of the specified variety (0 through 3) and size (0 = small, 1 = medium, 2 = large) and
     * positions it at the provided coordinates with a random rotation. Its velocity has the given speed but is in a
     * random direction.
     */
    public Bullet (double x, double y, double direction, double speed) {
        Path2D.Double poly = new Path2D.Double();
        poly.moveTo(3,0);
        poly.moveTo(-3, 0);
        outline = poly;
        
        setPosition(x, y);
        setRotation(direction);
        setVelocity(speed + 8, direction);
        
        new ParticipantCountdownTimer(this, "lifetime", 1500);
    }

    @Override
    protected Shape getOutline() { return outline; }

    /**
     * When this collides with an Asteroid, it expires.
     */
    @Override
    public void collidedWith (Participant p) {
        if (p instanceof Asteroid) Participant.expire(this);
    }
    
    /**
     * Destroys bullet when sufficient time has passed.
     */
    @Override
    public void countdownComplete (Object payload) {
        if (payload.equals("lifetime")) Participant.expire(this);
    }
}
