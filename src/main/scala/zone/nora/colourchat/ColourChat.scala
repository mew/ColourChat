package zone.nora.colourchat

import net.minecraftforge.client.ClientCommandHandler
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.Mod.EventHandler
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import zone.nora.colourchat.util.EventListener

@Mod(modid = "ColourGuildChat", name = "ColourGuildChat", version = "1.0", modLanguage = "scala")
object ColourChat {
  @EventHandler
  def init(e: FMLInitializationEvent): Unit = {
    ClientCommandHandler.instance.registerCommand(new Command)
    MinecraftForge.EVENT_BUS.register(new EventListener)
  }
}
