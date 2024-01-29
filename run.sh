#!/bin/bash

#if ! [ -d "$DIR" ]; then
#  printf "Error: ${DIR} not found. Creating and downloading files ...\n"
#  mkdir -p ${DIR}
#fi
printf "Checando se deputados.csv esta presente...\n"
cd "/var/data/csv"
if ! [ -f "deputados.csv" ]; then
  curl -O "https://dadosabertos.camara.leg.br/arquivos/deputados/csv/deputados.csv"
fi

printf "Checando se as planilhas de gastos estao presente...\n"
i=2008
while [ "$i" -le 2023 ];  do
 if ! [ -f "Ano-$i.csv" ]; then
   printf "Baixando planilha de $i \n"
   curl -O "https://www.camara.leg.br/cotas/Ano-$i.csv.zip"
   unzip "Ano-$i.csv.zip"
   rm "Ano-$i.csv.zip"
 fi
 i=$(( i + 1 ))
done

cd "/data/gsj"

#printf "Iniciando job spark...\n"

#/spark/bin/./spark-submit --class br.gsj.camara.main.Main --jars $(echo lib/*.jar | tr ' ' ',') --master local[*] acp.jar








