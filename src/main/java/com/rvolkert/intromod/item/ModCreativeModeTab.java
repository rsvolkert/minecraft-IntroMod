package com.rvolkert.intromod.item;

import com.rvolkert.intromod.IntroMod;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = IntroMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCreativeModeTab {
    public static CreativeModeTab INTRO_TAB;

    @SubscribeEvent
    public static void RegisterCreativeModeTabs(CreativeModeTabEvent.Register event) {
        INTRO_TAB = event.registerCreativeModeTab(new ResourceLocation(IntroMod.MOD_ID, "intro_tab"),
                builder -> builder.icon(() -> new ItemStack(ModItems.ZIRCON.get())).title(Component.literal("Intro Tab")).build());
    }
}
