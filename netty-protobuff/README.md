1. Generate Java Classes from *.proto file.
   
    a. got to 
    
    
    \\netty-protobuff\proto-messages 
    
   b. run  bellow commands   
    
    protoc --proto_path=src/protos --java_out=src/main/generated src/protos/Server.proto
    protoc --proto_path=src/protos --java_out=src/main/generated src/protos/Client.proto
   
 2. run the main() of MyNettryServer
 
 3. run the main() of MyNettryClient