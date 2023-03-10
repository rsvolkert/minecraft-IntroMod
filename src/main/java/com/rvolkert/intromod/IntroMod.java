package com.rvolkert.intromod;

import com.mojang.logging.LogUtils;
import com.rvolkert.intromod.block.ModBlocks;
import com.rvolkert.intromod.block.entity.ModBlockEntities;
import com.rvolkert.intromod.entity.ModEntityTypes;
import com.rvolkert.intromod.entity.client.ChomperRenderer;
import com.rvolkert.intromod.fluid.ModFluidTypes;
import com.rvolkert.intromod.fluid.ModFluids;
import com.rvolkert.intromod.item.ModCreativeModeTab;
import com.rvolkert.intromod.item.ModItems;
import com.rvolkert.intromod.loot.ModLootModifiers;
import com.rvolkert.intromod.networking.ModMessages;
import com.rvolkert.intromod.painting.ModPaintings;
import com.rvolkert.intromod.recipe.ModRecipes;
import com.rvolkert.intromod.screen.GemInfusingStationScreen;
import com.rvolkert.intromod.screen.ModMenuTypes;
import com.rvolkert.intromod.villager.ModVillagers;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import software.bernie.geckolib.GeckoLib;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(IntroMod.MOD_ID)
public class IntroMod {
    public static final String MOD_ID = "intromod";
    private static final Logger LOGGER = LogUtils.getLogger();

    public IntroMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        ModVillagers.register(modEventBus);
        ModPaintings.register(modEventBus);

        ModFluids.register(modEventBus);
        ModFluidTypes.register(modEventBus);

        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);

        ModRecipes.register(modEventBus);
        ModEntityTypes.register(modEventBus);

        ModLootModifiers.register(modEventBus);

        GeckoLib.initialize();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            SpawnPlacements.register(ModEntityTypes.CHOMPER.get(),
                    SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Monster::checkMonsterSpawnRules);

            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.JASMINE.getId(), ModBlocks.POTTED_JASMINE);

            ModMessages.register();
            ModVillagers.registerPOIs();
        });
    }

    private void addCreative(CreativeModeTabEvent.BuildContents event) {
        if(event.getTab() == ModCreativeModeTab.INTRO_TAB) {
            event.accept(ModItems.ZIRCON);
            event.accept(ModItems.RAW_ZIRCON);
            event.accept(ModBlocks.ZIRCON_BLOCK);
            event.accept(ModBlocks.ZIRCON_ORE);
            event.accept(ModBlocks.DEEPSLATE_ZIRCON_ORE);
            event.accept(ModBlocks.ENDSTONE_ZIRCON_ORE);
            event.accept(ModBlocks.NETHERRACK_ZIRCON_ORE);
            event.accept(ModItems.EIGHT_BALL);
            event.accept(ModBlocks.JUMPY_BLOCK);
            event.accept(ModBlocks.ZIRCON_LAMP);
            event.accept(ModItems.BLUEBERRY_SEEDS);
            event.accept(ModItems.BLUEBERRY);
            event.accept(ModItems.SOAP_WATER_BUCKET);
            event.accept(ModBlocks.GEM_INFUSING_STATION);
            event.accept(ModItems.CHOMPER_SPAWN_EGG);
            event.accept(ModItems.ZIRCON_PICKAXE);
            event.accept(ModBlocks.RED_MAPLE_LOG);
            event.accept(ModBlocks.RED_MAPLE_WOOD);
            event.accept(ModBlocks.STRIPPED_RED_MAPLE_LOG);
            event.accept(ModBlocks.STRIPPED_RED_MAPLE_WOOD);
            event.accept(ModBlocks.RED_MAPLE_PLANKS);
            event.accept(ModBlocks.RED_MAPLE_LEAVES);
            event.accept(ModBlocks.RED_MAPLE_SAPLING);
            event.accept(ModBlocks.JASMINE);
        }
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_SOAP_WATER.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_SOAP_WATER.get(), RenderType.translucent());

            MenuScreens.register(ModMenuTypes.GEM_INFUSING_STATION_MENU.get(), GemInfusingStationScreen::new);

            EntityRenderers.register(ModEntityTypes.CHOMPER.get(), ChomperRenderer::new);
        }
    }
}
