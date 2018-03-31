# BakaAPI
A Minecraft Server API Framework

## Examples

![examples.png](https://i.loli.net/2018/01/15/5a5cc68f30858.png)

## Config Samples
```
port: 8000 #Http API Port
auth: true #Use Authorize
password: "baka2333" #Authorize Password
```

## Source Tree
```
.
├── lib
│   ├── PaperSpigot-latest.jar
│   ├── gson-2.6.2.jar
│   └── nanohttpd-2.2.0.jar
└── src
    ├── config.yml  #Plugin Config
    ├── moe
    │   └── satori
    │       └── BakaAPI
    │           ├── App.java  # Http Service Class
    │           ├── Controller #API Controller Class
    │           │   └── Players.java
    │           ├── Main.java  #Plugins Main Class
    │           └── Utils.java #Util Class
    └── plugin.yml  #Plugin Base Config
    
```

## Authorize Demo
### Please First `composer require rmccue/requests`
```
<?php
include 'vendor/autoload.php';
$api = "http://127.0.0.1:8000/";
$password = "baka2333";
$arr = [
	"action" => "Players",
	"method" => "kickPlayer",
	"username" => "KagurazakaSatori",
	"content" => "Meow: " . time(),
];
function getSign($arr, $secret) {
	ksort($arr);
	return strtoupper(md5(http_build_query($arr) . "@" . $secret));
}
$sign = getSign($arr, $password);
$headers = [
	"X-AuthorizeToken" => $sign,
];

$result = Requests::get($api . "?" . http_build_query($arr), $headers);
print_r($result->body);
```
