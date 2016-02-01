OWNER := notyim
BUILDER  := midara-builder
MIDARA  := midara
VERSION := 0.1

build:
	docker build -t $(OWNER)/$(BUILDER):$(VERSION) docker/builder

run:
	docker run -p 8080:8080 $(OWNER)/$(BUILDER):$(VERSION)

login:
	docker login https://$(REGISTRY)

push:
	docker push $(OWNER)/$(BUILDER):$(VERSION)

build_midara:
	docker build -t $(OWNER)/$(MIDARA):$(VERSION) -f docker/Dockerfile

push_midara:
	docker push $(OWNER)/$(MIDARA):$(VERSION)
