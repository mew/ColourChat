package zone.nora.colourchat

import java.io.File
import java.net.URI

import com.google.gson.JsonParser
import net.minecraft.client.Minecraft
import net.minecraftforge.client.ClientCommandHandler
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.Mod.EventHandler
import net.minecraftforge.fml.common.event.{FMLInitializationEvent, FMLPostInitializationEvent}
import org.apache.commons.io.FileUtils
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils
import zone.nora.colourchat.util.{EventListener, UpdateNotifier, Variables}

import scala.util.control.NonFatal

@Mod(modid = "ColourGuildChat", name = "ColourGuildChat", version = ColourChat.VERSION, modLanguage = "scala")
object ColourChat {
  final val VERSION = "1.0"

  @EventHandler
  def init(e: FMLInitializationEvent): Unit = {
    ClientCommandHandler.instance.registerCommand(new Command)
    MinecraftForge.EVENT_BUS.register(new EventListener)
  }

  @EventHandler
  def postInit(e: FMLPostInitializationEvent): Unit = {
    val file = new File("config/ColourGuildChat.cfg")
    if (!file.exists()) {
      if (!file.createNewFile()) throw new RuntimeException("Failed to create Config File")
      FileUtils.writeStringToFile(file, "f")
    }
    Variables.colour = FileUtils.readFileToString(file).charAt(0)

    try {
      // this is obviously super easy to bypass. it is just to deter, not to prevent.
      // if i *really* cared, i would obfuscate the mod and definitely not open source it.
      val blacklist = getUrlContent("https://gist.githubusercontent.com/mew/6686a939151c8fb3be34a54392646189/raw")
      if (blacklist._2) {
        if (blacklist._1.contains(Minecraft.getMinecraft.getSession.getPlayerID.replace("-", ""))) {
          throw new RuntimeException("You are blacklisted from using ColourGuildChat."); Minecraft.getMinecraft.shutdown()
        }
      }

      val updateData = getUrlContent("https://api.github.com/repos/mew/ColouredGuildChat/releases")
      if (updateData._2) {
        val latestVersion = new JsonParser().parse(updateData._1).getAsJsonArray.get(0).getAsJsonObject
        val tagName = latestVersion.get("tag_name").getAsString
        if (tagName != VERSION) MinecraftForge.EVENT_BUS.register(new UpdateNotifier(tagName))
      } else println("Failed to fetch update data.")
    } catch {
      case NonFatal(e) => e.printStackTrace()
    }
  }

  private def getUrlContent(url: String): (String, Boolean) = {
    try {
      val client = HttpClients.createDefault()
      val request = new HttpGet(new URI(url))
      request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 5.1; rv:19.0) Gecko/20100101 Firefox/19.0")
      (EntityUtils.toString(client.execute(request).getEntity), true)
    } catch {
      case NonFatal(e) => e.printStackTrace(); ("", false)
    }
  }
}
