package zone.nora.colourchat.launch.mixins;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.client.C01PacketChatMessage;
import org.spongepowered.asm.mixin.*;
import zone.nora.colourchat.util.Variables;

import java.lang.reflect.Field;

@Mixin(EntityPlayerSP.class)
public class MixinEntityPlayerSP {
    @Shadow
    @Final
    public NetHandlerPlayClient sendQueue;

    /**
     * Add colour to messages.
     *
     * @author Nora Cos
     */
    @Overwrite
    public void sendChatMessage(String message) {
        System.out.println("Sending message: " + message);
        if (Variables.onHypixel) {
            if (Variables.allowedChannel) {
                if (message.startsWith("/gc ") || message.startsWith("/gchat ")) message = message.split(" ", 2)[1];
                else if (!message.startsWith("/")) message = "\u00a7" + Variables.colour + message.replace('&', '\u00a7');
            }
            C01PacketChatMessage packet = new C01PacketChatMessage(message);
            if (message.length() > 100) {
                try {
                    Field field = C01PacketChatMessage.class.getDeclaredField("message");
                    field.setAccessible(true);
                    field.set(packet, message.length() > 256 ? message.substring(0, 256) : message);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    // this should never happen.
                    e.printStackTrace();
                }
            }
            System.out.println("Sending message: " + packet.getMessage());
            this.sendQueue.addToSendQueue(packet);
        } else {
            this.sendQueue.addToSendQueue(new C01PacketChatMessage(message));
        }
    }
}
