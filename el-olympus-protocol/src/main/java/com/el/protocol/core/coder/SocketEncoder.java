package com.el.protocol.core.coder;

import com.el.protocol.entity.InterfaceTransformDefinition;
import com.el.protocol.utils.ProtostuffUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * 编码
 * 2019/11/3
 *
 * @author eddielee
 */
@Slf4j
public class SocketEncoder extends MessageToByteEncoder<InterfaceTransformDefinition> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, InterfaceTransformDefinition o, ByteBuf byteBuf) throws Exception {
        if (Objects.isNull(o)){
            log.error("远程调用接口： 发送对象为空");
            return;
        }
        byte[] serialized = ProtostuffUtil.serialize(o);
        byteBuf.writeBytes(serialized);
    }
}
