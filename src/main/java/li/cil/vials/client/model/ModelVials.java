package li.cil.vials.client.model;

import li.cil.vials.common.API;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import li.cil.vials.common.Constants;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.*;
import net.minecraftforge.common.model.IModelPart;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import org.apache.commons.lang3.tuple.Pair;

import javax.vecmath.Matrix4f;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by lordjoda on 15.04.2017.
 */
public final class ModelVials implements IModel {

    public static final ModelVials MODEL = new ModelVials();
    private final Fluid fluid;
    private ResourceLocation baseLocation;
    private ResourceLocation coverLocation;

    private static final float NORTH_Z_FLUID = 7.498f / 16f;
    private static final float SOUTH_Z_FLUID = 8.502f / 16f;

    private static final float NORTH_Z_BASE = 7.496f / 16f;
    private static final float SOUTH_Z_BASE = 8.504f / 16f;

    private final ResourceLocation liquidLocation;

    public ModelVials() {
        this(null, null, null, null);
    }

    public ModelVials(ResourceLocation location, ResourceLocation liquidLocation, ResourceLocation coverLocation, Fluid fluid) {

        this.baseLocation = location;
        this.liquidLocation = liquidLocation;
        this.coverLocation = coverLocation;
        this.fluid = fluid;
    }


    @Override
    public IModel process(ImmutableMap<String, String> customData) {
        String fluidName = customData.get("fluid");
        Fluid fluid = FluidRegistry.getFluid(fluidName);

        if (fluid == null) fluid = this.fluid;


        // create new model with correct liquid
        return new ModelVials(baseLocation, liquidLocation, coverLocation, fluid);
    }

    @Override
    public IModel retexture(ImmutableMap<String, String> textures) {
        ResourceLocation base = baseLocation;
        ResourceLocation liquid = liquidLocation;
        ResourceLocation cover = coverLocation;

        if (textures.containsKey("base"))
            base = new ResourceLocation(textures.get("base"));
        if (textures.containsKey("fluid"))
            liquid = new ResourceLocation(textures.get("fluid"));
        if (textures.containsKey("cover"))
            cover = new ResourceLocation(textures.get("cover"));

        return new ModelVials(base, liquid, cover, fluid);
    }

    @Override
    public Collection<ResourceLocation> getDependencies() {
        return ImmutableList.of();
    }

    @Override
    public Collection<ResourceLocation> getTextures() {
        ImmutableSet.Builder<ResourceLocation> builder = ImmutableSet.builder();
        if (baseLocation != null)
            builder.add(baseLocation);
        if (liquidLocation != null)
            builder.add(liquidLocation);
        if (coverLocation != null)
            builder.add(coverLocation);

        return builder.build();
    }

    @Override
    public IBakedModel bake(IModelState state, VertexFormat format,
                            java.util.function.Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter)
    {
        ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transformMap = PerspectiveMapWrapper.getTransforms(state);

//        // if the fluid is a gas wi manipulate the initial state to be rotated 180? to turn it upside down
//        if (flipGas && fluid != null && fluid.isGaseous())
//        {
//            state = new ModelStateComposition(state, TRSRTransformation.blockCenterToCorner(new TRSRTransformation(null, new Quat4f(0, 0, 1, 0), null, null)));
//        }

        TRSRTransformation transform = state.apply(java.util.Optional.empty()).orElse(TRSRTransformation.identity());
        TextureAtlasSprite fluidSprite = null;
        ImmutableList.Builder<BakedQuad> builder = ImmutableList.builder();

        if (fluid != null) {
            fluidSprite = bakedTextureGetter.apply(fluid.getStill());
        }

        if (baseLocation != null) {
            // build base (insidest)
            IBakedModel model = (new ItemLayerModel(ImmutableList.of(baseLocation))).bake(state, format, bakedTextureGetter);
            builder.addAll(model.getQuads(null, null, 0));
        }
        if (liquidLocation != null && fluidSprite != null) {
            TextureAtlasSprite liquid = bakedTextureGetter.apply(liquidLocation);
            // build liquid layer (inside)
            builder.addAll(ItemTextureQuadConverter.convertTexture(format, transform, liquid, fluidSprite, NORTH_Z_FLUID, EnumFacing.NORTH, fluid.getColor()));
            builder.addAll(ItemTextureQuadConverter.convertTexture(format, transform, liquid, fluidSprite, SOUTH_Z_FLUID, EnumFacing.SOUTH, fluid.getColor()));
        }
        if (coverLocation != null) {
            // cover (the actual item around the other two)
            TextureAtlasSprite base = bakedTextureGetter.apply(coverLocation);
            builder.add(ItemTextureQuadConverter.genQuad(format, transform, 0, 0, 16, 16, NORTH_Z_BASE, base, EnumFacing.NORTH, 0xffffffff));
            builder.add(ItemTextureQuadConverter.genQuad(format, transform, 0, 0, 16, 16, SOUTH_Z_BASE, base, EnumFacing.SOUTH, 0xffffffff));
        }


        return new BakedVials(this, builder.build(), fluidSprite, format, Maps.immutableEnumMap(transformMap), Maps.<String, IBakedModel>newHashMap());

    }

