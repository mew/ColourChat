package zone.nora.colourchat.commands

import java.io.File

import net.minecraft.client.Minecraft
import net.minecraft.command.{CommandBase, ICommandSender}
import net.minecraft.util.ChatComponentText
import org.apache.commons.io.FileUtils
import zone.nora.colourchat.util.Variables

class ChangeColourCommand extends CommandBase {
  override def getCommandName: String = "setcolour"

  override def getCommandUsage(sender: ICommandSender): String = "/setcolour [colour char] (eg '/setcolour d' for pink)"

  override def processCommand(sender: ICommandSender, args: Array[String]): Unit = if (args.length == 1 && args(0).length == 1) {
    val colour = if ("""[a-f0-9]""".r.pattern.matcher(args(0)).matches) args(0) else "f"
    Variables.colour = colour.charAt(0)
    val file = new File("config/ColourChat.cfg")
    FileUtils.writeStringToFile(file, colour)
    def codeToName(code: String): String = code match {
      case "0" => "Black"
      case "1" => "Dark Aqua"
      case "2" => "Dark Green"
      case "3" => "Aqua"
      case "4" => "Dark Red"
      case "5" => "Purple"
      case "6" => "Gold"
      case "7" => "Grey"
      case "8" => "Dark Grey"
      case "9" => "Blue"
      case "a" => "Green"
      case "b" => "Light Aqua"
      case "c" => "Red"
      case "d" => "Pink"
      case "e" => "Yellow"
      case _ => "White"
    }
    Minecraft.getMinecraft.thePlayer.addChatMessage(new ChatComponentText(s"\u00a7aSet chat colour to \u00a7$colour${codeToName(colour)}"))
  } else Minecraft.getMinecraft.thePlayer.addChatMessage(new ChatComponentText(s"\u00a7c${getCommandUsage(sender)}"))

  override def canCommandSenderUseCommand(sender: ICommandSender): Boolean = true
}
