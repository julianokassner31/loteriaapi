#!/usr/bin/env zsh
printf "rodando mvn package\n"
./mvnw package -DskipTests
printf "terminou de buildar\n"
printf  "Iniciando criacao da imagem docker\n"
docker build . -t julianokassner/loteriaapi
printf  "terminou de construir a imagem\n"
printf "enviando imagem para o repositorio\n"
docker push julianokassner/loteriaapi
printf "Enviou imagem para o repositorio"
# printf "Parando container analiselotofacil para subir alteração\n"
# docker stop analiselotofacil
# printf  "Iniciando container com analiselotofacil\n"
# docker run --rm -d --name analiselotofacil -p 8081:8081 --network bridge analiselotofacil
# printf  "Subiu container analiselotofacil"