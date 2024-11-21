# Projeto Encurtador de URLs com AWS
Este projeto é uma implementação em Java 17 de um encurtador de URLs usando serviços da AWS. Ele processa requisições
recebidas, analisa payloads JSON, gera URLs encurtadas e armazena os dados no bucket S3 da AWS.
## Funcionalidades
- Handler para AWS Lambda: Implementa a interface RequestHandler para processar requisições em um ambiente AWS Lambda.
- Análise de JSON: Utiliza a biblioteca Jackson para serialização e desserialização de JSON.
- Integração com AWS S3: Armazena os dados do URL encurtado em um bucket S3.
- Geração de UUID: Garante identificadores únicos para URLs encurtadas.

## Requisitos
- Java 17
- Conta AWS com bucket S3 configurado
- Maven para gerenciamento de dependências

## Instalação

1. **Clone o repositório**:
   Clone o projeto para sua máquina local:
   ```bash
   git clone https://github.com/GabrielBarrosAS/redirect-url-shorter-aws-lambda.git
   cd redirect-url-shorter-aws-lambda

2. **Atualize o nome do bucket S3**:  
   No código da classe Main, altere o nome do bucket para o que você configurou na AWS:
   ```bash
   .bucket("seu-nome-do-bucket")

3. **Compile o projeto**:  
   Compile o projeto utilizando Maven:
   ```bash
   mvn clean install

## Implantação
Deploy no AWS Lambda
1. **Empacote o projeto em um arquivo .jar:**:
   ```bash
   mvn package
2. **Faça o upload do .jar na função Lambda no console da AWS ou via CLI.**
3. **Configure variáveis de ambiente, se necessário (por exemplo, nome do bucket).**

## Postman Collection

Uma coleção do Postman foi preparada para facilitar os testes da aplicação. Esta coleção contém exemplos de requisições configuradas para testar a função Lambda de encurtamento de URLs.

## Como Importar a Coleção

1. Faça o download do arquivo `url-shortener-aws-lambda.postman_collection.json` do repositório.
2. No Postman, clique em **Importar** no canto superior esquerdo.
3. Selecione o arquivo `url-shortener-aws-lambda.postman_collection.json` baixado.
4. Após a importação, a coleção aparecerá na sua lista de coleções.

## Estrutura da Coleção

A coleção inclui os seguintes endpoints:

1. **POST Shorten (AWS Lambda) e POST Shorten (API Gateway)**  
Envia uma URL original e um tempo de expiração (timestamp: https://www.unixtimestamp.com/) para gerar uma URL encurtada, porém uma acessa a função lambda
diretamente e a segunda utiliza o gateway central da aplicação