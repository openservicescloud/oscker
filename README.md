# oscker

Oscker aim to make a generic descriptor to make images for different CSPs.

Packer needs real resource replacement demo to execute hcl
        source "huaweicloud-ecs" "demo"{
            vpc_id             = "demo"     
            subnets            = ["demo"]   
            security_groups    = ["demo"]   
        }
        build {
        sources = ["demo"]      
        }
    
