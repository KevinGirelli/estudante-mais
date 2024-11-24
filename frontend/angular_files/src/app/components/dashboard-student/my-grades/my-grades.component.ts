import { Component, OnInit } from '@angular/core';
import { DropdownModule } from 'primeng/dropdown';
import { FormsModule } from '@angular/forms';
import { ListboxModule } from 'primeng/listbox';
import { CommonModule } from '@angular/common';
import { saveAs } from 'file-saver';
import * as ExcelJS from 'exceljs';
import { HttpClientModule } from '@angular/common/http';

interface Grade {
  subject: string;
  trimester1: number | null;
  absences1: number | null;
  trimester2: number | null;
  absences2: number | null;
  trimester3: number | null;
  absences3: number | null;
  finalAverage: number | null;
  totalAbsences: number | null;
}

interface Period {
  label: string;
  value: number;
}
interface Subject {
  label: string;
  value: string;
  type: number;
}

@Component({
  selector: 'app-my-grades',
  standalone: true,
  imports: [
    CommonModule,
    DropdownModule,
    FormsModule,
    ListboxModule,
    HttpClientModule
  ],
  templateUrl: './my-grades.component.html',
  styleUrls: ['./my-grades.component.scss']
})
export class MyGradesComponent implements OnInit {
  periods: Period[] = [];
  selectedPeriod!: number;

  subjects: Subject[] = [];
  selectedSubject!: string;

  onPeriodSelected() {

  }

  onSelectChange() {

  }

  grades: Grade[] = [];
  totalGradeAverage: number = 7.5;
  totalAbsencesSum: number = 93;
  
  studentData = {
    name: 'Kévin Girelli',
    shift: 'Integral',
    class: '3-51',
    stage: '3° Ano - Ensino Médio',
    year: 2024
  };
  

  ngOnInit(): void {
    
  }

  initializeGrades(): void {
    this.grades = [
      {
        subject: 'Biologia',
        trimester1: 10,
        absences1: 10,
        trimester2: 9,
        absences2: 14,
        trimester3: 5,
        absences3: 10,
        finalAverage: 8,
        totalAbsences: 34
      },
      {
        subject: 'Matemática',
        trimester1: null,
        absences1: null,
        trimester2: null,
        absences2: null,
        trimester3: null,
        absences3: null,
        finalAverage: null,
        totalAbsences: null
      },
      {
        subject: 'Geografia',
        trimester1: null,
        absences1: null,
        trimester2: null,
        absences2: null,
        trimester3: null,
        absences3: null,
        finalAverage: null,
        totalAbsences: null
      },
      {
        subject: 'História',
        trimester1: null,
        absences1: null,
        trimester2: null,
        absences2: null,
        trimester3: null,
        absences3: null,
        finalAverage: null,
        totalAbsences: null
      },
      {
        subject: 'Educação Física',
        trimester1: null,
        absences1: null,
        trimester2: null,
        absences2: null,
        trimester3: null,
        absences3: null,
        finalAverage: null,
        totalAbsences: null
      },
      {
        subject: 'Inglês',
        trimester1: null,
        absences1: null,
        trimester2: null,
        absences2: null,
        trimester3: null,
        absences3: null,
        finalAverage: null,
        totalAbsences: null
      },
      {
        subject: 'Português',
        trimester1: null,
        absences1: null,
        trimester2: null,
        absences2: null,
        trimester3: null,
        absences3: null,
        finalAverage: null,
        totalAbsences: null
      },
      {
        subject: 'Sociologia',
        trimester1: null,
        absences1: null,
        trimester2: null,
        absences2: null,
        trimester3: null,
        absences3: null,
        finalAverage: null,
        totalAbsences: null
      },
      {
        subject: 'Física',
        trimester1: null,
        absences1: null,
        trimester2: null,
        absences2: null,
        trimester3: null,
        absences3: null,
        finalAverage: null,
        totalAbsences: null
      },
      {
        subject: 'Química',
        trimester1: null,
        absences1: null,
        trimester2: null,
        absences2: null,
        trimester3: null,
        absences3: null,
        finalAverage: null,
        totalAbsences: null
      },
      {
        subject: 'Filosofia',
        trimester1: null,
        absences1: null,
        trimester2: null,
        absences2: null,
        trimester3: null,
        absences3: null,
        finalAverage: null,
        totalAbsences: null
      },
      {
        subject: 'Arte',
        trimester1: null,
        absences1: null,
        trimester2: null,
        absences2: null,
        trimester3: null,
        absences3: null,
        finalAverage: null,
        totalAbsences: null
      }
    ];
  }

