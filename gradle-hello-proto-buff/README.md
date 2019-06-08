1. Generate Java Classes from *.proto file.
    run bellow command from the location of this file - 
    
    
    protoc --proto_path=src/proto --java_out=src/main/generated src/proto/ProtoContainer.proto
    
 2. run the main() in MyMain