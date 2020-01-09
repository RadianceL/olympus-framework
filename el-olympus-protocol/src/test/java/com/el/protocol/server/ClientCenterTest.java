package com.el.protocol.server;

import com.el.protocol.entity.InterfaceTransformDefinition;
import com.el.protocol.entity.ServiceDefinition;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * since 2019/11/30
 *
 * @author eddie
 */
class ClientCenterTest {

    ClientCenter abstractClientCenter = new ClientCenter();

    @Test
    void run() {
        abstractClientCenter.run("127.0.0.1", 8001);
        InterfaceTransformDefinition interfaceTransformDefinition = new InterfaceTransformDefinition();
        interfaceTransformDefinition.setClassName("123");
        abstractClientCenter.sendMessage(interfaceTransformDefinition);
    }
}