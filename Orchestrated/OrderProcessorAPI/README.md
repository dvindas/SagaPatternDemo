# OrderProcessorAPI



## Build and run


With JDK21
```bash
mvn package
java -jar target/orderprocessorapi.jar
```

## Exercise the application

Basic:
```
curl --location 'http://localhost:8081/api/orderProcessor/placeOrder' \
--header 'Long-Running-Action: http://lra-coordinator:8080/797398ef-296b-4853-8f0d-f4fe64877a43' \
--header 'Content-Type: application/json' \
--data '{
    "customerId": "c382f61d-3e6e-4abd-a6d9-54",
    "item": {
        "productId": "5895fbac-6a46-4122-a0af-6234ec7e633e",
        "price": 20,
        "quantity": 1
    }
}'
Hello World!
```



## Try health

```
curl -s -X GET http://localhost:8081/health
{"outcome":"UP",...
```

## OpenAPI

Find the open api documentation at http://localhost:8081/openapi

![img.png](images/openapi-example.png)

## Building the Docker Image

```
docker build -t orderprocessorapi .
```

## Running the Docker Image

```
docker run --rm -p 8081:8081 orderprocessorapi:latest
```

Exercise the application as described above.
                                
