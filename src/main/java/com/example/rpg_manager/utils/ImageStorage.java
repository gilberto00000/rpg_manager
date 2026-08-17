package com.example.rpg_manager.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public final class ImageStorage {

    /*
     * Pasta base das imagens do aplicativo.
     *
     * Como é relativa, será criada a partir da pasta
     * onde o programa estiver sendo executado.
     */
    private static final Path BASE_DIR =
            Path.of("data", "images");

    private ImageStorage() {
    }


    /*
     * =====================================================
     * SALVAR IMAGEM
     * =====================================================
     */

    public static String salvarImagem(
            File arquivoOriginal,
            String categoria
    ) {

        if (arquivoOriginal == null) {
            return null;
        }

        if (!arquivoOriginal.exists()) {
            throw new IllegalArgumentException(
                    "A imagem selecionada não existe."
            );
        }

        try {

            Path pastaDestino =
                    BASE_DIR.resolve(categoria);

            /*
             * Cria:
             *
             * data/
             *   images/
             *     cenarios/
             *
             * caso ainda não exista.
             */
            Files.createDirectories(
                    pastaDestino
            );


            String extensao =
                    pegarExtensao(
                            arquivoOriginal.getName()
                    );


            /*
             * UUID evita conflitos:
             *
             * castelo.png
             * castelo.png
             *
             * viram nomes diferentes.
             */
            String novoNome =
                    UUID.randomUUID()
                            + extensao;


            Path destino =
                    pastaDestino.resolve(
                            novoNome
                    );


            Files.copy(
                    arquivoOriginal.toPath(),
                    destino,
                    StandardCopyOption.REPLACE_EXISTING
            );


            /*
             * IMPORTANTE:
             *
             * retorna caminho RELATIVO.
             *
             * Ex:
             *
             * data/images/cenarios/abc.png
             */
            return destino
                    .normalize()
                    .toString();

        } catch (IOException e) {

            throw new RuntimeException(
                    "Erro ao salvar imagem.",
                    e
            );
        }
    }


    /*
     * =====================================================
     * RESOLVER CAMINHO
     * =====================================================
     */

    public static File carregarArquivo(
            String caminhoRelativo
    ) {

        if (caminhoRelativo == null
                || caminhoRelativo.isBlank()) {

            return null;
        }

        Path caminho =
                Path.of(caminhoRelativo)
                        .toAbsolutePath()
                        .normalize();

        return caminho.toFile();
    }


    /*
     * =====================================================
     * VERIFICAR SE JÁ É UMA IMAGEM INTERNA
     * =====================================================
     */

    public static boolean ehImagemInterna(
            String caminho
    ) {

        if (caminho == null
                || caminho.isBlank()) {

            return false;
        }

        Path base =
                BASE_DIR
                        .toAbsolutePath()
                        .normalize();

        Path arquivo =
                Path.of(caminho)
                        .toAbsolutePath()
                        .normalize();

        return arquivo.startsWith(base);
    }


    /*
     * =====================================================
     * EXTENSÃO
     * =====================================================
     */

    private static String pegarExtensao(
            String nomeArquivo
    ) {

        int ponto =
                nomeArquivo.lastIndexOf('.');

        if (ponto < 0) {
            return "";
        }

        return nomeArquivo
                .substring(ponto)
                .toLowerCase();
    }
}