  async generateBoletim(): Promise<void> {
    const workbook = new ExcelJS.Workbook();
    const worksheet = workbook.addWorksheet('Boletim Escolar');
    
    workbook.views = [
      {
        x: 0,
        y: 0,
        width: 10000,
        height: 20000,
        firstSheet: 0,
        activeTab: 1,
        visibility: 'visible'
      }
    ];
    
    // Cores
    const colors = {
      primary: '9747FF',
      secondary: 'C9A2FC',
      tertiary: 'D7C1F5',
      quaternary: 'E6DAF5',
      background: 'FAFAFA',
      white: 'FFFFFF',
      text: '292F47'
    };

    // Configurar larguras das colunas
    worksheet.columns = [
      { width: 25 }, // Disciplina
      { width: 12 }, // Nota T1
      { width: 12 }, // Faltas T1
      { width: 12 }, // Nota T2
      { width: 12 }, // Faltas T2
      { width: 12 }, // Nota T3
      { width: 12 }, // Faltas T3
      { width: 17 }, // Média Final
      { width: 20 }  // Total Faltas
    ];

    // Cabeçalho com logo
    const logoRow = worksheet.addRow(['']);
    logoRow.height = 40;
    logoRow.alignment = { vertical: 'middle', horizontal: 'left' };

    // Logo do Estudante+
    const logoImage = workbook.addImage({
      base64: 'data:image/png;base64,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',
      extension: 'png',
    });

    worksheet.mergeCells('A1:B1');
    const cellA1 = worksheet.getCell('A1');
    
    cellA1.fill = {
      type: 'pattern',
      pattern: 'solid',
      fgColor: { argb: colors.primary },
      
    };

    worksheet.getCell('C1').fill = {
      type: 'pattern',
      pattern: 'solid',
      fgColor: { argb: colors.primary },
      
    };
    
    worksheet.addImage(logoImage, {
      tl: { col: 2.8, row: 0.2 },
      ext: { width: 60, height: 60 }
    });
    
    const logoCell = worksheet.getCell('C1');
    logoCell.alignment = { vertical: 'middle', horizontal: 'left' };
    worksheet.getRow(1).height = 60;
  
    // Título Estudante+
    worksheet.mergeCells('D1:I1');
    const titleCell = worksheet.getCell('D1');
    titleCell.value = 'Estudante +';
    titleCell.font = { 
      name: 'Poppins',
      size: 24, 
      bold: true, 
      color: { argb: colors.white }
    };
    titleCell.fill = {
      type: 'pattern',
      pattern: 'solid',
      fgColor: { argb: colors.primary }
    };
    titleCell.alignment = { horizontal: 'left', vertical: 'middle' };

    // Título do Boletim e Ano
    worksheet.mergeCells('A2:H2');
    const boletimCell = worksheet.getCell('A2');
    boletimCell.value = 'BOLETIM ESCOLAR';
    boletimCell.font = { 
      name: 'Poppins',
      size: 12, 
      bold: true,
      color: { argb: colors.text }
    };
    boletimCell.fill = {
      type: 'pattern',
      pattern: 'solid',
      fgColor: { argb: colors.secondary }
    };
    boletimCell.alignment = { horizontal: 'center', vertical: 'middle' };

    worksheet.mergeCells('I2:I2');
    const yearCell = worksheet.getCell('I2');
    yearCell.value = this.studentData.year;
    yearCell.font = { 
      name: 'Poppins',
      size: 12, 
      bold: true,
      color: { argb: colors.text }
    };
    yearCell.fill = {
      type: 'pattern',
      pattern: 'solid',
      fgColor: { argb: colors.secondary }
    };
    yearCell.alignment = { horizontal: 'center', vertical: 'middle' };

    // Informações do aluno
    const studentInfo = [
      `Aluno: ${this.studentData.name}`,
      `Turno: ${this.studentData.shift}`,
      `Turma: ${this.studentData.class}`,
      `Etapa: ${this.studentData.stage}`
    ];

    worksheet.mergeCells('A3:C3');
    worksheet.mergeCells('D3:E3');
    worksheet.mergeCells('F3:G3');
    worksheet.mergeCells('H3:I3');

    ['A3', 'D3', 'F3', 'H3'].forEach((cell, index) => {
      const infoCell = worksheet.getCell(cell);
      infoCell.value = studentInfo[index];
      infoCell.font = { 
        name: 'Poppins',
        size: 11, 
        bold: true,
        color: { argb: colors.text }
      };
      infoCell.fill = {
        type: 'pattern',
        pattern: 'solid',
        fgColor: { argb: colors.quaternary }
      };
      infoCell.alignment = { horizontal: 'left', vertical: 'middle' };
    });

    // Cabeçalho da tabela de notas
    const headerRow = worksheet.addRow(['Disciplina', '', '', '', '', '', '', '', '']);
    worksheet.mergeCells('A4:A5');
    worksheet.mergeCells('H4:H5');
    worksheet.mergeCells('I4:I5');

    // Estilo do cabeçalho principal
    headerRow.eachCell((cell) => {
      cell.font = { 
        name: 'Poppins',
        bold: true, 
        color: { argb: colors.white }
      };
      cell.fill = {
        type: 'pattern',
        pattern: 'solid',
        fgColor: { argb: colors.primary }
      };
      cell.alignment = { horizontal: 'center', vertical: 'middle' };
    });

    // Adicionar trimestres
    worksheet.mergeCells('B4:C4');
    worksheet.mergeCells('D4:E4');
    worksheet.mergeCells('F4:G4');

    const trimesterRow = worksheet.getRow(4);
    trimesterRow.getCell(2).value = 'Trimestre 1';
    trimesterRow.getCell(4).value = 'Trimestre 2';
    trimesterRow.getCell(6).value = 'Trimestre 3';
    trimesterRow.getCell(8).value = 'Média Final';
    trimesterRow.getCell(9).value = 'Total de Faltas';

    // Adicionar cabeçalhos Nota/Faltas
    const subHeaderRow = worksheet.getRow(5);
    subHeaderRow.getCell(2).value = 'Nota';
    subHeaderRow.getCell(3).value = 'Faltas';
    subHeaderRow.getCell(4).value = 'Nota';
    subHeaderRow.getCell(5).value = 'Faltas';
    subHeaderRow.getCell(6).value = 'Nota';
    subHeaderRow.getCell(7).value = 'Faltas';

    // Estilo para ambas as linhas de cabeçalho
    [trimesterRow, subHeaderRow].forEach(row => {
      row.eachCell((cell) => {
        if (cell.value) {
          cell.font = { 
            name: 'Poppins',
            bold: true,
            color: { argb: colors.white }
          };
          cell.fill = {
            type: 'pattern',
            pattern: 'solid',
            fgColor: { argb: colors.primary }
          };
          cell.alignment = { horizontal: 'center', vertical: 'middle' };
        }
      });
    });

    // Adicionar notas
    this.grades.forEach((grade, index) => {
      const row = worksheet.addRow([
        grade.subject,
        grade.trimester1 ?? '',
        grade.absences1 ?? '',
        grade.trimester2 ?? '',
        grade.absences2 ?? '',
        grade.trimester3 ?? '',
        grade.absences3 ?? '',
        grade.finalAverage ?? '',
        grade.totalAbsences ?? ''
      ]);

      row.eachCell((cell, cellIndex) => {
        cell.font = { 
          name: 'Poppins',
          color: { argb: colors.text },
          bold: cellIndex === 8 || cellIndex === 9
        };
        cell.fill = {
          type: 'pattern',
          pattern: 'solid',
          fgColor: { argb: index % 2 === 0 ? colors.quaternary : colors.background }
        };
        cell.alignment = { 
          horizontal: cellIndex === 1 ? 'left' : 'right', 
          vertical: 'middle' 
        };
      });
    });

    // Adicionar médias totais
    const totalRow = worksheet.addRow([
      'Média Total das Notas',
      '',
      '',
      '',
      '',
      '',
      '',
      '',
      this.totalGradeAverage
    ]);

    const absencesRow = worksheet.addRow([
      'Média Total das Faltas',
      '',
      '',
      '',
      '',
      '',
      '',
      '',
      this.totalAbsencesSum
    ]);

    [totalRow, absencesRow].forEach(row => {
      row.eachCell((cell) => {
        cell.font = { 
          name: 'Poppins',
          bold: true,
          color: { argb: colors.text }
        };
        cell.fill = {
          type: 'pattern',
          pattern: 'solid',
          fgColor: { argb: colors.secondary }
        };
        cell.alignment = { horizontal: 'right', vertical: 'middle' };
      });
    });

    // Gerar o arquivo Excel
    const buffer = await workbook.xlsx.writeBuffer();
    saveAs(new Blob([buffer]), 'boletim_escolar.xlsx');
  }
}
