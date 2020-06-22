package zone.nora.colourchat.util

import net.minecraft.client.Minecraft
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.network.FMLNetworkEvent.{ClientConnectedToServerEvent, ClientDisconnectionFromServerEvent}

class EventListener {
  @SubscribeEvent
  def onClientJoinServer(e: ClientConnectedToServerEvent): Unit =
    Variables.onHypixel =
      !Minecraft.getMinecraft.isSingleplayer && e.manager.getRemoteAddress.toString.toLowerCase.contains("hypixel.net")

  @SubscribeEvent
  def onClientLeaveServer(e: ClientDisconnectionFromServerEvent): Unit = Variables.onHypixel = false

  @SubscribeEvent
  def onChatReceived(e: ClientChatReceivedEvent): Unit = if (e.`type`.toInt != 2) {
    val msg = e.message.getUnformattedText
    if (msg.startsWith("You are now in the "))
      Variables.currentChannel =
        if (msg.toLowerCase.contains("guild")) "guild" else "other"
  }
}
