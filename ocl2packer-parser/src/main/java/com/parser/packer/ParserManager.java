package com.parser.packer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;


/**
 * @Description:
 * @ClassName: ParserMananer
 * @Author: yy
 * @Date: 2023/1/29 9:19
 * @Version: 1.0
 */
public class ParserManager {

    private final static Map<String,ParserInfo> registeredParsers = new HashMap<>();

    static {
        loadInitialParsers();
        System.out.println("Ocl2PackerParser initialized");
    }

    /**
     *@Description: 类加载时执行
     *@Param: []
     *@Return: void
     *@DateTime: 9:37 2023/1/29
     */
    public static void loadInitialParsers(){
        ServiceLoader<Ocl2PackerParser> packerParsers = ServiceLoader.load(Ocl2PackerParser.class);
        Iterator<Ocl2PackerParser> driversIterator = packerParsers.iterator();
        try{
            while(driversIterator.hasNext()) {
                driversIterator.next();
            }
        } catch(Throwable t) {
            // Do nothing
        }

    }

    /**
     *@Description: 注册解析器,在plugins层类加载时执行
     *@Param: [ocl2PackerParser]
     *@Return: void
     *@DateTime: 9:42 2023/1/29
     */
    public static synchronized void registerParser(Ocl2PackerParser ocl2PackerParser) {
        registeredParsers.put(ocl2PackerParser.cloudType(),new ParserInfo(ocl2PackerParser));
    }

    /**
     *@Description: 获取hcl文件
     *@Param: []
     *@Return: java.lang.String
     *@DateTime: 9:42 2023/1/29
     */
    public static String getHcl(Provisioner provisioner) {
        try {
            ParserInfo parserInfo = registeredParsers.get(provisioner.getCloudType());
            String hcl = parserInfo.ocl2PackerParser.getHclImages(provisioner);
            if (!hcl.isEmpty()) {
                return hcl;
            }
        } catch (Exception e) {
            //wrong parser, ignored it.
        }
            return "111111";
    }

}
