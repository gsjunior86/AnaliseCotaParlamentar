
default: build


build:
	sbt reload clean compile package

dockerize:
	docker build -f Dockerfile.base -t gsjunior/analise_cota_parlamentar .


image-upload:
	docker push gsjunior/analise_cota_parlamentar
