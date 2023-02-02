
# Oscker

Oscker aim to make a generic descriptor to make images for different CSPs.

## Run

Specify the cloud vendor and basic image by calling the getHclScript method in Main to generate the hcl script executed by packer

### input param

```json
{
    "cloudType": "huaweicloud",
    "base_image": "Ubuntu 20.04 server 64bit",
    "version": "latest",
    "image_name": "my-image-demo",
    "type": "shell",
    "environments": [
      "WORK_HOME=/usr1/KAFKA/"
    ],
    "inline": [
      "echo \"start install docker\"", 
      "echo \"run install\"", "apt-get update", 
      "apt install -y docker.io", 
      "docker network create app-tier --driver bridge", 
      "docker run -d --name zookeeper-server --network app-tier -e ALLOW_ANONYMOUS_LOGIN=yes bitnami/zookeeper:latest", 
      "docker run -d --name kafka-server --network app-tier -e ALLOW_PLAINTEXT_LISTENER=yes -e KAFKA_CFG_ZOOKEEPER_CONNECT=zookeeper-server:2181 bitnami/kafka:latest", 
      "echo \"@reboot docker restart zookeeper-server ; docker container rm kafka-server ; docker run -d --name kafka-server --network app-tier -p 9092:9092  -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://\\`hostname\\`:9092 -e KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9092 -e ALLOW_PLAINTEXT_LISTENER=yes -e KAFKA_CFG_ZOOKEEPER_CONNECT=zookeeper-server:2181 bitnami/kafka:latest\" | crontab -"
    ]
}
```

### output

packer {
    required_plugins {
        huaweicloud-ecs = {
            version = ">= 0.4.0"
            source = "github.com/huaweicloud/huaweicloud"
        }
    }
}
variable "region_name" {
    type = string
    default = env("HW_REGION_NAME")
}
variable "secret_key" {
    type = string
    default = env("HW_SECRET_KEY")
}
variable "access_key" {
    type = string
    default = env("HW_ACCESS_KEY")
}
source "huaweicloud-ecs" "ecs_name" {
    region             = var.region_name
    image_name         = "my-image-demo"
    access_key         = var.access_key
    secret_key         = var.secret_key
    eip_bandwidth_size = "5"
    eip_type           = "5_sbgp"
    flavor             = "s6.large.2"
    instance_name      = "my-image-demo"
    vpc_id             = "vpc_id"
    subnets            = ["subnets"]
    security_groups    = ["security_groups"]
    source_image_filter {
        filters {
            name = "Ubuntu 20.04 server 64bit"
            visibility = "public"
        }
        most_recent = true
    }
    ssh_ip_version     = "4"
    ssh_username       = "root"
}

build {
    sources = ["source.huaweicloud-ecs.ecs_name"]
    provisioner "shell" {
        pause_before = "30s"
        inline       = ["echo \"start install docker\"", "echo \"run install\"", "apt-get update", "apt install -y docker.io", "docker network create app-tier --driver bridge", "docker run -d --name zookeeper-server --network app-tier -e ALLOW_ANONYMOUS_LOGIN=yes bitnami/zookeeper:latest", "docker run -d --name kafka-server --network app-tier -e ALLOW_PLAINTEXT_LISTENER=yes -e KAFKA_CFG_ZOOKEEPER_CONNECT=zookeeper-server:2181 bitnami/kafka:latest", "echo \"@reboot docker restart zookeeper-server ; docker container rm kafka-server ; docker run -d --name kafka-server --network app-tier -p 9092:9092  -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://\\`hostname\\`:9092 -e KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9092 -e ALLOW_PLAINTEXT_LISTENER=yes -e KAFKA_CFG_ZOOKEEPER_CONNECT=zookeeper-server:2181 bitnami/kafka:latest\" | crontab -"]
    }
}


### Packer needs real resource replacement demo to execute hcl

source "huaweicloud-ecs" "demo" {
    vpc_id             = "demo"
    subnets            = ["demo"]
    security_groups    = ["demo"]
}
build {
    sources = ["demo"]      
}


## Specify different cloud vendors in the input to obtain the corresponding scripts

* for Huaweicloud:
  cloudType:huaweicloud
* for k8s:
  cloudType:k8s
* for openstack:
  cloudType:openstack
    