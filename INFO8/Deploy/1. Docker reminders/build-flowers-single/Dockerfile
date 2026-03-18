FROM alpine:3.18

RUN apk add build-base

COPY flowers.c .

RUN gcc flowers.c -o flowers

ENTRYPOINT [ "./flowers" ]
