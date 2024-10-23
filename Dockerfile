FROM openjdk:21 AS build
WORKDIR /
RUN microdnf install -y glibc-langpack-ko
RUN echo 'LANG=ko_KR.UTF-8' > /etc/locale.conf && \
    echo 'LC_ALL=ko_KR.UTF-8' >> /etc/locale.conf
ENV LANG=ko_KR.UTF-8
ENV LC_ALL=ko_KR.UTF-8
COPY cotuber-api/build/libs/*.jar /app.jar
COPY domain/build/libs/*.jar .
COPY storage/db/build/libs/*.jar .
COPY support/jwt/build/libs/*.jar .
COPY support/logging/build/libs/*.jar .
COPY support/util/build/libs/*.jar .
ENV LANG=ko_KR.UTF-8
ENV LC_ALL=ko_KR.UTF-8
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-spring.active.profile=real", "/app.jar"]