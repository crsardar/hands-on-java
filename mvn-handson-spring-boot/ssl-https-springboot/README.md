Generate A Self Signed Key

    keytool -genkeypair -alias chittaranjan -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore my_key.p12 -validity 3650
    

Password 
    
    Chitta
    
Copy "my_pey.p12" 
 
    //ssl-https-springboot/src/main/resources/keystore
    
Add details in - 
    
    //ssl-https-springboot/src/main/resources/application.properties


Run main method in SSLApp.java

Hit either URLs - 

    https://127.0.0.1:8443/hello-ssl
    http://127.0.0.1:8080/hello-ssl