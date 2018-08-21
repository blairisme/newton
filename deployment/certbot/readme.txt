=============================================
Steps to generate an SSL certificate keystore
=============================================

1) Run docker image (Kitematic + keep stdin open)

2) Use Lets Encrypt to generate a certificate (requires adding resources to master)
    ./certbot-auto certonly --manual -d blairbutterworth.com -d www.blairbutterworth.com

3) Convert certificat in PEM format into PKCS12 format
    openssl pkcs12 -export -in fullchain.pem -inkey privkey.pem -out jetty.pkcs12 -name jetty -CAfile chain.pem -caname root

4) Jetty requires this for some reason...
    keytool -importkeystore -srckeystore jetty.pkcs12 -srcstoretype PKCS12 -destkeystore newton.keystore

5) Jetty also requires that the certificate inside the keystore has a password (if it moans about the same password, run twice):
    keytool -keypasswd -keystore newton.keystore -alias jetty -storetype JKS
