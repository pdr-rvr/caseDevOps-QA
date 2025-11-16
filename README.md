# Case GameClass: Especificações de Funcionalidades Iniciais

Este documento descreve as funcionalidades fundamentais desenvolvidas na fase inicial do projeto **GameClass**. O desenvolvimento foi guiado por práticas ágeis, utilizando User Stories para definir o valor de negócio e BDD (Behavior-Driven Development) para especificar os critérios de aceite de forma clara e testável.

## Funcionalidades Implementadas

Até o momento, o foco foi na estruturação do conteúdo da plataforma, permitindo que administradores criem a base para a experiência do aluno.

---

### US1: Criação de Novos Cursos

Esta funcionalidade permite que um administrador adicione um novo curso à plataforma, servindo como o contêiner principal para o conteúdo educacional.

> **Como** administrador de sistema,  
> **Quero** criar um novo curso,  
> **Para que** ele exista na plataforma.

#### Cenário de Aceitação (BDD)

O comportamento esperado para a criação de um curso foi definido pelo seguinte cenário:

```gherkin
Funcionalidade: Cadastro de Cursos
  Para que o catálogo de conteúdo possa ser construído,
  Como um administrador de sistema,
  Eu quero poder criar novos cursos na plataforma.

  Cenário: Adicionar um novo curso com sucesso
    Dado que eu sou um administrador logado no sistema
    E estou na página de "Criação de Cursos"
    Quando eu preencho o campo "Nome do Curso" com "Introdução a Java"
    E clico no botão "Salvar Curso"
    Então eu devo ser redirecionado para a "Lista de Cursos"
    E devo ver "Introdução a Java" presente na lista.
```

---

### US2: Adição de Aulas ao Curso

Com um curso criado, esta funcionalidade permite que o administrador popule este curso com aulas, que representam as unidades de aprendizado.

> **Como** administrador de sistema,  
> **Quero** adicionar aulas a um curso,  
> **Para que** esse curso tenha conteúdo.

#### Cenário de Aceitação (BDD)

O comportamento esperado para a adição de aulas foi definido pelo seguinte cenário:

```gherkin
Funcionalidade: Gerenciamento de Aulas
  Para que os cursos tenham conteúdo didático,
  Como um administrador de sistema,
  Eu quero poder adicionar aulas a um curso existente.

  Cenário: Adicionar uma nova aula a um curso existente
    Dado que o curso "Introdução a Java" já existe na plataforma
    E eu estou na página de detalhes do curso "Introdução a Java"
    Quando eu clico no botão "Adicionar Nova Aula"
    E preencho o campo "Título da Aula" com "Variáveis e Tipos Primitivos"
    E clico no botão "Salvar Aula"
    Então eu devo ver a aula "Variáveis e Tipos Primitivos" listada na página de detalhes do curso "Introdução a Java".

```
