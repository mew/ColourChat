package zone.nora.colourchat.launch.mixins;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.client.C01PacketChatMessage;
import org.spongepowered.asm.mixin.*;
import zone.nora.colourchat.util.Variables;

@Mixin(EntityPlayerSP.class)
public class MixinEntityPlayerSP {
    @Shadow
    @Final
    public NetHandlerPlayClient sendQueue;

    /**
     * @author Nora Cos
     */
    @Overwrite
    public void sendChatMessage(String message) {
        if (Variables.onHypixel) {
            if (Variables.currentChannel.equals("guild")) {
                if (message.startsWith("/gc ") || message.startsWith("/gchat ")) message = message.split(" ", 2)[0];
                message = "\u00a7d" + message.replace('&', '\u00a7');
            }
            C01PacketChatMessage packet = new C01PacketChatMessage(message);
            ((MixinC01PacketChatMessage) packet).setMessage(message.length() > 256 ? message.substring(0, 256) : message);
            this.sendQueue.addToSendQueue(packet);
        } else {
            this.sendQueue.addToSendQueue(new C01PacketChatMessage(message));
        }
    }
}
