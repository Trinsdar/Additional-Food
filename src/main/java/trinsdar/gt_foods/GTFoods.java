package trinsdar.gt_foods;

import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.AntimatterDynamics;
import muramasa.antimatter.AntimatterMod;
import muramasa.antimatter.Ref;
import muramasa.antimatter.datagen.ExistingFileHelperOverride;
import muramasa.antimatter.datagen.providers.AntimatterBlockStateProvider;
import muramasa.antimatter.datagen.providers.AntimatterBlockTagProvider;
import muramasa.antimatter.event.AntimatterLoaderEvent;
import muramasa.antimatter.recipe.loader.IRecipeRegistrate;
import muramasa.antimatter.registration.RegistrationEvent;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import trinsdar.gt_foods.data.GTFData;
import trinsdar.gt_foods.data.GTFConfiguredFeatures;
import trinsdar.gt_foods.data.Guis;
import trinsdar.gt_foods.data.Machines;
import trinsdar.gt_foods.data.RecipeMaps;
import trinsdar.gt_foods.datagen.GTFBlockTagProvider;
import trinsdar.gt_foods.datagen.GTFItemModelProvider;
import trinsdar.gt_foods.datagen.GTFItemTagProvider;
import trinsdar.gt_foods.datagen.GTFLangProvider;
import trinsdar.gt_foods.datagen.GTFRecipeProvider;
import trinsdar.gt_foods.loader.SlicerLoader;
import trinsdar.gt_foods.tree.TreeWorldGen;

import java.util.function.BiConsumer;

@Mod(value = GTFoods.MODID)
public class GTFoods extends AntimatterMod {
    public static final String MODID = "gt_foods";

    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();
    public static final CreativeTabAdditionalFood CREATIVE_TAB = new CreativeTabAdditionalFood();


    public GTFoods() {
        super();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);
        FMLJavaModLoadingContext.get().getModEventBus().register(this);
        MinecraftForge.EVENT_BUS.addListener(BiomeFeatureInjection::onEvent);
        AntimatterDynamics.addProvider(GTFoods.MODID, g -> new AntimatterBlockStateProvider(GTFoods.MODID, "GT Foods BlockStates", g));
        AntimatterDynamics.addProvider(GTFoods.MODID, GTFItemModelProvider::new);
        AntimatterDynamics.addProvider(GTFoods.MODID, GTFLangProvider::new);
        final AntimatterBlockTagProvider[] p = new AntimatterBlockTagProvider[1];
        AntimatterDynamics.addProvider(Ref.ID, g -> {
            p[0] = new GTFBlockTagProvider(MODID, "GT Foods Block Tags", false, g, new ExistingFileHelperOverride());
            return p[0];
        });
        AntimatterDynamics.addProvider(Ref.ID, g -> new GTFItemTagProvider(MODID, "GT Foods Item Tags", false, g, p[0], new ExistingFileHelperOverride()));
        AntimatterDynamics.addProvider(MODID, g -> new GTFRecipeProvider(MODID, "GT Foods Recipes", g));
        new GTFRegistrar();
        MinecraftForge.EVENT_BUS.addListener(GTFoods::registerRecipeLoaders);
    }

    @SubscribeEvent
    public static void onRegister(final RegistryEvent.Register<Feature<?>> e) {
        final String domain = ModLoadingContext.get().getActiveNamespace();
        e.getRegistry().register(TreeWorldGen.CINNAMON_TREE_FEATURE);
    }

    private static void registerRecipeLoaders(AntimatterLoaderEvent event) {
        BiConsumer<String, IRecipeRegistrate.IRecipeLoader> loader = (a, b) -> event.registrat.add(Ref.ID, a, b);
        loader.accept("slicer", SlicerLoader::init);
    }

    @Override
    public String getId() {
        return GTFoods.MODID;
    }

    @Override
    public void onRegistrationEvent(RegistrationEvent event, Dist side) {
        if (event == RegistrationEvent.DATA_INIT){
            GTFData.init();
            RecipeMaps.init();
            Machines.init();
            Guis.init();
            TreeWorldGen.init();
        }
    }

    @Override
    public int getPriority() {
        return 200000;
    }

    private void setup(final FMLCommonSetupEvent event) {
        GTFConfiguredFeatures.init();
    }

    private void setupClient(final FMLClientSetupEvent event) {
        addBlocksToRenderLayer(RenderType.getCutout(),
                GTFData.BLUEBERRY_BUSH, GTFData.BLACKBERRY_BUSH,
                GTFData.GOOSEBERRY_BUSH, GTFData.RASPBERRY_BUSH,
                GTFData.STRAWBERRY_BUSH, GTFData.CRANBERRY_CROP);
    }

    @OnlyIn(Dist.CLIENT)
    public void addBlocksToRenderLayer(RenderType type, Block... blocks){
        for (Block block : blocks) {
            RenderTypeLookup.setRenderLayer(block, type);
        }
    }
}
