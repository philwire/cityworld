package me.daddychurchill.CityWorld.Support;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plugins.LootProvider;
import me.daddychurchill.CityWorld.Plugins.LootProvider.LootLocation;
import org.bukkit.Axis;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.block.data.*;
import org.bukkit.block.data.Bisected.Half;
import org.bukkit.block.data.Rail.Shape;
import org.bukkit.block.data.type.*;
import org.bukkit.util.noise.NoiseGenerator;

public abstract class SupportBlocks extends AbstractBlocks {

    private boolean doPhysics;
    private boolean doClearData;

    public SupportBlocks(CityWorldGenerator generator) {
        super(generator);

        doPhysics = false;
        doClearData = false;
    }

    public abstract Block getActualBlock(int x, int y, int z);

    public final boolean getDoPhysics() {
        return doPhysics;
    }

    public final boolean setDoPhysics(boolean dophysics) {
        boolean was = doPhysics;
        doPhysics = dophysics;
        return was;
    }

    public final boolean getDoClearData() {
        return doClearData;
    }

    public final boolean setDoClearData(boolean docleardata) {
        boolean was = doClearData;
        doClearData = docleardata;
        return was;
    }

    @Override
    public final void setBlockIfEmpty(int x, int y, int z, Material material) {
        Block block = getActualBlock(x, y, z);
        if (block.isEmpty() && !getActualBlock(x, y - 1, z).isEmpty())
            setActualBlock(block, material);
    }

    private final void setActualBlock(Block block, Material material) {
        block.setType(material, doPhysics);
    }

    @Override
    public final void setBlock(int x, int y, int z, Material material) {
        setActualBlock(getActualBlock(x, y, z), material);
    }

    /**
     * @deprecated May cause lag
     */
    public final void setBlockWithPhysics(int x, int y, int z, Material material) {
        // Prevent to trigger physics event on chunk border
        if (x == 0 || x == 15 || z == 0 || z == 15) {
//            Bukkit.getServer().broadcastMessage("Border physics: " + this.sectionX + ", " + this.sectionZ + "(" + x + ", " + y + ", " + z + ")");
            setBlock(x, y, z, material);
            return;
        }
        boolean was = setDoPhysics(true);
        setBlock(x, y, z, material);
        setDoPhysics(was);
    }

    protected final boolean isType(Block block, Material... types) {
        Material type = block.getType();
        for (Material test : types)
            if (type == test)
                return true;
        return false;
    }

    public final boolean isType(int x, int y, int z, Material type) {
        return getActualBlock(x, y, z).getType() == type;
    }

    public final boolean isOfTypes(int x, int y, int z, Material... types) {
        return isType(getActualBlock(x, y, z), types);
    }

    public final void setBlockIfNot(int x, int y, int z, Material... types) {
        if (!isOfTypes(x, y, z, types))
            setBlock(x, y, z, types[0]);
    }

    @Override
    public final boolean isEmpty(int x, int y, int z) {
        return getActualBlock(x, y, z).isEmpty();
    }

    public final boolean isPartiallyEmpty(int x, int y1, int y2, int z) {
        for (int y = y1; y < y2; y++) {
            if (isEmpty(x, y, z))
                return true;
        }
        return false;
    }

    public final boolean isPartiallyEmpty(int x1, int x2, int y1, int y2, int z1, int z2) {
        for (int x = x1; x < x2; x++) {
            for (int y = y1; y < y2; y++) {
                for (int z = z1; z < z2; z++) {
                    if (isEmpty(x, y, z))
                        return true;
                }
            }
        }
        return false;
    }

    public abstract boolean isSurroundedByEmpty(int x, int y, int z);

    public final boolean isWater(int x, int y, int z) {
        return isOfTypes(x, y, z, Material.WATER);
//		return getActualBlock(x, y, z).isLiquid();
    }

    public abstract boolean isByWater(int x, int y, int z);

    public final Location getBlockLocation(int x, int y, int z) {
        return getActualBlock(x, y, z).getLocation();
    }

    @Override
    public final void clearBlock(int x, int y, int z) {
        getActualBlock(x, y, z).setType(Material.AIR);
    }

    //================ x, y1, y2, z
    @Override
    public final void setBlocks(int x, int y1, int y2, int z, Material material) {
        for (int y = y1; y < y2; y++)
            setBlock(x, y, z, material);
    }

