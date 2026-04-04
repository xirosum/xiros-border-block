package com.xirosum.xiros.border.block.config;

import com.xirosum.xiros.border.block.XirosBorderBlock;
import infinituum.fastconfigapi.FastConfigs;

import java.lang.reflect.Method;

public final class BorderBlockConfigAccess {
    private static boolean initAttempted = false;

    private BorderBlockConfigAccess() {
    }

    public static BorderBlockConfig get() {
        try {
            return FastConfigs.get(BorderBlockConfig.class);
        } catch (RuntimeException firstError) {
            initializeFastConfigs();
            return FastConfigs.get(BorderBlockConfig.class);
        }
    }

    public static void bootstrap() {
        initializeFastConfigs();
        FastConfigs.get(BorderBlockConfig.class);
    }

    private static synchronized void initializeFastConfigs() {
        if (initAttempted) {
            return;
        }

        initAttempted = true;

        try {
            Method init = FastConfigs.class.getMethod("init");
            init.invoke(null);
            XirosBorderBlock.LOGGER.info("Explicitly initialized FastConfigAPI before first config access");
        } catch (NoSuchMethodException ignored) {
            XirosBorderBlock.LOGGER.debug("FastConfigs.init() not present in current FastConfigAPI version");
        } catch (ReflectiveOperationException exception) {
            XirosBorderBlock.LOGGER.warn("Failed to explicitly initialize FastConfigAPI", exception);
        }
    }
}

