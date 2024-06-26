
FROM ubuntu:jammy
ARG PROJECT
ARG TZ=UTC

# Machine setup
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# Project install
USER 1000
ADD --chown=1000:1000 build/$PROJECT /$PROJECT
WORKDIR /$PROJECT
ENTRYPOINT [ "/todo_backend/bin/todo_backend" ]
