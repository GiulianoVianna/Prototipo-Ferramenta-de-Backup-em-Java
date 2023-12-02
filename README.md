# Aplicação de Agendamento de Backup

![image](https://github.com/GiulianoVianna/Prototipo-Ferramenta-de-Backup-em-Java/assets/101942554/6683a104-2382-4047-9cbd-59f8d29e1436)


Esta aplicação oferece uma solução completa para agendar e executar backups automáticos de arquivos e diretórios. Com uma interface amigável, os usuários podem configurar facilmente os backups, escolher entre diferentes opções e gerenciar agendamentos.

## Funcionalidades

### Selecionar Diretório ou Arquivo
- Permite ao usuário escolher entre selecionar um diretório ou um arquivo.
- Exibe uma caixa de diálogo para escolher o tipo de seleção.
- Retorna o caminho absoluto do item selecionado.

### Selecionar Diretório
- Específico para a escolha de diretórios.
- Atualiza a interface do usuário com o caminho do diretório selecionado.

### Verificar e Criar Banco de Dados
- Verifica a existência do banco de dados e o cria se necessário.
- Utiliza a classe `BancoDeDadosDAO` para as operações.

### Configurar Ícone da Janela
- Carrega e define um ícone para a janela da aplicação.

### Configurar Botões da Interface
- Habilita ou desabilita botões com base em parâmetros booleanos.

### Habilitar Campos da Interface
- Permite habilitar ou desabilitar campos da interface.

### Salvar Agendamento de Backup
- Salva um novo agendamento de backup com base nos campos preenchidos.

### Popular Tabela de Agendamento de Backup
- Preenche a tabela da interface com agendamentos de backup existentes.

### Formatar Data e Hora
- Formata data e hora para a exibição na interface.

### Editar e Atualizar Agendamento de Backup
- Permite a edição e atualização de um agendamento existente.

### Excluir Agendamento de Backup
- Remove um agendamento de backup com base no ID.

### Agendar Backup Automático
- Um `ActionListener` verifica e executa backups automaticamente com base na data e hora.

### Realizar Backup
- Compacta arquivos ou diretórios selecionados em um arquivo ZIP.

### Desligar o Computador
- Executa um comando para desligar o computador após um período de espera.

## Como Usar
1. Inicie a aplicação e selecione a opção desejada na interface.
2. Configure os parâmetros de backup, como diretórios de origem e destino.
3. Salve o agendamento para executar o backup automaticamente.
4. Gerencie os agendamentos existentes através da tabela de interface.

## Requisitos
- Java Runtime Environment.
- Acesso a um sistema de banco de dados.

## Desenvolvimento
Esta aplicação foi desenvolvida com foco na usabilidade e eficiência, procurando seguir as melhores práticas de engenharia de software.

