package com.mycompany.ferramentadebackup.dto;

/**
 * <p>Classe DTO (Data Transfer Object) para gerenciar os dados relacionados ao
 * banco de dados. Esta classe contém propriedades e métodos para acessar e
 * modificar dados de backup, como:</p>
 * 
 * <ul>
 *   <li>Diretórios de origem e destino</li>
 *   <li>Data</li>
 *   <li>Opção de desligar o PC</li>
 * </ul>
 *
 * <p>Os métodos incluem:</p>
 * <ul>
 *   <li>{@link #getDiretorioOrigem()} e {@link #setDiretorioOrigem(String)} para acessar e modificar o diretório de origem</li>
 *   <li>{@link #getDiretorioDestino()} e {@link #setDiretorioDestino(String)} para acessar e modificar o diretório de destino</li>
 *   <li>{@link #getData()} e {@link #setData(String)} para acessar e modificar a data</li>
 *   <li>{@link #getDesligarPC()} e {@link #setDesligarPC(String)} para acessar e modificar a opção de desligar o PC</li>
 *   <li>{@link #getId()} e {@link #setId(int)} para acessar e modificar o ID</li>
 * </ul>
 */
public class BancoDeDadosDTO {

    private String diretorioOrigem, diretorioDestino, data, desligarPC, nomeBackup, hora;
    private int id;

    /**
     * Obtém o diretório de origem.
     *
     * @return O diretório de origem.
     */
    public String getDiretorioOrigem() {
        return diretorioOrigem;
    }

    /**
     * Define o diretório de origem.
     *
     * @param diretorioOrigem O diretório de origem a ser definido.
     */
    public void setDiretorioOrigem(String diretorioOrigem) {
        this.diretorioOrigem = diretorioOrigem;
    }

    /**
     * Obtém o diretório de destino.
     *
     * @return O diretório de destino.
     */
    public String getDiretorioDestino() {
        return diretorioDestino;
    }

    /**
     * Define o diretório de destino.
     *
     * @param diretorioDestino O diretório de destino a ser definido.
     */
    public void setDiretorioDestino(String diretorioDestino) {
        this.diretorioDestino = diretorioDestino;
    }

    /**
     * Obtém a data.
     *
     * @return A data.
     */
    public String getData() {
        return data;
    }

    /**
     * Define a data.
     *
     * @param data A data a ser definida.
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * Obtém o status de desligamento do PC.
     *
     * @return O status de desligamento do PC.
     */
    public String getDesligarPC() {
        return desligarPC;
    }

    /**
     * Define o status de desligamento do PC.
     *
     * @param desligarPC O status de desligamento do PC a ser definido.
     */
    public void setDesligarPC(String desligarPC) {
        this.desligarPC = desligarPC;
    }

    /**
     * Obtém o ID.
     *
     * @return O ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Define o ID.
     *
     * @param id O ID a ser definido.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the nomeBackup
     */
    public String getNomeBackup() {
        return nomeBackup;
    }

    /**
     * @param nomeBackup the nomeBackup to set
     */
    public void setNomeBackup(String nomeBackup) {
        this.nomeBackup = nomeBackup;
    }

    /**
     * @return the hora
     */
    public String getHora() {
        return hora;
    }

    /**
     * @param hora the hora to set
     */
    public void setHora(String hora) {
        this.hora = hora;
    }
}
