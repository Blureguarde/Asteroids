package asteroids.participants;

import java.awt.Shape;
import java.awt.geom.*;
import asteroids.destroyers.AsteroidDestroyer;
import asteroids.game.Participant;
import asteroids.game.ParticipantCountdownTimer;

/**
 * Represents asteroids
 */
public class Bullet extends Participant implements AsteroidDestroyer {
    /** bullet shape */
    private Shape outline;
    
    private Ship ship;

    /**
     * Throws an IllegalArgumentException if size or variety is out of range.
     * 
     * Creates an asteroid of the specified variety (0 through 3) and size (0 = small, 1 = medium, 2 = large) and
     * positions it at the provided coordinates with a random rotation. Its velocity has the given speed but is in a
     * random direction.
     */
    public Bullet(double x, double y, double rotation, double speedX, double speedY, Ship ship) {
        Path2D.Double poly = new Path2D.Double();
        poly.moveTo(4,0);
        poly.lineTo(-4, -1);
        poly.lineTo(-4, 1);
        poly.closePath();
        outline = poly;
        
        setPosition(x, y);
        setRotation(rotation);
        setVelocity(8.0, rotation);
        incrementSpeed(speedX, speedY);
        
        new ParticipantCountdownTimer(this, "lifetime", 1500);
        this.ship = ship;
    }

    @Override
    protected Shape getOutline() { return outline; }

    /**
     * When this collides with an Asteroid, it expires.
     */
    @Override
    public void collidedWith (Participant p) {
        if (p instanceof Asteroid) {
            Participant.expire(this);
            ship.removeBullet();
        }
    }
    
    /**
     * Destroys bullet when sufficient time has passed.
     */
    @Override
    public void countdownComplete (Object payload) {
        if (payload.equals("lifetime")) {
            Participant.expire(this);
            ship.removeBullet();
        }
    }
}
