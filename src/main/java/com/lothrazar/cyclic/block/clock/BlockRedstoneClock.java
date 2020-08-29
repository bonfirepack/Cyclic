package com.lothrazar.cyclic.block.clock;

import com.lothrazar.cyclic.base.BlockBase;
import com.lothrazar.cyclic.registry.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BlockRedstoneClock extends BlockBase {

  public BlockRedstoneClock(Properties properties) {
    super(properties.hardnessAndResistance(1.8F));
    this.setDefaultState(this.stateContainer.getBaseState().with(IS_LIT, Boolean.valueOf(false)));
    this.setHasGui();
  }

  @Override
  public int getWeakPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
    return blockState.get(IS_LIT) ? getPower(blockAccess, pos, side.getOpposite()) : 0;
  }

  @Override
  public int getStrongPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
    return blockState.get(IS_LIT) ? getPower(blockAccess, pos, side.getOpposite()) : 0;
  }

  private int getPower(IBlockReader world, BlockPos pos, Direction side) {
    TileRedstoneClock tile = (TileRedstoneClock) world.getTileEntity(pos);
    return tile.getPower();
  }

  @Override
  public boolean canProvidePower(BlockState state) {
    return true;
  }

  @Override
  public boolean hasTileEntity(BlockState state) {
    return true;
  }

  @Override
  public TileEntity createTileEntity(BlockState state, IBlockReader world) {
    return new TileRedstoneClock();
  }

  @Override
  @OnlyIn(Dist.CLIENT)
  public void registerClient() {
    //    ClientRegistry.bindTileEntityRenderer(BlockRegistry.Tiles.harvesterTile, RenderHarvester::new);
    ScreenManager.registerFactory(BlockRegistry.ContainerScreenRegistry.clock, ScreenClock::new);
  }

  @Override
  protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
    builder.add(IS_LIT);
  }
}
