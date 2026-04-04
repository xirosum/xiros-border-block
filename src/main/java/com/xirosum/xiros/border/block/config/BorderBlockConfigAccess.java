package com.xirosum.xiros.border.block.config;

import com.xirosum.xiros.border.block.XirosBorderBlock;

public final class BorderBlockConfigAccess {
    private static BorderBlockConfig config;
    private static boolean initialized = false;

    private BorderBlockConfigAccess() {
    }

    public static BorderBlockConfig get() {
        if (!initialized) {
            bootstrap();
        }
        return config;
    }

    public static void bootstrap() {
        if (initialized) {
            return;
        }
        
        config = ConfigManager.loadConfig();
        initialized = true;
        XirosBorderBlock.LOGGER.info("Border Block config loaded successfully");
    }
}

