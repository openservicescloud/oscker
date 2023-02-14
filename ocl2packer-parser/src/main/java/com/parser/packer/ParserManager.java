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

    private final static Map<String, ParserInfo> registeredParsers = new HashMap<>();

    static {
        loadInitialParsers();
        System.out.println("Ocl2PackerParser initialized");
    }

    /**
     * @Description: SPI loads all child parsers
     * @Param: []
     * @Return: void
     * @DateTime: 9:37 2023/1/29
     */
    public static void loadInitialParsers() {
        ServiceLoader<Ocl2PackerParser> packerParsers = ServiceLoader.load(Ocl2PackerParser.class);
        Iterator<Ocl2PackerParser> driversIterator = packerParsers.iterator();
        try {
            while (driversIterator.hasNext()) {
                driversIterator.next();
            }
        } catch (Throwable t) {
            // Do nothing
        }

    }

    /**
     * @Description: SPI registers when loading child parsers
     * @Param: [ocl2PackerParser]
     * @Return: void
     * @DateTime: 9:42 2023/1/29
     */
    public static synchronized void registerParser(Ocl2PackerParser ocl2PackerParser) {
        registeredParsers.put(ocl2PackerParser.type(), new ParserInfo(ocl2PackerParser));
    }

    /**
     * @Description: Get hcl file
     * @Param: []
     * @Return: java.lang.String
     * @DateTime: 9:42 2023/1/29
     */
    public static String getHcl(Provisioner provisioner, String cloudType) {
        try {
            ParserInfo parserInfo = registeredParsers.get(cloudType);
            String hcl = parserInfo.ocl2PackerParser.getHclImages(provisioner, cloudType);
            if (!hcl.isEmpty()) {
                return hcl;
            }
        } catch (Exception e) {
            //wrong parser, ignored it.
        }
        return "The hcl is empty ";
    }

}
