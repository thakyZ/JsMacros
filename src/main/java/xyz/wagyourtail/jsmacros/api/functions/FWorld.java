package xyz.wagyourtail.jsmacros.api.functions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.gui.hud.ClientBossBar;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.ClientConnection;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.LightType;
import xyz.wagyourtail.jsmacros.jsMacros;
import xyz.wagyourtail.jsmacros.access.IBossBarHud;
import xyz.wagyourtail.jsmacros.api.Functions;
import xyz.wagyourtail.jsmacros.api.helpers.BlockDataHelper;
import xyz.wagyourtail.jsmacros.api.helpers.BlockPosHelper;
import xyz.wagyourtail.jsmacros.api.helpers.BossBarHelper;
import xyz.wagyourtail.jsmacros.api.helpers.EntityHelper;
import xyz.wagyourtail.jsmacros.api.helpers.PlayerEntityHelper;
import xyz.wagyourtail.jsmacros.api.helpers.PlayerListEntryHelper;

public class FWorld extends Functions {
    /**
     * Don't modify.
     */
    public static double serverInstantTPS = 20;
    /**
     * Don't modify.
     */
    public static double server1MAverageTPS = 20;
    /**
     * Don't modify.
     */
    public static double server5MAverageTPS = 20;
    /**
     * Don't modify.
     */
    public static double server15MAverageTPS = 20;

    public FWorld(String libName) {
        super(libName);
    }
    
    public FWorld(String libName, List<String> excludeLanguages) {
        super(libName, excludeLanguages);
    }
    
    /**
     * @return players within render distance.
     */
    public List<PlayerEntityHelper> getLoadedPlayers() {
        List<PlayerEntityHelper> players = new ArrayList<>();
        for (AbstractClientPlayerEntity p : ImmutableList.copyOf(mc.world.getPlayers())) {
            players.add(new PlayerEntityHelper(p));
        }
        return players;
    }
    
    /**
     * @return players on the tablist.
     */
    public List<PlayerListEntryHelper> getPlayers() {
        List<PlayerListEntryHelper> players = new ArrayList<>();
        for (PlayerListEntry p : ImmutableList.copyOf(mc.getNetworkHandler().getPlayerList())) {
            players.add(new PlayerListEntryHelper(p));
        }
        return players;
    }
    
    /**
     * 
     * @param x
     * @param y
     * @param z
     * @return The block at that position.
     */
    public BlockDataHelper getBlock(int x, int y, int z) {
        BlockState b = mc.world.getBlockState(new BlockPos(x,y,z));
        BlockEntity t = mc.world.getBlockEntity(new BlockPos(x,y,z));
        if (b.getBlock().equals(Blocks.VOID_AIR)) return null;
        return new BlockDataHelper(b, t, new BlockPos(x,y,z));
        
    }
    
    /**
     * @return all entities in the render distance.
     */
    public List<EntityHelper> getEntities() {
        List<EntityHelper> entities = new ArrayList<>();
        for (Entity e : ImmutableList.copyOf(mc.world.getEntities())) {
            if (e.getType() == EntityType.PLAYER) {
                entities.add(new PlayerEntityHelper((PlayerEntity)e));
            } else {
                entities.add(new EntityHelper(e));
            }
        }
        return entities;
    }
    
    /**
     * @return the current dimension.
     */
    public String getDimension() {
        return mc.world.getRegistryKey().getValue().toString();
    }
    
    /**
     * @return the current biome.
     */
    public String getBiome() {
        return mc.world.getRegistryManager().get(Registry.BIOME_KEY).getId(mc.world.getBiome(mc.player.getBlockPos())).toString();
    }
    
    /**
     * @return the current world time.
     */
    public long getTime() {
        return mc.world.getTime();
    }
    
    /**
     * This is supposed to be time of day, but it appears to be the same as {@link FWorld#getTime()} to me...
     * 
     * @return the current world time of day.
     */
    public long getTimeOfDay() {
        return mc.world.getTimeOfDay();
    }
    
    /**
     * @return respawn position.
     */
    public BlockPosHelper getRespawnPos() {
        if (mc.world.getDimension().isNatural()) return new BlockPosHelper( mc.world.getSpawnPos());
        return null;
    }
    
    /**
     * @return world difficulty as an {@link java.lang.Integer Integer}.
     */
    public int getDifficulty() {
        return mc.world.getDifficulty().getId();
    }
    
    /**
     * @return moon phase as an {@link java.lang.Integer Integer}.
     */    
    public int getMoonPhase() {
        return mc.world.getMoonPhase();
    }
    
    /**
     * @param x
     * @param y
     * @param z
     * @return sky light as an {@link java.lang.Integer Integer}.
     */
    public int getSkyLight(int x, int y, int z) {
        return mc.world.getLightLevel(LightType.SKY, new BlockPos(x, y, z));
    }
    
