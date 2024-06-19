
FROM ubuntu:jammy
ARG PROJECT

# Project install
USER 1000
ADD --chown=1000:1000 build/native/nativeCompile/$PROJECT /
WORKDIR /
ENTRYPOINT [ "/todo_backend" ]
