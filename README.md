# VehicleApp - Desafio Técnico 

Este projeto é uma solução para o desafio técnico proposto no processo seletivo para Analista de Desenvolvimento Android da Tecnomotor. O objetivo foi desenvolver um aplicativo Android que gerencia dados de veículos, consumindo uma API REST, persistindo os dados localmente e permitindo a manipulação dessas informações pelo usuário.

## Funcionalidades Implementadas

* **Sincronização de Dados**: O aplicativo consome os dados de veículos e montadoras a partir de uma API REST e os salva em um banco de dados local na primeira execução.
* **Listagem de Veículos**: Exibe a lista de veículos salvos localmente, mostrando informações como modelo, nome da montadora, ano e motorização.
* **Gerenciamento de Veículos (CRUD)**:
    * **Adicionar**: Permite ao usuário cadastrar novos veículos manualmente através de um formulário.
    * **Editar**: Permite editar as informações de um veículo existente.
    * **Excluir**: O usuário pode excluir um veículo da lista local.
    * **Visualizar Detalhes**: Oferece um modo de visualização somente leitura dos dados do veículo.
* **Tratamento de Erros**: Exibe uma mensagem ao usuário caso ocorra uma falha ao tentar buscar os dados da API (ex: falta de conexão com a internet).

## Tecnologias e Arquitetura

O projeto foi desenvolvido seguindo as melhores práticas e utilizando ferramentas modernas do ecossistema Android.

* **Linguagem**: 100% [Kotlin](https://kotlinlang.org/).
* **Arquitetura**: MVVM (Model-View-ViewModel), que promove um código mais limpo, organizado e testável.
* **Principais Bibliotecas**:
    * **Jetpack Room**: Para persistência de dados local de forma robusta e eficiente.
    * **Retrofit & Gson**: Para realizar a comunicação com a API REST e converter os dados JSON.
    * **ViewModel & LiveData**: Para gerenciar o estado da UI de forma consciente ao ciclo de vida dos componentes.
    * **Coroutines**: Para gerenciamento de operações assíncronas, como chamadas de rede e acesso ao banco de dados.
    * **Material Components**: Para a construção da interface do usuário, seguindo as diretrizes do Material Design.

## Como Compilar e Executar

Siga os passos abaixo para compilar e executar o projeto.

### Pré-requisitos

* Android Studio (versão mais recente recomendada).
* JDK 11.

### Passos

1.  **Clonar o Repositório**
    ```bash
    git clone [URL_DO_SEU_REPOSITORIO_AQUI]
    ```
2.  **Abrir no Android Studio**
    * Abra o Android Studio.
    * Selecione "Open an Existing Project".
    * Navegue até o diretório onde você clonou o projeto e selecione-o.
    * Aguarde o Gradle sincronizar e baixar todas as dependências.

3.  **Executar o Aplicativo**
    * Conecte um dispositivo Android ou inicie um emulador (API 26 ou superior).
    * Clique no botão "Run 'app'" (ícone de play verde) na barra de ferramentas do Android Studio.
    * O aplicativo será compilado e instalado no dispositivo/emulador selecionado.
