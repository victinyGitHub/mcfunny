package name.modid

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.entity.EntityType
import net.minecraft.entity.passive.CowEntity
import net.minecraft.entity.passive.PassiveEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvent
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import org.slf4j.LoggerFactory

object Xishitpost : ModInitializer {
    private val logger = LoggerFactory.getLogger("xi-shitpost")

	val customItem: Item = Registry.register(
		Registries.ITEM,
		Identifier("tutorial", "custom_item"),
		Item(FabricItemSettings()))

	val aidanBlock: AidanBlock = AidanBlock(FabricBlockSettings.create().strength(4.0f))

	override fun onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		logger.info("Hello Fabric world!")
		Registry.register(Registries.BLOCK, Identifier("tutorial", "aidan_block"), aidanBlock)
		Registry.register(
			Registries.ITEM,
			Identifier("tutorial", "aidan_block"),
			BlockItem(aidanBlock, FabricItemSettings())
			)
		Registry.register(
			Registries.SOUND_EVENT,
			aidanBlock.pipeSoundId,
			aidanBlock.pipeSoundEvent
		)
	}
}

class AidanBlock(settings: Settings) : Block(settings) {
	val pipeSoundId: Identifier = Identifier("tutorial:my_sound")
	val pipeSoundEvent: SoundEvent = SoundEvent.of(pipeSoundId)

	override fun onBreak(world: World?, pos: BlockPos?, state: BlockState?, player: PlayerEntity?): BlockState {
		if (!world!!.isClient()) {
			player!!.sendMessage(Text.literal(
				"TREAT ME LIKE WHITE TEES"
			))

			world.playSound(
				null,
				pos,
				pipeSoundEvent,
				SoundCategory.BLOCKS,
				10f,
				1f
			)
		}

		return state!!
	}
}

