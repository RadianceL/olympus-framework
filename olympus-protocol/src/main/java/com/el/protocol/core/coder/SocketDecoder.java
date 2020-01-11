package com.el.protocol.core.coder;

import com.el.protocol.entity.InterfaceTransformDefinition;
import com.el.protocol.utils.ProtostuffUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.nio.ByteBuffer;
import java.util.List;

/**
 * 解码
 * 2019/11/3
 *
 * @author eddielee
 */
public class SocketDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) {
        ByteBuffer byteBuffer = byteBuf.nioBuffer();
        int length = byteBuffer.limit();
        byte[] bytes = new byte[length];
        byteBuffer.get(bytes);
        InterfaceTransformDefinition deserialize = ProtostuffUtil.deserialize(bytes, InterfaceTransformDefinition.class);
        list.add(deserialize);
        byteBuf.skipBytes(byteBuf.readableBytes());
    }
}