    @Override
    public IModelState getDefaultState() {
        return TRSRTransformation.identity();
    }

    public enum LoaderVials implements ICustomModelLoader {
        INSTANCE;

        @Override
        public boolean accepts(ResourceLocation modelLocation) {

            return modelLocation.getResourceDomain().equals(API.MOD_ID) && modelLocation.getResourcePath().contains(Constants.VIAL_ITEM);
        }

        @Override
        public IModel loadModel(ResourceLocation modelLocation) {
            //  MODEL.setLocation(modelLocation);
            return MODEL;
        }

        @Override
        public void onResourceManagerReload(IResourceManager resourceManager) {
            // no need to clear cache since we create a new model instance
        }
    }

    private static final class BakedVialsOverrideHandler extends ItemOverrideList {
        public static final BakedVialsOverrideHandler INSTANCE = new BakedVialsOverrideHandler();

        private BakedVialsOverrideHandler() {
            super(ImmutableList.<ItemOverride>of());
        }

        @Override
        public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, World world, EntityLivingBase entity) {
            FluidStack fluidStack = FluidUtil.getFluidContained(stack);

            // not a fluid item apparently
            if (fluidStack == null) {
                // empty bucket
                return originalModel;
            }

            BakedVials model = (BakedVials) originalModel;

            Fluid fluid = fluidStack.getFluid();
            String name = fluid.getName();

            if (!model.cache.containsKey(name)) {
                IModel parent = model.parent.process(ImmutableMap.of("fluid", name));
                Function<ResourceLocation, TextureAtlasSprite> textureGetter;
                textureGetter = new Function<ResourceLocation, TextureAtlasSprite>() {
                    public TextureAtlasSprite apply(ResourceLocation location) {
                        return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString());
                    }
                };

                IBakedModel bakedModel = parent.bake(new SimpleModelState(model.transforms), model.format, textureGetter);
                model.cache.put(name, bakedModel);
                return bakedModel;
            }

            return model.cache.get(name);
        }
    }

    // the dynamic bucket is based on the empty bucket
    private static final class BakedVials implements IBakedModel {

        private final ModelVials parent;
        // FIXME: guava cache?
        private final Map<String, IBakedModel> cache; // contains all the baked models since they'll never change
        private final ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms;
        private final ImmutableList<BakedQuad> quads;
        private final TextureAtlasSprite particle;
        private final VertexFormat format;

        public BakedVials(ModelVials parent,
                          ImmutableList<BakedQuad> quads, TextureAtlasSprite particle, VertexFormat format, ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms,
                          Map<String, IBakedModel> cache) {
            this.quads = quads;
            this.particle = particle;
            this.format = format;
            this.parent = parent;
            this.transforms = transforms;
            this.cache = cache;
        }

        @Override
        public ItemOverrideList getOverrides() {
            return BakedVialsOverrideHandler.INSTANCE;
        }

        @Override
        public Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType) {
            return PerspectiveMapWrapper.handlePerspective(this, transforms, cameraTransformType);
        }

        @Override
        public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
            if (side == null) return quads;
            return ImmutableList.of();
        }

        public boolean isAmbientOcclusion() {
            return true;
        }

        public boolean isGui3d() {
            return false;
        }

        public boolean isBuiltInRenderer() {
            return false;
        }

        public TextureAtlasSprite getParticleTexture() {
            return particle;
        }

        public ItemCameraTransforms getItemCameraTransforms() {
            return ItemCameraTransforms.DEFAULT;
        }
    }
}
