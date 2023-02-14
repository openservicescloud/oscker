package com.parser.packer;

import java.util.List;
import lombok.Data;

/**
 * @Description:
 * @Param:
 * @Return:
 * @DateTime: 11:46 2023/2/1
 */
@Data
public class Provisioner {

    private String base_image;
    private String image_name;
    private List<String> inline;

}
