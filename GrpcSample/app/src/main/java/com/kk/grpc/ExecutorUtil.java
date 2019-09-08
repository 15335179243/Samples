package com.kk.grpc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池工具类
 * @author wangzhi
 */
public class ExecutorUtil {
	
	public static final ExecutorService FIXED_POOL_SERVICE = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	
	public static final ExecutorService SINGLE_SERVICE = Executors.newSingleThreadExecutor();
	
	public static void exec(Runnable run) {
		FIXED_POOL_SERVICE.execute(run);
	}
	
	public static void execSingle(Runnable run) {
		SINGLE_SERVICE.execute(run);
	}
}
