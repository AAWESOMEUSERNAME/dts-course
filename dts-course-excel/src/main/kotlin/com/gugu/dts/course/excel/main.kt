package com.gugu.dts.course.excel

import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File

fun main() {
    val wb = XSSFWorkbook()
    wb.createSheet("test_sheet")
    val out = File("D:/test.xlsx").outputStream()
    out.use {
        wb.write(it)
    }
}