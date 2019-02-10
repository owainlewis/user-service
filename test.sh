curl -X POST \
  -H 'Content-Type: application/json' \
  -d '{"first": "Owain", "last": "Lewis", "email": "owain@owainlewis.com"}' \
  localhost:8080/users

curl \
  -H 'Content-Type: application/json' \
  localhost:8080/users