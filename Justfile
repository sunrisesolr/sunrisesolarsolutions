build:
    #!/bin/bash
    cd sunrise/
    ./mvnw clean install -DskipTests

push: build
    #!/bin/bash
    rsync -av ./sunrise/target/sunrise-0.0.1-SNAPSHOT.jar ss-app:/home/ec2-user
