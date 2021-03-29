package com.hd.hse.padinterface;

import java.io.InputStream;
import java.util.Map;


/**
 * @author ZhangJie
 *
 */
public interface AndroidInterface {
    /**
     * @description
     * @author ZhangJie
     * @date 2014-8-20
     * @param type 对应业务实现类
     * @param parameters 需要其他参数时，按业务扩展
     * @param b 以字节数组上传文件时，使用；默认为null
     * @param is 以文件流形式上传文件时，使用；默认为null
     * @return 执行结果不同业务有不同格式，查看接口表
     * @throws Exception
     */
    public Object exchange(String type, Map<String, Object> parameters, byte[] b, InputStream is) throws Exception;
	/** 文件下载信息设置 */
	//每次下载量设置，没有必要修改
	public final int FILE_DOWNLOAD_SIZE = 20480;
	
}