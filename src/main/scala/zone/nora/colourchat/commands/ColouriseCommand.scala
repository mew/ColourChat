package zone.nora.colourchat.commands

import net.minecraft.client.Minecraft
import net.minecraft.command.{CommandBase, ICommandSender}

/**
 * Not really necessary for stuff other than testing
 */
class ColouriseCommand extends CommandBase {
  override def getCommandName: String = "colourise"

  override def getCommandUsage(sender: ICommandSender): String = "/colourise [message]"

  override def processCommand(sender: ICommandSender, args: Array[String]): Unit =
    Minecraft.getMinecraft.thePlayer.sendChatMessage(args.mkString(" ").replace("&", "\u00a7"))

  override def canCommandSenderUseCommand(sender: ICommandSender): Boolean = true
}
