package zone.nora.colourchat.util

import net.minecraft.client.Minecraft
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.event.ClickEvent
import net.minecraft.util.ChatComponentText
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.entity.EntityJoinWorldEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class UpdateNotifier(latestVersion: String) {
  val mc = Minecraft.getMinecraft

  @SubscribeEvent
  def onLogin(e: EntityJoinWorldEvent): Unit = if (!mc.isSingleplayer && e.entity.isInstanceOf[EntityPlayer]) {
    if (e.entity.getName == mc.thePlayer.getName) {
      breakline()
      put(s"An update is available for ColourChat ($latestVersion)!")
      put("You can Download it here:")
      Minecraft.getMinecraft.thePlayer.addChatMessage(
        ChatComponentBuilder.of("\u00a76>> \u00a79Click Here \u00a76<<")
          .setHoverEvent("\u00a79Click!")
          .setClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/mew/ColourChat/releases")
          .build()
      )
      breakline()
      MinecraftForge.EVENT_BUS.unregister(this)
    }
  }

  private def put(str: String): Unit = mc.thePlayer.addChatMessage(new ChatComponentText(str))

  private def breakline(): Unit = {
    val dashes = new StringBuilder
    val dash = Math.floor((280 * mc.gameSettings.chatWidth + 40) / 320 * (1 / mc.gameSettings.chatScale) * 53).toInt
    for (_ <- 1 to dash) dashes.append("-")
    mc.thePlayer.addChatMessage(new ChatComponentText(s"\u00a79\u00a7m$dashes"))
  }
}
