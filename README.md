# Media Service to Write WebRTC Streams

The protected backend (protected by JWT authentication) which makes the calls to OpenVidu service.

## Running

First step is to run OpenVidu service. The second step is to run the media service itself. 
To do this please follow the instructions below.  

### Run OpenVidu service

You have a proper service defined in `docker-compose.yml` configuration.

Please make sure you have a keystore generated before run OpenVidu service.
In the case you haven't, run to generate a new keystore:
```
$ keytool -genkey -keyalg RSA -alias selfsigned -keystore data/keystore/keystore.jks -storepass password -validity 360 -keysize 2048
```
Also you need to setup your browser to accept the self-signed certificates.
Here's working solution for Chromium [https://stackoverflow.com/a/31900210/5556633](https://stackoverflow.com/a/31900210/5556633)

You also can find some useful info by links:
* [https://openvidu.io/docs/deployment/custom-certificate/](https://openvidu.io/docs/deployment/custom-certificate/)
* [http://doc-kurento.readthedocs.io/en/stable/features/security.html](http://doc-kurento.readthedocs.io/en/stable/features/security.html)

Then you can run up the `openvidu` service:
```
$ MY_UID=$(id -u) docker-compose up openvidu -d
```

### Run media service

Please make sure you have generated the RSA key pair to verify/sign the JWT tokens (located in `classpath:/jwt`).
You can generate a new key pair by running:
```
$ openssl genrsa -out private_key.pem 4096
$ openssl rsa -pubout -in private.pem -out public.pem

# convert private key to pkcs8 format in order to import it from Java
$ openssl pkcs8 -topk8 -in private.pem -inform pem -out private_pkcs8.pem -outform pem -nocrypt
```

Right now you can run this as a regular Spring Boot application.

An access to the service endpoints is protected by JWT authentication. 
See `*.component.jwt.Auth0JwtManager.java` class for more details.
