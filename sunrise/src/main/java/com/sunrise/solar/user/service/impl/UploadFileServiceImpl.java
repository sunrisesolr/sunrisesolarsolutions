package com.sunrise.solar.user.service.impl;

import com.sunrise.solar.user.SubmitFormEntity;
import com.sunrise.solar.user.repo.SubmitFormRepo;
import com.sunrise.solar.user.service.UploadFileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

@Slf4j
@Service
public class UploadFileServiceImpl implements  UploadFileService{

    private static final DataFormatter formatter = new DataFormatter();

    @Autowired
    private SubmitFormRepo submitFormRepo;


    @Override
    public void uploadFile(String name, MultipartFile file) throws IOException, InvalidFormatException {
        processRowByRow(getFile(name,file),new StringBuilder());


    }


    public void processRowByRow(File excelFile, StringBuilder errorString) throws InvalidFormatException, IOException, InvalidFormatException {
        Workbook workbook = new XSSFWorkbook(excelFile);
        Sheet amcSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = amcSheet.iterator();


        while (iterator.hasNext()) {
            Row currentRow = iterator.next();
            String name=getCellValue(currentRow,0);
            String email=getCellValue(currentRow,1);
            String phone=getCellValue(currentRow,2);
            String address=getCellValue(currentRow,3);
            String comment=getCellValue(currentRow,4);
            SubmitFormEntity submitFormEntity = new SubmitFormEntity();
            submitFormEntity.setName(name);
            submitFormEntity.setEmail(email);
            submitFormEntity.setPhoneNumber(phone);
            submitFormEntity.setLocation(address);
            submitFormEntity.setComments(comment);
            submitFormRepo.save(submitFormEntity);
        }
        workbook.close();
    }



    public File getFile (String name, MultipartFile file) throws IOException {
        // Creating the directory to store file
        File serverFile = null;
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                // Creating the directory to store file.
                String rootPath = "/tmp";
                File dir = new File(rootPath + File.separator + "tmpFiles");

                if (!dir.exists())
                    dir.mkdirs();

                // Create the file on server.
                serverFile = new File(dir.getAbsolutePath()+ File.separator + name);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();
            }catch (Exception e){
                log.error("Error occurred while processing scheme master xlsx file.",e);
                return null;
            }
        }
        return serverFile;
    }

    private String getCellValue(Row currentRow, int cellNum){
        return formatter.formatCellValue(currentRow.getCell(cellNum)).replace(",","").replace(" ","");
    }


}
