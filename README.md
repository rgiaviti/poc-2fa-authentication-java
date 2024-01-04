# POC 2FA

## Requests Examples

The secret key need to be registered in Google Auth or Authy.

````shell
curl --request GET \
--url 'http://localhost:8080/2fa/secretKey'
````


```shell
curl --request POST \
--url http://localhost:8080/2fa/match/KK3JIOEKUI6MDTVXJ33E5DLZHOA32SKX \
--header 'content-type: application/json' \
--data '{
"code": "762730"
}'
```