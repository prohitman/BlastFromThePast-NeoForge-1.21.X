package com.clonz.blastfromthepast.client;

import net.minecraft.resources.ResourceLocation;

public class ClientResourceHelper {
    public static ResourceLocation textureLocation(ResourceLocation entityLocation) {
        return ResourceLocation.fromNamespaceAndPath(entityLocation.getNamespace(), String.format("textures/entity/%s.png", entityLocation.getPath()));
    }
    public static ResourceLocation textureLocationWithFolder(ResourceLocation entityLocation) {
        return ResourceLocation.fromNamespaceAndPath(entityLocation.getNamespace(), String.format("textures/entity/%s/%s.png", entityLocation.getPath(), entityLocation.getPath()));
    }
    public static ResourceLocation textureLocationWithFolderAndSuffix(ResourceLocation entityLocation, String suffix) {
        return ResourceLocation.fromNamespaceAndPath(entityLocation.getNamespace(), String.format("textures/entity/%s/%s_%s.png", entityLocation.getPath(), entityLocation.getPath(), suffix));
    }
    public static ResourceLocation textureLocationWithFolderAndPrefix(ResourceLocation entityLocation, String prefix) {
        return ResourceLocation.fromNamespaceAndPath(entityLocation.getNamespace(), String.format("textures/entity/%s/%s_%s.png", entityLocation.getPath(), prefix, entityLocation.getPath()));
    }
}
