package com.pontoeletronico.backend.service;

import com.pontoeletronico.backend.model.RegistroPonto;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

import java.time.format.DateTimeFormatter;

import java.util.List;

@Service
public class ExcelService {

    public byte[] gerarExcel(
            List<RegistroPonto> registros
    ) {

        try (

                Workbook workbook =
                        new XSSFWorkbook();

                ByteArrayOutputStream out =
                        new ByteArrayOutputStream()

        ) {

 
            Sheet sheet =
                    workbook.createSheet(
                            "Histórico de Pontos"
                    );


            Row header =
                    sheet.createRow(0);

            header.createCell(0)
                    .setCellValue("ID");

            header.createCell(1)
                    .setCellValue("Funcionário");

            header.createCell(2)
                    .setCellValue("Email");

            header.createCell(3)
                    .setCellValue("Tipo");

            header.createCell(4)
                    .setCellValue("Data/Hora");


            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern(
                            "dd/MM/yyyy HH:mm:ss"
                    );


            int rowIdx = 1;

            for (RegistroPonto registro : registros) {

                Row row =
                        sheet.createRow(rowIdx++);

                // ID
                row.createCell(0)
                        .setCellValue(
                                registro.getId()
                        );

                // Funcionário
                row.createCell(1)
                        .setCellValue(
                                registro
                                        .getUsuario()
                                        .getNome()
                        );

                // Email
                row.createCell(2)
                        .setCellValue(
                                registro
                                        .getUsuario()
                                        .getEmail()
                        );

                // Tipo
                row.createCell(3)
                        .setCellValue(
                                registro
                                        .getTipo()
                                        .name()
                        );

                // Data/Hora
                row.createCell(4)
                        .setCellValue(
                                registro
                                        .getDataHora()
                                        .format(formatter)
                        );
            }


            for (int i = 0; i < 5; i++) {

                sheet.autoSizeColumn(i);
            }


            workbook.write(out);

            return out.toByteArray();

        } catch (Exception e) {

            throw new RuntimeException(
                    "Erro ao gerar Excel"
            );
        }
    }
}