    /**
     * @deprecated May cause lag
     */
    public final void setBlocksWithPhysics(int x, int y1, int y2, int z, Material material) {
        // Prevent to trigger physics event on chunk border
        if (x == 0 || x == 15 || z == 0 || z == 15) {
//            Bukkit.getServer().broadcastMessage("Border physics: " + this.sectionX + ", " + this.sectionZ + "(" + x + ", " + y1 + ", " + z + ")");
            setBlocks(x, y1, y2, z, material);
            return;
        }
        boolean was = setDoPhysics(true);
        setBlocks(x, y1, y2, z, material);
        setDoPhysics(was);
    }

    //================ x1, x2, y1, y2, z1, z2
    @Override
    public final void setBlocks(int x1, int x2, int y1, int y2, int z1, int z2, Material material) {
        for (int x = x1; x < x2; x++) {
            for (int y = y1; y < y2; y++) {
                for (int z = z1; z < z2; z++) {
                    setBlock(x, y, z, material);
                }
            }
        }
    }

    /**
     * @deprecated May cause lag
     */
    public final void setBlocksWithPhysics(int x1, int x2, int y1, int y2, int z1, int z2, Material material) {
        // Prevent to trigger physics event on chunk border
        if (x1 == 0 || x2 == 16 || z1 == 0 || z2 == 16) {
//            Bukkit.getServer().broadcastMessage("Border physics: " + this.sectionX + ", " + this.sectionZ + "(" + x1 + ", " + y1 + ", " + z1 + ")");
            setBlocks(x1, x2, y1, y2, z1, z2, material);
            return;
        }
        boolean was = setDoPhysics(true);
        setBlocks(x1, x2, y1, y2, z1, z2, material);
        setDoPhysics(was);
    }

    //================ x1, x2, y, z1, z2
    @Override
    public final void setBlocks(int x1, int x2, int y, int z1, int z2, Material material) {
        for (int x = x1; x < x2; x++) {
            for (int z = z1; z < z2; z++) {
                setBlock(x, y, z, material);
            }
        }
    }

    /**
     * @deprecated May cause lag
     */
    public final void setBlocksWithPhysics(int x1, int x2, int y, int z1, int z2, Material material) {
        // Prevent to trigger physics event on chunk border
        if (x1 == 0 || x2 == 16 || z1 == 0 || z2 == 16) {
//            Bukkit.getServer().broadcastMessage("Border physics: " + this.sectionX + ", " + this.sectionZ + "(" + x1 + ", " + y + ", " + z1 + ")");
            setBlocks(x1, x2, y, z1, z2, material);
            return;
        }
        boolean was = setDoPhysics(true);
        setBlocks(x1, x2, y, z1, z2, material);
        setDoPhysics(was);
    }

    /**
     * @deprecated May cause lag
     */
    public final void setBlocksUpwardWithPhysics(int x, int y1, int z, int maxY, Material material) {
        // Prevent to trigger physics event on chunk border
        if (x == 0 || x == 15 || z == 0 || z == 15) {
//            Bukkit.getServer().broadcastMessage("Border physics: " + this.sectionX + ", " + this.sectionZ + "(" + x + ", " + y1 + ", " + z + ")");
            setBlocksUpward(x, y1, z, maxY, material);
            return;
        }
        boolean was = setDoPhysics(true);
        setBlocksUpward(x, y1, z, maxY, material);
        setDoPhysics(was);
    }

