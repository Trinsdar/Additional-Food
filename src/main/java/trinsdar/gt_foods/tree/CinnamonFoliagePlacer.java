package trinsdar.gt_foods.tree;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FoliagePlacerType;

import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;

public class CinnamonFoliagePlacer extends FoliagePlacer {
    public static final Codec<CinnamonFoliagePlacer> CODEC = RecordCodecBuilder.create((p_242834_0_) -> {
        return func_242830_b(p_242834_0_).apply(p_242834_0_, CinnamonFoliagePlacer::new);
    });

    protected CinnamonFoliagePlacer(FeatureSpread radius, FeatureSpread offset) {
        super(radius, offset);
    }

    public CinnamonFoliagePlacer() {
        super(FeatureSpread.create(3), FeatureSpread.create(-1));
    }

    @Override
    protected FoliagePlacerType<?> getPlacerType() {
        return TreeWorldGen.CINNAMON_FOLIAGE_PLACER;
    }

    @Override
    protected void func_230372_a_(IWorldGenerationReader world, Random random, BaseTreeFeatureConfig config, int trunkHeight, Foliage treeNode, int foilageHeight, int radius, Set<BlockPos> leaves, int offset, MutableBoundingBox box) {
        generate(world, random, config, trunkHeight, treeNode, foilageHeight, radius, leaves, offset, box);
    }

    protected void generate(IWorldGenerationReader world, Random random, BaseTreeFeatureConfig config, int trunkHeight, Foliage treeNode, int foliageHeight, int radius, Set<BlockPos> leaves, int offset, MutableBoundingBox box) {
        BlockPos center = treeNode.func_236763_a_();
        BlockPos.Mutable pos = center.toMutable();

        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        for(int i = offset + radius; i >= offset - radius; --i) {
            pos.setPos(x, y + i, z);
            if (i == offset - radius){
                this.func_236753_a_(world, random, config, center, radius - 1, leaves, i, treeNode.func_236765_c_(), box);
                continue;
            }
            if (i == offset + radius){
                cornerlessSquare(pos.toMutable(), radius - 1, false, position -> {
                    if (TreeFeature.isAirOrLeavesAt(world, position)) {
                        world.setBlockState(position, config.leavesProvider.getBlockState(random, position), 19);
                        box.expandTo(new MutableBoundingBox(position, position));
                        leaves.add(position.toImmutable());
                    }
                });
                continue;
            }
            if (i == offset + (radius - 1)){
                cornerlessSquare(pos.toMutable(), radius, true, position -> {
                    if (TreeFeature.isAirOrLeavesAt(world, position)) {
                        world.setBlockState(position, config.leavesProvider.getBlockState(random, position), 19);
                        box.expandTo(new MutableBoundingBox(position, position));
                        leaves.add(position.toImmutable());
                    }
                });
                continue;
            }
            cornerlessSquare(pos.toMutable(), radius, false, position -> {
                if (TreeFeature.isAirOrLeavesAt(world, position)) {
                    world.setBlockState(position, config.leavesProvider.getBlockState(random, position), 19);
                    box.expandTo(new MutableBoundingBox(position, position));
                    leaves.add(position.toImmutable());
                }
            });
        }

        /*int spikeHeight = 2 + random.nextInt(3);
        for (int i = 0; i < spikeHeight; i++){
            BlockPos leaf = center.up(i);
            if (TreeFeature.isAirOrLeavesAt(world, leaf)) {
                world.setBlockState(leaf, config.leavesProvider.getBlockState(random, leaf), 19);
                box.expandTo(new MutableBoundingBox(leaf, leaf));
                leaves.add(leaf.toImmutable());
            }
        }*/

    }

    /**
     * Iterates over the positions contained with in a circle defined by origin and radius. The circle is two dimensional,
     * perpendicular to the Y axis.
     *
     * @param origin The center block of the circle; this function clobbers the variable, and it must be reset afterwards
     * @param radius The radius of the circle
     * @param consumer The target of the positions; it passes the same BlockPos.Mutable object each time
     */
    private static void cornerlessSquare(BlockPos.Mutable origin, int radius, boolean rounded, Consumer<BlockPos.Mutable> consumer) {
        int x = origin.getX();
        int z = origin.getZ();

        for (int rx = -radius; rx <= radius; rx++){
            for (int rz = -radius; rz <= radius; rz++){
                int abx = Math.abs(rx), abz = Math.abs(rz);
                if (abx == abz && abx == radius){
                    continue;
                }
                if (rounded){
                    if (abx == radius || abz == radius){
                        if (abx == radius - 1 || abz == radius - 1){
                            continue;
                        }
                    }
                }
                origin.setPos(x + rx, origin.getY(), z + rz);
                consumer.accept(origin);
            }
        }

        //double radiusSq = radius * radius;
        //int radiusCeil = (int) Math.ceil(radius);

        /*for (int dz = -radiusCeil; dz <= radiusCeil; dz++) {
            int dzSq = dz * dz;

            for (int dx = -radiusCeil; dx <= radiusCeil; dx++) {
                int dxSq = dx * dx;

                if (dzSq + dxSq <= radiusSq) {
                    origin.setPos(x + dx, origin.getY(), z + dz);
                    consumer.accept(origin);
                }
            }
        }*/
    }

    @Override
    public int func_230374_a_(Random random, int radius, BaseTreeFeatureConfig config) {
        return Math.max(2, radius - (3 + random.nextInt(2)));
    }

    @Override
    protected boolean func_230373_a_(Random p_230373_1_, int p_230373_2_, int p_230373_3_, int p_230373_4_, int p_230373_5_, boolean p_230373_6_) {
        return false;
    }
}
