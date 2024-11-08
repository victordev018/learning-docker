## Learning Docker - Aprendendo Docker

Este projeto consiste em uma `API` para um sistema de gerenciamento de tarefas. Nele, o usuário poderá 
criar uma conta, gerenciar suas tarefas, publicá-las e manter um histórico de atividades.

O foco principal deste projeto é colocar em prática conceitos de `conteinerização` – uma tecnologia que 
agrupa o código da aplicação e todas as suas dependências (arquivos e bibliotecas) em um contêiner. 
Isso garante que a aplicação possa ser executada em qualquer infraestrutura, independentemente do 
ambiente ou sistema operacional, proporcionando maior __portabilidade__ e __consistência__.

## Uso do Docker-Compose em Ambiente de Desenvolvimento

Esta API está integrada com o __PostgreSQL__, um banco de dados relacional. Para simplificar a comunicação e 
evitar a instalação manual do banco de dados no ambiente local, criamos um arquivo `docker-compose.yml`. 
Com ele, é possível declarar e gerenciar diversos serviços necessários durante o desenvolvimento.

Utilizamos o `docker-compose.yml` para subir um contêiner do `PostgreSQL`, o que facilita a execução de testes locais
e mantém o ambiente de desenvolvimento mais organizado e padronizado. 

Confira o código abaixo:

```yml

services:                       # set the services
  postgres-db:                  # name service
    image: postgres:13          # image
    container_name: postgres-db
    environment:                # set environment variables
      POSTGRES_USER: dev
      POSTGRES_PASSWORD: dev
      POSTGRES_DB: task_manager_db
    ports:                      # mirrors the container door to the local one
      - "5432:5432"
    volumes:                    # set volume to store data
      - data:/var/lib/postgresql/data
    networks:                   # set network
      - springboot-network

volumes:
  data:

networks:
  springboot-network:

```

O código acima define um serviço chamdo __postgres-db__, e passa algumas propriedades necessárias
para que o `Docker` consiga localizar as imagens dos serviços e que crie uma nova instância local
de cada um.

Uma vez criado o arquivo, pode usar o seguinte comando do `Docker` para subir o serviço/serviços
definidos no arquivo:

```bash
docker-compose up -d
```

Caso queira `visualizar` o status e informações dos contâiners que estão rodando, pode utilizar o seguinte comando:

```bash
docker-compose ps -a
```

E para `matar/parar` os serviços que estão rodando, use o seguinte código:

```bash
docker-compose down
```

## Uso do Dockerfile para conteinerizar a aplicação e fazer Deploy

O `Dockerfile` é um arquivo de configuração usado para definir todos os passos necessários para construir a imagem de 
um contêiner Docker. Nele, são especificadas as instruções para configurar o ambiente, como o sistema base, bibliotecas, 
dependências, cópia de arquivos da aplicação, variáveis de ambiente e comandos de inicialização.

Com este arquivo criado, poderá gerar uma `imagem` de sua aplicação com tudo que ela precisa para poder executar,
independentemente do ambiente em que esteja.

Confira o código abaixo:

```Dockerfile
FROM maven:3.8.4-openjdk-17  AS build

COPY src /app/src
COPY pom.xml /app

WORKDIR /app
RUN mvn clean install -DskipTests

FROM openjdk:17-jdk-alpine

COPY --from=build /app/target/learning-docker-0.0.1-SNAPSHOT.jar /app/app.jar

WORKDIR /app

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
```

O código acima define faz a __cópia de todo o código__ fonte para um diretório que será criado no contâiner,
copia o arquivo que contém as __dependências do projeto__, roda comando de __instalçao de dependências__, __compila
o projeto__, __expõe a porta__ onde será executada e por fim executa o comando para __rodar a aplicação__.

Para gerar a imagem da aplicação a partir do __Dockerfile__, executei o seguinte comando:

```bash
docker build -t victordev018/learning-docker:1.0 .
```

O comando acima cria uma imagem chamada `learning-docker` do usuário `victordev018` na versão `1.0` e o `.` no final indica que
o arquivo __Dockerfile__ está na raiz do projeto. Ao gerar uma imagem de uma aplicação sua, substitua os valores para suas informações.

```bash
docker build -t <seu usuário>/<nome imagem>:<versão> <caminho do arquivo Dockerfile>
```

Com o Dockerfile criado, quando a aplicação for para um `ambiente de produção`, poderá fazer o uso deste arquivo `Dockerfile`
para fazer o `deploy`, já que nele contém todo o passo a passo para fazer o build do projeto.

> Incluse foi feito o deploy desta aplicação na Render e a URL base é a seguinte:

> URL : https://hello-world-157i.onrender.com


## Rotas da API


> POST /users/create  -> rota para criação de uma nova conta <br>
> URL: https://hello-world-157i.onrender.com/users/create <br>
> Body: <br>
> ```json
> {
>  "username" : "nome usuário",
>  "email" : "email@gmail.com",
>  "password" : "senha1"
> }
> ```


> GET /users/{id}  -> rota para obter os dados de uma conta por id <br>
> URL: https://hello-world-157i.onrender.com/users/1 <br>
> Body: sem corpo <br>


> GET /users/{id}/tasks  -> rota para obter as tarefas de um usuário pelo seu id <br>
> URL: https://hello-world-157i.onrender.com/users/1/tasks <br>
> Body: sem corpo <br>

> POST /tasks/create  -> rota para criar uma nova task para um usuário <br>
> URL: https://hello-world-157i.onrender.com/tasks/create <br>
> Body: <br>
>```json
>{
>  "content" : "conteúdo da tarefa",
>  "user_id" : 1
>}
>```


> GET /tasks/all -> rota para obter todas as tasks cadastradas na API <br>
> URL: https://hello-world-157i.onrender.com/tasks/all <br>
> Body: sem corpo <br>

## Tecnologias utilizadas
- Java
- Spring Boot
- PostgresSQL
- JPA
- Docker