    /**
     * @param x
     * @param y
     * @param z
     * @return block light as an {@link java.lang.Integer Integer}.
     */
    public int getBlockLight(int x, int y, int z) {
        return mc.world.getLightLevel(LightType.BLOCK, new BlockPos(x, y, z));
    }
    
    /**
     * plays a sound file using javax's sound stuff.
     * 
     * @param file
     * @param volume
     * @return
     * @throws LineUnavailableException
     * @throws IOException
     * @throws UnsupportedAudioFileException
     */
    public Clip playSoundFile(String file, double volume) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        Clip clip = AudioSystem.getClip();
        clip.open(AudioSystem.getAudioInputStream(new File(jsMacros.config.macroFolder, file)));
        FloatControl gainControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
        float min = gainControl.getMinimum();
        float range = gainControl.getMaximum() - min;
        float gain = (float) ((range * volume) + min);
        gainControl.setValue(gain);
        clip.addLineListener(new LineListener() {
            @Override
            public void update(LineEvent event) {
                if(event.getType().equals(LineEvent.Type.STOP)) {
                    clip.close();
                }
            }
        });
        clip.start();
        return clip;
    }
    
    /**
     * @see FWorld#playSound(String, float, float, double, double, double)
     * 
     * @param id
     */
    public void playSound(String id) {
        playSound(id, 1F);
    }
    
    /**
     * @see FWorld#playSound(String, float, float, double, double, double)
     * 
     * @param id
     * @param volume
     */
    public void playSound(String id, float volume) {
        playSound(id, volume, 0.25F);
    }
    
    /**
     * @see FWorld#playSound(String, float, float, double, double, double)
     * 
     * @param id
     * @param volume
     * @param pitch
     */
    public void playSound(String id, float volume, float pitch) {
        mc.getSoundManager().play(PositionedSoundInstance.master(Registry.SOUND_EVENT.get(new Identifier(id)), pitch, volume));
    }
    
    /**
     * plays a minecraft sound using the internal system.
     * 
     * @param id
     * @param volume
     * @param pitch
     * @param x
     * @param y
     * @param z
     */
    public void playSound(String id, float volume, float pitch, double x, double y, double z) {
        mc.world.playSound(x, y, z, Registry.SOUND_EVENT.get(new Identifier(id)), SoundCategory.MASTER, volume, pitch, true);
    }
    
    /**
     * @return a map of boss bars by the boss bar's UUID.
     */
    public Map<String, BossBarHelper> getBossBars() {
        Map<UUID, ClientBossBar> bars = ImmutableMap.copyOf(((IBossBarHud) mc.inGameHud.getBossBarHud()).jsmacros_GetBossBars());
        Map<String, BossBarHelper> out = new HashMap<>();
        for (Map.Entry<UUID, ClientBossBar> e : ImmutableList.copyOf(bars.entrySet())) {
            out.put(e.getKey().toString(), new BossBarHelper(e.getValue()));
        }
        return out;
    }
    
    /**
     * Check whether a chunk is within the render distance and loaded.
     * 
     * @param chunkX
     * @param chunkZ
     * @return
     */
    public boolean isChunkLoaded(int chunkX, int chunkZ) {
        if (mc.world == null) return false;
        return mc.world.getChunkManager().isChunkLoaded(chunkX, chunkZ);
    }
    
    /**
     * @return the current server address as a string ({@code server.address/server.ip:port}).
     */
    public String getCurrentServerAddress() {
        ClientPlayNetworkHandler h = mc.getNetworkHandler();
        if (h == null) return null;
        ClientConnection c = h.getConnection();
        if (c == null) return null;
        return c.getAddress().toString();
    }
    
    /**
     * 
     * @param x
     * @param z
     * @return biome at specified location, only works if the block/chunk is loaded.
     */
    public String getBiomeAt(int x, int z) {
        return mc.world.getRegistryManager().get(Registry.BIOME_KEY).getId(mc.world.getBiome(new BlockPos(x, 10, z))).toString();
    }
    
    /**
     * @return best attempt to measure and give the server tps with various timings.
     */
    public String getServerTPS() {
        return String.format("%.2f, 1M: %.1f, 5M: %.1f, 15M: %.1f", serverInstantTPS, server1MAverageTPS, server5MAverageTPS, server15MAverageTPS);
    }
    
    /**
     * @return best attempt to measure and give the server tps.
     */
    public double getServerInstantTPS() {
        return serverInstantTPS;
    }
    

    /**
     * @return best attempt to measure and give the server tps over the previous 1 minute average.
     */
    public double getServer1MAverageTPS() {
        return server1MAverageTPS;
    }
    

    /**
     * @return best attempt to measure and give the server tps over the previous 5 minute average.
     */
    public double getServer5MAverageTPS() {
        return server5MAverageTPS;
    }
    

    /**
     * @return best attempt to measure and give the server tps over the previous 15 minute average.
     */
    public double getServer15MAverageTPS() {
        return server15MAverageTPS;
    }
}