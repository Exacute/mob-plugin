/**
 * CreeperSpawner.java
 * 
 * Created on 10:39:49
 */
package de.kniffo80.mobplugin.entities.spawners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.nukkit.IPlayer;
import cn.nukkit.block.Block;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import de.kniffo80.mobplugin.AutoSpawnTask;
import de.kniffo80.mobplugin.entities.animal.walking.Pig;
import de.kniffo80.mobplugin.entities.autospawn.AbstractEntitySpawner;
import de.kniffo80.mobplugin.entities.autospawn.SpawnResult;

/**
 * Each entity get it's own spawner class.
 * 
 * @author <a href="mailto:kniffman@googlemail.com">Michael Gertz</a>
 */
public class PigSpawner extends AbstractEntitySpawner {

    private static final Logger AI_LOG = LogManager.getLogger(PigSpawner.class);

    /**
     * @param spawnTask
     */
    public PigSpawner(AutoSpawnTask spawnTask) {
        super(spawnTask);
    }

    public SpawnResult spawn(IPlayer iPlayer, Position pos, Level level) {
        SpawnResult result = SpawnResult.OK;

        int blockId = level.getBlockIdAt((int) pos.x, (int) pos.y, (int) pos.z);
        int blockLightLevel = level.getBlockLightAt((int) pos.x, (int) pos.y, (int) pos.z);

        if (blockId != Block.GRASS) { // only spawns on gras
            result = SpawnResult.WRONG_BLOCK;
//        } else if (blockLightLevel < 9) { // uncommented because lightlevel not working now
//            result = SpawnResult.WRONG_LIGHTLEVEL;
        } else if (pos.y > 127 || pos.y < 1 || level.getBlockIdAt((int) pos.x, (int) pos.y, (int) pos.z) == Block.AIR) { // cannot spawn on AIR block
            result = SpawnResult.POSITION_MISMATCH;
        } else { // creeper is spawned
            this.spawnTask.createEntity(getEntityName(), pos.add(0, 1.9, 0));
        }

        AI_LOG.info("Spawn for {} at {},{},{} with lightlevel {} and blockId {}, result: {}", iPlayer.getName(), pos.x, pos.y, pos.z, blockLightLevel, blockId, result);

        return result;
    }
    
    /* (@Override)
     * @see cn.nukkit.entity.ai.IEntitySpawner#getEntityNetworkId()
     */
    @Override
    public int getEntityNetworkId() {
        return Pig.NETWORK_ID;
    }

    /* (@Override)
     * @see cn.nukkit.entity.ai.IEntitySpawner#getEntityName()
     */
    @Override
    public String getEntityName() {
        return "Pig";
    }

}