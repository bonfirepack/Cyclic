/*******************************************************************************
 * The MIT License (MIT)
 * 
 * Copyright (C) 2014-2018 Sam Bassett (aka Lothrazar)
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package com.lothrazar.cyclicmagic.playerupgrade;

import com.lothrazar.cyclicmagic.ModCyclic;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketOpenGuiOnServer implements IMessage, IMessageHandler<PacketOpenGuiOnServer, IMessage> {

  private int guiId;

  public PacketOpenGuiOnServer() {}

  public PacketOpenGuiOnServer(int guiId) {
    this.guiId = guiId;
  }

  @Override
  public void fromBytes(ByteBuf buf) {
    NBTTagCompound tags = ByteBufUtils.readTag(buf);
    guiId = tags.getInteger("gui");
  }

  @Override
  public void toBytes(ByteBuf buf) {
    NBTTagCompound tags = new NBTTagCompound();
    tags.setInteger("gui", guiId);
    ByteBufUtils.writeTag(buf, tags);
  }

  @Override
  public IMessage onMessage(PacketOpenGuiOnServer message, MessageContext ctx) {
    ModCyclic.proxy.getThreadFromContext(ctx).addScheduledTask(() -> {
    ctx.getServerHandler().player.openGui(ModCyclic.instance, message.guiId, ctx.getServerHandler().player.getEntityWorld(),
        (int) ctx.getServerHandler().player.posX,
        (int) ctx.getServerHandler().player.posY,
        (int) ctx.getServerHandler().player.posZ);
    });
    return null;
  }
}
