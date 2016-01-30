OWNER := notyim
NAME  := midara-builder
VERSION := 0.1

build:
	docker build -t $(OWNER)/$(NAME):$(VERSION) docker/builder

run:
	docker run -p 8080:8080 $(OWNER)/$(NAME):$(VERSION)

login:
	docker login https://$(REGISTRY)

push:
	docker push $(OWNER)/$(NAME):$(VERSION)