    @Override
    public final void setWalls(int x1, int x2, int y1, int y2, int z1, int z2, Material material) {
        if (material.createBlockData() instanceof MultipleFacing) {
            setBlocks(x1 + 1, x2 - 1, y1, y2, z1, z1 + 1, material, BlockFace.EAST, BlockFace.WEST);    // N
            setBlocks(x1 + 1, x2 - 1, y1, y2, z2 - 1, z2, material, BlockFace.EAST, BlockFace.WEST);    // S
            setBlocks(x1, x1 + 1, y1, y2, z1 + 1, z2 - 1, material, BlockFace.SOUTH, BlockFace.NORTH);  // W
            setBlocks(x2 - 1, x2, y1, y2, z1 + 1, z2 - 1, material, BlockFace.SOUTH, BlockFace.NORTH);  // E
            setBlocks(x1, y1, y2, z1, material, BlockFace.SOUTH, BlockFace.EAST);                // NW
            setBlocks(x1, y1, y2, z2 - 1, material, BlockFace.NORTH, BlockFace.EAST);         // SW
            setBlocks(x2 - 1, y1, y2, z1, material, BlockFace.SOUTH, BlockFace.WEST);         // NE
            setBlocks(x2 - 1, y1, y2, z2 - 1, material, BlockFace.NORTH, BlockFace.WEST);  // SE
        } else {
            setBlocks(x1, x2, y1, y2, z1, z1 + 1, material);                    // N
            setBlocks(x1, x2, y1, y2, z2 - 1, z2, material);                    // S
            setBlocks(x1, x1 + 1, y1, y2, z1 + 1, z2 - 1, material);    // W
            setBlocks(x2 - 1, x2, y1, y2, z1 + 1, z2 - 1, material);    // E
        }
    }

    @Override
    public final int setLayer(int blocky, Material material) {
        setBlocks(0, width, blocky, blocky + 1, 0, width, material);
        return blocky + 1;
    }

    @Override
    public final int setLayer(int blocky, int height, Material material) {
        setBlocks(0, width, blocky, blocky + height, 0, width, material);
        return blocky + height;
    }

    @Override
    public final int setLayer(int blocky, int height, int inset, Material material) {
        setBlocks(inset, width - inset, blocky, blocky + height, inset, width - inset, material);
        return blocky + height;
    }

    @Override
    public final boolean setEmptyBlock(int x, int y, int z, Material material) {
        Block block = getActualBlock(x, y, z);
        if (block.isEmpty()) {
            block.setType(material);
            return true;
        } else
            return false;
    }

    @Override
    public final void setEmptyBlocks(int x1, int x2, int y, int z1, int z2, Material material) {
        for (int x = x1; x < x2; x++) {
            for (int z = z1; z < z2; z++) {
                Block block = getActualBlock(x, y, z);
                if (block.isEmpty())
                    block.setType(material);
            }
        }
    }

    //@@ I REALLY NEED TO FIGURE A DIFFERENT WAY TO DO THIS
    public final boolean isNonstackableBlock(Block block) { // either because it really isn't or it just doesn't look good
        return !block.getType().isOccluding();
//		switch (block.getType()) {
//		default: 
//			return true;
//		case STONE:
//		case GRASS:
//		case DIRT:
//		case COBBLESTONE:
////		case WOOD:
//		case SAND:
//		case GRAVEL:
//		case COAL_ORE:
//		case DIAMOND_ORE:
//		case EMERALD_ORE:
//		case GOLD_ORE:
//		case IRON_ORE:
//		case LAPIS_ORE:
////		case QUARTZ_ORE:
//		case REDSTONE_ORE:
//		case COAL_BLOCK:
//		case DIAMOND_BLOCK:
//		case EMERALD_BLOCK:
//		case GOLD_BLOCK:
//		case HAY_BLOCK:
//		case IRON_BLOCK:
//		case LAPIS_BLOCK:
//		case PURPUR_BLOCK:
//		case QUARTZ_BLOCK:
//		case SLIME_BLOCK:
//		case SNOW_BLOCK:
////		case LOG:
////		case LOG_2:
//		case SPONGE:
//		case SANDSTONE:
//		case RED_SANDSTONE:
//		case SOUL_SAND:
////		case STAINED_CLAY:
////		case HARD_CLAY:
//		case CLAY:
////		case WOOL:
////		case DOUBLE_STEP:
////		case WOOD_DOUBLE_STEP:
////		case DOUBLE_STONE_SLAB2:
////		case PURPUR_DOUBLE_SLAB:
//		case BRICK:
////		case END_BRICKS:
//		case NETHER_BRICK:
////		case SMOOTH_BRICK:
//		case BOOKSHELF:
//		case MOSSY_COBBLESTONE:
//		case OBSIDIAN:
////		case SOIL:
//		case ICE:
//		case PACKED_ICE:
//		case FROSTED_ICE:
//		case NETHERRACK:
////		case ENDER_STONE:
////		case MYCEL:
//		case PRISMARINE:
//		case GRASS_PATH:
//		case BEDROCK:
//			return false;
//		}
//		return isType(block, Material.STONE_SLAB, Material.WOOD_STEP, 
//				 			 Material.GLASS, Material.GLASS_PANE,
//							 Material.SNOW, Material.CARPET, Material.SIGN, 
//							 Material.WOOD_DOOR, Material.TRAP_DOOR, 
//							 Material.WHITE_STAINED_GLASS, Material.WHITE_STAINED_GLASS_PANE,
//							 Material.SPRUCE_FENCE, Material.SPRUCE_FENCE_GATE,
//							 Material.STONE_PRESSURE_PLATE, Material.BIRCH_PRESSURE_PLATE,
//							 Material.TRIPWIRE, Material.TRIPWIRE_HOOK,
//							 Material.IRON_DOOR_BLOCK, Material.IRON_BARS);
    }

