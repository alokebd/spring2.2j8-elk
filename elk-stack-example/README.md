# elk-stack-logging (Microservices Centralized Logging): 
Microservice Centralized Logging 
*Make sure these 3 components (elastic search, Kibana and Logstash are in same version).
*For understanding, instead of latest Elasticsearch, use 7.6.x version to miminize security configuration feature as it has default security feature disabled.

# 1.Elastic Search [Download](https://www.elastic.co/downloads/elasticsearch). 

NOTE: 
*Starting in Elasticsearch 8.0, security is enabled by default. 
The first time you start Elasticsearch, TLS encryption is configured automatically, a password is generated for the `elastic` user, and a Kibana enrollment token is created so you can connect Kibana to your secured cluster.

*Save all information (user, password, cert, token from the running trace for first time), which will require first time for configuring Kibina with elastic search.

*To regenerate token (for 8.x) run following command in command line:
elasticsearch-create-enrollment-token -s kibana --url https://172.22.224.1:9200


1.1) run C:\Porjects\<elasticsearch>\bin\elasticsearch
1.2) Check in browser (http://localhost:9200)
NOTE - will return values, example:
{
  "name" : "DASALOKE-17HPWI",
  "cluster_name" : "elasticsearch",
  "cluster_uuid" : "3E9DDuWtSUyG2XkmatHOHg",
  "version" : {
    "number" : "7.6.2",
    "build_flavor" : "default",
    "build_type" : "zip",
    "build_hash" : "ef48eb35cf30adf4db14086e8aabd07ef6fb113f",
    "build_date" : "2020-03-26T06:34:37.794943Z",
    "build_snapshot" : false,
    "lucene_version" : "8.4.0",
    "minimum_wire_compatibility_version" : "6.8.0",
    "minimum_index_compatibility_version" : "6.0.0-beta1"
  },
  "tagline" : "You Know, for Search"
}

# 2.Kibana [Download](https://artifacts.elastic.co/downloads/logstash/logstash-7.6.2.zip).
* go to Kibana config dirctory (C:\Porjects\<kibana>\config) update kibana.yml for elasticsearch.hosts as below
elasticsearch.hosts:["http://localhost:9200"]

2.1). Run kibana (/bin/kibana.bat)
NOTE: make sure Elstic Search is started before running Kibina

2.2). Copy the URL from the running window (eg. http://localhost:5601/?code=<your code>)

2.3). Use the token and click on Configure Elastic (Kibana will automatically set up everything and will connect over TLS to Elasticsearch.)

2.4). After configuration completed, log in with your specified user and password

# 3.Logstash [Download](https://www.elastic.co/downloads/kibana).
* create logstash.conf(https://www.elastic.co/guide/en/logstash/current/configuration.html)
and add it in bin directory. If there is no cusom index configured (index=logstsh_example), logstash will create a default.
Example:

input {
  file {
    path => "C:/Porjects/application-log/elk/elk-stack.log"
    start_position => "beginning"
  }
}
output {
  elasticsearch {
    hosts => ["localhost:9200"]
	index=logstsh_example
  }
  stdout { codec => rubydebug }
}

3.1). Run logstash.bat -f logstash.conf (bin director)

* Example below (http://localhost:9200/_cat/indices) where logstash-2024.05.17-000001 is created.

yellow open logstash-2024.05.17-000001 CwQeRhgASpiCe-D40UHd0g 1 1 19 0 44.5kb 44.5kb
green  open .kibana_task_manager_1     Rl7K0ctdQeuYY13XuvvcMA 1 0  2 0 49.2kb 49.2kb
green  open ilm-history-1-000001       EVPiXkDlRSqyeh4mxX4_pQ 1 0  6 0 15.6kb 15.6kb
green  open .apm-agent-configuration   fgT9wbJfSxm1P9rkbTgw2A 1 0  0 0   283b   283b
green  open .kibana_1                  oz4RoSFQQBqmRTfAZFUbSA 1 0  3 0 23.9kb 23.9kb


* Running logstash console also have all logging informaiton 

# 4. Run Microservice application
4.1). add log location as mentioned in logtash in application.yml as below.
spring:
  application:
    name: ELK-STACK-EXAMPLE

server:
  port: 9898

logging:
  file: C:/Porjects/app-log/elk-stack.log

4.2). Configure the logging location in micorservice 

4.3). Check logstash console and check the logging information as tested from API

4.5). Check your inoformation in Elastic as example below

# 5. Run Microservice application (ELK components are also running) and test
1). localhost:9200/_cat (for index from Elastic search) 
2). localhost:9200/_cat/indices
3). localhost:9200/<your indices>/_search

5.1). Go to your Kiban home page (http://localhost:5601/app/kibna#/home),
* Create Index pattern from Managment Consle >Kibana> Index Pattern (Create Index Pattern).


5.2). http://localhsot:9898/api/elk/users/1 (test with some user id which is not present).
* verify your logging information in browser
* localhost:9200/_cat (for index from Elastic search) 
* localhost:9200/_cat/indices
* localhost:9200/<your indices>/_search

5.3). Go to Discovery page on Kibana (http://localhost:5601) page and check 


