FROM alpine:3.18

RUN apk add build-base

COPY flowers.c .

RUN gcc flowers.c --static -o flowers

FROM scratch
COPY --from=0 flowers .

ENTRYPOINT [ "./flowers" ]