    public final boolean isNonstackableBlock(int x, int y, int z) {
        return isNonstackableBlock(getActualBlock(x, y, z));
    }

    private int clamp(double value, int max) {
        return clamp(value, 0, max);
    }

    private int clamp(double value, int min, int max) {
        return NoiseGenerator.floor((value - NoiseGenerator.floor(value)) * (max - min)) + min;
    }

    public final Block setBlock(int x, int y, int z, Material material, double level) {
        Block block = getActualBlock(x, y, z);
        block.setType(material, false);
        BlockData data = block.getBlockData();
        try {
            if (data instanceof Ageable) {
                Ageable ageable = (Ageable) data;
                ageable.setAge(clamp(level, ageable.getMaximumAge()));
            } else if (data instanceof Levelled) {
                Levelled levelled = (Levelled) data;
                levelled.setLevel(clamp(level, levelled.getMaximumLevel()));
            } else if (data instanceof Snow) {
                Snow snow = (Snow) data;
                snow.setLayers(clamp(level, snow.getMinimumLayers(), snow.getMaximumLayers()));
            }
        } finally {
            block.setBlockData(data, doPhysics);
        }

        return block;
    }

    public final void setCauldron(int x, int y, int z, Odds odds) {
        setBlock(x, y, z, Material.CAULDRON, odds.getRandomDouble());
    }

    public final void setBlocksRandomly(int x1, int x2, int y1, int y2, int z1, int z2, Odds odds, Material... materials) {
        for (int x = x1; x < x2; x++) {
            for (int y = y1; y < y2; y++) {
                for (int z = z1; z < z2; z++) {
                    setBlock(x, y, z, odds.getRandomMaterial(materials));
                }
            }
        }
    }

    public final void setBlockRandomly(int x, int y, int z, Odds odds, Material... materials) {
        setBlock(x, y, z, odds.getRandomMaterial(materials));
    }

    public final void setVine(int x, int y, int z, BlockFace... faces) {
        Block block = getActualBlock(x, y, z);
        block.setType(Material.VINE, false);
        BlockData data = block.getBlockData();
        try {
            if (data instanceof MultipleFacing) {
                MultipleFacing vines = (MultipleFacing) data;
                for (BlockFace face : faces)
                    vines.setFace(face, true);
            }
        } finally {
            block.setBlockData(data, doPhysics);
        }
    }

    public final void setVines(int x, int y1, int y2, int z, BlockFace... faces) {
        for (int y = y1; y < y2; y++)
            setVine(x, y, z, faces);
    }

    public final Block setBlock(int x, int y, int z, Material material, Shape shape, boolean powered) {
        Block block = getActualBlock(x, y, z);
        block.setType(material, false);
        BlockData data = block.getBlockData();
        try {
            if (data instanceof Rail)
                ((Rail) data).setShape(shape);
            if (data instanceof Powerable)
                ((Powerable) data).setPowered(powered);
        } finally {
            block.setBlockData(data, doPhysics);
        }
        return block;
    }

    public final Block setBlock(int x, int y, int z, Material material, Slab.Type type) {
        Block block = getActualBlock(x, y, z);
        block.setType(material, false);
        BlockData data = block.getBlockData();
        try {
            if (data instanceof Slab)
                ((Slab) data).setType(type);
        } finally {
            block.setBlockData(data, doPhysics);
        }
        return block;
    }

