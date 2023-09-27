
# Run this shell script to hit massive call to CACHE data

URL="http://localhost:8600/api/v1/posts"
TOKEN="eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoidXNlcjEiLCJleHAiOjE2OTgxNTQ3MTEsImlhdCI6MTY5NTU2MjcxMSwic2NvcGUiOiJSRUZSRVNIRUQifQ.c7Weyjj-zSx4RppYiKptrthCO9kyDn52HbBuak0WR025edXDFKzvRKew7GggCK8yaFpErtDuN3CJUP51Zn8M7xNLRuM4zMLohSgFameZTPOH1LoghpaRYA7eFZFaUKwq-KszTKbhJtU-6M3gDKUv_uZLDn85LNZGPf7YDmo_fI-VZOEVIvb8a8c6C6yLIDqpZskWS0_3c4IFyJOqrmsL4AzU3wyWjB-1aoFQYWJD_Ehy-yth-8xd7OuMqbU7NQQvemsNwx_p6VKtl-hyyO84ZkzxfBfII_0impPRoGLr_voj5G6PUp9s8tC4Nx_3XQe2l1RAOfSSK31qfVFggeeq1w"

while true; do
    # Make the cURL request with the specified URL and token
    curl -X GET "$URL" -H "Authorization: Bearer $TOKEN"
    
    # Sleep for 0.5 seconds before the next iteration
    sleep 0.5
done