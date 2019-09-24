FROM openjdk:8
ARG appname
ARG version
WORKDIR /opt/docker
COPY /build/distributions/$appname-$version.tar /opt/docker/$appname-$version.tar
RUN tar -xvf $appname-$version.tar && \
        rm -rf $appname-$version.tar && \
        cp -r $appname-$version/. . && \
        rm -rf $appname-$version
RUN ["chown", "-R", "daemon:daemon", "."]
USER daemon
ENTRYPOINT ["/opt/docker/bin/alert-hook"]
CMD []
