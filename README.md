# AidShelter

AidShelter é uma aplicação Java que utiliza JPA, Hibernate, Gradle e MySQL para gerenciar doações e distribuição de suprimentos para abrigos. O projeto tem como objetivo auxiliar vítimas de enchentes através da gestão eficiente de doações, centros de distribuição e abrigos.

## Configuração do Banco de Dados
O arquivo persistence.xml está localizado em src/main/resources/META-INF e contém a configuração de persistência do JPA. Certifique-se de configurar as propriedades do banco de dados corretamente, substituindo os valores de ${jdbc.url} pelo endereço do banco, ${jdbc.user} pelo usuário de login do banco e ${jdbc.password} pela senha do banco.

## Dependências

Este projeto utiliza Gradle para gerenciamento de dependências. Certifique-se de ter o Gradle instalado em sua máquina.

## Executando o Projeto

Para executar o projeto, você precisa fornecer dois parâmetros:

1. O caminho para o arquivo CSV que contém os centros de distribuição.
2. O caminho para o arquivo CSV que contém as doações.

### Passos:

1. Clone o repositório:

    ```sh
    git clone https://github.com/yourusername/aidshelter.git
    ```

2. Navegue até o diretório do projeto:

    ```sh
    cd aidshelter
    ```

3. Execute o projeto com os parâmetros necessários:

    ```sh
    ./gradlew run --args="path/to/distribution_centers.csv path/to/donations.csv"
    ```

## Estrutura do Código

- `config`: Contém classes de configuração.
- `dto`: Contém objetos de transferência de dados.
- `entities`: Contém as entidades JPA.
- `repositories`: Contém as interfaces de repositórios JPA.
- `services`: Contém as classes de serviço.
- `validators`: Contém as classes de validação.

## Contribuição

1. Faça um fork do projeto.
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`).
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`).
4. Dê um push na sua branch (`git push origin feature/AmazingFeature`).
5. Abra um Pull Request.


