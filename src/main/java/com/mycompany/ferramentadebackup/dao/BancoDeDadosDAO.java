package com.mycompany.ferramentadebackup.dao;

import com.mycompany.ferramentadebackup.dto.BancoDeDadosDTO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 * Classe para manipulação de operações de banco de dados relacionadas à tabela
 * 'dados_backup'.
 * <p>
 * Esta classe fornece funcionalidades para criar a tabela 'dados_backup' no
 * banco de dados SQLite, inserir, atualizar, excluir e listar registros, além
 * de métodos específicos para verificar a data, hora e a configuração de
 * desligamento do PC.
 * <p>
 * Métodos disponíveis:
 * <ul>
 * <li>{@link #verificarECriarBancoDeDados()} - Verifica e cria a tabela
 * 'dados_backup'.</li>
 * <li>{@link #cadastrar(BancoDeDadosDTO)} - Insere um novo registro na
 * tabela.</li>
 * <li>{@link #editar(BancoDeDadosDTO)} - Atualiza um registro existente.</li>
 * <li>{@link #excluir(BancoDeDadosDTO)} - Exclui um registro específico.</li>
 * <li>{@link #listar()} - Lista todos os registros da tabela.</li>
 * <li>{@link #verificarDataHoraAtual()} - Verifica se a data e hora atuais
 * correspondem a algum registro.</li>
 * <li>{@link #verificarDataHoraAtualArquivos()} - Busca um registro pela data e
 * hora atuais.</li>
 * <li>{@link #verificarDesligarPC()} - Verifica se o PC deve ser desligado com
 * base na data e hora atuais.</li>
 * </ul>
 */
public class BancoDeDadosDAO {

    // URL de conexão com o banco de dados SQLite
    String url = "jdbc:sqlite:dados_backup.db";

    // Lista para armazenar objetos do tipo BancoDeDadosDTO, que representam os dados do bakup
    ArrayList<BancoDeDadosDTO> lista = new ArrayList<>();

    /**
     * Verifica e cria a tabela 'dados_backup' no banco de dados SQLite se ela
     * não existir.
     * <p>
     * Este método cria uma nova tabela chamada 'dados_backup' com várias
     * colunas (id, diretorio_origem, diretorio_destino, data, desligar_pc,
     * nome_backup, hora) se essa tabela ainda não estiver presente no banco de
     * dados. Além disso, verifica se a tabela está vazia após a criação e
     * imprime um log no console.
     * <p>
     * Estrutura da tabela 'dados_backup':
     * <ul>
     * <li>id - INTEGER PRIMARY KEY</li>
     * <li>diretorio_origem - TEXT NOT NULL</li>
     * <li>diretorio_destino - TEXT NOT NULL</li>
     * <li>data - TEXT NOT NULL</li>
     * <li>desligar_pc - TEXT NOT NULL</li>
     * <li>nome_backup - TEXT NOT NULL</li>
     * <li>hora - TEXT NULL</li>
     * </ul>
     * <p>
     * Em caso de falha na conexão com o banco de dados ou execução do SQL, uma
     * mensagem de erro é exibida para o usuário através de um JOptionPane.
     */
    public void verificarECriarBancoDeDados() {

        // SQL para criar a tabela se ela não existir
        String sql = "CREATE TABLE IF NOT EXISTS dados_backup ("
                + "id INTEGER PRIMARY KEY,"
                + "diretorio_origem TEXT NOT NULL,"
                + "diretorio_destino TEXT NOT NULL,"
                + "data TEXT NOT NULL,"
                + "desligar_pc TEXT NOT NULL,"
                + "nome_backup TEXT NOT NULL,"
                + "hora TEXT NULL);";

        try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()) {

            // Criar a tabela se ela não existir
            stmt.execute(sql);

            // Verificar se a tabela está vazia
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS count FROM dados_backup");
            if (rs.next() && rs.getInt("count") == 0) {
                System.out.println("A tabela 'dados_backup' foi criada e está vazia.");
            } else {
                System.out.println("A tabela 'dados_backup' já existe e contém dados.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error Método Verificar e Criar Banco de Dados" + e, "Error", JOptionPane.ERROR);
        }
    }

    /**
     * Insere um novo registro na tabela 'dados_backup' no banco de dados.
     * <p>
     * Este método utiliza um objeto {@link BancoDeDadosDTO} para inserir um
     * novo registro na tabela 'dados_backup'. Os dados do objeto são mapeados
     * para as colunas correspondentes da tabela. O método prepara uma consulta
     * SQL com parâmetros para evitar SQL Injection.
     * <p>
     * Estrutura da inserção SQL:
     * <pre>
     * INSERT INTO dados_backup (diretorio_origem, diretorio_destino, data, desligar_pc, nome_backup, hora)
     * VALUES (?, ?, ?, ?, ?, ?)
     * </pre>
     * <p>
     * Após a execução bem-sucedida da inserção, é exibida uma mensagem de
     * confirmação para o usuário. Em caso de falha na execução da consulta, é
     * mostrada uma mensagem de erro com detalhes da exceção.
     *
     * @param objBancoDeDadosDTO Objeto {@link BancoDeDadosDTO} contendo os
     * dados a serem inseridos. Não deve ser {@code null}.
     * @throws NullPointerException Se {@code objBancoDeDadosDTO} for
     * {@code null}.
     */
    public void cadastrar(BancoDeDadosDTO objBancoDeDadosDTO) {
        String sql = "INSERT INTO dados_backup (diretorio_origem, diretorio_destino, data, desligar_pc, nome_backup, hora) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, objBancoDeDadosDTO.getDiretorioOrigem());
            pstmt.setString(2, objBancoDeDadosDTO.getDiretorioDestino());
            pstmt.setString(3, objBancoDeDadosDTO.getData());
            pstmt.setString(4, objBancoDeDadosDTO.getDesligarPC());
            pstmt.setString(5, objBancoDeDadosDTO.getNomeBackup());
            pstmt.setString(6, objBancoDeDadosDTO.getHora());
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Cadastro realizado!", "Informação", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error Método Cadastrar" + e, "Error", JOptionPane.ERROR);
        }
    }

    /**
     * Atualiza um registro existente na tabela 'dados_backup' no banco de
     * dados.
     * <p>
     * Este método atualiza um registro na tabela 'dados_backup' com base nos
     * dados fornecidos pelo objeto {@link BancoDeDadosDTO}. A chave primária
     * 'id' é usada para identificar o registro a ser atualizado. A consulta SQL
     * é preparada com parâmetros para garantir a segurança contra SQL
     * Injection.
     * <p>
     * Estrutura da atualização SQL:
     * <pre>
     * UPDATE dados_backup SET diretorio_origem = ?, diretorio_destino = ?, data = ?,
     * desligar_pc = ?, nome_backup = ?, hora = ? WHERE id = ?
     * </pre>
     * <p>
     * Após a execução bem-sucedida da atualização, uma mensagem de confirmação
     * é exibida. Em caso de falha, uma mensagem de erro detalhando a exceção é
     * mostrada ao usuário.
     *
     * @param objBancoDeDadosDTO Objeto {@link BancoDeDadosDTO} contendo os
     * dados atualizados e o ID do registro a ser editado. Não deve ser
     * {@code null}.
     * @throws NullPointerException Se {@code objBancoDeDadosDTO} for
     * {@code null}.
     */
    public void editar(BancoDeDadosDTO objBancoDeDadosDTO) {
        String sql = "UPDATE dados_backup SET diretorio_origem = ?, diretorio_destino = ?, data = ?, desligar_pc = ?, nome_backup = ?, hora = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, objBancoDeDadosDTO.getDiretorioOrigem());
            pstmt.setString(2, objBancoDeDadosDTO.getDiretorioDestino());
            pstmt.setString(3, objBancoDeDadosDTO.getData());
            pstmt.setString(4, objBancoDeDadosDTO.getDesligarPC());
            pstmt.setString(5, objBancoDeDadosDTO.getNomeBackup());
            pstmt.setString(6, objBancoDeDadosDTO.getHora());
            pstmt.setInt(7, objBancoDeDadosDTO.getId());

            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Agendamento de Buckup Atualizado!", "Informação", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error Método Editar" + e, "Error", JOptionPane.ERROR);
        }
    }

    /**
     * Passa por parâmetro o ID do agendamento a ser excluido no banco de dados
     *
     * @param objBancoDeDadosDTO
     */
    public void excluir(BancoDeDadosDTO objBancoDeDadosDTO) {
        String sql = "DELETE FROM dados_backup WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, objBancoDeDadosDTO.getId());
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Agendado de Backup Ecluído!", "Informação", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error Método Excluir" + e, "Error", JOptionPane.ERROR);
        }
    }

    /**
     * Recupera todos os registros da tabela 'dados_backup' do banco de dados.
     * <p>
     * Este método consulta a tabela 'dados_backup' e retorna uma lista de
     * objetos {@link BancoDeDadosDTO}, cada um representando um registro da
     * tabela. A consulta SQL utilizada é uma seleção simples que retorna todos
     * os registros.
     * <p>
     * Estrutura da consulta SQL:
     * <pre>
     * SELECT * FROM dados_backup
     * </pre>
     * <p>
     * Se a consulta for bem-sucedida, uma lista de objetos
     * {@link BancoDeDadosDTO} é retornada. Se ocorrer uma SQLException durante
     * a consulta, uma mensagem de erro é exibida e uma lista vazia é retornada.
     *
     * @return Uma lista de objetos {@link BancoDeDadosDTO}, que pode estar
     * vazia se não houver registros ou em caso de erro na consulta.
     */
    public ArrayList<BancoDeDadosDTO> listar() {
        String sql = "SELECT * FROM dados_backup";

        try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                // Cria um novo objeto FuncionarioModel para armazenar os dados da linha atual
                BancoDeDadosDTO objBancoDeDadosDTO = new BancoDeDadosDTO();

                objBancoDeDadosDTO.setId(rs.getInt("ID"));
                objBancoDeDadosDTO.setDiretorioOrigem(rs.getString("diretorio_origem"));
                objBancoDeDadosDTO.setDiretorioDestino(rs.getString("diretorio_destino"));
                objBancoDeDadosDTO.setData(rs.getString("data"));
                objBancoDeDadosDTO.setDesligarPC(rs.getString("desligar_pc"));
                objBancoDeDadosDTO.setNomeBackup(rs.getString("nome_backup"));
                objBancoDeDadosDTO.setHora(rs.getString("hora"));

                // Adiciona o objeto à lista 'lista'
                lista.add(objBancoDeDadosDTO);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error Método Listar" + e, "Error", JOptionPane.ERROR);
        }

        return lista;
    }

    /**
     * Verifica se a data e a hora atuais correspondem a algum registro na
     * tabela 'dados_backup'.
     * <p>
     * Este método consulta a tabela 'dados_backup' para verificar se existem
     * registros com a data e a hora atuais. A data e a hora são formatadas para
     * o padrão 'dd-MM-yyyy' e 'HH:mm', respectivamente, e comparadas com cada
     * registro na tabela.
     * <p>
     * Estrutura da consulta SQL:
     * <pre>
     * SELECT data, hora FROM dados_backup
     * </pre>
     * <p>
     * Se a data e a hora atuais corresponderem a um registro no banco de dados,
     * retorna {@code true}. Caso contrário, retorna {@code false}. Se ocorrer
     * uma SQLException durante a consulta, uma mensagem de erro é exibida e
     * retorna {@code false}.
     *
     * @return {@code true} se a data e a hora atuais corresponderem a um
     * registro no banco de dados, {@code false} caso contrário ou em caso de
     * erro na consulta.
     */
    public boolean verificarDataHoraAtual() {
        // Formato para data e hora
        SimpleDateFormat formatadorData = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat formatadorHora = new SimpleDateFormat("HH:mm");

        // Obtendo a data e hora atuais
        String dataAtual = formatadorData.format(new Date());
        String horaAtual = formatadorHora.format(new Date());

        // SQL para verificar a data e hora no banco de dados
        String sql = "SELECT data, hora FROM dados_backup";

        try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String dataBanco = rs.getString("data");
                String horaBanco = rs.getString("hora");

                if (dataBanco.equals(dataAtual) && horaBanco.equals(horaAtual)) {
                    return true; // Data e hora do banco são iguais à data e hora atual
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao verificar data e hora: " + e, "Error", JOptionPane.ERROR);
        }
        return false; // Não encontrou nenhuma correspondência
    }

    /**
     * Verifica a existência de registros na tabela 'dados_backup' que
     * correspondam à data e hora atuais.
     * <p>
     * Este método realiza uma consulta na tabela 'dados_backup' para encontrar
     * um registro cuja data e hora correspondam à data e hora atuais do
     * sistema. A data e a hora são formatadas para o padrão 'dd-MM-yyyy' e
     * 'HH:mm', respectivamente. A consulta SQL utiliza
     * {@link PreparedStatement} para garantir a segurança contra SQL Injection.
     * <p>
     * Estrutura da consulta SQL:
     * <pre>
     * SELECT * FROM dados_backup WHERE data = ? AND hora = ?
     * </pre>
     * <p>
     * Se um registro correspondente for encontrado, um objeto
     * {@link BancoDeDadosDTO} com os dados desse registro é retornado. Se não
     * houver correspondência ou ocorrer um erro, retorna {@code null}.
     *
     * @return Um objeto {@link BancoDeDadosDTO} contendo os dados do registro
     * correspondente, ou {@code null} se não houver correspondência ou em caso
     * de erro na consulta.
     */
    public BancoDeDadosDTO verificarDataHoraAtualArquivos() {
        SimpleDateFormat formatadorData = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat formatadorHora = new SimpleDateFormat("HH:mm");
        String dataAtual = formatadorData.format(new Date());
        String horaAtual = formatadorHora.format(new Date());

        String sql = "SELECT * FROM dados_backup WHERE data = ? AND hora = ?";

        try (Connection conn = DriverManager.getConnection(url); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, dataAtual);
            pstmt.setString(2, horaAtual);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                BancoDeDadosDTO dto = new BancoDeDadosDTO();
                dto.setDiretorioOrigem(rs.getString("diretorio_origem"));
                dto.setDiretorioDestino(rs.getString("diretorio_destino"));
                dto.setNomeBackup(rs.getString("nome_backup"));
                // Adicione aqui a configuração de outros campos, se necessário
                return dto; // Retorna o objeto com os dados encontrados
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao verificar data e hora: " + e, "Error", JOptionPane.ERROR);
        }
        return null; // Não encontrou nenhuma correspondência
    }

    /**
     * Verifica se o campo 'desligar_pc' está definido como 'Sim' para a data e
     * hora atuais na tabela 'dados_backup'.
     * <p>
     * Este método realiza uma consulta na tabela 'dados_backup' para verificar
     * se o campo 'desligar_pc' está definido como 'Sim' para um registro cuja
     * data e hora correspondam à data e hora atuais do sistema. A data e a hora
     * são formatadas para os padrões 'dd-MM-yyyy' e 'HH:mm', respectivamente. A
     * consulta SQL utiliza {@link PreparedStatement} para prevenir SQL
     * Injection.
     * <p>
     * Estrutura da consulta SQL:
     * <pre>
     * SELECT desligar_pc FROM dados_backup WHERE data = ? AND hora = ?
     * </pre>
     * <p>
     * Retorna {@code true} se o campo 'desligar_pc' estiver definido como 'Sim'
     * para a data e hora atuais. Caso contrário, ou em caso de erro na
     * consulta, retorna {@code false}.
     *
     * @return {@code true} se o campo 'desligar_pc' estiver definido como 'Sim'
     * para a data e hora atuais, {@code false} em outros casos ou em caso de
     * erro na consulta.
     */
    public boolean verificarDesligarPC() {
        // Formato para data e hora
        SimpleDateFormat formatadorData = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat formatadorHora = new SimpleDateFormat("HH:mm");

        // Obtendo a data e hora atuais
        String dataAtual = formatadorData.format(new Date());
        String horaAtual = formatadorHora.format(new Date());

        // SQL para verificar a data, hora e o campo desligar_pc no banco de dados
        String sql = "SELECT desligar_pc FROM dados_backup WHERE data = ? AND hora = ?";

        try (Connection conn = DriverManager.getConnection(url); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, dataAtual);
            pstmt.setString(2, horaAtual);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String desligarPC = rs.getString("desligar_pc");
                return "Sim".equalsIgnoreCase(desligarPC); // Retorna true se for "Sim", false caso contrário
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao verificar desligar_pc: " + e, "Error", JOptionPane.ERROR);
        }
        return false; // Retorna false se não encontrar correspondência ou ocorrer um erro
    }

}