    public final void setBlocks(int x1, int x2, int y, int z1, int z2, Material material, Slab.Type type) {
        setBlocks(x1, x2, y, y + 1, z1, z2, material, type);
    }

    public final void setBlocks(int x, int y1, int y2, int z, Material material, Slab.Type type) {
        for (int y = y1; y < y2; y++) {
            setBlock(x, y, z, material, type);
        }
    }

    public final void setBlocks(int x1, int x2, int y1, int y2, int z1, int z2, Material material, Slab.Type type) {
        for (int x = x1; x < x2; x++) {
            for (int y = y1; y < y2; y++) {
                for (int z = z1; z < z2; z++) {
                    setBlock(x, y, z, material, type);
                }
            }
        }
    }

    public final Block setBlock(int x, int y, int z, Material material, BlockFace facing) {
        Block block = getActualBlock(x, y, z);
        block.setType(material, false);
        BlockData data = block.getBlockData();
        try {
            if (data instanceof Directional) {
                ((Directional) data).setFacing(facing);
            } else if (data instanceof MultipleFacing) {
                ((MultipleFacing) data).setFace(facing, true);
            } else if (data instanceof Orientable) {
                switch (facing) {
                    case NORTH:
                    case SOUTH:
                        ((Orientable) data).setAxis(Axis.Z);
                        break;
                    case EAST:
                    case WEST:
                        ((Orientable) data).setAxis(Axis.X);
                        break;
                    default:
                        ((Orientable) data).setAxis(Axis.Y);
                }
            }
        } finally {
            block.setBlockData(data, doPhysics);
        }
        return block;
    }

    public final Block setBlock(int x, int y, int z, Material material, BlockFace... facing) {
        Block block = getActualBlock(x, y, z);
        block.setType(material, false);
        BlockData data = block.getBlockData();
        try {
            if (data instanceof MultipleFacing) {
                for (BlockFace face : facing) {
                    ((MultipleFacing) data).setFace(face, true);
                }
            }
        } finally {
            block.setBlockData(data, doPhysics);
        }
        return block;
    }

    public final Block setStair(int x, int y, int z, Material material, BlockFace facing) {
        return setStair(x, y, z, material, facing, Stairs.Shape.STRAIGHT);
    }

    public final Block setStair(int x, int y, int z, Material material, BlockFace facing, Stairs.Shape shape) {
        Block block = getActualBlock(x, y, z);
        block.setType(material, false);
        BlockData data = block.getBlockData();
        try {
            if (data instanceof Directional) {
                ((Directional) data).setFacing(facing);
            }
            if (data instanceof Stairs) {
                ((Stairs) data).setShape(shape);
            }
        } finally {
            block.setBlockData(data, doPhysics);
        }
        return block;
    }

    /**
     * @deprecated May cause lag
     */
    public final Block setBlockWithPhysics(int x, int y, int z, Material material, BlockFace facing) {
        boolean was = setDoPhysics(true);
        Block block = setBlock(x, y, z, material, facing);
        setDoPhysics(was);
        return block;
    }

    public final void setBlocks(int x, int y1, int y2, int z, Material material, BlockFace facing) {
        setBlocks(x, x + 1, y1, y2, z, z + 1, material, facing);
    }

    public final void setBlocks(int x, int y1, int y2, int z, Material material, BlockFace... facing) {
        setBlocks(x, x + 1, y1, y2, z, z + 1, material, facing);
    }

    public final void setBlocks(int x1, int x2, int y1, int y2, int z1, int z2, Material material, BlockFace facing) {
        for (int x = x1; x < x2; x++) {
            for (int y = y1; y < y2; y++) {
                for (int z = z1; z < z2; z++) {
                    setBlock(x, y, z, material, facing);
                }
            }
        }
    }

    public final void setBlocks(int x1, int x2, int y1, int y2, int z1, int z2, Material material, BlockFace... facing) {
        for (int x = x1; x < x2; x++) {
            for (int y = y1; y < y2; y++) {
                for (int z = z1; z < z2; z++) {
                    setBlock(x, y, z, material, facing);
                }
            }
        }
    }

