# github_proxy_empik

## Application
I used spring-boot framework with postgresql

## Database
I used PostgreSql in docker.

Go to postgresl directory and run "docker-compose up -d" to run docker image

Run "docker-compose down" to stop container

## Testing
I used 
- Spock (I like spock)
- TestContainers for database
- Wiremock for http connections

## Other
In real life I would consider changing logic for counter. 
I think that errors during connection to database could be ignored. 
business logic is to get data from API call to github and we could live with bad counter. 
Or we could send some message to some broker and than i separate thread run database operations without affecting the basic logic.

But this is business decision.
