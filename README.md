# Sistema Bancário

## 1. Descrição

Este projeto consiste em um sistema bancário simples desenvolvido em **Java**, executado via terminal, com operações básicas de contas.

**Funcionalidades**:

1. Cadastro de conta
2. Consulta de saldo
3. Crédito em conta
4. Débito em conta
5. Transferência entre contas

**Separação em camadas**:

* **UI (Interface com usuário)**: Interação via terminal
* **Service (Camada de Negócio)**: Regras e operações do sistema

> Não há persistência em banco de dados.

## 2. Equipe

* João Carlos de Magalhães Rodrigues
* Jorge William Camara Sales
* Rafael de Moura Cassiano Silva

## 3. Branches

### 3.1. Principais

* **`main`**: Referência de desenvolvimento; contém a versão atual do sistema em construção.

* **`staging`**: Ambiente de homologação (pré-produção); código em estabilização para próxima versão.

* **`production`**: Ambiente de produção; código estável.

### 3.2. Suporte

* **`feature/`**: Criada a partir da `main` para novas funcionalidades; merge na `main`.

* **`bugfix/`**: Criada a partir da `staging` para correções; merge na `staging` e na `main`.

* **`hotfix/`**: Criada a partir da `production` para correções urgentes;merge na `staging` e na `production`.

## 4. Processo de Desenvolvimento

1. Criar uma issue
2. Criar a branch adequada
3. Implementar a solução
4. Garantir que os testes passam
5. Abrir Pull Request
6. Revisão obrigatória
7. Merge realizado pelo responsável

## 5. Commits

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

> Não são permitidos commits com formato incorreto ou sem vínculo com issue.

## 6. Regras

* Use **feature branches** (*sem* commits diretos na `main`);
* Teste todos os commits, não apenas os da `main`;
* **Todo commit deve estar associado a uma issue**;
* Commits enviados **NUNCA** são rebased;
* Mensagens devem refletir claramente a intenção;
* As branches **NÃO** devem ser removidas – **nem mesmo as auxiliares** – durante todo o desenvolvimento do projeto.

## 7. Requisitos

* Java JDK 17+
* Maven 3+

## 8. Execução

* No terminal, clone o repositório:
  
  ```bash
  git clone https://github.com/johncarlosofficial/sistema-bancario.git
  ```

* Abra o projeto no [VSCode](https://code.visualstudio.com/).

* No terminal do VSCode, execute o projeto com Maven:

  ```bash
  mvn -q exec:java
  ```

### 8.1. VSCode

#### 8.1.1. Extensões Recomendadas

* [Extension Pack for Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack);
* [Maven for Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-maven);
* [Debugger for Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-debug);
* [Language Support for Java (by Red Hat)](https://marketplace.visualstudio.com/items?itemName=redhat.java).

#### 8.1.2. Problemas Comuns

Se houver warnings incorretos do Java no VSCode, limpe o workspace do Java Language Server:

1. Abra a paleta de comandos do VS Code
   * macOS: `Cmd + Shift + P`
   * Windows/Linux: `Ctrl + Shift + P`
2. Digite: `Java: Clean Java Language Server Workspace`
3. Confirme e reinicie quando solicitado