    public final void drawCrane(DataContext context, Odds odds, int x, int y, int z) {
        Colors colors = new Colors(odds);

        // vertical bit
        setBlocks(x, y, y + 8, z, Material.IRON_BARS, BlockFace.WEST);
        setBlocks(x - 1, y, y + 8, z, Material.IRON_BARS, BlockFace.EAST); // 1.9 shows iron fences very thin now
        setBlocks(x, y + 8, y + 10, z, Material.STONE);
        setBlocks(x - 1, y + 8, y + 10, z, Material.STONE_SLAB);
        setBlock(x, y + 10, z, context.torchMat, BlockFace.UP);

        // horizontal bit
        setBlock(x + 1, y + 8, z, Material.GLASS);
        setBlocks(x + 2, x + 10, y + 8, y + 9, z, z + 1, Material.IRON_BARS, BlockFace.EAST, BlockFace.WEST);
        setBlock(x + 10, y + 8, z, Material.IRON_BARS, BlockFace.WEST);
        setBlocks(x + 1, x + 10, y + 9, y + 10, z, z + 1, Material.STONE_SLAB);
        setBlock(x + 10, y + 9, z, Material.STONE_BRICK_STAIRS, BlockFace.WEST);

        // counter weight
        setBlock(x - 2, y + 9, z, Material.STONE_SLAB);
        setBlock(x - 3, y + 9, z, Material.STONE_BRICK_STAIRS, BlockFace.EAST);
        setBlocks(x - 3, x - 1, y + 7, y + 9, z, z + 1, colors.getConcrete());
    }

    public final void setTable(int x1, int x2, int y, int z1, int z2, Material tableLeg, Material tableTop) {
        for (int x = x1; x < x2; x++) {
            for (int z = z1; z < z2; z++) {
                setTable(x, y, z, tableLeg, tableTop);
            }
        }
    }

    public final void setTable(int x, int y, int z, Material tableLeg, Material tableTop) {
        setBlock(x, y, z, tableLeg);
        setBlock(x, y + 1, z, tableTop);
    }

    public void setDoor(int x, int y, int z, Material material, BlockFace facing) {
        clearBlock(x, y, z);
        clearBlock(x, y + 1, z);

        Block blockBottom = getActualBlock(x, y, z);
        Block blockTop = getActualBlock(x, y + 1, z);

        blockBottom.setType(material, false);
        blockTop.setType(material, false);

        BlockData dataBottom = blockBottom.getBlockData();
        BlockData dataTop = blockTop.getBlockData();

        // Fix facing
        switch (facing) {
            case WEST_NORTH_WEST:
            case WEST_SOUTH_WEST:
                facing = BlockFace.WEST;
                break;
            case NORTH_NORTH_WEST:
            case NORTH_NORTH_EAST:
                facing = BlockFace.NORTH;
                break;
            case EAST_NORTH_EAST:
            case EAST_SOUTH_EAST:
                facing = BlockFace.EAST;
                break;
            case SOUTH_SOUTH_EAST:
            case SOUTH_SOUTH_WEST:
                facing = BlockFace.SOUTH;
                break;
        }
        facing = facing.getOppositeFace();

        try {
            if (dataBottom instanceof Directional)
                ((Directional) dataBottom).setFacing(facing);
            if (dataTop instanceof Directional)
                ((Directional) dataTop).setFacing(facing);

            if (dataBottom instanceof Bisected)
                ((Bisected) dataBottom).setHalf(Half.BOTTOM);
            if (dataTop instanceof Bisected)
                ((Bisected) dataTop).setHalf(Half.TOP);
        } finally {
            blockBottom.setBlockData(dataBottom, false);
            blockTop.setBlockData(dataTop, true);
        }
    }

    public final void setLadder(int x, int y1, int y2, int z, BlockFace direction) {

        // this calculates which wall the ladder is on
        int offsetX = 0;
        int offsetZ = 0;
        switch (direction) {
            case EAST:
                offsetX = -1;
                break;
            case WEST:
                offsetX = 1;
                break;
            case SOUTH:
                offsetZ = -1;
                break;
            case NORTH:
            default:
                offsetZ = 1;
                break;
        }

        // only put the ladder on the wall (see above) if there is actually a wall
        for (int y = y1; y < y2; y++) {
            if (!isEmpty(x + offsetX, y, z + offsetZ)) {
                setBlock(x, y, z, Material.LADDER, direction);
            }
        }
    }

