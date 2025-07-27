package com.ringo.rinova.core.datagen.providers.loot;

import com.ringo.rinova.RinovaMod;
import com.ringo.rinova.common.loot.AddItemModifier;
import com.ringo.rinova.common.loot.RandomCountItemModifier;
import com.ringo.rinova.common.loot.RemoveItemModifier;
import com.ringo.rinova.common.loot.ReplaceItemModifier;
import com.ringo.rinova.core.registry.RItems;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;

public class RGlobalLootModifiersProvider extends GlobalLootModifierProvider {
    public RGlobalLootModifiersProvider(PackOutput output) {
        super(output, RinovaMod.MOD_ID);
    }

    @Override
    protected void start() {
        // Добавляем Hell Brush в сундуки бастиона
        add("hell_brush_from_bastion",
                new AddItemModifier(
                        new LootItemCondition[] {
                                LootTableIdCondition.builder(BuiltInLootTables.BASTION_TREASURE).build(),
                        },
                        RItems.HELL_BRUSH.get()
                )
        );

        // Добавляем сердце иссушителя с босса
        add("wither_heart_from_wither",
                new AddItemModifier(
                        new LootItemCondition[] {
                                LootTableIdCondition.builder(EntityType.WITHER.getDefaultLootTable()).build(),
                                LootItemKilledByPlayerCondition.killedByPlayer().build()
                        },
                        RItems.HEART_WITHER.get()
                )
        );
        // Заменяем скалковый катализатор на компас в Хранителе (Вардене)
        add("replace_sculk_from_warden",
                new ReplaceItemModifier(
                        new LootItemCondition[] {
                                LootTableIdCondition.builder(EntityType.WARDEN.getDefaultLootTable()).build()
                        },
                        Items.RECOVERY_COMPASS
                )
        );
        // Убираем кости из скелета-иссушителя
        add("remove_bones_from_wither_skeleton",
                new RemoveItemModifier(
                        new LootItemCondition[] {
                                LootTableIdCondition.builder(EntityType.WITHER_SKELETON.getDefaultLootTable()).build()
                        },
                        Items.BONE
                )
        );
        // Вултановые стержни в крепости незер
        add("vultan_rods_from_nether_bridge",
                new RandomCountItemModifier(
                        new LootItemCondition[] {
                                LootTableIdCondition.builder(BuiltInLootTables.NETHER_BRIDGE).build()
                        },
                        RItems.VULTAN_RODS.get(), 1, 5
                )
        );
    }
}
