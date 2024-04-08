# elk-stack-logging: 
Microservice Centralized Logging 
###### Download & Run
# 1.Elastic Search [Download](https://www.elastic.co/downloads/elasticsearch).
a) run C:\Porjects\elasticsearch-8.13.1\bin\elasticsearch

NOTE: 
1) Starting in Elasticsearch 8.0, security is enabled by default. 
The first time you start Elasticsearch, TLS encryption is configured automatically, a password is generated for the `elastic` user, 
and a Kibana enrollment token is created so you can connect Kibana to your secured cluster.

2). Save all information (user, password, cert, token from the running trace for first time), which will require first time for configuring 
Kibina with elastic search.

NOTE:
1). To regenerate token: 
elasticsearch-create-enrollment-token -s kibana --url https://172.22.224.1:9200


# 2.Kibana [Download](https://artifacts.elastic.co/downloads/logstash/logstash-7.6.2.zip).
a) go to Kibana config dirctory (C:\Porjects\kibana-8.13.1\config) update kibana.yml for elasticsearch.hosts as below

elasticsearch.hosts:["http://localhost:9200"]

b). Run kibana (/bin/kibana.bat)
NOTE: make sure Elstic Search is started before running Kibina

c). Copy the URL from the running window (eg. http://localhost:5601/?code=<your code>)

d). Use the token and click on Configure Elastic (Kibana will automatically set up everything and will connect over TLS to Elasticsearch.)

e). After configuration completed, log in with your specified user and password



# 2.Logstash [Download](https://www.elastic.co/downloads/kibana).
a). create logstash.conf(https://www.elastic.co/guide/en/logstash/current/configuration.html)
and add it in bin directory.
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
  }
  stdout { codec => rubydebug }
}

b). Run logstash.bat -f logstash.conf (bin director)

# 3. Run Microservice application and test
a). add log location as mentioned in logtash in application.yml as below.
spring:
  application:
    name: ELK-STACK-EXAMPLE

server:
  port: 9898

logging:
  file: C:/Porjects/app-log/elk-stack.log

b). Configure the logging location in micorservice 

c). Check logstash console and check the logging information as tested from API

d) check your inoformation in Elastic as example below

1). localhost:9200/_cat (for index from Elastic search) 
2). localhost:9200/_cat/indices
3). localhost:9200/<your indices>/_search

e). Go to your Kiban home page (http://localhost:5601/app/kibna#/home)


f). http://localhsot:9898/api/elk/users/1 (test with some user id which is not present).
g). verify your logging information in browser
1). localhost:9200/_cat (for index from Elastic search) 
2). localhost:9200/_cat/indices
3). localhost:9200/<your indices>/_search



h) check on broser as http://localhost:9200/ 
NOTE - will return values, example:
{
  "name" : "DASALOKE-17HPWI",
  "cluster_name" : "elasticsearch",
  "cluster_uuid" : "K6V9G_FmTx6zOd4E6K5rQA",
  "version" : {
    "number" : "8.13.1",
    "build_flavor" : "default",
    "build_type" : "zip",
    "build_hash" : "9287f29bba5e270bd51d557b8daccb7d118ba247",
    "build_date" : "2024-03-29T10:05:29.787251984Z",
    "build_snapshot" : false,
    "lucene_version" : "9.10.0",
    "minimum_wire_compatibility_version" : "7.17.0",
    "minimum_index_compatibility_version" : "7.0.0"
  },
  "tagline" : "You Know, for Search"
}

i). 

c). go to browser and check http://localhost:5601 (when it is ready eg.  Kibana is now available in Kibina log)
localhost:5601/app/kibana#/home

