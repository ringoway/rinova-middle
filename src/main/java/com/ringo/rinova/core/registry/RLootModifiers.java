package com.ringo.rinova.core.registry;

import com.mojang.serialization.Codec;
import com.ringo.rinova.RinovaMod;
import com.ringo.rinova.common.loot.AddItemModifier;
import com.ringo.rinova.common.loot.RandomCountItemModifier;
import com.ringo.rinova.common.loot.RemoveItemModifier;
import com.ringo.rinova.common.loot.ReplaceItemModifier;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RLootModifiers {

    // Создаём регистр для сериализаторов
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIER_SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, RinovaMod.MOD_ID);

    // Регистрируем наш модификатор
    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> ADD_ITEM =
            LOOT_MODIFIER_SERIALIZERS.register("add_item", AddItemModifier.CODEC);

    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> REMOVE_ITEM =
            LOOT_MODIFIER_SERIALIZERS.register("remove_item", RemoveItemModifier.CODEC);

    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> REPLACE_ITEM =
            LOOT_MODIFIER_SERIALIZERS.register("replace_item", ReplaceItemModifier.CODEC);

    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> RANDOM_COUNT_ITEM =
            LOOT_MODIFIER_SERIALIZERS.register("random_count_item", RandomCountItemModifier.CODEC);

    public static void register(IEventBus eventBus) {
        LOOT_MODIFIER_SERIALIZERS.register(eventBus);
    }
}
