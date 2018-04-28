package com.wangzhixuan.commons.utils;

/**
 * 工具类
 * @author L.cm
 */
public class RuntimeUtils {
    
    /**
     * 运行shell
     * @param script
     */
    public static void runShell(String script) {
        Process process = null;
        try {
            String[] cmd = { "sh", script };
            //执行liunx命令
            process = Runtime.getRuntime().exec(cmd);
            process.waitFor();
        } catch (Exception e) {
        } finally {
            if (null != process) {
                process.destroy();
            }
        }
    }

}
