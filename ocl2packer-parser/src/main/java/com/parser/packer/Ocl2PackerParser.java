package com.parser.packer;

/**
 * @Description:
 * @Param:
 * @Return:
 * @DateTime: 11:21 2023/2/1
 */
public interface Ocl2PackerParser {

    String type();

    String getHclImages(Provisioner provisioner, String cloudType);

}
