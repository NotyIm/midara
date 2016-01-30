FROM clojure

COPY * /usr/src/app
WORKDIR /usr/src/app
RUN ls -la .
RUN which lein
RUN lein deps

CMD ["lein", "run"]

