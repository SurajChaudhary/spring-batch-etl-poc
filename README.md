# spring-batch-etl-poc
This POC shows example of spring batch app for loading data from database, transforming it and then posting it to another service using rest template.

docker run --name postgresDb -e POSTGRES_PASSWORD=mysecretpassword -d -p 5432:5432 postgres:alpine

Prometheus: https://stackoverflow.com/questions/48704789/how-to-measure-service-methods-using-spring-boot-2-and-micrometer

public void consume(String message) {
        System.out.println("Received Message: " + message);
        // you can keep a ref to this; ok to call multiple times, though
        Timer timer = Timer.builder("myservice").tag("method", "consumer_sample").register(registry);
        timer.record(() -> {
            gateway.sendToRabbit(message + " resending via gateway");
            amqpTemplate.convertAndSend("cris.fanout.exchange", "cris.create.review.response.key", message + " resending");
        });
    }

