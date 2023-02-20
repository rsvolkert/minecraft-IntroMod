package com.rvolkert.intromod.entity.client;

import com.rvolkert.intromod.IntroMod;
import com.rvolkert.intromod.entity.custom.ChomperEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ChomperModel extends GeoModel<ChomperEntity> {
    @Override
    public ResourceLocation getModelResource(ChomperEntity animatable) {
        return new ResourceLocation(IntroMod.MOD_ID, "geo/chomper.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ChomperEntity animatable) {
        return new ResourceLocation(IntroMod.MOD_ID, "textures/entity/chomper_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ChomperEntity animatable) {
        return new ResourceLocation(IntroMod.MOD_ID, "animations/chomper.animation.json");
    }
}
