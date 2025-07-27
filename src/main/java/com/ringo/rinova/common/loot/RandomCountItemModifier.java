package com.ringo.rinova.common.loot;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class RandomCountItemModifier extends LootModifier {
    public static final Supplier<Codec<RandomCountItemModifier>> CODEC = Suppliers.memoize(()
            -> RecordCodecBuilder.create(inst -> codecStart(inst)
            .and(ForgeRegistries.ITEMS.getCodec().fieldOf("item").forGetter(m -> m.item))
            .and(Codec.INT.fieldOf("min").forGetter(m -> m.min)) // Минимальное количество
            .and(Codec.INT.fieldOf("max").forGetter(m -> m.max)) // Максимальное количество
            .apply(inst, RandomCountItemModifier::new))
    );

    private final Item item;
    private final int min;
    private final int max;

    public RandomCountItemModifier(LootItemCondition[] conditions, Item item, int min, int max) {
        super(conditions);
        this.item = item;
        this.min = min;
        this.max = max;

        // Валидация параметров
        if (min < 0) throw new IllegalArgumentException("Min cannot be negative");
        if (max < min) throw new IllegalArgumentException("Max cannot be less than min");
        if (max > 64) throw new IllegalArgumentException("Max/min limit is greater than 64!");
    }

    @Nonnull
    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        // Генерируем случайное количество
        int count = context.getRandom().nextInt(min, max + 1);

        // Создаем и добавляем предмет
        generatedLoot.add(new ItemStack(item, count));

        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}
