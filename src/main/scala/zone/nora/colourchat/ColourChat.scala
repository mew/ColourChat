package zone.nora.colourchat

import net.minecraftforge.client.ClientCommandHandler
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent

@Mod(modid = "ColourGuildChat", name = "ColourGuildChat", version = "1.0", modLanguage = "scala")
object ColourChat {
  @Mod.EventHandler
  def init(e: FMLInitializationEvent): Unit = ClientCommandHandler.instance.registerCommand(new Command)
}
