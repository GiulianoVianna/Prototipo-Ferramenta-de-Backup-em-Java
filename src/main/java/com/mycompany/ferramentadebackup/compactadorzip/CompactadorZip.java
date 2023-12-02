package com.mycompany.ferramentadebackup.compactadorzip;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.*;

/**
 * Classe para compactação de arquivos e diretórios em formato ZIP.
 * <p>
 * Esta classe fornece funcionalidades para compactar um único arquivo ou todos
 * os arquivos em um diretório para o formato ZIP. A compactação inclui a
 * possibilidade de definir um nome personalizado para o arquivo ZIP resultante.
 */
public class CompactadorZip {

    /**
     * Compacta um arquivo ou diretório para um arquivo ZIP.
     * <p>
     * Se o caminho de origem for um diretório, todos os arquivos e subdiretórios serão adicionados
     * ao arquivo ZIP. Se for um arquivo, somente este será adicionado. O nome do arquivo ZIP pode
     * ser personalizado. Se não for fornecido, o nome original do arquivo/diretório será usado.
     *
     * @param origem         O caminho do arquivo ou diretório a ser compactado.
     * @param destinoZip     O caminho do arquivo ZIP de destino.
     * @param nomeArquivoZip O nome personalizado para a entrada do arquivo ZIP. Pode ser {@code null} ou vazio.
     * @throws IOException Se ocorrer um erro durante a compactação.
     */
    public static void compactarParaZip(String origem, String destinoZip, String nomeArquivoZip) throws IOException {
        Path caminhoOrigem = Paths.get(origem);
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(destinoZip))) {
            if (Files.isDirectory(caminhoOrigem)) {
                adicionarDiretorioAoZip(caminhoOrigem, caminhoOrigem, zipOutputStream);
            } else {
                adicionarArquivoAoZip(caminhoOrigem, zipOutputStream, nomeArquivoZip);
            }
        }
    }

    /**
     * Adiciona um diretório ao arquivo ZIP, incluindo todos os seus arquivos e subdiretórios.
     * <p>
     * Este método é chamado recursivamente para cada subdiretório encontrado.
     *
     * @param diretorio        O caminho do diretório a ser adicionado ao ZIP.
     * @param caminhoBase      O caminho base para calcular os nomes relativos das entradas no ZIP.
     * @param zipOutputStream  A stream de saída do arquivo ZIP.
     * @throws IOException Se ocorrer um erro durante a adição do diretório ao ZIP.
     */
    private static void adicionarDiretorioAoZip(Path diretorio, Path caminhoBase, ZipOutputStream zipOutputStream) throws IOException {
        Files.walkFileTree(diretorio, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path arquivo, BasicFileAttributes attrs) throws IOException {
                adicionarArquivoAoZip(arquivo, caminhoBase, zipOutputStream);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    /**
     * Adiciona um arquivo ao arquivo ZIP com um nome de entrada personalizado.
     * <p>
     * Se o nome do arquivo ZIP for fornecido, ele será usado como o nome da entrada no ZIP. 
     * Caso contrário, o nome do arquivo original será usado.
     *
     * @param arquivo         O caminho do arquivo a ser adicionado ao ZIP.
     * @param zipOutputStream A stream de saída do arquivo ZIP.
     * @param nomeArquivoZip  O nome personalizado para a entrada do arquivo ZIP.
     * @throws IOException Se ocorrer um erro durante a adição do arquivo ao ZIP.
     */
    private static void adicionarArquivoAoZip(Path arquivo, ZipOutputStream zipOutputStream, String nomeArquivoZip) throws IOException {
        String nomeEntradaZip = nomeArquivoZip != null && !nomeArquivoZip.isEmpty() ? nomeArquivoZip : arquivo.getFileName().toString();
        System.out.println("Adicionando arquivo: " + nomeEntradaZip);
        ZipEntry zipEntry = new ZipEntry(nomeEntradaZip);
        zipOutputStream.putNextEntry(zipEntry);
        Files.copy(arquivo, zipOutputStream);
        zipOutputStream.closeEntry();
    }

    /**
     * Adiciona um arquivo ao arquivo ZIP, mantendo a estrutura de diretórios relativa.
     * <p>
     * O nome da entrada no ZIP é calculado relativamente ao caminho base fornecido.
     *
     * @param arquivo          O caminho do arquivo a ser adicionado ao ZIP.
     * @param caminhoBase      O caminho base para calcular o nome relativo da entrada no ZIP.
     * @param zipOutputStream  A stream de saída do arquivo ZIP.
     * @throws IOException Se ocorrer um erro durante a adição do arquivo ao ZIP.
     */
    private static void adicionarArquivoAoZip(Path arquivo, Path caminhoBase, ZipOutputStream zipOutputStream) throws IOException {
        String nomeEntradaZip = caminhoBase.relativize(arquivo).toString();
        System.out.println("Adicionando ao ZIP: " + nomeEntradaZip);
        ZipEntry zipEntry = new ZipEntry(nomeEntradaZip);
        zipOutputStream.putNextEntry(zipEntry);
        Files.copy(arquivo, zipOutputStream);
        zipOutputStream.closeEntry();
    }
}
