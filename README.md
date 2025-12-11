# Chat_project — консольный сетевой чат (Java, Sockets, Maven)

## Описание
Проект состоит из двух приложений:
- **Server** — сервер чата, принимает подключения клиентов и ретранслирует сообщения.
- **Client** — консольный клиент, подключается к серверу, отправляет и получает сообщения.

## Требования
- Java 17, Maven
- Сервер и клиент логируют события в файлы `server.log` и `client.log`
- Выход из чата командой `/exit`

## Запуск (локально)
### Сервер
1. Перейти в папку `server`
2. Убедиться, что `settings.txt` содержит `PORT=12345` (или другой порт)
3. Сборка: `mvn clean package`
4. Запуск: `java -jar target/server-1.0-SNAPSHOT.jar`

### Клиент
1. Перейти в папку `client`
2. Убедиться, что `src/main/resources/settings.txt` содержит `HOST=localhost` и `PORT=12345`
3. Сборка: `mvn clean package`
4. Запуск: `java -jar target/client-1.0-SNAPSHOT-jar-with-dependencies.jar`

## Тестирование
- Интеграционные тесты находятся в `server/src/test/java/.../ServerIntegrationTest.java`
- Запуск тестов: `mvn test` в корне модуля (`server` или `client`)

## Архитектура
- Сервер:
    - Main thread — слушает `ServerSocket`
    - Для каждого подключения создаётся `ClientHandler`
    - `broadcast()` рассылает сообщения всем клиентам, кроме отправителя
- Клиент:
    - Main thread — читает stdin и отправляет сообщения
    - `MessageReader` — отдельный поток читает входящие сообщения

## Формат сообщений
- Plain text `username: message`
- Логи содержат timestamp и текст сообщения.
