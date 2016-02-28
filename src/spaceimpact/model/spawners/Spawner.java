package spaceimpact.model.spawners;

import java.util.Random;

import spaceimpact.model.Area;
import spaceimpact.model.Location;
import spaceimpact.model.entities.EntityType;

/**
 * Generic Spawner implementation.<br>
 * <b>spawndelay</b> Delay between each Spawn<br>
 * <b>countdown</b> Internal timer for the spawn delay<br>
 * <b>spawncount</b> Spawn Count<br>
 * <b>maxperspawn</b> Max Spawns count per Spawn
 * <b>maxspawn</b> Maximum Spawn Count<br>
 * <b>type</b> Entity Type to Spawn<br>
 * <b>area</b> Area occupied by the spawned entities<br>
 * <b>damage</b> Entities Projectiles damage<br>
 */
public abstract class Spawner implements SpawnerInterface {

    //default area for entities
    private static final Area DEFAULTAREA = new Area(0.125, 0.0972);
    //default spawn location coordinates (out of boundaries to spawn enemy in not visible area)
    private static final double XMINSPAWN = 1.8; 
    private static final double XSPAWNAREA = 0.2;
    private static final double YMINSPAWN = 0.15;
    private static final double YSPAWNAREA = 0.70;
    
	//spawn delay
    private int spawndelay;
	private int countdown;
	
	//spawn management
	private int spawncount;
	
	//entity definition
	private EntityType type;
	private Area area;

	/**
	 * Constructor for Generic Spawner.
	 * @param inittype Entity Type to Spawn 
	 * @param initspawndelay Delay between each spawn (ticks)
	 */
	public Spawner(final EntityType inittype, final int initspawndelay) {
	       this.spawndelay = initspawndelay;
	    this.area = DEFAULTAREA;
		this.type = inittype;
		this.spawncount = 0;
		this.countdown = 0;
	}
					
	@Override
	public void update() {
		this.countdown++;
		if (this.countdown >= this.spawndelay) {
			this.countdown = 0;
		}
	}
	
	@Override
	public boolean canSpawn() {
		return this.countdown == 0;
	}
	
	/**
     * Increase spawn count.
     */
    protected void incrementSpawnCount() {
        this.spawncount++;
    }
        
    /**
     * Generate Random Entity Spawn Location.
     * @return tmparea Random generated spawn location
     */
    protected Location generateRandomLocation() {
        Random rnd = new Random();
        double x = XMINSPAWN + XSPAWNAREA * rnd.nextDouble();
        double y = YMINSPAWN + YSPAWNAREA * rnd.nextDouble();
        return new Location(x, y, this.area);
    }

	/* SETTERS */
		
	@Override
	public void setSpawnDelay(final int delay) throws IllegalArgumentException {
		if (delay < 0) {
			throw new IllegalArgumentException("Spawner spawn delay cannot be set as negative.");
		}
		this.spawndelay = delay;
	}
	
	@Override
	public void setSpawnedEntityType(final EntityType newtype) throws IllegalArgumentException {
		if (newtype == null) {
			throw new IllegalArgumentException("Spawner spawn entity type cannot be set as undefined.");
		}
		this.type = newtype;
	}
	
	@Override
	public void setSpawnedEntityArea(final Area newarea) throws IllegalArgumentException {		
		if (newarea == null) {
			throw new IllegalArgumentException("Spawner spawn entity area cannot be set as undefined.");
		}
		this.area = newarea;
	}
		
	/* GETTERS */
	
	@Override
    public int getSpawnedEntitiesCount() {
        return this.spawncount;
    }	   
	   
	/**
	 * Getter method for Area of the entities generated by the spawner.
	 * @return area Area of the entities
	 */
	protected Area getArea() {
	    return this.area;
	}
	
	/**
     * Getter method for Area of the entities generated by the spawner.
     * @return area Area of the entities
     */
	protected EntityType getType() {
        return this.type;
    }
}
