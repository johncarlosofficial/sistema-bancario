# Sistema Bancário

## 1. Descrição

Este projeto consiste em um sistema bancário simples desenvolvido em **Java**.

## 2. Equipe

* João Carlos de Magalhães Rodrigues: [@johncarlosofficial](https://github.com/johncarlosofficial)
* Jorge William Camara Sales: [@Jorgelino328](https://github.com/Jorgelino328)
* Rafael de Moura Cassiano Silva: [@rafael1109-moura](https://github.com/rafael1109-moura)

## 3. Branches

### 3.1. Principais

* **`main`**: Referência de desenvolvimento; contém a versão atual do sistema em construção.

* **`staging`**: Ambiente de homologação (pré-produção); código em estabilização para próxima versão.

* **`production`**: Ambiente de produção; código estável.

### 3.2. Suporte

* **`feature/`**: Criada a partir da `main` para novas funcionalidades; merge na `main`.

* **`bugfix/`**: Criada a partir da `staging` para correções; merge na `staging` e na `main`.

* **`hotfix/`**: Criada a partir da `production` para correções urgentes;merge na `staging` e na `production`.

## 4. Commits

* **Formato**: `tipo: descrição (#issue)`

* **Tipos**:
  * feat
  * fix
  * refactor
  * test
  * docs

**Exemplo**:

```text
feat: implementa cadastro de conta (#5)
```

## 5. Regras

* Use **feature branches** (*sem* commits diretos na `main`);
* Teste todos os commits, não apenas os da `main`;
* **Todo commit deve estar associado a uma issue**;
* Commits enviados **NUNCA** são rebased;
* Mensagens devem refletir claramente a intenção;
* As branches **NÃO** devem ser removidas – **nem mesmo as auxiliares** – durante todo o desenvolvimento do projeto.

## 6. VSCode

### 6.1. Extensões Recomendadas

* [Extension Pack for Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack);
* [Maven for Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-maven);
* [Debugger for Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-debug);
* [Language Support for Java (by Red Hat)](https://marketplace.visualstudio.com/items?itemName=redhat.java).

### 6.2. Problemas Comuns

Se houver warnings incorretos do Java no VSCode, limpe o workspace do Java Language Server:

1. Abra a paleta de comandos do VS Code
   * macOS: `Cmd + Shift + P`
   * Windows/Linux: `Ctrl + Shift + P`
2. Digite: `Java: Clean Java Language Server Workspace`
3. Confirme e reinicie quando solicitado

## 7. Execução

### 7.1. Clonar o projeto

```bash
git clone https://github.com/johncarlosofficial/sistema-bancario.git
cd sistema-bancario
```

### 7.2. Executar os testes

Antes de subir a aplicação, execute os testes para validar que as principais funcionalidades continuam funcionando corretamente.

```bash
mvn test
```

### 7.3. Gerar a imagem Docker

O Docker é utilizado para empacotar a aplicação junto com todas as dependências necessárias para sua execução. Isso garante que o sistema rode da mesma forma em qualquer ambiente, sem depender da configuração da máquina de quem está executando.

```bash
docker build -t sistema-bancario .
```

### 7.4. Subir o container

Após gerar a imagem, crie e execute um container. O container é uma instância da aplicação em execução, disponibilizando a API na porta 8080.

```bash
docker run -d --name sistema-bancario -p 8080:8080 sistema-bancario
```

## 8. Endpoints

### 8.1. Cadastrar Conta Simples

```bash
curl -X POST http://localhost:8080/banco/conta \
-H "Content-Type: application/json" \
-d '{
  "numero": "123",
  "tipoConta": "1",
  "saldoInicial": 1000
}'
```

### 8.2. Cadastrar Conta Poupança

```bash
curl -X POST http://localhost:8080/banco/conta \
-H "Content-Type: application/json" \
-d '{
  "numero": "456",
  "tipoConta": "2",
  "saldoInicial": 500
}'
```

### 8.3. Cadastrar Conta Bônus

```bash
curl -X POST http://localhost:8080/banco/conta \
-H "Content-Type: application/json" \
-d '{
  "numero": "789",
  "tipoConta": "3",
  "saldoInicial": 2000
}'
```

### 8.4. Consultar Conta

```bash
curl  http://localhost:8080/banco/conta/123
```

### 8.5. Consultar saldo

```bash
curl http://localhost:8080/banco/conta/123/saldo
```

### 8.6. Realizar Crédito

```bash
curl -X PUT http://localhost:8080/banco/conta/123/credito \
-H "Content-Type: application/json" \
-d '{"valor":250.0}'
```

### 8.7. Testar Erro - Crédito Negativo

```bash
curl -X PUT http://localhost:8080/banco/conta/123/credito \
-H "Content-Type: application/json" \
-d '{"valor":-50.0}'
```

### 8.8. Realizar Débito

```bash
curl -X PUT http://localhost:8080/banco/conta/123/debito \
-H "Content-Type: application/json" \
-d '{"valor":100.0}'
```

### 8.9. Testar Erro - Débito com valor maior que o saldo

```bash
curl -X PUT http://localhost:8080/banco/conta/123/debito \
-H "Content-Type: application/json" \
-d '{"valor":999999.0}'
```

### 8.10. Realizar Transferência entre Contas

```bash
curl -X PUT http://localhost:8080/banco/conta/transferencia \
-H "Content-Type: application/json" \
-d '{
  "from": "123",
  "to": "456",
  "amount": 150.0
}'
```

### 8.11. Testar Erro - Transferência com saldo insuficiente

```bash
curl -X PUT http://localhost:8080/banco/conta/transferencia \
-H "Content-Type: application/json" \
-d '{
  "from": "123",
  "to": "456",
  "amount": 999999.0
}'
```

### 8.12. Aplicar Rendimento (Juros)

```bash
curl -X PUT http://localhost:8080/banco/conta/rendimento \
-H "Content-Type: application/json" \
-d '{
  "taxa": 0.05
}'
```

### 8.13. Testar Erro - Taxa inválida (negativa)

```bash
curl -X PUT http://localhost:8080/banco/conta/rendimento \
-H "Content-Type: application/json" \
-d '{
  "taxa": -0.1
}'
```
