package xyz.wagyourtail.jsmacros.client.mixins.access;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.network.NetworkSide;
import net.minecraft.network.NetworkState;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.PacketListener;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.wagyourtail.jsmacros.client.access.IPacketHandler;
import xyz.wagyourtail.jsmacros.client.api.helpers.PacketByteBufferHelper;

import java.util.Map;
import java.util.function.Function;

/**
 * @author Etheradon
 * @since 1.8.4
 */
@Mixin(targets = "net.minecraft.network.NetworkState$PacketHandler")
public class MixinPacketHandler<T extends PacketListener> implements IPacketHandler {

    @Shadow
    @Final
    private Object2IntMap<Class<? extends Packet<T>>> packetIds;

    @Inject(method = "register", at = @At("HEAD"))
    private <P extends Packet<T>> void onRegister(Class<P> type, Function<PacketByteBuf, P> packetFactory, CallbackInfoReturnable<?> cir) {
        PacketByteBufferHelper.BUFFER_TO_PACKET.put(type, packetFactory);
    }

    @Override
    public Object2IntMap<Class<? extends Packet<T>>> getPacketIds() {
        return packetIds;
    }
}