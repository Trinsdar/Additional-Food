package trinsdar.gt_foods.loader;

import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.recipe.ingredient.RecipeIngredient;
import muramasa.antimatter.recipe.map.RecipeMap;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fluids.FluidStack;
import trinsdar.gt_foods.GTFoods;
import trinsdar.gt_foods.data.GTFData;
import trinsdar.gt_foods.data.GTFMaterialTypes;

public class MixingLoader {

    public static void init(){
        getMixer().RB().ii(RecipeIngredient.of(Items.EGG, 1), RecipeIngredient.of(Tags.Items.RODS_WOODEN, 1).setNoConsume()).io(new ItemStack(GTFData.SCRAMBLED_EGG)).add(16, 16);
        getMixer().RB().ii(RecipeIngredient.of(GTFMaterialTypes.FLOUR.getTag(), 1)).fi(new FluidStack(Fluids.WATER, 250)).io(new ItemStack(GTFData.DOUGH, 2)).add(16, 16);
        getMixer().RB().ii(RecipeIngredient.of(GTFMaterialTypes.FLOUR.getTag(), 1), RecipeIngredient.of(Items.EGG, 1)).io(new ItemStack(GTFData.EGG_DOUGH, 2)).add(16, 16);
        getMixer().RB().ii(RecipeIngredient.of(GTFData.DOUGH, 1), RecipeIngredient.of(Items.SUGAR, 1)).io(new ItemStack(GTFData.SUGARY_DOUGH, 2)).add(16, 16);
        getMixer().RB().ii(RecipeIngredient.of(GTFData.SUGARY_DOUGH, 1), RecipeIngredient.of(GTFData.CHOCOLATE_RAISINS, 1)).io(new ItemStack(GTFData.SUGARY_CHOCOLATE_RAISIN_DOUGH)).add(16, 16);
        getMixer().RB().ii(RecipeIngredient.of(GTFData.SUGARY_DOUGH, 1), RecipeIngredient.of(GTFData.WHITE_RAISINS, 1)).io(new ItemStack(GTFData.SUGARY_RAISIN_DOUGH)).add(16, 16);
        getMixer().RB().ii(RecipeIngredient.of(GTFData.SUGARY_DOUGH, 1), RecipeIngredient.of(GTFData.GREEN_RAISINS, 1)).io(new ItemStack(GTFData.SUGARY_RAISIN_DOUGH)).add(16, 16);
        getMixer().RB().ii(RecipeIngredient.of(GTFData.SUGARY_DOUGH, 1), RecipeIngredient.of(GTFData.RED_RAISINS, 1)).io(new ItemStack(GTFData.SUGARY_RAISIN_DOUGH)).add(16, 16);
        getMixer().RB().ii(RecipeIngredient.of(GTFData.SUGARY_DOUGH, 1), RecipeIngredient.of(GTFData.PURPLE_RAISINS, 1)).io(new ItemStack(GTFData.SUGARY_RAISIN_DOUGH)).add(16, 16);
        getMixer().RB().ii(RecipeIngredient.of(GTFData.SUGARY_DOUGH, 1), RecipeIngredient.of(GTFData.POMERAISINS, 1)).io(new ItemStack(GTFData.SUGARY_RAISIN_DOUGH)).add(16, 16);
    }

    static RecipeMap<?> getMixer(){
        String domain = AntimatterAPI.isModLoaded("gti") ? "gti" : GTFoods.MODID;
        return AntimatterAPI.get(RecipeMap.class, "mixing", domain);
    }
}