

# IWanttoBeliave

Aplicativo Android desenvolvido em Kotlin, com autenticação, feed de posts e integração com Firebase. Abaixo, um resumo dos principais arquivos e estrutura da pasta `java` (código-fonte):

## Estrutura e principais arquivos (`app/src/main/java/com/example/iwanttobeliave/`)

- **MainActivity.kt**: Ponto de entrada do app. Gerencia tema, navegação e autenticação do usuário (Firebase).

- **data/model/Post.kt**: Modelo de dados de um post (id, autor, imagem, descrição, data).

- **data/model/User.kt**: Modelo de dados de usuário.

- **data/repository/UserRepository.kt**: Criação, atualização e busca de perfis de usuário no Firestore.

- **data/repository/PostRepository.kt**: Criação e observação de posts em tempo real no Firestore.

- **ui/feed/FeedScreen.kt**: Tela principal do feed, exibe posts usando Jetpack Compose.

- **ui/auth/AuthViewModel.kt**: Lida com autenticação (registro, login, atualização de perfil) usando Firebase Auth e Firestore.

- **ui/navigation/NavGraph.kt**: Define a navegação principal do app (login, registro, feed).

- **ui/navigation/Destinations.kt**: Centraliza as rotas (nomes das telas) usadas na navegação.

- **ui/theme/Theme.kt**: Define o tema visual do app (cores, tipografia, claro/escuro).

- **ui/theme/Color.kt**: Paleta de cores do app.

## Como rodar o projeto

1. Instale o [Android Studio](https://developer.android.com/studio).
2. Clone este repositório:
  ```sh
  git clone https://github.com/josemateusferreira/IWanttoBeliave.git
  ```
3. Abra a pasta `IWanttoBeliave` no Android Studio.
4. Aguarde a sincronização do Gradle e o download das dependências.
5. Execute o app em um emulador ou dispositivo físico.

## Requisitos
- Android Studio (recomendado: versão mais recente)
- JDK 17 ou superior
- Gradle (configurado via wrapper)

## Licença
MIT. Você pode usar, modificar e distribuir o código, mantendo o aviso de copyright e a licença.

## Contribuição
Contribuições são bem-vindas! Abra uma issue ou envie um pull request para sugerir melhorias ou correções.