    public final void setTallBlock(int x, int y, int z, Material material) {
        setBlock(x, y, z, material, Half.BOTTOM);
        setBlock(x, y + 1, z, material, Half.TOP);
    }

    public final Block setBlock(int x, int y, int z, Material material, Half half) {
        Block block = getActualBlock(x, y, z);
        block.setType(material, false);
        BlockData data = block.getBlockData();
        try {
            if (data instanceof Bisected)
                ((Bisected) data).setHalf(half);
        } finally {
            block.setBlockData(data, doPhysics);
        }
        return block;
    }

    public final Block setBlock(int x, int y, int z, Material material, BlockFace facing, Half half) {
        Block block = getActualBlock(x, y, z);
        block.setType(material, false);
        BlockData data = block.getBlockData();
        try {
            if (data instanceof Directional)
                ((Directional) data).setFacing(facing);
            if (data instanceof Bisected)
                ((Bisected) data).setHalf(half);
        } finally {
            block.setBlockData(data, doPhysics);
        }
        return block;
    }

    public final void setBlocks(int x1, int x2, int y, int z1, int z2, Material material, BlockFace facing) {
        for (int x = x1; x < x2; x++)
            for (int z = z1; z < z2; z++)
                setBlock(x, y, z, material, facing);
    }

    public final void setBlocks(int x1, int x2, int y, int z1, int z2, Material material, BlockFace facing, Half half) {
        for (int x = x1; x < x2; x++)
            for (int z = z1; z < z2; z++)
                setBlock(x, y, z, material, facing, half);
    }

    public final void setBlocks(int x1, int x2, int y, int z1, int z2, Material material, Half half) {
        for (int x = x1; x < x2; x++)
            for (int z = z1; z < z2; z++)
                setBlock(x, y, z, material, half);
    }

    public final void setChest(CityWorldGenerator generator, int x, int y, int z, BlockFace facing, Odds odds, LootProvider lootProvider, LootLocation lootLocation) {
        Block block = setBlock(x, y, z, Material.CHEST, facing);
        connectDoubleChest(x, y, z, facing);
        if (isType(block, Material.CHEST))
            lootProvider.setLoot(generator, odds, world.getName(), lootLocation, block);
    }

    public final void setDoubleChest(CityWorldGenerator generator, int x, int y, int z, BlockFace facing, Odds odds, LootProvider lootProvider, LootLocation lootLocation) {
        switch (facing) {
            default:
            case EAST:
            case WEST:
                if (z == 15) // Whoops, too far
                    z = 14;
                setChest(generator, x, y, z, facing, odds, lootProvider, lootLocation);
                setChest(generator, x, y, z + 1, facing, odds, lootProvider, lootLocation);
                break;
            case NORTH:
            case SOUTH:
                if (x == 15) // Whoops, too far
                    x = 14;
                setChest(generator, x, y, z, facing, odds, lootProvider, lootLocation);
                setChest(generator, x + 1, y, z, facing, odds, lootProvider, lootLocation);
                break;
        }
    }

    public final void connectDoubleChest(int x, int y, int z, BlockFace facing) {
        Block block = getActualBlock(x, y, z);
        if (!isType(block, Material.CHEST)) {
            return;
        }
        if (((Chest) block.getBlockData()).getType() != Chest.Type.SINGLE) {
            return;
        }
        Block checkLeftBlock, checkRightBlock;
        switch (facing) {
            default:
            case EAST:
                checkLeftBlock = z > 0 ? getActualBlock(x, y, z - 1) : null;
                checkRightBlock = z < 15 ? getActualBlock(x, y, z + 1) : null;
                break;
            case SOUTH:
                checkLeftBlock = x < 15 ? getActualBlock(x + 1, y, z) : null;
                checkRightBlock = x > 0 ? getActualBlock(x - 1, y, z) : null;
                break;
            case WEST:
                checkLeftBlock = z < 15 ? getActualBlock(x, y, z + 1) : null;
                checkRightBlock = z > 0 ? getActualBlock(x, y, z - 1) : null;
                break;
            case NORTH:
                checkLeftBlock = x > 0 ? getActualBlock(x - 1, y, z) : null;
                checkRightBlock = x < 15 ? getActualBlock(x + 1, y, z) : null;
                break;
        }
        Chest blockData;
        if (checkLeftBlock != null && isType(checkLeftBlock, Material.CHEST) && ((Chest) checkLeftBlock.getBlockData()).getFacing() == facing) {
            blockData = (Chest) block.getBlockData();
            Chest checkLeftBlockData = (Chest) checkLeftBlock.getBlockData();
            blockData.setType(Chest.Type.RIGHT);
            checkLeftBlockData.setType(Chest.Type.LEFT);
            block.setBlockData(blockData);
            checkLeftBlock.setBlockData(checkLeftBlockData);
        } else if (checkRightBlock != null && isType(checkRightBlock, Material.CHEST) && ((Chest) checkRightBlock.getBlockData()).getFacing() == facing) {
            blockData = (Chest) block.getBlockData();
            Chest checkRightBlockData = (Chest) checkRightBlock.getBlockData();
            blockData.setType(Chest.Type.LEFT);
            checkRightBlockData.setType(Chest.Type.RIGHT);
            block.setBlockData(blockData);
            checkRightBlock.setBlockData(checkRightBlockData);
        }
    }

