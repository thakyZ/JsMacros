package xyz.wagyourtail.jsmacros.api.events;

import net.minecraft.client.network.PlayerListEntry;
import xyz.wagyourtail.jsmacros.api.helpers.PlayerListEntryHelper;
import xyz.wagyourtail.jsmacros.api.sharedinterfaces.IEvent;

import java.util.UUID;

/**
 * @author Wagyourtail
 * @since 1.2.7
 */
public class EventPlayerLeave implements IEvent {
    public final String UUID;
    public final PlayerListEntryHelper player;
    
    public EventPlayerLeave(UUID uuid, PlayerListEntry player) {
        this.UUID = uuid.toString();
        this.player = new PlayerListEntryHelper(player);
        
        profile.triggerMacro(this);
    }
    
    public String toString() {
        return String.format("%s:{\"player\": %s}", this.getEventName(), player.toString());
    }
}