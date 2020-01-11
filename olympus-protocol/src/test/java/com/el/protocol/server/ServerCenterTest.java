package com.el.protocol.server;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * since 2019/11/30
 *
 * @author eddie
 */
class ServerCenterTest {

    @Test
    void run() {
        ServerCenter serverCenter = new ServerCenter();
        serverCenter.run(8001);
    }
}