    public final void setWallSign(int x, int y, int z, BlockFace facing, String... lines) {
        Block block = getActualBlock(x, y, z);
        block.setType(Material.WALL_SIGN, false);
        BlockData data = block.getBlockData();
        try {
            if (data instanceof Directional)
                ((Directional) data).setFacing(facing);

            BlockState state = block.getState();
            if (state instanceof Sign) {
                Sign signState = (Sign) state;
                for (int i = 0; i < lines.length && i < 4; i++)
                    signState.setLine(i, lines[i]);
                state.update();
            }
        } finally {
            block.setBlockData(data, doPhysics);
        }
    }


    public final void setSignPost(int x, int y, int z, BlockFace rotation, String... lines) {
        Block block = getActualBlock(x, y, z);
        block.setType(Material.SIGN, false);
        BlockData data = block.getBlockData();
        try {
            if (data instanceof Rotatable)
                ((Rotatable) data).setRotation(rotation);

            BlockState state = block.getState();
            if (state instanceof Sign) {
                Sign signState = (Sign) state;
                for (int i = 0; i < lines.length && i < 4; i++)
                    signState.setLine(i, lines[i]);
                state.update();
            }
        } finally {
            block.setBlockData(data, doPhysics);
        }
    }

    public final Block setLeaf(int x, int y, int z, Material material, boolean isPersistent) {
        Block block = getActualBlock(x, y, z);
        block.setType(material, false);
        BlockData data = block.getBlockData();
        try {
            if (data instanceof Leaves)
                ((Leaves) data).setPersistent(isPersistent);
        } finally {
            block.setBlockData(data, doPhysics);
        }
        return block;
    }

    public final void setBed(int x, int y, int z, BlockFace facing) {
        Block bedHeadBlock, bedFootBlock;
        Material bedMaterial = Material.RED_BED;

        switch (facing) {
            default:
            case EAST:
                bedHeadBlock = getActualBlock(x, y, z);
                bedFootBlock = getActualBlock(x + 1, y, z);
                break;
            case SOUTH:
                bedHeadBlock = getActualBlock(x, y, z);
                bedFootBlock = getActualBlock(x, y, z + 1);
                break;
            case WEST:
                bedHeadBlock = getActualBlock(x + 1, y, z);
                bedFootBlock = getActualBlock(x, y, z);
                break;
            case NORTH:
                bedHeadBlock = getActualBlock(x, y, z + 1);
                bedFootBlock = getActualBlock(x, y, z);
                break;
        }

        bedHeadBlock.setType(bedMaterial, doPhysics);
        bedFootBlock.setType(bedMaterial, doPhysics);
        Bed bedHeadData = (Bed) bedHeadBlock.getBlockData();
        Bed bedFootData = (Bed) bedFootBlock.getBlockData();
        bedHeadData.setFacing(facing.getOppositeFace());
        bedFootData.setFacing(facing.getOppositeFace());
        bedHeadData.setPart(Bed.Part.HEAD);
        bedFootData.setPart(Bed.Part.FOOT);
        bedHeadBlock.setBlockData(bedHeadData);
        bedFootBlock.setBlockData(bedFootData);
    }

}
