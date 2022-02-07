package trinsdar.gt_foods.datagen;

import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.AntimatterDynamics;
import muramasa.antimatter.Data;
import muramasa.antimatter.datagen.providers.AntimatterBlockStateProvider;
import muramasa.antimatter.datagen.providers.AntimatterLanguageProvider;
import muramasa.antimatter.registration.IAntimatterObject;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import trinsdar.gt_foods.GTFoods;
import trinsdar.gt_foods.blocks.BlockCrop;
import trinsdar.gt_foods.blocks.BlockCropBerry;
import trinsdar.gt_foods.blocks.BlockLeaves;
import trinsdar.gt_foods.blocks.BlockLogStrippable;
import trinsdar.gt_foods.blocks.BlockPlanks;
import trinsdar.gt_foods.blocks.BlockSapling;
import trinsdar.gt_foods.data.GTFMaterialTypes;
import trinsdar.gt_foods.items.ItemBerry;
import trinsdar.gt_foods.items.ItemFood;
import trinsdar.gt_foods.items.ItemSeed;

import java.io.IOException;

import static muramasa.antimatter.util.Utils.lowerUnderscoreToUpperSpaced;

public class GTFLangProvider extends AntimatterLanguageProvider {
    DataGenerator generator;
    public GTFLangProvider(DataGenerator gen) {
        super(GTFoods.MODID, "GT Foods en_us Localization", "en_us", gen);
        this.generator = gen;
    }

    @Override
    protected void processTranslations(String domain, String locale) {
        super.processTranslations(domain, locale);
        AntimatterAPI.all(ItemBerry.class, domain).forEach(i -> add(i, lowerUnderscoreToUpperSpaced(i.getId())));
        AntimatterAPI.all(ItemSeed.class, domain).forEach(i -> add(i, lowerUnderscoreToUpperSpaced(i.getId())));
        AntimatterAPI.all(ItemFood.class, domain).forEach(i -> add(i, lowerUnderscoreToUpperSpaced(i.getId())));
        AntimatterAPI.all(BlockCropBerry.class, domain).forEach(i -> add(i, lowerUnderscoreToUpperSpaced(i.getId())));
        AntimatterAPI.all(BlockCrop.class, domain).forEach(i -> add(i, lowerUnderscoreToUpperSpaced(i.getId())));
        AntimatterAPI.all(BlockLogStrippable.class, domain).forEach(i -> add(i, lowerUnderscoreToUpperSpaced(i.getId())));
        AntimatterAPI.all(BlockLeaves.class, domain).forEach(i -> add(i, lowerUnderscoreToUpperSpaced(i.getId())));
        AntimatterAPI.all(BlockPlanks.class, domain).forEach(i -> add(i, lowerUnderscoreToUpperSpaced(i.getId())));
        AntimatterAPI.all(BlockSapling.class, domain).forEach(i -> add(i, lowerUnderscoreToUpperSpaced(i.getId())));
        GTFMaterialTypes.GROUND.all().forEach(m -> override(Data.DUST.get(m).getDescriptionId(), "Ground " + lowerUnderscoreToUpperSpaced(m.getId())));
        add("jei.category.gt_foods.juicing", "Juicing");
    }

    @Override
    public void run(DirectoryCache pCache) throws IOException {
        super.run(pCache);
        new GTFItemModelProvider(generator).run(pCache);
        new AntimatterBlockStateProvider(GTFoods.MODID, "GT Foods BlockStates", generator).run(pCache);
    }
}
